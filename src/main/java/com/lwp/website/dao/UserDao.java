package com.lwp.website.dao;

import com.lwp.website.entity.Users;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    void insertUser(Users user);
}
