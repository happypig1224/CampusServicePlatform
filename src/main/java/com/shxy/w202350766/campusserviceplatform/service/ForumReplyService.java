package com.shxy.w202350766.campusserviceplatform.service;

import com.shxy.w202350766.campusserviceplatform.domain.ForumReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.w202350766.campusserviceplatform.domain.dto.ForumReplyDTO;
import com.shxy.w202350766.campusserviceplatform.utils.Result;

/**
* @author 33046
* @description 针对表【forum_reply(帖子回复表)】的数据库操作Service
* @createDate 2025-10-30 17:00:58
*/
public interface ForumReplyService extends IService<ForumReply> {

    Result<Void> replyComment(Long replyId, ForumReplyDTO replyDTO);

}
