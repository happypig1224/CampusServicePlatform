package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.Product;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ProductCategory;
import com.shxy.w202350766.campusserviceplatform.service.ProductCategoryService;
import com.shxy.w202350766.campusserviceplatform.mapper.ProductCategoryMapper;
import com.shxy.w202350766.campusserviceplatform.mapper.ProductMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 33046
* @description 针对表【product_category(商品分类表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:59
*/
@Service
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory>
    implements ProductCategoryService{
    @Resource
    private ProductMapper productMapper;

    /**
     * 获取商品分类列表
     * @return
     */
    public List<ProductCategory> queryCategoryList() {
        //1.查询商品分类列表
        List<ProductCategory> categoryList = baseMapper.selectList(null);
        //2.查询每个分类下的商品数量
        for(ProductCategory category : categoryList) {
            Long productCount = productMapper.selectCount(new LambdaQueryWrapper<Product>()
                    .eq(Product::getCategoryId, category.getId()));
            category.setProductCount(productCount);
        }
        return categoryList;
    }
}