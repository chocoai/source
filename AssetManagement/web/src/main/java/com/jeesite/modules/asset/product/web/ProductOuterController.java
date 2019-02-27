package com.jeesite.modules.asset.product.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.product.dao.ProductCategoryDao;
import com.jeesite.modules.asset.product.dao.ProductSeriesDao;
import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.asset.product.service.ProductCategoryService;
import com.jeesite.modules.asset.product.service.ProductSeriesService;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import com.jeesite.modules.asset.tianmao.service.TbTianmaoItemsService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.util.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/product/productOuter")
public class ProductOuterController extends BaseController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductSeriesService productSeriesService;
    @Autowired
    private AmUtilService amUtilService;
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductSeriesDao productSeriesDao;
    //商品分类图片BizType
    private String categoryBizType="productCategory_image";
    //商品系列图片BizType
    private String seriesBizType="productSeries_image";
    @Autowired
    private TbTianmaoItemsService tbTianmaoItemsService;


    /**
     * 搜索页的默认商品列表：
     * 商品分类中一级分类需序号=1的所有二级分类的商品，数据包括：商品id ,商品主图，商品名称，商品最低价；
     */
//   @RequiresPermissions("sale:saleReception:view")
//    @PostMapping(value = "default")
//    @ResponseBody
    public ReturnInfo defaultProductList(int pageNo,HttpServletRequest request, HttpServletResponse response){
        //获取一级分类序号=1的商品分类信息
        try {
            int count = 0;
            List<ProductCategory> categoryList=productCategoryService.findListOuter();
            JSONArray jsonArray=new JSONArray();
            JSONObject jsonObject = new JSONObject();
            if(categoryList!=null &&categoryList.size()>0){
                for(int i=0;i<categoryList.size();i++){
                    ProductCategory productCategory1=new ProductCategory();
                    productCategory1.setParentCode(categoryList.get(i).getProcategoryCode());
                    //获取一级分类序号=1的商品分类信息的二级分类信息
                    List<ProductCategory> plist=productCategoryService.findList(productCategory1);
                    if(plist!=null &&plist.size()>0){
                        for(int j=0;j<plist.size();j++){
                            TbProduct tbProduct=new TbProduct();
                            tbProduct.setProcategoryCode(plist.get(j).getProcategoryCode());
                            //获取二级商品分类对应的商品信息
//                         Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct);
//                         List<TbProduct> tbProducts = page.getList();
                            int start = (pageNo-1)*50;
                            List<TbProduct> tbProducts = productCategoryDao.findProductListByCode(plist.get(j).getProcategoryCode(),start,50);
                            int count1 = productCategoryDao.findProductCountByCode(plist.get(j).getProcategoryCode());
//                         jsonObject.put("count",count1);
                            jsonObject.put("pageNo",pageNo);
                            if(tbProducts!=null &&tbProducts.size()>0){
                                count+=count1;
                                jsonObject.put("pageNo",pageNo);
                                JSONObject jsonObject1=null;
                                for(int k=0;k<tbProducts.size();k++){
                                    tbProduct=tbProducts.get(k);

                                    List<TbSku> tbSkuList = productCategoryDao.findSkuList(tbProduct.getNumIid());
                                    if( tbSkuList!=null&&tbSkuList.size()>0 ){
                                        jsonObject1=new JSONObject();
                                        jsonObject1.put("numIid",tbProduct.getNumIid());
                                        jsonObject1.put("picUrl",tbProduct.getPicUrl());
                                        jsonObject1.put("title",tbProduct.getTitle());
                                        jsonObject1.put("approveStatus", tbProduct.getApproveStatus());
                                        jsonObject1.put("nick", tbProduct.getNick());
                                        // 分销系统商品主图用 查出来的图片是一样的 所以取第一个就可以了
                                        String distrPicUrl =  tbTianmaoItemsService.getLastImg(tbProduct.getNumIid());
                                        jsonObject1.put("distrPicUrl", distrPicUrl);
                                        jsonObject1.put("lowerDistrPrice", tbProduct.getLowerDistrPrice());
                                        //此行代码测试库用
//                                     jsonObject1.put("lowestPrice", tbProduct.getPrice());
                                        //获取商品对应的多个SKU中真实售价最低值
                                        Double prices[]=new Double[tbSkuList.size()];
                                        for(int m=0;m<tbSkuList.size();m++){
                                            if (StringUtils.isEmpty(tbSkuList.get(m).getSkuUrl())) {
                                                tbSkuList.get(m).setSkuUrl(distrPicUrl);
                                            }
                                            prices[m]=Double.valueOf(tbSkuList.get(m).getRealPrice());
                                        }
                                        Arrays.sort(prices);
                                        jsonObject1.put("lowestPrice",prices[0]);

                                        jsonObject1.put("skuList", tbSkuList);
                                        jsonArray.add(jsonObject1);
                                    }
                                }
                            }
                        }
                    }
                }
                if(jsonArray==null ||jsonArray.size()<=0){
                    return ReturnDate.success("暂无商品信息");
                }
                jsonObject.put("count",count);
                jsonObject.put("list",jsonArray);
                return ReturnDate.success(jsonObject);
            }else{
                return ReturnDate.success("暂无商品信息");
            }
        } catch (NumberFormatException e) {
            return ReturnDate.error(400,"请求失败");
        }
    }

    /**
     * 前台请求商品分类数据：
     * @return 商品分类和商品系列表的数据，一级分类id ,一级分类名称，序号，二级分类id ,二级分类名称，序号，图片；
     * 按序号从小到大排序返回给前台；先返回商品分类表，再返回商品系列表；
     * 商品获取接口增加一个效验条件，商品分类状态=已审核（注意层级关系，必须一二级商品分类状态=已审核）
     * 同时还要考虑商品系列也要==已审核；而且前端商品分类中显示的分类和系列也要==已审核，未审核状态不显示分类和系列
     */
    @RequiresPermissions(value = {"sale:saleReception:view", "distribution:api"}, logical = Logical.OR)
    @PostMapping(value = "product")
    @ResponseBody
    public ReturnInfo product() {
        try {
            JSONObject total=new JSONObject();
            JSONArray jsonArray=new JSONArray();
            ProductCategory productCategory=new ProductCategory();
            productCategory.setParentCode("0");
            productCategory.setProcategoryStatus("0");
            //获取已审核状态的一级商品分类
            List<ProductCategory> products=productCategoryService.findList(productCategory);
            if(products!=null &&products.size()>0){
                JSONObject jsonObject=null;
                ProductCategory productCategory1=null;
                for(int i=0;i<products.size();i++){
                    productCategory1=products.get(i);
                    jsonObject=new JSONObject();
                    jsonObject.put("id",productCategory1.getProcategoryCode());
                    jsonObject.put("categoryName",productCategory1.getCategoryName());
                    jsonObject.put("treeSort",productCategory1.getTreeSort());
                    ProductCategory productCategory2=new ProductCategory();
                    //获取一级商品系列对应的已审核状态的二级商品分类
                    productCategory2.setParentCode(productCategory1.getProcategoryCode());
                    productCategory2.setProcategoryStatus("0");
                    List<ProductCategory> list=productCategoryService.findList(productCategory2);
                    JSONArray jsonArray1=null;
                    if(list!=null && list.size()>0){
                        jsonArray1=new JSONArray();
                        for(int j=0;j<list.size();j++){
                            ProductCategory productCategory3=list.get(j);
                            JSONObject jsonObject1=new JSONObject();
                            jsonObject1.put("id",productCategory3.getProcategoryCode());
                            jsonObject1.put("categoryName",productCategory3.getCategoryName());
                            jsonObject1.put("treeSort",productCategory3.getTreeSort());
                            //商品分类阿里云路径
                            String imgPath=amUtilService.getImgPathAli(productCategory3.getProcategoryCode(),categoryBizType);
                            if(null==imgPath){
                                imgPath="";
                            }
                            jsonObject1.put("imgPath",imgPath);
                            jsonArray1.add(jsonObject1);
                        }
                    }
                    if(jsonArray1!=null &&jsonArray1.size()>0) {
                        jsonObject.put("children", jsonArray1);
                    }
                    jsonArray.add(jsonObject);
                }
                total.put("products",jsonArray);
            }
            ProductSeries productSeries=new ProductSeries();
            productSeries.setParentCode("0");
            productSeries.setProseriesStatus("0");
            //获取已审核状态的二级商品系列
            List<ProductSeries> productSeriesList=productSeriesService.findList(productSeries);
            JSONArray series=new JSONArray();

            if(productSeriesList!=null &&productSeriesList.size()>0){
                for(int i=0;i<productSeriesList.size();i++){
                    JSONObject jsonObject=new JSONObject();
                    ProductSeries productSeries1=productSeriesList.get(i);
                    jsonObject.put("id",productSeries1.getProseriesCode());
                    jsonObject.put("seriesName",productSeries1.getSeriesName());
                    jsonObject.put("treeSort",productSeries1.getTreeSort());
                    ProductSeries productSeries2=new ProductSeries();
                    productSeries2.setParentCode(productSeries1.getProseriesCode());
                    productSeries2.setProseriesStatus("0");
                    //获取一级商品系列对应的已审核状态的二级商品系列
                    List<ProductSeries> list=productSeriesService.findList(productSeries2);
                    JSONArray jsonArray1= new JSONArray();
                    if(list!=null &&list.size()>0){
                        for(int j=0;j<list.size();j++){
                            ProductSeries productSeries3=list.get(j);
                            JSONObject jsonObject1=new JSONObject();
                            jsonObject1.put("id",productSeries3.getProseriesCode());
                            jsonObject1.put("seriesName",productSeries3.getSeriesName());
                            jsonObject1.put("treeSort",productSeries3.getTreeSort());
                            //图片阿里云路径
                            String imgPath=amUtilService.getImgPathAli(productSeries3.getProseriesCode(),seriesBizType);
                            //图片相对路径
                            if(imgPath==null){
                                imgPath="";
                            }
                            jsonObject1.put("imgPath",imgPath);
                            jsonArray1.add(jsonObject1);
                        }
                    }
                    if(jsonArray1!=null &&jsonArray1.size()>0) {
                        jsonObject.put("children", jsonArray1);
                    }
                    series.add(jsonObject);
                }
                total.put("series",series);
            }
            if(!total.containsKey("series") &&!total.containsKey("products")){
                return ReturnDate.success("暂无商品分类/商品系列信息");
            }
            return ReturnDate.success(total);
        } catch (Exception e) {
            return ReturnDate.error(400,"请求失败");
        }
    }


    /**
     *前台传 二级分类id ;
     *
     * 后台返回 商品资料中商品的商品分类/商品系列为该分类的商品，数据包括：商品id ,商品主图，商品名称，商品最低价；
     * @return
     */
    @RequiresPermissions(value = {"tianmao:tbProduct:view", "distribution:api"}, logical = Logical.OR)
    @PostMapping(value = "secondProduct")
    @ResponseBody
    public ReturnInfo secondProduct(@RequestParam(name = "id", required = false) String id ,@RequestParam(name = "pageNo", required = false) int pageNo,HttpServletRequest request, HttpServletResponse response) {

        try {
            String code =id;
            //code为null默认返回商品分类中一级分类需序号=1的所有二级分类的商品
            if(code==null || "".equals(code)){
                return defaultProductList(pageNo,request,response);
            }
            ProductCategory productCategory = new ProductCategory();
            ProductSeries productSeries=new ProductSeries();
            productCategory.setProcategoryCode(code);
            productSeries.setProseriesCode(code);
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject1 = null;
            TbProduct tbProduct = null;
            ProductCategory productCategory1 = productCategoryService.get(productCategory);
            if (productCategoryService.get(productCategory) != null) {
                String[] strings = productCategory1.getParentCodes().split(",");
                if(strings.length!=2){
                    return ReturnDate.success("该分类不属于二级分类");
                }
                TbProduct tbProduct1 = new TbProduct();
                tbProduct1.setProcategoryCode(code);
//                Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct1);
                int start = (pageNo-1)*50;
                List<TbProduct> list = productCategoryDao.findProductListByCode(code,start,50);
                int count = productCategoryDao.findProductCountByCode(code);
                jsonObject.put("count",count);
                jsonObject.put("pageNo",pageNo);
//                List<TbProduct> list = page.getList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        tbProduct = list.get(i);
                        List<TbSku> tbSkuList = productCategoryDao.findSkuList(tbProduct.getNumIid());
                        if(tbSkuList != null && tbSkuList.size() > 0){
                            jsonObject1 = new JSONObject();
                            jsonObject1.put("numIid", tbProduct.getNumIid());
                            jsonObject1.put("picUrl", tbProduct.getPicUrl());
                            jsonObject1.put("title", tbProduct.getTitle());
                            jsonObject1.put("approveStatus", tbProduct.getApproveStatus());
                            jsonObject1.put("nick",tbProduct.getNick());
                            jsonObject1.put("lowerDistrPrice", tbProduct.getLowerDistrPrice());
                            String distrPicUrl =  tbTianmaoItemsService.getLastImg(tbProduct.getNumIid());
                            jsonObject1.put("distrPicUrl", distrPicUrl);
                            //此行代码测试库用
//                            jsonObject1.put("lowestPrice", tbProduct.getPrice());
                            Double prices[] = new Double[tbSkuList.size()];
                            for (int j = 0; j < tbSkuList.size(); j++) {
                                if (StringUtils.isEmpty(tbSkuList.get(j).getSkuUrl())) {
                                    tbSkuList.get(j).setSkuUrl(distrPicUrl);
                                }
                                prices[j] = Double.valueOf(tbSkuList.get(j).getRealPrice());
                            }
                            Arrays.sort(prices);
                            jsonObject1.put("lowestPrice", prices[0]);

                            jsonObject1.put("skuList",tbSkuList);
                            jsonArray.add(jsonObject1);
                        }
                    }
                } else {
                    return ReturnDate.success("暂无属于该商品分类的商品");
                }
                jsonObject.put("list",jsonArray);
                return ReturnDate.success(jsonObject);
            } else if (productSeriesService.get(productSeries) != null) {
                TbProduct tbProduct1 = new TbProduct();
                tbProduct1.setProseriesCode(code);
//                Page<TbProduct> page = tbProductService.findPage(new Page<TbProduct>(request, response), tbProduct1);
                int start = (pageNo-1)*50;
                List<TbProduct> list = productSeriesDao.findProductListByCode(code,start,50);
                int count = productSeriesDao.findProductCountByCode(code);
                jsonObject.put("count",count);
                jsonObject.put("pageNo",pageNo);
//                List<TbProduct> list = page.getList();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        tbProduct = list.get(i);
                        List<TbSku> tbSkuList = productCategoryDao.findSkuList(tbProduct.getNumIid());
                        if(tbSkuList!=null&&tbSkuList.size() > 0){
                            jsonObject1 = new JSONObject();
                            jsonObject1.put("numIid", tbProduct.getNumIid());
                            jsonObject1.put("picUrl", tbProduct.getPicUrl());
                            jsonObject1.put("title", tbProduct.getTitle());
                            jsonObject1.put("approveStatus", tbProduct.getApproveStatus());
                            jsonObject1.put("nick",tbProduct.getNick());
                            // 分销系统商品主图用 查出来的图片是一样的 所以取第一个就可以了
                            String distrPicUrl =  tbTianmaoItemsService.getLastImg(tbProduct.getNumIid());
                            jsonObject1.put("distrPicUrl", distrPicUrl);
                            jsonObject1.put("lowerDistrPrice", tbProduct.getLowerDistrPrice());
                            //此行代码测试库用
//                            jsonObject1.put("lowestPrice", tbProduct.getPrice());
                            Double prices[] = new Double[tbSkuList.size()];
                            for (int j = 0; j < tbSkuList.size(); j++) {
                                if (StringUtils.isEmpty(tbSkuList.get(j).getSkuUrl())) {
                                    tbSkuList.get(j).setSkuUrl(distrPicUrl);
                                }
                                prices[j] = Double.valueOf(tbSkuList.get(j).getRealPrice());
                            }
                            Arrays.sort(prices);
                            jsonObject1.put("lowestPrice", prices[0]);

                            jsonObject1.put("skuList", tbSkuList);
                            jsonArray.add(jsonObject1);
                        }
                    }
                } else {
                    return ReturnDate.success("暂无属于该商品系列的商品");
                }
                jsonObject.put("list",jsonArray);
                return ReturnDate.success(jsonObject);
            } else {
                return ReturnDate.success("暂无属于该商品系列的商品");
            }
        }catch (Exception e){
            e.printStackTrace();
            return  ReturnDate.error(400,"请求失败");
        }
    }

}
