/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.web;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;


import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.AmUtilService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.asset.product.service.ProductSeriesService;

/**
 * 商品系列表Controller
 * @author Scarlett
 * @version 2018-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productSeries")
public class ProductSeriesController extends BaseController {

	@Autowired
	private ProductSeriesService productSeriesService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private TbProductService tbProductService;
	private String bizType="productSeries_image";

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductSeries get(String proseriesCode, boolean isNewRecord) {
		return productSeriesService.get(proseriesCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productSeries:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductSeries productSeries, Model model) {
		model.addAttribute("productSeries", productSeries);
		return "asset/product/productSeriesList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productSeries:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<ProductSeries> listData(ProductSeries productSeries) {
		if (StringUtils.isBlank(productSeries.getParentCode())) {
			productSeries.setParentCode(ProductSeries.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(productSeries.getProseriesStatus())){
			productSeries.setParentCode(null);
		}
		if (StringUtils.isNotBlank(productSeries.getSeriesName())){
			productSeries.setParentCode(null);
		}
		if (StringUtils.isNotBlank(productSeries.getStatus())){
			productSeries.setParentCode(null);
		}
		if (StringUtils.isNotBlank(productSeries.getRemarks())){
			productSeries.setParentCode(null);
		}
		List<ProductSeries> list = productSeriesService.findList(productSeries);
		for (int i =0;i<list.size();i++){
			ProductSeries productSeries1=list.get(i);
			//获取照片路径
			String imgPath=amUtilService.getImgPathAli(productSeries1.getProseriesCode(),bizType);
			productSeries1.setImgPath(imgPath);
		}
		return list;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productSeries:view")
	@RequestMapping(value = "form")
	public String form(ProductSeries productSeries, Model model) {
		// 创建并初始化下一个节点信息
		if(!productSeries.getIsNewRecord()){
			productSeries = createNextNode(productSeries);
		}
		if(productSeries.getIsNewRecord()){
			productSeries.setProseriesCode(UUID.randomUUID().toString());
		}
		model.addAttribute("productSeries", productSeries);
		return "asset/product/productSeriesForm";
	}

	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("product:productSeries:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public ProductSeries createNextNode(ProductSeries productSeries) {
		if (StringUtils.isNotBlank(productSeries.getParentCode())){
			productSeries.setParent(productSeriesService.get(productSeries.getParentCode()));
		}
		if (productSeries.getIsNewRecord()) {
			ProductSeries where = new ProductSeries();
			where.setParentCode(productSeries.getParentCode());
			ProductSeries last = productSeriesService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				productSeries.setTreeSort(last.getTreeSort() + 1);
				productSeries.setProseriesCode(IdGen.nextCode(last.getProseriesCode()));
			}else if (productSeries.getParent() != null){
				productSeries.setProseriesCode(productSeries.getParent().getProseriesCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (productSeries.getTreeSort() == null){
			productSeries.setTreeSort(1);
		}
		return productSeries;
	}

	/**
	 * 保存商品系列表
	 */
	@RequiresPermissions("product:productSeries:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductSeries productSeries) {
		productSeriesService.save(productSeries);
		return renderResult(Global.TRUE, "保存商品系列信息成功！");
	}

	/**
	 * 删除商品系列表
	 */
	@RequiresPermissions("product:productSeries:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductSeries productSeries) {
		Integer num=productSeriesService.selectByProseriesStatus(productSeries.getProseriesCode());
		TbProduct tbProduct=new TbProduct();
		tbProduct.setProseriesCode(productSeries.getProseriesCode());
		List<TbProduct> tbProducts=tbProductService.findList(tbProduct);
		if (ParamentUntil.isBackList(tbProducts) ){
			return renderResult(Global.FALSE, "删除失败！商品系列已存在商品信息中");
		}
		if(num>0){
			return renderResult(Global.FALSE, "删除失败！商品系列为已审核状态");
		}
		//删除时删除子数据
		ProductSeries productSeries1=new ProductSeries();
		productSeries1.setParentCodes(productSeries.getProseriesCode());
		List<ProductSeries> productSeriesList=productSeriesService.findList(productSeries1);
		if(ParamentUntil.isBackList(productSeriesList)){
			for (int i=0;i<productSeriesList.size();i++){
				//删除前判断商品系列已存在商品信息中
				TbProduct tbProduct1=new TbProduct();
				tbProduct1.setProseriesCode(productSeriesList.get(i).getProseriesCode());
				List<TbProduct> tbProductsL=tbProductService.findList(tbProduct1);
				Integer num1=productSeriesService.selectByProseriesStatus(productSeriesList.get(i).getProseriesCode());
				if (ParamentUntil.isBackList(tbProductsL)){
					return renderResult(Global.FALSE, "删除失败！商品系列已存在商品信息中");
				}
				if(num1>0){
					return renderResult(Global.FALSE, "该系列下存在已审核的商品系列，无法删除");
				}
			}
			for (int j=0;j<productSeriesList.size();j++){
				productSeriesService.delete(productSeriesList.get(j));
				//根据父节点去查上一级
				if(!("0".equals(productSeriesList.get(j).getParentCode()))){
					ProductSeries productSeries2=new ProductSeries();
					productSeries2.setProseriesCode(productSeriesList.get(j).getParentCode());
					productSeries2=productSeriesService.get(productSeries2);
					if (productSeries2!=null){
						//查询父节点下是还存在子节点
						ProductSeries productSeries3=new ProductSeries();
						productSeries3.setParentCodes(productSeries2.getProseriesCode());
						List<ProductSeries> productCategoryList1=productSeriesService.findList(productSeries3);

						if (productCategoryList1==null||productCategoryList1.size()<=0){
							//如果没有子节点跟新父节点的是否最末级
							productSeries2.setTreeLeaf("1");
							productSeriesService.save(productSeries2);
						}

					}
				}
			}
		}
		productSeriesService.delete(productSeries);
		//根据父节点去查上一级
		if(!("0".equals(productSeries.getParentCode()))){
			ProductSeries productSeries2=new ProductSeries();
			productSeries2.setProseriesCode(productSeries.getParentCode());
			productSeries2=productSeriesService.get(productSeries2);
			if (productSeries2!=null){
				ProductSeries productSeries3=new ProductSeries();
				productSeries3.setParentCodes(productSeries2.getProseriesCode());
				List<ProductSeries> productList=productSeriesService.findList(productSeries3);
				if (productList==null||productList.size()<=0){
					//如果没有子节点跟新父节点的是否最末级
					productSeries2.setTreeLeaf("1");
					productSeriesService.save(productSeries2);
				}

			}
		}
		return renderResult(Global.TRUE, "成功删除商品系列数据！");
	}


	/**
	 * 获取树结构数据
	 * @param excludeCode 排除的Code
	 * @param isShowCode 是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("product:productSeries:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<ProductSeries> list = productSeriesService.findList(new ProductSeries());
		for (int i=0; i<list.size(); i++){
			ProductSeries e = list.get(i);
			// 过滤非正常的数据
			if (!ProductSeries.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getProseriesCode(), e.getSeriesName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 *
	 * @param excludeCode
	 * @param isShowCode
	 * @return
	 * 父类未审核的话该父类下的所有子类
	 * 都不能选择（包括已审核和未审核的子类）），随后从已审核变成未审核的分类和系列不影响历史数据
	 */
	@RequiresPermissions("product:productSeries:view")
	@RequestMapping(value = "treeData1")
	@ResponseBody
	public List<Map<String, Object>> treeData1(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		ProductSeries productSeries = new ProductSeries();
		productSeries.setItem("treeData");
		List<ProductSeries> list = productSeriesService.findList(productSeries);
		for (int i=0; i<list.size(); i++){
			ProductSeries e = list.get(i);
			// 过滤非正常的数据
			if (!ProductSeries.STATUS_NORMAL.equals(e.getStatus())){
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)){
				if (e.getId().equals(excludeCode)){
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")){
					continue;
				}
			}
//			//剔除未审核状态的商品系列
//			if(e.getProseriesStatus().equals("1")){
//				continue;
//			}
//			boolean flag=true;
//			//剔除父类为未审核状态的商品分类
//			if(!e.getParentCode().equals("0")){
//				String str[]=e.getParentCodes().split(",");
//				for(int k=1;k<str.length;k++){
//					ProductSeries productSeries=new ProductSeries();
//					productSeries.setProseriesCode(str[k]);
//					productSeries=productSeriesService.get(productSeries);
//					if(productSeries!=null &&productSeries.getProseriesStatus().equals("1")){
//						flag=false;
//						break;
//					}
//				}
//			}
//			if(!flag){
//				continue;
//			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getProseriesCode(), e.getSeriesName()));
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("product:productSeries:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(ProductSeries productSeries){
		if (!UserUtils.getUser().isAdmin()){
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		productSeriesService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}
    /**
     * 查询列表数据
     */
    @RequiresPermissions("product:productCategory:view")
    @RequestMapping(value = "listTreeData")
    @ResponseBody
    public List<ProductSeries> listTreeData(ProductSeries productSeries) {
        Predicate<? super ProductSeries> predicate = a -> a.getParentCode().equals(ProductSeries.ROOT_CODE);
        if (StringUtils.isBlank(productSeries.getParentCode())) {
            productSeries.setParentCode(ProductSeries.ROOT_CODE);
        } else predicate = a -> a.getParentCode().equals(productSeries.getParentCode());
        if (StringUtils.isNotBlank(productSeries.getProseriesStatus())) {
            productSeries.setParentCode(null);
        }
        if (StringUtils.isNotBlank(productSeries.getSeriesName())) {
            productSeries.setParentCode(null);
        }
        if (StringUtils.isNotBlank(productSeries.getStatus())) {
            productSeries.setParentCode(null);
        }
        if (StringUtils.isNotBlank(productSeries.getRemarks())) {
            productSeries.setParentCode(null);
        }
        List<ProductSeries> list = productSeriesService.findList(new ProductSeries());
        List<ProductSeries> result = list.stream().filter(predicate).collect(Collectors.toList());
        buildTree(list, result);
        return result;
    }
    private void buildTree(List<ProductSeries> list, List<ProductSeries> result){
        result.forEach(a->{
            List<ProductSeries> children = list.stream().filter(b->b.getParentCode().equals(a.getProseriesCode())).collect(Collectors.toList());
            buildTree(list, children);
            a.setChildList(children);
            String imgPath = amUtilService.getImgPathAli(a.getProseriesCode(), bizType);
            a.setImgPath(imgPath);
        });
    }
}