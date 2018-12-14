/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.picture.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.consumables.entity.AmOutStorageDetails;
import com.jeesite.modules.asset.picture.entity.PicReview;
import com.jeesite.modules.file.entity.FileUpload;

import java.util.HashMap;
import java.util.List;

/**
 * 图片审核单DAO接口
 * @author Scarllet
 * @version 2018-05-31
 */
@MyBatisDao
public interface PicReviewDao extends CrudDao<PicReview> {
    List<FileUpload> findPictureId(String bizKey);
    int updateFileUploadRemarks(String bizKey,String StringList,String remarks);
    void deleteDB(String reviewCode);
    List<PicReview> findPathByStatus(String bizKey);
    List<PicReview> findPath(String bizKey);
    void deleteFileUploadRecord(String bizKey);
    void deleteByStatus(String bizKey);
    void deleteFileEntityByFileId(String fileId);
    void deleteFileUploadByFileId(String fileId);

 }