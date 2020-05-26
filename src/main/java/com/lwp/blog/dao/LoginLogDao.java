package com.lwp.blog.dao;

import com.lwp.blog.entity.Users;
import com.lwp.blog.entity.Vo.LoginLogVo;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/27/16:48
 * @Description:
 */
@Component
public interface LoginLogDao {
    void insertUser(Users user);


    int insertLog(LoginLogVo login_logVo);

    LoginLogVo selectAllLog();

    int selectCountFalse();
}
