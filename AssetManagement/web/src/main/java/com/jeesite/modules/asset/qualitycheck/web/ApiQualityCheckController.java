package com.jeesite.modules.asset.qualitycheck.web;


import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.fgcimage.entity.FgcImg;
import com.jeesite.modules.asset.fgcimage.service.FgcImgService;
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheck;
import com.jeesite.modules.asset.qualitycheck.entity.FgcQualityCheckDetails;
import com.jeesite.modules.asset.qualitycheck.entity.QualityCheckRequestModel;
import com.jeesite.modules.asset.qualitycheck.service.FgcQualityCheckService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.fgc.entity.FgcUser;
import com.jeesite.modules.fgc.service.FgcUserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @program: AssetManagement
 * @description: 质检单接口
 * @author: Albert Feng
 * @create: 2018-08-01 16:22
 **/
@Controller
@RequestMapping(value = "/api/qualityCheck")
public class ApiQualityCheckController extends BaseController {
    @Autowired
    private FgcQualityCheckService fgcQualityCheckService;

    @Resource
    private RedisUtil<String, String> redisString;

    @Autowired
    private FgcUserService fgcUserService;

    @Autowired
    private FgcImgService fgcImgService;
    /**
    * @Description: 质检单列表
    * @Param: [requstBody]
    * @return: com.jeesite.modules.asset.util.result.ReturnInfo
    * @Author: Albert Feng
    * @Date: 2018-08-04
    */ 
    @PostMapping("list")
    @ResponseBody
    public Object list(@RequestBody String requstBody){
        if (requstBody.isEmpty())
            return ReturnDate.error(400000,"请求参数错误！");
        //Json转对象
        QualityCheckRequestModel qualityCheckRequest = (QualityCheckRequestModel)JSONObject.toBean(JSONObject.fromObject(requstBody),QualityCheckRequestModel.class);


        return null;
    }

