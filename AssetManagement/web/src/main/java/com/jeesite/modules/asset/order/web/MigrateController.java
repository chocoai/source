package com.jeesite.modules.asset.order.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.modules.asset.order.entity.AmOrder;
import com.jeesite.modules.asset.order.entity.OrderApply;
import com.jeesite.modules.asset.order.service.AmOrderService;
import com.jeesite.modules.asset.order.service.OrderApplyService;
import com.jeesite.modules.asset.util.service.AmSeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据迁移接口
 */
@RestController
@RequestMapping(value = "migrate")
public class MigrateController {
    @Autowired
    private AmSeqService amSeqService;
    @Autowired
    private AmOrderService amOrderService;
    @Autowired
    private OrderApplyService orderApplyService;
    /**
     * 订单前缀
     */
    private final String PERFIX = "MD";
    /**
     * 单据前缀
     */
    private final String PERFIXC = "BGSQ";

    @RequestMapping(value = "migrateDataNotExists")
    public Map migrateDataNotExists(@RequestBody String request) {
        JSON json = JSONObject.parseObject(request);
        Map<String, Object> map = new HashMap<>();
        AmOrder amOrder = null;
        try {
            amOrder = JSONObject.toJavaObject(json, AmOrder.class);
        }catch (Exception e ) {
            map.put("flag", "failure");
            map.put("code", "500");
            map.put("msg", "数据格式错误");
            map.put("time", DateUtils.getDateTime());
            return map;
        }
        String documentCode = amOrder.getDocumentCode();
        AmOrder amOrder1 = new AmOrder();
        amOrder1.setDocumentCode(documentCode);
        amOrder1 = amOrderService.get(amOrder1);
        if (amOrder1 != null) {
            map.put("flag", "success");
            map.put("code", "200");
            map.put("msg", "存在相同数据!");
            map.put("time", DateUtils.getDateTime());
            return map;
        } else {
            amOrder.setIsNewRecord(true);
        }
        for (int i = 0; i< amOrder.getAmOrderDetailList().size(); i++) {
            amOrder.getAmOrderDetailList().get(i).setDocumentCode(amOrder);
        }
        amOrder.setCreateTime(getDate(amOrder.getCreateTime()));
        amOrder.setSubmitDate(getDate(amOrder.getSubmitDate()));
        amOrder.setConfirmDate(getDate(amOrder.getConfirmDate()));
        amOrder.setPrintingDate(getDate(amOrder.getPrintingDate()));
        return amOrderService.saveMigrateData(amOrder, DateUtils.getDateTime());

    }

    /**
     * 订单管理
     * @param request
     * @return
     */
    @RequestMapping(value = "migrateData")
    @ResponseBody
    public Map migrateData(@RequestBody String request) {
        Map<String, Object> map = new HashMap<>();
        JSON json = JSONObject.parseObject(request);
        AmOrder amOrder = null;
        try {
            amOrder = JSONObject.toJavaObject(json, AmOrder.class);
        }catch (Exception e ) {
            map.put("flag", "failure");
            map.put("code", "500");
            map.put("msg", "数据格式错误");
            map.put("time", DateUtils.getDateTime());
            return map;
        }
        String documentCode = amOrder.getDocumentCode();
        if ("".equals(documentCode) || documentCode == null) {
            amOrder.setDocumentCode(amSeqService.getOrderCode(PERFIX));
            amOrder.setIsNewRecord(true);
        } else {
            AmOrder amOrder1 = new AmOrder();
            amOrder1.setDocumentCode(documentCode);
            amOrder1 = amOrderService.get(amOrder1);
            if (amOrder1 != null) {
                amOrder.setIsNewRecord(false);
            } else {
                amOrder.setIsNewRecord(true);
            }
            amOrderService.deleteDb(documentCode);
        }
        for (int i = 0; i< amOrder.getAmOrderDetailList().size(); i++) {
            amOrder.getAmOrderDetailList().get(i).setDocumentCode(amOrder);
        }
        amOrder.setCreateTime(getDate(amOrder.getCreateTime()));
        amOrder.setSubmitDate(getDate(amOrder.getSubmitDate()));
        amOrder.setConfirmDate(getDate(amOrder.getConfirmDate()));
        amOrder.setPrintingDate(getDate(amOrder.getPrintingDate()));
       return amOrderService.saveMigrateData(amOrder, DateUtils.getDateTime());
    }

    /**
     * 订单变更申请
     * @param request
     * @return
     */
    @RequestMapping(value = "orderChange")
    @ResponseBody
    public Map orderChange(@RequestBody String request) {
        Map<String, Object> map = new HashMap<>();
        JSON json = JSONObject.parseObject(request);
        OrderApply orderApply = null;
        try {
            orderApply = JSONObject.toJavaObject(json, OrderApply.class);
        }catch (Exception e) {
            map.put("flag", "failure");
            map.put("code", "500");
            map.put("msg", "数据格式错误");
            map.put("time", DateUtils.getDateTime());
            return map;
        }

        String documentCode = orderApply.getDocumentCode();
        if ("".equals(documentCode) || documentCode == null) {
            orderApply.setDocumentCode(amSeqService.getOrderApply(PERFIXC));
            orderApply.setIsNewRecord(true);
        } else {
            OrderApply orderApply1 = new OrderApply();
            orderApply1.setDocumentCode(documentCode);
            orderApply1 = orderApplyService.get(orderApply1);
            if (orderApply1 != null) {
                orderApply.setIsNewRecord(false);
            } else {
                orderApply.setIsNewRecord(true);
            }
        }
        orderApply.setApplyDate(getDate(orderApply.getApplyDate()));
        orderApply.setConfirmDate(getDate(orderApply.getConfirmDate()));
        return orderApplyService.saveMigrateData(orderApply, DateUtils.getDateTime());
    }

    public String getDate (String datetime) {
        if (datetime != null && !"".equals(datetime)) {
            datetime = datetime.replace("/", "-");
            String [] date = datetime.split(" ");
            String [] hour = date[1].split(":");
            int length = Integer.parseInt(hour[0]);
            if (hour[0].length() == 1 && length < 10) {
                hour[0] =  "0" + hour[0];
            }
//            String[] time = date[0].split("-");
//            if (Integer.parseInt(time[1]) < 10) {
//                time[1] = "0" + time[1];
//            }
//            if (Integer.parseInt(time[2]) < 10) {
//                time[2] = "0" + time[2];
//            }
//            datetime = time[0] + time[1] + time[2] + " " + hour[0] + ":" + hour[1] + ":" + hour[2];
            datetime = date[0] + " " + hour[0] + ":" + hour[1] + ":" + hour[2];
        }
        return datetime;
    }
}
