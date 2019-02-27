/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.camera.service;

import java.util.List;
import java.util.stream.Collectors;

import com.jeesite.common.collect.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.camera.entity.SvCamera;
import com.jeesite.modules.storevideo.camera.dao.SvCameraDao;
import com.jeesite.modules.storevideo.camera.entity.SvTv;
import com.jeesite.modules.storevideo.camera.dao.SvTvDao;

/**
 * 摄像头Service
 * @author AlbertFeng
 * @version 2019-01-17
 */
@Service
@Transactional(readOnly=true)
public class SvCameraService extends CrudService<SvCameraDao, SvCamera> {
	
	@Autowired
	private SvTvDao svTvDao;
	
	/**
	 * 获取单条数据
	 * @param svCamera
	 * @return
	 */
	@Override
	public SvCamera get(SvCamera svCamera) {
		SvCamera entity = super.get(svCamera);
		if (entity != null){
			SvTv svTv = new SvTv(entity);
			svTv.setStatus(SvTv.STATUS_NORMAL);
			entity.setSvTvList(svTvDao.findList(svTv));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svCamera
	 * @return
	 */
	@Override
	public Page<SvCamera> findPage(Page<SvCamera> page, SvCamera svCamera) {
		return super.findPage(page, svCamera);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svCamera
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvCamera svCamera) {
		super.save(svCamera);
		// 保存 SvCamera子表
		for (SvTv svTv : svCamera.getSvTvList()){
			if (!SvTv.STATUS_DELETE.equals(svTv.getStatus())){
				svTv.setCameraCode(svCamera);
				if (svTv.getIsNewRecord()){
					svTv.preInsert();
					svTvDao.insert(svTv);
				}else{
					svTv.preUpdate();
					svTvDao.update(svTv);
				}
			}else{
				svTvDao.delete(svTv);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param svCamera
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvCamera svCamera) {
		super.updateStatus(svCamera);
	}
	
	/**
	 * 删除数据
	 * @param svCamera
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvCamera svCamera) {
		super.delete(svCamera);
		SvTv svTv = new SvTv();
		svTv.setCameraCode(svCamera);
		svTvDao.delete(svTv);
	}

	/**
	 * 根据mac查找匹配的电视IP
	 * @param cameraMac
	 * @return
	 */
	public List<String> findPushIps(String cameraMac){
		SvCamera svCamera = new SvCamera();
		svCamera.setDeviceMac(cameraMac);


		SvTv svTv = new SvTv(svCamera);
		svTv.setStatus(SvTv.STATUS_NORMAL);

        List<SvTv> list = svTvDao.findList(svTv);

		if(ListUtils.isNotEmpty(list)){
		    List<String> tvList = list.stream().map(a->a.getTvNumber()).collect(Collectors.toList());
		    return tvList;
        }
        return null;
	}
	
}