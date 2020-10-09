package com.lwp.website.service;

import com.lwp.website.entity.Vo.DictVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: liweipeng
 * @Date: 2020/09/29/17:53
 * @Description:
 */
@Service
public interface DictService {

    List<DictVo> getDictList();

}
