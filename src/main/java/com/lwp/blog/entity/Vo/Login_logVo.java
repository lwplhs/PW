package com.lwp.blog.entity.Vo;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/03/27/16:39
 * @Description:
 */
public class Login_logVo {
    private Integer id;
    private String userName;
    private Integer loginTime;
    private String loginResult;
    private String loginResultDetail;
    private String loginUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLoginResultDetail() {
        return loginResultDetail;
    }

    public void setLoginResultDetail(String loginResultDetail) {
        this.loginResultDetail = loginResultDetail;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
