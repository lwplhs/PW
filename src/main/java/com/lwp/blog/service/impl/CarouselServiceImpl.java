package com.lwp.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lwp.blog.dao.AttachmentDao;
import com.lwp.blog.dao.CarouselDao;
import com.lwp.blog.entity.Vo.AttachmentVo;
import com.lwp.blog.entity.Vo.CarouselVo;
import com.lwp.blog.entity.Vo.UserVo;
import com.lwp.blog.service.CarouselService;
import com.lwp.blog.utils.StringUtil;
import com.lwp.blog.utils.TaleUtils;
import com.lwp.blog.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(com.lwp.blog.service.UserService.class);

    @Resource
    private CarouselDao carouselDao;

    @Resource
    private AttachmentDao attachmentDao;

    /**
     * 添加首页轮播图
     * @param carouselVo
     * @param userVo
     */
    @Override
    public String insertCarousel(CarouselVo carouselVo,UserVo userVo) {
        JSONObject jsonObject = new JSONObject();
        int num = 0;
        try {
            if (StringUtil.isNull(carouselVo.getId())) {
                String id = UUID.createID();
                carouselVo.setId(id);
                carouselVo.setCreate_time(new Date());
                carouselVo.setUpdate_time(new Date());
                carouselVo.setIs_delete("0");
                carouselVo.setCreate_user_id(userVo.getUid().toString());
                carouselVo.setUpdate_user_id(userVo.getUid().toString());
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
                carouselVo.setUpdate_time(new Date());
                carouselVo.setUpdate_user_id(userVo.getUid().toString());
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
        return list;
    }

    /**
     * 获取首页轮播 -已启用未删除
     * @return
     */
    @Override
    public List<CarouselVo> getListCarouselByStatus() {
        List<CarouselVo> list = carouselDao.getListCarouselByStatus();
        return list;
    }

    /**
     * webUplooad 上传图片 上传到服务器 并将地址信息保存到数据库
     * @param files
     * @param user
     * @return
     */
    @Override
    public Map UploadCarousel(MultipartFile files, UserVo user) {
        Map result = new HashMap();
        boolean b = false;
        //绝对路径
        String path = "";
        //相对路径
        String url = "";
        //文件名称
        String realName = "";
        //文件后缀
        String suffix = "";
        //文件上传时的名称
        String name = "";
        //保存上传文件
        String size = "";
        //图片高宽
        String width = "";
        String height = "";
        try{
            name = files.getOriginalFilename();
            suffix = name.substring(name.lastIndexOf("."));
            Map map = TaleUtils.getCarouselPath(suffix);
            path = map.get("path").toString();
            url = map.get("url").toString();
            realName = map.get("name").toString();
            File uploadFile = new File(path);
            if(!uploadFile.getParentFile().exists()){
                uploadFile.mkdirs();
            }
            size = String.valueOf(files.getSize());
            files.transferTo(uploadFile);
            b = true;
        }catch(Exception e){
            b = false;
            result.put("code","111111");
            result.put("msg","上传失败");
            e.printStackTrace();
        }
        //保存上传文件信息到数据库中
        if(b){
            AttachmentVo attachmentVo = new AttachmentVo();
            attachmentVo.setId(UUID.createID());
            attachmentVo.setSave_name(realName);
            attachmentVo.setOld_name(name);
            attachmentVo.setSave_path(path);
            attachmentVo.setUrl_path(url);
            attachmentVo.setTime(new Date());
            attachmentVo.setUser_id(user.getUid().toString());
            attachmentVo.setType("1");
            attachmentVo.setSuffix(suffix);
            attachmentVo.setSize(size);
            try {
                File picture=new File(path);
                BufferedImage bufferedImage = ImageIO.read(new FileInputStream(picture)); // 通过MultipartFile得到InputStream，从而得到BufferedImage
                if (bufferedImage != null) {
                    LOGGER.info("获取轮播图的高宽");
                    width = String.valueOf(bufferedImage.getWidth());
                    height = String.valueOf(bufferedImage.getHeight());
                    attachmentVo.setWidth(width);
                    attachmentVo.setHeight(height);
                }
                // 省略逻辑判断
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                e.printStackTrace();
            }
            //保存信息
            int num = attachmentDao.insertAttachment(attachmentVo);
            if(num > 0){
                LOGGER.info("保存成功");
            }
            result.put("path",path);
            result.put("url",url);
            result.put("aid",attachmentVo.getId());
            result.put("code","100000");
            result.put("msg","上传成功");
        }
        return result;
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
            map.put("update_time",new Date());
            map.put("update_user_id",userVo.getUid());
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
            map.put("update_time",new Date());
            map.put("update_user_id",userVo.getUid());
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
