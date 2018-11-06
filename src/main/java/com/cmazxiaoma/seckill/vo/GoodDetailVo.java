package com.cmazxiaoma.seckill.vo;

import com.cmazxiaoma.seckill.model.MiaoshaUser;
import lombok.Data;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:44
 */
@Data
public class GoodDetailVo {

    private int miaoshaStatus = 0;

    private int remainSeconds = 0;

    private MiaoshaUser user;

    private GoodsVo goods;
}
