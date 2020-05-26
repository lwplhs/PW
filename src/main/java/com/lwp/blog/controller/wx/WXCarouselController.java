package com.lwp.blog.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.config.SysConfig;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.service.CarouselService;
import com.lwp.blog.utils.StringUtil;
import com.sun.tools.hat.internal.model.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/09/11:48
 * @Description:
 */
@RestController("WXCarouselController")
@RequestMapping("/wx/carousel")
public class WXCarouselController {

    private static String RootUrl="http://192.168.31.40:8888";

    @Autowired
    private SysConfig sysConfig;

    @Resource
    private CarouselService carouselService;

    @GetMapping("getCarousel")
    @ResponseBody
    public Map<String,Object> getCarousels(){
        JSONObject jsonObject = new JSONObject();
        String path = sysConfig.getPersonal_url();
        if(!StringUtil.isNull(path)){
            RootUrl = path;
        }
        List l = carouselService.getListCarouselByStatus();
        List list = new ArrayList();
        for(int i = 0; i < l.size();i++) {
            list.add(RootUrl + ((CarouselVo)l.get(i)).getPath());
        }
        Map map = new HashMap();
        map.put("imgUrls",list);
        return map;
    }
}
