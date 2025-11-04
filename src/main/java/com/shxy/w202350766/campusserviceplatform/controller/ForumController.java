package com.shxy.w202350766.campusserviceplatform.controller;

import com.shxy.w202350766.campusserviceplatform.pojo.dto.ForumPostDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ForumPostQueryDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.ForumReplyDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ForumCategoryVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ForumPostVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ForumReplyVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ForumStatsVO;
import com.shxy.w202350766.campusserviceplatform.service.ForumPostService;
import com.shxy.w202350766.campusserviceplatform.service.ForumReplyService;
import com.shxy.w202350766.campusserviceplatform.utils.JwtUtils;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.31
 * 论坛板块控制器
 */
@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Resource
    private ForumPostService forumPostService;
    @Resource
    private ForumReplyService forumReplyService;

    /**
     * 获取论坛版块列表
     * @return 论坛版块列表
     */
    @GetMapping("/sections")
    public Result<List<ForumCategoryVO>> getForumCategories() {
        List<ForumCategoryVO> forumSections = forumPostService.queryCategoryList();
        return Result.success(forumSections);
    }
    /**
     * 获取帖子列表
     * @param queryDTO 查询参数DTO
     * @return
     */
    @GetMapping("/posts")
    public Result<List<ForumPostVO>> getForumPosts(ForumPostQueryDTO queryDTO) {
        List<ForumPostVO> posts = forumPostService.queryList(queryDTO.getPage(), queryDTO.getLimit(), 
                                                           queryDTO.getSectionId(), queryDTO.getKeyword(), queryDTO.getSort());
        return Result.success(posts);
    }

    /**
     * 获取热门帖子列表
     * @param limit
     * @return
     */
    @GetMapping("/posts/hot")
    public Result<List<ForumPostVO>> getHotForumPosts(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        List<ForumPostVO> hotPosts = forumPostService.queryHotPosts(limit);
        return Result.success(hotPosts);
    }

    /**
     * 获取最新回复帖子列表
     * @param limit
     * @return
     */
    @GetMapping("/replies/latest")
    public Result<List<ForumReplyVO>> getLatestReplyPosts(@RequestParam(value = "limit", defaultValue = "10") Integer limit) {

        List<ForumReplyVO> latestReplyPosts = forumPostService.queryLatestReplyPosts(limit);
        return Result.success(latestReplyPosts);
    }

    /**
     * 获取论坛统计信息  帖子总数 今日发帖 注册用户
     * @return 论坛统计信息
     */
    @GetMapping("/stats")
    public Result<ForumStatsVO> getForumStats() {
        ForumStatsVO stats = forumPostService.queryStats();
        return Result.success(stats);
    }

    /**
     * 发布帖子
     * @param forumPostDTO
     * @param token
     * @return
     */
    @PostMapping("/posts")
    public Result<ForumPostVO> publishPost(@RequestBody ForumPostDTO forumPostDTO,
                                           @RequestHeader("Authorization") String token) {
        //1.校验是否登录
        System.out.println("forumPostDTO = " + forumPostDTO);
        Long userId = JwtUtils.parseToken(token);
        if(userId == null) {
            return Result.fail(401, "未登录");
        }
        //2.设置帖子作者
        forumPostDTO.setUserId(userId);
        return forumPostService.publishPost(forumPostDTO);
    }

    /**
     * 获取帖子详情
     * @param postId
     * @return
     */
    @GetMapping("/post/{postId}")
    public Result<ForumPostVO> getPostDetail(@PathVariable("postId") Long postId) {
        return forumPostService.getForumPostById(postId);

    }

    /**
     * 点赞或取消点赞帖子
     * @param postId
     * @param token
     * @return
     */
    @PostMapping("/posts/{postId}/like")
    public Result<Void> likePost(@PathVariable("postId") Long postId,
                                 @RequestHeader("Authorization") String token) {
        //1.校验是否登录
        Long userId = JwtUtils.parseToken(token);
        if(userId == null) {
            return Result.fail(401, "未登录");
        }
        //2.点赞或取消点赞
        return forumPostService.likePost(postId, userId);
    }
    
    /**
     * 收藏或取消收藏帖子
     * @param postId
     * @param token
     * @return
     */
    @PostMapping("/posts/{postId}/collect")
    public Result<Void> collectPost(@PathVariable("postId") Long postId,
                                 @RequestHeader("Authorization") String token) {
        //1.校验是否登录
        Long userId = JwtUtils.parseToken(token);
        if(userId == null) {
            return Result.fail(401, "未登录");
        }
        //2.收藏或取消收藏
        return forumPostService.collectPost(postId, userId);
    }

    /**
     * 评论帖子
     * @param postId
     * @param forumReplyDTO
     * @param token
     * @return
     */
    @PostMapping("/posts/{postId}/comments")
    public Result<Void> commentPost(@PathVariable("postId") Long postId,
                                     @RequestBody ForumReplyDTO forumReplyDTO,
                                     @RequestHeader("Authorization") String token) {
        //1.校验是否登录
        Long userId = JwtUtils.parseToken(token);
        if(userId == null) {
            return Result.fail(401, "未登录");
        }
        //2.设置评论作者
        forumReplyDTO.setUserId(userId);
        //3.评论帖子
        return forumPostService.commentPost(postId, forumReplyDTO);
    }

    /**
     * 回复评论
     * @param commentId
     * @param replyDTO
     * @param token
     * @return
     */
    @PostMapping("/comments/{commentId}/replies")
    public Result<Void> replyComment(@PathVariable("commentId") Long commentId,
                                     @RequestBody ForumReplyDTO replyDTO,
                                     @RequestHeader("Authorization") String token) {
        //1.校验是否登录
        Long userId = JwtUtils.parseToken(token);
        if(userId == null) {
            return Result.fail(401, "未登录");
        }
        //2.设置回复作者
        replyDTO.setUserId(userId);
        //3.回复评论
        return forumReplyService.replyComment(commentId, replyDTO);
    }
}