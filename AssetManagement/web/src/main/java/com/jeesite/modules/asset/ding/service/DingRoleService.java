package com.jeesite.modules.asset.ding.service;

import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.dao.DingRoleDao;
import com.jeesite.modules.asset.ding.entity.DingRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DingRoleService extends CrudService<DingRoleDao, DingRole> {
    @Autowired
    private DingRoleDao dingRoleDao;
    @Override
    public void save(DingRole entity) {
        super.save(entity);
    }

    @Override
    public void update(DingRole entity) {
        super.update(entity);
    }

    @Override
    public DingRole get(DingRole entity) {
        return super.get(entity);
    }

    @Override
    public List<DingRole> findList(DingRole entity) {
        return super.findList(entity);
    }
    public List<String> getRoleNamesByUser(String userId){
        return dingRoleDao.getRoleNamesByUser(userId);
    }
//
}
