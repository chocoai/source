package com.jeesite.modules.asset.util;

import com.jeesite.modules.asset.fgcqualitycheck.service.QualityCheckService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @desc 执行计划帮助类
 * @author AlbertFeng
 * @date 2018-08-20 17:50
 */
@Component
public class ExecutePlanHelper implements Job {
    private static Logger log = LoggerFactory.getLogger(ExecutePlanHelper.class);
    private static ExecutePlanHelper executePlanHelper;
    @Autowired
    private QualityCheckService qualityCheckService;


    @PostConstruct
    public void init() {
        executePlanHelper = this;
        executePlanHelper.qualityCheckService=this.qualityCheckService;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("SyncK3QualityCheck进入执行计划帮助类成功");
        try {
            executePlanHelper.qualityCheckService.SyncK3QualityCheck("2018-08-01");
        }catch (Exception ex){
            log.error("SyncK3QualityCheck",ex.toString());
        }
    }
}
