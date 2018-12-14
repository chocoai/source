package com.jeesite.modules.asset.fgcqualitycheck.web;

import com.jeesite.modules.asset.fgcqualitycheck.service.QualityCheckService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qualityCheck")
public class QualityCheckApi {
    private static Logger log = LoggerFactory.getLogger(QualityCheckApi.class);
    @Autowired
    private QualityCheckService qualityCheckService;
    /**
     * 同步K3质检单数据接口
     * @param defaultBeginTime
     */
    @ResponseBody
    @GetMapping(value = "syncQualityCheck")
    public Object SyncK3QualityCheck(String defaultBeginTime) {
        try {
            qualityCheckService.SyncK3QualityCheck(defaultBeginTime);
        }catch (Exception ex){
            log.error("SyncK3QualityCheckApi",ex);
            return ReturnDate.error(-1,ex.toString());
        }
        return ReturnDate.success(0,"成功","");

    }
}