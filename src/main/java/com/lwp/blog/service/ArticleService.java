package com.lwp.blog.service;

import com.lwp.blog.entity.Vo.ContentVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/04/27/11:34
 * @Description:
 */
public interface ArticleService {
    void insertArticle(ContentVo contentVo);

    List<ContentVo> listArticle();
}
