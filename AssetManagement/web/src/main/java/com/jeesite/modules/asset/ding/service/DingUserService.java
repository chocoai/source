/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.ding.service;

import com.google.common.collect.Sets;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.Page;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.mapper.JsonMapper;
import com.jeesite.common.service.CrudService;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.common.web.http.HttpClientUtils;
import com.jeesite.modules.asset.ding.FzTask;
import com.jeesite.modules.asset.ding.dao.*;
import com.jeesite.modules.asset.ding.entity.*;
import com.jeesite.modules.asset.fgcqualitycheck.common.Convert;
import com.jeesite.modules.asset.util.ParamentUntil;
import com.jeesite.modules.dingding.entity.DingApiUser;
import com.jeesite.modules.fz.fzlogin.service.FzLoginRecordService;
import com.jeesite.modules.util.CompareObejct;
import com.jeesite.modules.util.CompareStatus;
import com.jeesite.modules.util.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 人员管理Service
 *
 * @author scarlett
 * @version 2018-09-19
 */
@Service
@Transactional(readOnly = true)
public class DingUserService extends CrudService<DingUserDao, DingUser> {

    @Value("${FZ_EXPRIED_TIME}")
    Long FZ_EXPRIED_TIME;  //token过期时间
    public static final String REDIS_KEY_DING_USER = "dinguser";
    public static final String REDIS_KEY_DING_USER_TOKEN = "dingusertoken";

    @Resource
    private RedisUtil<String, String> redisString;
    @Resource
    private RedisUtil<String, DingDepartment> redisDingDepartment;
    @Resource
    private RedisUtil<String, DingUser> redisUser;

    @Autowired
    private FzLoginRecordService fzLoginRecordService;
    @Autowired
    private DingDepartmentDao dingDepartmentDao;
    @Autowired
    private DingDepartmentService dingDepartmentService;
    @Autowired
    private DingUserDepartmentDao dingUserDepartmentDao;

    @Autowired
    private DingRoleDao dingRoleDao;
    @Autowired
    private DingUserDao dingUserDao;

    /**
     * 获取单条数据
     *
     * @param dingUser
     * @return
     */
    @Override
    public DingUser get(DingUser dingUser) {
        DingUser entity = super.get(dingUser);
		/*if (entity != null){
			DingDepartment dingDepartment = new DingDepartment(entity);
			dingDepartment.setStatus(DingDepartment.STATUS_NORMAL);
			entity.setDingDepartmentList(dingDepartmentDao.findList(dingDepartment));
			DingRole dingRole = new DingRole(entity);
			dingRole.setStatus(DingRole.STATUS_NORMAL);
			entity.setDingRoleList(dingRoleDao.findList(dingRole));
		}*/
        return entity;
    }

