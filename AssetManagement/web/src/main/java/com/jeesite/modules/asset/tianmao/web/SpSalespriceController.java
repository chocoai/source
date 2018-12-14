/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.k3webapi.InvokeHelper;
import com.jeesite.modules.asset.k3webapi.K3connection;
import com.jeesite.modules.asset.tianmao.entity.SpSelling;
import com.jeesite.modules.asset.tianmao.service.SpSellingService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.YzyGetToken;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.youzan.open.sdk.api.API;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.gen.v3_0_0.api.*;
import com.youzan.open.sdk.gen.v3_0_0.model.*;
import com.youzan.open.sdk.model.ByteWrapper;
import com.youzan.open.sdk.util.json.JsonUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.beetl.ext.simulate.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.tianmao.entity.SpSalesprice;
import com.jeesite.modules.asset.tianmao.service.SpSalespriceService;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 商品销售价目Controller
 *
 * @author dwh
 * @version 2018-08-16
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/spSalesprice")
public class SpSalespriceController extends BaseController {

    @Autowired
    private SpSalespriceService spSalespriceService;
    @Autowired
    private K3connection k3connection;
    @Autowired
    private SpSellingService spSellingService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public SpSalesprice get(String tableCode, boolean isNewRecord) {
        return spSalespriceService.get(tableCode, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("tianmao:spSalesprice:view")
    @RequestMapping(value = {"list", ""})
    public String list(SpSalesprice spSalesprice, Model model) {
        model.addAttribute("spSalesprice", spSalesprice);
        return "asset/tianmao/spSalespriceList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("tianmao:spSalesprice:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<SpSalesprice> listData(SpSalesprice spSalesprice, HttpServletRequest request, HttpServletResponse response) {
        Page<SpSalesprice> page = spSalespriceService.findPage(new Page<SpSalesprice>(request, response), spSalesprice);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("tianmao:spSalesprice:view")
    @RequestMapping(value = "form")
    public String form(SpSalesprice spSalesprice, Model model) {
        model.addAttribute("spSalesprice", spSalesprice);
        return "asset/tianmao/spSalespriceForm";
    }

    /**
     * 保存商品销售价目
     */
    @RequiresPermissions("tianmao:spSalesprice:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated SpSalesprice spSalesprice) {
        spSalespriceService.save(spSalesprice);
        return renderResult(Global.TRUE, "保存商品销售价目成功！");
    }

    /**
     * 删除商品销售价目
     */
    @RequiresPermissions("tianmao:spSalesprice:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(SpSalesprice spSalesprice) {
        spSalespriceService.delete(spSalesprice);
        return renderResult(Global.TRUE, "删除商品销售价目成功！");
    }

    @RequestMapping(value = "synSpPirce")
    @ResponseBody
    public ReturnInfo synSpPirce() {
        if (!k3connection.getConnection()) {
            return ReturnDate.error(10001, "k3登录失败");
        }
        try {
            spSalespriceService.synSpPirce();
        } catch (Exception e) {
            return ReturnDate.error(-1, "同步失败，出现异常");
        }
        return ReturnDate.success();
    }

    //	Collections.max(list)取集合中的最大值
//  Collections.min(list)
    @RequestMapping(value = "synSpPirceToYzy")
    @ResponseBody
    public ReturnInfo synSpPirceToYzy() {
        //获取k3拉下来的数据
        List<SpSalesprice> spSalesprices = spSalespriceService.getAllK3Info();
        if (!ParamentUntil.isBackList(spSalesprices)) {
            return ReturnDate.error(10009, "k3销售价目表为空");
        }
        //获取有赞云拉下来的数据
        List<SpSelling> spSellings = spSellingService.getAllYzyInfo();
        if (!ParamentUntil.isBackList(spSellings)) {
            return ReturnDate.error(10009, "有赞云商品为空");
        }
        try {
            spSalespriceService.synSpPirceToYzy(spSalesprices, spSellings);
        }catch (Exception e) {
            e.printStackTrace();
            return ReturnDate.error(-1,"同步异常，未知错误");
        }
        return ReturnDate.success();
    }


    //测试接口
    @RequestMapping(value = "isGood")
    @ResponseBody
    public ReturnInfo isGood() {
        String token= YzyGetToken.getToken();
        YZClient client = new DefaultYZClient(new Token(token)); //new Sign(appKey, appSecret)
        //获取单个商品
        YouzanItemGetParams youzanItemGetParams = new YouzanItemGetParams();
//        youzanItemGetParams.setAlias("1y8vmov2k5wgy");
        youzanItemGetParams.setItemId((long) 435789622);
        YouzanItemGet youzanItemGet = new YouzanItemGet();
        youzanItemGet.setAPIParams(youzanItemGetParams);
        String result = client.execute(youzanItemGet);
        System.out.println("获取单个商品1");

        //获取出售中的商品列表
        YouzanItemsOnsaleGetParams youzanItemsOnsaleGetParams = new YouzanItemsOnsaleGetParams();
        youzanItemsOnsaleGetParams.setPageNo(1L);
        youzanItemsOnsaleGetParams.setPageSize(10L);
        youzanItemsOnsaleGetParams.setOrderBy("created_time");
//        youzanItemsOnsaleGetParams.setQ();
//        youzanItemsOnsaleGetParams.setTagId();
//        youzanItemsOnsaleGetParams.setUpdateTimeEnd();
//        youzanItemsOnsaleGetParams.setUpdateTimeStart();
        YouzanItemsOnsaleGet youzanItemsOnsaleGet = new YouzanItemsOnsaleGet();
        youzanItemsOnsaleGet.setAPIParams(youzanItemsOnsaleGetParams);
        String result1 = client.execute(youzanItemsOnsaleGet);
        System.out.println("获取出售中的商品列表2"+result1.toString());
        return ReturnDate.success(JsonUtils.toJson(result));
    }
    //测试接口
    @RequestMapping(value = "add")
    @ResponseBody
    public ReturnInfo add() {
        String token= YzyGetToken.getToken();
        YZClient client = new DefaultYZClient(new Token(token)); //new Sign(appKey, appSecret)
        YouzanItemCreateParams youzanItemCreateParams = new YouzanItemCreateParams();
        youzanItemCreateParams.setAutoListingTime((long) 0);
        youzanItemCreateParams.setCid((long) 7000000);
        youzanItemCreateParams.setTitle("测试新增价格");
        youzanItemCreateParams.setPrice(888888L);
        youzanItemCreateParams.setImageIds("1180889984");
        youzanItemCreateParams.setDesc("测试新增数据");
        youzanItemCreateParams.setEtdEnd("2019-01-10 08:00:00");
        youzanItemCreateParams.setEtdStart("2019-01-10 08:00:00");
        youzanItemCreateParams.setEtdType((long) 1);
        youzanItemCreateParams.setHideStock((long) 0);
        youzanItemCreateParams.setHotelExtra( "{ 'service_tel_code':'0571', 'service_tel':'15873465905' }");
        youzanItemCreateParams.setIsDisplay((long) 0);
        youzanItemCreateParams.setItemNo("6933285600396");
        youzanItemCreateParams.setItemType((long) 60);
        youzanItemCreateParams.setJoinLevelDiscount((long) 2);
        youzanItemCreateParams.setMessages("");
        youzanItemCreateParams.setOriginPrice(String.valueOf(199999));
        youzanItemCreateParams.setPostFee((long) 188);
        youzanItemCreateParams.setPreSale(false);
        youzanItemCreateParams.setPrice((long) 89899);
        youzanItemCreateParams.setPurchaseRight(false);
        youzanItemCreateParams.setQuantity((long) 0);
        youzanItemCreateParams.setSkuImages("[{'v':'22','img_url':'https://img.yzcdn.cn/upload_files/2016/09/24/1a6004ee7c5ecd970affba1999c7e0e1.jpg'}]");
        youzanItemCreateParams.setSkuStocks("[{'sku_id':3337,'code':'sdsfdsfs','price':8880000,'quantity':10,'skus':[{'k':'颜色','kid':1,'v':'22','vid':1196}]}]");
//        youzanItemCreateParams.setAutoListingTime(332222L);

        YouzanItemCreate youzanItemCreate = new YouzanItemCreate();
        youzanItemCreate.setAPIParams(youzanItemCreateParams);
        String result1 = client.execute(youzanItemCreate);
//        YouzanItemCreateResult result = client.invoke(youzanItemCreate);
        System.out.println("添加商品成功");

        return ReturnDate.success(result1);
    }

    //测试接口
    @RequestMapping(value = "uploadImg")
    @ResponseBody
    public ReturnInfo uploadImg(MultipartFile file) {

        String imgFile = "D:\\img\\amspecimen\\2018\\08\\07\\7bc20068e4fec7446026d0f0082045c1.jpg";
        File f = new File(imgFile);
        ByteWrapper bytes=null;
        List<ByteWrapper> byteWrappersList = new ArrayList<ByteWrapper>();
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);  //经测试转换的图片是格式这里就什么格式，否则会失真
            bytes= new ByteWrapper(imgFile);
            byteWrappersList.add(bytes);
             BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String token= YzyGetToken.getToken();
        YZClient client = new DefaultYZClient(new Token(token)); //new Sign(appKey, appSecret)
        //获取单个商品
        YouzanMaterialsStoragePlatformImgUploadParams youzanMaterialsStoragePlatformImgUploadParams = new YouzanMaterialsStoragePlatformImgUploadParams();
        youzanMaterialsStoragePlatformImgUploadParams.setImage(byteWrappersList.toArray(new ByteWrapper[byteWrappersList.size()]));
        YouzanMaterialsStoragePlatformImgUpload youzanMaterialsStoragePlatformImgUpload = new YouzanMaterialsStoragePlatformImgUpload();
        youzanMaterialsStoragePlatformImgUpload.setAPIParams(youzanMaterialsStoragePlatformImgUploadParams);
        String result = client.execute(youzanMaterialsStoragePlatformImgUpload);
        return ReturnDate.success(result);
    }

    //测试接口
    @RequestMapping(value = "update")
    @ResponseBody
    public ReturnInfo update() {
        String token= YzyGetToken.getToken();
        YZClient client = new DefaultYZClient(new Token(token)); //new Sign(appKey, appSecret)
        YouzanItemUpdateParams youzanItemUpdateParams = new YouzanItemUpdateParams();
        youzanItemUpdateParams.setAutoListingTime((long) 0);
        youzanItemUpdateParams.setCid((long) 8000000);
        youzanItemUpdateParams.setTitle("测试修改价格");
        youzanItemUpdateParams.setPrice((long) 888888);
        youzanItemUpdateParams.setImageIds("1181212061");
        youzanItemUpdateParams.setDesc("测试修改数据");
        youzanItemUpdateParams.setEtdEnd("2019-01-10 08:00:00");
        youzanItemUpdateParams.setEtdStart("2019-01-10 08:00:00");
        youzanItemUpdateParams.setEtdType((long) 1);
        youzanItemUpdateParams.setHideStock((long) 0);
        youzanItemUpdateParams.setHotelExtra( "{ 'service_tel_code':'0571', 'service_tel':'15873465905' }");
        youzanItemUpdateParams.setIsDisplay((long) 0);
        youzanItemUpdateParams.setIsUsed(false);
        youzanItemUpdateParams.setItemId((long) 435789622);
        youzanItemUpdateParams.setItemNo("6933285600396");
        youzanItemUpdateParams.setMessages("[{'messages':'测试数据无法购买'}]");
        youzanItemUpdateParams.setJoinLevelDiscount((long) 2);
        youzanItemUpdateParams.setOriginPrice(String.valueOf(399999));
        youzanItemUpdateParams.setPostFee((long) 189);
        youzanItemUpdateParams.setPreSale(false);
        youzanItemUpdateParams.setPrice((long) 89899);
        youzanItemUpdateParams.setPurchaseRight(false);
        youzanItemUpdateParams.setQuantity((long) 0);
        youzanItemUpdateParams.setRemoveImageIds(String.valueOf(1180544219));
        youzanItemUpdateParams.setSkuImages("[{'v':'22','img_url':'https://img.yzcdn.cn/upload_files/2016/09/24/1a6004ee7c5ecd970affba1999c7e0e1.jpg'}]");
        youzanItemUpdateParams.setSkuStocks("[{'sku_id':3337,'code':'sdsfdsfs','price':8880000,'quantity':10,'skus':[{'k':'颜色','kid':1,'v':'22','vid':1196}]}]");
//        youzanItemCreateParams.setAutoListingTime(332222L);

        YouzanItemUpdate youzanItemUpdate = new YouzanItemUpdate();
        youzanItemUpdate.setAPIParams(youzanItemUpdateParams);
        String result = client.execute( youzanItemUpdate);
//        YouzanItemCreateResult result = client.invoke(youzanItemCreate);
        System.out.println("添加商品成功");

        return ReturnDate.success(result);
    }

}