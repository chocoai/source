/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.track.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.track.dao.AmTrackDao;
import com.jeesite.modules.asset.track.dao.AmTrackDetailDao;
import com.jeesite.modules.asset.track.dao.AmTrackSpeedDao;
import com.jeesite.modules.asset.track.dao.AmTrackTransferDao;
import com.jeesite.modules.asset.track.entity.AmTrack;
import com.jeesite.modules.asset.track.entity.AmTrackDetail;
import com.jeesite.modules.asset.track.entity.AmTrackSpeed;
import com.jeesite.modules.asset.track.entity.AmTrackTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 退货跟踪单Service
 * @author czy
 * @version 2018-06-21
 */
@Service
@Transactional(readOnly=true)
public class AmTrackService extends CrudService<AmTrackDao, AmTrack> {
	
	@Autowired
	private AmTrackDetailDao amTrackDetailDao;
	
	@Autowired
	private AmTrackTransferDao amTrackTransferDao;
	
	@Autowired
	private AmTrackSpeedDao amTrackSpeedDao;
	
	/**
	 * 获取单条数据
	 * @param amTrack
	 * @return
	 */
	@Override
	public AmTrack get(AmTrack amTrack) {
		AmTrack entity = super.get(amTrack);
		if (entity != null){
			AmTrackDetail amTrackDetail = new AmTrackDetail(entity);
			amTrackDetail.setStatus(AmTrackDetail.STATUS_NORMAL);
			entity.setAmTrackDetailList(amTrackDetailDao.findList(amTrackDetail));
			AmTrackTransfer amTrackTransfer = new AmTrackTransfer(entity);
			amTrackTransfer.setStatus(AmTrackTransfer.STATUS_NORMAL);
			entity.setAmTrackTransferList(amTrackTransferDao.findList(amTrackTransfer));
			AmTrackSpeed amTrackSpeed = new AmTrackSpeed(entity);
			amTrackSpeed.setStatus(AmTrackSpeed.STATUS_NORMAL);
			entity.setAmTrackSpeedList(amTrackSpeedDao.findList(amTrackSpeed));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param amTrack
	 * @return
	 */
	@Override
	public Page<AmTrack> findPage(Page<AmTrack> page, AmTrack amTrack) {
		return super.findPage(page, amTrack);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param amTrack
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AmTrack amTrack) {
		super.save(amTrack);
		// 保存 AmTrack子表
//		for (AmTrackDetail amTrackDetail : amTrack.getAmTrackDetailList()){
//			if (!AmTrackDetail.STATUS_DELETE.equals(amTrackDetail.getStatus())){
//				amTrackDetail.setDocumentCode(amTrack);
//				if (amTrackDetail.getIsNewRecord()){
//					amTrackDetail.preInsert();
//					amTrackDetailDao.insert(amTrackDetail);
//				}else{
//					amTrackDetail.preUpdate();
//					amTrackDetailDao.update(amTrackDetail);
//				}
//			}else{
//				amTrackDetailDao.delete(amTrackDetail);
//			}
//		}
//		// 保存 AmTrack子表
//		for (AmTrackTransfer amTrackTransfer : amTrack.getAmTrackTransferList()){
//			if (!AmTrackTransfer.STATUS_DELETE.equals(amTrackTransfer.getStatus())){
//				amTrackTransfer.setDocumentCode(amTrack);
//				if (amTrackTransfer.getIsNewRecord()){
//					amTrackTransfer.preInsert();
//					amTrackTransferDao.insert(amTrackTransfer);
//				}else{
//					amTrackTransfer.preUpdate();
//					amTrackTransferDao.update(amTrackTransfer);
//				}
//			}else{
//				amTrackTransferDao.delete(amTrackTransfer);
//			}
//		}
		// 保存 AmTrack子表
		for (AmTrackSpeed amTrackSpeed : amTrack.getAmTrackSpeedList()){
			if (!AmTrackSpeed.STATUS_DELETE.equals(amTrackSpeed.getStatus())){
				amTrackSpeed.setDocumentCode(amTrack);
				if (amTrackSpeed.getIsNewRecord()){
					amTrackSpeed.preInsert();
					amTrackSpeedDao.insert(amTrackSpeed);
				}else{
					amTrackSpeed.preUpdate();
					amTrackSpeedDao.update(amTrackSpeed);
				}
			}else{
				amTrackSpeedDao.delete(amTrackSpeed);
			}
		}
	}

	/**
	 * 只更新明细表
	 * @param amTrackDetail
	 */
	@Transactional(readOnly=false)
	public void saveDetail(AmTrackDetail amTrackDetail) {
		if (amTrackDetail.getIsNewRecord()){
			amTrackDetail.preInsert();
			amTrackDetailDao.insert(amTrackDetail);
		}else{
			amTrackDetail.preUpdate();
			amTrackDetailDao.update(amTrackDetail);
		}
	}

	/**
	 * 只更新调货信息表
	 * @param amTrackTransfer
	 */
	@Transactional(readOnly=false)
	public void saveTransfer(AmTrackTransfer amTrackTransfer) {
		if (amTrackTransfer.getIsNewRecord()){
			amTrackTransfer.preInsert();
			amTrackTransferDao.insert(amTrackTransfer);
		}else{
			amTrackTransfer.preUpdate();
			amTrackTransferDao.update(amTrackTransfer);
		}
	}
	/**
	 * 更新状态
	 * @param amTrack
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AmTrack amTrack) {
		super.updateStatus(amTrack);
	}
	
	/**
	 * 删除数据
	 * @param amTrack
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AmTrack amTrack) {
		super.delete(amTrack);
		AmTrackDetail amTrackDetail = new AmTrackDetail();
		amTrackDetail.setDocumentCode(amTrack);
		amTrackDetailDao.delete(amTrackDetail);
		AmTrackTransfer amTrackTransfer = new AmTrackTransfer();
		amTrackTransfer.setDocumentCode(amTrack);
		amTrackTransferDao.delete(amTrackTransfer);
		AmTrackSpeed amTrackSpeed = new AmTrackSpeed();
		amTrackSpeed.setDocumentCode(amTrack);
		amTrackSpeedDao.delete(amTrackSpeed);
	}

	/**
	 * 根据entryID 和物料code查询明细
	 * @param entryId
	 * @param consumablesCode
	 * @return
	 */
	@Transactional(readOnly=false)
	public List<AmTrackDetail> getDetail(String entryId,String consumablesCode) {
		return amTrackDetailDao.getDetail(entryId,consumablesCode);
	}

	public List<AmTrackTransfer> getTransfer (String entryId,String consumablesCode) {
		return amTrackTransferDao.getTransfer(entryId, consumablesCode);
	}
	/**
	 * 只保存主表
	 * @param amTrack
	 */
	@Transactional(readOnly=false)
	public void saveData(AmTrack amTrack) {
		super.save(amTrack);
	}
}