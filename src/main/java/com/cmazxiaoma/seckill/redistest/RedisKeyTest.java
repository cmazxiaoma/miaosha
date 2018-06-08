package com.cmazxiaoma.seckill.redistest;

import com.cmazxiaoma.seckill.redis.GoodsKey;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/10 16:21
 */
public class RedisKeyTest {

    public static void main(String[] args) {
        GoodsKey goodsKey = GoodsKey.getMiaoshaGoodsStock;
        System.out.print(goodsKey.getPrefix());
    }
}
