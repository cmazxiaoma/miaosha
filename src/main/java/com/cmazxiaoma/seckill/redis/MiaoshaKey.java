package com.cmazxiaoma.seckill.redis;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/10 13:40
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");

    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");

    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
}