    /**
     * 查询分页数据
     *
     * @param page     分页对象
     * @param dingUser
     * @return
     */
    @Override
    public Page<DingUser> findPage(Page<DingUser> page, DingUser dingUser) {
        try {
            super.findPage(page, dingUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findPage(page, dingUser);
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param dingUser
     */
    @Override
    @Transactional(readOnly = false)
    public void save(DingUser dingUser) {
        super.save(dingUser);
        // 保存 DingUser子表
		/*for (DingDepartment dingDepartment : dingUser.getDingDepartmentList()){
			if (!DingDepartment.STATUS_DELETE.equals(dingDepartment.getStatus())){
				dingDepartment.setDepartmentId(dingUser);
				if (dingDepartment.getIsNewRecord()){
					dingDepartment.preInsert();
					dingDepartmentDao.insert(dingDepartment);
				}else{
					dingDepartment.preUpdate();
					dingDepartmentDao.update(dingDepartment);
				}
			}else{
				dingDepartmentDao.delete(dingDepartment);
			}*/
//		}
        // 保存 DingUser子表
		/*for (DingRole dingRole : dingUser.getDingRoleList()){
			if (!DingRole.STATUS_DELETE.equals(dingRole.getStatus())){
				dingRole.setRoleId(dingUser);
				if (dingRole.getIsNewRecord()){
					dingRole.preInsert();
					dingRoleDao.insert(dingRole);
				}else{
					dingRole.preUpdate();
					dingRoleDao.update(dingRole);
				}
			}else{
				dingRoleDao.delete(dingRole);
			}
		}*/
    }

    /**
     * 分页查询用户
     */
   /* @Transactional(readOnly = true)
    public List<DingUser> getUserList(int start, int end) {
        return dingUserDao.getUserList(start, end);
    }*/

    /**
     * 用户名模糊分页查询用户
     */
   /* @Transactional(readOnly = true)
    public List<DingUser> getUserListByName(int start, int end, String name) {
        return dingUserDao.getUserListByName(start, end, name);
    }*/

    /**
     * 获取用户数量
     */
  /*  public int getUserCount() {
        return dingUserDao.getUserCount();
    }*/

    /**
     * 按姓名获取用户数
     */
    /*public int getUserCountByname(String name) {
        name = "%" + name + "%";
        return dingUserDao.getUserCountByname(name);
    }*/

    /**
     * 更新状态
     *
     * @param dingUser
     */
    @Override
    @Transactional(readOnly = false)
    public void updateStatus(DingUser dingUser) {
        super.updateStatus(dingUser);
    }

    /**
     * 删除数据
     *
     * @param dingUser
     */
    @Override
    @Transactional(readOnly = false)
    public void delete(DingUser dingUser) {
		/*super.delete(dingUser);
		DingDepartment dingDepartment = new DingDepartment();
		dingDepartment.setDepartmentId(dingUser);
		dingDepartmentDao.delete(dingDepartment);*/
		/*DingRole dingRole = new DingRole();
		dingRole.setRoleId(dingUser);
		dingRoleDao.delete(dingRole);*/
    }

    /**
     * 同步钉钉用户
     */

    @Transactional(readOnly = false)
    public String sysUser(String user) {
        JSONObject jsonObject = JSONObject.fromObject(user);
        String userId = jsonObject.getString("userid");
        DingUser dingUser1 = new DingUser();
        dingUser1.setUserid(userId);
        dingUser1 = this.get(dingUser1);
        DingUser dingUser = new DingUser();
        if (jsonObject.containsKey("department")) {
            String departmnet = jsonObject.getString("department");
            dingUserDao.deleteUserDepartment(userId);
            JSONArray jsonArray1 = JSONArray.fromObject(departmnet);
            if (jsonArray1 != null && jsonArray1.size() > 0) {
                for (int j = 0; j < jsonArray1.size(); j++) {
                    String departmnetId = String.valueOf(jsonArray1.get(j));
                    DingDepartment dingDepartment = new DingDepartment();
                    dingDepartment.setDepartmentId(departmnetId);
                    dingDepartment = dingDepartmentDao.get(dingDepartment);
                    if (dingDepartment != null) {
                        if ("1".equals(dingDepartment.getOuterDept())) {
                            if (dingUser1 != null) {
                                dingUserDao.delete(dingUser1);
                            }
                            return "该员工所在部门限制本部门成员查看通讯录";
                        } else {
                            dingUserDao.insertUserDepartment(userId, departmnetId);
                        }
                    }

                }
            }
        }
        if (jsonObject.containsKey("remark")) {
            dingUser.setRemark(jsonObject.getString("remark"));
        }
        if (jsonObject.containsKey("openId")) {
            dingUser.setOpenid(jsonObject.getString("openId"));
        }
        dingUser.setUserid(jsonObject.getString("userid"));
        if (jsonObject.containsKey("isLeaderInDepts")) {
            dingUser.setIsLeaderInDepts(jsonObject.getString("isLeaderInDepts"));
        }
        if (jsonObject.containsKey("isBoss")) {
            boolean isBoss = jsonObject.getBoolean("isBoss");
            if (isBoss) {
                dingUser.setIsBoss("1");
            } else {
                dingUser.setIsBoss("0");
            }

        }
        if (jsonObject.containsKey("name")) {
            dingUser.setName(jsonObject.getString("name"));
        }
        if (jsonObject.containsKey("tel")) {
            dingUser.setTel(jsonObject.getString("tel"));
        }
        if (jsonObject.containsKey("workPlace")) {
            dingUser.setWorkPlace(jsonObject.getString("workPlace"));
        }
        if (jsonObject.containsKey("mobile")) {
            dingUser.setMobile(jsonObject.getString("mobile"));
        }
        if (jsonObject.containsKey("email")) {
            dingUser.setEmail(jsonObject.getString("email"));
        }
        if (jsonObject.containsKey("orgEmail")) {
            dingUser.setOrgEmail(jsonObject.getString("orgEmail"));
        }
        if (jsonObject.containsKey("active")) {
            boolean active = jsonObject.getBoolean("active");
            if (active) {
                dingUser.setActive("1");
            } else {
                dingUser.setActive("0");
            }

        }
        if (jsonObject.containsKey("orderInDepts")) {
            dingUser.setOrderinDepts(jsonObject.getString("orderInDepts"));
        }
        if (jsonObject.containsKey("isAdmin")) {
            boolean isAdmin = jsonObject.getBoolean("isAdmin");
            if (isAdmin) {
                dingUser.setIsAdmin("1");
            } else {
                dingUser.setIsAdmin("0");
            }

        }
        if (jsonObject.containsKey("unionid")) {
            dingUser.setUnionid(jsonObject.getString("unionid"));
        }

        if (jsonObject.containsKey("isHide")) {
            boolean isHide = jsonObject.getBoolean("isHide");
            if (isHide) {
                dingUser.setIsHide("1");
            } else {
                dingUser.setIsHide("0");
            }
        }
        if (jsonObject.containsKey("position")) {
            dingUser.setPosition(jsonObject.getString("position"));
        }
        if (jsonObject.containsKey("avatar")) {
            dingUser.setAvatar(jsonObject.getString("avatar"));
        }
        if (jsonObject.containsKey("hiredDate")) {
            Long date1 = jsonObject.getLong("hiredDate");
            Date date = DateUtils.parseDate(date1);
            String datestr = DateUtils.formatDate(date1, "yyyy-MM-dd HH:mm:ss");
            dingUser.setHiredDate(DateUtils.parseDate(datestr));

        }
        if (jsonObject.containsKey("jobnumber")) {
            dingUser.setJobnumber(jsonObject.getString("jobnumber"));
        }
        if (jsonObject.containsKey("extattr")) {
            dingUser.setExtattr(jsonObject.getString("extattr"));
        }
        if (jsonObject.containsKey("stateCode")) {
            dingUser.setStateCode(jsonObject.getString("stateCode"));
        }
        if (jsonObject.containsKey("isSenior")) {
            boolean isSenior = jsonObject.getBoolean("isSenior");
            if (isSenior) {
                dingUser.setIsSenior("1");
            } else {
                dingUser.setIsSenior("0");
            }
        }
        dingUserDao.deleteUserRole(userId);
        if (jsonObject.containsKey("roles")) {
            JSONArray jsonArray = jsonObject.getJSONArray("roles");
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    DingRole dingRole = new DingRole();
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String id = Convert.getString(jsonObject1, "id");
                    dingRole.setRoleId(id);
                    dingRole = dingRoleDao.get(dingRole);
                    DingRole dingRole1 = new DingRole();
                    dingRole1.setRoleId(id);
                    dingRole1.setGroupName(Convert.getString(jsonObject1, "groupName"));
                    dingRole1.setRoleName(Convert.getString(jsonObject1, "name"));
                    if (dingRole == null) {
                        dingRole1.setIsNewRecord(true);
                        dingRoleDao.insert(dingRole1);
                    } else {
                        dingRole.setIsNewRecord(false);
                        dingRoleDao.update(dingRole1);
                    }
                    dingUserDao.insertUserRole(userId, id);
                }
            }
        }

        if (null == dingUser1) {
            dingUser.setIsNewRecord(true);
            dingUserDao.insert(dingUser);
        } else {
            dingUser.setIsNewRecord(false);
            dingUserDao.update(dingUser);
        }
        return "";
    }

    /**
     * 同步钉钉部门
     */
    @Transactional(readOnly = false)
    public void sysDepartment(String department) {
        JSONObject jsonObject = JSONObject.fromObject(department);
        String id = Convert.getString(jsonObject, "id");
        DingDepartment dingDepartment = new DingDepartment();
        dingDepartment.setDepartmentId(id);
        dingDepartment = dingDepartmentDao.get(dingDepartment);
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
            } else {
                dingDepartment1.setGroupContainSubdept("0");
            }

        }
        if (dingDepartment == null) {
            dingDepartment1.setIsNewRecord(true);
            dingDepartmentDao.insert(dingDepartment1);
        } else {
            dingDepartment1.setIsNewRecord(false);
            dingDepartmentDao.update(dingDepartment1);
        }
    }

    /**
     * 同步钉钉角色
     */
    @Transactional(readOnly = false)
    public void sysRole(String role) {
        JSONObject jsonObject = JSONObject.fromObject(role);
        JSONArray jsonArray = jsonObject.getJSONArray("List");
        if (jsonArray != null && jsonArray.size() > 0) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject4 = jsonArray.getJSONObject(i);
                //JSONObject jsonObject5 = jsonObject4.getJSONObject("Roles");
                String groupName = jsonObject4.getString("GroupName");
                if (jsonObject4 != null) {
                    JSONArray jsonArray1 = jsonObject4.getJSONArray("Roles");
                    if (jsonArray1 != null && jsonArray1.size() > 0) {
                        for (int j = 0; j < jsonArray1.size(); j++) {
                            JSONObject jsonObject6 = jsonArray1.getJSONObject(j);
                            String id = jsonObject6.getString("Id");
                            DingRole dingRole = new DingRole();
                            dingRole.setRoleId(id);
                            dingRole = dingRoleDao.get(dingRole);
                            DingRole dingRole1 = new DingRole();
                            dingRole1.setRoleId(id);
                            dingRole1.setRoleName(jsonObject6.getString("RoleName"));
                            dingRole1.setGroupName(groupName);
                            if (dingRole == null) {
                                dingRole1.setIsNewRecord(true);
                                dingRoleDao.insert(dingRole1);
                            } else {
                                dingRole1.setIsNewRecord(false);
                                dingRoleDao.update(dingRole1);
                            }
                        }
                    }
                }
            }
        }

    }

    @Transactional(readOnly = false)
    public List<DingDepartment> getDingDepartmentByUser(String userid) {
        return dingDepartmentDao.getDingDepartmentByUser(userid);
    }

    public List<DingUser> getUserListByDepartmentId(String departmentId) {
        return dingUserDao.getUserListByDepartmentId(departmentId);
    }


    /**
     * 得到该员工的所有根部门
     *
     * @param userid
     * @return
     */
    public List<DingDepartment> getRootDepartmentByUserId(String userid) {
        List<DingDepartment> departmentList = dingDepartmentService.getDingDepartmentByUser(userid);
        List<DingDepartment> dingDepartmentList = new ArrayList<>();
        //总公司的部门id:0
        String deptcode = dingDepartmentDao.getRootCode();
        if (departmentList != null && departmentList.size() > 0) {
            for (int i = 0; i < departmentList.size(); i++) {
                DingDepartment dingDepartment = departmentList.get(i);
                if (deptcode.equals(dingDepartment.getParentid())) {
                    return departmentList;
                }
                String depts[] = dingDepartment.getParentCodes().split(",");
                if (depts != null && depts.length > 0) {
                    for (String s : depts) {
                        if ("0".equals(s)) {
                            continue;
                        }
                        DingDepartment dingDepartment1 = new DingDepartment();
                        dingDepartment1.setDepartmentId(s);
                        dingDepartment1 = dingDepartmentService.get(dingDepartment1);
                        if (deptcode.equals(dingDepartment1.getParentCode())) {
                            dingDepartmentList.add(dingDepartment1);
                        }
                    }
                }
            }
        }
        return dingDepartmentList;
    }

    @Transactional(readOnly = true)
    public DingUser getUserById(String praiserId) {
        DingUser dingUser = new DingUser();
        dingUser.setUserid(praiserId);
        dingUser.setleft("0");

        List<DingUser> list = dingUserDao.findList(dingUser);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

    @Transactional(readOnly = true)
    public DingUser getUserWithId(String userid) {
        DingUser dingUser = new DingUser();
        dingUser.setUserid(userid);
        dingUser.setleft("0");
        DingUser user = this.get(dingUser);
        return user;
    }

    @Transactional(readOnly = true)
    public List<String> getUserIdList() {
        return dingUserDao.getUserIdList();
    }

    @Transactional(readOnly = false)
    public void updateLeftStatus(List<String> idList) {
        dingUserDao.updateLeftStatus(idList);
    }

    @Transactional(readOnly = true)
    public List<DingUser> selectUserCountByDepartment(String departmentId) {
        return dingUserDao.selectUserCountByDepartment(departmentId);
    }

    @Transactional(readOnly = true)
    public void updateGold(long inDepartmentGold, long outDepartmentGold) {
        dingUserDao.updateGold(inDepartmentGold, outDepartmentGold);
    }

    @Transactional(readOnly = false)
    public void updateGoldBydepartmentId(long inDepartmentGold, long outDepartmentGold, String id) {
        dingUserDao.updateGoldBydepartmentId(inDepartmentGold, outDepartmentGold, id);
    }

    @Transactional(readOnly = true)
    public Integer getPraiserCount(String userid) {
        return dao.getPraiserCount(userid);
    }

    @Transactional(readOnly = true)
    public Integer getPresenterCount(String userid) {
        return dao.getPresenterCount(userid);
    }

    public List<DingDepartment> getDepartName(List<String> praiser) {
        return dao.getDepartName(praiser);
    }

    public Integer getPraiserFollowCount(String userId) {
        return dao.getPraiserFollowCount(userId);
    }

    public Integer getPresenterFollowCount(String userId) {
        return dao.getPresenterFollowCount(userId);
    }

    @Autowired
    private DingUserBackupsDao dingUserBackupsDao;

    // 先备份表再更新梵钻
    @Transactional(readOnly = false)
    public void updateGoldNumber(List<DingUser> dingUserList) {
        List<DingUserBackups> dingUserBackupsList = ListUtils.newArrayList();
        for (DingUser dingUser : dingUserList) {
            DingUserBackups dingUserBackups = new DingUserBackups();
            try {
                // 复制原表数据到备份表
                BeanUtils.copyProperties(dingUserBackups, dingUser);
                dingUserBackups.setConvertibleGold(dingUser.getConvertibleGold());
                dingUserBackups.setInDepartmentGold(dingUser.getInDepartmentGold());
                dingUserBackups.setOutDepartmentGold(dingUser.getOutDepartmentGold());
                // 更新的时间
                dingUserBackups.setDate(new Date());
                dingUserBackups.setPkey(IdGen.nextId());
                // 当前年月日作为标识
                String flag = DateUtils.getDate("yyyyMMdd");
                dingUserBackups.setFlag(flag);
                dingUserBackups.setIsNewRecord(true);
                dingUserBackupsList.add(dingUserBackups);
            } catch (Exception e) {

            }
        }
        dingUserBackupsDao.insertBatch(dingUserBackupsList);
        dao.updateGoldNumber();
    }

    /**
     * 根据电话号码查询钉钉用户
     *
     * @param mobile
     * @return
     */
    public List<DingUser> getUserByMobile(String mobile) {
        return dao.getUserByMobile(mobile);
    }

    public List<DingUser> getUserByIds(List<String> userIdList) {
        return dao.getUserByIds(userIdList);
    }

    public List<String> getNamesByUserIds(List<String> userIdList) {
        return dao.getNamesByUserIds(userIdList);
    }

    public List<DingUser> selectByLeft() {
        return dao.selectByLeft();
    }

    /**
     * 更新用户的特权用户值
     *
     * @param userid
     * @param status
     * @throws Exception
     */
    @Transactional(readOnly = false)
    public void updateUserIsVip(String userid, String status) {
        dingUserDao.updateUserIsVip(userid, status);
    }

    /**
     * 内购专用的post请求
     *
     * @param url
     * @param dataMap
     * @param headerMap
     * @param testStr
     * @param charset
     * @return
     */
    public static String doPost(String url, Map<String, String> dataMap, Map<String, String> headerMap, String testStr, String charset) {
        HttpPost httpPost = new HttpPost(url);
        if (headerMap != null) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            if (dataMap != null) {
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                data.add(new BasicNameValuePair("data", testStr));
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(data, charset);
                formEntity.setContentType("application/x-www-form-urlencoded");
                formEntity.setContentEncoding(charset);
                httpPost.setEntity(formEntity);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return HttpClientUtils.executeRequest(httpPost, charset);
    }


    @Transactional(readOnly = false)
    public String importData(MultipartFile file, Boolean isUpdateSupport) {
        if (file == null) {
            throw new ServiceException("请选择导入的数据文件！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        // 当前登录用户
        try (ExcelImport ei = new ExcelImport(file, 2, 0)) {
            // 获取所有用户

            List<DingUser> dingUserList = this.findList(new DingUser());
            List<DingUser> list = ei.getDataList(DingUser.class);
            List<DingUser> dingUserList1 = ListUtils.newArrayList();
            for (DingUser dingUser : list) {
                try {
                    DingUser dingUser1 = dingUserList.stream().filter(s -> s.getJobnumber().equals(dingUser.getJobnumber())).findFirst().get();
                    dingUser1.setJobLevel(dingUser.getJobLevel());
                    dingUserList1.add(dingUser1);
                    successNum++;
                } catch (NoSuchElementException e) {

                } catch (Exception e) {
                    failureNum++;
                    String msg = "<br/>" + failureNum + " 导入失败：";
                    if (e instanceof ConstraintViolationException) {
                        List<String> messageList = ValidatorUtils.extractPropertyAndMessageAsList((ConstraintViolationException) e, ": ");
                        for (String message : messageList) {
                            msg += message + "; ";
                        }
                    } else {
                        msg += e.getMessage();
                    }
                    failureMsg.append(msg);

                }
            }
            if (ListUtils.isNotEmpty(dingUserList1)) {
                dingUserDao.updateBatch(dingUserList1);
            }
        } catch (Exception e) {
            failureMsg.append(e.getMessage());
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }


    public void setUserData(DingUser dingUser, String token, UserData userData) {


        userData.setActive(dingUser.getActive());
        userData.setAvatar(dingUser.getAvatar());
        userData.setChineseName(dingUser.getChineseName());
        userData.setConvertibleGold(dingUser.getConvertibleGold());
        userData.setDepartmentId(dingUser.getDepartmentId());
        userData.setDepartmentNames(dingUser.getDepartmentNames());
        userData.setEmail(dingUser.getEmail());
        userData.setExtattr(dingUser.getExtattr());
        userData.setInDepartmentGold(dingUser.getInDepartmentGold());
        userData.setIsAdmin(dingUser.getIsAdmin());
        userData.setJobnumber(dingUser.getJobnumber());
        userData.setLeft(dingUser.getleft());
        userData.setMobile(dingUser.getMobile());
        userData.setName(dingUser.getName());
        userData.setOpenid(dingUser.getOpenid());
        userData.setOrderinDepts(dingUser.getOrderinDepts());
        userData.setOrgEmail(dingUser.getOrgEmail());
        userData.setOutDepartmentGold(dingUser.getOutDepartmentGold());
        userData.setRoleNames(dingUser.getRoleNames());
        userData.setSex(dingUser.getSex());
        userData.setStateCode(dingUser.getStateCode());
        userData.setTel(dingUser.getTel());
        userData.setUnionid(dingUser.getUnionid());
        userData.setUserid(dingUser.getUserid());
        userData.setWorkPlace(dingUser.getWorkPlace());
        userData.setUvan_token(token);
        userData.setDingDepartmentList(dingUser.getDingDepartmentList());
        userData.setPosition(dingUser.getPosition());

        userData.setPraiserName(dingUser.getPraiserName());
        userData.setPresenterName(dingUser.getPresenterName());
        userData.setWinningPrize(dingUser.getWinningPrize());
        userData.setPrizeType(dingUser.getPrizeType());
        userData.setAchievement(dingUser.getAchievement());
        userData.setUserStatus(dingUser.getUserStatus());
        userData.setRemoveId(dingUser.getRemoveId());
        userData.setUserIsVip(dingUser.getUserIsVip());
        userData.setIsLeaderInDepts(dingUser.getIsLeaderInDepts());

        userData.setDirectSuperior(dingUser.getDirectSuperior());
        userData.setIsManager(dingUser.getIsManager());
        userData.setDepartmentHeader(dingUser.getDepartmentHeader());
    }

    public UserData getUserData(DingUser dingUser, String token) {


        UserData userData = new UserData();

        userData.setActive(dingUser.getActive());
        userData.setAvatar(dingUser.getAvatar());
        userData.setChineseName(dingUser.getChineseName());
        userData.setConvertibleGold(dingUser.getConvertibleGold());
        userData.setDepartmentId(dingUser.getDepartmentId());
        userData.setDepartmentNames(dingUser.getDepartmentNames());
        userData.setEmail(dingUser.getEmail());
        userData.setExtattr(dingUser.getExtattr());
        userData.setInDepartmentGold(dingUser.getInDepartmentGold());
        userData.setIsAdmin(dingUser.getIsAdmin());
        userData.setJobnumber(dingUser.getJobnumber());
        userData.setLeft(dingUser.getleft());
        userData.setMobile(dingUser.getMobile());
        userData.setName(dingUser.getName());
        userData.setOpenid(dingUser.getOpenid());
        userData.setOrderinDepts(dingUser.getOrderinDepts());
        userData.setOrgEmail(dingUser.getOrgEmail());
        userData.setOutDepartmentGold(dingUser.getOutDepartmentGold());
        userData.setRoleNames(dingUser.getRoleNames());
        userData.setSex(dingUser.getSex());
        userData.setStateCode(dingUser.getStateCode());
        userData.setTel(dingUser.getTel());
        userData.setUnionid(dingUser.getUnionid());
        userData.setUserid(dingUser.getUserid());
        userData.setWorkPlace(dingUser.getWorkPlace());
        userData.setUvan_token(token);
        userData.setDingDepartmentList(dingUser.getDingDepartmentList());
        userData.setPosition(dingUser.getPosition());

        userData.setPraiserName(dingUser.getPraiserName());
        userData.setPresenterName(dingUser.getPresenterName());
        userData.setWinningPrize(dingUser.getWinningPrize());
        userData.setPrizeType(dingUser.getPrizeType());
        userData.setAchievement(dingUser.getAchievement());
        userData.setUserStatus(dingUser.getUserStatus());
        userData.setRemoveId(dingUser.getRemoveId());
        userData.setUserIsVip(dingUser.getUserIsVip());
        userData.setIsLeaderInDepts(dingUser.getIsLeaderInDepts());

        userData.setDirectSuperior(dingUser.getDirectSuperior());
        userData.setIsManager(dingUser.getIsManager());
        userData.setDepartmentHeader(dingUser.getDepartmentHeader());

        //List<DingDepartment> depList = dingUser.getDingDepartmentList();
        //if(depList!=null &&depList.size() >0){
        //
        //}

        return userData;
    }


    @Transactional(readOnly = true)
    public DingUser getMyBoss(String userId) {
        return dingUserDao.getMyBoss(userId);
    }

    public DingUser getDingUser(String userId) {
        DingUser dingUser = null;
        if (ParamentUntil.isBackString(userId)) {
            dingUser = getUserById(userId);
            if (dingUser != null) {
                // 保存登录记录表，生成token并保存到redis
                saveToken(dingUser);
            }
        }
        return dingUser;
    }

    /**
     * 获取钉钉用户(优先缓存)
     * @param userId
     * @return
     */
    public DingUser getDingUserFromCache(String userId) {
        DingUser dingUser = null;
        if (!StringUtils.isBlank(userId)) {
            dingUser = redisUser.get(redisUser.DINGUSER, REDIS_KEY_DING_USER + "_" + userId);
            if(dingUser == null){
                dingUser = getUserById(userId);
                if(dingUser != null){
                    setNewToken(dingUser);
                }
            }
            else{
                if(StringUtils.isBlank(dingUser.getUvan_token())){
                    setNewToken(dingUser);
                } else {
                    //token过期，则重设uvantoken
                    Long expTime = redisString.getExpire(REDIS_KEY_DING_USER_TOKEN + "_" + dingUser.getUvan_token(), TimeUnit.MICROSECONDS);
                    if(expTime == null || expTime < 0){
                        setNewToken(dingUser);
                    }
                }
            }
        }
        return dingUser;
    }

    private void setNewToken(DingUser dingUser){
        dingUser.setUvan_token(getUvanToken());
        saveCache(dingUser);
    }

    private String getUvanToken(){
        //根据uuid生产随机token,把"-"去掉,不然在跟前端交互的时候会出现问题
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Deprecated
    public void saveToken(DingUser dingUser) {
        // 保存登录记录表
        fzLoginRecordService.saveData(dingUser);
        List<DingDepartment> dingDepartments = dingDepartmentService.getDingDepartmentByUser(dingUser.getUserid());
        dingUser.setDingDepartmentList(dingDepartments);
        String token = getUvanToken();
        //token = EncodeUtils.encodeHex(token.getBytes());     //不加密了
        dingUser.setUvan_token(token);
        String userDataJson = prossesOne(dingUser, token);
        saveCacheOld(dingUser.getUserid(), token, userDataJson);
    }

    public void saveCache(DingUser dingUser){
        redisString.set(REDIS_KEY_DING_USER_TOKEN + "_" + dingUser.getUvan_token(), dingUser.getUserid(), FZ_EXPRIED_TIME, TimeUnit.SECONDS);
        redisUser.set(redisUser.DINGUSER,REDIS_KEY_DING_USER + "_" + dingUser.getUserid(), dingUser, FZ_EXPRIED_TIME, TimeUnit.SECONDS);
        saveCacheOld(dingUser.getUserid(), dingUser.getUvan_token(), prossesOne(dingUser, dingUser.getUvan_token()));
    }

    @Deprecated
    public void saveCacheOld(String userId, String token, String userDataJson){
        redisString.set("dingding_user_" + token, userId, FZ_EXPRIED_TIME, TimeUnit.SECONDS);
        redisString.set("dingding_user_" + userId, userDataJson, FZ_EXPRIED_TIME, TimeUnit.SECONDS);
    }

    @Deprecated
    private String prossesOne(DingUser dingUser, String token) {

        UserData userData = getUserData(dingUser, token);

        String jsonString = JsonMapper.toJson(userData);

        //JSONObject userJson = (JSONObject) JSONObject.toJSON(userData);
        return jsonString;
    }


    /**
     * 将同步的用户处理后保存到数据库
     * @param users
     */

    @Transactional(readOnly = false)
    public void saveUsersFromApi(List<DingApiUser> users) {
        List<DingUser> dbUsers = dingUserDao.findList(new DingUser());
        List<DingUserDepartment> dbUserDepts = dingUserDepartmentDao.findList(new DingUserDepartment());

        boolean dbIsEmpty = ListUtils.isEmpty(dbUsers);
        Map<String, DingUser> dbMap = null;
        if(!dbIsEmpty)
            dbMap = dbUsers.stream().collect(Collectors.toMap(DingUser::getUserid, a -> a,(k1, k2)->k1));   //如果有重复的key,则保留key1,舍弃key2
        List<DingUser> updateUsersList = new ArrayList<>();
        List<DingUser> insertUsersList = new ArrayList<>();
        List<DingUserDepartment> insertUserDeptsList = new ArrayList<>();
        List<DingUserDepartment> deleteUserDeptsList = new ArrayList<>();

        if (!ListUtils.isEmpty(users)) {
            for (int j = 0; j < users.size(); j++) {

                DingApiUser apiUser = users.get(j);
                if(StringUtils.isBlank(apiUser.getExtattr()) || StringUtils.isBlank(apiUser.getOpenId()) || StringUtils.isBlank(apiUser.getUserid())) continue; //供应商、空id不加入

                DingUser dingUser = getDingUserFromApiUser(apiUser);

                CompareObejct<DingUser> compare = new CompareObejct<>();
                if(dbMap != null && dbMap.containsKey(dingUser.getUserid())){
                    DingUser dbUser = dbMap.get(dingUser.getUserid());

                    dingUser.setIsNewRecord(false);
                    //设置导入的工号及职级
                    dingUser.setJobnumber(dbUser.getJobnumber());
                    dingUser.setJobLevel(dbUser.getJobLevel());
                    //设置其他增加的属性
                    dingUser.setWinningPrize(dbUser.getWinningPrize());
                    dingUser.setUserStatus(dbUser.getUserStatus());
                    dingUser.setUserIsVip(dbUser.getUserIsVip());
                    dingUser.setIsManager(dbUser.getIsManager());
                    //dingUser.setDirectSuperior(dbUser.getDirectSuperior());
                    //dingUser.setDepartmentHeader(dbUser.getDepartmentHeader());
                    dingUser.setInDepartmentGold(dbUser.getInDepartmentGold());
                    dingUser.setOutDepartmentGold(dbUser.getOutDepartmentGold());
                    dingUser.setConvertibleGold(dbUser.getConvertibleGold());

                    compare.setOriginal(dbUser);
                    DingUser changed = new DingUser();
                    compare.setIgnoreNames(Sets.newHashSet("department", "dingDepartmentList", "chineseName", "sex", "serialVersionUID", "roleNames", "usedPoint", "freezePoint", "achievement", "prizeType"));
                    if(dbUser.isCustomized()) {
                        Set<String> sets = compare.getIgnoreNames();
                        sets.add("directSuperior");
                        sets.add("departmentHeader");
                        sets.add("isManager");
                        compare.setIgnoreNames(sets);
                    }
                    compare.setChanged(changed);
                    compare.setCurrent(dingUser);
                    compare.contrastObj(DingUser.class);
                    //System.out.println(compare.getStatus());

                    if(compare.getStatus().equals(CompareStatus.CHANGE)) {
                        changed.setUserid(dingUser.getUserid());
                        updateUsersList.add(changed);
                        //过滤已配置过的上级,上级已变更&部门!=最高级的部门
                        //if(changed.getDirectSuperior() != null && !ObjectUtils.isNull(maxDept) && !dingUser.getDepartment().equals(maxDept.getDepartmentId())){
                        //
                        //}
                    }
                } else {
                    dingUser.setInDepartmentGold(0L);
                    dingUser.setOutDepartmentGold(0L);
                    dingUser.setConvertibleGold(0L);
                    dingUser.setIsNewRecord(true);
                    if(insertUsersList.stream().noneMatch(a->a.getUserid().equals(dingUser.getUserid()))){
                        insertUsersList.add(dingUser);
                    }
                    //else{
                    //    insertUsersList.stream().filter(a->a.getUserid().equals(dingUser.getUserid())).findFirst().ifPresent(a->{
                    //        CompareObejct<DingUser> compare1 = new CompareObejct<>();
                    //        DingUser changed1 = new DingUser();
                    //        compare1.setOriginal(dingUser);
                    //        compare1.setCurrent(a);
                    //        compare1.setChanged(changed1);
                    //        compare1.contrastObj(DingUser.class);
                    //        System.out.println(compare1.getStatus());
                    //    });
                    //}
                }
                redisUser.set(redisDingDepartment.DINGUSER, REDIS_KEY_DING_USER + "_" + dingUser.getUserid(), dingUser);
                if(ListUtils.isNotEmpty(dingUser.getDingDepartmentList())){
                    List<DingUserDepartment> currentDbDepts = dbUserDepts.stream().filter(a-> a.getUserId().equals(dingUser.getUserid())).collect(Collectors.toList());
                    List<DingDepartment> insertDepts = dingUser.getDingDepartmentList().stream().filter(a->
                            currentDbDepts.stream().noneMatch(b->b.getDepartmentId().equals(a.getDepartmentId()))
                    ).collect(Collectors.toList());
                    insertUserDeptsList.addAll(insertDepts.stream().map(a-> new DingUserDepartment(dingUser.getUserid(), a.getDepartmentId())).collect(Collectors.toList()));
                    deleteUserDeptsList.addAll(currentDbDepts.stream().filter(a->dingUser.getDingDepartmentList().stream().noneMatch(b->b.getDepartmentId().equals(a.getDepartmentId()))).collect(Collectors.toList()));
                }
            }


            if (!ListUtils.isEmpty(insertUsersList)) dingUserDao.insertAllUsers(insertUsersList);
            if (!ListUtils.isEmpty(updateUsersList)) dingUserDao.updateAllUsers(updateUsersList);
            if (!ListUtils.isEmpty(insertUserDeptsList)) {
                insertUserDeptsList = removeDuplicateDepts(insertUserDeptsList);
                dingUserDepartmentDao.insertBatch(insertUserDeptsList);
            }
            if (!ListUtils.isEmpty(deleteUserDeptsList)) dingUserDepartmentDao.deleteBatch(deleteUserDeptsList);
            if(dbMap != null) {
                List<String> deleteUsersList = dbMap.keySet().stream().filter(a ->
                        users.stream().noneMatch(b -> String.valueOf(b.getUserid()).equals(a))
                ).collect(Collectors.toList());
                if (!ListUtils.isEmpty(deleteUsersList)) dingUserDao.deleteBatch(deleteUsersList);
            }
            //删除加盟商多余数据
            dingUserDao.deleteAllianceUser();
            FzTask.departmentUser();
        }
    }

    /**
     * 移除重复数据
     * @param orderList
     * @return
     */
    private List<DingUserDepartment> removeDuplicateDepts(List<DingUserDepartment> orderList) {
        Set<DingUserDepartment> set = new TreeSet<DingUserDepartment>(new Comparator<DingUserDepartment>() {
            @Override
            public int compare(DingUserDepartment a, DingUserDepartment b) {
                // 字符串则按照asicc码升序排列
                int c1 = a.getDepartmentId().compareTo(b.getDepartmentId());
                if(c1 == 0)
                    return a.getUserId().compareTo(b.getUserId());
                return c1;
            }
        });

        set.addAll(orderList);
        return new ArrayList<DingUserDepartment>(set);
    }

    /**
     * 将API用户转为数据库钉钉用户
     * @param apiUser
     * @return
     */
    public DingUser getDingUserFromApiUser(DingApiUser apiUser) {
        DingUser dingUser = new DingUser();
        apiUser.CopyTo(dingUser);
        setDingUserDepts(dingUser, JsonMapper.toJson(apiUser.getDepartment()));
        return dingUser;
    }

    /**
     * 设置用户部门信息
     * @param dingUser
     * @param apiDeptIdsString
     */
    private void setDingUserDepts(DingUser dingUser, String apiDeptIdsString){
        //设置用户关联的部门
        dingUser.setDepartment(apiDeptIdsString);
        DingDepartment maxDept = null;
        if(StringUtils.isBlank(apiDeptIdsString)) return;
        List<Integer> apiDeptIds = com.alibaba.fastjson.JSONObject.parseArray(apiDeptIdsString, Integer.class);
        if(apiDeptIds != null && !apiDeptIds.isEmpty()){
            List<DingDepartment> depts = new ArrayList<>();
            for(Integer iDeptCode : apiDeptIds){
                //String key = redisDingDepartment.getFirstKey(redisString.DEPT, dingDepartmentService.REDIS_KEY_DING_DEPARTMENT + "*_" + iDeptId.toString());
                DingDepartment dept = dingDepartmentService.getDingDepartmentByIdFromCache(iDeptCode.toString());
                if(dept != null){
                    depts.add(dept);
                }
            }
            dingUser.setDingDepartmentList(depts);
            if(!dingUser.getUserid().equals("manager9586")){   //排除CEO，工号0000是CEO,userid=manager9586
                Optional<DingDepartment> optionalDingDepartment = depts.stream().min(Comparator.comparingInt(DingDepartment::getTreeLevel));
                if(optionalDingDepartment.isPresent()){
                    maxDept = optionalDingDepartment.get();
                    if(maxDept.getDepartmentId().equals("1")){
                        //上级是根，则属于CEO考核
                        dingUser.setDirectSuperior("manager9586");
                        dingUser.setDepartmentHeader("manager9586");
                    }
                    else{
                        //获取上级部门
                        String ownerId = getDeptOwner(maxDept);
                        if(ownerId.equals(dingUser.getUserid()))
                            maxDept = dingDepartmentService.getDingDepartmentByIdFromCache(maxDept.getParentCode());
                        //设置上级
                        dingUser.setDirectSuperior(getDeptOwner(maxDept));
                        //设置上上级
                        DingDepartment deptParent = dingDepartmentService.getDingDepartmentByIdFromCache(maxDept.getParentCode());
                        if(deptParent != null && !deptParent.getDepartmentId().equals("1")){
                            //上上级并非根节点，则设置为上上级部门的管理者
                            dingUser.setDepartmentHeader(getDeptOwner(deptParent));
                        } else {
                            //上级为中心管理员，上上级则是CEO
                            dingUser.setDepartmentHeader("manager9586");
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取部门主管ID
     * @param maxDept
     * @return
     */
    private String getDeptOwner(DingDepartment maxDept){
        if(!StringUtils.isBlank(maxDept.getDeptManagerUseridList())) {
            if(maxDept.getDeptManagerUseridList().contains("|")){
                String[] managerUserIds = maxDept.getDeptManagerUseridList().split("|");
                if(managerUserIds.length > 0)
                    return managerUserIds[0];
            } else {
                return maxDept.getDeptManagerUseridList();
            }

        } else if(!StringUtils.isBlank(maxDept.getOrgDeptOwner())) {
            return maxDept.getOrgDeptOwner();
        } else {
            DingDepartment deptParent = dingDepartmentService.getDingDepartmentByIdFromCache(maxDept.getParentCode());
            if(deptParent!= null) return getDeptOwner(deptParent);
        }
        return maxDept.getOrgDeptOwner();
    }

    @Deprecated
    private String getLeaderUserId(String userId) {
        if (userId.equals("manager9586"))return " ";
        List<DingDepartment> deptList = dingDepartmentDao.getDingDepartmentByUser(userId);
        if (deptList.size() > 0) {
            //树排序
            deptList.sort(Comparator.comparingInt(o -> (o.getTreeLevel() == null ? 10 : o.getTreeLevel())));
            //得到最高部门
            DingDepartment department = deptList.get(0);
            String leaderId = department.getOrgDeptOwner();
            String pid = department.getParentid();
            if (pid!=null && pid.equals("1"))return "manager9586";
            if (leaderId == null) return null;
            else if (leaderId.equals(userId)) {
                //如果本部门的领导是自己，取本部门的上级部门领导
                DingDepartment upperDepartment = dingDepartmentDao.getDeptByID(pid);
                if (upperDepartment == null || upperDepartment.getOrgDeptOwner() == null) return null;
                else return upperDepartment.getOrgDeptOwner();
            }
            else if (!leaderId.equals("")) return leaderId;
        }
        return null;
    }

    @Transactional(readOnly = false)
    public void updateCustomized(String userId) {
        dingUserDao.updateCustomized(userId);
    }
}