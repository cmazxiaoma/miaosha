package com.cmazxiaoma.seckill.vo;

import com.cmazxiaoma.seckill.model.OrderInfo;
import lombok.Data;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:47
 */
@Data
public class OrderDetailVo {

    private GoodsVo goods;

    private OrderInfo order;
}
