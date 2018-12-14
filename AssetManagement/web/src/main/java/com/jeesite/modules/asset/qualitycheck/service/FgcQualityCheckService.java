/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.qualitycheck.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeesite.modules.asset.aliyunimage.web.AmAliyunImageController;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.util.service.AmUtilService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheck;
import com.jeesite.modules.asset.qualitycheck.dao.FgcQualityCheckDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheckDetails;
import com.jeesite.modules.asset.qualitycheck.dao.FgcQualityCheckDetailsDao;

/**
 * 质检单Service
 * @author Albert
 * @version 2018-07-25
 */
@Service
@Transactional(readOnly=true)
public class FgcQualityCheckService extends CrudService<FgcQualityCheckDao, FgcQualityCheck> {
	private static Logger log = LoggerFactory.getLogger(AmAliyunImageController.class);

	@Autowired
	private FgcQualityCheckDetailsDao fgcQualityCheckDetailsDao;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private FgcQualityCheckDao fgcQualityCheckDao;
	@Autowired
	private K3connection k3connection;
	
	/**
	 * 获取单条数据
	 * @param fgcQualityCheck
	 * @return
	 */
	@Override
	public FgcQualityCheck get(FgcQualityCheck fgcQualityCheck) {
		FgcQualityCheck entity = super.get(fgcQualityCheck);
		if (entity != null){
			FgcQualityCheckDetails fgcQualityCheckDetails = new FgcQualityCheckDetails(entity);
			fgcQualityCheckDetails.setStatus(FgcQualityCheckDetails.STATUS_NORMAL);
			entity.setFgcQualityCheckDetailsList(fgcQualityCheckDetailsDao.findList(fgcQualityCheckDetails));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param fgcQualityCheck
	 * @return
	 */
	@Override
	public Page<FgcQualityCheck> findPage(Page<FgcQualityCheck> page, FgcQualityCheck fgcQualityCheck) {
		page = super.findPage(page, fgcQualityCheck);
		for (int i = 0; i < page.getList().size(); i++) {
			// 预约时间类型
			String reservationTimeType = amUtilService.findDictLabel(page.getList().get(i).getReservationTimeType(),"fgc_reservation_time_type");
			page.getList().get(i).setReservationTimeType(reservationTimeType);
		}
		return super.findPage(page, fgcQualityCheck);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param fgcQualityCheck
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(FgcQualityCheck fgcQualityCheck) {
		super.save(fgcQualityCheck);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(fgcQualityCheck.getId(), "fgcQualityCheck_image");
		// 保存 FgcQualityCheck子表
		for (FgcQualityCheckDetails fgcQualityCheckDetails : fgcQualityCheck.getFgcQualityCheckDetailsList()){
			if (!FgcQualityCheckDetails.STATUS_DELETE.equals(fgcQualityCheckDetails.getStatus())){
				fgcQualityCheckDetails.setQualityId(fgcQualityCheck);
				if (fgcQualityCheckDetails.getIsNewRecord()){
					fgcQualityCheckDetails.preInsert();
					fgcQualityCheckDetailsDao.insert(fgcQualityCheckDetails);
				}else{
					fgcQualityCheckDetails.preUpdate();
					fgcQualityCheckDetailsDao.update(fgcQualityCheckDetails);
				}
			}else{
				fgcQualityCheckDetailsDao.delete(fgcQualityCheckDetails);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param fgcQualityCheck
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(FgcQualityCheck fgcQualityCheck) {
		super.updateStatus(fgcQualityCheck);
	}
	
	/**
	 * 删除数据
	 * @param fgcQualityCheck
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(FgcQualityCheck fgcQualityCheck) {
		super.delete(fgcQualityCheck);
		FgcQualityCheckDetails fgcQualityCheckDetails = new FgcQualityCheckDetails();
//		fgcQualityCheckDetails.setBillno(fgcQualityCheck);
		fgcQualityCheckDetails.setQualityId(fgcQualityCheck);
		fgcQualityCheckDetailsDao.delete(fgcQualityCheckDetails);
	}

	/**
	 * 根据id查询明细表信息
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public FgcQualityCheckDetails selectById (String id) {
		return fgcQualityCheckDetailsDao.selectById(id);
	}

	public List<FgcQualityCheckDetails> getDetail(FgcQualityCheck fgcQualityCheck) {

		FgcQualityCheckDetails fgcQualityCheckDetails = new FgcQualityCheckDetails();
		fgcQualityCheckDetails.setQualityId(fgcQualityCheck);
		return fgcQualityCheckDetailsDao.findList(fgcQualityCheckDetails);
	}
	/**
	 * 根据质检单.fid、更新预约日期、预约时间类型、填单状态
	 * @param fgcQualityCheck
	 */
	@Transactional(readOnly = false)
	public Map saveData(FgcQualityCheck fgcQualityCheck) {
		Map<String, Object> map = new HashMap<>();
		try {
			super.save(fgcQualityCheck);
		}catch (Exception e) {
			map.put("code", 900);
			map.put("msg", "更新失败");
			return map;
		}
		map.put("code", 200);
		map.put("msg", "更新成功");
		return map;
	}

	/**
	 * 根据明细编码更新【抽检数量、结构检验、外观检验、包装检验、整改状态、不合格数、不良比例、整改类型、合格类型】
	 * @param fgcQualityCheckDetails
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveDetail (FgcQualityCheckDetails fgcQualityCheckDetails) {
		fgcQualityCheckDetails.preUpdate();
		fgcQualityCheckDetailsDao.update(fgcQualityCheckDetails);
	}

	@Transactional(readOnly = true)
	public FgcQualityCheck getByBillNo(String billNo) {
		return fgcQualityCheckDao.getByBillNo(billNo);
	}


	@Transactional(readOnly = true)
	public List<FgcQualityCheckDetails> selectByQualityId(String qualityId){
		return fgcQualityCheckDetailsDao.selectByQualityId(qualityId);
	}

	/**
	 * 获取质检单详情
	 * @param fgcQualityCheck
	 * @return
	 */
	@Transactional
	public FgcQualityCheck getData(FgcQualityCheck fgcQualityCheck) {
		FgcQualityCheck entity = super.get(fgcQualityCheck);
		if (entity != null) {
			entity.setFgcQualityCheckDetailsList(this.selectByQualityId(fgcQualityCheck.getId()));
		}
		return entity;
	}

	@Value("${POST_K3ClOUDRL}")
	String POST_K3ClOUDRLS;

	@Transactional(readOnly = true)
	public void SyncK3QualityCheck(){
		String querySql = "";
		try {
			//连接K3Cloud服务器
			boolean isLogin = k3connection.getConnection();
			if (!isLogin){
				log.info("SyncK3QualityCheck连接K3Cloud服务器失败!");
				return;
			}
			//获取表中最大时间
			String maxQualityTime = null;//this.fgcQualityCheckDao.getMaxQualityTime();
			//默认开始同步数据时间2018-08-01 00:00:00
			if (maxQualityTime == null || maxQualityTime.isEmpty())
				maxQualityTime = "2018-08-01 00:00:00";
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowDate = formatter.format(currentTime);
			//质检单主表数据
			querySql = "/*dialect*/\nSELECT quality.FID as id,FBILLNO as billno,FCREATEDATE as qualityTime,'初始' as documentStatus,F_YF_RESERVATIONDATETYPE as reservationTimeType,F_YF_RESERVATIONDATE as reservationTime,\n" +
					"inspector.FNAME as qualityInspectorName,qcGroup.FNUMBER as qcGroupName,buyer.FNAME as purchaserName,\n" +
					"buyGroup.FNUMBER as purchasingGroupName,supplier.FNAME as supplierName,'K3接口' as creatorName\n" +
					"FROM YF_T_PUR_QualityCheck as quality\n" +
					"LEFT JOIN V_BD_INSPECTOR_L as inspector on inspector.fid = quality.F_YF_QUALITYUSERID --质检员\n" +
					"LEFT JOIN T_BD_OPERATORGROUPENTRY as qcGroup  on qcGroup.FENTRYID = quality.F_YF_QUALITYGROUP --质检组\n" +
					"LEFT JOIN V_BD_BUYER_L as buyer on buyer.fid = quality.FPURCHASERID  --采购员\n" +
					"LEFT JOIN T_BD_OPERATORGROUPENTRY as buyGroup  on buyGroup.FENTRYID = quality.FPURCHASERGROUPID --采购组\n" +
					"LEFT JOIN T_BD_SUPPLIER_L as supplier on supplier.FSUPPLIERID = quality.F_YF_SUPPLIERID -- 供应商 \n" +
					"WHERE FDOCUMENTSTATUS = 'C' and FCREATEDATE >= '"+maxQualityTime+"' and FCREATEDATE <= '" + nowDate+"'";
			JSONArray jsonArrayQualityCheck = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
			if (jsonArrayQualityCheck == null || jsonArrayQualityCheck.toArray().length <= 0){
				log.info("SyncK3QualityCheck未查询到质检单单据头数据!");
				return;
			}
			//质检单明细数据
			querySql = "/*dialect*/\nSELECT qc.FID as qualityId,qcDetails.FEntryID as id,F_YF_PURCHASEBILLNO as sourceBillno,F_YF_QUALITYQTY as qcQty,\n" +
					"material.FNUMBER as materialCode,materialName.FNAME as materialName  FROM YF_T_PUR_QualityCheck as qc\n" +
					"left join YF_T_PUR_QualityCheckEntry as qcDetails on qc.FID = qcDetails.FID\n" +
					"left join T_BD_MATERIAL as material on material.FMATERIALID = qcDetails.F_YF_MATERIALID \n" +
					"left join T_BD_MATERIAL_L as materialName on materialName.FMATERIALID = material.FMATERIALID\n" +
					"WHERE qc.FDOCUMENTSTATUS = 'C' and qc.FCREATEDATE >= '"+maxQualityTime+"' and qc.FCREATEDATE <= '" + nowDate+"'";
			JSONArray jsonArrayQcDetails = InvokeHelper.Execute("QueryBySql",querySql,POST_K3ClOUDRLS);
			if (jsonArrayQcDetails == null || jsonArrayQcDetails.toArray().length <= 0){
				log.info("SyncK3QualityCheck未查询到质检单明细数据!");
				return;
			}

			List<FgcQualityCheck> qualityCheckList = (List<FgcQualityCheck>) JSONArray.toCollection(jsonArrayQualityCheck);
			List<FgcQualityCheckDetails> qualityCheckDetailsList = (List<FgcQualityCheckDetails>) JSONArray.toCollection(jsonArrayQcDetails);
			for (FgcQualityCheck qc :qualityCheckList) {
				FgcQualityCheck qualityCheck = this.get(qc.getId());
				if (qualityCheck != null){
					qualityCheck.setIsNewRecord(false);
				}else{
					qualityCheck.setCreateTime(new Date());
					qualityCheck.setIsNewRecord(true);
				}
				super.save(qc);
			}
			for (FgcQualityCheckDetails details :qualityCheckDetailsList) {
				FgcQualityCheckDetails qualityCheckDetails = this.fgcQualityCheckDetailsDao.selectById(details.getId());
				if (qualityCheckDetails != null){
					this.fgcQualityCheckDetailsDao.update(details);
				}else{
					this.fgcQualityCheckDetailsDao.insert(details);
				}
			}
			log.info("SyncK3QualityCheck质检单数据同步成功!");
		}catch (Exception ex){
			log.error("SyncK3QualityCheck",ex);
		}
	}
}