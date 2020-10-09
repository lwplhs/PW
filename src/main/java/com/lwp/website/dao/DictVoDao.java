package com.lwp.website.dao;

import com.lwp.website.entity.Vo.DictVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/29/17:10
 * @Description:
 */
public interface DictVoDao {

    List<DictVo> getDictListByNotDelete();

}
