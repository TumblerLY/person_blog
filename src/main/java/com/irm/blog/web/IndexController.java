package com.irm.blog.web;


import com.irm.blog.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;

/**
 * @author Tumbler
 */
@Controller
public class IndexController {


    @GetMapping("/")
    public String index() {
//        int i=9/0;
//        String blog=null;
//        if (blog==null){
//            throw new NotFoundException("博客找不到");
//        }

        return "index";
    }

}
