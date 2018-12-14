/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.tianmao.entity.SpSelling;
import com.jeesite.modules.asset.util.YzyGetToken;
import com.jeesite.modules.fgc.util.JsonUnit;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.*;
import com.youzan.open.sdk.gen.v3_0_0.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.tianmao.entity.SpSalesprice;
import com.jeesite.modules.asset.tianmao.dao.SpSalespriceDao;

/**
 * 商品销售价目Service
 *
 * @author dwh
 * @version 2018-08-16
 */
@Service
@Transactional(readOnly = true)
public class SpSalespriceService extends CrudService<SpSalespriceDao, SpSalesprice> {
    @Autowired
    private SpSalespriceDao spSalespriceDao;
    @Autowired
    private SpSellingService spSellingService;

    @Value("${POST_K3ClOUDRL}")
    String POST_K3ClOUDRL;  //测试库

    /**
     * 获取单条数据
     *
     * @param spSalesprice
     * @return
     */
    @Override
    public SpSalesprice get(SpSalesprice spSalesprice) {
        return super.get(spSalesprice);
    }

    /**
     * 查询分页数据
     *
     * @param page         分页对象
     * @param spSalesprice
     * @return
     */
    @Override
    public Page<SpSalesprice> findPage(Page<SpSalesprice> page, SpSalesprice spSalesprice) {
        return super.findPage(page, spSalesprice);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param spSalesprice
     */
    @Override
    @Transactional(readOnly = false)
    public void save(SpSalesprice spSalesprice) {
        super.save(spSalesprice);
    }

    /**
     * 更新状态
     *
     * @param spSalesprice
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(SpSalesprice spSalesprice) {
        super.updateStatus(spSalesprice);
    }

    /**
     * 删除数据
     *
     * @param spSalesprice
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(SpSalesprice spSalesprice) {
        super.delete(spSalesprice);
    }

    @Transactional(readOnly = false)
    public boolean synSpPirce() throws Exception {
        boolean rst = false;
        String sql = "/*dialect*/ with base as\n" +
                " (select distinct c.FNUMBER FMATERIALNUMBER,b.FPRICE,b.F_YF_EFFECTIVEDATELONG,F_YF_PRODUCECYCLE+F_YF_DELIVERYCYCLE F_DeliveryCycle  from T_SAL_PRICELIST a\n" +
                "  left join T_SAL_PRICELISTENTRY b on a.FID = b.FID\n" +
                "  left join T_BD_MATERIAL c on b.FMATERIALID = c.FMATERIALID\n" +
                "  left join t_bd_MaterialPurchase d on b.FMATERIALID = d.FMATERIALID\n" +
                "  where b.FMATERIALID > 0 and a.F_YF_SHOP = 152625 and a.FFORBIDSTATUS = 'A' and b.FFORBIDSTATUS = 'A' and ((GETDATE() between F_YF_EFFECTIVEDATELONG and F_YF_EXPIRYDATELONG)))\n" +
                "  select FMATERIALNUMBER F_MaterialCode,FPRICE F_Price,F_DeliveryCycle from base a where F_YF_EFFECTIVEDATELONG = (select MAX(b.F_YF_EFFECTIVEDATELONG) from base b where a.FMATERIALNUMBER = b.FMATERIALNUMBER) and \n" +
                "  FPRICE = (select MAX(c.FPRICE) from base c where a.FMATERIALNUMBER = c.FMATERIALNUMBER)";

        JSONArray jsonArray = InvokeHelper.Execute("QueryBySql", sql, POST_K3ClOUDRL);
        List<String> materialCodeList = spSalespriceDao.getMaterialCodeList();
        spSalespriceDao.deleteAll();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            SpSalesprice spSalesprice = prossOne(jsonObject, materialCodeList);
            this.save(spSalesprice);
        }
        rst = true;

