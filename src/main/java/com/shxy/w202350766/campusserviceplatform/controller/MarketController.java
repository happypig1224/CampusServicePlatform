package com.shxy.w202350766.campusserviceplatform.controller;

import com.shxy.w202350766.campusserviceplatform.pojo.entity.Product;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ProductCategory;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ProductDetailVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ProductListVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ProductVO;
import com.shxy.w202350766.campusserviceplatform.service.ProductCategoryService;
import com.shxy.w202350766.campusserviceplatform.service.ProductService;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.11.1
 * 二手市场控制器
 */
@RestController
@RequestMapping("/api/market")
public class MarketController {
    @Resource
    private ProductCategoryService productCategoryService;
    @Resource
    private ProductService productService;

    /**
     * 获取商品分类列表
     *
     * @return
     */
    @GetMapping("/categories")
    public Result<List<ProductCategory>> getCategories() {
        List<ProductCategory> list = productCategoryService.queryCategoryList();
        return Result.success(list);
    }

    /**
     * 获取商品列表
     *
     * @param page       页码
     * @param limit      每页数量
     * @param categoryId 分类ID
     * @param keyword    搜索关键词
     * @param minPrice   最低价格
     * @param maxPrice   最高价格
     * @param condition  商品状态
     * @param sort       排序字段
     * @return 商品列表
     */
    @GetMapping("/products")
    public Result<List<ProductListVO>> getProducts(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "categoryId", required = false) String categoryId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "minPrice", required = false) Float minPrice,
            @RequestParam(value = "maxPrice", required = false) Float maxPrice,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "sort", required = false) String sort) {

       return productService.queryProductList(page, limit, categoryId, keyword, minPrice, maxPrice, condition, sort);
    }

    /**
     * 获取热门商品列表
     * @param limit 热门商品数量
     * @return 热门商品列表
     */
    @GetMapping("/products/hot")
    public Result<List<ProductVO>> getHotProducts(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return productService.queryHotProductList(limit);
    }

    /**
     * 获取商品详情
     * @param id 商品ID
     * @return 商品详情
     */
    @GetMapping("/products/{id}")
    public Result<ProductDetailVO> getProductDetail(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String token) {
        return productService.queryProductDetail(id,token);
    }
    /**
     * 收藏商品
     * @param id 商品ID
     * @return 操作结果
     */
    @PostMapping("/products/{id}/collect")
    public Result<Void> collectProduct(@PathVariable Long id,
                                       @RequestHeader("Authorization") String token) {

        return productService.collectProduct(id,token);
    }

    /**
     * 取消收藏商品
     * @param id 商品ID
     * @return 操作结果
     */
    @DeleteMapping("/products/{id}/collect")
    public Result<Void> deleteCollectProduct(@PathVariable Long id,
                                       @RequestHeader("Authorization") String token) {

        return productService.deleteCollectProduct(id,token);
    }

    /**
     * 商品发布
     * @param product
     * @param token
     * @return
     */
    @PostMapping("/products")
    public Result<ProductDetailVO> publishProduct(@RequestBody Product product,
                                       @RequestHeader("Authorization") String token) {

        return productService.publishProduct(product,token);
    }
}