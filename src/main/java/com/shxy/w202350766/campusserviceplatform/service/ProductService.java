package com.shxy.w202350766.campusserviceplatform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.w202350766.campusserviceplatform.domain.Product;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ProductDetailVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ProductListVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ProductVO;
import com.shxy.w202350766.campusserviceplatform.utils.Result;

import java.util.List;

/**
* @author 33046
* @description 针对表【product(商品表)】的数据库操作Service
* @createDate 2025-10-30 17:00:58
*/
public interface ProductService extends IService<Product> {

    Result<List<ProductListVO>> queryProductList(Integer page, Integer limit, String categoryId, String keyword, Float minPrice, Float maxPrice, String condition, String sort);

    Result<List<ProductVO>> queryHotProductList(Integer limit);

    Result<ProductDetailVO> queryProductDetail(Long id,String token);

    Result<Void> collectProduct(Long id, String token);

    Result<Void> deleteCollectProduct(Long id, String token);

    Result<ProductDetailVO> publishProduct(Product product, String token);
}