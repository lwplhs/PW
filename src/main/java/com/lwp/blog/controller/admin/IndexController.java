package com.lwp.blog.controller.admin;

import com.lwp.blog.config.SysConfig;
import com.lwp.blog.controller.BaseController;
import com.lwp.blog.entity.Bo.RestResponseBo;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.entity.Vo.LoginLogVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.CarouselService;
import com.lwp.blog.service.LogService;
import com.lwp.blog.service.UserService;
import com.lwp.blog.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * 后台管理首页
 * @Auther: liweipeng
 * @Date: 2019/12/20/9:38
 * @Description:
 */

@Controller("adminIndexController")
@RequestMapping("/admin")
@Transactional(rollbackFor = TipException.class)
public class IndexController extends BaseController {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private SysConfig sysConfig;


    @GetMapping(value = {"","/index"})
    public String index(HttpServletResponse response){
        return this.render("admin/index");
    }
    @GetMapping(value={"/getPage/{name}", "/getPage/{name}.html"})
    public String getHtml(HttpServletResponse response,@PathVariable String name){

        return this.render("admin/"+name);
    }

    @GetMapping(value = "/main")
    public String getMainHtml(HttpServletResponse response){
        return this.render("admin/main");
    }

    @GetMapping(value = "login")
    public String login(){
        return "/admin/login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam (required = false) String rememberMe,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        Integer error_count = UserRedisUtil.getErrorCount(request);
        if(null != error_count && error_count >=3){
            LoginLogVo login_logVo = new LoginLogVo(UUID.createID(),username,"0","登录失败次数超过3次，请10分钟后尝试", IPKit.getIpAddrByRequest(request));
            logService.insertLoginLog(login_logVo);
            return RestResponseBo.fail("您输入的密码已经错误超过3次，请10分钟后尝试");
        }else {
            try {
                UserVo user = userService.login(username,password);
                UserRedisUtil.insertOrUpdateUserSession(request,user);
                if(StringUtils.isNotBlank(rememberMe)){
                    TaleUtils.setCookie(response,user.getId());
                }
                UserRedisUtil.insertErrorCount(request,error_count);
                LoginLogVo login_logVo = new LoginLogVo(UUID.createID(),username,"1","登录成功",IPKit.getIpAddrByRequest(request));
                logService.insertLoginLog(login_logVo);
                LOGGER.info("用户： "+username+"登录成功");
            }catch (Exception e){
                error_count = null == error_count ? 1 :error_count +1;
                if(error_count > 3){
                    LoginLogVo login_logVo = new LoginLogVo(UUID.createID(),username,"0","登录失败次数超过3次，请10分钟后尝试", IPKit.getIpAddrByRequest(request));
                    logService.insertLoginLog(login_logVo);
                    return RestResponseBo.fail("您输入密码已经错误超过三次，请10分钟后尝试");
                }
                UserRedisUtil.insertErrorCount(request,error_count);
                String msg = "登录失败";
                if(e instanceof TipException){
                    msg = e.getMessage();
                }
                LOGGER.error(msg,e);
                LoginLogVo login_logVo = new LoginLogVo(UUID.createID(),username,"0","登录失败,登录失败次数:"+String.valueOf(error_count), IPKit.getIpAddrByRequest(request));
                logService.insertLoginLog(login_logVo);
                return RestResponseBo.fail(msg);
            }
        }
        return RestResponseBo.ok();
    }
    @PostMapping(value = "/logout")
    @ResponseBody
    public RestResponseBo logout(@CookieValue("${website.defaultCookie}") String cookie,
                                 HttpServletRequest request,
                                 HttpServletResponse response){
        String loginUserKey = sysConfig.getLoginUser();
        try {
            UserVo userVo = TaleUtils.getLoginUserByRedis(request);
            UserRedisUtil.delUserSession(request);
            LOGGER.info("用户: "+userVo.getUsername()+"退出系统");
            return RestResponseBo.ok();
        }catch (Exception e){
            e.printStackTrace();
            return RestResponseBo.fail();
        }
    }
}
