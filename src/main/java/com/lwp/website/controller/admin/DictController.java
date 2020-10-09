package com.lwp.website.controller.admin;

import com.lwp.website.controller.BaseController;
import com.lwp.website.entity.Bo.RestResponseBo;
import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.service.DictService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/29/16:38
 * @Description:
 */
@Controller
@RequestMapping(value = "/admin/dict")
public class DictController extends BaseController {

    @Resource
    private DictService dictService;

    @GetMapping("/manager")
    public String manager(){
        return this.render("/admin/dict/manager");
    }

    @PostMapping(value = "/getData")
    @ResponseBody
    public RestResponseBo getData(){
        List<DictVo> list = dictService.getDictList();
        RestResponseBo<List<DictVo>> dictVoRestResponseBo = new RestResponseBo<List<DictVo>>(true,list);
        return dictVoRestResponseBo;
    }

}
