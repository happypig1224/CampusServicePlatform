package com.shxy.w202350766.campusserviceplatform.service;

import com.shxy.w202350766.campusserviceplatform.pojo.dto.LostFoundItemDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.LostAndFound;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundCategoryVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundItemDetailVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundItemVO;
import com.shxy.w202350766.campusserviceplatform.utils.Result;

import java.util.List;

/**
* @author 33046
* @description 针对表【lost_and_found(失物招领表)】的数据库操作Service
* @createDate 2025-10-30 17:00:58
*/
public interface LostAndFoundService extends IService<LostAndFound> {

    /**
     * 获取失物招领分类列表
     * @return 失物招领分类列表
     */
    List<LostFoundCategoryVO> getCategories();

    /**
     * 获取最新的失物招领项目列表
     * @param limit 项目数量限制
     * @return 最新的失物招领项目列表
     */
    List<LostFoundItemVO> getLatestItems(Integer limit);

    /**
     * 获取失物招领物品列表
     * @param categoryId 分类ID（可选）
     * @param limit      项目数量限制
     * @param sort       排序方式（desc/asc）
     * @param page       页码
     * @param type       搜索类型（all/lost/found）
     * @param keyword    搜索关键词（可选）
     * @return 失物招领物品列表
     */
    List<LostFoundItemVO> getItems(Long categoryId, Integer limit, String sort, Integer page, String type, String keyword);

     /**
     * 添加失物物品
     * @param dto 失物物品DTO
     * @return 失物物品列表
     */
    LostFoundItemVO addLostItems(LostFoundItemDTO dto, String token);

    Result<LostFoundItemDetailVO> getItemDetail(Long id);

    Result<?> claimItem(Long itemId, String token);
}
