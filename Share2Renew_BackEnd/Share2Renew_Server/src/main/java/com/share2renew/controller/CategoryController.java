package com.share2renew.controller;


import com.share2renew.pojo.Category;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.ICategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    //TODO: 做一个可以搜索的
    @ApiOperation(value = "get all the category")
    @GetMapping("/getAllCategory")
    public List<Category> getAllCategory() {
        return categoryService.list();
    }

    @ApiOperation(value = "create category")
    @PostMapping("/createCategory")
    public GeneralBean createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @ApiOperation(value = "update category")
    @PostMapping("/updateCategory")
    public GeneralBean updateCategory(@RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @ApiOperation(value = "delete category")
    @DeleteMapping("/deleteCategory")
    public GeneralBean deleteCategory(@RequestParam Integer id) {
        return categoryService.deleteCategory(id);
    }

}
