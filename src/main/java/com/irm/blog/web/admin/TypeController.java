package com.irm.blog.web.admin;

import com.irm.blog.po.Type;
import com.irm.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Tumbler
 */

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;


    //分页查询
    @GetMapping("/types")
    public String type(@PageableDefault(size = 10, sort = {"id"},
            direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }


    //分类修改
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";

    }


    //添加分类
    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-input";

    }


    //增加分类
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        //判断是否有重复添加的
        List ty = typeService.FindTypeByName(type.getName());
        if (ty.size() != 0) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }
        System.out.println("查出的重复内容是" + ty);
        //判断是否有错误提示的
        if (result.hasErrors()) {
            return "admin/type-input";
        }

        //添加成功
        Type type1 = typeService.saveType(type);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }

        return "redirect:/admin/types";
    }


    /**
     * 注意BindingResult和Type类型需要绑定在一起
     *
     * @param type
     * @param result
     * @param attributes
     * @return 更新分类如果正确的话就更新成功
     */
    @PostMapping("/types/{id}")
    public String editpost(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        List ty = typeService.FindTypeByName(type.getName());
        if (ty.size() != 0) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }
        System.out.println(ty);

        if (result.hasErrors()) {
            return "admin/type-input";
        }
        Type type1 = typeService.updateType(id, type);
        System.out.println(type1);
        if (type1 == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";

    }


    //删除分类
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes) {

        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
