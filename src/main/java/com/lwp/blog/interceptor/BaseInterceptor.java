package com.lwp.blog.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.config.SysConfig;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

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
    private RedisUtil redisUtil;

    @Resource
    private SysConfig sysConfig;

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
        if(uri.startsWith(contextPath+"/404")){
            response.sendRedirect(request.getContextPath() +"/getPage/404.html");
        }
        request.getSession();
        if(uri.startsWith(contextPath + "/admin") && !uri.startsWith(contextPath +"/admin/login")){
            UserVo user = TaleUtils.getLoginUser(request);
            if (null == user) {
                String uid = TaleUtils.getCookieUid(request);
                if (null != uid) {
                    user = userService.queryUserById(uid);
                    String defaultCookie = sysConfig.getDefaultCookie();
                    String loginUserKey = sysConfig.getLoginUser();
                    Cookie cookie = TaleUtils.cookieRaw(defaultCookie, request);
                    if(null != cookie) {
                        String cookieValue = cookie.getValue();
                        redisUtil.set(loginUserKey + cookieValue, user, 60 * 30);
                    }
                }
            }
            if (null == user) {
                //response.sendRedirect(request.getContextPath() +"/admin/login");
                String url = request.getContextPath() + "/admin/login";
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html; charset=utf-8");
                PrintWriter out = response.getWriter();
                this.toLogin(out, url);
                return false;
            }
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
    public boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header) ? true:false;
        return isAjax;
    }
    private void toLogin(PrintWriter out,String url){
/*        out.print("<html>");
        out.print("<head>");
        out.print("<title>跳转中</title>");*/
        out.print("<script   language= 'javascript'>  ");
        out.print("top.location.href=\""+url+"\";");
/*        out.print(" function openwin(){   ");
        out.print("     top.location.href=\""+url+"\";");
        out.print(" } ");*/
        out.print("</script>");
/*        out.print("</head>");s
        out.print("<body onLoad='openwin()'>");
        out.print("</body>");
        out.print("</html>");*/
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        httpServletRequest.setAttribute("commons",commons);

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }



}
