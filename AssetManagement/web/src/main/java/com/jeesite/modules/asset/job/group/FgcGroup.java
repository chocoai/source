package com.jeesite.modules.asset.job.group;

import com.jeesite.modules.asset.fgcqualitycheck.timedtask.QcBillTask;
import org.springframework.stereotype.Component;

/**
 * 梵工厂定时任务
 */
@Component("fgcGroup")
public class FgcGroup {
    /**
     * 同步K3质检单
     */
    public void syncK3QualityCheck() {
        QcBillTask.syncK3QualityCheck();
    }

    /**
     * 保存质检单到K3Cloud
     */
    public void syncQualityCheckSaveToK3() {
        QcBillTask.syncQualityCheckSaveToK3();
    }

    /**
     * 审核K3Cloud质检单
     */
    public void auditK3QcBill() {
        QcBillTask.auditK3QcBill();
    }
}
