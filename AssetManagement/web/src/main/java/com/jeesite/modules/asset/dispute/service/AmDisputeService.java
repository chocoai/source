/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.dispute.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.dispute.dao.AmDisputeDao;
import com.jeesite.modules.asset.dispute.dao.AmDisputeDetailDao;
import com.jeesite.modules.asset.dispute.dao.AmDisputeDisposeDao;
import com.jeesite.modules.asset.dispute.entity.AmDispute;
import com.jeesite.modules.asset.dispute.entity.AmDisputeDetail;
import com.jeesite.modules.asset.dispute.entity.AmDisputeDispose;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 物流纠纷表Service
 * @author czy
 * @version 2018-06-09
 */
@Service
@Transactional(readOnly=true)
public class AmDisputeService extends CrudService<AmDisputeDao, AmDispute> {
	
	@Autowired
	private AmDisputeDisposeDao amDisputeDisposeDao;
	
	@Autowired
	private AmDisputeDetailDao amDisputeDetailDao;
	@Autowired
	private AmDisputeDao amDisputeDao;
	/**
	 * 获取单条数据
	 * @param amDispute
	 * @return
	 */
	@Override
	public AmDispute get(AmDispute amDispute) {
		AmDispute entity = super.get(amDispute);
		if (entity != null){
			AmDisputeDispose amDisputeDispose = new AmDisputeDispose(entity);
			amDisputeDispose.setStatus(AmDisputeDispose.STATUS_NORMAL);
			entity.setAmDisputeDisposeList(amDisputeDisposeDao.findList(amDisputeDispose));
			AmDisputeDetail amDisputeDetail = new AmDisputeDetail(entity);
			amDisputeDetail.setStatus(AmDisputeDetail.STATUS_NORMAL);
			entity.setAmDisputeDetailList(amDisputeDetailDao.findList(amDisputeDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amDispute
	 * @return
	 */
	@Override
	public Page<AmDispute> findPage(Page<AmDispute> page, AmDispute amDispute) {
		return super.findPage(page, amDispute);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amDispute
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmDispute amDispute) {
		super.save(amDispute);
		// 保存上传图片
	//	FileUploadUtils.saveFileUpload(amDispute.getId(), "amDispute_image");
		//循环将页面的所有照片保存
//		String bizType = null;
//		for (int i=1;i<7;i++){
//			bizType = "amDispute_image" + i;
//			FileUploadUtils.saveFileUpload(amDispute.getId(), bizType);
//		}
		FileUploadUtils.saveFileUpload(amDispute.getId(), "amDispute_image7");
		// 保存 AmDispute子表
		for (AmDisputeDispose amDisputeDispose : amDispute.getAmDisputeDisposeList()){
			if (!AmDisputeDispose.STATUS_DELETE.equals(amDisputeDispose.getStatus())){
				amDisputeDispose.setDocumentCode(amDispute);
				amDisputeDispose.setDocumentCode(amDispute);
				if (amDisputeDispose.getIsNewRecord()){
					amDisputeDispose.preInsert();
					amDisputeDisposeDao.insert(amDisputeDispose);
				}else{
					amDisputeDispose.preUpdate();
					amDisputeDisposeDao.update(amDisputeDispose);
				}
			}else{
				amDisputeDisposeDao.delete(amDisputeDispose);
			}
		}
		// 保存 AmDispute子表
		for (AmDisputeDetail amDisputeDetail : amDispute.getAmDisputeDetailList()){
			if (!AmDisputeDetail.STATUS_DELETE.equals(amDisputeDetail.getStatus())){
				amDisputeDetail.setDocumentCode(amDispute);
				if (amDisputeDetail.getIsNewRecord()){
					amDisputeDetail.preInsert();
					amDisputeDetailDao.insert(amDisputeDetail);
				}else{
					amDisputeDetail.preUpdate();
					amDisputeDetailDao.update(amDisputeDetail);
				}
			}else{
				amDisputeDetailDao.delete(amDisputeDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param amDispute
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmDispute amDispute) {
		super.updateStatus(amDispute);
	}
	
	/**
	 * 删除数据
	 * @param amDispute
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmDispute amDispute) {
		super.delete(amDispute);
		AmDisputeDispose amDisputeDispose = new AmDisputeDispose();
		amDisputeDispose.setDocumentCode(amDispute);
		amDisputeDispose.setDocumentCode(amDispute);
		amDisputeDisposeDao.delete(amDisputeDispose);
		AmDisputeDetail amDisputeDetail = new AmDisputeDetail();
		amDisputeDetail.setDocumentCode(amDispute);
		amDisputeDetailDao.delete(amDisputeDetail);
	}

	/**
	 * 创建状态下只保存主表数据
	 * @param amDispute
	 */
	@Transactional(readOnly=false)
	public void saveData(AmDispute amDispute) {
		super.save(amDispute);
		FileUploadUtils.saveFileUpload(amDispute.getId(), "amDispute_image7");
	}

	/**
	 * 只保存明细数据
	 * @param amDisputeDetail
	 */
	@Transactional(readOnly=false)
	public void saveDetil(AmDisputeDetail amDisputeDetail) {
		if (amDisputeDetail.getIsNewRecord()) {
			amDisputeDetail.preInsert();
			amDisputeDetailDao.insert(amDisputeDetail);
		} else {
			amDisputeDetail.preUpdate();
			amDisputeDetailDao.update(amDisputeDetail);
		}
	}

	public AmDisputeDetail findDetil(String code, String consumablesCode) {
		return amDisputeDetailDao.findDetil(code, consumablesCode);
	}

	/**
	 * 根据英文名匹配省份
	 * @param empNameEn
	 * @return
	 */
	public List<User> userSelect(String empNameEn, int pageNo) {
		return amDisputeDao.userSelect(empNameEn, pageNo);
	}

	/**
	 * 根据entryID 和物料code查询明细
	 * @param entryId
	 * @param consumablesCode
	 * @return
	 */
	@Transactional(readOnly=false)
	public  List<AmDisputeDetail> getDetail(String entryId, String consumablesCode) {
		return 	amDisputeDetailDao.getDetail(entryId,consumablesCode);
	}
}