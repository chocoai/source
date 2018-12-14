package com.jeesite.modules.asset.ding.service;

import com.jeesite.modules.asset.ding.dao.SyncDingDao;
import com.jeesite.modules.asset.ding.entity.SyncOrganize;
import com.jeesite.modules.asset.ding.entity.SyncPosition;
import com.jeesite.modules.asset.ding.entity.SyncUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//
/**
 * 主动同步其他平台的服务 包括主动调用elearning平台发送数据给对方同步，
 * 以及主动调用钉钉平台去获取信息进行本地同步更新
 * @author thomas
 * @version 2018-11-24
 */

@Service
@Transactional(readOnly = true)
public class SyncDingService {

    @Autowired
    private SyncDingDao syncDingDao;



    //e-learning平台同步后保存结果信息
    @Transactional(readOnly = false)
    public void saveResult(String date, String method, String result){
        syncDingDao.saveSyncResult(date,method,result);
    }

    /**
     * 给E-learning 平台同步部门数据使用，从数据库查询，封装List<SyncOrganize>实体类
     * 按照需求，总父部门需要把parentCode改成*
     * @author thomas
     * @version 2018-11-20
     * @param
     * @return List
     */

    public List<SyncOrganize> getSyncOrganizes(){
        SyncOrganize parent = syncDingDao.getParentOrganize();
        parent.setParentCode("*");
        List<SyncOrganize> organizes =syncDingDao.getOrganizes();
        organizes.add(parent);
        organizes.forEach(a->a.setCorpCode("uvanart"));
        return organizes;
    }

    /**
     * 给E-learning平台同步岗位信息数据，获得所有信息用于同步,遍历加入CorpCode的默认值（需求）
     * 由于我们数据库没有岗位类别这个对应数据以及字段，而role_id不能用是用为它与类别是多对多的关系，强行使用会出现无法去重的笛卡儿积
     * 因此需求更改，使岗位类别和岗位类别编号与岗位编号，名称设置一样
     * @author thomas
     * @version 2018-11-21
     * @return List
     */
    public List<SyncPosition> getSyncPosition(){

        List<SyncPosition> list = syncDingDao.getSyncPosition();
        for (SyncPosition p :list) {
            p.setCategoryCode(p.getPositionCode());
            p.setCategoryName(p.getPositionName());
            p.setCorpCode("uvanart");
            //syncDingDao.savePositionResult(p.getPositionCode(),p.getPositionName());
        }



        return list;
    }

    /**
     * 给E-learning 平台员工数据使用，从数据库查询，封装List<SyncUser>实体类
     * 按照需求，AccountStatus设定为ENABLE/FORBIDDEN；CorpCode固定为uvanart；
     * 登录名为姓名
     * @author thomas
     * @version 2018-11-21
     * @param
     * @return List
     */

    public List<SyncUser> getSyncUsers(){

        List<SyncUser> list = syncDingDao.getSyncUsers();

        for (SyncUser syn:list){
            //测试数据用，冻结所有账户，因为有激活状态的账户不能超过900
            //if (syn.getAccountStatus().equals("0"))syn.setAccountStatus("FORBIDDEN");else syn.setAccountStatus("ENABLE");
            if (syn.getAccountStatus().equals("0"))syn.setAccountStatus("ENABLE");else syn.setAccountStatus("FORBIDDEN");
            syn.setCorpCode("uvanart");
        }
        return list;
    }









}
