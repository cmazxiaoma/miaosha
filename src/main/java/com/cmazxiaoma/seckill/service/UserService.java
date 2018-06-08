package com.cmazxiaoma.seckill.service;

import com.cmazxiaoma.seckill.dao.UserDao;
import com.cmazxiaoma.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cmazxiaoma
 * @version V1.0
 * @Description: TODO
 * @date 2018/5/29 13:13
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User getById(Long id) {
        return userDao.getById(id);
    }

    public void insert(User user) {
        userDao.insert(user);
    }
}
