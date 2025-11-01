package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.domain.ProductCollect;
import com.shxy.w202350766.campusserviceplatform.service.ProductCollectService;
import com.shxy.w202350766.campusserviceplatform.mapper.ProductCollectMapper;
import org.springframework.stereotype.Service;

/**
* @author 33046
* @description 针对表【product_collect(商品收藏表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:59
*/
@Service
public class ProductCollectServiceImpl extends ServiceImpl<ProductCollectMapper, ProductCollect>
    implements ProductCollectService{

    /**
     * 根据收藏ID和用户ID删除收藏
     * @param id
     * @param userId
     */
    public void deleteById(Long id, Long userId) {
        baseMapper.deleteById(id,userId);
    }
}




