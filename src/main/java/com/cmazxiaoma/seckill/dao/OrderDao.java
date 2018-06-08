package com.cmazxiaoma.seckill.dao;

import com.cmazxiaoma.seckill.domain.MiaoshaOrder;
import com.cmazxiaoma.seckill.domain.MiaoshaUser;
import com.cmazxiaoma.seckill.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:38
 */
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id = #{userId} and goods_id = #{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId();

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values(" +
            "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel}, #{status}, #{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "select last_insert_id()")
    Long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select(
            "select * from order_info where id = #{orderId}"
    )
    OrderInfo getOrderById(@Param("orderId") Long orderId);

    @Delete(
            "delete from order_info"
    )
    void deleteOrders();

    @Delete(
            "delete from miaosha_order"
    )
    void deleteMiaoshaOrders();
}