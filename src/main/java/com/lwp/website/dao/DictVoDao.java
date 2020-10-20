package com.lwp.website.dao;

import com.lwp.website.entity.Vo.DictVo;
import com.lwp.website.utils.DictUtil;

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

    /**
     * 获取首级排序值
     * @return
     */
    String getFirSort();

    /**
     * 查找子级排序值
     * @param lastId
     * @return
     */
    String getSecSort(String lastId);

    /**
     * 查找是否有相同的名称 id 不为空 则查找排除该id的name
     * @return
     */
    int getCountByName(String name,String id,String lastId);


    int insertDict(DictVo dictVo);

    int updateDict(DictVo dictVo);

    DictVo getDictById(String id);

    List<DictVo> getSubData(String lastId);

    DictVo getDictByName(String name);


}
