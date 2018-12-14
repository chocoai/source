/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.fz.fzgoldchangerecord.service;

import java.util.List;

import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.fz.fzgoldchangerecord.entity.FzGoldChangeRecord;
import com.jeesite.modules.fz.fzgoldchangerecord.dao.FzGoldChangeRecordDao;

/**
 * 梵钻变更记录Service
 *
 * @author dwh
 * @version 2018-09-21
 */
@Service
@Transactional(readOnly = true)
public class FzGoldChangeRecordService extends CrudService<FzGoldChangeRecordDao, FzGoldChangeRecord> {

    /**
     * 获取单条数据
     *
     * @param fzGoldChangeRecord
     * @return
     */
    @Autowired
    private DingUserService dingUserService;
    @Autowired
    private FzGoldChangeRecordDao fzGoldChangeRecordDao;

    @Override
    public FzGoldChangeRecord get(FzGoldChangeRecord fzGoldChangeRecord) {
        return super.get(fzGoldChangeRecord);
    }

    /**
     * 查询分页数据
     *
     * @param page               分页对象
     * @param fzGoldChangeRecord
     * @return
     */
    @Override
    public Page<FzGoldChangeRecord> findPage(Page<FzGoldChangeRecord> page, FzGoldChangeRecord fzGoldChangeRecord) {
        return super.findPage(page, fzGoldChangeRecord);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param fzGoldChangeRecord
     */
    @Override
    @Transactional(readOnly = false)
    public void save(FzGoldChangeRecord fzGoldChangeRecord) {
        DingUser dingUser = null;
        if (fzGoldChangeRecord.getGoldType().equals("0")) {      //可兑换梵钻
            dingUser = dingUserService.get(fzGoldChangeRecord.getUserid());
            if (dingUser != null) {
                fzGoldChangeRecord.setBalance(dingUser.getConvertibleGold());
            }
        } else if (fzGoldChangeRecord.getGoldType().equals("1")) {  //部门内梵钻
            dingUser = dingUserService.get(fzGoldChangeRecord.getUserid());
            if (dingUser != null) {
                fzGoldChangeRecord.setBalance(dingUser.getInDepartmentGold());
            }
        } else if (fzGoldChangeRecord.getGoldType().equals("2")) {  //夸部门梵钻
            dingUser = dingUserService.get(fzGoldChangeRecord.getUserid());
            if (dingUser != null) {
                fzGoldChangeRecord.setBalance(dingUser.getOutDepartmentGold());
            }

        }
        super.save(fzGoldChangeRecord);
    }

        /**
         * 更新状态
         * @param fzGoldChangeRecord
         */
        @Override
        @Transactional(readOnly = false)
        public void updateStatus (FzGoldChangeRecord fzGoldChangeRecord){
            super.updateStatus(fzGoldChangeRecord);
        }

        /**
         * 删除数据
         * @param fzGoldChangeRecord
         */
        @Override
        @Transactional(readOnly = false)
        public void delete (FzGoldChangeRecord fzGoldChangeRecord){
            fzGoldChangeRecordDao.deleteDB(fzGoldChangeRecord.getRecordCode());
        }

    public void insetBatch(List<FzGoldChangeRecord> fzGoldChangeRecords2) {
        fzGoldChangeRecordDao.insetBatch(fzGoldChangeRecords2);
    }

}