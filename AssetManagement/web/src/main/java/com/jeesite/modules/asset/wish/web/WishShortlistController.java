/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.asset.wish.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import com.jeesite.common.utils.excel.ExcelExport;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.ding.service.DingUserService;
import com.jeesite.modules.asset.util.result.ReturnDate;
import com.jeesite.modules.asset.util.result.ReturnInfo;
import com.jeesite.modules.asset.wish.dao.WishVotedDetailDao;
import com.jeesite.modules.asset.wish.entity.ShortlistImage;
import com.jeesite.modules.asset.wish.entity.WishPrimary;
import com.jeesite.modules.asset.wish.entity.WishVotedDetail;
import com.jeesite.modules.asset.wish.service.WishPrimaryService;
import com.jeesite.modules.fz.config.IsFileter;
import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.sys.utils.ConfigUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.asset.wish.entity.WishShortlist;
import com.jeesite.modules.asset.wish.service.WishShortlistService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 入围名单Controller
 * @author len
 * @version 2018-11-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fz/wish/wishShortlist")
public class WishShortlistController extends BaseController {

	@Autowired
	private WishShortlistService wishShortlistService;

	@Resource
	private RedisUtil<String, List> redisList;

	public static Map<String, Integer> map = new HashMap<>();

	@Autowired
	private WishPrimaryService wishPrimaryService;

	@Autowired
	private DingUserService dingUserService;
	@Autowired
	private WishVotedDetailDao wishVotedDetailDao;

	// 直接所属的一级部门
	private List<String> sectorIdList = ListUtils.newArrayList();

	// 二级或二级以下部门时获取到的所属一级部门
	private List<String> firstIdList = ListUtils.newArrayList();


	// 个人简介入口关闭截止时间
	private static final String PERSONAL_PROFILE = "personal_profile_time";
	// 复选入口截止时间
	private static final String WISH_CHECK = "wish_check_time";
	// 分数
	private Double score = new Double(0);
	/**
	 * 各级别的分数
	 */
	static {
		map.put("P5", 1);
		map.put("P6", 2);
		map.put("P7", 3);
		map.put("P8", 3);
		map.put("P9", 5);
		map.put("P10", 5);
		map.put("P11", 5);
		map.put("P12", 5);
		map.put("M1", 2);
		map.put("M2", 3);
		map.put("M3", 3);
		map.put("M4", 5);
		map.put("M5", 5);
		map.put("M6", 5);
		map.put("M7", 5);
	}

