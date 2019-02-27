/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.service;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.service.TreeService;
import com.jeesite.modules.asset.ding.dao.DingDepartmentDao;
import com.jeesite.modules.asset.ding.dao.DingUserDepartmentDao;
import com.jeesite.modules.asset.ding.data.Node;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.dingding.DingDepartmentApiObject;
import com.jeesite.modules.util.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * js_ding_departmentService
 *
 * @author scarlett
 * @version 2018-09-20
 */
@Service
@Transactional(readOnly = true)
public class DingDepartmentService extends TreeService<DingDepartmentDao, DingDepartment> {
    @Autowired
    DingDepartmentDao departmentDao;
    @Autowired
    DingUserDepartmentDao dingUserDepartmentDao;
    @Autowired
    RedisUtil<String, DingDepartment> redisDingDepartment;

    public static final String REDIS_KEY_DING_DEPARTMENT = "dingdept";

    /**
     * 获取单条数据
     *
     * @param dingDepartment
     * @return
     */
    @Override
    public DingDepartment get(DingDepartment dingDepartment) {
        return super.get(dingDepartment);
    }

    /**
     * 查询列表数据
     *
     * @param dingDepartment
     * @return
     */
    @Override
    public List<DingDepartment> findList(DingDepartment dingDepartment) {
        return super.findList(dingDepartment);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param dingDepartment
     */
    @Override
    @Transactional(readOnly = false)
    public void save(DingDepartment dingDepartment) {
        super.save(dingDepartment);
    }

    /**
     * 更新状态
     *
     * @param dingDepartment
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(DingDepartment dingDepartment) {
        super.updateStatus(dingDepartment);
    }

    /**
     * 删除数据
     *
     * @param dingDepartment
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(DingDepartment dingDepartment) {
        super.delete(dingDepartment);
    }

    /**
     * 插入所有的部门
     */
    @Transactional(readOnly = false)
    public void sysAllDepartment(String deparment) {
        JSONArray jsonArray = JSONArray.fromObject(deparment);
        if (jsonArray != null && jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                DingDepartment dingDepartment = new DingDepartment();
                dingDepartment.setDepartmentId(id);
                dingDepartment = this.get(dingDepartment);
                DingDepartment dingDepartment1 = new DingDepartment();
                dingDepartment1.setDepartmentId(id);
                if (jsonObject.containsKey("name")) {
                    dingDepartment1.setName(jsonObject.getString("name"));
                }
                if (jsonObject.containsKey("order")) {
                    dingDepartment1.setOrder(jsonObject.getString("order"));
                }
                if (jsonObject.containsKey("parentid")) {
                    dingDepartment1.setParentid(jsonObject.getString("parentid"));
                }
                if (jsonObject.containsKey("createDeptGroup")) {
                    boolean createDeptGroup = jsonObject.getBoolean("createDeptGroup");
                    if (createDeptGroup) {
                        dingDepartment1.setCreateDeptGroup("1");
                    } else {
                        dingDepartment1.setCreateDeptGroup("0");
                    }

                }
                if (jsonObject.containsKey("autoAddUser")) {
                    boolean autoAddUser = jsonObject.getBoolean("autoAddUser");
                    if (autoAddUser) {
                        dingDepartment1.setAutoAddUser("1");
                    } else {
                        dingDepartment1.setAutoAddUser("0");
                    }

                }
                if (jsonObject.containsKey("deptHiding")) {
                    boolean deptHiding = jsonObject.getBoolean("deptHiding");
                    if (deptHiding) {
                        dingDepartment1.setDeptHiding("1");
                    } else {
                        dingDepartment1.setDeptHiding("0");
                    }

                }
                if (jsonObject.containsKey("deptPermits")) {
                    dingDepartment1.setDeptPermits(jsonObject.getString("deptPermits"));
                }
                if (jsonObject.containsKey("userPermits")) {
                    dingDepartment1.setUserPermits(jsonObject.getString("userPermits"));
                }
                if (jsonObject.containsKey("outerDept")) {
                    boolean outerDept = jsonObject.getBoolean("outerDept");
                    if (outerDept) {
                        dingDepartment1.setOuterDept("1");
                    } else {
                        dingDepartment1.setOuterDept("0");
                    }

                }
                if (jsonObject.containsKey("outerPermitDepts")) {
                    dingDepartment1.setOuterPermitPepts(jsonObject.getString("outerPermitDepts"));
                }
                if (jsonObject.containsKey("outerPermitUsers")) {
                    dingDepartment1.setOuterPermitUsers(jsonObject.getString("outerPermitUsers"));
                }
                if (jsonObject.containsKey("orgDeptOwner")) {
                    dingDepartment1.setOrgDeptOwner(jsonObject.getString("orgDeptOwner"));
                }
                if (jsonObject.containsKey("deptManagerUseridList")) {
                    dingDepartment1.setDeptManagerUseridList(jsonObject.getString("deptManagerUseridList"));
                }
                if (jsonObject.containsKey("sourceIdentifier")) {
                    dingDepartment1.setSourceIdentifier(jsonObject.getString("sourceIdentifier"));
                }

                if (jsonObject.containsKey("groupContainSubDept")) {
                    boolean groupContainSubDept = jsonObject.getBoolean("groupContainSubDept");
                    if (groupContainSubDept) {
                        dingDepartment1.setGroupContainSubdept("1");
                        //dingDepartment.setTreeLeaf("0");
                    } else {
                        dingDepartment1.setGroupContainSubdept("0");
                        //dingDepartment.setTreeLeaf("1");
                    }

                }
                if (dingDepartment == null) {
                    dingDepartment1.setIsNewRecord(true);
                    this.insert(dingDepartment1);
                } else {
                    dingDepartment1.setIsNewRecord(false);
                    this.update(dingDepartment1);
                }

            }
        }
        //更新树形结构
        List<DingDepartment> departmentList = findList(new DingDepartment());
        if (departmentList != null && departmentList.size() > 0) {
            for (int k = 0; k < departmentList.size(); k++) {
                DingDepartment dingDepartment = departmentList.get(k);
				/*Map<String,Object> map=new HashMap<String,Object>();
				map.put("parentCode","");
				map.put("parentCodes","0,");
				map.put("treeSorts","");
				map.put("treeNames","");*/
                //map.put("treeLeaf","");

            /*  String parentCode="";
              String parentCodes="0,";
              String treenames="";
              String*/
                List<String> list1 = new ArrayList<>();
                getParentNodes(departmentList, dingDepartment, list1);
                dingDepartment.setTreeSort(Integer.valueOf(dingDepartment.getOrder() == null ? " " : dingDepartment.getOrder()));
                //GroupContainSubdept为true（1）时，含有子节点
				/*List<String> strs=departmentDao.getDingDepartmentByParentCode(dingDepartment.getDepartmentId());
				if(strs==null ||strs.size()<=0){
					dingDepartment.setTreeLeaf("1");
				}else{
					dingDepartment.setTreeLeaf("0");
				}*/
				/*if("0".equals(dingDepartment.getGroupContainSubdept())){
					dingDepartment.setTreeLeaf("1");
				}else{
					List<String> str=departmentDao.getDingDepartmentByParentCode(dingDepartment.getDepartmentId());
					if(str==null ||str.size()<=0){
						dingDepartment.setTreeLeaf("1");
					}else{
						dingDepartment.setTreeLeaf("0");
					}

				}*/
                List<String> strs = this.getDingDepartmentByParentCode(dingDepartment.getDepartmentId());
                if (strs == null || strs.size() <= 0) {
                    dingDepartment.setTreeLeaf("1");
                } else {
                    dingDepartment.setTreeLeaf("0");
                }
                if (null == dingDepartment.getParentid() || "".equals(dingDepartment.getParentid())) {
                    dingDepartment.setParentCode("0");
                } else {
                    dingDepartment.setParentCode(dingDepartment.getParentid());
                }
                dingDepartment.setTreeName_(dingDepartment.getName());
                if (null == list1 || list1.size() <= 0) {
                    dingDepartment.setTreeLevel(0);
                    dingDepartment.setParentCodes("0,");
                } else {
                    dingDepartment.setTreeLevel(list1.size());
                    String str = "0,";
                    for (int i = 0; i < list1.size(); i++) {
                        str = str + list1.get(i) + ",";
                    }
                    dingDepartment.setParentCodes(str);
                }

                this.update(dingDepartment);
            }
        }

    }

