package com.cmazxiaoma.seckill.service;

import com.cmazxiaoma.seckill.dao.MiaoshaUserDao;
import com.cmazxiaoma.seckill.model.MiaoshaUser;
import com.cmazxiaoma.seckill.exception.GlobalException;
import com.cmazxiaoma.seckill.redis.MiaoshaUserKey;
import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.result.CodeMsg;
import com.cmazxiaoma.seckill.util.MD5Util;
import com.cmazxiaoma.seckill.util.UUIDUtil;
import com.cmazxiaoma.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 13:13
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserDao miaoshaUserDao;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getById(Long id) {
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, "" + id, MiaoshaUser.class);

        if (!Objects.isNull(user)) {
            return user;
        }
        user = miaoshaUserDao.getById(id);

        if (!Objects.isNull(user)) {
            redisService.set(MiaoshaUserKey.getById, "" + id, user);
        }

        return user;
    }

    /**
     * 先更新数据库，然后再删除缓存
     * 参考http://blog.csdn.net/tTU1EvLDeLFq5btqiK/article/details/78693323
     * @return
     */
    public boolean updatePassword(String token, Long id, String formPass) {
        MiaoshaUser user = getById(id);

        if (Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        MiaoshaUser miaoshaUser = new MiaoshaUser();
        miaoshaUser.setId(id);
        miaoshaUser.setPassword(MD5Util.formPassToDBPass(formPass, user.getSalt()));
        miaoshaUserDao.update(miaoshaUser);

        //处理缓存
        redisService.delete(MiaoshaUserKey.getById, "" + id);
        user.setPassword(miaoshaUser.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);

        return true;
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);

        //延长Cookie有效期
        if (!Objects.isNull(user)) {
            addCookie(response, token, user);
        }
        return user;
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));

        if (Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);

        if (!calcPass.equalsIgnoreCase(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成Cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);

        return token;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