    /**
     * 用户在质检单的【待质检】和【已完成】按钮进行切换操作时调用接口：
     * @param fgcQualityCheck
     * @param request
     * @param response
     * @param token
     * @return
     */
    @RequestMapping(value = "listData")
    @ResponseBody
    public Object listData(FgcQualityCheck fgcQualityCheck, HttpServletRequest request, HttpServletResponse response, String token) {
        String openId = redisString.get("uvanfactory_user_" + token);
//        String openId = "obaI65Fwh5oFiRmbBdI7kLY0i1Zw";
        if (openId == null || "".equals(openId)) {
            return ReturnDate.error(10000, "请检查登录信息");
        }
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);
        fgcQualityCheck.setQualityInspectorName(fgcUser.getUserName());
        Page<FgcQualityCheck> page = fgcQualityCheckService.findPage(new Page<FgcQualityCheck>(request, response), fgcQualityCheck);
        return page;
    }

    /**
     *  用户点击任意质检单卡片按钮时调用接口，进入物料明细页面；（待质检和已完成页面均通用）
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "formData")
    public Map formData (FgcQualityCheck fgcQualityCheck) {
        Map<String, Object> map = new HashMap<>();
        FgcQualityCheck fgcQualityChecks = fgcQualityCheckService.getData(fgcQualityCheck);
        if (fgcQualityChecks == null) {
            map.put("code", 900);
            map.put("msg", "质检单不存在");
        } else {
//            fgcQualityChecks.setFgcQualityCheckDetailsList(fgcQualityCheckService.selectByQualityId(fgcQualityCheck));
            for (int i = 0; i<fgcQualityChecks.getFgcQualityCheckDetailsList().size(); i++) {
                // 单据号
//                fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setBillNo(fgcQualityChecks.getBillno());
                fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setFid(fgcQualityChecks.getId());
//                // 外观检验
//                if ("A".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getAppearanceTest())) {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setAppearanceTest("合格");
//                }else {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setAppearanceTest("不合格");
//                }
//                // 结构检验
//                if ("A".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getStructuralTest())) {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setStructuralTest("合格");
//                }else {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setStructuralTest("不合格");
//                }
//                // 包装检验
//                if ("A".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getPackTest())) {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setPackTest("合格");
//                }else {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setPackTest("不合格");
//                }
//                // 整改类型
//                if ("A".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getRectifyType())) {
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setRectifyType("8D整改报告");
//                }else if("B".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getRectifyType())){
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setRectifyType("工艺整改报告");
//                } else if("C".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getRectifyType())){
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setRectifyType("异常整改报告");
//                }else if("D".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getRectifyType())){
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setRectifyType("常规异常");
//                }else if("E".equals(fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).getRectifyType())){
//                    fgcQualityChecks.getFgcQualityCheckDetailsList().get(i).setRectifyType("不需要");
//                }
            }
            map.put("code", 200);
            map.put("msg", "请求成功");
            map.put("data", fgcQualityChecks);
        }
        return map;
    }


    /**
     * 用户填写完质检报告内容后，点击【提交质检报告】按钮时调用接口：
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveDetail")
    public Map saveDetail(@RequestBody String req) {
        Map<String, Object> map = new HashMap<>();
        JSONObject jsonObject = JSONObject.fromObject(req);

        String entryID = jsonObject.get("entryID").toString();  // 明细表id
//        List<FgcQualityCheckDetails> detailsList = fgcQualityCheckService.getDetail(entryID);
        //
        String qualityId = jsonObject.get("fid").toString(); // 明细表质检单Id
        FgcQualityCheck fgcQualityCheck1 = fgcQualityCheckService.get(qualityId);
        List<FgcQualityCheckDetails> detailsList = fgcQualityCheckService.getDetail(fgcQualityCheck1);
        System.out.println(detailsList);
        FgcQualityCheckDetails fgcQualityCheckDetails = fgcQualityCheckService.selectById(entryID);
        // 抽检数量p
        fgcQualityCheckDetails.setSampleQty(jsonObject.getLong("sampleQty"));
        // 合格数
        fgcQualityCheckDetails.setSampleQualifiedQty(jsonObject.getLong("sampleQualifiedQty"));
        // 抽检不合格数
        fgcQualityCheckDetails.setSampleDisqualifiedQty(jsonObject.getLong("sampleDisqualifiedQty"));
        // 不良比率
        fgcQualityCheckDetails.setBadRatio(jsonObject.getDouble("badRatio"));
        // 外观检验
        fgcQualityCheckDetails.setAppearanceTest(jsonObject.get("appearanceTest").toString());
        // 结构检验
        fgcQualityCheckDetails.setStructuralTest(jsonObject.get("structuralTest").toString());
        // 包装检验
        fgcQualityCheckDetails.setPackTest(jsonObject.get("packTest").toString());
        // 整改类型
        fgcQualityCheckDetails.setRectifyType(jsonObject.get("rectifyType").toString());
        // 整改状态
        fgcQualityCheckDetails.setRectifyStatus(jsonObject.get("rectifyStatus").toString());
        // 备注
        fgcQualityCheckDetails.setRemarks(jsonObject.get("remarks").toString());
        // 合格类型
        fgcQualityCheckDetails.setQualifiedType(jsonObject.get("qualifiedType").toString());
        // 已检查
        fgcQualityCheckDetails.setIsCheck("1");
        fgcQualityCheckDetails.setIsNewRecord(false);
        try{
            fgcQualityCheckDetails.setQualityId(fgcQualityCheck1);
            fgcQualityCheckService.saveDetail(fgcQualityCheckDetails);
        }catch (Exception e) {
            e.printStackTrace();
            map.put("code", 10000);
            map.put("msg", "更新失败");
            return map;
        }

        // ===========================
        // 2） 若《质检单》-质检单明细中[FID]= FID的所有物料明细的【已检查】字段的值都为“是”，则更新《质检单》的【单据状态】=“完成”；
        List<FgcQualityCheckDetails> fgcQualityCheckDetailsList = fgcQualityCheckService.selectByQualityId(qualityId);
        boolean flag = true;
        if (fgcQualityCheckDetailsList != null && fgcQualityCheckDetailsList.size() >0) {
            for (FgcQualityCheckDetails fgcQualityCheckDetails1 : fgcQualityCheckDetailsList) {
                if ("0".equals(fgcQualityCheckDetails1.getIsCheck())) {
                    flag = false;
                }
            }
            if (flag) {
                FgcQualityCheck fgcQualityCheck = new FgcQualityCheck();
                fgcQualityCheck.setId(qualityId);
                fgcQualityCheck = fgcQualityCheckService.get(fgcQualityCheck);
                fgcQualityCheck.setDocumentStatus("完成");
                fgcQualityCheck.setIsNewRecord(false);
                fgcQualityCheckService.save(fgcQualityCheck);
            }
        }
        // 3） 将不良照片的URL地址及物料信息插入《梵工厂图片》表：
//        String photo1 = jsonObject.getJSONObject("photo").getString("photoUrl1");
        // 物料编码
        String materialCode = jsonObject.getString("materialCode");
        // 物料名称
        String materialName = jsonObject.getString("materialName");
        // 质检单号
        String billno = jsonObject.getString("billno");

        // 操作人
        String openId = redisString.get("uvanfactory_user_" + jsonObject.getString("token"));
        FgcUser fgcUser = fgcUserService.getFgcUserByOpenId(openId);
        String userName = fgcUser.getUserName();
        List<FgcImg> fgcImgList = new ArrayList<>();
        String photoUrl = getInfo(jsonObject, "photo");
        if (!"".equals(photoUrl)) {
            FgcImg fgcImg = new FgcImg();
            fgcImg.setFid(qualityId);
            fgcImg.setFentryId(entryID);
            fgcImg.setMaterielCode(materialCode);
            fgcImg.setMaterielName(materialName);
            fgcImg.setDocumentCode(billno);
            // 照片来源
            fgcImg.setPhotoSource("质检");
            fgcImg.setOperationBy(userName);
            fgcImg.setOperationDate(new Date());
            fgcImg.setIsNewRecord(true);
            fgcImgList.add(fgcImg);
        }
        try{
            fgcImgService.saveData(fgcImgList);
        }catch (Exception e) {
            map.put("code", 10000);
            map.put("msg", "梵工厂图片表保存失败");
            return map;
        }
        map.put("code", 0);
        map.put("msg", "更新成功");
        return map;
    }


    /**
     * 保存质检单物料明细接口
     * 更新匹配行的字段信息【抽检数量、结构检验、外观检验、包装检验、整改状态、不合格数、不良比例、整改类型、合格类型】
     * @param fgcQualityCheckDetails
     */
    @ResponseBody
    @GetMapping(value = "queryk3bysql")
    public Object QueryK3BySql(FgcQualityCheckDetails fgcQualityCheckDetails) {
        try {
            fgcQualityCheckService.SyncK3QualityCheck();
        }catch (Exception ex){
            return ex.toString();
        }
        return "成功";
    }


    public String getInfo (JSONObject json, String key) {
        String value = "";
        try {
            value = json.get(key).toString();
        }catch (NullPointerException e) {

        }
        return value;
    }

}
