package com.jeesite.modules.asset.consumables.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.consumables.dao.AmConStockDao;


import com.jeesite.modules.asset.consumables.entity.AmConStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 库位表Service
 * @author dwh
 * @version 2018-05-07
 */
@Service
@Transactional(readOnly=true)
public class AmConStockService extends CrudService<AmConStockDao,AmConStock> {
    @Autowired
    private AmConStockDao amConStockDao;

    @Override
    public AmConStock get(AmConStock amConStock) {
        AmConStock entity = super.get(amConStock);
        return entity;
    }
    /**
     * 保存数据（插入或更新）
     * @param amConStock
     */
    @Override
    @Transactional(readOnly=false)
    public void save(AmConStock amConStock) {
        Date now=new Date();
        if (amConStock.getIsNewRecord()){
            amConStock.setCreateDate(now);
        }
        super.save(amConStock);
    }

}
