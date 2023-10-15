package com.share2renew.service;

import com.share2renew.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.share2renew.pojo.GeneralBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Junxian Cai
 * @since 2023-09-07
 */
public interface ICategoryService extends IService<Category> {

    /**
     * For admin create category
     * @param category
     * @return
     */
    GeneralBean createCategory(Category category);

    /**
     * For admin update Category
     * @param category
     * @return
     */
    GeneralBean updateCategory(Category category);

    /**
     * For admin delete Category
     * @param id
     * @return
     */
    GeneralBean deleteCategory(Integer id);
}
