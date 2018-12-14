/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.consumables.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.amlocation.entity.AmLocation;
import com.jeesite.modules.asset.amlocation.service.AmLocationService;
import com.jeesite.modules.asset.consumables.entity.*;
import com.jeesite.modules.asset.consumables.service.*;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.asset.warehouse.entity.AmWarehouse;
import com.jeesite.modules.asset.warehouse.service.AmWarehouseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 调拨单Controller
 *
 * @author dwh
 * @version 2018-05-21
 */
@Controller
@RequestMapping(value = "${adminPath}/consumables/amTransfer")
public class AmTransferController extends BaseController {
    private static String CK_PER_FIX = "DB";

    @Autowired
    private AmTransferService amTransferService;
    @Autowired
    private AmWarehouseService amWarehouseService;

    @Autowired
    private AmUtilService amUtilService;
    @Autowired
    private AmSeqService amSeqService;

    @Autowired
    private AmInstorageService amInstorageService;

    @Autowired
    private AmLocationService locationService;

    @Autowired
    private ConsumablesService consumablesService;

    @Autowired
    private AmConStockService amConStockService;
    @Autowired
    private AmConLocationStockService amConLocationStockService;

    @Autowired
    private AmLibIodetailsService amLibIodetailsService;
    @Autowired
    private AmWarehIodetailsService amWarehIodetailsService;
    /**
     * 获取数据
     */
    @ModelAttribute
    public AmTransfer get(String documentsCode, boolean isNewRecord) {
        return amTransferService.get(documentsCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("consumables:amTransfer:view")
    @RequestMapping(value = {"list", ""})
    public String list(AmTransfer amTransfer, Model model) {
        List<AmWarehouse> warehouseList = amWarehouseService.getWarehouseListByLeaf("1");
        model.addAttribute("warehouseList", warehouseList);
        model.addAttribute("amTransfer", amTransfer);
        return "asset/consumables/amTransferList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("consumables:amTransfer:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<AmTransfer> listData(AmTransfer amTransfer, HttpServletRequest request, HttpServletResponse response) {
        Page<AmTransfer> page = amTransferService.findPage(new Page<AmTransfer>(request, response), amTransfer);
        for (int i = 0; i < page.getList().size(); i++) {
            AmWarehouse inAmWarehouse = new AmWarehouse();
            inAmWarehouse.setWarehouseCode(page.getList().get(i).getInWarehouseCode());
            inAmWarehouse = amWarehouseService.get(inAmWarehouse);
            page.getList().get(i).setInWarehouseName(inAmWarehouse.getWarehouseName());
            AmWarehouse outAmWarehouse = new AmWarehouse();
            outAmWarehouse.setWarehouseCode(page.getList().get(i).getOutWarehouseCode());
            outAmWarehouse = amWarehouseService.get(outAmWarehouse);
            page.getList().get(i).setOutWarehouseName(outAmWarehouse.getWarehouseName());
        }

        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("consumables:amTransfer:view")
    @RequestMapping(value = "form")
    public String form(AmTransfer amTransfer, Model model, String warehouseCode) {
        List<AmWarehouse> warehouseList = amWarehouseService.getWarehouseListByLeaf("1");
        String code = null;
        if (amTransfer.getIsNewRecord()) {
            code = amSeqService.getSeq(CK_PER_FIX);
            amTransfer.setDocumentsCode(code);
            amTransfer.setDocumentStatus("0");
            amTransfer.setDocumentType("0");
        }
        String documentStatusName = amUtilService.findDictLabel(amTransfer.getDocumentStatus(), "am_document_status");
        amTransfer.setDocumentStatusName(documentStatusName);
        model.addAttribute("warehouseList", warehouseList);
        if (amTransfer.getDocumentStatus().equals("1")) {
            amTransfer.setRoad(true);
        } else {
            amTransfer.setRoad(false);
        }
        for (int i = 0; i < amTransfer.getAmTransferDetailsList().size(); i++) {
            AmTransferDetails amTransferDetails = amTransfer.getAmTransferDetailsList().get(i);
            String measureUnitName = amUtilService.findDictLabel(amTransferDetails.getMeasureUnit(), "sys_measure_unit");
            amTransferDetails.setMeasureValue(measureUnitName);
        }
        model.addAttribute("amTransfer", amTransfer);

        return "asset/consumables/amTransferForm";
    }


    /**
     * 保存调拨单
     */
    @RequiresPermissions("consumables:amTransfer:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(AmTransfer amTransfer, String documentStatusSH) {
        //am_detail.Detail(amTransfer);
        boolean isReview = false;
        //判断是否有明细
        if (documentStatusSH != null && documentStatusSH.length() > 0) {
            if (amTransfer.getAmTransferDetailsList().size() <= 0 || amTransfer.getAmTransferDetailsList() == null) {
                return renderResult(Global.FALSE, "审核失败，明细为空");
            }
            String message = amUtilService.getSection();    // 单据合法性检查
            if (!"".equals(message)) {
                return renderResult(Global.FALSE, message);
            }
            isReview = true;
        }
        //判断入库仓库是否停用，如果停用，提示
        AmWarehouse inAmWarehouse = amWarehouseService.get(amTransfer.getInWarehouseCode());
        if (inAmWarehouse == null || !(inAmWarehouse.getStatus().equals("0"))) {
            return renderResult(Global.FALSE, "保存失败，入库仓库未启用或被删除");
        }
        //判断出库仓库是否停用，如果停用，提示
        AmWarehouse outAmWarehouse = amWarehouseService.get(amTransfer.getOutWarehouseCode());
        if (outAmWarehouse == null || !(outAmWarehouse.getStatus().equals("0"))) {
            return renderResult(Global.FALSE, "保存失败，出库仓库未启用或被删除");
        }

        //保存的时候判断仓位与仓库是否对应，验证仓库和仓位，耗材存不存在
        if (amTransfer.getAmTransferDetailsList() != null && amTransfer.getAmTransferDetailsList().size() > 0) {
            for (int i = 0; i < amTransfer.getAmTransferDetailsList().size(); i++) {
                //出库库位是否对应
                String outLocaltionCode = amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode();
                String inLocaltionCode = amTransfer.getAmTransferDetailsList().get(i).getInLocationCode();
                AmLocation outAmLocation = new AmLocation();
                outAmLocation.setLocationCode(outLocaltionCode);
                outAmLocation = locationService.get(outAmLocation);
                if (outAmLocation == null || !(outAmLocation.getWarehouseCode().equals(amTransfer.getOutWarehouseCode()))) {
                    return renderResult(Global.FALSE, "保存失败，出库仓库中找不到该仓位");
                }
                if (outAmLocation == null || !(outAmLocation.getStatus().equals("0"))) {
                    return renderResult(Global.FALSE, "保存失败，入库仓位已停用或删除");
                }
                //入库仓库是否对应
                AmLocation inAmLocation = new AmLocation();
                inAmLocation.setLocationCode(inLocaltionCode);
                inAmLocation = locationService.get(inAmLocation);
                if (inAmLocation != null && !(inAmLocation.getWarehouseCode().equals(amTransfer.getInWarehouseCode()))) {
                    return renderResult(Global.FALSE, "保存失败，入库仓库中找不到该仓位");
                }
                if (inAmLocation != null && !(inAmLocation.getStatus().equals("0"))) {
                    return renderResult(Global.FALSE, "保存失败，入库仓位已停用或删除");
                }
                //耗材是否存在
                Consumables consumables = new Consumables();
                consumables.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
                consumables = consumablesService.get(consumables);
                if (consumables == null || !(consumables.getStatus().equals("0"))) {
                    return renderResult(Global.FALSE, "保存失败，耗材" + consumables.getConsumablesName() + "已删除或停用");
                }


            }

        }
        if (amTransfer.getIsNewRecord()) {
            String code = amSeqService.getCode(CK_PER_FIX);
            amTransfer.setDocumentsCode(code);
        }
        //审核反审核明细出现同样的数据验证
        amTransferService.save(amTransfer);
        AmTransfer amTransferYz = amTransferService.rmRepeatList(amTransfer);
        for (int i = 0; i < amTransferYz.getAmTransferDetailsList().size(); i++) {
            if (documentStatusSH != null && documentStatusSH.equals("1")) {                      //审核时判断出库的调拨数量是否足够
                //验证耗材库存表调拨数量是否足够(出库)
                AmConStock stock = new AmConStock();
                stock.setWarehouseCode(amTransferYz.getOutWarehouseCode());
                stock.setConsumablesCode(amTransferYz.getAmTransferDetailsList().get(i).getConsumablesCode());
                stock = amConStockService.get(stock);
                if (stock == null || stock.getStock() - amTransferYz.getAmTransferDetailsList().get(i).getTransferCount() < 0) {
                    return renderResult(Global.FALSE, "保存失败，" + amTransferYz.getOutWarehouseCode() + " 出库库存不够冲减");
                }
                //验证库位库存表调拨数量是否足够(出库)
                AmConLocationStock amConLocationStock = new AmConLocationStock();
                amConLocationStock.setWarehouseCode(amTransferYz.getOutWarehouseCode());
                amConLocationStock.setConsumablesCode(amTransferYz.getAmTransferDetailsList().get(i).getConsumablesCode());
                amConLocationStock.setLocationCode(amTransferYz.getAmTransferDetailsList().get(i).getOutLocationCode());
                amConLocationStock = amConLocationStockService.get(amConLocationStock);
                if (amConLocationStock == null || amConLocationStock.getStock() - amTransferYz.getAmTransferDetailsList().get(i).getTransferCount() < 0) {
                    return renderResult(Global.FALSE, "保存失败，" + amTransferYz.getAmTransferDetailsList().get(i).getOutLocationCode() + " 出库库存不够冲减");
                }
            } else if (documentStatusSH != null && documentStatusSH.equals("2")) {               //反审核时判断入库仓库是否足够
                //验证耗材库存表调拨数量是否足够(入库)
                AmConStock stock = new AmConStock();
                stock.setWarehouseCode(amTransferYz.getInWarehouseCode());
                stock.setConsumablesCode(amTransferYz.getAmTransferDetailsList().get(i).getConsumablesCode());
                stock = amConStockService.get(stock);
                if (stock == null || stock.getStock() - amTransferYz.getAmTransferDetailsList().get(i).getTransferCount() < 0) {
                    return renderResult(Global.FALSE, "保存失败，" + amTransferYz.getOutWarehouseCode() + " 库存不够冲减");
                }
                //验证库位库存表调拨数量是否足够(入库)
                AmConLocationStock amConLocationStock = new AmConLocationStock();
                amConLocationStock.setWarehouseCode(amTransferYz.getInWarehouseCode());
                amConLocationStock.setConsumablesCode(amTransferYz.getAmTransferDetailsList().get(i).getConsumablesCode());
                amConLocationStock.setLocationCode(amTransferYz.getAmTransferDetailsList().get(i).getInLocationCode());
                amConLocationStock = amConLocationStockService.get(amConLocationStock);
                if (amConLocationStock == null || amConLocationStock.getStock() - amTransferYz.getAmTransferDetailsList().get(i).getTransferCount() < 0) {
                    return renderResult(Global.FALSE, "保存失败，" + amTransferYz.getAmTransferDetailsList().get(i).getOutLocationCode() + " 库位不够冲减");
                }
            }
        }
        //通过事务保存操作
        amTransferService.saveIds(amTransfer,isReview,documentStatusSH,inAmWarehouse,outAmWarehouse);
//        //审核反审核
//        AmConStock stock = null;
//        AmConLocationStock amConLocationStock = null;
//        AmInstorageDetails details = null;
//        if (amTransfer != null && isReview) {
//            for (int i = 0; i < amTransfer.getAmTransferDetailsList().size(); i++) {
//                if (documentStatusSH.equals("1")) {        //审核先做出库操作，再做入库
//                    /**
//                     * 审核的入库操作更新数量
//                     * **/
//                    //耗材库存表出库,
//                    stock = new AmConStock();
//                    stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    stock = amConStockService.get(stock);     //上面已经验证，不会出现为空或者库存小于输入的
//                    //保存出库耗材库存表
//                    stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
////				if (documentStatusSH.equals("1")){      //审核反审核都只改变数量和总金额，不改变单价
//                    stock.setStockPrice(stock.getStockPrice());
//                    stock.setStock(stock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                    stock.setStockAtm(stock.getStock() * stock.getStockPrice());
////				}else if (documentStatusSH.equals("2")){
////					stock.setStockAtm(stock.getStockAtm());
////					stock.setStockPrice(stock.getStockPrice());
////					stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
////				}
//                    stock.setIsNewRecord(false);
//                    amConStockService.save(stock);
//
//                    //库位库存表出库,更新数量
//                    amConLocationStock = new AmConLocationStock();
//                    amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
//                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //上面已经验证，不会出现为空或者库存小于输入的
//                    //保存出库库位库存表
//                    amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
//                    //审核反审核都只改变数量和总金额，不改变单价
//                    amConLocationStock.setStockPrice(amConLocationStock.getStockPrice());
//                    amConLocationStock.setStock(amConLocationStock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                    amConLocationStock.setStockAtm(amConLocationStock.getStock() * amConLocationStock.getStockPrice());
//                    amConLocationStock.setIsNewRecord(false);
//                    amConLocationStockService.save(amConLocationStock);
//
//                    /**
//                     * 审核的入库操作
//                     * **/
//                    //耗材库存表入库,
//                    stock = new AmConStock();
//                    stock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    stock = amConStockService.get(stock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
//                    //带入入库数据保存
//                    if (stock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
//                        stock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + stock.getStock());
//                        stock.setStockAtm(stock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
//                        stock.setIsNewRecord(false);
//                        amConStockService.save(stock);
//                    } else {
//                        stock = new AmConStock();
//                        stock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                        stock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
//                        stock.setIsNewRecord(true);
//                        amConStockService.save(stock);
//                    }
//
//                    //库位库存表入库,
//                    amConLocationStock = new AmConLocationStock();
//                    amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
//                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
//                    //带入入库数据保存
//                    if (amConLocationStock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
//                        amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
//                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + amConLocationStock.getStock());
//                        amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
//                        amConLocationStock.setIsNewRecord(false);
//                        amConLocationStockService.save(amConLocationStock);
//                    } else {
//                        amConLocationStock = new AmConLocationStock();
//                        amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
//                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                        amConLocationStock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
//                        amConLocationStock.setIsNewRecord(true);
//                        amConLocationStockService.save(amConLocationStock);
//                    }
//
//                  //调拨单入库明细
//                    amUtilService.saveDetail(amTransfer.getDocumentsCode(),amTransfer.getTransferDate(),amTransfer.getDocumentType(),inAmWarehouse.getWarehouseCode(),
//                            amTransfer.getAmTransferDetailsList().get(i).getInLocationCode(),amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode(),
//                            amTransfer.getAmTransferDetailsList().get(i).getTransferCount(),amTransfer.getAmTransferDetailsList().get(i).getTransferAmount(),
//                            null,null,amTransfer.getAmTransferDetailsList().get(i).getTransferPrice(),"0");
//                    //调拨单出库明细
//                    amUtilService.saveDetail(amTransfer.getDocumentsCode(),amTransfer.getTransferDate(),amTransfer.getDocumentType(),outAmWarehouse.getWarehouseCode(),
//                            amTransfer.getAmTransferDetailsList().get(i).getInLocationCode(),amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode(),
//                            null,null,
//                            amTransfer.getAmTransferDetailsList().get(i).getTransferCount(),amTransfer.getAmTransferDetailsList().get(i).getTransferAmount(),
//                            amTransfer.getAmTransferDetailsList().get(i).getTransferPrice(),"1");
//                }else if (documentStatusSH.equals("2")){                        //反审核，判断库位库存表和耗材库存表是否为空，
//                    /**
//                     * 反审核的出库操作更新数量(入库调到出库,入库仓库数量相减，出库仓库数量相加)
//                     * **/
//                    //耗材库存表入库,数量相减(做出库操作)
//                    stock = new AmConStock();
//                    stock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    stock = amConStockService.get(stock);     //上面已经验证，不会出现为空或者库存小于输入的数量
//                    //保存出库耗材库存表
//                    stock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
////				if (documentStatusSH.equals("1")){      //审核反审核都只改变数量和总金额，不改变单价
//                    stock.setStockPrice(amTransfer.getAmTransferDetailsList().get(i).getTransferPrice());
//                    stock.setStock(stock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                    stock.setStockAtm(stock.getStock() * stock.getStockPrice());
//                    stock.setIsNewRecord(false);
//                    amConStockService.save(stock);
//
//                    //库位库存表入库,更新数量(做出库操作)
//                    amConLocationStock = new AmConLocationStock();
//                    amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
//                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //上面已经验证，不会出现为空或者库存小于输入的
//                    //保存出库库位库存表
//                    amConLocationStock.setWarehouseCode(amTransfer.getInWarehouseCode());
//                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getInLocationCode());
//                    //审核反审核都只改变数量和总金额，不改变单价
//                    amConLocationStock.setStockPrice(amTransfer.getAmTransferDetailsList().get(i).getTransferPrice());
//                    amConLocationStock.setStock(amConLocationStock.getStock() - amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                    amConLocationStock.setStockAtm(amConLocationStock.getStock() * amConLocationStock.getStockPrice());
//                    amConLocationStock.setIsNewRecord(false);
//                    amConLocationStockService.save(amConLocationStock);
//
//                    /**
//                     * 反审核的入库操作更新数量(入库调到出库,入库仓库数量相减，出库仓库数量相加)
//                     * **/
//                    //耗材库存表出库,(做入库操作)
//                    stock = new AmConStock();
//                    stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                    stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    stock = amConStockService.get(stock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
//                    //带入入库数据保存
//                    if (stock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
//                        stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + stock.getStock());
//                        stock.setStockAtm(stock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
//                        stock.setIsNewRecord(false);
//                        amConStockService.save(stock);
//                    } else {
//                        stock = new AmConStock();
//                        stock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                        stock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        stock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                        stock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        stock.setStockPrice(stock.getStockAtm() / stock.getStock());
//                        stock.setIsNewRecord(true);
//                        amConStockService.save(stock);
//                    }
//
//                    //库位库存表入库,
//                    amConLocationStock = new AmConLocationStock();
//                    amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                    amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                    amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
//                    amConLocationStock = amConLocationStockService.get(amConLocationStock);     //入库不用考虑存在不存在，有记录就更新，没有就新增
//                    //带入入库数据保存
//                    if (amConLocationStock != null) {    //如果有这条数据，总金额相加，单价不变，数量相加
//                        amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
//                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount() + amConLocationStock.getStock());
//                        amConLocationStock.setStockAtm(amConLocationStock.getStockAtm() + amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
//                        amConLocationStock.setIsNewRecord(false);
//                        amConLocationStockService.save(amConLocationStock);
//                    } else {
//                        amConLocationStock = new AmConLocationStock();
//                        amConLocationStock.setWarehouseCode(amTransfer.getOutWarehouseCode());
//                        amConLocationStock.setConsumablesCode(amTransfer.getAmTransferDetailsList().get(i).getConsumablesCode());
//                        amConLocationStock.setLocationCode(amTransfer.getAmTransferDetailsList().get(i).getOutLocationCode());
//                        amConLocationStock.setStock(amTransfer.getAmTransferDetailsList().get(i).getTransferCount());
//                        amConLocationStock.setStockAtm(amTransfer.getAmTransferDetailsList().get(i).getTransferAmount());
//                        amConLocationStock.setStockPrice(amConLocationStock.getStockAtm() / amConLocationStock.getStock());
//                        amConLocationStock.setIsNewRecord(true);
//                        amConLocationStockService.save(amConLocationStock);
//                    }
//                    //反审核删除除仓库数据
//                    amLibIodetailsService.deleteDb(amTransfer.getDocumentsCode());
//                    //反审核删除除库位数据
//                    amWarehIodetailsService.deleteDb(amTransfer.getDocumentsCode());
//                }
//
//            }
//            //更新状态
//            amTransfer.setDocumentStatus(documentStatusSH);
//            amTransferService.updateDocumentStatus(amTransfer);
//        }

        return renderResult(Global.TRUE, "保存调拨单成功！");
    }

    /**
     * 停用调拨单
     */
    @RequiresPermissions("consumables:amTransfer:edit")
    @RequestMapping(value = "disable")
    @ResponseBody
    public String disable(AmTransfer amTransfer) {
        amTransfer.setStatus(AmTransfer.STATUS_DISABLE);
        amTransferService.updateStatus(amTransfer);
        return renderResult(Global.TRUE, "停用调拨单成功");
    }

    /**
     * 启用调拨单
     */
    @RequiresPermissions("consumables:amTransfer:edit")
    @RequestMapping(value = "enable")
    @ResponseBody
    public String enable(AmTransfer amTransfer) {
        amTransfer.setStatus(AmTransfer.STATUS_NORMAL);
        amTransferService.updateStatus(amTransfer);
        return renderResult(Global.TRUE, "启用调拨单成功");
    }

//	/**
//	 * 删除调拨单
//	 */
//	@RequiresPermissions("consumables:amTransfer:edit")
//	@RequestMapping(value = "delete")
//	@ResponseBody
//	public String delete(AmTransfer amTransfer) {
//		amTransferService.delete(amTransfer);
//		return renderResult(Global.TRUE, "删除调拨单成功！");
//	}

    /**
     * 物理删除入库表
     */
    @RequiresPermissions("consumables:amTransfer:edit")
    @RequestMapping(value = "deleteDb")
    @ResponseBody
    public String deleteDb(AmTransfer amTransfer, String ids) {
        boolean isShStutrs=  amTransferService.deleteDbs(amTransfer,ids);
        if (isShStutrs == true) {
            return renderResult(Global.TRUE, "删除成功！已审核单据未被删除");
        } else {
            return renderResult(Global.TRUE, "删除调拨单成功！");
        }
    }

}