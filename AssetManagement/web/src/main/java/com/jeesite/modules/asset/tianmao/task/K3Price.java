package com.jeesite.modules.asset.tianmao.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.tianmao.entity.TbLog;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbLogService;
import com.jeesite.modules.asset.tianmao.service.TbSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

@Service
@Transactional(readOnly=false)
public class K3Price implements Runnable {

    private static Logger log = LoggerFactory.getLogger(K3Price.class);

    private TbSku tbSku;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TbSku getTbSku() {
        return tbSku;
    }

    public void setTbSku(TbSku tbSku) {
        this.tbSku = tbSku;
    }

    private final TbLogService tbLogService;
    @Autowired
    public K3Price(TbLogService tbLogService, TbSkuService tbSkuService) {
        this.tbLogService = tbLogService;
        this.tbSkuService = tbSkuService;
    }
    @Autowired
    private final TbSkuService tbSkuService;

    @Override
    public void run() {
        String result = null;
        String sFormId= "BD_SAL_PriceList";
        String time = DateUtils.getDateTime();
        String content = "{\"FormId\":\"BD_SAL_PriceList\",\"FieldKeys\":\"FPrice\",\"FilterString\":\"FMaterialId.FNumber='"+tbSku.getOuterId()+"' and F_YF_Shop=152634 AND F_YF_EntryExpiryDateLong>'"+time+"' AND F_YF_EntryEffectiveDateLong<'"+time+"'AND FRowAuditStatus='A'\",\"OrderString\":\"\",\"TopRowCount\":\"0\",\"StartRow\":\"0\",\"Limit\":\"0\"}";
        try{
            Thread.sleep(1000);
            result = InvokeHelper.ExecuteBillQuery(sFormId, content, url);
            JSONArray jsonArray = JSONObject.parseArray(result);
            String realPrice = jsonArray.getJSONArray(0).get(0).toString();
            if (realPrice != null && !"[]".equals(realPrice)) {
                // 原值r
                String oldPrice = tbSku.getRealPrice();

                BigDecimal old = new BigDecimal(oldPrice);
                BigDecimal now = new BigDecimal(realPrice);
                if (old.compareTo(now) != 0) {
                    realPrice = getTwoDecimal(Double.parseDouble(realPrice));
                    tbSkuService.savePrice(tbSku.getSkuId(), realPrice);

                    TbLog tbLog = new TbLog();
                    tbLog.setSkuId(tbSku.getSkuId().toString());
                    tbLog.setSku(tbSku.getOuterId());
                    tbLog.setType("更新saladliang店铺价格");
                    String describe = "真实售价原值【"+oldPrice+"】,新值【"+realPrice+"】";
                    tbLog.setDescribe(describe);
                    tbLog.setLogTime(new Date());
                    tbLog.setIsNewRecord(true);
                    tbLogService.save(tbLog);
                }
            }
        }catch (Exception e) {
            log.info("K3Cloud服务器异常!");
        }
    }

    /**
     * 将数据保留两位小数
     */
    private String getTwoDecimal(double num) {
        DecimalFormat dFormat = new DecimalFormat("#.00");
        String atm = dFormat.format(num);
        if (".00".equals(atm)) {
            atm = "0.00";
        }
        return atm;
    }
}
