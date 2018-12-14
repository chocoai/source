/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.job.service;

import java.util.Date;
import java.util.List;

import com.jeesite.common.io.PropertiesUtils;
import com.jeesite.modules.asset.job.dao.SysJobDao;
import com.jeesite.modules.asset.job.entity.SysJob;
import com.jeesite.modules.asset.job.util.ScheduleUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;

import javax.annotation.PostConstruct;

/**
 * 定时任务调度表Service
 * @author len
 * @version 2018-11-08
 */
@Service
@Transactional(readOnly=true)
public class SysJobService extends CrudService<SysJobDao, SysJob> {
	@Autowired
	private SysJobDao sysJobDao;
	/**
	 * 获取单条数据
	 * @param sysJob
	 * @return
	 */
	@Override
	public SysJob get(SysJob sysJob) {
		return super.get(sysJob);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param sysJob
	 * @return
	 */
	@Override
	public Page<SysJob> findPage(Page<SysJob> page, SysJob sysJob) {
		return super.findPage(page, sysJob);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param sysJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SysJob sysJob) {
		super.save(sysJob);
	}
	
	/**
	 * 更新状态
	 * @param sysJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SysJob sysJob) {
		super.updateStatus(sysJob);
	}
	
	/**
	 * 删除数据
	 * @param sysJob
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SysJob sysJob) {
		super.delete(sysJob);
	}


	@Autowired
	private Scheduler scheduler;

	@Value("${jobGroups}")
	private String jobGroups;

	public static String key;
	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init()
	{
		PropertiesUtils loader = PropertiesUtils.getInstance();
		key = loader.getProperty("jobGroups");
		List<SysJob> jobList = this.findList(new SysJob());
		for (SysJob job : jobList)
		{
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getJobId());
			// 如果不存在，则创建
			if (cronTrigger == null)
			{
				ScheduleUtils.createScheduleJob(scheduler, job, jobGroups);
			}
			else
			{
				ScheduleUtils.updateScheduleJob(scheduler, job, jobGroups);
			}
		}
	}

	/**
	 * 暂停任务
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public boolean pauseJob(SysJob job) {

		boolean flag = ScheduleUtils.pauseJob(scheduler, job.getJobId());
		if (flag) {
			job.setJobStatus("2");
			super.save(job);
		}
		return flag;
	}

	/**
	 * 恢复任务
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public boolean resumeJob(SysJob job)
	{

		boolean flag = ScheduleUtils.resumeJob(scheduler, job);
		if (flag) {
			job.setJobStatus("0");
			super.save(job);
		}
		return flag;

	}

	/**
	 * 删除任务后，所对应的trigger也将被删除
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public boolean deleteJob(SysJob job)
	{
		boolean flag = ScheduleUtils.deleteScheduleJob(scheduler, job.getJobId());
		if (flag) {
			sysJobDao.delete(job);
		}
		return flag;

	}

	/**
	 * 批量删除调度信息
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */

//	public void deleteJobByIds(String ids)
//	{
//		Long[] jobIds = Convert.toLongArray(ids);
//		for (Long jobId : jobIds)
//		{
//			Job job = jobMapper.selectJobById(jobId);
//			deleteJob(job);
//		}
//	}


	/**
	 * 立即运行任务
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public boolean run(SysJob job)
	{
		return ScheduleUtils.run(scheduler, job);
	}

	/**
	 * 新增任务
	 *
	 * @param job 调度信息 调度信息
	 */

	@Transactional(readOnly = false)
	public boolean insertJobCron(SysJob job) {
		job.setCreateDate(new Date());
		job.setJobStatus("0");
		job.setIsNewRecord(true);
		super.save(job);
		boolean flag = ScheduleUtils.createScheduleJob(scheduler, job, jobGroups);

		return flag;
	}

	/**
	 * 更新任务的时间表达式
	 *
	 * @param job 调度信息
	 */
	@Transactional(readOnly = false)
	public boolean updateJobCron(SysJob job)
	{
		super.save(job);
		boolean flag = ScheduleUtils.updateScheduleJob(scheduler, job, jobGroups);
		return flag;
	}
}