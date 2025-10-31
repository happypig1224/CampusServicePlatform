package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.domain.ForumReply;
import com.shxy.w202350766.campusserviceplatform.domain.dto.ForumReplyDTO;
import com.shxy.w202350766.campusserviceplatform.service.ForumReplyService;
import com.shxy.w202350766.campusserviceplatform.mapper.ForumReplyMapper;
import com.shxy.w202350766.campusserviceplatform.utils.JwtUtils;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
* @author 33046
* @description 针对表【forum_reply(帖子回复表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:58
*/
@Service
public class ForumReplyServiceImpl extends ServiceImpl<ForumReplyMapper, ForumReply>
    implements ForumReplyService{
    @Resource
    private ForumReplyMapper forumReplyMapper;

    /**
     * 回复评论
     * @param replyId 回复ID
     * @param replyDTO 回复DTO
     * @return 回复结果
     */
    public Result<Void> replyComment(Long replyId, ForumReplyDTO replyDTO) {
        //1.找到关联的评论
        ForumReply reply = forumReplyMapper.selectById(replyId);
        if(reply == null) {
            return Result.fail(404, "评论不存在");
        }
        //2.设置回复作者
        replyDTO.setUserId(reply.getUserId());
        //3.回复评论
        ForumReply forumReply = new ForumReply();
        forumReply.setPostId(reply.getPostId());
        forumReply.setParentId(replyId);
        forumReply.setUserId(replyDTO.getUserId());
        forumReply.setContent(replyDTO.getContent());
        forumReplyMapper.insert(forumReply);
        return Result.success(null);
    }
}




