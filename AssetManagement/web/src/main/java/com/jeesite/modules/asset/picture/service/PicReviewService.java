/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.picture.service;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.jeesite.common.config.Global;
import com.jeesite.modules.asset.file.dao.AmFileUploadDao;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import com.jeesite.modules.asset.file.service.AmFileUploadService;
import com.jeesite.modules.asset.supplier.service.SupSupplierService;
import com.jeesite.modules.asset.util.TimeUtils;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.file.entity.FileEntity;
import com.jeesite.modules.file.entity.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.picture.entity.PicReview;
import com.jeesite.modules.asset.picture.dao.PicReviewDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * 图片审核单Service
 * @author Scarllet
 * @version 2018-05-31
 */
@Service
@Transactional(readOnly=true)
public class PicReviewService extends CrudService<PicReviewDao, PicReview> {
	@Autowired
	private PicReviewDao picReviewDao;
	@Autowired
	private AmFileUploadService uploadService;
	@Autowired
	private AmFileUploadDao amFileUploadDao;
    @Autowired
	AmUtilService amUtilService;
	@Value("${ENDPOINT}")
	private String ENDPOINT;
	@Value("${ACCESS_KEY_ID}")
	private String ACCESS_KEY_ID; //密钥Id
	@Value("${ACCESS_KEY_SECRET}")
	private String ACCESS_KEY_SECRET;
	@Value("${BUCKET}")
	private String BUCKET;
	private String bizType="picReview_image";
	/**
	 * 获取单条数据
	 * @param picReview
	 * @return
	 */
	@Override
	public PicReview get(PicReview picReview) {
		return super.get(picReview);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param picReview
	 * @return
	 */
	@Override
	public Page<PicReview> findPage(Page<PicReview> page, PicReview picReview) {
		return super.findPage(page, picReview);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param picReview
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(PicReview picReview) {
		super.save(picReview);
	}
	
	/**
	 * 更新状态
	 * @param picReview
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(PicReview picReview) {
		super.updateStatus(picReview);
	}
	
	/**
	 * 删除数据
	 * @param picReview
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(PicReview picReview) {
		super.delete(picReview);
	}

	//从数据库删除图片审核单记录
	@Transactional(readOnly=false)
	public void deleteDB(String reviewCode){
		picReviewDao.deleteDB(reviewCode);
	}
	public List<PicReview> findPath(String bizKey){
		return picReviewDao.findPath(bizKey);
	}

	/**
	 * 2018-08-22
	 * 删除单据并删除阿里云图片
	 * @param picReview
	 * @param reviewCode
	 * @return
	 */
	@Transactional(readOnly=false)
	public String deleteData(PicReview picReview,String reviewCode) {
		    String message = "";
			String[] str = reviewCode.split(",");
		    //获取图片对应的阿里云keys
		    List<String> keys=new ArrayList<String>();
			for (int i = 0; i < str.length; i++) {
				picReview.setReviewCode(str[i]);
				picReview = this.get(picReview);
				// 已审核的单据不能被删除
				if ("1".equals(picReview.getReviewStatus())) {
					message = "删除成功！已审核的单据未被删除!";
					continue;
				}
				List<AmFileUpload> uploadList=uploadService.getImg(str[i],bizType);
					if(null!=uploadList &&uploadList.size()>0){
						for(int j=0;j<uploadList.size();j++){
							AmFileUpload amFileUpload=uploadList.get(j);
							keys.add(amFileUpload.getFilePath());
							amFileUploadDao.deleteDb(amFileUpload.getId());
						}
				}
				this.deleteDB(str[i]);
				/*if(null!=keys &&keys.size()>0) {
					OSSClient ossClient = new OSSClient("https://" + ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
					// 执行删除阿里云文件
					ossClient.deleteObjects(new DeleteObjectsRequest(BUCKET).withKeys(keys));
				}*/
			}
			//删除阿里云图片
		    amUtilService.deletePicAli(keys);
			return message;
	}
}