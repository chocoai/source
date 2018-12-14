package com.jeesite.modules.asset.job.group;

import com.jeesite.modules.asset.tianmao.task.SkuSynchriK3Task;
import org.springframework.stereotype.Component;

/**
 * 商品sku同步名称和同步Sku价格
 */
@Component("skuGroup")
public class SkuGroup {

    /**
     * 定时同步K3物料名
     */
    public void skuName () {
        SkuSynchriK3Task.skuSych();
    }

    /**
     * 定时同步K3天猫物料价格
     */
    public void skuPrice () {
        SkuSynchriK3Task.sychPrice();
    }
}
