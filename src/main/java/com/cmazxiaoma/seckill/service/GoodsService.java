package com.cmazxiaoma.seckill.service;

import com.cmazxiaoma.seckill.dao.GoodsDao;
import com.cmazxiaoma.seckill.domain.Goods;
import com.cmazxiaoma.seckill.domain.MiaoshaGoods;
import com.cmazxiaoma.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 13:12
 */
@Service
public class GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public boolean reduceStock(GoodsVo goods) {
        MiaoshaGoods miaoshaGood = new MiaoshaGoods();
        miaoshaGood.setGoodsId(goods.getId());
        return goodsDao.reduceStock(miaoshaGood) > 0;
    }

    public void resetStock(List<GoodsVo> goodsVoList) {
        for (GoodsVo goods : goodsVoList) {
            MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
            miaoshaGoods.setGoodsId(goods.getId());
            miaoshaGoods.setStockCount(goods.getStockCount());
            goodsDao.resetStock(miaoshaGoods);
        }
    }
}
