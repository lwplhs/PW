package com.lwp.blog.controller.admin;

import com.lwp.blog.controller.BaseController;
import com.lwp.blog.entity.Bo.RestResponseBo;
import com.lwp.blog.entity.Vo.Login_logVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.LogService;
import com.lwp.blog.service.UserService;
import com.lwp.blog.utils.TaleUtils;
import com.lwp.blog.utils.TipException;
import com.lwp.blog.utils.WebConst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @GetMapping(value = {"","/index"})
    public String index(HttpServletResponse response){
        return this.render("index");
    }


    @GetMapping(value = "login")
    public String login(){
        return "/admin/login";
    }

    @PostMapping(value = "login")
    @ResponseBody
    public RestResponseBo doLogin(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam (required = false) String remeber_me,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        HttpSession session = request.getSession();
        Integer error_count = cache.get("login_error_count");
        Login_logVo login_logVo = new Login_logVo();
        login_logVo.setUserName(username);
        login_logVo.setLoginUrl("1");
        login_logVo.setLoginResult("0");
        logService.insertLoginLog(login_logVo);
        if(null != error_count && error_count >=3){
            return RestResponseBo.fail("您输入的密码已经错误超过3次，请10分钟后尝试");

        }else {
            try {
                UserVo user = userService.login(username,password);
                request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY,user);
                if(StringUtils.isNotBlank(remeber_me)){
                    TaleUtils.setCookie(response,user.getUid());
                }
            }catch (Exception e){
                error_count = null == error_count ? 1 :error_count +1;
                if(error_count > 3){
                    return RestResponseBo.fail("您输入密码已经错误超过三次，请10分钟后尝试");
                }
                cache.set("login_error_count",error_count,10*60);
                String msg = "登录失败";
                if(e instanceof TipException){
                    msg = e.getMessage();
                }else {
                    LOGGER.error(msg,e);
                }
                return RestResponseBo.fail(msg);
            }
        }




        return RestResponseBo.ok();
    }

}
