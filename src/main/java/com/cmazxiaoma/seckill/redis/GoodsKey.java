package com.cmazxiaoma.seckill.redis;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/10 13:34
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");

    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");

    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");
}
