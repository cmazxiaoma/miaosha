package com.cmazxiaoma.seckill.dao;

import com.cmazxiaoma.seckill.core.Mapper;
import com.cmazxiaoma.seckill.model.Goods;
import com.cmazxiaoma.seckill.model.MiaoshaGoods;
import com.cmazxiaoma.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:38
 */
public interface GoodsDao {

    @Select(
            "select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id"
    )
    List<GoodsVo> listGoodsVo();

    @Select(
            "select g.*, mg.stock_count, mg.start_date, mg.end_date, mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}"
    )
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") Long goodsId);

    @Update(
            "update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0"
    )
    int reduceStock(MiaoshaGoods miaoshaGoods);

    @Update(
            "update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}"
    )
    int resetStock(MiaoshaGoods miaoshaGoods);
}
