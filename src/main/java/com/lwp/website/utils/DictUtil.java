package com.lwp.website.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * 参照获取
 * @Auther: liweipeng
 * @Date: 2020/09/28/16:46
 * @Description:
 */
public class DictUtil {

    public static List<Map<String,Object>> getDictByType(String dictType){
        List<Map<String,Object>> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("key",i);
            map.put("value",i);
            list.add(map);
        }
        return list;
    }

}