    //获取父节点
	/*public Map<String,Object> getParentNodes(List<DingDepartment> departmentList, DingDepartment dingDepartment,Map<String,Object> map){

		if(null==dingDepartment.getParentid() ||"".equals(dingDepartment.getParentid())){
			map.put("parentCode","0");
			map.put("parentCodes","0,");
			map.put("treeSorts",String.format("%010d",dingDepartment.getOrder()));
			map.put("treeNames","");

		}
		for(DingDepartment dingDepartment1:departmentList){

		}
		return map;


	}*/

    /**
     * 获取部门所有父节点
     * @param departmentList
     * @param dingDepartment
     * @param list
     * @return
     */
    public void getParentNodes(List<DingDepartment> departmentList, DingDepartment dingDepartment, List<String> list) {
        if (!StringUtils.isEmpty(dingDepartment.getParentid())) {
            Optional<DingDepartment> optionalDingDepartment = departmentList.stream().filter(a->a.getDepartmentId().equals(dingDepartment.getParentid())).findFirst();
            if(optionalDingDepartment.isPresent()){
                DingDepartment parentDept = optionalDingDepartment.get();
                list.add(parentDept.getDepartmentId());
                getParentNodes(departmentList, optionalDingDepartment.get(), list);
            }
        }
    }


    public List<String> getDingDepartmentByParentCode(String parentCode) {
        return departmentDao.getDingDepartmentByParentCode(parentCode);
    }




