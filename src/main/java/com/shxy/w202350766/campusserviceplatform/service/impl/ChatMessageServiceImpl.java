package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ChatMessage;
import com.shxy.w202350766.campusserviceplatform.service.ChatMessageService;
import com.shxy.w202350766.campusserviceplatform.mapper.ChatMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 33046
* @description 针对表【chat_message(聊天消息表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:58
*/
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageMapper, ChatMessage>
    implements ChatMessageService{

}




