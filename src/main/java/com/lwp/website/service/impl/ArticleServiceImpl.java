package com.lwp.website.service.impl;

import com.lwp.website.dao.ContentVoDao;
import com.lwp.website.entity.Vo.ContentVo;
import com.lwp.website.service.ArticleService;
import com.lwp.website.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/04/27/11:35
 * @Description:
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(com.lwp.website.service.UserService.class);

    @Resource
    private ContentVoDao contentVoDao;

    @Override
    public void insertArticle(ContentVo contentVo) {
        String uuid = UUID.createID();
        contentVo.setCid(uuid);
        contentVoDao.insert(contentVo);
        LOGGER.info(uuid);

    }

    @Override
    public List<ContentVo> listArticle() {
        /*ContentVoExample contentVoExample = new ContentVoExample();*/
        List<ContentVo> list = contentVoDao.listContentVo();
        return list;
    }
}
