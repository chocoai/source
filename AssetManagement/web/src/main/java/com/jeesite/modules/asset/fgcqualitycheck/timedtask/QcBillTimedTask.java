package com.jeesite.modules.asset.fgcqualitycheck.timedtask;


import com.jeesite.modules.asset.fgcqualitycheck.service.QualityCheckService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @desc 质检单定时任务
 * @author AlbertFeng
 * @date 2018-08-28 22:23
 */
@Configuration
@EnableScheduling
@Controller
@RequestMapping("/qcBillTimed")
public class QcBillTimedTask {
    private static Logger log = LoggerFactory.getLogger(QcBillTimedTask.class);
    @Autowired
    private QualityCheckService qualityCheckService;

    @Value("${file.baseDir}")
    String baseDir;


    /**
     * @desc 同步K3质检单(每五分钟同步)
     * @author AlbertFeng
     * @date 2018-08-29 13:18
     */
    @GetMapping("syncqualitycheck")
//    @Scheduled(cron = "0 0/5 * * * ?") //每五分钟执行一次
    public void SyncK3QualityCheck() {
        String FGC_TIMEDTASK_SWITCH = baseDir + "\\fgc_timedtask";
        File dirFile = new File(FGC_TIMEDTASK_SWITCH);
        if (!JudgmentDirExists(dirFile,true)){
            return;
        }
        File file = new File(FGC_TIMEDTASK_SWITCH + "\\fgc_timedtask_synck3qcbilltask.txt");
        if (!JudgmentFileExists(file,true)){
            return;
        }
        try {
            String content = ReadToString(file);
            if (content == null || !content.equals("true")){
                return;
            }
            qualityCheckService.SyncK3QualityCheck("");
        }catch (Exception ex){
            log.error("同步K3质检单定时任务执行失败",ex);
            return;
        }
        log.info("同步K3质检单定时任务成功");
    }

    /**
     * @desc 保存质检单到K3Cloud(每五分钟同步)
     * @author AlbertFeng
     * @date 2018-08-29 13:18
     */
    @GetMapping("savequalitycheck")
//    @Scheduled(cron = "0 0/1 * * * ?") //每1分钟执行一次
    public void SyncQualityCheckSaveToK3() {
        String FGC_TIMEDTASK_SWITCH = baseDir + "\\fgc_timedtask";
        File dirFile = new File(FGC_TIMEDTASK_SWITCH);
        if (!JudgmentDirExists(dirFile,true)){
            return;
        }
        File file = new File(FGC_TIMEDTASK_SWITCH + "\\fgc_timedtask_saveqcbilltok3.txt");
        if (!JudgmentFileExists(file,true)){
            return;
        }
        try {
            String content = ReadToString(file);
            if (content == null || !content.equals("true")){
                return;
            }
            qualityCheckService.SaveQcBillToK3();
        }catch (Exception ex){
            WriteTxtFile("false",file);
            log.error("保存质检单到K3Cloud定时任务",ex);
            return;
        }
        log.info("保存质检单到K3Cloud定时任务执行成功!");
    }


    /**
     * @desc 审核K3Cloud质检单(每五分钟同步)
     * @author AlbertFeng
     * @date 2018-08-31 10:35
     */
    @GetMapping("auditqualitycheck")
//    @Scheduled(cron = "0 0/1 * * * ?") //每五分钟执行一次
    public void AuditK3QcBill() {
        String FGC_TIMEDTASK_SWITCH = baseDir + "\\fgc_timedtask";
        File dirFile = new File(FGC_TIMEDTASK_SWITCH);
        if (!JudgmentDirExists(dirFile,true)){
            return;
        }
        File file = new File(FGC_TIMEDTASK_SWITCH + "\\fgc_timedtask_auditk3qcbill.txt");
        if (!JudgmentFileExists(file,true)){
            return;
        }
        try {
            String content = ReadToString(file);
            if (content == null || !content.equals("true")){
                return;
            }
            qualityCheckService.AuditK3QcBill();
        }catch (Exception ex){
            WriteTxtFile("false",file);
            log.error("审核K3Cloud质检单定时任务执行失败",ex);
            return;
        }
        log.warn("审核K3Cloud质检单定时任务执行成功!");
    }


    /**
     * 定时任务开关控制
     * @param content
     */
    @ResponseBody
    @GetMapping(value = "timedtaskswitch")
    public Object TimedTaskSwitch(String content) {
        String[] fileNames = new String[]{"fgc_timedtask_synck3qcbilltask.txt","fgc_timedtask_saveqcbilltok3.txt","fgc_timedtask_auditk3qcbill.txt"};
        String message = "";
        try {
            if (content.isEmpty()) return ReturnDate.error(-1, "内容错误");
            String FGC_TIMEDTASK_SWITCH = baseDir + "\\fgc_timedtask";
            File dirFile = new File(FGC_TIMEDTASK_SWITCH);
            if (!JudgmentDirExists(dirFile,false)){
                return ReturnDate.error(-1,FGC_TIMEDTASK_SWITCH + "路径不存在");
            }
            for (String fileName:fileNames) {
                File file = new File(FGC_TIMEDTASK_SWITCH + "\\" +fileName);
                if (!JudgmentFileExists(file,false)){
                    return ReturnDate.error(-1, "开关控制文件不存在");
                }
                WriteTxtFile(content,file);
            }
        }catch (Exception ex){
            return ReturnDate.error(-1,ex.toString());
        }
        return ReturnDate.success(0,"成功","");
    }

    /**
     * @desc 判断目录是否存在，并且根据isCreate参数是否创建目录
     * @author AlbertFeng
     * @date 2018-08-28 23:14
     * @param file, isCreate
     * @return boolean
     */
    private boolean JudgmentDirExists(File file,boolean isCreate){
        if (file.isDirectory() && file.exists()){
            return true;
        }else if (isCreate){
            file.mkdir();
            return true;
        }else {
            return false;
        }
    }

    /**
     * @desc 判断文件是否存在，并且根据isCreate参数是否创建文件
     * @author AlbertFeng
     * @date 2018-08-28 23:16
     * @param file, isCreate
     * @return boolean
     */
    private boolean JudgmentFileExists(File file,boolean isCreate){
        if (file.isFile() && file.exists()){
            return true;
        }else if (isCreate){
            try {
                file.createNewFile();
            } catch (Exception e) {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public String ReadToString(File file) {
        String encoding = "UTF-8";
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static boolean WriteTxtFile(String content,File file){
        boolean flag = false;
        FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes("utf-8"));
            flag = true;
        } catch (Exception e) {

        }finally {
            try {
                if (fileOutputStream != null){
                    fileOutputStream.close();
                }
            }catch (IOException e){

            }

        }
        return flag;
    }
}
