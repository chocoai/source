/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.fgcqualitycheck.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.asset.aliyunimage.web.AmAliyunImageController;
import com.jeesite.modules.asset.fgcimage.entity.FgcImg;
import com.jeesite.modules.asset.fgcimage.service.FgcImgService;
import com.jeesite.modules.asset.fgcqualitycheck.common.Convert;
import com.jeesite.modules.asset.fgcqualitycheck.entity.FgcLog;
import com.jeesite.modules.asset.fgcqualitycheck.entity.SyncQcBillToK3;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.fgcqualitycheck.entity.QualityCheck;
import com.jeesite.modules.asset.fgcqualitycheck.dao.QualityCheckDao;
import com.jeesite.modules.asset.fgcqualitycheck.entity.QualityCheckDetails;
import com.jeesite.modules.asset.fgcqualitycheck.dao.QualityCheckDetailsDao;

/**
 * 质检单Service
 * @author len
 * @version 2018-08-18
 */
@Service
@Transactional(readOnly=true)
public class QualityCheckService extends CrudService<QualityCheckDao, QualityCheck> {
	private static Logger log = LoggerFactory.getLogger(QualityCheckService.class);
	@Autowired
	private QualityCheckDetailsDao qualityCheckDetailsDao;
	@Autowired
	private QualityCheckDao qualityCheckDao;
	@Autowired
	private K3connection k3connection;
	@Value("${POST_K3ClOUDRL}")
	String POST_K3ClOUDRLS;

	@Autowired
	private FgcImgService fgcImgService;

	@Autowired
	private AmUtilService amUtilService;

	public static final String YF_PUR_QualityCheck = "YF_PUR_QualityCheck";

