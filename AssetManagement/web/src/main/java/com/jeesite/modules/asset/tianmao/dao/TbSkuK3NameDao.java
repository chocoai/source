package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbSkuK3Name;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * sku-k3物料名称表持久层
 *
 * @author Jace Xiong
 */
@MyBatisDao
public interface TbSkuK3NameDao {
    /**
     * 查询是否存在该条记录
     */
    @Select("select * from tb_sku_k3_name where outer_id = #{arg0}")
    List<TbSkuK3Name> selectByOuterId(String outerId);

    /**
     * 插入新的记录
     */
    @Insert("insert into tb_sku_k3_name(sku_id,outer_id,sku_name,date) values(#{arg0},#{arg1},#{arg2},#{arg3})")
    int insertK3Name(Long skuId,String outerId,String k3Name,String date);

    /**
     * 更新数据
     */
    @Update("update tb_sku_k3_name set sku_name=#{arg1} where sku_id=#{arg0}")
    int updateK3Name(Long skuId,String k3Name);
}
