package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.domain.*;
import com.shxy.w202350766.campusserviceplatform.domain.dto.ForumPostDTO;
import com.shxy.w202350766.campusserviceplatform.domain.dto.ForumReplyDTO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumCategoryVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumPostVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumReplyVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumStatsVO;
import com.shxy.w202350766.campusserviceplatform.mapper.ForumLikeMapper;
import com.shxy.w202350766.campusserviceplatform.mapper.UserMapper;
import com.shxy.w202350766.campusserviceplatform.service.*;
import com.shxy.w202350766.campusserviceplatform.mapper.ForumPostMapper;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 33046
 * @description 针对表【forum_post(帖子表)】的数据库操作Service实现
 * @createDate 2025-10-30 17:00:58
 */
@Service
public class ForumPostServiceImpl extends ServiceImpl<ForumPostMapper, ForumPost>
        implements ForumPostService {
    @Resource
    private ForumSectionService forumSectionService;
    @Resource
    private UserService userService;
    @Resource
    private ForumLikeService forumLikeService;
    @Resource
    private ForumReplyService forumReplyService;
    @Resource
    private ForumCollectService forumCollectService;

    /**
     * 查询版块分类列表
     *
     * @return
     */
    public List<ForumCategoryVO> queryCategoryList() {
        List<ForumPost> postVOS = this.list();
        //将帖子列表转换为map，以版块id为key,帖子列表为value
        Map<Long, List<ForumPost>> postMap = postVOS.stream()
                .collect(Collectors.groupingBy(ForumPost::getSectionId));
        return forumSectionService.list().stream()
                .map(section -> {
                    ForumCategoryVO vo = new ForumCategoryVO();
                    BeanUtils.copyProperties(section, vo);
                    vo.setCategoryId(section.getId());
                    //设置帖子数量
                    vo.setPostCount(Optional.ofNullable(postMap.get(section.getId())).map(List::size).orElse(0));
                    return vo;
                })
                .collect(Collectors.toList());
    }

    /**
     * 查询帖子列表
     *
     * @param page
     * @param size
     * @param categoryId
     * @param keyword
     * @param sort
     * @return
     */
    public List<ForumPostVO> queryList(Integer page, Integer size, Long categoryId, String keyword, String sort) {
        LambdaQueryWrapper<ForumPost> queryWrapper = new LambdaQueryWrapper<>();
        Page<ForumPost> postPage = new Page<>(page, size);
        //根据版块id查询帖子
        if (categoryId != null) {
            queryWrapper.eq(ForumPost::getSectionId, categoryId);
        }
        //根据标题模糊查询
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.like(ForumPost::getTitle, keyword);
        }
        //最新发布，最热门，最精华排序
        if (sort != null && !sort.isEmpty()) {
            //根据创建时间降序排序
            //如果是最新发布排序，需要根据创建时间降序排序
           switch (sort) {
               case "latest":
                   queryWrapper.orderByDesc(ForumPost::getCreateTime);
                   break;
               case "hot":
                   queryWrapper.orderByDesc(ForumPost::getLikeCount);
                   break;
               case "essence":
                   queryWrapper.orderByDesc(ForumPost::getIsEssence);
                   break;
           }
        }
        List<ForumPost> list = this.list(queryWrapper);
        //将分类列表转换为map，以id为key,name为value
        Map<Long, String> categoryMap = forumSectionService.list().stream().collect(Collectors.toMap(ForumSection::getId, ForumSection::getName));
        //将查询结果转换为VO列表
        List<ForumPostVO> voList = list.stream().map(post -> {
            ForumPostVO vo = new ForumPostVO();
            BeanUtils.copyProperties(post, vo);
            //设置用户信息
            User user = userService.getById(post.getUserId());
            vo.setUserNickname(user.getNickname());
            vo.setUserAvatar(user.getAvatar());
            //设置版块名称
            //如果版块id不存在，设置为默认值
            if (post.getSectionId() != null) {
                vo.setSectionName(categoryMap.get(post.getSectionId()));
            } else {
                vo.setSectionName("无版块");
            }
            return vo;
        }).collect(Collectors.toList());
        return voList;
    }

    /**
     * 查询热门帖子列表
     *
     * @param limit
     * @return
     */
    public List<ForumPostVO> queryHotPosts(Integer limit) {
        //自定义排序规则，根据点赞数降序排序
        LambdaQueryWrapper<ForumPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(ForumPost::getLikeCount);
        queryWrapper.last("limit " + limit);
        //以id为key,name为value，将分类列表转换为map
        Map<Long, String> categoryMap = forumSectionService.list().stream().collect(Collectors.toMap(ForumSection::getId, ForumSection::getName));
        return baseMapper.selectList(queryWrapper).stream().map(post -> {
            ForumPostVO vo = new ForumPostVO();
            BeanUtils.copyProperties(post, vo);
            //设置版块名称
            //如果版块id不存在，设置为默认值
            if (post.getSectionId() != null) {
                vo.setSectionName(categoryMap.get(post.getSectionId()));
            } else {
                vo.setSectionName("无版块");
            }
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询最新回复帖子列表
     *
     * @param limit
     * @return
     */
    public List<ForumReplyVO> queryLatestReplyPosts(Integer limit) {
        //自定义排序规则，根据最后回复时间降序排序
        LambdaQueryWrapper<ForumPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(ForumPost::getLastReplyTime);
        queryWrapper.last("limit " + limit);
        Map<Long, String> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, User::getUsername));
        Map<Long, String> postMap = this.list().stream().collect(Collectors.toMap(ForumPost::getId, ForumPost::getTitle));
        return baseMapper.selectList(queryWrapper).stream().map(post -> {
            ForumReplyVO vo = new ForumReplyVO();
            BeanUtils.copyProperties(post, vo);
            //如果用户id不存在，设置为默认值
            if (post.getUserId() != null) {
                vo.setUserNickname(userMap.get(post.getUserId()));
            } else {
                vo.setUserNickname("匿名用户");
            }
            //如果帖子id不存在，设置为默认值
            if (post.getId() != null) {
                vo.setPostTitle(postMap.get(post.getId()));
            } else {
                vo.setPostTitle("无帖子");
            }
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 查询帖子统计信息
     */
    public ForumStatsVO queryStats() {
        //帖子总数 今日发帖
        List<ForumPost> list = this.list();
        //近7天注册用户
        //近7天注册用户
        long totalUsers = userService.list(new LambdaQueryWrapper<User>().gt(User::getCreateTime, LocalDate.now().minusDays(7))).size();
        //帖子总数
        long totalPosts = list.size();
        //今日发帖
        //1.获取当天时间的0点0分0秒0毫秒
        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        //2.过滤出创建时间在今日0点0分0秒0毫秒之后的帖子
        long todayPosts = list.stream().filter(post -> {
            return post.getCreateTime().isAfter(todayStart);
        }).count();
        //返回VO
        ForumStatsVO vo = new ForumStatsVO();
        vo.setTotalPosts(totalPosts);
        vo.setTodayPosts(todayPosts);
        vo.setTotalUsers(totalUsers);
        return vo;
    }

    /**
     * 发布帖子
     *
     * @param postDTO
     * @return 发布的帖子VO
     */
    public Result<ForumPostVO> publishPost(ForumPostDTO postDTO) {
        //1.校验帖子内容是否为空
        if (StringUtils.isBlank(postDTO.getContent())) {
            throw new IllegalArgumentException("帖子内容不能为空");
        }
        //2.设置帖子默认值
        ForumPost post = new ForumPost();
        BeanUtils.copyProperties(postDTO, post);
        post.setViewCount(0);
        post.setReplyCount(0);
        post.setLikeCount(0);
        post.setCollectCount(0);
        post.setIsTop(0);
        post.setIsEssence(0);
        // 显式设置sectionId，确保字段正确复制
        if (postDTO.getSectionId() != null) {
            post.setSectionId(postDTO.getSectionId());
        }
        //3.保存帖子
        int count = baseMapper.insert(post);
        //4.转换为VO
        ForumPostVO postVO = new ForumPostVO();
        BeanUtils.copyProperties(post, postVO);
        if (count > 0) {
            //设置帖子ID
            postVO.setId(post.getId());
        }
        //5.校验帖子是否保存成功
        if (count <= 0) {
            return Result.error("帖子发布失败,服务器异常");
        }
        //4.返回帖子VO
        return Result.success(postVO);
    }

    /**
     * 获取帖子详情
     * @param postId
     * @return
     */
    public Result<ForumPostVO> getForumPostById(Long postId) {
        //1.校验帖子是否存在
        ForumPost post = baseMapper.selectById(postId);
        if(post == null) {
            return Result.fail(404, "帖子不存在");
        }
        //2.增加帖子浏览数
        post.setViewCount(post.getViewCount() + 1);
        baseMapper.updateById(post);
        //3.获取用户信息
        User user = userService.getById(post.getUserId());
        if(user == null) {
            return Result.fail(404, "用户不存在");
        }
        //2.转换为VO
        ForumPostVO vo = new ForumPostVO();
        BeanUtils.copyProperties(post, vo);
        vo.setUserNickname(user.getNickname());
        vo.setUserAvatar(user.getAvatar());
        //3.返回VO
        return Result.success(vo);
    }

    /**
     * 点赞或取消点赞帖子
     * @param postId
     * @param userId
     * @return
     */
    public Result<Void> likePost(Long postId, Long userId) {
        //1.校验帖子是否存在
        ForumPost post = baseMapper.selectById(postId);
        if(post == null) {
            return Result.fail(404, "帖子不存在");
        }
        //2.校验用户是否存在
        User user = userService.getById(userId);
        if(user == null) {
            return Result.fail(404, "用户不存在");
        }
        //3.点赞或取消点赞
        //3.1 检查用户是否已点赞
        LambdaQueryWrapper<ForumLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ForumLike::getPostId, postId)
                .eq(ForumLike::getUserId, userId);
        ForumLike like = forumLikeService.getOne(queryWrapper);
        if (like==null) {
            //未点赞过，新增点赞记录
            ForumLike forumLike = new ForumLike();
            forumLike.setPostId(postId);
            forumLike.setUserId(userId);
            forumLikeService.save(forumLike);
            //增加帖子点赞数
            post.setLikeCount(post.getLikeCount() + 1);
        } else {
            //已点赞过，删除点赞记录
            forumLikeService.remove(queryWrapper);
            //减少帖子点赞数
            post.setLikeCount(post.getLikeCount() - 1);
        }
        //3.2 更新帖子点赞数
        baseMapper.updateById(post);
        //4.返回结果
        return Result.success(null);
    }

    /**
     * 评论帖子
     * @param postId
     * @param forumReplyDTO
     * @return
     */
    public Result<Void> commentPost(Long postId, ForumReplyDTO forumReplyDTO) {
        //1.校验帖子是否存在
        ForumPost post = baseMapper.selectById(postId);
        if(post == null) {
            return Result.fail(404, "帖子不存在");
        }
        //2.校验用户是否存在
        User user = userService.getById(forumReplyDTO.getUserId());
        if(user == null) {
            return Result.fail(404, "用户不存在");
        }
        //3.评论帖子
        //3.1 设置评论默认值
        forumReplyDTO.setPostId(postId);
        //3.2 保存评论
        ForumReply reply = new ForumReply();
        BeanUtils.copyProperties(forumReplyDTO, reply);
        boolean saved = forumReplyService.save(reply);
        if (!saved) {
            return Result.error("评论失败,服务器异常");
        }
        //4.更新帖子评论数
        post.setReplyCount(post.getReplyCount() + 1);
        baseMapper.updateById(post);
        //5.返回结果
        return Result.success(null);
    }

    /**
     * 收藏或取消收藏帖子
     * @param postId
     * @param userId
     * @return
     */
    public Result<Void> collectPost(Long postId, Long userId) {
        //1.校验帖子是否存在
        ForumPost post = baseMapper.selectById(postId);
        if(post == null) {
            return Result.fail(404, "帖子不存在");
        }
        //2.校验用户是否存在
        User user = userService.getById(userId);
        if(user == null) {
            return Result.fail(404, "用户不存在");
        }
        //3.收藏或取消收藏
        //3.1 检查用户是否已收藏
        LambdaQueryWrapper<ForumCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ForumCollect::getPostId, postId)
                .eq(ForumCollect::getUserId, userId);
        ForumCollect collect = forumCollectService.getOne(queryWrapper);
        if (collect==null) {
            //未收藏过，新增收藏记录
            ForumCollect forumCollect = new ForumCollect();
            forumCollect.setPostId(postId);
            forumCollect.setUserId(userId);
            forumCollectService.save(forumCollect);
            //增加帖子收藏数
            post.setCollectCount(post.getCollectCount() + 1);

        } else {
            //已收藏过，删除收藏记录
            forumCollectService.remove(queryWrapper);
            //减少帖子收藏数
            post.setCollectCount(post.getCollectCount() - 1);
        }
        //3.2 更新帖子收藏数
        baseMapper.updateById(post);
        //4.返回结果
        return Result.success(null);
    }
}