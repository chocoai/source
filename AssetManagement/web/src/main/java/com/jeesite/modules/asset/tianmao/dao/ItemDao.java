package com.jeesite.modules.asset.tianmao.dao;

import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.asset.tianmao.entity.BigItem;
import com.jeesite.modules.asset.tianmao.entity.TbProduct;
import com.jeesite.modules.asset.tianmao.entity.TbSku;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 类描述
 *
 * @author Jace Xiong
 */
@MyBatisDao
public interface ItemDao {
    @Insert("INSERT INTO tb_tianmao_items VALUES(#{id},#{body},#{time})")
    int insert(BigItem item);

    @Update("UPDATE tb_tianmao_items SET body=#{body},time=#{time} where id=#{id}")
    int update(BigItem item);

    @Select("select * from tb_tianmao_items where id=#{arg0}")
    BigItem selectBigItemBynumId(String id);

    @Delete("delete from tb_tianmao_items where id=#{arg0}")
    int deleteBigItemBynumId(String id);

    @Insert("INSERT INTO tb_product(num_iid,title,pic_url," +
            "cid,approve_status," +
            "price,detail_url," +
            "list_time," +
            "delist_tim," +
            "modified," +
            "nick,outer_code) " +
            "VALUES(#{numIid}," +
            "#{title}," +
            "#{picUrl}," +
            "#{cid}," +
            "#{approveStatus},#{price},#{detailUrl},#{listTime},#{delistTim},#{modified},#{nick},#{outerCode})")
    int insertProdect(TbProduct tbProduct);

    @Update("insert into tb_sku VALUES(#{skuId},#{numIid},#{propertiesName},#{price},#{quantity},#{properties},#{created},#{modified},#{status},#{barcode},#{outerId},#{k3Name},#{realPrice},#{preSale})")
    int insertSku(TbSku tbSku);

    @Delete("DELETE FROM tb_sku WHERE num_iid = #{arg0}")
    int deleteSku(Long numIid);

    @Delete("DELETE FROM tb_item_imgs WHERE item_id = #{arg0}")
    int deleteImg(Long numIid);

    @Select("SELECT * FROM tb_sku WHERE num_iid = #{arg0}")
    List<TbSku> selectByNumId(Long numIid);
}