    /*	@Transactional
        public DingDepartment getDepByUserId(String userId){

        }*/

    /**
     * 根据用户钉钉id得到部门数据
     *
     * @param userid
     * @return
     */
    @Transactional(readOnly = true)
    public List<DingDepartment> getDingDepartmentByUser(String userid) {
        return departmentDao.getDingDepartmentByUser(userid);
    }

    public String getRootCode() {
        return departmentDao.getRootCode();
    }

    public List<String> getDepartmentNameByUser(String userId) {
        return departmentDao.getDepartmentNameByUser(userId);
    }

    @Transactional(readOnly = true)
    public Set<String> getIdByParntsId(String s) {
        s = "%" + s + "%";
        List<String> list = departmentDao.getIdByParntsId(s);
        Set<String> set = new HashSet<>();
        if (ParamentUntil.isBackList(list)) {

            for (int i = 0; i < list.size(); i++) {
                set.add(list.get(i));
            }

        }
        return set;
    }

    /**
     * 根据父部门获取子部门
     *
     * @param parentCode
     * @return
     */
    @Transactional(readOnly = true)
    public List<DingDepartment> getChiddeptsByParentCode(String parentCode) {
        return departmentDao.getChiddeptsByParentCode(parentCode);
    }

    public List<DepartmentData> getDepartment() {
        return departmentDao.getDepartment();
    }

    @Transactional(readOnly = false)
    public void updateDepartmentUserCount(Long userCount, String departmentId) {
        departmentDao.updateDepartmentUserCount(userCount, departmentId);
    }


    public List<Node> getAll() {
        List<Node> list = departmentDao.getAll();
        return list;
    }

    /**
     * 根据一级部门名字得到该一级部门下有多少人
     *
     * @param name
     * @return
     */
    public int getNumberByName(String name) {
        String id = departmentDao.getDeptIdByName(name);
        return departmentDao.getNumberById("%" + id + "%");
    }

    /**
     * 返回所有一级部门的名字,除了加盟体系
     *
     * @return
     */
    public List getDeptLevelName() {
        List<DingDepartment> parentCode = departmentDao.getChiddeptsByParentCode("1");
        List<String> list = new ArrayList<>();
        for (DingDepartment dingDepartment : parentCode) {
            String name = dingDepartment.getName();
            if (!"加盟体系".equals(name)) {
                list.add(name);
            }
        }
        return list;
    }

