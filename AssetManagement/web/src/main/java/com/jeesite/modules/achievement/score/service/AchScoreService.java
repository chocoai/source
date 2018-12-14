/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.score.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.ding.entity.DingDepartment;
import com.jeesite.modules.asset.ding.entity.DingUser;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.score.entity.AchScore;
import com.jeesite.modules.achievement.score.dao.AchScoreDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

/**
 * 加减分管理Service
 * @author len
 * @version 2018-11-16
 */
@Service
@Transactional(readOnly=true)
public class AchScoreService extends CrudService<AchScoreDao, AchScore> {
	@Autowired
	private AchScoreDao achScoreDao;
	@Autowired
	private AmSeqService amSeqService;
	/**
	 * 获取单条数据
	 * @param achScore
	 * @return
	 */
	@Override
	public AchScore get(AchScore achScore) {
		return super.get(achScore);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achScore
	 * @return
	 */
	@Override
	public Page<AchScore> findPage(Page<AchScore> page, AchScore achScore) {
		return super.findPage(page, achScore);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achScore
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchScore achScore) {
		super.save(achScore);
	}
	
	/**
	 * 更新状态
	 * @param achScore
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchScore achScore) {
		super.updateStatus(achScore);
	}
	
	/**
	 * 删除数据
	 * @param achScore
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchScore achScore) {
		achScoreDao.deleteDb(achScore.getBillCode());
//		super.delete(achScore);
	}


	@Resource
	private RedisTemplate<String, List> redisTemplate;
	/**
	 * 导入用户数据
	 * @param file 导入的用户数据文件
	 * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
	 */
	@Transactional(readOnly=false)
	public String importData(MultipartFile file, Boolean isUpdateSupport) {
		if (file == null){
			throw new ServiceException("请选择导入的数据文件！");
		}
		int successNum = 0; int failureNum = 0;
		StringBuilder successMsg = new StringBuilder();
		StringBuilder failureMsg = new StringBuilder();
		List<DepartmentData> departmentList = redisTemplate.opsForValue().get("dingDepartment" + Variable.dataBase + Variable.RANDOMID);
		// 当前登录用户
		String userName = UserUtils.getUser().getUserName();
		try(ExcelImport ei = new ExcelImport(file, 2, 0)){
			List<AchScore> list = ei.getDataList(AchScore.class);
			for (AchScore achScore : list) {
				try{
					if (StringUtils.isEmpty(achScore.getSourceDepartCode()) || StringUtils.isEmpty(achScore.getStaffCode())) {
						failureNum++;
						failureMsg.append("<br/>" + failureNum +  "存在为空的来源部门编码或者员工编码");
					} else {
						achScore.setBillCode(amSeqService.getAchCode("JKF"));
						if (StringUtils.isNotEmpty(achScore.getSourceDepartCode())) {
							try{
								DepartmentData dingDepartment = departmentList.stream().filter(s ->s.getDepartmentId().equals(achScore.getSourceDepartCode())).findFirst().get();
								achScore.setSourceDepartName(dingDepartment.getName());
							}catch (NoSuchElementException e) {

							}
						}
						achScore.setDataType("0");
						achScore.setCreateBy(userName);
						achScore.setCreateDate(new Date());
						achScore.setIsNewRecord(true);
						this.save(achScore);
						successNum++;
						successMsg.append("<br/>" + successNum + " 导入成功");
					}
				} catch (Exception e) {
					failureNum++;
					String msg = "<br/>" + failureNum  + " 导入失败：";
					if (e instanceof ConstraintViolationException){
						List<String> messageList = ValidatorUtils.extractPropertyAndMessageAsList((ConstraintViolationException)e, ": ");
						for (String message : messageList) {
							msg += message + "; ";
						}
					}else{
						msg += e.getMessage();
					}
					failureMsg.append(msg);
					logger.error(msg, e);
				}
			}
		} catch (Exception e) {
			failureMsg.append(e.getMessage());
			logger.error(e.getMessage(), e);
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new ServiceException(failureMsg.toString());
		}else{
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	}

	/**
	 * 随机查一条数据
	 * @return
	 */
	public List<AchScore> selectTemp () {
		return achScoreDao.selectTemp();
	}
}