/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.modules.asset.product.entity.ProductCategory;
import com.jeesite.modules.asset.product.entity.ProductSeries;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.storevideo.ovopark.api.GetAgeData;
import com.jeesite.modules.storevideo.video.entity.SvVideo;
import com.jeesite.modules.storevideo.video.entity.SvVideoDto;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlat;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlatResult;
import com.jeesite.modules.storevideo.video.service.SvVideoRlatService;
import com.jeesite.modules.storevideo.video.service.SvVideoService;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 店铺视频Controller
 * @author Philip Guan
 * @version 2019-01-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sv/video")
public class SvVideoController extends BaseController {

	@Autowired
	private SvVideoService svVideoService;
	@Autowired
    private SvVideoRlatService svVideoRlatService;

    @Autowired
    private GetAgeData getAgeData;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public SvVideo get(String videoCode, boolean isNewRecord) {
		return svVideoService.get(videoCode, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("sv:video:view")
	@RequestMapping(value = {"list", ""})
	public String list(SvVideo svVideo, Model model) {
		model.addAttribute("svVideo", svVideo);
		return "storevideo/video/svVideoList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("sv:video:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<SvVideo> listData(SvVideo svVideo, HttpServletRequest request, HttpServletResponse response) {

        getAgeData.test();
		Page<SvVideo> page = svVideoService.findPage(new Page<SvVideo>(request, response), svVideo); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("sv:video:view")
	@RequestMapping(value = "form")
	public String form(SvVideo svVideo, Model model) {
		model.addAttribute("svVideo", svVideo);

        SvVideoRlat svVideoRlat = new SvVideoRlat();
        svVideoRlat.setVideoCode(svVideo.getVideoCode());
        List<SvVideoRlat> list = svVideoRlatService.findList(svVideoRlat);

        SvVideoRlat svVideoRlatSex = new SvVideoRlat();
        svVideoRlatSex.setVideoCode(svVideo.getVideoCode());
        svVideoRlatSex.setDimensionId("sex");
        model.addAttribute("svVideoRlatSex", svVideoRlatSex);

        SvVideoRlat svVideoRlatAge = new SvVideoRlat();
        svVideoRlatAge.setVideoCode(svVideo.getVideoCode());
        svVideoRlatAge.setDimensionId("age");
        model.addAttribute("svVideoRlatAge", svVideoRlatAge);

        SvVideoRlat svVideoRlatCategory = new SvVideoRlat();
        svVideoRlatCategory.setVideoCode(svVideo.getVideoCode());
        svVideoRlatCategory.setDimensionId("category");
        model.addAttribute("svVideoRlatCategory", svVideoRlatCategory);

        SvVideoRlat svVideoRlatStyle = new SvVideoRlat();
        svVideoRlatStyle.setVideoCode(svVideo.getVideoCode());
        svVideoRlatStyle.setDimensionId("style");
        model.addAttribute("svVideoRlatStyle", svVideoRlatStyle);

        SvVideoRlat svVideoRlatProduct = new SvVideoRlat();
        svVideoRlatProduct.setVideoCode(svVideo.getVideoCode());
        svVideoRlatProduct.setDimensionId("product");
        model.addAttribute("svVideoRlatProduct", svVideoRlatProduct);

        List<SvVideoRlat> svVideoSexList = list.stream().filter(a->a.getDimensionId().equals("sex")).collect(Collectors.toList());
		model.addAttribute("svVideoSexList", svVideoSexList);

        List<SvVideoRlat> svVideoAgeList = list.stream().filter(a->a.getDimensionId().equals("age")).collect(Collectors.toList());
        model.addAttribute("svVideoAgeList", svVideoAgeList);

        List<SvVideoRlat> svVideoCategoryList  = list.stream().filter(a->a.getDimensionId().equals("category")).collect(Collectors.toList());
        model.addAttribute("svVideoCategoryList ", svVideoCategoryList );

        List<SvVideoRlat> svVideoSeriesGrid = list.stream().filter(a->a.getDimensionId().equals("age")).collect(Collectors.toList());
        model.addAttribute("svVideoSeriesList", svVideoSeriesGrid);



        List<SvVideoRlat> svVideoProductGrid = list.stream().filter(a->a.getDimensionId().equals("age")).collect(Collectors.toList());
        model.addAttribute("svVideoProductList", svVideoProductGrid);

		return "storevideo/video/amOrderForm";
	}

    @RequiresPermissions("sv:video:view")
    @RequestMapping(value = "listDictData")
    @ResponseBody
	public Page<SvVideoRlat> listDictData(SvVideoRlat svVideoRlat, HttpServletRequest request, HttpServletResponse response){
        Page<SvVideoRlat> page =new Page<>(request, response);
        //List<DictData> cacheDictList = DictUtils.getDictList("sv_type");
        if(StringUtils.isBlank(svVideoRlat.getVideoCode())) {
            page.setList(null);
            page.setCount(0);
        }
        else{
            List<SvVideoRlat> list = svVideoRlatService.findList(svVideoRlat);
            //List<SvVideoRlat> dictList = new ArrayList<>();
            //for(SvVideoRlat item : list){
            //    cacheDictList.stream().filter(a->a.getDictLabel().equals(item.getDimensionValue())).findFirst().ifPresent(a->{
            //        dictList.add(item);
            //    });
            //}
            page.setList(list);
            page.setCount(list.size());
        }

        return page;
    }

	/**
	 * 保存店铺视频
	 */
	@RequiresPermissions("sv:video:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated SvVideo svVideo) {
		svVideoService.save(svVideo);
		return renderResult(Global.TRUE, text("保存店铺视频成功！"));
	}
	
	/**
	 * 删除店铺视频
	 */
	@RequiresPermissions("sv:video:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(SvVideo svVideo) {
		svVideoService.delete(svVideo);
		return renderResult(Global.TRUE, text("删除店铺视频成功！"));
	}

    @RequestMapping(value = {"dictSelector", ""})
    public String dictSelector(DictData dictData, Model model, String selectData, String checkbox) {
        dictData.setDictType("sv_type");
        model.addAttribute("selectData", selectData);
        model.addAttribute("dictData", dictData);
        model.addAttribute("checkbox", checkbox);
        return "storevideo/video/dictSelector";
    }

    @RequiresPermissions("sv:video:view")
    @RequestMapping(value = "listDictSelector")
    @ResponseBody
    public Page<DictData> listDictSelector(DictData dictData, HttpServletRequest request, HttpServletResponse response) {
        List<DictData> list = DictUtils.getDictList(dictData.getDictType())
                .stream().filter(a->a.getParentCode().equals(dictData.getParentCode())).collect(Collectors.toList());
        Page<DictData> page = new Page<>(request, response);
        page.setList(list);
        page.setCount(list.size());
        return page;
    }

    @RequestMapping(value = {"categorySelector", ""})
    public String categorySelector(ProductCategory categoryData, Model model, String selectData, String checkbox) {
        model.addAttribute("selectData", selectData);
        model.addAttribute("category", categoryData);
        model.addAttribute("checkbox", checkbox);
        return "storevideo/video/categorySelector";
    }

    @RequestMapping(value = {"styleSelector", ""})
    public String styleSelector(ProductSeries productSeries, Model model, String selectData, String checkbox) {
        model.addAttribute("selectData", selectData);
        model.addAttribute("productSeries", productSeries);
        model.addAttribute("checkbox", checkbox);
        return "storevideo/video/styleSelector";
    }

    @RequestMapping(value = {"productSelector", ""})
    public String productSelector(TbProduct tbProduct, Model model, String selectData, String checkbox) {
        model.addAttribute("selectData", selectData);
        model.addAttribute("product", tbProduct);
        model.addAttribute("checkbox", checkbox);
        return "storevideo/video/productSelector";
    }

    @RequiresPermissions("sv:video:edit")
    @PostMapping(value = "saveData")
    @ResponseBody
    public String saveData(@Validated @RequestBody SvVideoDto data) {
	    SvVideo video = data.getSvVideo();
        video.setIsNewRecord(StringUtils.isBlank(video.getVideoCode()));
        List<SvVideoRlat> list = data.getSvVideoRlatList();
        svVideoService.save(video);
        for(SvVideoRlat item : list){
            item.setVideoCode(video.getVideoCode());
            item.setIsNewRecord(true);
        }

        svVideoRlatService.deleteByVideoId(video.getVideoCode());
        svVideoRlatService.insertBatch(list);
        return renderResult(Global.TRUE, text("保存店铺视频成功！"));
    }

    @RequiresPermissions("sv:video:view")
	@RequestMapping(value = {"videoSelect", ""})
	public String enterpriseSelect(SvVideo svVideo, Model model, String selectData) {
		model.addAttribute("selectData", selectData);
		model.addAttribute("svVideo", svVideo);
		return "storevideo/video/svVideoSelect";
	}

    @PostMapping(value = "getDetectData")
    @ResponseBody
    public ReturnInfo getDetectData(@RequestParam(required = false) Map param) {
        List<SvVideoRlatResult> list = svVideoRlatService.getVideoListByMap(param);
        return ReturnDate.successObject(list);
    }

    /**
     * 查询列表
     */
    @RequestMapping(value = {"websocket", ""})
    public String websocket(Model model) {
        return "storevideo/video/svSSS";
    }
}