	@Autowired
	private FgcLogService fgcLogService;
	/**
	 * 获取单条数据
	 * @param qualityCheck
	 * @return
	 */
	@Override
	public QualityCheck get(QualityCheck qualityCheck) {
		QualityCheck entity = super.get(qualityCheck);
		if (entity != null){
			QualityCheckDetails qualityCheckDetails = new QualityCheckDetails(entity);
			qualityCheckDetails.setStatus(QualityCheckDetails.STATUS_NORMAL);
			entity.setQualityCheckDetailsList(qualityCheckDetailsDao.findList(qualityCheckDetails));
		}
		return entity;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param qualityCheck
	 * @return
	 */
	@Override
	public Page<QualityCheck> findPage(Page<QualityCheck> page, QualityCheck qualityCheck) {
		if ("完成".equals(qualityCheck.getDocumentStatus())) {
			qualityCheck.setDocumentStatus(null);
			qualityCheck.setBillStatus("1");
			//  [创建时间]在当前时间往前30天内
			Calendar now = Calendar.getInstance();
			now.add(Calendar.DAY_OF_MONTH, -30);
			qualityCheck.setEndDate(now.getTime());
			qualityCheck.setNowDate(new Date());
		}
		page = super.findPage(page, qualityCheck);
		for (int i = 0; i < page.getList().size(); i++) {
			// 预约时间类型
			String reservationTimeType = amUtilService.findDictLabel(page.getList().get(i).getReservationTimeType(),"fgc_reservation_time_type");
			page.getList().get(i).setReservationTimeType(reservationTimeType);
		}
		return super.findPage(page, qualityCheck);
	}

	/**
	 * 保存数据（插入或更新）
	 * @param qualityCheck
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(QualityCheck qualityCheck) {
		super.save(qualityCheck);
		// 保存 QualityCheck子表
		for (QualityCheckDetails qualityCheckDetails : qualityCheck.getQualityCheckDetailsList()){
			if (!QualityCheckDetails.STATUS_DELETE.equals(qualityCheckDetails.getStatus())){
				qualityCheckDetails.setFid(qualityCheck);
				if (qualityCheckDetails.getIsNewRecord()){
					qualityCheckDetails.preInsert();
					qualityCheckDetailsDao.insert(qualityCheckDetails);
				}else{
					qualityCheckDetails.preUpdate();
					qualityCheckDetailsDao.update(qualityCheckDetails);
				}
			}else{
				qualityCheckDetailsDao.delete(qualityCheckDetails);
			}
		}
	}

	/**
	 * 更新状态
	 * @param qualityCheck
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(QualityCheck qualityCheck) {
		super.updateStatus(qualityCheck);
	}

	/**
	 * 删除数据
	 * @param qualityCheck
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(QualityCheck qualityCheck) {
		super.delete(qualityCheck);
		QualityCheckDetails qualityCheckDetails = new QualityCheckDetails();
		qualityCheckDetails.setFid(qualityCheck);
		qualityCheckDetailsDao.delete(qualityCheckDetails);
	}

	@Transactional
	public QualityCheckDetails selectByEntityId(String entityId) {
		return qualityCheckDetailsDao.selectByEntityId(entityId);
	}
	/**
	 * 根据明细编码更新【抽检数量、结构检验、外观检验、包装检验、整改状态、不合格数、不良比例、整改类型、合格类型】
	 * @param
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveDetail (QualityCheckDetails qualityCheckDetails) {
		qualityCheckDetails.preUpdate();
		qualityCheckDetailsDao.update(qualityCheckDetails);
	}

	/**
	 * 用户填写完质检报告内容后，点击【提交质检报告】按钮时调用接口：
	 * @param qualityCheckDetails
	 * @param qualityCheck
	 * @param fid
	 * @param jsonObject
	 * @param userName
	 * @param entityId
	 */
	@Transactional(readOnly = false)
	public void saveDetail (QualityCheckDetails qualityCheckDetails, QualityCheck qualityCheck, String fid,
							JSONObject jsonObject, String userName, String entityId) {
		qualityCheckDetails.preUpdate();
		qualityCheckDetailsDao.update(qualityCheckDetails);

		// 3） 将不良照片的URL地址及物料信息插入《梵工厂图片》表：
		// 物料编码
		String materialCode = jsonObject.getString("materialCode");
		// 物料名称
		String materialName = jsonObject.getString("materialName");
		// 质检单号
		String billno = jsonObject.getString("billno");
		// 操作人
		List<FgcImg> fgcImgList = new ArrayList<>();
		String photoUrl = Convert.getString(jsonObject, "photo");
		if (!"".equals(photoUrl)) {
			JSONObject json = JSONObject.fromObject(photoUrl);
			for (int i =1; i<= json.size(); i++) {
				String photo = Convert.getString(json, "photoUrl" + i);
				if (!"".equals(photo)) {
					FgcImg fgcImg = new FgcImg();
					fgcImg.setFid(fid);
					fgcImg.setFentryId(entityId);
					fgcImg.setMaterielCode(materialCode);
					fgcImg.setMaterielName(materialName);
					fgcImg.setDocumentCode(billno);
					fgcImg.setPhotoUrl(photo);
					// 照片来源
					fgcImg.setPhotoSource("质检");
					fgcImg.setOperationBy(userName);
					fgcImg.setOperationDate(new Date());
					fgcImg.setIsNewRecord(true);
					fgcImgList.add(fgcImg);
				}
			}
			if (fgcImgList != null && fgcImgList.size() >0) {
				fgcImgService.saveData(fgcImgList);
			}
		}
	}

	/**
	 * 保存主表信息
	 * @param qualityCheck
	 */
	@Transactional(readOnly = false)
	public void saveData(QualityCheck qualityCheck) {
		super.save(qualityCheck);
	}

	/**
	 * 根据质检单号获取主表和明细信息
	 * @param billNo
	 * @return
	 */
	@Transactional(readOnly = true)
	public QualityCheck getData(String billNo) {
		QualityCheck entity = qualityCheckDao.getData(billNo);
		if (entity != null){
			QualityCheckDetails qualityCheckDetails = new QualityCheckDetails(entity);
			if (ParamentUntil.isBackList(qualityCheckDetailsDao.findList(qualityCheckDetails))) {
				List<QualityCheckDetails> detailsList = qualityCheckDetailsDao.findList(qualityCheckDetails);
				boolean flag = true;
				for (int i = 0; i<detailsList.size(); i++) {
					if ("1".equals(detailsList.get(i).getIsCheck())) {
						detailsList.get(i).setIsCheck("已检");
					} else {
						detailsList.get(i).setIsCheck("");
						flag = false;
					}
					if (detailsList.get(i).getAvgBadRatio() == null || "".equals(detailsList.get(i))) {
						detailsList.get(i).setAvgBadRatio(new Double(-1));
					}
				}
				if (flag) {
					entity.setIsCheck("已检");
				} else {
					entity.setIsCheck("");
				}
			}
			entity.setQualityCheckDetailsList(qualityCheckDetailsDao.findList(qualityCheckDetails));
		}
		return entity;
	}

	@Transactional(readOnly = false)
	public void SyncK3QualityCheck(String defaultBeginTime) throws Exception{
		log.info("SyncK3QualityCheck：进入同步K3质检单数据方法成功");
		String querySql = "";
		//连接K3Cloud服务器
		boolean isLogin = k3connection.getConnection();
		if (!isLogin){
			log.info("SyncK3QualityCheck：连接K3Cloud服务器失败!");
			return;
		}
		//质检单主表数据
		querySql = "/*dialect*/\nSELECT quality.FID as fid,FBILLNO as billno,CONVERT(varchar(20),FCREATEDATE,120) as  qualityTime,'初始' as documentStatus,F_YF_RESERVATIONDATETYPE as reservationTimeType,CONVERT(varchar(20),F_YF_RESERVATIONDATE,120) as reservationTime,\n" +
				"Replace(inspector.FNAME,' ','') as qualityInspectorName,qcGroup.FNUMBER as qcGroupName,Replace(buyer.FNAME,' ','') as purchaserName,\n" +
				"buyGroup.FNUMBER as purchasingGroupName,supplier.FNAME as supplierName,'K3接口' as creatorName\n" +
				"FROM YF_T_PUR_QualityCheck as quality\n" +
				"LEFT JOIN V_BD_INSPECTOR_L as inspector on inspector.fid = quality.F_YF_QUALITYUSERID --质检员\n" +
				"LEFT JOIN T_BD_OPERATORGROUPENTRY as qcGroup  on qcGroup.FENTRYID = quality.F_YF_QUALITYGROUP --质检组\n" +
				"LEFT JOIN V_BD_BUYER_L as buyer on buyer.fid = quality.FPURCHASERID  --采购员\n" +
				"LEFT JOIN T_BD_OPERATORGROUPENTRY as buyGroup  on buyGroup.FENTRYID = quality.FPURCHASERGROUPID --采购组\n" +
				"LEFT JOIN T_BD_SUPPLIER_L as supplier on supplier.FSUPPLIERID = quality.F_YF_SUPPLIERID -- 供应商 \n" +
				"WHERE quality.FID > 0 and FQUALITYCHECKSTATUS = 'B' AND  FDOCUMENTSTATUS = 'B'";
		JSONArray jsonArrayQualityCheck = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
		if (jsonArrayQualityCheck == null || jsonArrayQualityCheck.toArray().length <= 0){
			log.info("SyncK3QualityCheck：未查询到质检单单据头数据!");
			return;
		}
		//质检单明细数据
		querySql = "/*dialect*/\nSELECT qc.FID as fid,qcDetails.FEntryID as fentityid,qc.FBILLNO as billno,F_YF_PURCHASEBILLNO as sourceBillno,F_YF_QUALITYQTY as qcQty,\n" +
				"material.FNUMBER as materialCode,materialName.FNAME as materialName,materialName.FSPECIFICATION as specification,qcDetails.F_YF_BATCHTEXT as batchNumber " +
				",ISNULL(materialBase.F_YF_PACKINGVOLUME,0) as packageVolume,ISNULL(materialBase.F_YF_PackNum,0) as packageNum,materialBase.FGROSSWEIGHT as weight,assistData.FNAME as color,\n" +
				"materialBase.F_YF_PackingLen as packingLength,materialBase.F_YF_PackingWid as packingWidth,materialBase.F_YF_PackingHeig as packingHigh\n" +
				"FROM YF_T_PUR_QualityCheck as qc\n" +
				"left join YF_T_PUR_QualityCheckEntry as qcDetails on qc.FID = qcDetails.FID\n" +
				"left join T_BD_MATERIAL as material on material.FMATERIALID = qcDetails.F_YF_MATERIALID \n" +
				"left join T_BD_MATERIAL_L as materialName on materialName.FMATERIALID = material.FMATERIALID\n" +
				"left join t_BD_MaterialBase as materialBase on materialBase.FMATERIALID = qcDetails.F_YF_MATERIALID\n" +
				"LEFT JOIN YF_T_BD_AssistData_L as assistData on material.F_YF_Colour = assistData.Fid\n" +
				"WHERE (qcDetails.FEntryID is not null or qcDetails.FEntryID > 0) and qc.FQUALITYCHECKSTATUS = 'B' AND qc.FDOCUMENTSTATUS = 'B'";
		JSONArray jsonArrayQcDetails = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
		if (jsonArrayQcDetails == null || jsonArrayQcDetails.toArray().length <= 0){
			log.info("SyncK3QualityCheck：未查询到质检单明细数据!");
			return;
		}

		List<QualityCheck> qualityCheckList = (List<QualityCheck>) JSONArray.toCollection(jsonArrayQualityCheck,QualityCheck.class);
		List<QualityCheckDetails> qualityCheckDetailsList = (List<QualityCheckDetails>) JSONArray.toCollection(jsonArrayQcDetails,QualityCheckDetails.class);
		List<QualityCheck> insertQcBillList = new ArrayList<QualityCheck>();
		List<QualityCheckDetails> insertQcBillDetailsList = new ArrayList<QualityCheckDetails>();
		for (QualityCheck qc :qualityCheckList) {
			QualityCheck qualityCheck = this.get(qc.getId());
			if (qualityCheck != null){
				continue;
			}
			List<QualityCheckDetails> qualityCheckDetailsExists = qualityCheckDetailsList.stream().filter(s -> s.getBillno().equals(qc.getBillno())).collect(Collectors.toList());
			if (qualityCheckDetailsExists == null || qualityCheckDetailsExists.size() <= 0){
				continue;
			}
			for (QualityCheckDetails details :qualityCheckDetailsExists) {
				QualityCheckDetails qualityCheckDetails = this.qualityCheckDetailsDao.selectByEntityId(details.getId());
				details.setFid(qc);
				if (qualityCheckDetails == null){
					details.setIsNewRecord(true);
					insertQcBillDetailsList.add(details);
				}
			}
			qc.setCreateTime(new Date());
			qc.setIsNewRecord(true);
			insertQcBillList.add(qc);
		}
		if (insertQcBillList == null || insertQcBillDetailsList == null || insertQcBillList.size() <= 0 || insertQcBillDetailsList.size() <= 0){
			return;
		}
		qualityCheckDao.insertBatch(insertQcBillList);
		qualityCheckDetailsDao.insertBatch(insertQcBillDetailsList);
		log.info("SyncK3QualityCheck：质检单数据同步成功!");
	}


	/**
	 * @desc 保存质检单到K3Cloud
	 * @author AlbertFeng
	 * @date 2018-08-30 15:54
	 * @return void
	 */
	@Transactional()
	public void SaveQcBillToK3(){
		log.info("SaveQcBillToK3：成功调用保存质检单ToK3Cloud服务");
		List<SyncQcBillToK3>  saveToK3QcBills =  qualityCheckDao.findSaveK3QcBill();
		if (saveToK3QcBills == null || saveToK3QcBills.size() <= 0){
			log.info("SaveQcBillToK3：未找到保存到K3Cloud质检单");
			return;
		}
		//连接K3Cloud服务器
		boolean isLogin = k3connection.getConnection();
		if (!isLogin){
			log.warn("SaveQcBillToK3：连接K3Cloud服务器失败!");
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<String> fentryIds = new ArrayList<String>();
		for (SyncQcBillToK3 saveToK3QcBill:saveToK3QcBills) {
			FgcImg fgcImg = new FgcImg();
			fgcImg.setFentryId(saveToK3QcBill.getFentityid());
			fgcImg.setFid(saveToK3QcBill.getFid());
			String pictureUrl1 = "",pictureUrl2 = "",pictureUrl3 = "",pictureUrl4 = "",pictureUrl5 = "",pictureUrl6 = "";
			//获取质检单图片
			List<FgcImg> fgcImgs= fgcImgService.findList(fgcImg);
			if (fgcImgs != null && fgcImgs.size() > 0){
				pictureUrl1 = fgcImgs.get(0) != null?StringUtils.substring(fgcImgs.get(0).getPhotoUrl(),fgcImgs.get(0).getPhotoUrl().indexOf("//"),fgcImgs.get(0).getPhotoUrl().length()):"";
				pictureUrl2 = fgcImgs.size() > 1?fgcImgs.get(1) != null?StringUtils.substring(fgcImgs.get(1).getPhotoUrl(),fgcImgs.get(1).getPhotoUrl().indexOf("//"),fgcImgs.get(1).getPhotoUrl().length()):"":"";
				pictureUrl3 = fgcImgs.size() > 2?fgcImgs.get(2) != null?StringUtils.substring(fgcImgs.get(2).getPhotoUrl(),fgcImgs.get(2).getPhotoUrl().indexOf("//"),fgcImgs.get(2).getPhotoUrl().length()):"":"";
				pictureUrl4 = fgcImgs.size() > 3?fgcImgs.get(3) != null?StringUtils.substring(fgcImgs.get(3).getPhotoUrl(),fgcImgs.get(3).getPhotoUrl().indexOf("//"),fgcImgs.get(3).getPhotoUrl().length()):"":"";
				pictureUrl5 = fgcImgs.size() > 4?fgcImgs.get(4) != null?StringUtils.substring(fgcImgs.get(4).getPhotoUrl(),fgcImgs.get(4).getPhotoUrl().indexOf("//"),fgcImgs.get(4).getPhotoUrl().length()):"":"";
				pictureUrl6 = fgcImgs.size() > 5?fgcImgs.get(5) != null?StringUtils.substring(fgcImgs.get(5).getPhotoUrl(),fgcImgs.get(5).getPhotoUrl().indexOf("//"),fgcImgs.get(5).getPhotoUrl().length()):"":"";
			}
			 try {
				Integer sysltzs = null;
				if (saveToK3QcBill.getQcQualifiedQty() != null) {
					sysltzs = saveToK3QcBill.getQcQualifiedQty().intValue();
				}
			 	//TODO:IsDeleteEntry是否删除明细
				String saveParam = "{\"Creator\": \"\",\"NeedUpDateFields\": [],\"NeedReturnFields\": [],\"IsDeleteEntry\": \"false\",\"SubSystemId\": \"\","+
						"\"IsVerifyBaseDataField\": \"false\",\"IsEntryBatchFill\": \"True\","+
						"\"Model\": {\"FID\": \""+saveToK3QcBill.getFid()+"\","+
						"\"FBillNo\": \""+saveToK3QcBill.getBillno()+"\"," +											//单据编号
						"\"FEntity\": [{" +
						"\"FEntryID\": \""+saveToK3QcBill.getFentityid()+"\"," +										//明细Id
						"\"F_YF_SpotCheckNum\": \""+saveToK3QcBill.getSampleQty()+"\"," +								//抽检数量
						"\"F_YF_SamplingFailed\": \""+saveToK3QcBill.getSampleDisqualifiedQty()+"\"," +					//抽检不合格数
//						"\"F_YF_NoQualifiedQty\": \""+saveToK3QcBill.getSampleDisqualifiedQty()+"\"," +					//不合格数（不可收料）
						"\"F_YF_NoQualifiedQty\": \""+saveToK3QcBill.getQcDisqualifiedQty()+"\"," +						//不合格数
//						"\"F_YF_QualifiedQty\": \""+saveToK3QcBill.getSampleQualifiedQty()+"\"," +						//合格数（可收料）
						"\"F_YF_QualifiedQty\": \""+saveToK3QcBill.getQcQualifiedQty()+"\"," +							//合格数（可收料）
						"\"F_YF_BadRatio\": \""+saveToK3QcBill.getBadRatio()+"\"," +									//抽检不良率
						"\"F_YF_ReExamination\": \""+saveToK3QcBill.getReexaminationNum()+"\"," +						//复检可接受数
//						"\"F_YF_AvgBadRatio\": \""+saveToK3QcBill.getAvgBadRatio()+"\"," +								//平均不良率
						"\"F_YF_StructuralDisq\": \""+saveToK3QcBill.getStructuralDisqualifiedQty()+"\"," +  			//结构不合格数
						"\"F_YF_UnqualifiedAppe\": \""+saveToK3QcBill.getAppearanceDisqualifiedQty()+"\"," +			//外观不合格数
						"\"F_YF_UnqualifiedPacka\": \""+saveToK3QcBill.getPackageDisqualifiedQty()+"\"," +				//包装不合格数
                        "\"F_YF_SYSLTZS\": \""+sysltzs+"\"," +			                								//剩余收料通知数
						"\"F_YF_AppearanceInspection\": \""+saveToK3QcBill.getAppearanceTest()+"\"," +					//外观检验
						"\"F_YF_StructuralInspection\": \""+saveToK3QcBill.getStructuralTest()+"\"," +					//结构检验
						"\"F_YF_PackagInspection\": \""+saveToK3QcBill.getPackTest()+"\"," +							//包装检验
						"\"F_YF_RectificatType\": \""+saveToK3QcBill.getRectifyType()+"\"," +							//整改类型
						"\"F_YF_Remark\": \""+saveToK3QcBill.getRemarks()+"\"," +										//备注
						"\"F_YF_QualityCheckComDate\": \""+format.format(saveToK3QcBill.getSubmitTime())+"\"," + 		//质检完成时间
						"\"F_YF_SamplingRate\":\""+(1 - saveToK3QcBill.getBadRatio())+"\","+ 								//抽检合格率
						"\"F_YF_PictureUrl1\": \""+pictureUrl1+"\"," +
						"\"F_YF_PictureUrl2\": \""+pictureUrl2+"\"," +
						"\"F_YF_PictureUrl3\": \""+pictureUrl3+"\"," +
						"\"F_YF_PictureUrl4\": \""+pictureUrl4+"\"," +
						"\"F_YF_PictureUrl5\": \""+pictureUrl5+"\"," +
						"\"F_YF_PictureUrl6\": \""+pictureUrl6+"\", }]} }";
				StringBuffer jsonRst = InvokeHelper.Save(YF_PUR_QualityCheck, saveParam,POST_K3ClOUDRLS);
				com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) ((com.alibaba.fastjson.JSONObject) ((com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(String.valueOf(jsonRst))).get("Result")).get("ResponseStatus");
				boolean isSuceess= (boolean) json.get("IsSuccess");
				if (isSuceess){
					fentryIds.add(saveToK3QcBill.getFentityid());
				}else{
					if (!json.toString().contains("您要读取的数据在系统中不存在")) {
						fgcLogService.saveData("调用K3质检单保存接口,明细Id"+saveToK3QcBill.getFentityid(), json.toString(), saveToK3QcBill.getFentityid(), null);
					}
					log.info("SaveQcBillToK3：明细Id"+saveToK3QcBill.getFentityid()+",调用K3质检单保存接口失败!"+json);
				}
			}catch (Exception e){
				 log.error("SaveQcBillToK3：明细Id" + saveToK3QcBill.getFentityid() + ",保存质检单到K3Cloud", e);
			}
		}
		if (fentryIds != null && fentryIds.size() > 0){
			for (String fentryId:fentryIds) {
				QualityCheckDetails qualityCheckDetails = this.qualityCheckDetailsDao.selectByEntityId(fentryId);
				if (qualityCheckDetails == null){
					continue;
				}
				qualityCheckDetails.setReverseWrite("1");
				qualityCheckDetailsDao.update(qualityCheckDetails);
			}
			log.info("SaveQcBillToK3：更新质检单明细已反写K3成功!");
		}
	}


	/**
	 * @desc 审核K3质检单
	 * @author AlbertFeng
	 * @date 2018-08-31 00:27
	 * @return void
	 */
	@Transactional()
	public void AuditK3QcBill(){
		log.info("AuditK3QcBill：成功调用审核K3质检单服务!");
		List<SyncQcBillToK3>  auditK3QcBills =  qualityCheckDao.findAudioK3QcBill();
		if (auditK3QcBills == null || auditK3QcBills.size() <= 0){
			log.info("AuditK3QcBill：未找到需要审核的质检单!");
			return;
		}
		//连接K3Cloud服务器
		boolean isLogin = k3connection.getConnection();
		if (!isLogin){
			log.info("AuditK3QcBill：连接K3Cloud服务器失败!");
			return;
		}
		Set<String> setFIds = new HashSet<>();
		for (SyncQcBillToK3 saveToK3QcBill:auditK3QcBills) {
			setFIds.add(saveToK3QcBill.getFid());
		}
		if (setFIds == null || setFIds.size() <= 0){
			log.info("AuditK3QcBill：获取质检单fid集合失败!");
			return;
		}
		//删除setFIds不满足条件数据
		for (Iterator<String> it = setFIds.iterator(); it.hasNext();) {
			//2次使用了Iterator.next() 方法，会导致边界溢出，java.util.NoSuchElementException
			String fid = it.next();
			try {
				List<SyncQcBillToK3> syncQcBillToK3s = auditK3QcBills.stream().filter(s -> s.getFid().equals(fid)).collect(Collectors.toList());
				if (syncQcBillToK3s == null || syncQcBillToK3s.size() <= 0) {
					continue;
				}
				int writeK3QcBillCount = syncQcBillToK3s.stream().filter(s -> "1".equals(s.getReverseWrite())).collect(Collectors.toList()).size();
				if (writeK3QcBillCount != syncQcBillToK3s.size()) {
					it.remove();
				}
			}catch (Exception ex){
				fgcLogService.saveData("K3质检单审核异常", ex.toString(), null, fid);
				log.error("AuditK3QcBill：检验单据集合中是否有满足审核条件的单据，在单据Id["+fid+"]处校验失败!",ex);
			}
		}
		if (setFIds == null ||setFIds.size() <= 0){
			log.info("AuditK3QcBill：单据集合中没有满足审核条件的单据!");
			return;
		}
		boolean isSuceess = false;
		List<String> suceessIds = new ArrayList<String>();
		for (String setFId : setFIds) {
			try {
				String audioParam = "{\"CreateOrgId\":\"0\",\"Numbers\":[],\"Ids\":\""+setFId+"\"}";
				StringBuffer jsonRst = InvokeHelper.Audit(YF_PUR_QualityCheck, audioParam,POST_K3ClOUDRLS);
				com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) ((com.alibaba.fastjson.JSONObject) ((com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(String.valueOf(jsonRst))).get("Result")).get("ResponseStatus");
				isSuceess = (boolean) json.get("IsSuccess");
				if (isSuceess){
					suceessIds.add(setFId);
				}else {
					if (!json.toString().contains("数据已经审核")) {
						fgcLogService.saveData("Ids["+setFId+"],调用K3质检单审核接口失败", json.toString(), null, setFId);
					}
					log.info("AuditK3QcBill：Ids["+setFId+"],调用K3质检单审核接口失败,失败信息："+json);
				}
			}catch (Exception e){
				fgcLogService.saveData("K3质检单审核异常", e.toString(), null, setFId);
				log.error("AuditK3QcBill：Ids["+setFId+"],调用K3质检单审核接口失败",e);
			}

		}
		if (suceessIds != null && suceessIds.size() > 0){
			for (String suceessId:suceessIds) {
				QualityCheck qualityCheck = this.get(suceessId);
				if (qualityCheck == null){
					continue;
				}
				qualityCheck.setDocumentStatus("已反写K3");
				qualityCheck.setIsNewRecord(false);
				qualityCheck.setModifierName("审核质检单");
				qualityCheck.setModifyTime(new Date());
				super.save(qualityCheck);
				log.info("AuditK3QcBill：质检单"+suceessId+"更新单据状态为已反写K3");
			}
		}
	}

	@Transactional(readOnly = true)
	public List<QualityCheck> selectByFid(List<String> billNoList) {
		return qualityCheckDao.selectByFid(billNoList);
	}

	/**
	 * 删除质检单
	 * @param billNoList
	 */
	@Transactional(readOnly = false)
	public void deleteDb(List<String> billNoList) {
		qualityCheckDao.deleteDb(billNoList);
		qualityCheckDetailsDao.deleteDb(billNoList);
	}
}