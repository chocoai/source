/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.tvclient.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.tvclient.entity.SvTvClient;
import com.jeesite.modules.storevideo.tvclient.dao.SvTvClientDao;

/**
 * 电视在线客户端Service
 * @author Philip Guan
 * @version 2019-02-07
 */
@Service
@Transactional(readOnly=true)
public class SvTvClientService extends CrudService<SvTvClientDao, SvTvClient> {
	
	/**
	 * 获取单条数据
	 * @param svTvClient
	 * @return
	 */
	@Override
	public SvTvClient get(SvTvClient svTvClient) {
		return super.get(svTvClient);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svTvClient
	 * @return
	 */
	@Override
	public Page<SvTvClient> findPage(Page<SvTvClient> page, SvTvClient svTvClient) {
		return super.findPage(page, svTvClient);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svTvClient
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvTvClient svTvClient) {
		super.save(svTvClient);
	}
	
	/**
	 * 更新状态
	 * @param svTvClient
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvTvClient svTvClient) {
		super.updateStatus(svTvClient);
	}
	
	/**
	 * 删除数据
	 * @param svTvClient
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvTvClient svTvClient) {
		super.delete(svTvClient);
	}
	
}