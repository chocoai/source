/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscoremodify.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.service.ServiceException;
import com.jeesite.common.utils.excel.ExcelImport;
import com.jeesite.common.validator.ValidatorUtils;
import com.jeesite.modules.achievement.cardscore.service.AchCardScoreService;
import com.jeesite.modules.asset.ding.entity.DepartmentData;
import com.jeesite.modules.asset.util.service.AmSeqService;
import com.jeesite.modules.fz.utils.common.Variable;
import com.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.cardscoremodify.entity.AchCardScoreModify;
import com.jeesite.modules.achievement.cardscoremodify.dao.AchCardScoreModifyDao;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;

/**
 * 加减分管理Service
 * @author PhilipGuan
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly=true)
public class AchCardScoreModifyService extends CrudService<AchCardScoreModifyDao, AchCardScoreModify> {

	@Autowired
	private AchCardScoreModifyDao achCardScoreModifyDao;
	@Autowired
	private AmSeqService amSeqService;
	@Autowired
	private AchCardScoreService achCardScoreService;

	/**
	 * 获取单条数据
	 * @param achCardScoreModify
	 * @return
	 */
	@Override
	public AchCardScoreModify get(AchCardScoreModify achCardScoreModify) {
		return super.get(achCardScoreModify);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achCardScoreModify
	 * @return
	 */
	@Override
	public Page<AchCardScoreModify> findPage(Page<AchCardScoreModify> page, AchCardScoreModify achCardScoreModify) {
		return super.findPage(page, achCardScoreModify);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achCardScoreModify
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchCardScoreModify achCardScoreModify) {
		super.save(achCardScoreModify);
		achCardScoreService.updateScoreModifyScore(null, achCardScoreModify.getExamineMonth(), achCardScoreModify.getUserId());
	}
	
	/**
	 * 更新状态
	 * @param achCardScoreModify
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchCardScoreModify achCardScoreModify) {
		super.updateStatus(achCardScoreModify);
	}
	
	/**
	 * 删除数据
	 * @param achCardScoreModify
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchCardScoreModify achCardScoreModify) {
		achCardScoreModifyDao.deleteDb(achCardScoreModify.getScoreModifyCode());
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
			List<AchCardScoreModify> list = ei.getDataList(AchCardScoreModify.class);
			for (AchCardScoreModify achCardScoreModify : list) {
				try{
					if (StringUtils.isEmpty(achCardScoreModify.getSourceDepartCode()) || StringUtils.isEmpty(achCardScoreModify.getUserId())) {
						failureNum++;
						failureMsg.append("<br/>" + failureNum +  "存在为空的来源部门编码或者员工编码");
					} else {
						achCardScoreModify.setScoreModifyCode(amSeqService.getAchCode("JKF"));
						if (StringUtils.isNotEmpty(achCardScoreModify.getSourceDepartCode())) {
							try{
								DepartmentData dingDepartment = departmentList.stream().filter(s ->s.getDepartmentId().equals(achCardScoreModify.getSourceDepartCode())).findFirst().get();
								achCardScoreModify.setSourceDepartName(dingDepartment.getName());
							}catch (NoSuchElementException e) {

							}
						}
						achCardScoreModify.setDataType("0");
						achCardScoreModify.setCreateBy(userName);
						achCardScoreModify.setCreateDate(new Date());
						achCardScoreModify.setIsNewRecord(true);
						this.save(achCardScoreModify);
						successNum++;
						successMsg.append("<br/>" + successNum + " 导入成功");
					}
				} catch (Exception e) {
					failureNum++;
					StringBuilder stringBuilder = new StringBuilder("<br/>").append(failureNum).append(" 导入失败：");
					if (e instanceof ConstraintViolationException){
						List<String> messageList = ValidatorUtils.extractPropertyAndMessageAsList((ConstraintViolationException)e, ": ");
						for (String message : messageList) {
							stringBuilder.append(message).append("; ");
						}
					}else{
						stringBuilder.append(e.getMessage());
					}
					failureMsg.append(stringBuilder);
					logger.error(stringBuilder.toString(), e);
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
	 * 获取我的加扣分,只有在绩效卡状态=60，即终审后才可见
	 * @param userId
	 * @param examineMonth
	 * @return
	 */
	public List<AchCardScoreModify> getMyList (String userId, String examineMonth) {
		return achCardScoreModifyDao.getMyList(userId, examineMonth);
	}

    /**
     * 随机查询一条数据，用于导出模板
     * @return
     */
    public List<AchCardScoreModify> selectTemp () {
        return achCardScoreModifyDao.selectTemp();
    }

	/**
	 * 更新单据状态
	 * @param dataStatus
	 * @param updateBy
	 * @param cardCode
	 * @param userId
	 */
	@Transactional(readOnly=false)
	public void updateStatus(String dataStatus, String updateBy, String cardCode, String userId) {
		achCardScoreModifyDao.updateStatus(dataStatus,updateBy,cardCode,userId);
	}
	
}