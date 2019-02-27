/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.distribution.material.service;

import java.util.List;

import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.distribution.material.entity.DistrMaterial;
import com.jeesite.modules.distribution.material.dao.DistrMaterialDao;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.jeesite.modules.distribution.material.entity.DistrMaterialDetail;
import com.jeesite.modules.distribution.material.dao.DistrMaterialDetailDao;

/**
 * 分销素材Service
 * @author len
 * @version 2019-01-05
 */
@Service
@Transactional(readOnly=true)
public class DistrMaterialService extends CrudService<DistrMaterialDao, DistrMaterial> {

	@Autowired
	private DistrMaterialDao distrMaterialDao;

	@Autowired
	private DistrMaterialDetailDao distrMaterialDetailDao;
	
	/**
	 * 获取单条数据
	 * @param distrMaterial
	 * @return
	 */
	@Override
	public DistrMaterial get(DistrMaterial distrMaterial) {
		DistrMaterial entity = super.get(distrMaterial);
		if (entity != null){
			DistrMaterialDetail distrMaterialDetail = new DistrMaterialDetail(entity);
			distrMaterialDetail.setStatus(DistrMaterialDetail.STATUS_NORMAL);
			entity.setDistrMaterialDetailList(distrMaterialDetailDao.findList(distrMaterialDetail));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param distrMaterial
	 * @return
	 */
	@Override
	public Page<DistrMaterial> findPage(Page<DistrMaterial> page, DistrMaterial distrMaterial) {
		return super.findPage(page, distrMaterial);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param distrMaterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(DistrMaterial distrMaterial) {
		super.save(distrMaterial);
		// 保存上传图片
//		FileUploadUtils.saveFileUpload(distrMaterial.getId(), "distrMaterial_image");
		// 保存 DistrMaterial子表
		for (DistrMaterialDetail distrMaterialDetail : distrMaterial.getDistrMaterialDetailList()){
			if (!DistrMaterialDetail.STATUS_DELETE.equals(distrMaterialDetail.getStatus())){
				distrMaterialDetail.setMaterialCode(distrMaterial);
				if (distrMaterialDetail.getIsNewRecord()){
					distrMaterialDetail.preInsert();
					distrMaterialDetailDao.insert(distrMaterialDetail);
				}else{
					distrMaterialDetail.preUpdate();
					distrMaterialDetailDao.update(distrMaterialDetail);
				}
			}else{
				distrMaterialDetailDao.delete(distrMaterialDetail);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param distrMaterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(DistrMaterial distrMaterial) {
		super.updateStatus(distrMaterial);
	}
	
	/**
	 * 删除数据
	 * @param distrMaterial
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(DistrMaterial distrMaterial) {
		super.delete(distrMaterial);
		distrMaterialDetailDao.deleteDb(distrMaterial.getMaterialCode());
	}

	/**
	 * 查询详情图
	 * @param materialCode
	 * @return
	 */
	@Transactional(readOnly = false)
	public DistrMaterial selectByMaterial(String materialCode) {
		return distrMaterialDao.selectByMaterial(materialCode);
	}

	public List<TbProduct> selectByNumIid(List<String> numIidList) {
		return distrMaterialDao.selectByNumIid(numIidList);
	}

	public List<TbSku> selectSkuByNumIid(List<String> numIidList) {
		return distrMaterialDao.selectSkuByNumIid(numIidList);
	}

	/**
	 * 查询最后一张图片作为主图
	 * @param numIidList
	 * @return
	 */
	public List<TbItemImgs> selectImgByNumIid(List<String> numIidList) {
		return distrMaterialDao.selectImgByNumIid(numIidList);
	}
}