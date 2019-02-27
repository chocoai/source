/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.service;

import java.util.List;
import java.util.NoSuchElementException;

import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.fz.utils.common.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.wish.entity.WishShortlist;
import com.jeesite.modules.asset.wish.dao.WishShortlistDao;
import com.jeesite.modules.asset.wish.entity.WishVotedDetail;
import com.jeesite.modules.asset.wish.dao.WishVotedDetailDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

/**
 * 入围名单Service
 * @author len
 * @version 2018-11-20
 */
@Service
@Transactional(readOnly=true)
public class WishShortlistService extends CrudService<WishShortlistDao, WishShortlist> {
	
	@Autowired
	private WishVotedDetailDao wishVotedDetailDao;
	@Autowired
	private WishShortlistDao wishShortlistDao;
	@Resource
	private RedisUtil<String, List> redisList;
	/**
	 * 获取单条数据
	 * @param wishShortlist
	 * @return
	 */
	@Override
	public WishShortlist get(WishShortlist wishShortlist) {
		WishShortlist entity = super.get(wishShortlist);
		if (entity != null){
			WishVotedDetail wishVotedDetail = new WishVotedDetail(entity);
			wishVotedDetail.setStatus(WishVotedDetail.STATUS_NORMAL);
			entity.setWishVotedDetailList(wishVotedDetailDao.findList(wishVotedDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param wishShortlist
	 * @return
	 */
	@Override
	public Page<WishShortlist> findPage(Page<WishShortlist> page, WishShortlist wishShortlist) {
		return super.findPage(page, wishShortlist);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param wishShortlist
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(WishShortlist wishShortlist) {
		super.save(wishShortlist);
		// 保存 WishShortlist子表
		for (WishVotedDetail wishVotedDetail : wishShortlist.getWishVotedDetailList()){
			if (!WishVotedDetail.STATUS_DELETE.equals(wishVotedDetail.getStatus())){
				wishVotedDetail.setUserId(wishShortlist);
				if (wishVotedDetail.getIsNewRecord()){
					wishVotedDetail.preInsert();
					wishVotedDetailDao.insert(wishVotedDetail);
				}else{
					wishVotedDetail.preUpdate();
					wishVotedDetailDao.update(wishVotedDetail);
				}
			}else{
				wishVotedDetailDao.delete(wishVotedDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param wishShortlist
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(WishShortlist wishShortlist) {
		super.updateStatus(wishShortlist);
	}
	
	/**
	 * 删除数据
	 * @param wishShortlist
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(WishShortlist wishShortlist) {
		super.delete(wishShortlist);
		WishVotedDetail wishVotedDetail = new WishVotedDetail();
		wishVotedDetail.setUserId(wishShortlist);
		wishVotedDetailDao.delete(wishVotedDetail);
	}

	@Transactional(readOnly = true)
	public List<WishVotedDetail> getCheck (WishVotedDetail wishVotedDetail) {
		return wishVotedDetailDao.findList(wishVotedDetail);
	}

	@Transactional(readOnly=false)
	public String importData(MultipartFile file, Boolean isUpdateSupport) {
		if (file == null){
			throw new ServiceException("请选择导入的数据文件！");
		}
		int successNum = 0; int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		try(ExcelImport ei = new ExcelImport(file, 2, 0)){
			List<WishShortlist> list = ei.getDataList(WishShortlist.class);
			// 获取所有用户
			List<DingUser> dingUserList = redisList.get("dingUser" + Variable.dataBase + Variable.RANDOMID);
			for (WishShortlist wishShortlist : list) {
				try{
					try{
						DingUser dingUser = dingUserList.stream().filter(s ->s.getUserid().equals(wishShortlist.getUserId())).findFirst().get();
						// 导入时获取用户头像
						wishShortlist.setAvatar(dingUser.getAvatar());
						wishShortlist.setPersonalProfile("");
						// 默认状态为否
						wishShortlist.setUserStatus("0");
						wishShortlist.setIsNewRecord(true);
					} catch (NoSuchElementException e) {

					}

				} catch (Exception e) {
					failureNum++;
					String msg = "<br/>" + failureNum  + " 导入失败：";
					if (e instanceof ConstraintViolationException){
						List<String> messageList = ValidatorUtils.extractPropertyAndMessageAsList((ConstraintViolationException)e, ": ");
						for (String message : messageList) {
							msg += message + "; ";
						}
					}else{
						msg += e.getMessage();
					}
					failureMsg.append(msg);
					logger.error(msg, e);
				}
			}
			wishShortlistDao.insertData(list);
		} catch (Exception e) {
			failureMsg.append(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new ServiceException(failureMsg.toString());
		}else{
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	}
	/**
	 * 根据入围名单userId获取入围信息
	 * @param list
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<WishShortlist> selectByUserId(List<String> list) {
		return wishShortlistDao.selectByUserId(list);
	}

	/**
	 * 更新复选投票票数插入明细记录
	 * @param wishShortlistList
	 * @param wishVotedDetailList
	 */
	@Transactional(readOnly = false)
	public void updateShortlist(List<WishShortlist> wishShortlistList, List<WishVotedDetail> wishVotedDetailList){
		wishShortlistDao.updateShortlist(wishShortlistList);
		wishVotedDetailDao.insertBatch(wishVotedDetailList);
	}

	/**
	 * 根据用户Id获取被投票人的信息
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<WishShortlist> getPollResults (String userId) {
		return wishShortlistDao.getPollResults(userId);
	}
}