        return rst;
    }

    public SpSalesprice prossOne(JSONObject jsonObject, List<String> materialCodeList) {
        String materialCode = (String) jsonObject.get("F_MaterialCode"); //物料编码
        int deliveryCycle = jsonObject.getInt("F_DeliveryCycle");
        SpSalesprice spSalesprice = new SpSalesprice();
        spSalesprice.setFDeliveryCycle((long) deliveryCycle);
        spSalesprice.setFPrice((float) ((double) jsonObject.get("F_Price")));
        spSalesprice.setFMaterialCode(materialCode);
        spSalesprice.setIsNewRecord(true);
        return spSalesprice;
    }

    public List<SpSalesprice> getAllK3Info() {
        return spSalespriceDao.getAllK3Info();
    }

    /**
     * 同步有赞云
     **/
    @Transactional(readOnly = false)
    public void synSpPirceToYzy (List<SpSalesprice> spSalesprices, List<SpSelling> spSellings) {
        String token= YzyGetToken.getToken();
        YZClient client = new DefaultYZClient(new Token(token)); //new Sign(appKey, appSecret)
        for (int i = 0; i < spSalesprices.size(); i++) {
            SpSalesprice spSalesprice = spSalesprices.get(i);
            String k3Wuliao = spSalesprice.getFMaterialCode();  //K3物料编码
            //根据物料编码查询sku
            YouzanSkusCustomGetParams youzanSkusCustomGetParams = new YouzanSkusCustomGetParams();
            youzanSkusCustomGetParams.setItemNo(k3Wuliao);
//				youzanSkusCustomGetParams.setItemId(spSalesprice);
            YouzanSkusCustomGet youzanSkusCustomGet = new YouzanSkusCustomGet();
            youzanSkusCustomGet.setAPIParams(youzanSkusCustomGetParams);
            String result = client.execute(youzanSkusCustomGet);
            com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(result);
            com.alibaba.fastjson.JSONObject respon= (com.alibaba.fastjson.JSONObject) jsonObject.get("response");
            com.alibaba.fastjson.JSONArray skus_json= (com.alibaba.fastjson.JSONArray) respon.get("skus");
            List skus=null;
            if (skus_json!=null&&skus_json.size()>0){
                skus= JsonUnit.jsonToList(skus_json.toJSONString(),YouzanSkusCustomGetResult.ItemSku.class);
            }
            //更新有赞云sku
            if (skus != null && skus.size() > 0) {
                for (int j = 0; j < skus.size(); j++) {
                    YouzanSkusCustomGetResult.ItemSku obj= (YouzanSkusCustomGetResult.ItemSku) skus.get(j);
                    YouzanItemSkuUpdateParams youzanItemSkuUpdateParams = new YouzanItemSkuUpdateParams();
                    youzanItemSkuUpdateParams.setItemId(obj.getItemId());
                    youzanItemSkuUpdateParams.setItemNo(obj.getItemNo());
                    youzanItemSkuUpdateParams.setPrice(spSalesprice.getFPrice());
//				        youzanItemSkuUpdateParams.setQuantity("210");
                    youzanItemSkuUpdateParams.setSkuId(obj.getSkuId());
                    YouzanItemSkuUpdate youzanItemSkuUpdate = new YouzanItemSkuUpdate();
                    youzanItemSkuUpdate.setAPIParams(youzanItemSkuUpdateParams);
                    String rst = client.execute(youzanItemSkuUpdate);
                }
            }
        }
        //同步商品价格
        for (int j = 0; j < spSellings.size(); j++) {
            SpSelling spSelling = spSellings.get(j);              //获取从有赞云拉下来得
            YouzanItemGetParams youzanItemGetParams = new YouzanItemGetParams();
            youzanItemGetParams.setItemId(spSelling.getItemId());
            YouzanItemGet youzanItemGet = new YouzanItemGet();
            youzanItemGet.setAPIParams(youzanItemGetParams);
            String result = client.execute(youzanItemGet);
            com.alibaba.fastjson.JSONObject jsonObject= com.alibaba.fastjson.JSONObject.parseObject(result);
            com.alibaba.fastjson.JSONObject respon= (com.alibaba.fastjson.JSONObject) jsonObject.get("response");
            com.alibaba.fastjson.JSONArray skus_json= (com.alibaba.fastjson.JSONArray) respon.get("skus");
            List skus=null;
            if (skus_json!=null&&skus_json.size()>0){
                skus= JsonUnit.jsonToList(skus_json.toJSONString(),YouzanItemGetResult.ItemSkuOpenModel.class);
            }
//            YouzanItemGetResult.ItemSkuOpenModel[] skus=result.getItem().getSkus();
            if(skus!=null&&skus.size()>0){
                List<Long> prices=null;
                for (int i=0;i<skus.size();i++){
                    prices=new ArrayList<>();
                    YouzanItemGetResult.ItemSkuOpenModel obj= (YouzanItemGetResult.ItemSkuOpenModel) skus.get(j);
                    Long skuId=obj.getSkuId();
                    Long price=obj.getPrice();
                    prices.add(price);
                }
                Long minPrice= Collections.min(prices);     //得到集合中最小价格
                //更新商品价格
                YouzanItemUpdateParams youzanItemUpdateParams = new YouzanItemUpdateParams();
                youzanItemUpdateParams.setPrice(minPrice);
                youzanItemUpdateParams.setItemId(spSelling.getItemId());
                YouzanItemUpdate youzanItemUpdate = new YouzanItemUpdate();
                youzanItemUpdate.setAPIParams(youzanItemUpdateParams);
                String rst = client.execute(youzanItemUpdate);
            }
        }
    }
}