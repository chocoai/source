/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.amspecimen.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.amspecimen.dao.UploadFaleParam;
import com.jeesite.modules.asset.amspecimen.entity.AmSpeciQualifications;
import com.jeesite.modules.asset.amspecimen.entity.AmSpecimen;
import com.jeesite.modules.asset.amspecimen.entity.AmSpecimenProduct;
import com.jeesite.modules.asset.amspecimen.entity.AmSpecimenSchedule;
import com.jeesite.modules.asset.amspecimen.service.AmSpeciQualificationsService;
import com.jeesite.modules.asset.amspecimen.service.AmSpecimenService;
import com.jeesite.modules.asset.fault.service.FaultRegistrationService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.utils.EmpUtils;
import com.jeesite.modules.sys.utils.UserUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 构建样品进度Controller
 *
 * @author mclaran
 * @version 2018-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/amspecimen/amSpecimen")
public class AmSpecimenController extends BaseController {

    private final String code = "YPJD";
    @Autowired
    private AmSpecimenService amSpecimenService;

    @Autowired
    private AmUtilService amUtilService;
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private AmSpeciQualificationsService amSpeciQualificationsService;
    @Autowired
    private OfficeService service;
    @Autowired
    private FaultRegistrationService faultRegistrationService;
    /**
     * 获取数据
     */
    @ModelAttribute
    public AmSpecimen get(String specimenCode, boolean isNewRecord) {
        return amSpecimenService.get(specimenCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("amspecimen:amSpecimen:view")
    @RequestMapping(value = {"list", ""})
    public String list(AmSpecimen amSpecimen, Model model) {
        model.addAttribute("amSpecimen", amSpecimen);
        return "asset/amspecimen/amSpecimenList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("amspecimen:amSpecimen:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AmSpecimen> listData(AmSpecimen amSpecimen, HttpServletRequest request, HttpServletResponse response) {
        String offerCode=EmpUtils.getOffice().getOfficeCode();
        List<Office> offercList=amSpecimenService.getOfferList("%SJ2%");

        if (ParamentUntil.isBackString(offerCode)&&ParamentUntil.isBackList(offercList)){
            for (int i=0;i<offercList.size();i++){
                String offerCode1=offercList.get(i).getOfficeCode();
                if (offerCode.equals(offerCode1)){
                    amSpecimen.setOfferCode(offerCode);
                }

            }
        }
        Page<AmSpecimen> page = amSpecimenService.findPage(new Page<AmSpecimen>(request, response), amSpecimen);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("amspecimen:amSpecimen:view")
    @RequestMapping(value = "form")
    public String form(AmSpecimen amSpecimen, Model model) {
        if (amSpecimen.getIsNewRecord()) {
            amSpecimen.setSpecimenCode(amSeqService.getSeq(code));
            amSpecimen.setCreateBy(UserUtils.getUser().getUserCode());
            amSpecimen.setCreateDate(new Date());
            amSpecimen.setBillsStatus("0");
        }
        List<Office> offercList=amSpecimenService.getOfferList("%SJ2%");
        String documentStatusName = amUtilService.findDictLabel(amSpecimen.getBillsStatus(), "am_specimen_status");
        amSpecimen.setDocumentStatusName(documentStatusName);
        String documentTypeName = amUtilService.findDictLabel(amSpecimen.getBillsStatus(), "specimen_schedule");
        amSpecimen.setDocumentTypeName(documentTypeName);

//        List<AmSpeciQualifications> speciQualificationsList = new ArrayList<>();
        if (ParamentUntil.isBackList(amSpecimen.getAmSpecimenProductList())) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < amSpecimen.getAmSpecimenProductList().size(); i++) {
                String code = amSpecimen.getAmSpecimenProductList().get(i).getCode();
                AmSpeciQualifications amSpeciQualifications = new AmSpeciQualifications();
                amSpeciQualifications.setCode(code);
                List<AmSpeciQualifications> amSpeciQualificationsList = amSpeciQualificationsService.findList(amSpeciQualifications);
                for (int j = 0; j < amSpeciQualificationsList.size(); j++) {
                    AmSpeciQualifications qualifications = amSpeciQualificationsList.get(j);
                    if ("img".equals(qualifications.getTypeName())){
                        amSpecimen.getAmSpecimenProductList().get(i).setImgUrl(qualifications.getFileUrl());
                        amSpecimen.getAmSpecimenProductList().get(i).setImgPath(qualifications.getSavePath());
                    }
                    if ("file".equals(qualifications.getTypeName())){
                        amSpecimen.getAmSpecimenProductList().get(i).setFileUrl(qualifications.getFileUrl());
                        amSpecimen.getAmSpecimenProductList().get(i).setFilePath(qualifications.getSavePath());
                    }

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code", qualifications.getCode());  //明细编号
                    jsonObject.put("qualificationName", qualifications.getQualificationName());  //文件名
                    jsonObject.put("typeName", qualifications.getTypeName());    //文件类型
                    jsonObject.put("profileSurfix", qualifications.getProfileSurfix());    //文件后缀
                    jsonObject.put("savePath", qualifications.getSavePath());    //文件日期路径
                    jsonObject.put("fileUrl", qualifications.getFileUrl());    //文件全路径
                    jsonObject.put("createTime", qualifications.getCreateTime());    //文件创建时间
                    array.add(jsonObject);
                }
            }
            if (array.size() <= 0) {
                model.addAttribute("speciQualificationsList", "");
            } else {
                model.addAttribute("speciQualificationsList", array.toString());
            }
        } else {
            model.addAttribute("speciQualificationsList", "");
        }

        model.addAttribute("amSpecimen", amSpecimen);
        model.addAttribute("offercList",offercList);
        return "asset/amspecimen/amSpecimenForm";
    }


    /**
     * 保存构建样品进度
     */
    @RequiresPermissions("amspecimen:amSpecimen:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(AmSpecimen amSpecimen, String documentStatusSH) {
        if (ParamentUntil.isBackString(amSpecimen.getOfferCode())){
            Office office=new Office();
            office.setOfficeCode(amSpecimen.getOfferCode());
             office=service.get(office);
            amSpecimen.setFactory(office.getOfficeName());
        }
        //校验完成后，保存
        if (amSpecimen.getIsNewRecord()) {
            amSpecimen.setSpecimenCode(amSeqService.getCode(code));
//            amSpecimen.setBillsStatus("0");
        }
        for (int i=0;i<amSpecimen.getAmSpecimenProductList().size();i++){
            AmSpecimenProduct amSpecimenProduct=amSpecimen.getAmSpecimenProductList().get(i);
            if ("0".equals(amSpecimenProduct.getStatus())&&amSpecimenProduct.getCreateDate()==null){
                amSpecimenProduct.setIsNewRecord(true);
            }
        }
        amSpecimenService.save(amSpecimen);
        amSpecimen=amSpecimenService.get(amSpecimen);
        if (ParamentUntil.isBackString(documentStatusSH)) {
            //保存审核前做校验
            if ("1".equals(documentStatusSH)) {
                if ("0".equals(amSpecimen.getBillsStatus()) || "2".equals(amSpecimen.getBillsStatus())) {
                } else {
                    return renderResult(Global.FALSE, "保存构建样品进度失败！单据状态不为创建或重新审核");
                }
            }

            //保存反审核前做校验
            if ("2".equals(documentStatusSH)) {
                if ("1".equals(amSpecimen.getBillsStatus()) && amSpecimen.getAmSpecimenScheduleList().size() <= 1) {    //反审核前置单据状态必须为1（已审核）
                } else {
                    return renderResult(Global.FALSE, "保存构建样品进度失败！[单据状态]不为已审核或已有处理进度,不能反审");
                }
            }
            //保存审核计划前做校验
            if ("3".equals(documentStatusSH)) {
                //判断审核计划时，是否每一条明细都有对应的进度
                boolean isNotHaveProduct = false;
                for (int i = 0; i < amSpecimen.getAmSpecimenProductList().size(); i++) {
                    AmSpecimenSchedule amSpecimenSchedule = new AmSpecimenSchedule();
                    amSpecimenSchedule.setProductCode(amSpecimen.getAmSpecimenProductList().get(i).getCode());
                    List<AmSpecimenSchedule> amSpecimenScheduleList = amSpecimenService.findScheduleList(amSpecimenSchedule);
                    if (amSpecimenScheduleList == null || amSpecimenScheduleList.size() <= 0) {
                        isNotHaveProduct = true;
                        break;
                    }
                }


                if ("1".equals(amSpecimen.getBillsStatus()) && !(isNotHaveProduct)) {    //反审核前置单据状态必须为1（已审核）
                } else {
                    return renderResult(Global.FALSE, "保存构建样品进度失败！[单据状态]不为已审核或明细没有处理进度,不能审核计划");
                }
            }
            //保存审核计划前做校验
            boolean isHaveTime = false;
            for (int i = 0; i < amSpecimen.getAmSpecimenScheduleList().size(); i++) {
                AmSpecimenSchedule amSpecimenSchedule = amSpecimen.getAmSpecimenScheduleList().get(i);
                if (amSpecimenSchedule.getStartDate() != null && amSpecimenSchedule.getStartDate().length() > 0 || amSpecimenSchedule.getDate() != null && amSpecimenSchedule.getDate().length() > 0) {
                    isHaveTime = true;
                    break;
                }
            }
            if ("4".equals(documentStatusSH)) {
                if ("4".equals(amSpecimen.getBillsStatus()) && !isHaveTime) {    //反审核计划前置单据状态必须为4（已安排）并且不存在时间
                } else {
                    return renderResult(Global.FALSE, "保存构建样品进度失败！处理进度填有时间");
                }
            }
        }

        amSpecimenService.saveRecords(amSpecimen,documentStatusSH);
        return renderResult(Global.TRUE, "保存构建样品进度成功！");
    }

    /**
     * 停用构建样品进度
     */
    @RequiresPermissions("amspecimen:amSpecimen:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(AmSpecimen amSpecimen) {
        amSpecimen.setStatus(AmSpecimen.STATUS_DISABLE);
        amSpecimenService.updateStatus(amSpecimen);
        return renderResult(Global.TRUE, "停用构建样品进度成功");
    }

    /**
     * 启用构建样品进度
     */
    @RequiresPermissions("amspecimen:amSpecimen:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(AmSpecimen amSpecimen) {
        amSpecimen.setStatus(AmSpecimen.STATUS_NORMAL);
        amSpecimenService.updateStatus(amSpecimen);
        return renderResult(Global.TRUE, "启用构建样品进度成功");
    }

    /**
     * 删除构建样品进度
     */
    @RequiresPermissions("amspecimen:amSpecimen:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(AmSpecimen amSpecimen) {
        amSpecimenService.delete(amSpecimen);
        return renderResult(Global.TRUE, "删除构建样品进度成功！");
    }

    /**
     * 保存构建样品文件
     */

  /*  @PostMapping(value = "saveFile")
    @ResponseBody
//    public String saveFile(String detailCode, String qualificationName, String imgBase64String, String profileSurfix, String fileBase64String) {
    public String saveFile(@RequestBody UploadFaleParam UploadFaleParam) {
        String savePath = "amspecimen/"+DateUtils.formatDate(new Date(), "yyyy/MM/dd/");
        boolean imgRst = false;
        boolean fileRst = false;
       if (ParamentUntil.isBackString(UploadFaleParam.getDetailCode())&&!(UploadFaleParam.getDetailCode().indexOf("jqg")!=-1)) {
           if (ParamentUntil.isBackString(UploadFaleParam.getImgBase64String())) {         //有值就进入保存
               AmSpeciQualifications amSpeciQualifications = new AmSpeciQualifications();
               amSpeciQualifications.setTypeName("img");
//            amSpeciQualifications.setFileUrl("/img/"+savePath + qualificationName);
//            qualificationName=((UUID.randomUUID()).toString().replaceAll("-",""))+profileSurfix;
               imgRst = amSpecimenService.uploadFile(UploadFaleParam.getDetailCode(), UploadFaleParam.getImgBase64String(), UploadFaleParam.getQualificationName(), savePath, UploadFaleParam.getProfileSurfix(), amSpeciQualifications);
           }
           if ( ParamentUntil.isBackString(UploadFaleParam.getFileBase64String())) {         //有值就进入保存
               AmSpeciQualifications amSpeciQualifications = new AmSpeciQualifications();
               amSpeciQualifications.setTypeName("file");
//            amSpeciQualifications.setFileUrl("/img/" + savePath + qualificationName);
               fileRst = amSpecimenService.uploadFile(UploadFaleParam.getDetailCode(), UploadFaleParam.getFileBase64String(), UploadFaleParam.getQualificationName(), savePath,  UploadFaleParam.getProfileSurfix(), amSpeciQualifications);
           }
//		amSpecimenService.save(amSpecimen,base64String,qualificationName,profileSurfix);
           if (ParamentUntil.isBackString(UploadFaleParam.getImgBase64String()) && !(ParamentUntil.isBackString(UploadFaleParam.getFileBase64String())) && imgRst) {
               return renderResult(Global.TRUE, "保存上传成功！");
           }
           if (!(ParamentUntil.isBackString(UploadFaleParam.getImgBase64String())) && ParamentUntil.isBackString(UploadFaleParam.getFileBase64String()) && fileRst) {
               return renderResult(Global.TRUE, "保存上传成功！");
           }
           if (ParamentUntil.isBackString(UploadFaleParam.getImgBase64String()) && ParamentUntil.isBackString(UploadFaleParam.getFileBase64String()) && fileRst && imgRst) {
               return renderResult(Global.TRUE, "保存上传成功！");
           }
       }else {
           return renderResult(Global.FALSE, "明细code为空！保存上传失败");
       }
        return renderResult(Global.FALSE, "保存上传失败！");
    }*/

    @PostMapping(value = "saveFile")
    @ResponseBody
    public String saveFile(@RequestBody UploadFaleParam uploadFaleParam) {
        boolean imgRst = false;
        boolean fileRst = false;
        if (ParamentUntil.isBackString(uploadFaleParam.getDetailCode())&&!(uploadFaleParam.getDetailCode().indexOf("jqg")!=-1)) {
            AmSpeciQualifications amSpeciQualifications = new AmSpeciQualifications();
            if (ParamentUntil.isBackString(uploadFaleParam.getFile())) {         //有值就进入保存
                    amSpeciQualifications.setTypeName(uploadFaleParam.getTypeName());
                     amSpeciQualifications.setFileUrl(uploadFaleParam.getFile());
            }
            fileRst = amSpecimenService.uploadFile1(uploadFaleParam.getDetailCode(), uploadFaleParam.getQualificationName(),  uploadFaleParam.getProfileSurfix(),uploadFaleParam.getFilePath(), amSpeciQualifications);
            if (fileRst){
                return renderResult(Global.TRUE, "保存上传成功");
            }
        }else {
            return renderResult(Global.FALSE, "明细code为空！保存上传失败");
        }
        return renderResult(Global.FALSE, "保存上传失败！");
    }

    /**
     * 删除构建样品文件
     */
    @RequestMapping(value = "deletFile", method = RequestMethod.POST)
    @ResponseBody
    public String deletFile(@RequestBody String info) {
        JSONObject jsonObject=JSONObject.fromObject(info);
        String detailCode=jsonObject.getString("detailCode");
        String typeName=jsonObject.getString("typeName");
        if (!(ParamentUntil.isBackString(detailCode))&&!(ParamentUntil.isBackString(typeName))){
            return renderResult(Global.TRUE, "删除文件失败！");
        }
        AmSpeciQualifications amSpeciQualifications = new AmSpeciQualifications();
        amSpeciQualifications.setTypeName(typeName);
        amSpeciQualifications.setCode(detailCode);
        List<AmSpeciQualifications> amSpeciQualificationsList = amSpeciQualificationsService.findList(amSpeciQualifications);
        if (ParamentUntil.isBackList(amSpeciQualificationsList)) {
            for (int i = 0; i < amSpeciQualificationsList.size(); i++) {
                boolean isdelete = amSpecimenService.deleFile(amSpeciQualificationsList.get(i).getFileUrl(),amSpeciQualificationsList.get(i).getTypeName());
//                if (isdelete) {
                    amSpeciQualificationsService.delete(amSpeciQualificationsList.get(i));
//                }

            }
        }
        return renderResult(Global.TRUE, "删除文件成功！");
    }

    /**
     * 物理删除入库表
     */
    @RequiresPermissions("amspecimen:amSpecimen:edit")
    @RequestMapping(value = "deleteDb")
    @ResponseBody
    public String deleteDb(AmSpecimen amSpecimen, String ids) {
        boolean isShStutrs=  amSpecimenService.deleteDbs(amSpecimen,ids);
        if (isShStutrs == true) {
            return renderResult(Global.TRUE, "删除成功！已审核或已安排单据未被删除");
        } else {
            return renderResult(Global.TRUE, "删除样品构建单成功！");
        }
    }
//    /**
//     * 删除构建样品文件
//     */
//    @RequestMapping(value = "deletFile", method = RequestMethod.POST)
//    @ResponseBody
//    public String deletFile(@RequestBody String info) {
//        JSONObject jsonObject=JSONObject.fromObject(info);
//        String detailCode=jsonObject.getString("detailCode");
//        String typeName=jsonObject.getString("typeName");
//        String filePath=jsonObject.getString("filePath");
//        //删除数据库
//        amSpeciQualificationsService.deleteByCodeAndType(detailCode,typeName);
//        List<String> list=new ArrayList<>();
//        list.add(filePath);
//        //删除阿里云，filePath是截取过后的路径
//        faultRegistrationService.deletePicAli(list);
//        return renderResult(Global.TRUE, "删除文件成功！");
//    }

}