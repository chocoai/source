/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.tianmao.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.tianmao.entity.SpWarehouse;
import com.jeesite.modules.asset.tianmao.service.SpWarehouseService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 仓库中商品列表Controller
 *
 * @author dwh
 * @version 2018-08-18
 */
@Controller
@RequestMapping(value = "${adminPath}/tianmao/spWarehouse")
public class SpWarehouseController extends BaseController {

    @Autowired
    private SpWarehouseService spWarehouseService;

    /**
     * 获取数据
     */
    @ModelAttribute
    public SpWarehouse get(String tableId, boolean isNewRecord) {
        return spWarehouseService.get(tableId, isNewRecord);
    }

    /**
     * 查询列表
     */
    @RequiresPermissions("tianmao:spWarehouse:view")
    @RequestMapping(value = {"list", ""})
    public String list(SpWarehouse spWarehouse, Model model) {
        model.addAttribute("spWarehouse", spWarehouse);
        return "asset/tianmao/spWarehouseList";
    }

    /**
     * 查询列表数据
     */
    @RequiresPermissions("tianmao:spWarehouse:view")
    @RequestMapping(value = "listData")
    @ResponseBody
    public Page<SpWarehouse> listData(SpWarehouse spWarehouse, HttpServletRequest request, HttpServletResponse response) {
        Page<SpWarehouse> page = spWarehouseService.findPage(new Page<SpWarehouse>(request, response), spWarehouse);
        return page;
    }

    /**
     * 查看编辑表单
     */
    @RequiresPermissions("tianmao:spWarehouse:view")
    @RequestMapping(value = "form")
    public String form(SpWarehouse spWarehouse, Model model) {
        model.addAttribute("spWarehouse", spWarehouse);
        return "asset/tianmao/spWarehouseForm";
    }

    /**
     * 保存仓库中商品列表
     */
    @RequiresPermissions("tianmao:spWarehouse:edit")
    @PostMapping(value = "save")
    @ResponseBody
    public String save(@Validated SpWarehouse spWarehouse) {
        spWarehouseService.save(spWarehouse);
        return renderResult(Global.TRUE, "保存仓库中商品列表成功！");
    }

    /**
     * 删除仓库中商品列表
     */
    @RequiresPermissions("tianmao:spWarehouse:edit")
    @RequestMapping(value = "delete")
    @ResponseBody
    public String delete(SpWarehouse spWarehouse) {
        spWarehouseService.delete(spWarehouse);
        return renderResult(Global.TRUE, "删除仓库中商品列表成功！");
    }

    @RequestMapping(value = "synInventorySpYZY")
    @ResponseBody
    public ReturnInfo synInventorySpYZY() {
        try {
            boolean rst = spWarehouseService.synSpSellingSpYZY();
            if (rst) {
                return ReturnDate.success();
            } else {
                return ReturnDate.error(-1, "同步失败");
            }
        } catch (Exception e) {
            return ReturnDate.error(-1, "同步失败");
        }

    }

}