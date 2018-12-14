package com.jeesite.modules.asset.job.group;

import com.jeesite.modules.asset.scheduledtask.K3Scheduled;
import com.jeesite.modules.asset.scheduledtask.K3ScheduledTask;
import com.jeesite.modules.asset.scheduledtask.K3Track;
import org.springframework.stereotype.Component;

/**
 * 菜鸟工单定时任务
 */
@Component("rookieGroup")
public class RookieGroup {

    /**
     * 物流查询单
     * @throws Exception
     */
    public void logistics () throws Exception{
        K3ScheduledTask.getK3InfoUpAmLogistics();
    }

    /**
     * 物流纠纷单
     * @throws Exception
     */
    public void dispute () throws Exception{
        K3Scheduled.getInfo();
    }

    /**
     * 退货跟踪单
     * @throws Exception
     */
    public void track () throws Exception{
        K3Track.getInfo();
    }
}
