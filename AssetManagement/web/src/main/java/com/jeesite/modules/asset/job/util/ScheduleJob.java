package com.jeesite.modules.asset.job.util;

import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.asset.job.bean.BeanUtils;
import com.jeesite.modules.asset.job.entity.SysJob;
import com.jeesite.modules.asset.job.entity.SysJobLog;
import com.jeesite.modules.asset.job.service.SysJobLogService;
import com.jeesite.modules.asset.job.service.SysJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 定时任务
 * 
 * @author ruoyi
 *
 */
public class ScheduleJob extends QuartzJobBean
{
    private static final Logger log = LoggerFactory.getLogger(ScheduleJob.class);

    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        SysJob job = new SysJob();
        BeanUtils.copyBeanProp(job, context.getMergedJobDataMap().get(ScheduleUtils.TASK_PROPERTIES));

        SysJobLogService jobLogService = SpringUtils.getBean(SysJobLogService.class);
        if (!SysJobService.key.contains(job.getJobName())) {
            return;
        }
        SysJobLog jobLog = new SysJobLog();
        jobLog.setJobName(job.getJobName());
        jobLog.setJobGroup(job.getJobGroup());
        jobLog.setMethodName(job.getMethodName());
        jobLog.setMethodParams(job.getMethodParams());
        jobLog.setCreateTime(new Date());

        long startTime = System.currentTimeMillis();

        try
        {
            // 执行任务
            log.info("任务开始执行 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            ScheduleRunnable task = new ScheduleRunnable(job.getJobName(), job.getMethodName(), job.getMethodParams());
            Future<?> future = service.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            // 任务状态 0：成功 1：失败
            jobLog.setStatus("0");
            jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");

            log.info("任务执行结束 - 名称：{} 耗时：{} 毫秒", job.getJobName(), times);
        }
        catch (Exception e)
        {
            log.info("任务执行失败 - 名称：{} 方法：{}", job.getJobName(), job.getMethodName());
            log.error("任务执行异常  - ：", e);
            long times = System.currentTimeMillis() - startTime;
            jobLog.setJobMessage(job.getJobName() + " 总共耗时：" + times + "毫秒");
            // 任务状态 0：成功 1：失败
            jobLog.setStatus("1");
            jobLog.setExceptionInfo(e.toString());
        }
        finally
        {
            jobLogService.save(jobLog);
        }
    }
}
