package com.jeesite.modules.asset.ding.service;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.asset.ding.dao.DingRoleDao;
import com.jeesite.modules.asset.ding.entity.DingRole;
import com.jeesite.modules.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * 调用钉钉接口，同步角色信息,与上面不同，返回数据中的键值不一样
     * @author thomas
     * @version 2018-12-05
     * @param
     * @return void
     */
    @Transactional(readOnly = false)
    public void sysDingRole(String role) {
        JSONObject jsonObject = JSONObject.fromObject(role);
        JSONArray jsonArray = jsonObject.getJSONArray("list");

        List<DingRole> dbDingRoleList = dingRoleDao.findList(new DingRole());
        boolean dbIsEmpty = ListUtils.isEmpty(dbDingRoleList);
        Map<String, DingRole> dbMap = null;
        if(!dbIsEmpty)
            dbMap = dbDingRoleList.stream().collect(Collectors.toMap(DingRole::getRoleId, a -> a,(k1,k2)->k1));   //如果有重复的key,则保留key1,舍弃key2
        List<DingRole> dingRoleInsertList = new ArrayList<>();
        List<DingRole> dingRoleUpdateList = new ArrayList<>();
        List<String> dingRoleDeleteList = new ArrayList<>();
        List<DingRole> apiDingRoleList = new ArrayList<>();

        if (jsonArray != null && jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObjGroup = jsonArray.getJSONObject(i);
                if (!jsonObjGroup.isEmpty()) {
                    String groupName = jsonObjGroup.getString("name");
                    JSONArray jsonArrayRoles = jsonObjGroup.getJSONArray("roles");
                    if (jsonArrayRoles != null && jsonArrayRoles.size() > 0) {
                        for (int j = 0; j < jsonArrayRoles.size(); j++) {
                            JSONObject jsonObjRole = jsonArrayRoles.getJSONObject(j);
                            String id = jsonObjRole.getString("id");
                            if(StringUtils.isBlank(id)) continue;;
                            DingRole newDingRole = new DingRole();
                            newDingRole.setRoleId(id);
                            newDingRole.setRoleName(jsonObjRole.getString("name"));
                            newDingRole.setGroupName(groupName);
                            apiDingRoleList.add(newDingRole);
                            if(!dbIsEmpty && dbMap.containsKey(id)){
                                DingRole dbItem = dbMap.get(id);
                                if(!dbItem.getGroupName().equals(newDingRole.getGroupName()) || !dbItem.getRoleName().equals(newDingRole.getRoleName())){
                                    newDingRole.setIsNewRecord(false);
                                    dingRoleUpdateList.add(newDingRole);
                                }
                            } else {
                                newDingRole.setIsNewRecord(true);
                                dingRoleInsertList.add(newDingRole);
                            }
                        }
                    }
                }
            }

            List<String> departmentListDelete = dbMap.keySet().stream().filter(a->
                    apiDingRoleList.stream().noneMatch(b->String.valueOf(b.getRoleId()).equals(a))
            ).collect(Collectors.toList());

            if(!ListUtils.isEmpty(dingRoleInsertList)) dingRoleDao.insertBatch(dingRoleInsertList);
            if(!ListUtils.isEmpty(dingRoleUpdateList)) dingRoleDao.updateBatch(dingRoleUpdateList);
            if(!ListUtils.isEmpty(departmentListDelete)) dingRoleDao.deleteBatch(dingRoleDeleteList);

        }

    }

}
