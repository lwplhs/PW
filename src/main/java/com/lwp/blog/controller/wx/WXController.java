package com.lwp.blog.controller.wx;

import com.lwp.blog.controller.admin.IndexController;
import com.lwp.blog.utils.TipException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("wxController")
@RequestMapping("/wx")
@Transactional(rollbackFor = TipException.class)
public class WXController {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("getUser")
    @ResponseBody
    public Map<String,Object> getUser(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("iconUrl","../../image/icon-qiandao.png");
        jsonObject.put("iconText","签到");
        jsonObject.put("pageUrl","../pay/pay");
        List list = new ArrayList();
        list.add(jsonObject);
        Map<String,Object> map = new HashMap();
        map.put("list",list);
        return map;

    }
    @GetMapping("getPic")
    @ResponseBody
    public Map<String,Object> getIndexPic(){
        JSONObject jsonObject = new JSONObject();

        List list = new ArrayList();
        list.add("http://u2575k5964.wicp.vip/media/ueditor/upload/image/20200503/1588494587434065729.png");
        list.add("http://u2575k5964.wicp.vip/media/ueditor/upload/image/20200503/1588494587434065729.png");
        list.add("http://u2575k5964.wicp.vip/media/ueditor/upload/image/20200503/1588494587434065729.png");
        list.add("http://u2575k5964.wicp.vip/media/ueditor/upload/image/20200503/1588494587434065729.png");
        Map map = new HashMap();
        map.put("imgUrls",list);
        return map;
    }
}
