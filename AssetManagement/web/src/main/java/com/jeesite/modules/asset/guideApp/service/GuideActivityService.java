/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.guideApp.service;


import com.jeesite.modules.asset.guideApp.dao.GuideImgDao;
import com.jeesite.modules.asset.guideApp.entity.GuideImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.guideApp.entity.GuideActivity;
import com.jeesite.modules.asset.guideApp.dao.GuideActivityDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.asset.guideApp.entity.GuideComment;
import com.jeesite.modules.asset.guideApp.dao.GuideCommentDao;
import com.jeesite.modules.asset.guideApp.entity.GuideFaq;
import com.jeesite.modules.asset.guideApp.dao.GuideFaqDao;

import java.util.Date;
import java.util.List;

/**
 * 导购活动表Service
 * @author len
 * @version 2018-12-07
 */
@Service
@Transactional(readOnly=true)
public class GuideActivityService extends CrudService<GuideActivityDao, GuideActivity> {
	@Autowired
	private GuideActivityDao guideActivityDao;

	@Autowired
	private GuideCommentDao guideCommentDao;
	
	@Autowired
	private GuideFaqDao guideFaqDao;

	@Autowired
	private GuideImgService guideImgService;
	/**
	 * 获取单条数据
	 * @param guideActivity
	 * @return
	 */
	@Override
	public GuideActivity get(GuideActivity guideActivity) {
		GuideActivity entity = super.get(guideActivity);
		if (entity != null){
			GuideImg guideImg = guideImgService.get(entity.getActivityCode());
			if (guideImg != null) {
				entity.setBannerImage(guideImg.getBannerUrl());
			}
			GuideComment guideComment = new GuideComment(entity);
			guideComment.setStatus(GuideComment.STATUS_NORMAL);
			entity.setGuideCommentList(guideCommentDao.findList(guideComment));
			GuideFaq guideFaq = new GuideFaq(entity);
			guideFaq.setStatus(GuideFaq.STATUS_NORMAL);
			entity.setGuideFaqList(guideFaqDao.findList(guideFaq));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param guideActivity
	 * @return
	 */
	@Override
	public Page<GuideActivity> findPage(Page<GuideActivity> page, GuideActivity guideActivity) {
		return super.findPage(page, guideActivity);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param guideActivity
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(GuideActivity guideActivity) {
		super.save(guideActivity);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(guideActivity.getId(), "guideActivity_image");
		// 保存 GuideActivity子表
		for (GuideComment guideComment : guideActivity.getGuideCommentList()){
			if (!GuideComment.STATUS_DELETE.equals(guideComment.getStatus())){
				guideComment.setActivityCode(guideActivity);
				if (guideComment.getIsNewRecord()){
					guideComment.preInsert();
					guideCommentDao.insert(guideComment);
				}else{
					guideComment.preUpdate();
					guideCommentDao.update(guideComment);
				}
			}else{
				guideCommentDao.delete(guideComment);
			}
		}
		// 保存 GuideActivity子表
		for (GuideFaq guideFaq : guideActivity.getGuideFaqList()){
			if (!GuideFaq.STATUS_DELETE.equals(guideFaq.getStatus())){
				guideFaq.setActivityCode(guideActivity);
				if (guideFaq.getIsNewRecord()){
					guideFaq.preInsert();
					guideFaqDao.insert(guideFaq);
				}else{
					guideFaq.preUpdate();
					guideFaqDao.update(guideFaq);
				}
			}else{
				guideFaqDao.delete(guideFaq);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param guideActivity
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(GuideActivity guideActivity) {
		super.updateStatus(guideActivity);
	}
	
	/**
	 * 删除数据
	 * @param guideActivity
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(GuideActivity guideActivity) {
		super.delete(guideActivity);
		GuideComment guideComment = new GuideComment();
		guideComment.setActivityCode(guideActivity);
		guideCommentDao.delete(guideComment);
		GuideFaq guideFaq = new GuideFaq();
		guideFaq.setActivityCode(guideActivity);
		guideFaqDao.delete(guideFaq);
	}

	/**
	 * 查询所有状态是有效的活动
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<GuideActivity> selectList() {
		return guideActivityDao.selectList();
	}

	/**
	 * 添加新的提问信息
	 * @param guideComment
	 */
	@Transactional (readOnly = false)
	public void insertComment (GuideComment guideComment) {
		guideCommentDao.insert(guideComment);
	}

	/**
	 * 更新回复内容，回复时间，回复人等
	 * @param guideComment
	 */
	@Transactional (readOnly = false)
	public void updateComment(GuideComment guideComment) {
		guideCommentDao.update(guideComment);
	}

	/**
	 * 根据评论id获取评论信息
	 * @param commentCode
	 * @return
	 */
	public GuideComment getGuideComment(String commentCode) {
		return guideCommentDao.getGuideComment(commentCode);
	}

	/**
	 * 删除评论里的回复信息（回复人，回复，回复时间等）
	 * @param commentCode
	 */
	@Transactional(readOnly = false)
	public void deleteAnswer(String commentCode) {
		guideCommentDao.deleteAnswer(commentCode);
	}

	/**
	 * 根据活动编码和关键字查询QA
	 * @param activityCode
	 * @param keyword
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<GuideFaq> selectFAQ(String activityCode, String keyword) {
		return guideFaqDao.selectFAQ(activityCode, keyword);
	}

	/**
	 * 获取活动表中最新一条的创建时间
	 * @return
	 */
	public Date getNewTime (){
		return guideActivityDao.getNewTime();
	}
}