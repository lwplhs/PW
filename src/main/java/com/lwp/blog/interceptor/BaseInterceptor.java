package com.lwp.blog.interceptor;

import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.UserService;
import com.lwp.blog.service.impl.UserServiceImpl;
import com.lwp.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
@Component
public class BaseInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseInterceptor.class);
    private static final String USER_AGENT = "user_agent";

    @Resource
    private UserService userService;

    private MapCache cache = MapCache.single();

    @Resource
    private Commons commons;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object o)throws Exception{
        String uri = request.getRequestURI();
        LOGGER.info("UserAgent:{}",request.getHeader(USER_AGENT));
        LOGGER.info("用户访问地址：{}，来路地址：{}",uri, IPKit.getIpAddrByRequest(request));
        String contextPath = request.getContextPath();
        LOGGER.info(contextPath);
        //请求拦截处理

        UserVo user = TaleUtils.getLoginUser(request);
        if(null == user){
            Integer uid = TaleUtils.getCookieUid(request);
            if(null != uid){
                user = userService.queryUserById(uid);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY,user);
            }
        }
        if(uri.startsWith(contextPath + "/admin") && !uri.startsWith(contextPath +"/admin/login") && null == user){
            response.sendRedirect(request.getContextPath() +"/admin/login");
            return false;
        }
        //设置get请求的token
        if(request.getMethod().equals("GET")){
            String csrf_token = UUID.UU64();
            // 默认存储30分钟
            cache.hset(Types.CSRF_TOKEN.getType(), csrf_token, uri, 30 * 60);
            request.setAttribute("_csrf_token", csrf_token);
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletRequest.setAttribute("commons",commons);

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }



}
