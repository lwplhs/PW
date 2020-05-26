package com.lwp.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.dao.AttachmentDao;
import com.lwp.blog.dao.CarouselDao;
import com.lwp.blog.entity.Vo.AttachmentVo;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.CarouselService;
import com.lwp.blog.service.WebUploadService;
import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/05/04/15:28
 * @Description:
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarouselServiceImpl.class);

    @Resource
    private CarouselDao carouselDao;

    @Resource
    private WebUploadService webUploadService;
    /**
     * 添加首页轮播图
     * @param carouselVo
     * @param userVo
     */
    @Override
    public String saveCarousel(CarouselVo carouselVo, UserVo userVo) {
        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if (StringUtil.isNull(carouselVo.getId())) {
                String id = UUID.createID();
                carouselVo.setId(id);
                carouselVo.setCreateTime(new Date());
                carouselVo.setUpdateTime(new Date());
                carouselVo.setIsDelete("0");
                carouselVo.setCreateUserId(userVo.getUid().toString());
                carouselVo.setUpdateUserId(userVo.getUid().toString());
                if (StringUtil.isNull(carouselVo.getSort())) {
                    String sort = String.valueOf(carouselDao.getMaxSort());
                    carouselVo.setSort(sort);
                }
                num = carouselDao.insertCarousel(carouselVo);
                if(num > 0){
                    jsonObject.put("code","100000");
                    jsonObject.put("msg","添加成功");
                }else {
                    jsonObject.put("code","100001");
                    jsonObject.put("msg","添加失败");
                }

            } else {
                carouselVo.setUpdateTime(new Date());
                carouselVo.setUpdateUserId(userVo.getUid().toString());
                if (StringUtil.isNull(carouselVo.getSort())) {
                    String sort = String.valueOf(carouselDao.getMaxSort());
                    carouselVo.setSort(sort);
                }
                num = carouselDao.updateCarousel(carouselVo);
                if(num > 0){
                    jsonObject.put("code","100000");
                    jsonObject.put("msg","修改成功");
                }else {
                    jsonObject.put("code","100002");
                    jsonObject.put("msg","修改失败");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            jsonObject.put("code","100003");
            jsonObject.put("msg","出现错误，请刷新页面重试");
        }
        return jsonObject.toString();

    }

    /**
     * 获取首页轮播 未删除
     * @return
     */
    @Override
    public List<CarouselVo> getListCarousel() {

        List<CarouselVo> list = carouselDao.getListCarousel();
        for(int i = 0;i < list.size();i++){
            CarouselVo carouselVo = list.get(i);
            String path = carouselVo.getPath();
            if(StringUtil.isNull(path)){
                String temp = webUploadService.getPathById("");
                list.get(i).setPath(temp);
            }
        }
        return list;
    }

    /**
     * 获取首页轮播 -已启用未删除
     * @return
     */
    @Override
    public List<CarouselVo> getListCarouselByStatus() {
        List<CarouselVo> list = carouselDao.getListCarouselByStatus();
        for(int i = 0;i < list.size();i++){
            CarouselVo carouselVo = list.get(i);
            String path = carouselVo.getPath();
            if(StringUtil.isNull(path)){
                String temp = webUploadService.getPathById("");
                list.get(i).setPath(temp);
            }
        }
        return list;
    }


    /**
     * 根据id获取首页轮播图
     * @param id
     * @return
     */
    @Override
    public CarouselVo getCarouselById(String id) {
        CarouselVo carouselVo = new CarouselVo();
        if(!StringUtil.isNull(id)){
            carouselVo = carouselDao.getCarouselById(id);
            String path = carouselVo.getPath();
            if(StringUtil.isNull(path)){
                String temp = webUploadService.getPathById("");
                carouselVo.setPath(temp);
            }
        }
        return carouselVo;
    }

    /**
     * 根据类型 更新首页轮播图
     * @param ids ids
     * @param type 1、启用/未启用 2 删除
     * @return
     */
    @Override
    public boolean updateCarousel(String ids, String type,UserVo userVo) {
        Boolean bool = false;
        if(type != null){
            switch (type){
                case "1":
                    bool = this.updateStatus(ids,userVo);
                    break;
                case "2":
                    bool = this.updateDelete(ids,userVo);
                    break;
                default:
                    break;
            }
        }
        return bool;
    }

    /**
     * 更新状态 已启用的修改为未启用 未启用的修改成已启用
     *
     * @param ids
     * @return
     */
    private boolean updateStatus(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            map.put("updateTime",new Date());
            map.put("updateUserId",userVo.getUid());
            int count = carouselDao.updateCarouselWithStatus(map);
            if(count > 0){
                return true;
            }
        }

        return false;
    }

    /**
     * 更新删除状态，将首页轮播图删除
     * @param ids
     * @return
     */
    private boolean updateDelete(String ids,UserVo userVo){
        List<String> temp = this.toList(ids);
        if(!StringUtil.isNull(temp)){
            Map<String,Object> map = new HashMap<>();
            map.put("ids",temp);
            map.put("updateTime",new Date());
            map.put("updateUserId",userVo.getUid());
            int count = carouselDao.updateCarouseWithDelete(map);
            if(count > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 将 ids 转化成list
     * @param ids
     * @return
     */
    private List<String> toList(String ids){
        List<String> list = new ArrayList();
        String[] args = ids.split(",");
        if(null != args && args.length > 0){
            for(int i = 0;i < args.length;i++){
                list.add(args[i]);
            }
        }
        return list;
    }


}
