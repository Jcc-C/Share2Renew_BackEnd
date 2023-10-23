package com.share2renew.service.Impl;

import com.share2renew.pojo.Category;
import com.share2renew.mapper.CategoryMapper;
import com.share2renew.pojo.GeneralBean;
import com.share2renew.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * For admin create category
     * @param category
     * @return
     */
    @Override
    public GeneralBean createCategory(Category category) {
        category.setValidity(1);
        int result = categoryMapper.insert(category);
        if (result == 1) {
            return GeneralBean.success("Create category successfully!");
        }
        return GeneralBean.error("Create category failed!");
    }


    /**
     * For admin update category
     * @param category
     * @return
     */
    @Override
    public GeneralBean updateCategory(Category category) {
        int result = categoryMapper.updateById(category);
        if (result == 1) {
            return GeneralBean.success("update category successfully!");
        }
        return GeneralBean.error("Update category failed");
    }

    /**
     * For admin delete category
     * @param id
     * @return
     */
    @Override
    public GeneralBean deleteCategory(Integer id) {

        int result = categoryMapper.deleteById(id);
        if (result == 1) {
            return GeneralBean.success("Delete category successfully!");
        }
        return GeneralBean.error("Delete category failed!");
    }
}
