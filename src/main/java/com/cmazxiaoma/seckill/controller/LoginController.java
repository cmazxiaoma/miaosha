package com.cmazxiaoma.seckill.controller;

import com.cmazxiaoma.seckill.redis.RedisService;
import com.cmazxiaoma.seckill.result.Result;
import com.cmazxiaoma.seckill.service.MiaoshaUserService;
import com.cmazxiaoma.seckill.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.awt.*;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 16:35
 */
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController extends BaseController {

    @Autowired
    private MiaoshaUserService miaoshaUserService;

    @Autowired
    private RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/do_login")
    @ResponseBody
    public Result<String> doLogin(@Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        String token = miaoshaUserService.login(response, loginVo);
        return Result.success(token);
    }
}
