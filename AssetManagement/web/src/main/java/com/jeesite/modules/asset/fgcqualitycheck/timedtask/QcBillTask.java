package com.jeesite.modules.asset.fgcqualitycheck.timedtask;

import com.jeesite.modules.asset.fgcqualitycheck.service.QualityCheckService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QcBillTask {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(QcBillTask.class);

    @Autowired
    private QualityCheckService qualityCheckService;

    private static QcBillTask qcBillTask;

    @PostConstruct
    public void init() {
        qcBillTask = this;
        qcBillTask.qualityCheckService = this.qualityCheckService;
    }

    /**
     * @desc 同步K3质检单
     * @author AlbertFeng
     * @date 2018-08-29 13:18
     */

    public static void syncK3QualityCheck() {
        try {
            qcBillTask.qualityCheckService.SyncK3QualityCheck("");
            logger.info("同步K3质检单=================");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 保存质检单到K3Cloud
     * @author AlbertFeng
     * @date 2018-08-29 13:18
     */
    public static void syncQualityCheckSaveToK3(){
        try {
            qcBillTask.qualityCheckService.SaveQcBillToK3();
            logger.info("保存质检单到K3Cloud=================");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 审核K3Cloud质检单
     * @author AlbertFeng
     * @date 2018-08-31 10:35
     */
    public static void auditK3QcBill () {
        try {
            qcBillTask.qualityCheckService.AuditK3QcBill();
            logger.info("审核K3Cloud质检单=================");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
