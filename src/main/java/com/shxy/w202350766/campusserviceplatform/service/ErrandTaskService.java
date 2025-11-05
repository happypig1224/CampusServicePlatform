package com.shxy.w202350766.campusserviceplatform.service;

import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandReviewDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskQueryDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskStatusDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ErrandTask;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.*;

import java.util.List;

/**
 * @author 33046
 * @description 针对表【errand_task(跑腿任务表)】的数据库操作Service
 * @createDate 2025-10-30 17:00:58
 */
public interface ErrandTaskService extends IService<ErrandTask> {
    List<ErrandCategoryVO> getCategories();

    List<ErrandRunnerVO> getRunners(Integer limit);

    ErrandTaskPageVO getTasks(ErrandTaskQueryDTO queryDTO, String token);

    ErrandTaskDetailVO getTaskDetail(Long taskId, String token);

    ErrandTaskVO publishTask(ErrandTaskDTO taskDTO, String token);

    void acceptTask(Long taskId, String token);

    void updateTaskStatus(Long taskId, ErrandTaskStatusDTO statusDTO, String token);

    void cancelTask(Long taskId, String token);

    void abandonTask(Long taskId, String token);

    void completeTask(Long taskId, ErrandTaskStatusDTO statusDTO, String token);

    List<ErrandReviewVO> getTaskReviews(Long taskId);

    void submitReview(Long taskId, ErrandReviewDTO reviewDTO, String token);

    ErrandStatsVO getMyStats(String token);

    ErrandTaskPageVO getMyPublishedTasks(ErrandTaskQueryDTO queryDTO, String token);

    ErrandTaskPageVO getMyAcceptedTasks(ErrandTaskQueryDTO queryDTO, String token);

    /**
     * 恢复已取消的跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    void restoreTask(Long taskId, String token);

    /**
     * 删除已取消的跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    void deleteTask(Long taskId, String token);

}