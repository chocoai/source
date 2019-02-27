package com.jeesite.modules.asset.fgcqualitycheck.web;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.modules.asset.fgcqualitycheck.common.Convert;
import com.jeesite.modules.asset.fgcqualitycheck.entity.QualityCheck;
import com.jeesite.modules.asset.fgcqualitycheck.entity.QualityCheckDetails;
import com.jeesite.modules.asset.fgcqualitycheck.service.QualityCheckService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.service.FgcUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther: len
 * @Date: 2018/8/18 17:00
 * @Description:
 */
@RestController
@RequestMapping("/a/fgc/api/quality")
public class ApiQualityController {
    @Autowired
    private QualityCheckService qualityCheckService;

    @Resource
    private RedisUtil<String, String> redisString;

    @Autowired
    private FgcUserService fgcUserService;

    /**
     * 用户在质检单的【待质检】和【已完成】按钮进行切换操作时调用接口：
     * @param
     * @param request
     * @param response
     * @param token
     * @return
     */

    @RequestMapping(value = "listData", method = RequestMethod.GET)
    @ResponseBody
    public Object listData(QualityCheck qualityCheck, HttpServletRequest request, HttpServletResponse response, String token) {
        String openId = redisString.get("uvanfactory_user_" + token);
//        String openId = "obaI65A28NoBge7apAkT_KH-dt5g";
//        if (openId == null || "".equals(openId)) {
//            return ReturnDate.error(10000, "请检查登录信息");
//        }
//
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);
//        if(fgcUser == null) {
//            return ReturnDate.error(10001, "请检查用户信息");
//        }
        qualityCheck.setUserName(fgcUser.getSysLoginCode().replace(" ",""));
        Page<QualityCheck> page = qualityCheckService.findPage(new Page<QualityCheck>(request, response), qualityCheck);
        if (page.getList() == null || page.getList().size() <0) {
            return ReturnDate.error(10006, "查询不到质检单");
        }
        return ReturnDate.success(0, "请求成功", page);
    }

    /**
     *  用户点击任意质检单卡片按钮时调用接口，进入物料明细页面；（待质检和已完成页面均通用）
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "formData", method = RequestMethod.GET)
    public Map formData (QualityCheck qualityCheck) {
        // 传billno
        Map<String, Object> map = new HashMap<>();
//        String openId = redisString.get("uvanfactory_user_" + token);
////        String openId = "obaI65Fwh5oFiRmbBdI7kLY0i1Zw";
//        if (openId == null || "".equals(openId)) {
//            map.put("code", 10000);
//            map.put("msg", "请检查登录信息");
//            return map;
//        }
//        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);
//        if(fgcUser == null) {
//            map.put("code", 10001);
//            map.put("msg", "请检查用户信息");
//            return map;
//        }

        QualityCheck qualityChecks = qualityCheckService.getData(qualityCheck.getBillno());
        if (qualityChecks == null) {
            map.put("code", 10005);
            map.put("msg", "质检单不存在");
        } else {
            map.put("code", 0);
            map.put("msg", "请求成功");
            map.put("data", qualityChecks);
        }
        return map;
    }


    /**
     * 用户填写完质检报告内容后，点击【提交质检报告】按钮时调用接口：
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveDetail", method = RequestMethod.POST)
    public Map saveDetail(@RequestBody String req, String token) {
        Map<String, Object> map = new HashMap<>();
        String openId = redisString.get("uvanfactory_user_" + token);
//        String  openId = "obaI65GJhkkCUKeJudphg2-mndeg";
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);

        JSONObject jsonObject = JSONObject.fromObject(req);

        String entityId = jsonObject.get("fentityid").toString();  // 明细表id
        //
        String fid = jsonObject.get("fid").toString(); // 明细表fid
        QualityCheck qualityCheck = qualityCheckService.get(fid);
        QualityCheckDetails qualityCheckDetails = qualityCheckService.selectByEntityId(entityId);
        // 复检可接受数
        Integer reexaminationNum = Convert.getLong(jsonObject, "reexaminationNum").intValue();
        qualityCheckDetails.setReexaminationNum(Convert.getLong(jsonObject, "reexaminationNum").intValue());
        // 是否接受收料(A是B否)
        String isReceive = Convert.getString(jsonObject, "isReceive");
        qualityCheckDetails.setIsReceive(Convert.getString(jsonObject, "isReceive"));

        // 抽检数量
        qualityCheckDetails.setSampleQty(Convert.getLong(jsonObject,"sampleQty"));
        // 合格数
        Long qcQualifiedQty = Convert.getLong(jsonObject,"sampleQualifiedQty");
        // 不合格数
        Long qcDisqualifiedQty = Convert.getLong(jsonObject,"sampleDisqualifiedQty");

        // 【合格数】=若[是否接受收料]=“是”，则取：合格数，若[是否接受收料]=“否”，则取0；
        //【不合格数】（后台新字段）=若[是否接受收料]=“是”，则取：不合格数-复检可接受数，若[是否接受收料]=“否”，则取：质检数；
        if ("A".equals(isReceive)) {
            // 合格数
            qualityCheckDetails.setQcQualifiedQty(qcQualifiedQty.doubleValue());
            // 不合格数
            qualityCheckDetails.setQcDisqualifiedQty(NumberUtils.sub(qcDisqualifiedQty.doubleValue(),reexaminationNum.doubleValue()));
        } else {
            qualityCheckDetails.setQcQualifiedQty(new Double(0));
            qualityCheckDetails.setQcDisqualifiedQty(qualityCheckDetails.getQcQty());
        }
        // 抽检不合格数
        qualityCheckDetails.setSampleDisqualifiedQty(Convert.getLong(jsonObject,"sampleDisqualifiedQty"));
        // 不良比率
        qualityCheckDetails.setBadRatio(Convert.getDouble(jsonObject,"badRatio"));
//        // 外观检验
//        qualityCheckDetails.setAppearanceTest(Convert.getString(jsonObject,"appearanceTest"));
//        // 结构检验
//        qualityCheckDetails.setStructuralTest(Convert.getString(jsonObject,"structuralTest"));
//        // 包装检验
//        qualityCheckDetails.setPackTest(Convert.getString(jsonObject,"packTest"));
        // 整改类型
        qualityCheckDetails.setRectifyType(Convert.getString(jsonObject,"rectifyType"));
//        // 整改状态
//        qualityCheckDetails.setRectifyStatus(Convert.getString(jsonObject,"rectifyStatus"));
        // 备注
        qualityCheckDetails.setRemarks(Convert.getString(jsonObject,"remarks"));
//        // 合格类型
//        qualityCheckDetails.setQualifiedType(Convert.getString(jsonObject,"qualifiedType"));
        // 已检查
        qualityCheckDetails.setIsCheck("1");
        //结构不合格数
        if(jsonObject.containsKey("structuralDisqualifiedQty")){
            qualityCheckDetails.setStructuralDisqualifiedQty(jsonObject.getInt("structuralDisqualifiedQty"));
        }
        //外观不合格数
        if(jsonObject.containsKey("appearanceDisqualifiedQty")){
            qualityCheckDetails.setAppearanceDisqualifiedQty(jsonObject.getInt("appearanceDisqualifiedQty"));
        }
        //包装不合格数
        if(jsonObject.containsKey("packageDisqualifiedQty")){
            qualityCheckDetails.setPackageDisqualifiedQty(jsonObject.getInt("packageDisqualifiedQty"));
        }


        // 任务1803 start
//        // 平均不良率
//        qualityCheckDetails.setAvgBadRatio(Convert.getDouble(jsonObject, "avgBadRatio"));
        // 提交时间
        qualityCheckDetails.setSubmitTime(new Date());

        qualityCheckDetails.setIsNewRecord(false);
        String userName = fgcUser.getUserName();
        try{
            qualityCheckDetails.setFid(qualityCheck);
            qualityCheckService.saveDetail(qualityCheckDetails, qualityCheck, fid, jsonObject, userName, entityId);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("code", 10002);
            map.put("msg", "更新失败");
            return map;
        }
        // 2） 若《质检单》-质检单明细中[FID]= FID的所有物料明细的【已检查】字段的值都为“是”，则更新《质检单》的【单据状态】=“完成”；
        boolean flag = true;
        QualityCheck qualityCheck2 = qualityCheckService.get(fid);
        for (QualityCheckDetails qualityCheckDetails1 : qualityCheck2.getQualityCheckDetailsList()) {
            if (!"1".equals(qualityCheckDetails1.getIsCheck())) {
                flag = false;
                continue;
            }
        }
        if (flag) {
            QualityCheck qualityCheck1 = new QualityCheck();
            qualityCheck1.setFid(fid);
            qualityCheck1 = qualityCheckService.get(qualityCheck1);
            qualityCheck1.setDocumentStatus("完成");
            qualityCheck1.setIsNewRecord(false);
            qualityCheckService.saveData(qualityCheck1);
        }
        map.put("code", 0);
        map.put("msg", "更新成功");
        return map;
    }

}
