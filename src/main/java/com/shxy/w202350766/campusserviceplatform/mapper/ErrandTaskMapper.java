package com.shxy.w202350766.campusserviceplatform.mapper;

import com.shxy.w202350766.campusserviceplatform.pojo.entity.ErrandTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 33046
* @description 针对表【errand_task(跑腿任务表)】的数据库操作Mapper
* @createDate 2025-10-30 17:00:58
* @Entity com.shxy.w202350766.suiestation.domain.ErrandTask
*/
public interface ErrandTaskMapper extends BaseMapper<ErrandTask> {

    @Select("select * from errand_task where  order by  limit #{limit}")
    List<ErrandTask> list(Integer limit);

    @Select("select COUNT(*) from errand_task where acceptor_id=#{revieweeId} and status='COMPLETED'")
    Integer selectCompleteTask(Long revieweeId);
    
    @Select("select AVG(TIMESTAMPDIFF(HOUR, accept_time, complete_time)) from errand_task where acceptor_id=#{userId} and status='COMPLETED' and accept_time is not null and complete_time is not null")
    Double selectAverageCompletionTime(Long userId);
    
    @Select("select COUNT(*) from errand_task where acceptor_id=#{userId}")
    Integer selectTotalAcceptedTasks(Long userId);

    @Select("select COUNT(*) from errand_task where user_id=#{userId}")
    Long count(Long userId);

    @Update("update errand_task set status=#{status}, acceptor_id=null, accept_time=null where id=#{id}")
    void updateTask(ErrandTask task);
}