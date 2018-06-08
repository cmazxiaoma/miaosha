package com.cmazxiaoma.seckill.rabbitmq;

import com.cmazxiaoma.seckill.domain.MiaoshaUser;
import lombok.Data;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/6/4 11:35
 */
@Data
public class MiaoshaMessage {

    private MiaoshaUser miaoshaUser;

    private Long goodsId;
}
