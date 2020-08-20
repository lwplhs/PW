package com.lwp.blog.service.impl;

import com.lwp.blog.dao.UserDao;
import com.lwp.blog.dao.UserVoDao;
import com.lwp.blog.entity.Users;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.entity.Vo.UserVoExample;
import com.lwp.blog.service.UserService;
import com.lwp.blog.utils.TaleUtils;
import com.lwp.blog.utils.TipException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(com.lwp.blog.service.UserService.class);

    @Resource
    private UserDao userDao;
    @Resource
    private UserVoDao userVoDao;

    @Override
    @Transactional
    public void insertUser() {
        Users user = new Users();
        user.setName("李卫朋");
        user.setSex("男");
        userDao.insertUser(user);
    }

    @Override
    public Integer insertUser(UserVo userVo) {
        int j =  userVoDao.insert(userVo);
        LOGGER.info(String.valueOf(j));
        return null;
    }

    @Override
    public UserVo queryUserById(String uid) {
        return null;
    }

    @Override
    public UserVo login(String username, String password) {
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            throw new TipException("用户名和密码不能为空");
        }
        UserVoExample example = new UserVoExample();
        UserVoExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        long count = userVoDao.countByExample(example);
        if(count < 1){
            throw new TipException("用户名或密码错误");
        }
        String pwd = TaleUtils.MD5encode(username + password);
        criteria.andPasswordEqualTo(pwd);
        List<UserVo> userVos = userVoDao.selectByExample(example);
        if(userVos.size() != 1){
            throw new TipException("用户名或密码错误");
        }
        return userVos.get(0);
    }

    @Override
    public void updateByUid(UserVo userVo) {

    }
}