    /**
     * 返回所有部门id
     *
     * @return List<String>
     */
    @Transactional(readOnly = false)
    public List<String> getAllDepartmentId() {
        return departmentDao.getAllDepartmentId();
    }


    /**
     * 插入所有部门
     */
    @Transactional(readOnly = false)
    public void syncAllDepartment(String departments) {
        List<DingDepartment> departmentListInsert = new ArrayList<>();
        List<DingDepartment> departmentListUpdate = new ArrayList<>();
        List<DingDepartmentApiObject> apiDepartmentList = com.alibaba.fastjson.JSONObject.parseArray(departments, DingDepartmentApiObject.class);
        if (!ListUtils.isEmpty(apiDepartmentList)) {
            List<DingDepartment> dbDepartmentList = findList(new DingDepartment());
            for (int i = 0; i < apiDepartmentList.size(); i++) {

                DingDepartmentApiObject dingDepartmentApiObject = apiDepartmentList.get(i);
                if(dingDepartmentApiObject.getName() != null && dingDepartmentApiObject.getName().equals("加盟体系")) continue;

                DingDepartment newDept = new DingDepartment();
                dingDepartmentApiObject.CopyTo(newDept);

                List<String> parentIds = new ArrayList<>();
                getParentNodes(dbDepartmentList, newDept, parentIds);
                //设置parentIds拼接字符串
                if(ListUtils.isEmpty(parentIds)){
                    newDept.setTreeLevel(0);
                    newDept.setParentCodes("0,");
                } else {
                    newDept.setTreeLevel(parentIds.size());
                    StringBuilder sbParentIds = new StringBuilder("0,");
                    parentIds.forEach(a->sbParentIds.append(a).append(','));
                    newDept.setParentCodes(sbParentIds.toString());
                }

                //判断是否有叶子节点
                newDept.setTreeLeaf(apiDepartmentList.stream().anyMatch(a-> a.getParentid() != null && a.getParentid().equals(dingDepartmentApiObject.getId())) ? "0" : "1");

                //根据数据库数据判断新增或更新
                if(dbDepartmentList.stream().anyMatch(a->a.getDepartmentId().equals(newDept.getDepartmentId()))){
                    newDept.setIsNewRecord(false);
                    departmentListUpdate.add(newDept);
                } else {
                    newDept.setIsNewRecord(true);
                    departmentListInsert.add(newDept);
                }

                //写入缓存
                if(ListUtils.isEmpty(parentIds)){
                    redisDingDepartment.set(redisDingDepartment.DEPT, REDIS_KEY_DING_DEPARTMENT + "_" + newDept.getDepartmentId(), newDept);
                } else {
                    parentIds.remove("1");
                    parentIds.add(REDIS_KEY_DING_DEPARTMENT);
                    Collections.reverse(parentIds);
                    parentIds.add(newDept.getDepartmentId());
                    String deptString = String.join("_", parentIds);
                    redisDingDepartment.set(redisDingDepartment.DEPT, deptString, newDept);
                }

            }
            List<String> departmentListDelete = dbDepartmentList.stream().filter(a->
                    apiDepartmentList.stream().noneMatch(b->String.valueOf(b.getId()).equals(a.getDepartmentId()))
            ).map(a->a.getDepartmentId()).collect(Collectors.toList());
            if(!ListUtils.isEmpty(departmentListDelete)) departmentDao.deleteBatch(departmentListDelete);
            if(!ListUtils.isEmpty(departmentListInsert)) departmentDao.insertBatch(departmentListInsert);
            if(!ListUtils.isEmpty(departmentListUpdate)) departmentDao.updateBatch(departmentListUpdate);
            departmentDao.updateAllUserCount();
        }
    }

    public DingDepartment getDingDepartmentByIdFromCache(String departmentId){
        return redisDingDepartment.getByPattern(redisDingDepartment.DEPT, REDIS_KEY_DING_DEPARTMENT + "*_" + departmentId);
    }
}