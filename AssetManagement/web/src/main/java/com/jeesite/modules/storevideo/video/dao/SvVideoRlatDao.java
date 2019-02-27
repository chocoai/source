/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.storevideo.video.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlat;
import com.jeesite.modules.storevideo.video.entity.SvVideoRlatResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 店铺视频关系DAO接口
 * @author Philip Guan
 * @version 2019-01-16
 */
@MyBatisDao
public interface SvVideoRlatDao extends CrudDao<SvVideoRlat> {
    @Select("SELECT * FROM (SELECT sv.video_id AS videoCode,SUM(CONVERT(IFNULL(dic.dict_value,0),SIGNED)) videoScore \n" +
            "FROM sv_video_rlat svr \n" +
            "LEFT JOIN js_sys_dict_data dic on dic.description = svr.dimension_id and dic.parent_code = '0'\n" +
            "LEFT JOIN sv_video sv on sv.video_code = svr.video_code\n" +
            "WHERE ${where} \n" +
            " GROUP BY svr.video_code) T ORDER BY videoScore DESC")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    List<SvVideoRlatResult> getData(@Param(value="where") String whereSql);

    @Delete("DELETE FROM sv_video_rlat WHERE video_code = #{arg0}")    //AND (#{arg2} IS NULL OR card_code = #{arg2})
    void deleteByVideoId(String videoId);
}