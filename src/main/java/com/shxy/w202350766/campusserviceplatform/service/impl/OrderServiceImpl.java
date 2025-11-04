package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.Order;
import com.shxy.w202350766.campusserviceplatform.service.OrderService;
import com.shxy.w202350766.campusserviceplatform.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author 33046
* @description 针对表【order(订单表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:58
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




