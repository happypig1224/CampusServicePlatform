package com.shxy.w202350766.campusserviceplatform.service;

import com.shxy.w202350766.campusserviceplatform.domain.ForumPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.w202350766.campusserviceplatform.domain.dto.ForumPostDTO;
import com.shxy.w202350766.campusserviceplatform.domain.dto.ForumReplyDTO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumCategoryVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumPostVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumReplyVO;
import com.shxy.w202350766.campusserviceplatform.domain.vo.ForumStatsVO;
import com.shxy.w202350766.campusserviceplatform.utils.Result;

import java.util.List;

/**
* @author 33046
* @description 针对表【forum_post(帖子表)】的数据库操作Service
* @createDate 2025-10-30 17:00:58
*/
public interface ForumPostService extends IService<ForumPost> {

    List<ForumCategoryVO> queryCategoryList();
    List<ForumPostVO> queryList(Integer page, Integer size, Long categoryId, String keyword, String sort);

    List<ForumPostVO> queryHotPosts(Integer limit);

    List<ForumReplyVO> queryLatestReplyPosts(Integer limit);

    ForumStatsVO queryStats();

    Result<ForumPostVO> publishPost(ForumPostDTO postDTO);

    Result<ForumPostVO> getForumPostById(Long postId);

    Result<Void> likePost(Long postId, Long userId);

    Result<Void> commentPost(Long postId, ForumReplyDTO commentVO);

    Result<Void> collectPost(Long postId, Long userId);
}
