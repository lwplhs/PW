package com.lwp.blog.service;

import com.lwp.blog.entity.Vo.Login_logVo;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/28/14:14
 * @Description:
 */
public interface LogService {
    /**
     * 增加登录日志
     * @param login_logVo
     */
    void insertLoginLog(Login_logVo login_logVo);
}
