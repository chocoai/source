/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.product.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.service.TbProductService;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.asset.util.service.AmUtilService;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.modules.sys.utils.UserUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.product.service.ProductCategoryService;

/**
 * 商品分类表Controller
 * @author Scarlett
 * @version 2018-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/product/productCategory")
public class ProductCategoryController extends BaseController {

	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private AmUtilService amUtilService;
	@Autowired
	private TbProductService tbProductService;
	private String bizType = "productCategory_image";
	@Autowired
	private FileUploadService fileUploadService;
	@Value("${file.baseDir}")
	private String  baseDir;

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public ProductCategory get(String procategoryCode, boolean isNewRecord) {
		return productCategoryService.get(procategoryCode, isNewRecord);
	}

	/**
	 * 查询列表
	 */
	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ProductCategory productCategory, Model model) {
		model.addAttribute("productCategory", productCategory);
		return "asset/product/productCategoryList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public List<ProductCategory> listData(ProductCategory productCategory) {
		if (StringUtils.isBlank(productCategory.getParentCode())) {
			productCategory.setParentCode(ProductCategory.ROOT_CODE);
		}
		if (StringUtils.isNotBlank(productCategory.getProcategoryStatus())) {
			productCategory.setParentCode(null);
		}
		if (StringUtils.isNotBlank(productCategory.getCategoryName())) {
			productCategory.setParentCode(null);
		}
		if (StringUtils.isNotBlank(productCategory.getStatus())) {
			productCategory.setParentCode(null);
		}
		if (StringUtils.isNotBlank(productCategory.getRemarks())) {
			productCategory.setParentCode(null);
		}
		List<ProductCategory> list = productCategoryService.findList(productCategory);
		for (int i = 0; i < list.size(); i++) {
			ProductCategory productCategory1 = list.get(i);
			//获取照片路径
			String imgPath = amUtilService.getImgPathAli(productCategory1.getProcategoryCode(), bizType);
			productCategory1.setImgPath(imgPath);
		}
		return list;
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = "listTreeData")
	@ResponseBody
	public List<ProductCategory> listTreeData(ProductCategory productCategory) {
        Predicate<? super ProductCategory> predicate = a -> a.getParentCode().equals(ProductCategory.ROOT_CODE);
        if (StringUtils.isBlank(productCategory.getParentCode())) {
            productCategory.setParentCode(ProductCategory.ROOT_CODE);
        } else predicate = a -> a.getParentCode().equals(productCategory.getParentCode());
        if (StringUtils.isNotBlank(productCategory.getProcategoryStatus())) {
            productCategory.setParentCode(null);
        }
        if (StringUtils.isNotBlank(productCategory.getCategoryName())) {
            productCategory.setParentCode(null);
        }
        if (StringUtils.isNotBlank(productCategory.getStatus())) {
            productCategory.setParentCode(null);
        }
        if (StringUtils.isNotBlank(productCategory.getRemarks())) {
            productCategory.setParentCode(null);
        }
		List<ProductCategory> list = productCategoryService.findList(new ProductCategory());
        List<ProductCategory> result = list.stream().filter(predicate).collect(Collectors.toList());
        buildTree(list, result);
		return result;
	}
	private void buildTree(List<ProductCategory> list, List<ProductCategory> result){
        result.forEach(a->{
            List<ProductCategory> children = list.stream().filter(b->b.getParentCode().equals(a.getProcategoryCode())).collect(Collectors.toList());
            buildTree(list, children);
            a.setChildList(children);
            String imgPath = amUtilService.getImgPathAli(a.getProcategoryCode(), bizType);
            a.setImgPath(imgPath);
        });
    }

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = "form")
	public String form(ProductCategory productCategory, Model model) {
		// 创建并初始化下一个节点信息
		if (!productCategory.getIsNewRecord()) {
			productCategory = createNextNode(productCategory);
		}
		if(productCategory.getIsNewRecord()){
			productCategory.setProcategoryCode(UUID.randomUUID().toString());
		}

		model.addAttribute("productCategory", productCategory);
		return "asset/product/productCategoryForm";
	}

	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequiresPermissions("product:productCategory:edit")
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public ProductCategory createNextNode(ProductCategory productCategory) {
		if (StringUtils.isNotBlank(productCategory.getParentCode())) {
			productCategory.setParent(productCategoryService.get(productCategory.getParentCode()));
		}
		if (productCategory.getIsNewRecord()) {
			ProductCategory where = new ProductCategory();
			where.setParentCode(productCategory.getParentCode());
			ProductCategory last = productCategoryService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null) {
				productCategory.setTreeSort(last.getTreeSort() + 1);
				productCategory.setProcategoryCode(IdGen.nextCode(last.getProcategoryCode()));
			} else if (productCategory.getParent() != null) {
				productCategory.setProcategoryCode(productCategory.getParent().getProcategoryCode() + "001");
			}
		}
		// 以下设置表单默认数据
		if (productCategory.getTreeSort() == null) {
			productCategory.setTreeSort(1);
		}
		return productCategory;
	}

	/**
	 * 保存商品分类表
	 */
	@RequiresPermissions("product:productCategory:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated ProductCategory productCategory) {
		productCategoryService.save(productCategory);
		return renderResult(Global.TRUE, "保存商品分类信息成功！");
	}

	/**
	 * 删除商品分类表
	 */
	@RequiresPermissions("product:productCategory:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(ProductCategory productCategory) {
		Integer num = productCategoryService.selectByProcategoryStatus(productCategory.getProcategoryCode());
		TbProduct tbProduct = new TbProduct();
		tbProduct.setProcategoryCode(productCategory.getProcategoryCode());
		List<TbProduct> tbProducts = tbProductService.findList(tbProduct);
		if (ParamentUntil.isBackList(tbProducts)) {
			return renderResult(Global.FALSE, "删除失败！商品分类已存在商品信息中");
		}
		if (num > 0) {
			return renderResult(Global.FALSE, "删除失败！商品分类为已审核状态");
		}
		//删除时删除子数据
		ProductCategory productCategory1 = new ProductCategory();
		productCategory1.setParentCodes(productCategory.getProcategoryCode());
		List<ProductCategory> productCategories = productCategoryService.findList(productCategory1);
		if (ParamentUntil.isBackList(productCategories)) {
			for (int i = 0; i < productCategories.size(); i++) {
				//删除前判断商品分类是否已存在商品信息中
				TbProduct tbProduct1 = new TbProduct();
				tbProduct1.setProcategoryCode(productCategories.get(i).getProcategoryCode());
				List<TbProduct> tbProductsL = tbProductService.findList(tbProduct1);
				Integer num1 = productCategoryService.selectByProcategoryStatus(productCategories.get(i).getProcategoryCode());
				if (ParamentUntil.isBackList(tbProductsL)) {
					return renderResult(Global.FALSE, "删除失败！商品分类已存在商品信息中");
				}
				if (num1 > 0) {
					return renderResult(Global.FALSE, "该分类下存在已审核的商品分类，无法删除");
				}
			}
			for (int j = 0; j < productCategories.size(); j++) {
				productCategoryService.delete(productCategories.get(j));
				//根据父节点去查上一级
				if (!("0".equals(productCategories.get(j).getParentCode()))) {
					ProductCategory productCategory2 = new ProductCategory();
					productCategory2.setProcategoryCode(productCategories.get(j).getParentCode());
					productCategory2 = productCategoryService.get(productCategory2);
					if (productCategory2 != null) {
						//查询父节点下是还存在子节点
						ProductCategory productCategory3 = new ProductCategory();
						productCategory3.setParentCodes(productCategory2.getProcategoryCode());
						List<ProductCategory> productCategories1 = productCategoryService.findList(productCategory3);

						if (productCategories1 == null || productCategories1.size() <= 0) {
							//如果没有子节点跟新父节点的是否最末级
							productCategory2.setTreeLeaf("1");
							productCategoryService.save(productCategory2);
						}

					}
				}
			}
		}

		productCategoryService.delete(productCategory);
		//根据父节点去查上一级
		if (!("0".equals(productCategory.getParentCode()))) {
			ProductCategory productCategory2 = new ProductCategory();
			productCategory2.setProcategoryCode(productCategory.getParentCode());
			productCategory2 = productCategoryService.get(productCategory2);
			if (productCategory2 != null) {
				ProductCategory productCategory3 = new ProductCategory();
				productCategory3.setParentCodes(productCategory2.getProcategoryCode());
				List<ProductCategory> productList = productCategoryService.findList(productCategory3);
				if (productList == null || productList.size() <= 0) {
					//如果没有子节点跟新父节点的是否最末级
					productCategory2.setTreeLeaf("1");
					productCategoryService.save(productCategory2);
				}

			}
		}
		return renderResult(Global.TRUE, "成功删除商品分类数据！");
	}

	/**
	 * 获取树结构数据
	 *
	 * @param excludeCode 排除的Code
	 * @param isShowCode  是否显示编码（true or 1：显示在左侧；2：显示在右侧；false or null：不显示）
	 * @return
	 */
	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = "treeData")
	@ResponseBody
	public List<Map<String, Object>> treeData(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		List<ProductCategory> list = productCategoryService.findList(new ProductCategory());
		for (int i = 0; i < list.size(); i++) {
			ProductCategory e = list.get(i);
			// 过滤非正常的数据
			if (!ProductCategory.STATUS_NORMAL.equals(e.getStatus())) {
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)) {
				if (e.getId().equals(excludeCode)) {
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")) {
					continue;
				}
			}
			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getProcategoryCode(), e.getCategoryName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * @param excludeCode
	 * @param isShowCode
	 * @return 父类未审核的话该父类下的所有子类
	 * 都不能选择（包括已审核和未审核的子类）），随后从已审核变成未审核的分类和系列不影响历史数据
	 */
	@RequiresPermissions("product:productCategory:view")
	@RequestMapping(value = "treeData1")
	@ResponseBody
	public List<Map<String, Object>> treeData1(String excludeCode, String isShowCode) {
		List<Map<String, Object>> mapList = ListUtils.newArrayList();
		ProductCategory productCategory = new ProductCategory();
		productCategory.setItem("treeData");
		List<ProductCategory> list = productCategoryService.findList(productCategory);
		for (int i = 0; i < list.size(); i++) {
			ProductCategory e = list.get(i);
			// 过滤非正常的数据
			if (!ProductCategory.STATUS_NORMAL.equals(e.getStatus())) {
				continue;
			}
			// 过滤被排除的编码（包括所有子级）
			if (StringUtils.isNotBlank(excludeCode)) {
				if (e.getId().equals(excludeCode)) {
					continue;
				}
				if (e.getParentCodes().contains("," + excludeCode + ",")) {
					continue;
				}
			}
//			//剔除未审核状态的商品分类
//			if (e.getProcategoryStatus().equals("1")) {
//				continue;
//			}
//			boolean flag = true;
//			//剔除父类为未审核状态的商品分类
//			if (!e.getParentCode().equals("0")) {
//				String str[] = e.getParentCodes().split(",");
//				for (int k = 1; k < str.length; k++) {
//					ProductCategory productCategory1 = new ProductCategory();
//					productCategory1.setProcategoryCode(str[k]);
//					productCategory1 = productCategoryService.get(productCategory1);
//					if (productCategory1 != null && productCategory1.getProcategoryStatus().equals("1")) {
//						flag = false;
//						break;
//					}
//				}
//			}
//			if (!flag) {
//				continue;
//			}

			Map<String, Object> map = MapUtils.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentCode());
			map.put("name", StringUtils.getTreeNodeName(isShowCode, e.getProcategoryCode(), e.getCategoryName()));
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 修复表结构相关数据
	 */
	@RequiresPermissions("product:productCategory:edit")
	@RequestMapping(value = "fixTreeData")
	@ResponseBody
	public String fixTreeData(ProductCategory productCategory) {
		if (!UserUtils.getUser().isAdmin()) {
			return renderResult(Global.FALSE, "操作失败，只有管理员才能进行修复！");
		}
		productCategoryService.fixTreeData();
		return renderResult(Global.TRUE, "数据修复成功");
	}

	/**
	 * 修复数据
	 */
	@ResponseBody
	@RequestMapping(value = "fixPicData")
	public String fixPicData(@RequestBody String bizType) {
		FileUpload fileUpload = new FileUpload();
		fileUpload.setBizType(bizType);
		List<FileUpload> list = fileUploadService.findList(fileUpload);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				FileUpload imgUntil = list.get(i);
				//String url=amUtilService.getImgPath(imgUntil.getBizKey());
				String url = "/userfiles/fileupload" + "/" + imgUntil.getFileEntity().getFilePath() + imgUntil.getFileEntity().getFileId() + "." + imgUntil.getFileEntity().getFileExtension();
				String url_fix =baseDir;
				File file = new File(url_fix + url);
				// 989689001677406208.jpg
				if (file.exists()) {

				}

			}
		}


       return renderResult(Global.TRUE,"保存成功");
	}
}