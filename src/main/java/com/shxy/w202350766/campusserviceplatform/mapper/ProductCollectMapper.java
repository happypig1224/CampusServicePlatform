package com.shxy.w202350766.campusserviceplatform.mapper;

import com.shxy.w202350766.campusserviceplatform.pojo.entity.ProductCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;

/**
* @author 33046
* @description 针对表【product_collect(商品收藏表)】的数据库操作Mapper
* @createDate 2025-10-30 17:00:59
* @Entity com.shxy.w202350766.suiestation.domain.ProductCollect
*/
public interface ProductCollectMapper extends BaseMapper<ProductCollect> {

    /**
     * 根据收藏ID和用户ID删除收藏
     * @param id 收藏ID
     * @param userId 用户ID
     */
    @Delete("delete from product_collect where id = #{id} and user_id = #{userId}")
    void deleteById(Long id, Long userId);
}




