package com.lwp.blog.dao;

import com.lwp.blog.entity.Users;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    void insertUser(Users user);
}
