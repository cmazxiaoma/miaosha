package com.cmazxiaoma.seckill.dao;

import com.cmazxiaoma.seckill.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 10:39
 */
public interface UserDao {

    @Select(
            "select * from user where id = #{id}"
    )
    User getById(@Param("id") Long id);

    @Insert(
            "insert into user(id, name) values(#{id}, #{name})"
    )
    int insert(User user);
}
