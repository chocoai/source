/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.dao.TbLogDao;
import com.jeesite.modules.asset.tianmao.entity.TbLog;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * tb_logService
 * @author jace
 * @version 2018-07-23
 */
@Service
@Transactional(readOnly=true)
public class TbLogService extends CrudService<TbLogDao, TbLog> {
	@Autowired
	private TbSkuService tbSkuService;
	@Autowired
    private TbLogDao tbLogDao;

	private org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TbLogService.class);
	/**
	 * 获取单条数据
	 * @param tbLog
	 * @return
	 */
	@Override
	public TbLog get(TbLog tbLog) {
		return super.get(tbLog);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param tbLog
	 * @return
	 */
	@Override
	public Page<TbLog> findPage(Page<TbLog> page, TbLog tbLog) {
		return super.findPage(page, tbLog);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param tbLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TbLog tbLog) {
		super.save(tbLog);
	}
	
	/**
	 * 更新状态
	 * @param tbLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TbLog tbLog) {
		super.updateStatus(tbLog);
	}
	
	/**
	 * 删除数据
	 * @param tbLog
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TbLog tbLog) {
		super.delete(tbLog);
	}

	/**
	 * 商品日志记录
	 */
	public void insertLog(List<TbSku> tbSkuList, String type){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		Date date = null;
		try {
			 date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = UserUtils.getUser();
		System.out.println("获取用户名："+ user.getUserName());
		//循环遍历skuList并对比sku的字段值
		for(TbSku tbSku: tbSkuList){
			//SKU是否更新
			TbSku tbSku2 = tbSkuService.get(tbSku);
			if(tbSku2!=null && tbSku2.getOuterId()!=null){
				if(!tbSku2.getOuterId().equals(tbSku.getOuterId())){
					LOGGER.info("SKU原值【"+tbSku2.getOuterId()+"】,新值【"+tbSku.getOuterId()+"】");
					String describe = "SKU原值【"+tbSku2.getOuterId()+"】,新值【"+tbSku.getOuterId()+"】";
					TbLog tbLog = new TbLog();
					tbLog.setDescribe(describe);
					tbLog.setSku(tbSku.getOuterId());
					tbLog.setSkuId(tbSku.getSkuId().toString());
					tbLog.setUser(user.getUserName());
					tbLog.setType(type);
					tbLog.setLogTime(date);
					tbLogDao.insertLog(tbLog);
				}
			}
			//标准售价是否更新
			if( tbSku2!=null && !tbSku2.getPrice().equals(tbSku.getPrice())){
				LOGGER.info("标准售价原值【"+tbSku2.getPrice()+"】,新值【"+tbSku.getPrice()+"】");
				String describe = "标准售价原值【"+tbSku2.getPrice()+"】,新值【"+tbSku.getPrice()+"】";
				TbLog tbLog = new TbLog();
				tbLog.setDescribe(describe);
				tbLog.setSku(tbSku.getOuterId());
				tbLog.setSkuId(tbSku.getSkuId().toString());
				tbLog.setUser(user.getUserName());
				tbLog.setType(type);
				tbLog.setLogTime(date);
                tbLogDao.insertLog(tbLog);
			}
			//真实售价是否更新
			if(tbSku2!=null && !tbSku2.getRealPrice().equals(tbSku.getRealPrice())){
				LOGGER.info("真实售价原值【"+tbSku2.getRealPrice()+"】,新值【"+tbSku.getRealPrice()+"】");
				String describe = "真实售价原值【"+tbSku2.getRealPrice()+"】,新值【"+tbSku.getRealPrice()+"】";
				TbLog tbLog = new TbLog();
				tbLog.setDescribe(describe);
				tbLog.setSku(tbSku.getOuterId());
				tbLog.setSkuId(tbSku.getSkuId().toString());
				tbLog.setUser(user.getUserName());
				tbLog.setType(type);
				tbLog.setLogTime(date);
                tbLogDao.insertLog(tbLog);
			}
			//商品规格是否更新
			if (tbSku2!=null && !tbSku2.getPropertiesName().equals(tbSku.getPropertiesName())){
				LOGGER.info("商品规格原值【"+tbSku2.getPropertiesName()+"】,新值【"+tbSku.getPropertiesName()+"】");
				String describe = "商品规格原值【"+tbSku2.getPropertiesName()+"】,新值【"+tbSku.getPropertiesName()+"】";
				TbLog tbLog = new TbLog();
				tbLog.setDescribe(describe);
				tbLog.setSku(tbSku.getOuterId());
				tbLog.setSkuId(tbSku.getSkuId().toString());
				tbLog.setUser(user.getUserName());
				tbLog.setType(type);
				tbLog.setLogTime(date);
                tbLogDao.insertLog(tbLog);
			}
		}
	}

}