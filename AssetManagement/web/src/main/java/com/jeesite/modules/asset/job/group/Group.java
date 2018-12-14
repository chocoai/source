package com.jeesite.modules.asset.job.group;

import com.jeesite.modules.asset.ding.FzTask;
import org.springframework.stereotype.Component;

/**
 * 梵赞定时任务
 */
@Component("fzGroup")
public class Group {

    /**
     * 梵赞排行榜定时任务
     */
    public void leaderboard() throws Exception{
        FzTask.leaderboard();
    }

    /**
     * 定时同步部门人员
     */
    public void departmentUser() {
        FzTask.departmentUser();
    }

    /**
     * 月排行钉钉消息每周五定时推送
     */
    public void msgEveryWeek() {
        FzTask.inoutGoldMessageEveryWeek();
    }

    /**
     * 每月第一天凌晨 定时清零部门内梵钻，跨部门梵钻
     */
    public void updateGoldMonth () {
        FzTask.updateGoldNumberEndOfMonth();
    }
}
