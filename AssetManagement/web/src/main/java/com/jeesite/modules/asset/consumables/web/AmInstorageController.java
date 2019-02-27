/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.amlocation.service.AmLocationService;
import com.jeesite.modules.asset.consumables.entity.*;
import com.jeesite.modules.asset.consumables.service.*;
import com.jeesite.modules.userroleconfig.UserDataPermissionUnit;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.service.AmWarehouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
//import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 入库表Controller
 *
 * @author dwh
 * @version 2018-05-03
 */
@Controller
@RequestMapping(value = "${adminPath}/consumables/amInstorage")
public class AmInstorageController extends BaseController {
    private static final String CK_PER_FIX = "RK";

    @Autowired
    private AmInstorageService amInstorageService;
    @Autowired
    private AmWarehouseService amWarehouseService;
    @Autowired
    private AmConStockService amConStockService;
    @Autowired
    private AmConLocationStockService amConLocationStockService;
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private AmUtilService amUtilService;
    @Autowired
    private AmLocationService locationService;
    @Autowired
    private AmWarehIodetailsService amWarehIodetailsService;
    @Autowired
    private AmLibIodetailsService amLibIodetailsService;
    @Autowired
    private UserDataPermissionUnit userDataPermissionUnit;
    /**
     * 获取数据
     */
    @ModelAttribute
    public AmInstorage get(String instorageCode, boolean isNewRecord) {
        return amInstorageService.get(instorageCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("consumables:amInstorage:view")
    @RequestMapping(value = {"list", ""})
    public String list(AmInstorage amInstorage, Model model) {
        List<AmWarehouse> warehouseList = amWarehouseService.getWarehouseListByLeaf("1");
        model.addAttribute("warehouseList", warehouseList);

        model.addAttribute("amInstorage", amInstorage);
        return "asset/consumables/amInstorageList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("consumables:amInstorage:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AmInstorage> listData(AmInstorage amInstorage, HttpServletRequest request, HttpServletResponse response) {
        String param=userDataPermissionUnit.getParam(request);
        amInstorage.setParam(param);
        Page<AmInstorage> page = amInstorageService.findPage(new Page<AmInstorage>(request, response), amInstorage);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("consumables:amInstorage:view")
    @RequestMapping(value = "form")
    public String form(AmInstorage amInstorage, Model model, String warehouseCode) {
        List<AmWarehouse> warehouseList = amWarehouseService.getWarehouseListByLeaf("1");
        List<AmLocation> amLocationList = amInstorageService.getAmLocationListByCode(warehouseCode);
        String code = null;
        if (amInstorage.getIsNewRecord()) {
            code = amSeqService.getSeq(CK_PER_FIX);
            amInstorage.setInstorageCode(code);

            amInstorage.setDocumentStatus("0");
            amInstorage.setDocumentType("0");
        }

        String documentStatusName = amUtilService.findDictLabel(amInstorage.getDocumentStatus(), "am_document_status");
        amInstorage.setDocumentStatusName(documentStatusName);
        model.addAttribute("amLocationList", amLocationList);
        model.addAttribute("warehouseList", warehouseList);
        for (int i = 0; i < amInstorage.getAmInstorageDetailsList().size(); i++) {
            AmInstorageDetails amInstorageDetails = amInstorage.getAmInstorageDetailsList().get(i);
            String measureUnitName = amUtilService.findDictLabel(amInstorageDetails.getMeasureUnit(), "sys_measure_unit");
            amInstorageDetails.setMeasureValue(measureUnitName);
        }
        model.addAttribute("amInstorage", amInstorage);
        return "asset/consumables/amInstorageForm";
    }


    /**
     * 保存入库表
     */
    @RequiresPermissions("consumables:amInstorage:edit")
    @PostMapping(value = "save")
    @ResponseBody
//    public String save(@Validated AmInstorage amInstorage, String documentStatusSH) {
    public String save(AmInstorage amInstorage, String documentStatusSH, HttpServletRequest request) throws IOException {

        AmConStock stock = null;
        AmConLocationStock amConLocationStock = null;
        AmInstorageDetails details = null;
        boolean isReview = false;
        if (documentStatusSH != null && documentStatusSH.length() > 0) {
//            System.out.print("---------------次数"+"---------------------------");

            if (amInstorage.getAmInstorageDetailsList().size() <= 0 || amInstorage.getAmInstorageDetailsList() == null) {
                return renderResult(Global.FALSE, "审核失败，明细为空");
            }
            String message = amUtilService.getSection();    // 单据合法性检查
            if (!"".equals(message)) {
                return renderResult(Global.FALSE, message);
            }
            isReview = true;
        }

        //保存的时候判断仓位与仓库是否对应，验证仓库和仓位
//        if (amInstorage.getAmInstorageDetailsList() != null && amInstorage.getAmInstorageDetailsList().size() > 0) {
            //保存审核的时候判断仓库是否停用，如果停用，提示
            if (!"2".equals(documentStatusSH)) {
                AmWarehouse amWarehouse = amWarehouseService.get(amInstorage.getWarehouseCode());
                if (amWarehouse == null || amWarehouse.getStatus().equals("2")) {
                    return renderResult(Global.FALSE, "保存失败，仓库已被停用据或删除");
                }

                for (int i = 0; i < amInstorage.getAmInstorageDetailsList().size(); i++) {
                    String localtionCode = amInstorage.getAmInstorageDetailsList().get(i).getLocationCode();
                    AmLocation amLocation = new AmLocation();
                    amLocation.setLocationCode(localtionCode);
                    amLocation = locationService.get(amLocation);
                    if (amLocation != null && !(amLocation.getWarehouseCode().equals(amInstorage.getWarehouseCode()))) {
                        return renderResult(Global.FALSE, "保存失败，仓库中找不到该仓位");
                    }
                    if (amLocation != null && !(amLocation.getStatus().equals("0"))) {
                        return renderResult(Global.FALSE, "保存失败，仓位已停用或删除");
                    }
                }
                String message = amInstorageService.getoutStock(amInstorage);
                if (!"".equals(message)) {
                    return renderResult(Global.FALSE, message);
                }
                if (amInstorage.getIsNewRecord()) {
                    String code = amSeqService.getCode(CK_PER_FIX);
                    amInstorage.setInstorageCode(code);
                }
                amInstorageService.save(amInstorage);
            }
            //审核反审核验证
            if (isReview) {

                for (int i = 0; i < amInstorage.getAmInstorageDetailsList().size(); i++) {
                    stock = new AmConStock();
                    stock.setWarehouseCode(amInstorage.getWarehouseCode());
                    stock.setConsumablesCode(amInstorage.getAmInstorageDetailsList().get(i).getConsumablesCode());
                    stock = amConStockService.get(stock);
                    if (stock != null) {
                        if ("1".equals(documentStatusSH) && stock.getStock() + amInstorage.getAmInstorageDetailsList().get(i).getInstorageCount() < 0) {
                            return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减！");
                        }
                        if ("2".equals(documentStatusSH) && stock.getStock() - amInstorage.getAmInstorageDetailsList().get(i).getInstorageCount() < 0) {
                            return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减！");
                        }
                    }


                    amConLocationStock = new AmConLocationStock();
                    amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());//仓库编码
                    details = amInstorage.getAmInstorageDetailsList().get(i);
                    amConLocationStock.setConsumablesCode(details.getConsumablesCode());
                    amConLocationStock.setLocationCode(details.getLocationCode());
                    amConLocationStock = amConLocationStockService.get(amConLocationStock); //根据三个条件查询数据库
                    if (amConLocationStock != null) {
                        if ("1".equals(documentStatusSH) && amConLocationStock.getStock() + amInstorage.getAmInstorageDetailsList().get(i).getInstorageCount() < 0) {
                            return renderResult(Global.FALSE, "审核失败，仓位不够库存冲减！");
                        }
                        if ("2".equals(documentStatusSH) && amConLocationStock.getStock() - amInstorage.getAmInstorageDetailsList().get(i).getInstorageCount() < 0) {
                            return renderResult(Global.FALSE, "审核失败，仓位不够库存冲减！");
                        }
                    }
                }
            }
//        }

        List<AmConStock> stocks = new ArrayList<>();
        List<AmConLocationStock> amConLocationStocks = new ArrayList<>();
        //通过事务保存操作
        amInstorageService.saveIds(amInstorage,isReview,stock,amConLocationStock,details,documentStatusSH);
//        if (amInstorage != null && isReview) {
//
//            //更新插入库存表
////            stock = new AmConStock();
////            stock.setWarehouseCode(amInstorage.getWarehouseCode());
//            //更新插入库位库存表
//
//            for (int i = 0; i < amInstorage.getAmInstorageDetailsList().size(); i++) {
//                stock = new AmConStock();
//                stock.setWarehouseCode(amInstorage.getWarehouseCode());
//                stock.setConsumablesCode(amInstorage.getAmInstorageDetailsList().get(i).getConsumablesCode());
//                stock = amConStockService.get(stock);
//
//                amConLocationStock = new AmConLocationStock();
//                amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());//仓库编码
//                details = amInstorage.getAmInstorageDetailsList().get(i);
//                amConLocationStock.setConsumablesCode(details.getConsumablesCode());
//                amConLocationStock.setLocationCode(details.getLocationCode());
//                amConLocationStock = amConLocationStockService.get(amConLocationStock); //根据三个条件查询数据库
//                if (stock != null) {
////                    if (stock.getStock() + details.getInstorageCount() <= 0) {
////                        return renderResult(Global.FALSE, "审核失败，仓库不够库存冲减！");
////                    }
////                    AmConStock stockadd = new AmConStock();
//                    stock.setWarehouseCode(amInstorage.getWarehouseCode());
//                    stock.setConsumablesCode(details.getConsumablesCode());
//                    if ("1".equals(documentStatusSH)) {
//                        stock.setStock(stock.getStock() + details.getInstorageCount());
//                        stock.setStockAtm(stock.getStockAtm() + details.getInstorageAmount());
//                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
//                    } else if ("2".equals(documentStatusSH)) {
//                        long getInstorageCount = stock.getStock() - details.getInstorageCount();
//                        if (getInstorageCount <= 0) {                 //如果反审核时的库存数量为0时,总和和单价都为0
//                            stock.setStock((long) 0);
//                            stock.setStockPrice(0.0);
//                            stock.setStockAtm(0.0);
//                        } else {
//                            stock.setStock(stock.getStock() - details.getInstorageCount());
//                            stock.setStockAtm(stock.getStockAtm() - details.getInstorageAmount());
//                            stock.setStockPrice(stock.getStockAtm() / stock.getStock());
//                        }
//                    }
//                    stock.setIsNewRecord(false);
////                    stocks.add(stockadd);
//                    amConStockService.save(stock);
//                } else {
//                    stock = new AmConStock();
//                    stock.setConsumablesCode(details.getConsumablesCode());
//                    stock.setWarehouseCode(amInstorage.getWarehouseCode());
//                    stock.setStock(details.getInstorageCount());
//                    stock.setStockPrice(details.getInstoragePrice());
//                    stock.setStockAtm(details.getInstorageAmount());
//                    stock.setIsNewRecord(true);
////                    stocks.add(stock);
//                    amConStockService.save(stock);
//                }
//                //更新插入库位库存表
//                if (amConLocationStock != null) {
////                    if (amConLocationStock.getStock() + amInstorage.getAmInstorageDetailsList().get(i).getInstorageCount() <= 0) {
////                        return renderResult(Global.FALSE, "审核失败，仓位不够库存冲减！");
////                    }
////                    AmConLocationStock amConLocationStockadd = new AmConLocationStock();
//                    amConLocationStock.setLocationCode(details.getLocationCode());
//                    amConLocationStock.setConsumablesCode(details.getConsumablesCode());
//                    amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());
//                    if ("1".equals(documentStatusSH)) {
//                        amConLocationStock.setStock(amConLocationStock.getStock() + details.getInstorageCount());
//                        amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() + details.getInstorageAmount());
//                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
//                    } else if ("2".equals(documentStatusSH)) {
//                        long InstorageCount = amConLocationStock.getStock() - details.getInstorageCount();
//                        if (InstorageCount <= 0) {//如果反审核时的库存数量为0时
//                            amConLocationStock.setStock((long) 0);
//                            amConLocationStock.setStockPrice(0.0);
//                            amConLocationStock.setStockAtm(0.0);
//                        } else {
//                            amConLocationStock.setStock(amConLocationStock.getStock() - details.getInstorageCount());
//                            amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() - details.getInstorageAmount());
//                            amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
//                        }
//                    }
//                    amConLocationStock.setIsNewRecord(false);
////                    amConLocationStocks.add(amConLocationStockadd);
//                    amConLocationStockService.save(amConLocationStock);
//
//                } else {
//                    amConLocationStock = new AmConLocationStock();
//                    amConLocationStock.setLocationCode(details.getLocationCode());
//                    amConLocationStock.setConsumablesCode(details.getConsumablesCode());
//                    amConLocationStock.setWarehouseCode(amInstorage.getWarehouseCode());
//                    amConLocationStock.setStock(details.getInstorageCount());
//                    amConLocationStock.setStockPrice(details.getInstoragePrice());
//                    amConLocationStock.setStockAtm(details.getInstorageAmount());
//                    amConLocationStock.setIsNewRecord(true);
////                    amConLocationStocks.add(amConLocationStock);
//                    amConLocationStockService.save(amConLocationStock);
//                }
//                if ("1".equals(documentStatusSH)) {
//                    amUtilService.saveDetail(amInstorage.getInstorageCode(),amInstorage.getIncomingDate(),amInstorage.getDocumentType(),amInstorage.getWarehouseCode(),
//                            details.getLocationCode(),details.getConsumablesCode(),details.getInstorageCount(),details.getInstorageAmount(),
//                            null,null,details.getInstoragePrice(),"0");
//
//                } else if ("2".equals(documentStatusSH)) {
//                    amLibIodetailsService.deleteDb(amInstorage.getInstorageCode());
//                    amWarehIodetailsService.deleteDb(amInstorage.getInstorageCode());
//                }
//            }
//            //更新状态
//            amInstorage.setDocumentStatus(documentStatusSH);
//            amInstorageService.updateDocumentStatus(amInstorage);
//        }


        return renderResult(Global.TRUE, "保存入库表成功！");
    }

    /**
     * 停用入库表
     */
    @RequiresPermissions("consumables:amInstorage:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(AmInstorage amInstorage) {
        amInstorage.setStatus(AmInstorage.STATUS_DISABLE);
        amInstorageService.updateStatus(amInstorage);
        return renderResult(Global.TRUE, "停用入库表成功");
    }

    /**
     * 启用入库表
     */
    @RequiresPermissions("consumables:amInstorage:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(AmInstorage amInstorage) {
        amInstorage.setStatus(AmInstorage.STATUS_NORMAL);
        amInstorageService.updateStatus(amInstorage);
        return renderResult(Global.TRUE, "启用入库表成功");
    }

    /**
     * 删除入库表
     */
    @RequiresPermissions("consumables:amInstorage:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(AmInstorage amInstorage, String ids) {
        if (ids != null && ids.length() > 0) {
            String[] codeList = ids.split(",");
            for (int i = 0; i < codeList.length; i++) {
                String code = codeList[i];
                amInstorage = new AmInstorage();
                amInstorage.setInstorageCode(code);
                amInstorageService.delete(amInstorage);
            }
        } else {
            amInstorageService.delete(amInstorage);
        }

        return renderResult(Global.TRUE, "删除入库表成功！");
    }

    /**
     * 查看编辑表单
     */
    @RequestMapping(value = "formUtil")
    public String formUtil(Consumables consumables, Model model, String selectData) {
        model.addAttribute("selectData", selectData);

        model.addAttribute("consumables", consumables);
        return "asset/consumables/selectConsumablesList";
    }


    /**
     * 物理删除入库表
     */
    @RequiresPermissions("consumables:amInstorage:edit")
    @RequestMapping(value = "deleteDb")
    @ResponseBody
    public String deleteDb(AmInstorage amInstorage, String ids) {
        boolean isShStutrs=  amInstorageService.deleteDbs(amInstorage,ids);
        if (isShStutrs == true) {
            return renderResult(Global.TRUE, "删除成功！已审核单据未被删除");
        } else {
            return renderResult(Global.TRUE, "删除入库表成功！");
        }
    }


}