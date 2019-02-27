/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.service;

import java.util.*;
import java.util.stream.Collectors;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.collect.MapUtils;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlatResult;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.utils.DictUtils;
import com.jeesite.modules.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlat;
import com.jeesite.modules.storevideo.video.dao.SvVideoRlatDao;

/**
 * 店铺视频关系Service
 * @author Philip Guan
 * @version 2019-01-16
 */
@Service
@Transactional(readOnly=true)
public class SvVideoRlatService extends CrudService<SvVideoRlatDao, SvVideoRlat> {

    private static final String REDIS_KEY_SV_FOLDER = "sv:";
    private static final String REDIS_KEY_SV_TYPE = "sv:type:";
    private static final String videoTypeKey = "sv_type";
    private RedisUtil<String, String> redisUtil;
    @Autowired
    private SvVideoRlatDao svVideoRlatDao;

    private static Map<String, Integer> videoTypeScore;

    /**
     * 视频类型默认分值：{ "性别": 10，"年龄": 20 }
     * @return
     */
    public static synchronized Map<String, Integer> getVideoTypeScore() {
        if(videoTypeScore == null){
            videoTypeScore = new HashMap<>();
            List<DictData> dictList = DictUtils.getDictList(videoTypeKey).stream().filter(a->a.getParentCode().equals("0")).collect(Collectors.toList());
            for (DictData dictData:dictList){
                videoTypeScore.put(dictData.getDictCode(), Integer.valueOf(dictData.getDictValue()));
            }
        }
        return videoTypeScore;
    }

    //public void setVideoTypeSelector() {
    //    List<DictData> dictList = DictUtils.getDictList(videoTypeKey).stream().filter(a->a.getParentCode().equals("0")).collect(Collectors.toList());
    //    for (DictData dictData:dictList){
    //        List<String> map = new ArrayList<>();
    //        String setName = dictData.getDescription();
    //        if(ListUtils.isEmpty(dictData.getChildList())){
    //
    //        } else {
    //            map = dictData.getChildList().stream().map(DictData::getDictLabel).collect(Collectors.toList());
    //        }
    //        redisUtil.opsForList().rightPushAll(setName, map);
    //    }
    //}

	/**
	 * 获取单条数据
	 * @param svVideoRlat
	 * @return
	 */
	@Override
	public SvVideoRlat get(SvVideoRlat svVideoRlat) {
		return super.get(svVideoRlat);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param svVideoRlat
	 * @return
	 */
	@Override
	public Page<SvVideoRlat> findPage(Page<SvVideoRlat> page, SvVideoRlat svVideoRlat) {
		return super.findPage(page, svVideoRlat);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param svVideoRlat
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(SvVideoRlat svVideoRlat) {
		super.save(svVideoRlat);
	}
	
	/**
	 * 更新状态
	 * @param svVideoRlat
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(SvVideoRlat svVideoRlat) {
		super.updateStatus(svVideoRlat);
	}
	
	/**
	 * 删除数据
	 * @param svVideoRlat
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(SvVideoRlat svVideoRlat) {
		super.delete(svVideoRlat);
	}

    @Transactional(readOnly=false)
    public void deleteByVideoId(String videoId) {
        svVideoRlatDao.deleteByVideoId(videoId);
    }

    /**
     * 获取视频列表
     * @param map
     * @return
     */
	public List<SvVideoRlatResult> getVideoListByMap(Map<String, String> map){
        if(MapUtils.isEmpty(map)) return null;
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()){
            stringBuilder.append(" OR (dimension_id = '").append(entry.getKey()).append("' and dimension_value = '").append(entry.getValue()).append("')");
        }
        String whereSql = stringBuilder.substring(3);
        return svVideoRlatDao.getData(whereSql);
    }


	private static void buildVedioTypeScore(List<DictData> dictList, Map<String, Integer> typeScore){
        for(DictData dictData : dictList){
            //List<DictData> children = DictUtils.getDictList(dicType);
            //if(ListUtils.isNotEmpty(children) && children.size() > 0){
            //
            //}
            if(ListUtils.isEmpty(dictData.getChildList())){
                buildVedioTypeScore(dictData.getChildList(), typeScore);
            }
            else
                typeScore.put(dictData.getDictCode(), Integer.valueOf(dictData.getDictValue()));
        }
    }

    @Transactional(readOnly=false)
    public void insertBatch(List<SvVideoRlat> list) {
        svVideoRlatDao.insertBatch(list);
    }
}