package com.irm.blog.web.admin;


import com.irm.blog.po.Blog;
import com.irm.blog.po.User;
import com.irm.blog.service.BlogService;
import com.irm.blog.service.TagService;
import com.irm.blog.service.TypeService;
import com.irm.blog.vo.BlogQuery;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Tumbler
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT="admin/blogs-input";
    private static final String List="admin/blogs";
    private static final String REDIRECT_List="redirect:/admin/blogs";

    @Autowired
    private BlogService blogsService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(Model model,
           @PageableDefault(size = 2,sort ="updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogsService.listBlog(pageable,blog));
        return List;
    }


    //博客搜索按钮
    @PostMapping ("/blogs/search")
    public String searchblogs(Model model,
                        @PageableDefault(size = 2,sort ="updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog){
        model.addAttribute("page",blogsService.listBlog(pageable,blog));


        return "admin/blogs::blogList";
    }

    //新增博客
    @GetMapping( "/blogs/input")
    public String inputBlogs(Model model){

        model.addAttribute("blog",new Blog());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        return INPUT;

    }



    //修改博客
    @GetMapping( "/blogs/{id}/input")
    public String editBlogs(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogsService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);

        return INPUT;

    }

    //新增提交
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){

        //获取当前的用户
            blog.setUser((User) session.getAttribute("user"));
            blog.setType(typeService.getType(blog.getType().getId()));
            blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b = blogsService.saveBlog(blog);
        if (b==null)
        {
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }

        return REDIRECT_List;

    }



    @GetMapping("/blogs/{id}/delete")

    public String deleteBlogs(@PathVariable Long id ,RedirectAttributes redirectAttributes){
        blogsService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return REDIRECT_List;
    }


}
