package com.jeesite.modules.asset.tianmao.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.asset.tianmao.entity.BigItem;
import com.jeesite.modules.asset.tianmao.entity.TbItemImgs;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.SynchroTbService;
import com.taobao.api.domain.Item;
import com.taobao.api.domain.ItemImg;
import com.taobao.api.domain.PropImg;
import com.taobao.api.domain.Sku;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 接收淘宝商品数据
 *
 * @author Jace Xiong
 */
@RestController
@RequestMapping("tianmao")
public class SynchroTbController {
    @Autowired
    private SynchroTbService service;
    /**
     * 同步商品信息
     * @return 是否插入成功
     */
    @RequestMapping(value = "synchro")
    public Map synchroItems(@RequestBody String reqItem){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date());
        JSON json = JSONObject.parseObject(reqItem);
        Item item = JSONObject.toJavaObject(json,Item.class);
        try {
            if ("saladliang".equals(item.getNick())) {
                Map<String,Object> map = new HashMap<>(20);
                map.put("flag", "success");
                map.put("code", 200);
                map.put("msg", "saladliang店铺的商品不同步！");
                return map;
            }
            // sku图片
            List<PropImg> propImgList = item.getPropImgs();
            //遍历sku
            List<Sku> skuList = item.getSkus();
            List<TbSku> tbSkus = new ArrayList<>();
            if(skuList!=null && skuList.size()>0){
                for (Sku aSkuList : skuList) {
                    TbSku tbSku = new TbSku();
                    tbSku.setSkuId(aSkuList.getSkuId());
                    tbSku.setNumIid(item.getNumIid());
                    tbSku.setPropertiesName(aSkuList.getPropertiesName());
                    if (ListUtils.isNotEmpty(propImgList)) {
                        Optional<PropImg> proImg = propImgList.stream().filter(s -> aSkuList.getPropertiesName().contains(s.getProperties())).findFirst();
                        if (proImg.isPresent()) {
                            tbSku.setSkuUrl(proImg.get().getUrl());
                        }
                    }
                    tbSku.setPrice(aSkuList.getPrice());
                    tbSku.setQuantity(aSkuList.getQuantity());
                    tbSku.setProperties(aSkuList.getProperties());
                    tbSku.setCreated(aSkuList.getCreated());
                    tbSku.setModified(aSkuList.getModified());
                    tbSku.setStatus(aSkuList.getStatus());
//            tbSku.setBarcode(skuList.get(i).getBarcode());
                    tbSku.setRealPrice(aSkuList.getBarcode());
                    tbSku.setOuterId(aSkuList.getOuterId());
                    // 预售时间
                    tbSku.setPreSale(aSkuList.getChangeProp());
                    tbSkus.add(tbSku);
                }
            }

            //遍历图片
            List<ItemImg> imgs = item.getItemImgs();
            List<TbItemImgs> itemImgs = new ArrayList<>();
            if(imgs!=null && imgs.size()>0){
                for(ItemImg itemImg: imgs){
                    TbItemImgs tbItemImgs = new TbItemImgs();
                    tbItemImgs.setItemId(itemImg.getId());
                    tbItemImgs.setCreated(itemImg.getCreated());
                    tbItemImgs.setUrl(itemImg.getUrl());
                    tbItemImgs.setPosition(itemImg.getPosition());
                    tbItemImgs.setItemId(item.getNumIid());
                    itemImgs.add(tbItemImgs);
                }
            }


            TbProduct tbProduct = new TbProduct();

            try {
                tbProduct.setPicUrl(item.getPicUrl());
                tbProduct.setDelistTim(item.getDelistTime());
                tbProduct.setListTime(item.getListTime());
                tbProduct.setModified(item.getModified());
                tbProduct.setNumIid(String.valueOf(item.getNumIid()));
                tbProduct.setCid(item.getCid());
                tbProduct.setTitle(item.getTitle());
                tbProduct.setPrice(item.getPrice());
                tbProduct.setApproveStatus(item.getApproveStatus());
                tbProduct.setDetailUrl(item.getDetailUrl());
                tbProduct.setNick(item.getNick());
                tbProduct.setOuterCode(item.getOuterId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            BigItem bigItem = new BigItem();
            bigItem.setId(item.getNumIid().toString());
            bigItem.setBody(JSONObject.toJSONString(item));
            bigItem.setTime(time);
            Map map = service.insert(bigItem,tbProduct,tbSkus,itemImgs,time);
            return map;
        }catch (Exception e){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            Map<String,Object> mapError = new HashMap<>(20);
            mapError.put("flag", "failure");
            mapError.put("code",500);
            mapError.put("msg",sw.toString());
            mapError.put("time",time);
            return mapError;
        }
    }
}
