package com.lwp.blog.service;

import com.lwp.blog.dao.UserDao;
import com.lwp.blog.entity.Users;
import com.lwp.blog.entity.Vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

public interface UserService {
    /**
     * 测试方法 插入一条数据
     */
    void insertUser();

    /**
     * 保存用户数据
     * @param userVo
     * @return
     */
    Integer insertUser(UserVo userVo);

    /**
     * 通过uid查找对象
     * @param uid
     * @return
     */
    UserVo queryUserById(String uid);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    UserVo login(String username, String password);

    /**
     * 根据主键更新user对象
     * @param userVo
     */
    void updateByUid(UserVo userVo);

}
