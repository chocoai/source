package com.jeesite.modules.asset.job.group;

import com.jeesite.modules.asset.ding.DingSyncTask;
import com.jeesite.modules.asset.ding.SyncELearningTask;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 * 用于与第三方平台同步信息使用
 */

//
@Component("SyncGroup")
public class SyncGroup {

    /**
     * 调用第三方接口，给elearning学习平台同步数据
     */
    public void syncElearning(){ SyncELearningTask.syncElearning();}

    /**
     * 调用钉钉接口，从钉钉平台拉取人员，组织以及部门数据
     */
    public void syncDingData(){ DingSyncTask.syncDingData();}

}
