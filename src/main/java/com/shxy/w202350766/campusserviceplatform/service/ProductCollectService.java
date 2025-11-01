package com.shxy.w202350766.campusserviceplatform.service;

import com.shxy.w202350766.campusserviceplatform.domain.ProductCollect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 33046
* @description 针对表【product_collect(商品收藏表)】的数据库操作Service
* @createDate 2025-10-30 17:00:59
*/
public interface ProductCollectService extends IService<ProductCollect> {

    void deleteById(Long id, Long userId);
}