	/**
	 * 获取数据
	 */
	@ModelAttribute
	public WishShortlist get(String userId, boolean isNewRecord) {
		return wishShortlistService.get(userId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("wish:wishShortlist:view")
	@RequestMapping(value = {"list", ""})
	public String list(WishShortlist wishShortlist, Model model) {
		model.addAttribute("wishShortlist", wishShortlist);
		return "asset/wish/wishShortlistList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("wish:wishShortlist:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<WishShortlist> listData(WishShortlist wishShortlist, HttpServletRequest request, HttpServletResponse response) {
		Page<WishShortlist> page = wishShortlistService.findPage(new Page<WishShortlist>(request, response), wishShortlist); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("wish:wishShortlist:view")
	@RequestMapping(value = "form")
	public String form(WishShortlist wishShortlist, Model model) {
		model.addAttribute("wishShortlist", wishShortlist);
		return "asset/wish/wishShortlistForm";
	}

	/**
	 * 保存入围名单
	 */
	@RequiresPermissions("wish:wishShortlist:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated WishShortlist wishShortlist) {
		wishShortlistService.save(wishShortlist);
		return renderResult(Global.TRUE, text("保存入围名单成功！"));
	}

	@RequiresPermissions("wish:wishShortlist:view")
	@RequestMapping(value = "exportData")
	@ResponseBody
	public void export(WishShortlist wishShortlist, HttpServletResponse response) {
		List<WishShortlist> reportList = wishShortlistService.findList(wishShortlist);
		String fileName = "入围名单.xlsx";
		try(ExcelExport ee = new ExcelExport("入围名单", WishShortlist.class)){
			ee.setDataList(reportList).write(response, fileName);
		}
	}

	/**
	 * 导入用户数据
	 */
	@ResponseBody
	@RequiresPermissions("wish:wishShortlist:edit")
	@PostMapping(value = "importData")
	public String importData(MultipartFile file, String updateSupport) {
		try {
			boolean isUpdateSupport = Global.YES.equals(updateSupport);
			String message = wishShortlistService.importData(file, isUpdateSupport);
			return renderResult(Global.TRUE, "posfull:"+message);
		} catch (Exception ex) {
			return renderResult(Global.FALSE, "posfull:"+ex.getMessage());
		}
	}

	/**
	 * 下载导入用户数据模板
	 */
	@RequiresPermissions("wish:wishShortlist:view")
	@RequestMapping(value = "importTemplate")
	public void importTemplate(HttpServletResponse response) {
		// 随机查一条数据作为模板
		List<WishShortlist> list = wishShortlistService.findList(new WishShortlist());
		String fileName = "入围名单.xlsx";
		try(ExcelExport ee = new ExcelExport("入围名单" , WishShortlist.class, ExcelField.Type.IMPORT)){
			ee.setDataList(list).write(response, fileName);
		}
	}
	/**
	 * 根据userId查询是否存在入围名单
	 * @param userId
	 */
	@IsFileter(isFile="true")
	@ResponseBody
	@RequestMapping(value = "qualification",method = RequestMethod.GET)
    public ReturnInfo qualification(String userId) {

        // 复选入口时间
        String checkTime = ConfigUtils.getConfig(WISH_CHECK).getConfigValue();
        if (StringUtils.isNotEmpty(userId)) {
            JSONObject json = new JSONObject();

            // 根据用户id查询是否填写过初选
            WishPrimary wishPrimary = wishPrimaryService.get(userId);
            if (wishPrimary == null) {
                // 如果没有返回0
                json.put("primary", 0);
            } else {
                // 填写过返回1
                json.put("primary", 1);

            }

            // 根据userId查询入围名单
            WishShortlist wishShortlist = wishShortlistService.get(userId);
            if (wishShortlist == null) {
                // 未入围0
                json.put("shortlist", 0);
            } else {
                if (StringUtils.isEmpty(wishShortlist.getPersonalProfile())) {
                    // 入围未填写
                    json.put("shortlist", 1);
                } else {
                    // 入围已填写
                    json.put("shortlist", 2);
                }
            }


            DingUser dingUser = dingUserService.get(userId);
            if (dingUser != null) {
                String jobLevel = dingUser.getJobLevel();
                if (StringUtils.isNotEmpty(jobLevel)) {
                    // 如果是P开头的级别
                    if (jobLevel.startsWith("P")) {
                        String level = jobLevel.substring(1, 2);
                        // 如果低于P5 返回0
                        if (Integer.parseInt(level) < 5) {
                            json.put("level", 0);
                        } else {
                            // 复选是否已填写
                            writeCheck(json, userId);
                        }
                    } else {
                        // 复选是否已填写
                        writeCheck(json, userId);
                    }
                } else {
                    json.put("level", 0);
                }

            }
            return ReturnDate.success(json);
        } else {
            return ReturnDate.error(12001, "参数错误");
        }
    }


	/**
	 * 判断P5以上复选记录是否已填写
	 * @param json
	 * @param userId
	 */
	public void writeCheck (JSONObject json, String userId) {
		// P5以上查询是否已经选择填写
		WishVotedDetail wishVotedDetail = new WishVotedDetail();
		wishVotedDetail.setVotersUserId(userId);
		List<WishVotedDetail> wishVotedDetailList = wishShortlistService.getCheck(wishVotedDetail);
		if (ListUtils.isEmpty(wishVotedDetailList)) {
			// 未填写1
			json.put("level", 1);
		} else {
			// 填写2
			json.put("level", 2);
		}
	}
	/**
	 * 添加个人简介
	 * @param req
	 */
	@IsFileter(isFile="true")
	@ResponseBody
	@RequestMapping(value = "addPersonalProfile", method = RequestMethod.POST)
	public ReturnInfo addPersonalProfile(@RequestBody String req) {
        // 填写个人简介时间
        String profileTime = ConfigUtils.getConfig(PERSONAL_PROFILE).getConfigValue();
        if (DateUtils.parseDate(profileTime).compareTo(new Date()) < 0) {
            return ReturnDate.error(12005, "填写个人简介时间已截止");
        }
		JSONObject jsonObject = JSONObject.parseObject(req);
		if (jsonObject.containsKey("userId")) {
			// 用户名
			String userId = jsonObject.get("userId").toString();
			WishShortlist wishShortlist = wishShortlistService.get(userId);
			if (wishShortlist == null) {
				return ReturnDate.error(12002, "不存在入围名单内");
			} else {
				if ("1".equals(wishShortlist.getUserStatus())) {
					return ReturnDate.error(12004, "已经填写过个人简介");
				}
			}
			if (jsonObject.containsKey("personalProfile")) {
				String personalProfile = jsonObject.get("personalProfile").toString();
				wishShortlist.setPersonalProfile(personalProfile);
			}
			if (jsonObject.containsKey("photoList")) {
				JSONArray jsonArray = jsonObject.getJSONArray("photoList");
				for (int i = 0; i < jsonArray.size(); i++) {
					String img = jsonArray.getJSONObject(i).get("img").toString();
					if (i == 0) {
						wishShortlist.setImg1(img);
					} else if (i == 1) {
						wishShortlist.setImg2(img);
					} else if (i == 2) {
						wishShortlist.setImg3(img);
					} else if (i == 3) {
						wishShortlist.setImg4(img);
					} else if (i == 4) {
						wishShortlist.setImg5(img);
					} else if (i == 5) {
						wishShortlist.setImg6(img);
					}
				}
			}
			// 简历填写后更新状态为是
			wishShortlist.setUserStatus("1");
			wishShortlist.setIsNewRecord(false);
			wishShortlistService.save(wishShortlist);
			return ReturnDate.success();
		} else {
			return ReturnDate.error(12001, "参数错误");
		}
	}


	/**
	 * 获取个人简历
	 * @param userId
	 * @return
	 */
//	@IsFileter(isFile="true")
	@RequestMapping(value = "getPersonalProfile", method = RequestMethod.GET)
	@ResponseBody
	public ReturnInfo getPersonalProfile (String userId) {
		if (StringUtils.isNotEmpty(userId)) {
			WishShortlist wishShortlist = wishShortlistService.get(userId);
			getImgList(wishShortlist);
			return ReturnDate.success(wishShortlist);
		} else {
			return ReturnDate.error(12001, "参数错误");
		}
	}


	/**
	 * 输入部门名或者员工名 获取员工信息
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "getQualification", method = RequestMethod.GET)
	@IsFileter(isFile="true")
	public ReturnInfo getQualification (WishShortlist wishShortlist, HttpServletRequest request, HttpServletResponse response) {
	    // 只显示个人简介填写过的
		wishShortlist.setUserStatus("1");
		Page<WishShortlist> page = wishShortlistService.findPage(new Page<WishShortlist>(request, response), wishShortlist);
		if (ListUtils.isNotEmpty(page.getList())) {
			for (WishShortlist wishShortlist1 : page.getList()) {
				getImgList(wishShortlist1);
				wishShortlist1.setPackup(true);
				wishShortlist1.setChecked(false);
			}
			return ReturnDate.success(page);
		} else {
			return ReturnDate.error(12001, "查询不到数据");
		}
	}

	/**
	 * 把图片封装成List传给前端用
	 * @param wishShortlist
	 */
	public void getImgList (WishShortlist wishShortlist){
		List<ShortlistImage> photoList = ListUtils.newArrayList();
		if (StringUtils.isNotEmpty(wishShortlist.getImg1())) {
			ShortlistImage shortlistImage = new ShortlistImage();
			shortlistImage.setImg(wishShortlist.getImg1());
			photoList.add(shortlistImage);
		}
		if (StringUtils.isNotEmpty(wishShortlist.getImg2())) {
			ShortlistImage shortlistImage = new ShortlistImage();
			shortlistImage.setImg(wishShortlist.getImg2());
			photoList.add(shortlistImage);
		}
		if (StringUtils.isNotEmpty(wishShortlist.getImg3())) {
			ShortlistImage shortlistImage = new ShortlistImage();
			shortlistImage.setImg(wishShortlist.getImg3());
			photoList.add(shortlistImage);
		}
		if (StringUtils.isNotEmpty(wishShortlist.getImg4())) {
			ShortlistImage shortlistImage = new ShortlistImage();
			shortlistImage.setImg(wishShortlist.getImg4());
			photoList.add(shortlistImage);
		}
		if (StringUtils.isNotEmpty(wishShortlist.getImg5())) {
			ShortlistImage shortlistImage = new ShortlistImage();
			shortlistImage.setImg(wishShortlist.getImg5());
			photoList.add(shortlistImage);
		}
		if (StringUtils.isNotEmpty(wishShortlist.getImg6())) {
			ShortlistImage shortlistImage = new ShortlistImage();
			shortlistImage.setImg(wishShortlist.getImg6());
			photoList.add(shortlistImage);
		}
		wishShortlist.setPhotoList(photoList);
	}

	/**
	 * 复选投票
	 */
	@IsFileter(isFile = "true")
	@RequestMapping(value = "secondBallot", method = RequestMethod.POST)
	@ResponseBody
	public ReturnInfo secondBallot (@RequestBody String req) {
		try {
            // 填写个人简介时间
            String checkTime = ConfigUtils.getConfig(WISH_CHECK).getConfigValue();
            if (DateUtils.parseDate(checkTime).compareTo(new Date()) < 0) {
                return ReturnDate.error(12003, "复选投票时间已截止");
            }
			JSONObject jsonObject = JSONObject.parseObject(req);
			// 投票者的票数
			Integer num = 0;
			// 用户id
			String userId = null;
			if (jsonObject.containsKey("userId")) {
				userId = jsonObject.get("userId").toString();
				DingUser dingUser = dingUserService.get(userId);
				// 获取用户的职级
				String jobLevel = dingUser.getJobLevel();
				String level = jobLevel.substring(0, 2);
				// 根据该用户职级获取票数
				num = map.get(level);
				WishVotedDetail wishVotedDetail = new WishVotedDetail();
                wishVotedDetail.setVotersUserId(userId);
                List<WishVotedDetail> wishVotedDetailList = wishVotedDetailDao.findList(wishVotedDetail);
                if (ListUtils.isNotEmpty(wishVotedDetailList)) {
                    return ReturnDate.error(12002, "已参与过复选投票，请不要重复投票");
                }
			}
			// 获取缓存中所有部门
			List<DepartmentData> departmentList = redisList.get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
			String departmentId = jsonObject.get("departmentId").toString();
			String [] departmentIds = departmentId.split(",");
			// 投票者所有部门
			sectorIdList = ListUtils.newArrayList();
			// 投票者所属直接部门是2级或以下时
			firstIdList = ListUtils.newArrayList();
			// 如果多部门 把2级以下部门全部查到2及部门存在一起
			getAllSector(departmentList, sectorIdList, firstIdList, departmentIds, "0", num);
			if (jsonObject.containsKey("userIdList")) {
				List<String> list = ListUtils.newArrayList();
				JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("userIdList").toString());
				for (int i = 0; i < jsonArray.size(); i++) {
					list.add(jsonArray.getJSONObject(i).get("userId").toString());
				}
				// 根据选中的人在入围名单中获取数据
				List<WishShortlist> wishShortlistList = wishShortlistService.selectByUserId(list);
				List<WishVotedDetail> wishVotedDetailList = ListUtils.newArrayList();
				// 用户名
				String userName = jsonObject.get("userName").toString();
				// 工号
				String jobNumber = jsonObject.get("jobNumber").toString();
				// 职位
				String position = jsonObject.get("position").toString();
				// 部门名
				String department = jsonObject.get("department").toString();

				for (WishShortlist wishShortlist : wishShortlistList) {
					WishVotedDetail wishVotedDetail = new WishVotedDetail();
					wishVotedDetail.setUserId(wishShortlist);
					wishVotedDetail.setVotersUserId(userId);
					wishVotedDetail.setUserName(userName);
					wishVotedDetail.setJobNumber(jobNumber);
					wishVotedDetail.setDepartmentId(departmentId);
					wishVotedDetail.setDepartment(department);
					wishVotedDetail.setPosition(position);
					wishVotedDetail.setVotedTime(new Date());
					wishVotedDetail.setIsNewRecord(true);
					wishVotedDetailList.add(wishVotedDetail);
					// 分隔部门id
					String [] departments = wishShortlist.getDepartmentId().split(",");
					score = new Double(0);
					// 获取二级部门和一级部门id
					getSector(departmentList, departments, num);
					if (score.compareTo(new Double(0)) == 0) {
						score = num.doubleValue();
					}
					// 分数
					Double scores = wishShortlist.getScore();
					wishShortlist.setScore(NumberUtils.add(scores, score));
					// 票数
					wishShortlist.setVotesNum(wishShortlist.getVotesNum() + 1);
				}
				wishShortlistService.updateShortlist(wishShortlistList, wishVotedDetailList);
			}
			return ReturnDate.success();
		} catch (Exception e) {
			return ReturnDate.error(12001, "服务异常");
		}
	}

	/**
	 * 根据部门id获取计算分数
	 * @param departmentList
	 * @param departmentIds
	 * @param num
	 */
	public void getSector(List<DepartmentData> departmentList, String[] departmentIds, Integer num) {
		if (departmentIds.length == 1) {
			String department = departmentIds[0];
			getScore(department, departmentList, num);
		} else {
			// 如果是多部门存放所有二级部门和一级部门
			getSectorList(departmentIds, departmentList, num);
		}
	}

	/**
	 * 多部门时计算分数
	 * @param departments
	 * @param departmentList
	 * @param num
	 */
	public void getSectorList(String[] departments, List<DepartmentData> departmentList, Integer num) {
		for (int i = 0; i < departments.length; i++) {
			String departmentCode = departments[i];
			getScore(departmentCode, departmentList, num);
		}
	}

	/**
	 * 如果直接是一级部门判断是否和投票者的一级部门是否有一致的
	 * @param departmentCode
	 * @param departmentList
	 * @param num
	 */
	public void getScore(String departmentCode, List<DepartmentData> departmentList, Integer num) {
		DepartmentData departmentData = departmentList.stream().filter(s -> s.getDepartmentId().equals(departmentCode)).findFirst().get();
		// 部门级别
		Integer treeLevel = departmentData.getTreeLevel();
		// 大于是二级部门或者二级部门以下
		if (treeLevel >= 2) {
			// 获取二级部门id
			String sector = getFirstSector(departmentData, treeLevel);
			if (ListUtils.isNotEmpty(firstIdList)) {
				if (firstIdList.contains(sector)) {
					score = (double) num / 2;
					return;
				}
			}
		} else if (treeLevel == 1) {
			if (ListUtils.isNotEmpty(sectorIdList)) {
				if (sectorIdList.contains(departmentCode)) {
					score = (double) num / 2;
					return;
				}
			}
		}
	}
	/**
	 * 根据部门id获取所有的二级部门id或者一级部门id
	 * @param departmentList
	 * @param sectorIdList
	 * @param departmentIds
	 */
	public void getAllSector(List<DepartmentData> departmentList, List<String> sectorIdList, List<String> firstIdList, String[] departmentIds, String flag, Integer num) {
		// 如果是单部门
		if (departmentIds.length == 1) {
			String department = departmentIds[0];
			DepartmentData departmentData = departmentList.stream().filter(s -> s.getDepartmentId().equals(department)).findFirst().get();
			// 部门级别
			Integer treeLevel = departmentData.getTreeLevel();
			// 如果是二级部门以下 查到所属的二级部门放在一起
			if (treeLevel == 1) {
				sectorIdList.add(department);
			} else if (treeLevel >= 2){
				String departmentCode = getFirstSector(departmentData, treeLevel);
				// 如果所属部门是二级或二级以下
				firstIdList.add(departmentCode);
			}
		} else {
			// 如果是多部门存放所有二级部门和一级部门
			getList(departmentIds, departmentList, sectorIdList, firstIdList, flag, num);
		}
	}


	/**
	 * // 如果是多部门找到所属的一级部门和直接一级部门
	 * @param departments
	 * @param departmentList
	 * @param sectorList
	 */
	public void getList(String [] departments, List<DepartmentData> departmentList, List<String> sectorList, List<String> firstIdList, String flag, Integer num) {
		for (int i = 0; i < departments.length; i++) {
			String departmentCode = departments[i];
			DepartmentData departmentData = departmentList.stream().filter(s -> s.getDepartmentId().equals(departmentCode)).findFirst().get();
			// 部门级别
			Integer treeLevel = departmentData.getTreeLevel();
			// 大于是二级部门或者二级部门以下
			if (treeLevel >= 2) {
				// 获取二级部门id
				String sector = getFirstSector(departmentData, treeLevel);
				// 把所有的二级部门和一级部门放在一起 用于比较
				firstIdList.add(sector);
			} else if (treeLevel == 1){
				// 等于1都放在一起
				sectorList.add(departmentCode);
			}
		}
	}

	/**
	 * 用户是所属部门二级部门或者是二级部门一下，获取该用户的一级部门Id
	 * @return
	 */
	public String getFirstSector (DepartmentData departmentData, Integer treeLevel) {
		String parentCodes = departmentData.getParentCodes();
		String []parentCode = parentCodes.split(",");
		// 一级部门id
		String secondSector = parentCode[treeLevel - 1];
		return secondSector;
	}


	/**
	 * 投票成功复选列表
	 * @return
	 */
	@IsFileter(isFile = "true")
	@ResponseBody
	@RequestMapping(value = "pollResults", method = RequestMethod.GET)
	public ReturnInfo pollResults (String userId) {
		if (StringUtils.isNotEmpty(userId)) {
			List<WishShortlist> wishShortlistList = wishShortlistService.getPollResults(userId);
			for (WishShortlist wishShortlist : wishShortlistList) {
				getImgList(wishShortlist);
				wishShortlist.setPackup(true);
                wishShortlist.setChecked(false);
			}
			return ReturnDate.success(wishShortlistList);
		} else {
			return ReturnDate.error(12001, "参数错误");
		}
	}
}