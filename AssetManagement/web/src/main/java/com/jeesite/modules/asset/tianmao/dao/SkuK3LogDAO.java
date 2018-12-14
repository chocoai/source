package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Insert;

/**
 * 更新到商品资料sku中K3物料名称
 *
 * @author Jace Xiong
 */
@MyBatisDao
public interface SkuK3LogDAO {
    /**
     * 插入日志表
     * @param log 日志
     * @param date 时间
     * @return 插入记录数
     */
    @Insert("insert into tb_k3_log(log,date) values(#{arg0},#{arg1})")
    int inert(String log,String date);
}
