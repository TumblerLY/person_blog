package com.irm.blog.service;

import com.irm.blog.dao.UserRepository;
import com.irm.blog.po.User;
import com.irm.blog.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tumbler
 */
@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {

        User user=userRepository.findByUsernameAndPassword(username, MD5Util.code(password));

         return user;
    }
}
