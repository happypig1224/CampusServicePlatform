package com.shxy.w202350766.campusserviceplatform.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shxy.w202350766.campusserviceplatform.mapper.LostItemCategoryMapper;
import com.shxy.w202350766.campusserviceplatform.pojo.dto.LostFoundItemDTO;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.LostAndFound;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.LostItemCategory;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.User;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundCategoryVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundItemDetailVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.LostFoundItemVO;
import com.shxy.w202350766.campusserviceplatform.service.LostAndFoundService;
import com.shxy.w202350766.campusserviceplatform.mapper.LostAndFoundMapper;
import com.shxy.w202350766.campusserviceplatform.service.LostItemCategoryService;
import com.shxy.w202350766.campusserviceplatform.service.OssService;
import com.shxy.w202350766.campusserviceplatform.service.UserService;
import com.shxy.w202350766.campusserviceplatform.utils.JwtUtils;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 33046
 * @description 针对表【lost_and_found(失物招领表)】的数据库操作Service实现
 * @createDate 2025-10-30 17:00:58
 */
@Service
public class LostAndFoundServiceImpl extends ServiceImpl<LostAndFoundMapper, LostAndFound>
        implements LostAndFoundService {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private LostItemCategoryService lostItemCategoryService;
    @Resource
    private UserService userService;
    @Resource
    private OssService ossService;

    /**
     * 获取失物招领分类列表
     *
     * @return
     */
    public List<LostFoundCategoryVO> getCategories() {
        // 获取所有失物招领记录
        List<LostAndFound> list = this.list();

        // 获取所有分类
        List<LostItemCategory> itemCategories = lostItemCategoryService.list();
        Map<Long, String> categoryMap = itemCategories.stream().collect(Collectors.toMap(LostItemCategory::getId, LostItemCategory::getName));
        // 将分类转换为VO对象
        List<LostFoundCategoryVO> lostFoundCategoryVOList =
                list.stream().map(item -> {
                    LostFoundCategoryVO lostFoundCategoryVO = new LostFoundCategoryVO();
                    BeanUtils.copyProperties(item, lostFoundCategoryVO);
                    try {
                        lostFoundCategoryVO.setIcon(objectMapper.readValue(item.getImages().toString(), new TypeReference<List<String>>() {
                        }).get(0));
                    } catch (JsonProcessingException e) {
                        lostFoundCategoryVO.setIcon("");
                    }
                    lostFoundCategoryVO.setName(categoryMap.get(item.getCategoryId()));
                    lostFoundCategoryVO.setItemCount((int) list.stream().filter(i -> i.getCategoryId().equals(item.getCategoryId())).count());
                    return lostFoundCategoryVO;
                }).collect(Collectors.toList());
        return lostFoundCategoryVOList;
    }


    /**
     * 获取最新的失物招领项目列表
     *
     * @param limit 项目数量限制
     * @return 最新的失物招领项目列表
     */
    public List<LostFoundItemVO> getLatestItems(Integer limit) {
        Page<LostAndFound> page = new Page<>(1, limit);
        List<LostAndFound> list = this.list(page);
        Map<Long, String> categoryMap = lostItemCategoryService.list().stream().collect(Collectors.toMap(LostItemCategory::getId, LostItemCategory::getName));
        return list.stream().map(item -> {
            LostFoundItemVO lostFoundItemVO = new LostFoundItemVO();
            lostFoundItemVO.setId(item.getId());
            lostFoundItemVO.setUserId(item.getUserId());
            lostFoundItemVO.setType((String) item.getType());
            lostFoundItemVO.setTitle(item.getTitle());
            lostFoundItemVO.setDescription(item.getDescription());
            lostFoundItemVO.setLostFoundTime(item.getLostFoundTime());
            lostFoundItemVO.setLocation(item.getLocation());
            try {
                lostFoundItemVO.setImages(objectMapper.readValue(item.getImages().toString(), new TypeReference<List<String>>() {
                }));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            lostFoundItemVO.setCreateTime(item.getCreateTime());
            lostFoundItemVO.setUpdateTime(item.getUpdateTime());
            lostFoundItemVO.setCategoryName(categoryMap.get(item.getCategoryId()));
            return lostFoundItemVO;
        }).toList();
    }

    /**
     * 获取失物招领物品列表
     *
     * @param categoryId 分类ID（可选）
     * @param page       页码
     * @param limit      项目数量限制
     * @param sort       排序方式（latest/popular/resolved）
     * @param type       搜索类型（all/lost/found）
     * @param keyword    搜索关键词（可选）
     * @return 失物招领物品列表
     */
    public List<LostFoundItemVO> getItems(Long categoryId, Integer limit, String sort, Integer page, String type, String keyword) {
        Page<LostAndFound> queryPage = new Page<>(page, limit);
        LambdaQueryWrapper<LostAndFound> queryWrapper = new LambdaQueryWrapper<>();

        // 处理排序
        switch (sort) {
            case "latest":
                queryWrapper.orderByDesc(LostAndFound::getCreateTime);
                break;
            case "popular":
                queryWrapper.orderByDesc(LostAndFound::getCreateTime); // 临时按创建时间排序，可以后续创建浏览量等修改
                break;
            case "resolved":
                queryWrapper.eq(LostAndFound::getStatus, "RESOLVED");
                queryWrapper.orderByDesc(LostAndFound::getResolveTime);
                break;
            default:
                queryWrapper.orderByDesc(LostAndFound::getCreateTime);
                break;
        }

        // 处理分类过滤
        if (categoryId != null) {
            queryWrapper.eq(LostAndFound::getCategoryId, categoryId);
        }

        // 处理类型过滤
        switch (type.toLowerCase()) {
            case "lost":
                queryWrapper.eq(LostAndFound::getType, "LOST");
                break;
            case "found":
                queryWrapper.eq(LostAndFound::getType, "FOUND");
                break;
            case "all":
            default:
                // 不添加类型过滤，获取所有类型
                break;
        }

        // 处理关键词搜索
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper ->
                    wrapper.like(LostAndFound::getTitle, keyword)
                            .or()
                            .like(LostAndFound::getDescription, keyword)
                            .or()
                            .like(LostAndFound::getLocation, keyword)
            );
        }
        Map<Long, String> categoryMap = lostItemCategoryService.list().stream().collect(Collectors.toMap(LostItemCategory::getId, LostItemCategory::getName));
        Map<Long, String> userMap = userService.list().stream().collect(Collectors.toMap(User::getId, User::getNickname));
        List<LostAndFound> list = this.list(queryPage, queryWrapper);
        List<LostFoundItemVO> lostFoundItemVOS = list.stream().map(item -> {
            LostFoundItemVO lostFoundItemVO = new LostFoundItemVO();
            lostFoundItemVO.setId(item.getId());
            lostFoundItemVO.setUserId(item.getUserId());
            lostFoundItemVO.setType((String) item.getType());
            lostFoundItemVO.setTitle(item.getTitle());
            lostFoundItemVO.setDescription(item.getDescription());
            lostFoundItemVO.setLostFoundTime(item.getLostFoundTime());
            lostFoundItemVO.setLocation(item.getLocation());
            lostFoundItemVO.setStatus((String) item.getStatus());
            //获取分类名称
            lostFoundItemVO.setCategoryName(categoryMap.get(item.getCategoryId()));
            //获取用户昵称
            lostFoundItemVO.setUsername(userMap.get(item.getUserId()));
            try {
                lostFoundItemVO.setImages(objectMapper.readValue(item.getImages().toString(), new TypeReference<List<String>>() {
                }));
            } catch (JsonProcessingException e) {
                lostFoundItemVO.setImages(List.of()); // 如果解析失败，返回空列表
            }
            lostFoundItemVO.setCreateTime(item.getCreateTime());
            lostFoundItemVO.setUpdateTime(item.getUpdateTime());
            return lostFoundItemVO;
        }).toList();
        return lostFoundItemVOS;
    }

    /**
     * 添加失物物品
     *
     * @param dto 失物物品DTO
     * @return 失物物品VO
     */
    public LostFoundItemVO addLostItems(LostFoundItemDTO dto, String token) {
        // 从token中提取用户ID
        Long userId = JwtUtils.parseToken(token);

        LostAndFound item = new LostAndFound();
        // 手动设置字段值，确保必填字段都有值
        item.setType(dto.getType());
        item.setTitle(dto.getTitle());
        item.setDescription(dto.getDescription());
        item.setCategoryId(dto.getCategoryId());
        item.setLostFoundTime(dto.getLostFoundTime());
        item.setLocation(dto.getLocation());
        // 设置联系方式（DTO中的contact对应实体类的contactInfo）
        item.setContactInfo(dto.getContact());

        // 设置必填字段的默认值
        item.setUrgency("NORMAL");  // 默认普通紧急程度
        item.setStatus("PENDING");   // 默认待处理状态

        // 处理图片
        if (dto.getImages() != null) {
            try {
                if (dto.getImages() instanceof List) {
                    item.setImages(objectMapper.writeValueAsString(dto.getImages()));
                } else if (dto.getImages() instanceof String) {
                    item.setImages((String) dto.getImages());
                } else {
                    item.setImages("[]");
                }
            } catch (Exception e) {
                item.setImages("[]");
            }
        } else {
            item.setImages("[]");
        }

        item.setUserId(userId);
        item.setCreateTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());

        this.save(item);
        LostFoundItemVO lostFoundItemVO = BeanUtil.copyProperties(item, LostFoundItemVO.class);
        return lostFoundItemVO;
    }

    /**
     * 获取失物物品详情
     *
     * @param id 失物物品ID
     * @return 失物物品详情VO
     */
    public Result<LostFoundItemDetailVO> getItemDetail(Long id) {
        LostAndFound item = this.getById(id);
        if (item == null) {
            return Result.fail("物品不存在");
        }
        Map<Long, String> categoryMap = lostItemCategoryService.list().stream().collect(Collectors.toMap(LostItemCategory::getId, LostItemCategory::getName));
        LostFoundItemDetailVO lostFoundItemDetailVO=new LostFoundItemDetailVO();
        lostFoundItemDetailVO.setId(item.getId());
        lostFoundItemDetailVO.setType((String) item.getType());
        lostFoundItemDetailVO.setTitle(item.getTitle());
        lostFoundItemDetailVO.setDescription(item.getDescription());
        lostFoundItemDetailVO.setLocation(item.getLocation());
        lostFoundItemDetailVO.setStatus((String) item.getStatus());
        // 设置联系方式（实体类中的contactInfo对应VO中的contact）
        lostFoundItemDetailVO.setContact(item.getContactInfo());
        //获取分类名称
        lostFoundItemDetailVO.setCategoryName(categoryMap.get(item.getCategoryId()));

        // 根据类型设置时间字段
        if ("LOST".equals(item.getType())) {
            lostFoundItemDetailVO.setLostTime(item.getLostFoundTime());
        } else if ("FOUND".equals(item.getType())) {
            lostFoundItemDetailVO.setFoundTime(item.getLostFoundTime());
        }

        // 设置特定字段
        if ("FOUND".equals(item.getType())) {
            // 招领物品设置存放地点
            lostFoundItemDetailVO.setStorage(item.getLocation());
        } else if ("LOST".equals(item.getType())) {
            // 失物设置悬赏金额
            lostFoundItemDetailVO.setReward(item.getReward());
        }

        try {
            lostFoundItemDetailVO.setImages(objectMapper.readValue(item.getImages().toString(), new TypeReference<List<String>>() {
            }));
        } catch (JsonProcessingException e) {
            lostFoundItemDetailVO.setImages(List.of()); // 如果解析失败，返回空列表
        }

        lostFoundItemDetailVO.setCreateTime(item.getCreateTime());
        lostFoundItemDetailVO.setViewCount(item.getViewCount());
        // 更新浏览次数
        item.setViewCount(item.getViewCount() + 1);
        this.updateById(item);
        return Result.success(lostFoundItemDetailVO);
    }

    /**
     * 认领失物招领物品
     * @param itemId 物品ID
     * @param token  用户token
     * @return 操作结果
     */
    public Result<?> claimItem(Long itemId, String token) {
        Long userId = JwtUtils.parseToken(token);
        if (userId==null) {
            return Result.fail("用户未登录");
        }
        LostAndFound item = this.getById(itemId);
        if (item == null) {
            return Result.fail("物品不存在");
        }
        if (!"PENDING".equals(item.getStatus())) {
            return Result.fail("物品已被处理");
        }
        //TODO 后期增加认领人信息方便对证物品
        item.setStatus("RESOLVED");
        item.setResolveTime(LocalDateTime.now());
        this.updateById(item);
        return Result.success("认领成功");
    }

}