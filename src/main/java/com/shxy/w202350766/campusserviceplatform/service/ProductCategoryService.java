package com.shxy.w202350766.campusserviceplatform.service;

import com.shxy.w202350766.campusserviceplatform.pojo.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 33046
* @description 针对表【product_category(商品分类表)】的数据库操作Service
* @createDate 2025-10-30 17:00:59
*/
public interface ProductCategoryService extends IService<ProductCategory> {

    List<ProductCategory> queryCategoryList();

}
