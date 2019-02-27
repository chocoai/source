/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.file.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.file.entity.AmFileUpload;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 文件上传表DAO接口
 * @author len
 * @version 2018-08-10
 */
@MyBatisDao
public interface AmFileUploadDao extends CrudDao<AmFileUpload> {
	List<AmFileUpload> getImg(String bizKey, String bizType);
	int deleteDb(String id);
	@Update("update js_am_file_upload set pic_status=#{arg1},pic_remarks=#{arg2} where id=#{arg0}")
	int updatePicRemark(String id,String picStatus,String picRemarks);

	List<AmFileUpload> getImage(List<String> appreciation);

	/**
	 * 根据bizType和bizKey获取图片
	 * @param bizKeyList
	 * @param bizType
	 * @return
	 */
	List<AmFileUpload> getImgs(@Param("bizKeyList") List<String> bizKeyList, @Param("bizType") String bizType);
}