package com.cmazxiaoma.seckill.access;

import com.cmazxiaoma.seckill.model.MiaoshaUser;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 14:45
 */
public class UserContext {

    private static final ThreadLocal<MiaoshaUser> USERHOLDER = new ThreadLocal<>();

    public static void setUser(MiaoshaUser user) {
        USERHOLDER.set(user);
    }

    public static MiaoshaUser getUser() {
        return USERHOLDER.get();
    }

    public static void remove() {
        USERHOLDER.remove();
    }

}
