package com.jeesite.modules.asset.job.util;

import com.jeesite.modules.asset.job.entity.SysJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时任务工具类
 * 
 * @author ruoyi
 *
 */
public class ScheduleUtils
{
    private static final Logger log = LoggerFactory.getLogger(ScheduleUtils.class);
    public static final String TASK_CLASS_NAME = "__TASK_CLASS_NAME__";

    public static final String TASK_PROPERTIES = "__TASK_PROPERTIES__";

    /** 默认 */
    public static final String MISFIRE_DEFAULT = "0";

    /** 立即触发执行 */
    public static final String MISFIRE_IGNORE_MISFIRES = "1";

    /** 触发一次执行 */
    public static final String MISFIRE_FIRE_AND_PROCEED = "2";

    /** 不触发立即执行 */
    public static final String MISFIRE_DO_NOTHING = "3";
    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(String jobId)
    {
        return TriggerKey.triggerKey(TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getJobKey(String jobId)
    {
        return JobKey.jobKey(TASK_CLASS_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, String jobId)
    {
        try
        {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        }
        catch (SchedulerException e)
        {
            log.error("getCronTrigger 异常：", e);
        }
        return null;
    }

    /**
     * 创建定时任务
     */
    public static boolean createScheduleJob(Scheduler scheduler, SysJob job, String jobGroups)
    {
        try
        {
            if (!jobGroups.contains(job.getJobName())) {
                return false;
            }
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getJobId())).build();

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getJobId())).withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(TASK_PROPERTIES, job);

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if ("2".equals(job.getJobStatus()))
            {
                pauseJob(scheduler, job.getJobId());
            }
            return true;
        }
        catch (SchedulerException e)
        {
            log.error("createScheduleJob 异常：", e);
            return false;
        }
        catch (Exception e)
        {
            log.error("createScheduleJob 异常：", e);
            return false;
        }
    }

    /**
     * 更新定时任务
     */
    public static boolean updateScheduleJob(Scheduler scheduler, SysJob job, String jobGroups)
    {
        try
        {
            if (!jobGroups.contains(job.getJobName())) {
                return false;
            }
            TriggerKey triggerKey = getTriggerKey(job.getJobId());

            // 表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

            CronTrigger trigger = getCronTrigger(scheduler, job.getJobId());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            // 参数
            trigger.getJobDataMap().put(TASK_PROPERTIES, job);

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if ("2".equals(job.getJobStatus()))
            {
                pauseJob(scheduler, job.getJobId());
            }
            return true;
        }
        catch (SchedulerException e)
        {
            log.error("SchedulerException 异常：", e);
            return false;
        }
        catch (Exception e)
        {
            log.error("SchedulerException 异常：", e);
            return false;
        }
    }

    /**
     * 立即执行任务
     */
    public static boolean run(Scheduler scheduler, SysJob job)
    {
        try
        {
            // 参数
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(TASK_PROPERTIES, job);

            scheduler.triggerJob(getJobKey(job.getJobId()), dataMap);
            return true;
        }
        catch (SchedulerException e)
        {
            log.error("run 异常：", e);
            return false;
        }

    }

    /**
     * 暂停任务
     */
    public static boolean pauseJob(Scheduler scheduler, String jobId)
    {
        try
        {
            scheduler.pauseJob(getJobKey(jobId));
            scheduler.pauseTrigger(getTriggerKey(jobId)); //停止触发器
//            scheduler.unscheduleJob(getTriggerKey(jobId));
            return true;
        }
        catch (SchedulerException e)
        {
            log.error("pauseJob 异常：", e);
            return false;
        }
    }

    /**
     * 恢复任务
     */
    public static boolean resumeJob(Scheduler scheduler, SysJob job)
    {
        try
        {
            scheduler.resumeJob(getJobKey(job.getJobId()));
            scheduler.resumeTrigger(getTriggerKey(job.getJobId()));
//            createScheduleJob(scheduler, job);
//            scheduler.start();
            return true;
        }
        catch (SchedulerException e)
        {
            log.error("resumeJob 异常：", e);
            return false;
        }
    }

    /**
     * 删除定时任务
     */
    public static boolean deleteScheduleJob(Scheduler scheduler, String jobId)
    {
        try
        {
            scheduler.deleteJob(getJobKey(jobId));
            scheduler.unscheduleJob(getTriggerKey(jobId));
            return true;
        }
        catch (SchedulerException e)
        {
            log.error("deleteScheduleJob 异常：", e);
            return false;
        }
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)

    {
        switch (job.getMisfirePolicy())
        {
            case MISFIRE_DEFAULT:
                return cb;
            case MISFIRE_IGNORE_MISFIRES:
                return cb.withMisfireHandlingInstructionIgnoreMisfires();
            case MISFIRE_FIRE_AND_PROCEED:
                return cb.withMisfireHandlingInstructionFireAndProceed();
            case MISFIRE_DO_NOTHING:
                return cb.withMisfireHandlingInstructionDoNothing();
            default:
                return cb.withMisfireHandlingInstructionDoNothing();
        }
    }
}
