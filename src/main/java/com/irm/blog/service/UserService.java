package com.irm.blog.service;

import com.irm.blog.po.User;

/**
 * @author Tumbler
 */
public interface UserService {

    User checkUser(String username,String password);
}
