package com.shxy.w202350766.campusserviceplatform.controller;

import com.shxy.w202350766.campusserviceplatform.pojo.dto.LostFoundItemDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundCategoryVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundItemDetailVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundItemVO;
import com.shxy.w202350766.campusserviceplatform.service.LostAndFoundService;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.11.2
 * 失物招领模块控制器
 */
@RestController
@RequestMapping("/api/lost-found")
public class LostFoundController {
    @Resource
    private LostAndFoundService lostFoundService;

    /**
     * 获取失物招领分类列表
     *
     * @return 失物招领分类列表
     */
    @GetMapping("/categories")
    public Result<List<LostFoundCategoryVO>> getCategories() {
        return Result.success(lostFoundService.getCategories());
    }

    /**
     * 获取最新的失物招领项目列表
     *
     * @param limit 项目数量限制
     * @return 最新的失物招领项目列表
     */
    @GetMapping("/latest")
    public Result<List<LostFoundItemVO>> getLatestItems(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return Result.success(lostFoundService.getLatestItems(limit));
    }

    /**
     * 获取失物招领物品列表
     *
     * @param categoryId 分类ID（可选）
     * @param page       页码
     * @param limit      项目数量限制
     * @param sort       排序方式（latest/popular/resolved）
     * @param type       搜索类型（all/lost/found）
     * @param keyword    搜索关键词（可选）
     * @return 失物招领物品列表
     */
    @GetMapping("/items")
    public Result<List<LostFoundItemVO>> getItems(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "sort", defaultValue = "latest") String sort,
            @RequestParam(value = "type", defaultValue = "all") String type,
            @RequestParam(value = "keyword", required = false) String keyword) {
        return Result.success(lostFoundService.getItems(categoryId, limit, sort, page, type, keyword));
    }

    /**
     * 发布失物
     * @param dto
     * @return
     */
    @PostMapping("/publishItems")
    public Result<LostFoundItemVO> addItems(@RequestBody LostFoundItemDTO dto,
                                            @RequestHeader("Authorization") String token) {
        return Result.success(lostFoundService.addLostItems(dto,token));
    }

    //查看详情
    @GetMapping("items/{id}")
    public Result<LostFoundItemDetailVO> getItem(@PathVariable Long id) {
        return lostFoundService.getItemDetail(id);
    }

    /**
     * 认领失物招领物品
     * @param itemId 物品ID
     * @param token  用户token
     * @return 操作结果
     */
    @PostMapping("/items/{itemId}/claim")
    public Result<?> claimItem(@PathVariable Long itemId,
                               @RequestHeader("Authorization") String token) {
        return lostFoundService.claimItem(itemId, token);
    }

    /**
     * 物品联系
     * @param itemId 物品ID
     * @param token  用户token
     * @return 操作结果
     */
    @GetMapping("/items/communicate/{itemId}")
    public Result<?> communicateItem(@PathVariable Long itemId,
                               @RequestHeader("Authorization") String token) {
        //TODO 后期增加联系功能
        return null;
    }
}