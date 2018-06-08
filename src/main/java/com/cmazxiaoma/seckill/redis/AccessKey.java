package com.cmazxiaoma.seckill.redis;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/10 13:31
 */
public class AccessKey extends BasePrefix {

    private AccessKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static AccessKey withExpire(int exipreSeconds) {
        return new AccessKey(exipreSeconds, "access");
    }
}
