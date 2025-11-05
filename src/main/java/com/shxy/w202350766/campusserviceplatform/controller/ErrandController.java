package com.shxy.w202350766.campusserviceplatform.controller;

import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskQueryDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandReviewDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskStatusDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.*;
import com.shxy.w202350766.campusserviceplatform.service.ErrandTaskService;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 跑腿任务模块控制器
 * 提供跑腿任务相关的API接口
 */
@RestController
@RequestMapping("/api/errand")
public class ErrandController {

    @Resource
    private ErrandTaskService errandTaskService;

    /**
     * 获取跑腿任务分类列表
     *
     * @return 跑腿任务分类列表
     */
    @GetMapping("/categories")
    public Result<List<ErrandCategoryVO>> getCategories() {
        return Result.success(errandTaskService.getCategories());
    }

    /**
     * 获取跑腿达人榜
     *
     * @param limit 榜单数量限制，默认为10
     * @return 跑腿达人列表
     */
    @GetMapping("/runners")
    public Result<List<ErrandRunnerVO>> getRunners(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return Result.success(errandTaskService.getRunners(limit));
    }

    /**
     * 获取跑腿任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @return 跑腿任务分页结果
     */
    @GetMapping("/tasks")
    public Result<ErrandTaskPageVO> getTasks(
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "latest") String sortBy,
            @RequestParam(value = "onlyUrgent", defaultValue = "false") Boolean onlyUrgent,
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestHeader("Authorization") String token) {
        ErrandTaskQueryDTO queryDTO = new ErrandTaskQueryDTO();
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        queryDTO.setSortBy(sortBy);
        queryDTO.setOnlyUrgent(onlyUrgent);
        queryDTO.setCategoryId(categoryId);
        queryDTO.setKeyword(keyword);
        System.out.println("queryDTO = " + queryDTO);
        return Result.success(errandTaskService.getTasks(queryDTO, token));
    }

    /**
     * 获取跑腿任务详情
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     * @return 跑腿任务详情
     */
    @GetMapping("/tasks/{taskId}")
    public Result<ErrandTaskDetailVO> getTaskDetail(@PathVariable Long taskId,
                                                    @RequestHeader(value = "Authorization", required = false) String token) {
        return Result.success(errandTaskService.getTaskDetail(taskId, token));
    }

    /**
     * 发布跑腿任务
     *
     * @param taskDTO 任务信息
     * @param token   用户认证令牌
     * @return 发布的任务信息
     */
    @PostMapping("/tasks")
    public Result<ErrandTaskVO> publishTask(@RequestBody ErrandTaskDTO taskDTO,
                                            @RequestHeader("Authorization") String token) {
        return Result.success(errandTaskService.publishTask(taskDTO, token));
    }

    /**
     * 接受跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     * @return 操作结果
     */
    @PostMapping("/tasks/{taskId}/accept")
    public Result<?> acceptTask(@PathVariable Long taskId,
                                @RequestHeader("Authorization") String token) {
        errandTaskService.acceptTask(taskId, token);
        return Result.success(null);
    }

    /**
     * 更新跑腿任务状态
     *
     * @param taskId    任务ID
     * @param statusDTO 状态更新信息
     * @param token     用户认证令牌
     * @return 操作结果
     */
    @PutMapping("/tasks/{taskId}/status")
    public Result<?> updateTaskStatus(@PathVariable Long taskId,
                                      @RequestBody ErrandTaskStatusDTO statusDTO,
                                      @RequestHeader("Authorization") String token) {
        errandTaskService.updateTaskStatus(taskId, statusDTO, token);
        return Result.success(null);
    }

    /**
     * 取消跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     * @return 操作结果
     */
    @PostMapping("/tasks/{taskId}/cancel")
    public Result<?> cancelTask(@PathVariable Long taskId,
                                @RequestHeader("Authorization") String token) {
        errandTaskService.cancelTask(taskId, token);
        return Result.success(null);
    }

    /**
     * 放弃跑腿任务（接单人放弃已接受的任务）
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     * @return 操作结果
     */
    @PostMapping("/tasks/{taskId}/abandon")
    public Result<?> abandonTask(@PathVariable Long taskId,
                                  @RequestHeader("Authorization") String token) {
        errandTaskService.abandonTask(taskId, token);
        return Result.success(null);
    }

    /**
     * 完成跑腿任务
     *
     * @param taskId    任务ID
     * @param proofImages 完成证明图片
     * @param token     用户认证令牌
     * @return 操作结果
     */
    @PostMapping("/tasks/{taskId}/complete")
    public Result<?> completeTask(@PathVariable Long taskId,
                                  @RequestParam("proofImages") MultipartFile[] proofImages,
                                  @RequestHeader("Authorization") String token) {
        ErrandTaskStatusDTO statusDTO = new ErrandTaskStatusDTO();
        statusDTO.setProofImages(proofImages);
        errandTaskService.completeTask(taskId, statusDTO, token);
        return Result.success(null);
    }

    /**
     * 获取任务评价列表
     *
     * @param taskId 任务ID
     * @return 任务评价列表
     */
    @GetMapping("/tasks/{taskId}/reviews")
    public Result<List<ErrandReviewVO>> getTaskReviews(@PathVariable Long taskId) {
        return Result.success(errandTaskService.getTaskReviews(taskId));
    }

    /**
     * 提交任务评价
     *
     * @param taskId    任务ID
     * @param reviewDTO 评价信息
     * @param token     用户认证令牌
     * @return 操作结果
     */
    @PostMapping("/tasks/{taskId}/reviews")
    public Result<?> submitReview(@PathVariable Long taskId,
                                  @RequestBody ErrandReviewDTO reviewDTO,
                                  @RequestHeader("Authorization") String token) {
        errandTaskService.submitReview(taskId, reviewDTO, token);
        return Result.success(null);
    }

    /**
     * 获取我的跑腿任务统计
     *
     * @param token 用户认证令牌
     * @return 跑腿任务统计信息
     */
    @GetMapping("/stats")
    public Result<ErrandStatsVO> getMyStats(@RequestHeader("Authorization") String token) {
        return Result.success(errandTaskService.getMyStats(token));
    }

    /**
     * 获取我发布的跑腿任务
     *
     * @param queryDTO 查询条件
     * @param token    用户认证令牌
     * @return 跑腿任务分页结果
     */
    @GetMapping("/my-published")
    public Result<ErrandTaskPageVO> getMyPublishedTasks(ErrandTaskQueryDTO queryDTO,
                                                        @RequestHeader("Authorization") String token) {
        return Result.success(errandTaskService.getMyPublishedTasks(queryDTO, token));
    }

    /**
     * 获取可接任务列表
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @param sortBy   排序方式（latest-最新发布，price-asc-价格从低到高，price-desc-价格从高到低，urgent-紧急任务）
     * @param onlyUrgent 是否仅显示紧急任务
     * @param categoryId 任务分类ID
     * @param keyword  搜索关键词
     * @return 可接任务分页结果
     */
    @GetMapping("/tasks/available")
    public Result<ErrandTaskPageVO> getAvailableTasks(@RequestParam("pageNum") Integer pageNum,
                                                      @RequestParam("pageSize") Integer pageSize,
                                                      @RequestParam(value = "sortBy", defaultValue = "latest") String sortBy,
                                                      @RequestParam(value = "onlyUrgent", defaultValue = "false") Boolean onlyUrgent,
                                                      @RequestParam(value = "categoryId", required = false) Long categoryId,
                                                      @RequestParam(value = "keyword", required = false) String keyword) {
        ErrandTaskQueryDTO queryDTO = new ErrandTaskQueryDTO();
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        queryDTO.setSortBy(sortBy);
        queryDTO.setOnlyUrgent(onlyUrgent);
        queryDTO.setCategoryId(categoryId);
        queryDTO.setKeyword(keyword);
        // 设置状态为待接单
        queryDTO.setStatus("PENDING");
        return Result.success(errandTaskService.getTasks(queryDTO, null));
    }

    /**
     * 获取我接受的跑腿任务
     *
     * @param queryDTO 查询条件
     * @param token    用户认证令牌
     * @return 跑腿任务分页结果
     */
    @GetMapping("/my-accepted")
    public Result<ErrandTaskPageVO> getMyAcceptedTasks(ErrandTaskQueryDTO queryDTO,
                                                       @RequestHeader("Authorization") String token) {
        return Result.success(errandTaskService.getMyAcceptedTasks(queryDTO, token));
    }

    /**
     * 恢复已取消的跑腿任务（将任务状态从已取消恢复为待接单）
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     * @return 操作结果
     */
    @PostMapping("/tasks/{taskId}/restore")
    public Result<?> restoreTask(@PathVariable Long taskId,
                                 @RequestHeader("Authorization") String token) {
        errandTaskService.restoreTask(taskId, token);
        return Result.success(null);
    }

    /**
     * 删除已取消的跑腿任务（永久删除）
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     * @return 操作结果
     */
    @DeleteMapping("/tasks/{taskId}")
    public Result<?> deleteTask(@PathVariable Long taskId,
                                @RequestHeader("Authorization") String token) {
        errandTaskService.deleteTask(taskId, token);
        return Result.success(null);
    }
}