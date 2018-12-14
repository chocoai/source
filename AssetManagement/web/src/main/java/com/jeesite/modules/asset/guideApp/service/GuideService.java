package com.jeesite.modules.asset.guideApp.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.guideApp.dao.GuideDao;
import com.jeesite.modules.asset.guideApp.entity.GuideGoods;
import com.jeesite.modules.asset.guideApp.entity.GuideOrder;
import com.jeesite.modules.sys.entity.Employee;
import com.jeesite.modules.sys.entity.Office;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
public class GuideService  extends CrudService<GuideDao, GuideOrder> {
    @Autowired
    private GuideDao guideDao;
    @Autowired
    private EmployeeService employeeService;
    /**
     * 根据登录用户帐号获取用户信息
     * @param loginCode
     * @return
     */
    @Transactional(readOnly = true)
    public User getLoginCode(String loginCode) {
        return guideDao.getLoginCode(loginCode);
    }
    public List<GuideGoods> getDetail(String documentCode) {
        return guideDao.getDetail(documentCode);
    }

    @Override
    public Page<GuideOrder> findPage(Page<GuideOrder> page, GuideOrder entity) {
        Employee employee = employeeService.get(entity.getUserCode());
        if (employee != null) {
            Office office = employee.getOffice();
            String parentCode = office.getParentCode();
            User user = UserUtils.get(entity.getUserCode());
            if (!"0".equals(parentCode) && "employee".equals(user.getUserType())) {
                entity.setOfficeCode(office.getOfficeCode());
                entity.setUserName(user.getUserName());
            }
        }
        return super.findPage(page, entity);
    }

    /**
     * 根据用户账号获取部门里的tree_names
     * @param userCode
     * @return
     */
    public String selectShop(String userCode) {
        return guideDao.selectShop(userCode);
    }
}
