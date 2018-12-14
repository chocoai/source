package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 导购APP商品接口专用
 *
 * @author Jace Xiong
 */
@MyBatisDao
public interface DaoGouDAO {
    /**
     * 分页查询状态=onsale 的商品
     */
    @Select("select * from tb_product where approve_status='onsale' limit #{arg0},#{arg1}")
    List<TbProduct> getTbProductList(int start,int end);

    @Select("select count(1) from tb_product where approve_status = #{arg0}")
    int getCountByOnsale(String state);

    @Select("SELECT * FROM tb_sku where quantity>0 and num_iid=#{arg0}")
    List<TbSku> getSkuList(String numIid);
}
