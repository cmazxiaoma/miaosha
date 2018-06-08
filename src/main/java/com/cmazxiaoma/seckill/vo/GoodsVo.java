package com.cmazxiaoma.seckill.vo;

import com.cmazxiaoma.seckill.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:41
 */
@Data
public class GoodsVo extends Goods {

    private Double miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;
}
