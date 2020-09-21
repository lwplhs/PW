package com.vomworthy;

import com.lwp.website.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/16/20:04
 * @Description:
 */
@Controller
@RequestMapping("/vom")
public class VomController extends BaseController {


    @RequestMapping(value = "/js")
    public String js(){

        return this.render("/vom-worthy/js/show.html");
    }

}
