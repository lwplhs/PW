package com.lwp.website.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.lwp.website.dao.DictVoDao;
import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.entity.Vo.UserVo;
import com.lwp.website.service.DictService;
import com.lwp.website.utils.StringUtil;
import com.lwp.website.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/29/17:55
 * @Description:
 */
@Service(value = "dictService")
public class DictServiceImpl implements DictService {

    private Logger LOGGER = LoggerFactory.getLogger(DictServiceImpl.class);

    @Resource
    private DictVoDao dictVoDao;

    @Override
    public List getDictList() {
        List<DictVo> dictVoList = dictVoDao.getDictListByNotDelete();
        List temp = new ArrayList();
        for (int i = 0; i < dictVoList.size(); i++) {
            DictVo dictVo = dictVoList.get(i);
            JSONObject jsonObject = new JSONObject();
            String id = dictVo.getId();
            jsonObject.put("id",id);
            String lastId = dictVo.getLastId();
            jsonObject.put("pId",lastId);
            String name = dictVo.getName();
            jsonObject.put("name",name);
            String status = dictVo.getStatus();
            jsonObject.put("status",status);
            String series = dictVo.getSeries();
            jsonObject.put("series",series);
            jsonObject.put("drag",false);
            jsonObject.put("drop",false);
            temp.add(jsonObject);
        }
        return temp;
    }

    @Override
    public int getCountDictByNameId(String id,String name,String lastId) {

        int count = dictVoDao.getCountByName(name,id,lastId);

        return count;
    }

    /**
     * 获取首级 排序值
     * type:1 首级 2 子级
     * @return
     */
    @Override
    public String getSort(String type,String lastId) {
        String sort ="1";
        switch (type){
            case "1":
                sort = dictVoDao.getFirSort();
                break;
            case "2":
                sort = dictVoDao.getSecSort(lastId);
                break;
            default:
                sort = "1";
                break;
        }
        return sort;
    }

    /**
     * 根据id查找
     * @param id
     * @return
     */
    @Override
    public DictVo getDictById(String id) {
        DictVo dictVo = dictVoDao.getDictById(id);
        return dictVo;
    }


    /**
     * type
     * @param dictVo
     * @param userVo
     * @return
     */
    @Transactional
    @Override
    public String saveDict(DictVo dictVo, UserVo userVo) {
        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if(StrUtil.isEmpty(dictVo.getId())){
                String id = UUID.createID();
                dictVo.setId(id);
                //设置fullName
                String series = dictVo.getSeries();
                String fullName = "";
                String firstName = "";
                String separator = "/";
                if("1".equals(series)){
                    fullName = dictVo.getName();
                    firstName = dictVo.getName();
                    separator = "/";
                }else if("2".equals(series)) {
                    DictVo tempVo = dictVoDao.getDictById(dictVo.getLastId());
                    if(null != tempVo){
                        firstName = tempVo.getFirstName();
                        fullName = tempVo.getFullName()+tempVo.getSeparator()+dictVo.getName();
                        separator = tempVo.getSeparator();
                    }
                }
                dictVo.setFullName(fullName);
                dictVo.setSeparator(separator);
                dictVo.setFirstName(firstName);
                num = dictVoDao.insertDict(dictVo);
                if(num > 0){
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "添加成功");
                } else {
                    jsonObject.put("code", "100001");
                    jsonObject.put("msg", "添加失败");
                }

            }else {
                //
                String fullName = "";
                String firstName = "";
                if("1".equals(dictVo.getSeries())){
                    //修改fullName
                    fullName = dictVo.getName();
                    firstName = dictVo.getName();
                    dictVo.setFirstName(firstName);
                    dictVo.setFullName(fullName);
                    //修改所有子节点
                    List<DictVo> list = dictVoDao.getSubData(dictVo.getId());
                    for (int i = 0; i < list.size(); i++) {
                        DictVo nestDictVo = list.get(i);
                        nestDictVo.setFirstName(dictVo.getFirstName());
                        nestDictVo.setLastName(dictVo.getName());
                        nestDictVo.setFullName(dictVo.getFullName()+nestDictVo.getSeparator()+nestDictVo.getName());
                        dictVoDao.updateDict(nestDictVo);
                    }
                }else if("2".equals(dictVo.getSeries())){
                    //获取上级
                    DictVo lastDictVo = dictVoDao.getDictById(dictVo.getLastId());
                    dictVo.setLastName(lastDictVo.getName());
                    dictVo.setFullName(lastDictVo.getFullName()+dictVo.getSeparator()+dictVo.getName());
                    dictVo.setFirstName(lastDictVo.getFirstName());
                }
                num = dictVoDao.updateDict(dictVo);
                if (num > 0) {
                    jsonObject.put("code", "100000");
                    jsonObject.put("msg", "修改成功");
                } else {
                    jsonObject.put("code", "100002");
                    jsonObject.put("msg", "修改失败");
                }
            }
        }catch (Exception e){
            jsonObject.put("code","100003");
            jsonObject.put("msg","出现错误，请刷新页面重试");
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 获取数据
     * @param id
     * @return
     */
    @Override
    public List getSubData(String id) {
        List<DictVo> list = dictVoDao.getSubData(id);
        List temp = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            DictVo dictVo = list.get(i);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",dictVo.getId());
            jsonObject.put("name",dictVo.getName());
            jsonObject.put("describe",dictVo.getDescribe());
            jsonObject.put("status",dictVo.getStatus());
            jsonObject.put("sort",dictVo.getSort());
            jsonObject.put("lastName",dictVo.getLastName());
            temp.add(jsonObject);
        }


        return temp;
    }

    /**
     * 根据id 获取series
     * @param id
     * @return
     */
    @Override
    public String getSeriesById(String id) {

        DictVo dictVo = dictVoDao.getDictById(id);
        String series = "";
        if(null != dictVo){
            series = dictVo.getSeries();
        }
        return series;
    }

    @Override
    public List getListByName(String name) {
        DictVo dictVo = dictVoDao.getDictByName(name);
        List<DictVo> list = new ArrayList();
        if(null != dictVo) {
            String id = dictVo.getId();
            list = dictVoDao.getSubData(id);
        }
        return list;
    }

    @Override
    public Boolean updateDictWithType(String type, String id, UserVo userVo) {
        switch (type){
            case "1":
                return updateStatus(id,userVo);
            case "2":
                return updateDelete(id,userVo);
        }
        return false;
    }

    /**
     * 修改状态 启用/未启用
     * @param id
     * @param userVo
     * @return
     */
    private Boolean updateStatus(String id,UserVo userVo){

        //修改 id的状态
        int num = dictVoDao.updateDictWithStatusById(id);
        if(num > 0){
            return true;
        }
        return false;
    }

    @Transactional
    public Boolean updateDelete(String id,UserVo userVo){
        //删除 id
        int num = dictVoDao.updateDictStatusById(id,"2");
        if(num > 0){
            dictVoDao.updateDictStatusByLastId(id,"2");
            return true;
        }
        return false;
    }
}
