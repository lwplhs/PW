package com.lwp.blog.dao;

import com.lwp.blog.entity.Users;
import com.lwp.blog.entity.Vo.Login_logVo;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/27/16:48
 * @Description:
 */
@Component
public interface Login_logVoDao {
    void insertUser(Users user);


    int insertLog(Login_logVo login_logVo);

    Login_logVo selectAllLog();

    int selectCountFalse();
}
