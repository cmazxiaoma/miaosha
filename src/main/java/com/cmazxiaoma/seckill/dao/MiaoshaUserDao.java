package com.cmazxiaoma.seckill.dao;

import com.cmazxiaoma.seckill.core.Mapper;
import com.cmazxiaoma.seckill.model.MiaoshaUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:38
 */
public interface MiaoshaUserDao {

    @Select(
            "select * from miaosha_user where id = #{id}"
    )
    MiaoshaUser getById(@Param("id") Long id);

    @Update(
            "update miaosha_user set password = #{password} where id = #{id}"
    )
    void update(MiaoshaUser miaoshaUser);
}
