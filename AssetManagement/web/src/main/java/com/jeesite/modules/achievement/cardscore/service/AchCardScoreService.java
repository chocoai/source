/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.achievement.cardscore.service;

import java.util.*;
import java.util.stream.Collectors;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.achievement.card.dao.AchCardDao;
import com.jeesite.modules.achievement.card.entity.AchCard;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.achievement.cardscore.entity.AchCardScore;
import com.jeesite.modules.achievement.cardscore.dao.AchCardScoreDao;

/**
 * 绩效卡评分Service
 * @author Philip Guan
 * @version 2018-11-22
 */
@Service
@Transactional(readOnly=true)
public class AchCardScoreService extends CrudService<AchCardScoreDao, AchCardScore> {

	@Autowired
	private AchCardScoreDao achCardScoreDao;

	@Autowired
    private AchCardDao achCardDao;

	//String[] examineNames = new String[] { "KPI-指标", "KPI-任务", "职场基础素养", "价值观", "其他部门", "观察指标/任务" };
	//private static Map<String, String> examineNames = new HashMap() { {
	//	put("KPI-指标","a.工作业绩(85%)");
	//	put("KPI-任务","a.工作业绩(85%)");
	//	put("职场基础素养","b.职场综合(15%)");
	//	put("价值观","b.职场综合(15%)");
	//	put("其他部门","c.加减分");
	//	put("观察指标/任务","c.加减分");
	//} };

	/**
	 * 获取单条数据
	 * @param achCardScore
	 * @return
	 */
	@Override
	public AchCardScore get(AchCardScore achCardScore) {
		return super.get(achCardScore);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param achCardScore
	 * @return
	 */
	@Override
	public Page<AchCardScore> findPage(Page<AchCardScore> page, AchCardScore achCardScore) {
		return super.findPage(page, achCardScore);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param achCardScore
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(AchCardScore achCardScore) {
		super.save(achCardScore);
	}
	
	/**
	 * 更新状态
	 * @param achCardScore
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(AchCardScore achCardScore) {
		super.updateStatus(achCardScore);
	}
	
	/**
	 * 删除数据
	 * @param achCardScore
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(AchCardScore achCardScore) {
		super.delete(achCardScore);
	}

	/**
	 * 创建默认评分表
	 * @param cardCode
	 * @param userId
	 * @param examinedStaffCode
	 */
	@Transactional(readOnly=false)
	public void createDefaultData(String cardCode, String userId, String examinedStaffCode) {
		List<AchCardScore> list = new ArrayList<>();

		List<DictData> dictList = DictUtils.getDictList("ach_examine_group");

		//重新排序
		dictList.sort(Comparator.comparing(DictData::getTreeSort));

		List<DictData> parentDictList = dictList.stream().filter(x->x.getTreeLevel().equals(0)).collect(Collectors.toList());


        //根据数据字典生成评分统计行数据
		parentDictList.forEach(parent->{
			List<DictData> children = dictList.stream().filter(x->x.getParentCode().equals(parent.getDictCode())).collect(Collectors.toList());
			children.forEach(child-> {
				AchCardScore item = new AchCardScore();
				//if(!StringUtils.isBlank(child.getExtend().getExtendS1())){
				//	try{
				//		double v = Double.valueOf(child.getExtend().getExtendS1());
				//		item.setStandardScore(v);
				//	}
				//	catch(Exception e){
				//		item.setStandardScore(0d);
				//	}
                //
				//}

				item.setUserId(userId);
				item.setCardCode(cardCode);
				item.setScoreGroup(child.getDictValue());
				item.setExamineName(child.getDictLabel());
				item.setExamineType(parent.getDictLabel());
				item.setEvaluationScore(0d);
				item.setExamineScore(0d);
				item.setDataStatus("0");

				item.setExaminedStaffCode(examinedStaffCode);
				list.add(item);
			});
		});

		achCardScoreDao.insertBatch(list);

	}

    /**
     * 更新指标统计分数
     * @param cardCode 绩效卡编号
     */
    @Transactional(readOnly=false)
    public void updateTargerScore(String cardCode){
        achCardScoreDao.updateTargerScore(cardCode);
        achCardDao.updateFinalScore(cardCode);
    }

    /**
     * 更新任务统计分数
     * @param cardCode 绩效卡编号
     */
    @Transactional(readOnly=false)
    public void updateMissionScore(String cardCode){
        achCardScoreDao.updateMissionScore(cardCode);
        achCardDao.updateFinalScore(cardCode);
    }

    /**
     * 更新综合统计分数
     * @param cardCode 绩效卡编号
     */
    @Transactional(readOnly=false)
    public void updateSyntheticalScore(String cardCode){
        achCardScoreDao.updateSyntheticalScore(cardCode);
        achCardDao.updateFinalScore(cardCode);
    }

    /**
     * 更新加减分统计分数
     * @param cardCode 绩效卡编号
     * @param month 月份
     * @param scoreGroup 分类，加减分默认3010，来源于数据字典
     */
    @Transactional(readOnly=false)
    public void updateScoreModifyScore(String cardCode, String month, String scoreGroup){
        achCardScoreDao.updateScoreModifyScore(cardCode, month, scoreGroup);
        achCardDao.updateFinalScore(cardCode);
    }

    /**
     * 清空加减分统计分数
     * @param cardCode 绩效卡编号
     * @param month 月份
     * @param scoreGroup 分类，加减分默认3010，来源于数据字典
     */
    @Transactional(readOnly=false)
    public void clearScoreModifyScore(String cardCode, String month, String scoreGroup){
        achCardScoreDao.clearScoreModifyScore(cardCode, month, scoreGroup);
        achCardDao.updateFinalScore(cardCode);
    }
}