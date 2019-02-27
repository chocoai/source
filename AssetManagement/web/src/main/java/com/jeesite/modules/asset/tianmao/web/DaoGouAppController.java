package com.jeesite.modules.asset.tianmao.web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.entity.Page;
import com.jeesite.modules.asset.product.dao.ProductCategoryDao;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.asset.product.service.ProductSeriesService;
import com.jeesite.modules.asset.tianmao.entity.Series;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.ChineseAndEnglish;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "${adminPath}/tianmao/tbProduct")
public class DaoGouAppController {

    @Autowired
    private TbProductService tbProductService;
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductSeriesService productSeriesService;
    private static String REGEX_CHINESE = "[\u4e00-\u9fa5]";// 中文正则
    @Autowired
    private TbTianmaoItemsService tbTianmaoItemsService;
    /**
     * 查询列表数据
     */
//    @RequiresPermissions("tianmao:tbProduct:view")
    @RequestMapping(value = "query")
    @ResponseBody
    public ReturnInfo listData(TbProduct tbProduct, HttpServletRequest request, HttpServletResponse response) {
        tbProduct.setApproveStatus("onsale");
        tbProduct.setQueryNick("saladliang");
        Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct);
        List<TbProduct> list = page.getList();
        if (list.size() == 0) {
            return ReturnDate.success(10000, "未找到符合的商品", "");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("count", page.getCount());
        jsonObject.put("pageNo", page.getPageNo());
        jsonObject.put("pageSize", page.getPageSize());

        JSONArray jsonArray = new JSONArray();

        for (TbProduct aList : list) {
            List<TbSku> tbSkuList = productCategoryDao.findSkuList(aList.getNumIid());
            if (tbSkuList.size() == 0) {
                continue;
            }
            String distrPicUrl =  tbTianmaoItemsService.getLastImg(aList.getNumIid());
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("numIid", aList.getNumIid());
            jsonObject1.put("picUrl", aList.getPicUrl());
            jsonObject1.put("title", aList.getTitle());
            jsonObject1.put("distrPicUrl", distrPicUrl);
            Double[] prices = new Double[tbSkuList.size()];
            Double[] lowerDistrPrice = new Double[tbSkuList.size()];
            for (int m = 0; m < tbSkuList.size(); m++) {
                if (StringUtils.isEmpty(tbSkuList.get(m).getSkuUrl())) {
                    tbSkuList.get(m).setSkuUrl(distrPicUrl);
                }
                prices[m] = Double.valueOf(tbSkuList.get(m).getRealPrice());
                lowerDistrPrice[m] = Double.valueOf(tbSkuList.get(m).getDistributionPrice());
            }
            Arrays.sort(prices);
            Arrays.sort(lowerDistrPrice);
            jsonObject1.put("lowestPrice", prices[0]);
            jsonObject1.put("lowerDistrPrice", lowerDistrPrice[0]);
            jsonObject1.put("nick", aList.getNick());
            jsonObject1.put("approveStatus", aList.getApproveStatus());
            jsonObject1.put("skuList", tbSkuList);
            jsonArray.add(jsonObject1);
        }
        jsonObject.put("list", jsonArray);
        return ReturnDate.success(200, "请求成功", jsonObject);
    }

    /**
     * 商品搜索页关键词联想接口
     * @param seriesName
     * @return
     */
    @RequiresPermissions("tianmao:tbProduct:view")
    @RequestMapping("querySeries")
    @ResponseBody
    public ReturnInfo series(String seriesName) {
        // 判断传入的值是否为空
        if ("".equals(seriesName) || seriesName == null) {
            return ReturnDate.error(10001, "输入为空");
        } else {
            // 判断是否为英文
            boolean flag = ChineseAndEnglish.isEnglish(seriesName);
            if (flag) {
                // 把传入的英文转化为小写，因为sql中把数据也相应转成小写匹配
                seriesName = seriesName.toLowerCase();
                // 查询是否有符合条件的数据
                List<ProductSeries> seriesList = productSeriesService.selectByName(seriesName);
                if (seriesList != null && seriesList.size() >0) {
                    // 中文的正则表达式
                    Pattern pat = Pattern.compile(REGEX_CHINESE);
                    List<Series> productSeriesList = new ArrayList<>();
                    for (ProductSeries productSeries : seriesList) {
                        Series series = new Series();
                        // 获取的二级系列名去掉中文
                        Matcher mat = pat.matcher(productSeries.getSeriesName());
                        series.setSeriesName(mat.replaceAll(""));
                        productSeriesList.add(series);
                    }
                    return ReturnDate.success(200, "请求成功" , productSeriesList);
                } else {
                    return ReturnDate.error(10002, "没有符合条件的系列");
                }
            } else {
                return ReturnDate.error(10003, "输入的值不合法");
            }
        }
    }

}
