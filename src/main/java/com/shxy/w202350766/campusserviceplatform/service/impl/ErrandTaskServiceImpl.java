package com.shxy.w202350766.campusserviceplatform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.pojo.ErrandCategory;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandReviewDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskQueryDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ErrandTaskStatusDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ErrandReview;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ErrandTask;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.User;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.*;
import com.shxy.w202350766.campusserviceplatform.service.ErrandCategoryService;
import com.shxy.w202350766.campusserviceplatform.service.ErrandReviewService;
import com.shxy.w202350766.campusserviceplatform.service.ErrandTaskService;
import com.shxy.w202350766.campusserviceplatform.mapper.ErrandTaskMapper;
import com.shxy.w202350766.campusserviceplatform.service.OssService;
import com.shxy.w202350766.campusserviceplatform.service.UserService;
import com.shxy.w202350766.campusserviceplatform.utils.JwtUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 33046
 * @description 针对表【errand_task(跑腿任务表)】的数据库操作Service实现
 * @createDate 2025-10-30 17:00:58
 */
@Service
public class ErrandTaskServiceImpl extends ServiceImpl<ErrandTaskMapper, ErrandTask>
        implements ErrandTaskService {
    @Resource
    private ErrandTaskMapper errandTaskMapper;

    @Resource
    private ErrandCategoryService errandCategoryService;
    @Resource
    private UserService userService;
    @Resource
    private ErrandReviewService errandReviewService;
    @Resource
    private OssService ossService;

    /**
     * 获取跑腿任务分类列表
     *
     * @return 跑腿任务分类列表
     */
    public List<ErrandCategoryVO> getCategories() {
        return errandCategoryService.list().stream()
                .map(category ->
                {
                    ErrandCategoryVO vo = new ErrandCategoryVO();
                    vo.setName(category.getName());
                    vo.setIcon(category.getIcon());
                    vo.setId(category.getId());
                    vo.setDescription(category.getDescription());
                    vo.setTaskCount(category.getTaskCount());
                    return vo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取跑腿达人榜
     *
     * @param limit 返回的达人数量限制
     * @return 跑腿达人榜
     */
    public List<ErrandRunnerVO> getRunners(Integer limit) {
        Page<ErrandReview> page = new Page<>(1, limit);
        LambdaQueryWrapper<ErrandReview> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ErrandReview::getRating);
        List<ErrandReview> reviewList = errandReviewService.list(page, wrapper);
        Map<Long, User> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, user -> user));

        return reviewList.stream().map(review -> {
            ErrandRunnerVO vo = new ErrandRunnerVO();
            vo.setId(review.getRevieweeId());
            vo.setUsername(userMap.get(review.getRevieweeId()).getUsername());
            vo.setAvatar(userMap.get(review.getRevieweeId()).getAvatar());
            vo.setRating(review.getRating());
            vo.setCompletedTasks(errandTaskMapper.selectCompleteTask(review.getRevieweeId()));
            // 计算平均完成时间（小时）
            Double avgTime = errandTaskMapper.selectAverageCompletionTime(review.getRevieweeId());
            vo.setAverageCompletionTime(avgTime != null ? avgTime : 0.0);
            // 计算完成率
            Integer totalAccepted = errandTaskMapper.selectTotalAcceptedTasks(review.getRevieweeId());
            Integer completedTasks = vo.getCompletedTasks();
            Double completionRate = (totalAccepted != null && totalAccepted > 0) ?
                    (double) completedTasks / totalAccepted * 100 : 0.0;
            vo.setCompletionRate(completionRate);
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取跑腿任务列表
     *
     * @param queryDTO 查询参数
     * @param token    用户认证令牌
     * @return 跑腿任务列表
     */
    public ErrandTaskPageVO getTasks(ErrandTaskQueryDTO queryDTO, String token) {
        // 创建分页对象
        Page<ErrandTask> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());

        // 创建查询条件
        LambdaQueryWrapper<ErrandTask> wrapper = new LambdaQueryWrapper<>();

        // 添加查询条件
        if (queryDTO.getCategoryId() != null) {
            wrapper.eq(ErrandTask::getCategoryId, queryDTO.getCategoryId());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(ErrandTask::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getPublisherId() != null) {
            wrapper.eq(ErrandTask::getUserId, queryDTO.getPublisherId());
        }
        if (queryDTO.getAcceptorId() != null) {
            wrapper.eq(ErrandTask::getAcceptorId, queryDTO.getAcceptorId());
        }
        if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().trim().isEmpty()) {
            wrapper.and(w -> w.like(ErrandTask::getTitle, queryDTO.getKeyword())
                    .or()
                    .like(ErrandTask::getDescription, queryDTO.getKeyword()));
        }

        // 排序
        if (queryDTO.getSortBy() != null) {
            switch (queryDTO.getSortBy()) {
                case "latest":
                    wrapper.orderByDesc(ErrandTask::getCreateTime);
                    break;
                case "price-asc":
                    wrapper.orderByAsc(ErrandTask::getReward);
                    break;
                case "price-desc":
                    wrapper.orderByDesc(ErrandTask::getReward);
                    break;
                case "urgent":
                    // 按截止时间升序，即将到期的排在前面
                    wrapper.orderByAsc(ErrandTask::getDeadline);
                    break;
                default:
                    wrapper.orderByDesc(ErrandTask::getCreateTime);
            }
        } else {
            // 默认按创建时间倒序
            wrapper.orderByDesc(ErrandTask::getCreateTime);
        }
        Page<ErrandTask> taskPage = errandTaskMapper.selectPage(page, wrapper);
        // 转换为VO
        // 解析用户ID
        Long userId = JwtUtils.parseToken(token);
        List<ErrandTaskVO> taskVOList = taskPage.getRecords().stream().map(task -> {
            ErrandTaskVO vo = new ErrandTaskVO();
            BeanUtils.copyProperties(task, vo);
            ErrandCategory category = errandCategoryService.getById(task.getCategoryId());
            if (category != null) {
                vo.setCategoryName(category.getName());
            } else {
                vo.setCategoryName("未知分类");
            }
            vo.setPublisherId(task.getUserId());
            vo.setPublisherName(userService.getById(task.getUserId()).getUsername());
            vo.setPublisherAvatar(userService.getById(task.getUserId()).getAvatar());
            vo.setViewCount(task.getViewCount());
            vo.setIsOwnTask(task.getUserId().equals(userId));
            vo.setCanAccept((!task.getUserId().equals(userId)) && task.getStatus().equals("PENDING"));
            return vo;
        }).toList();
        ErrandTaskPageVO pageVO = new ErrandTaskPageVO();
        pageVO.setPageNum(taskPage.getCurrent());
        pageVO.setPageSize(taskPage.getSize());
        pageVO.setTotal(taskPage.getTotal());
        pageVO.setPages(taskPage.getPages());
        pageVO.setHasPrevious(taskPage.hasPrevious());
        pageVO.setHasNext(taskPage.hasNext());
        pageVO.setTasks(taskVOList);
        return pageVO;
    }

    /**
     * 获取跑腿任务详情
     *
     * @param taskId 任务ID
     * @return 跑腿任务详情
     */
    public ErrandTaskDetailVO getTaskDetail(Long taskId, String token) {
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        Long userId = JwtUtils.parseToken(token);
        ErrandTaskDetailVO vo = new ErrandTaskDetailVO();
        BeanUtils.copyProperties(task, vo);
        vo.setStatus(task.getStatus().toString());
        ErrandCategory category = errandCategoryService.getById(task.getCategoryId());
        if (category != null) {
            vo.setCategoryName(category.getName());
        } else {
            vo.setCategoryName("未知分类");
        }
        vo.setIsOwnTask(false);
        vo.setCanComplete(false);
        vo.setCanCancel(false);
        if (userId != null) {
            //如果是任务发布者
            vo.setIsOwnTask(task.getUserId().equals(userId));
            // 如果是任务发布者，在ACCEPTED状态下可以取消任务
            if (task.getUserId().equals(userId) && task.getStatus().equals("ACCEPTED")) {
                vo.setCanCancel(true);
            }
        }
        // 任务接收者可以确认完成任务
        vo.setCanComplete(task.getAcceptorId() != null && task.getAcceptorId().equals(userId));
        // 任务接收者或发布者可以取消/放弃任务
        vo.setCanCancel(task.getAcceptorId() != null && task.getAcceptorId().equals(userId) || task.getUserId().equals(userId));
        //设置发布者信息
        ErrandTaskDetailVO.ErrandPublisherVO publisherVO = new ErrandTaskDetailVO.ErrandPublisherVO();
        publisherVO.setId(task.getUserId());
        publisherVO.setUsername(userService.getById(task.getUserId()).getUsername());
        publisherVO.setAvatar(userService.getById(task.getUserId()).getAvatar());
        vo.setPublisher(publisherVO);
        List<ErrandReview> list = errandReviewService.list(Wrappers.<ErrandReview>lambdaQuery()
                .eq(ErrandReview::getTaskId, taskId));
        List<ErrandReviewVO> reviewVOList = list.stream().map(review -> {
            ErrandReviewVO reviewVO = new ErrandReviewVO();
            BeanUtils.copyProperties(review, reviewVO);
            reviewVO.setReviewerName(userService.getById(review.getReviewerId()).getUsername());
            reviewVO.setReviewerAvatar(userService.getById(review.getReviewerId()).getAvatar());
            return reviewVO;
        }).toList();
        vo.setReviews(reviewVOList);
        //更新浏览次数
        task.setViewCount(task.getViewCount() + 1);
        errandTaskMapper.updateById(task);

        return vo;
    }

    /**
     * 发布跑腿任务
     *
     * @param taskDTO 任务信息
     * @param token   用户认证令牌
     * @return 发布的跑腿任务VO
     */
    public ErrandTaskVO publishTask(ErrandTaskDTO taskDTO, String token) {
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        ErrandTask task = new ErrandTask();
        BeanUtils.copyProperties(taskDTO, task);
        task.setUserId(userId);
        task.setStatus("PENDING");
        task.setViewCount(0L);
        errandTaskMapper.insert(task);
        ErrandTaskVO vo = new ErrandTaskVO();
        ErrandTaskDetailVO detailVO = new ErrandTaskDetailVO();
        BeanUtils.copyProperties(vo, detailVO);
        return vo;
    }

    /**
     * 接单跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    public void acceptTask(Long taskId, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验任务状态是否可接单
        if (!task.getStatus().equals("PENDING")) {
            throw new IllegalArgumentException("任务已被接受或已完成");
        }
        //4.更新任务状态为已接单
        task.setAcceptorId(userId);
        task.setStatus("ACCEPTED");
        task.setAcceptTime(LocalDateTime.now());
        errandTaskMapper.updateById(task);
    }

    /**
     * 更新跑腿任务状态
     *
     * @param taskId    任务ID
     * @param statusDTO 状态更新信息
     * @param token     用户认证令牌
     */
    public void updateTaskStatus(Long taskId, ErrandTaskStatusDTO statusDTO, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务接受者
        if (!task.getAcceptorId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务接受者");
        }
        //4.校验状态是否合法
        if (!statusDTO.getStatus().matches("PENDING|ACCEPTED|COMPLETED|CANCELLED")) {
            throw new IllegalArgumentException("状态值非法");
        }
        //5.更新任务状态
        //如果是已完成状态，校验是否上传完成证明
        if (statusDTO.getStatus().equals("COMPLETED")) {
            if (statusDTO.getProofImages() == null) {
                throw new IllegalArgumentException("完成状态下需上传完成证明");
            }
        }
        task.setStatus(statusDTO.getStatus());
        errandTaskMapper.updateById(task);
    }

    /**
     * 取消跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    public void cancelTask(Long taskId, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务发布者或接受者
        if (!task.getUserId().equals(userId) && !task.getAcceptorId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务发布者或接受者");
        }
        //4.校验任务状态是否可取消
        if (!task.getStatus().equals("PENDING") && !task.getStatus().equals("ACCEPTED")) {
            throw new IllegalArgumentException("任务已被处理或已完成");
        }
        //5.更新任务状态为已取消
        task.setStatus("CANCELLED");
        errandTaskMapper.updateById(task);
    }

    /**
     * 放弃跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    public void abandonTask(Long taskId, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务接受者
        if (!task.getAcceptorId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务接受者");
        }
        //4.校验任务状态是否可放弃
        if (!task.getStatus().equals("ACCEPTED")) {
            throw new IllegalArgumentException("任务状态不可放弃");
        }
        //5.更新任务状态为待接单，并清空接受者信息
        task.setStatus("PENDING");
        task.setAcceptorId(null);
        errandTaskMapper.updateTask(task);
    }

    /**
     * 完成跑腿任务
     *
     * @param taskId    任务ID
     * @param statusDTO 状态更新信息
     * @param token     用户认证令牌
     */
    public void completeTask(Long taskId, ErrandTaskStatusDTO statusDTO, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务接受者
        if (!task.getAcceptorId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务接受者");
        }
        //4.校验任务状态是否为已接单
        if (!task.getStatus().equals("ACCEPTED")) {
            throw new IllegalArgumentException("只有已接单的任务才能完成");
        }
        //5.校验是否上传完成证明
        if (statusDTO.getProofImages() == null) {
            throw new IllegalArgumentException("完成状态下需上传完成证明");
        }
        
        //6.处理上传的证明图片
        MultipartFile[] proofImages = (MultipartFile[]) statusDTO.getProofImages();
        if (proofImages.length == 0) {
            throw new IllegalArgumentException("请至少上传一张完成证明图片");
        }
        
        //7.上传图片到OSS并获取URL
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile image : proofImages) {
            try {
                String imageUrl = ossService.uploadImage(image, "errand/proof/");
                imageUrls.add(imageUrl);
            } catch (Exception e) {
                throw new IllegalArgumentException("图片上传失败：" + e.getMessage());
            }
        }
        
        //8.更新任务状态为已完成，并保存证明图片URL
        task.setStatus("COMPLETED");
        task.setCompleteTime(LocalDateTime.now());
        // 将图片URL列表转换为JSON数组格式
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String proofImagesJson = objectMapper.writeValueAsString(imageUrls);
            task.setProofImages(proofImagesJson);
        } catch (Exception e) {
            throw new IllegalArgumentException("图片URL格式转换失败：" + e.getMessage());
        }
        errandTaskMapper.updateById(task);
    }

    /**
     * 获取跑腿任务评论列表
     *
     * @param taskId 任务ID
     * @return 跑腿任务评论列表
     */
    public List<ErrandReviewVO> getTaskReviews(Long taskId) {
        //1.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //2.查询任务评论列表
        List<ErrandReviewVO> reviewList = errandReviewService.list(
                new LambdaQueryWrapper<ErrandReview>()
                        .eq(ErrandReview::getTaskId, taskId)
                        .orderByDesc(ErrandReview::getCreateTime)
        ).stream().map(review -> {
            ErrandReviewVO reviewVO = new ErrandReviewVO();
            BeanUtils.copyProperties(review, reviewVO);
            reviewVO.setReviewerName(userService.getById(review.getReviewerId()).getUsername());
            reviewVO.setReviewerAvatar(userService.getById(review.getReviewerId()).getAvatar());
            return reviewVO;
        }).collect(Collectors.toList());
        return reviewList;
    }

    /**
     * 提交跑腿任务评价
     *
     * @param taskId    任务ID
     * @param reviewDTO 评价信息
     * @param token     用户认证令牌
     */
    public void submitReview(Long taskId, ErrandReviewDTO reviewDTO, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务发布者
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务发布者");
        }
        //4.校验任务状态是否已完成
        if (!task.getStatus().equals("COMPLETED")) {
            throw new IllegalArgumentException("任务未完成");
        }
        //5.校验评价是否已存在
        if (errandReviewService.count(
                new LambdaQueryWrapper<ErrandReview>()
                        .eq(ErrandReview::getTaskId, taskId)
                        .eq(ErrandReview::getReviewerId, userId)) > 0) {
            throw new IllegalArgumentException("评价已存在");
        }
        //6.校验评价内容是否为空
        if (StringUtils.isEmpty(reviewDTO.getContent())) {
            throw new IllegalArgumentException("评价内容不能为空");
        }
        //7.校验评分是否在1-5之间
        if (reviewDTO.getRating() == null || reviewDTO.getRating() < 1 || reviewDTO.getRating() > 5) {
            throw new IllegalArgumentException("评分必须在1-5之间");
        }
        //8.校验被评价用户是否为任务接受者
        if (!task.getAcceptorId().equals(reviewDTO.getRevieweeId())) {
            throw new IllegalArgumentException("被评价用户不是任务接受者");
        }
        //9.创建评价记录
        ErrandReview review = new ErrandReview();
        BeanUtils.copyProperties(reviewDTO, review);
        review.setTaskId(taskId);
        review.setReviewerId(userId);
        review.setCreateTime(LocalDateTime.now());
        review.setRevieweeId(task.getAcceptorId());
        errandReviewService.save(review);
    }

    /**
     * 获取用户跑腿任务统计信息
     *
     * @param token 用户认证令牌
     * @return 用户跑腿任务统计信息
     */
    public ErrandStatsVO getMyStats(String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.查询用户跑腿任务统计信息
        ErrandStatsVO statsVO = new ErrandStatsVO();
        //2.1查询总任务数
        statsVO.setTotalTasks(errandTaskMapper.count(userId));
        //2.2查询待接单任务数
        statsVO.setPendingTasks(this.count(Wrappers.<ErrandTask>lambdaQuery()
                .eq(ErrandTask::getUserId, userId)
                .eq(ErrandTask::getStatus, "PENDING")));
        //2.3查询进行中任务数
        statsVO.setOngoingTasks(this.count(Wrappers.<ErrandTask>lambdaQuery()
                .eq(ErrandTask::getUserId, userId)
                .eq(ErrandTask::getStatus, "ONGOING")));
        //2.4查询已完成任务数
        statsVO.setCompletedTasks(this.count(Wrappers.<ErrandTask>lambdaQuery()
                .eq(ErrandTask::getUserId, userId)
                .eq(ErrandTask::getStatus, "COMPLETED")));
        //2.5查询已取消任务数
        statsVO.setCancelledTasks(this.count(Wrappers.<ErrandTask>lambdaQuery()
                .eq(ErrandTask::getUserId, userId)
                .eq(ErrandTask::getStatus, "CANCELLED")));

        //3.返回用户跑腿任务统计信息
        return statsVO;
    }

    /**
     * 获取用户发布的跑腿任务
     *
     * @param queryDTO 查询参数
     * @param token    用户认证令牌
     * @return 用户发布的跑腿任务分页信息
     */
    public ErrandTaskPageVO getMyPublishedTasks(ErrandTaskQueryDTO queryDTO, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.查询用户发布的跑腿任务
        IPage<ErrandTask> page = this.page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                Wrappers.<ErrandTask>lambdaQuery()
                        .eq(ErrandTask::getUserId, userId)
                        .orderByDesc(ErrandTask::getCreateTime));
        //3.转换为分页VO
        ErrandTaskPageVO vo = new ErrandTaskPageVO();
        vo.setPageNum(page.getCurrent());
        vo.setPageSize(page.getSize());
        vo.setTotal(page.getTotal());
        vo.setPages(page.getPages());
        List<ErrandTaskVO> taskVOS = page.getRecords().stream().map(task -> {
            ErrandTaskVO taskVO = new ErrandTaskVO();
            BeanUtils.copyProperties(task, taskVO);
            ErrandCategory category = errandCategoryService.getById(task.getCategoryId());
            if (category != null) {
                taskVO.setCategoryName(category.getName());
            } else {
                taskVO.setCategoryName("未知分类");
            }

            return taskVO;
        }).toList();
        vo.setTasks(taskVOS);
        return vo;
    }

    /**
     * 获取用户接受的跑腿任务
     *
     * @param queryDTO 查询参数
     * @param token    用户认证令牌
     * @return 用户接受的跑腿任务分页信息
     */
    public ErrandTaskPageVO getMyAcceptedTasks(ErrandTaskQueryDTO queryDTO, String token) {
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.查询用户接受的跑腿任务
        IPage<ErrandTask> page = this.page(new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize()),
                Wrappers.<ErrandTask>lambdaQuery()
                        .eq(ErrandTask::getAcceptorId, userId)
                        .orderByDesc(ErrandTask::getCreateTime));
        //3.转换为分页VO
        ErrandTaskPageVO vo = new ErrandTaskPageVO();
        vo.setPageNum(page.getCurrent());
        vo.setPageSize(page.getSize());
        vo.setTotal(page.getTotal());
        vo.setPages(page.getPages());
        List<ErrandTaskVO> taskVOS = page.getRecords().stream().map(task -> {
            ErrandTaskVO taskVO = new ErrandTaskVO();
            BeanUtils.copyProperties(task, taskVO);
            ErrandCategory category = errandCategoryService.getById(task.getCategoryId());
            if (category != null) {
                taskVO.setCategoryName(category.getName());
            } else {
                taskVO.setCategoryName("未知分类");
            }
            return taskVO;
        }).toList();
        vo.setTasks(taskVOS);
        return vo;
    }

    /**
     * 恢复已取消的跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    public void restoreTask(Long taskId, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务发布者
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务发布者");
        }
        //4.校验任务状态是否为已取消
        if (!task.getStatus().equals("CANCELLED")) {
            throw new IllegalArgumentException("只有已取消的任务才能恢复");
        }
        //5.更新任务状态为待接单
        task.setStatus("PENDING");
        errandTaskMapper.updateById(task);
    }

    /**
     * 删除跑腿任务
     *
     * @param taskId 任务ID
     * @param token  用户认证令牌
     */
    public void deleteTask(Long taskId, String token) {
        //1.校验用户是否登录
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }
        //2.校验任务是否存在
        ErrandTask task = errandTaskMapper.selectById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在");
        }
        //3.校验用户是否为任务发布者
        if (!task.getUserId().equals(userId)) {
            throw new IllegalArgumentException("用户不是任务发布者");
        }
        //4.校验任务状态是否为已取消
        if (!task.getStatus().equals("CANCELLED")) {
            throw new IllegalArgumentException("只有已取消的任务才能删除");
        }
        //5.删除任务记录
        errandTaskMapper.deleteById(taskId);
    }
}