package com.shxy.w202350766.campusserviceplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shxy.w202350766.campusserviceplatform.constant.ProductStatusEnum;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.Product;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ProductCategory;
import com.shxy.w202350766.campusserviceplatform.pojo.entity.ProductCollect;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ProductDetailVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ProductListVO;
import com.shxy.w202350766.campusserviceplatform.pojo.vo.ProductVO;
import com.shxy.w202350766.campusserviceplatform.mapper.ProductCategoryMapper;
import com.shxy.w202350766.campusserviceplatform.mapper.ProductCollectMapper;
import com.shxy.w202350766.campusserviceplatform.mapper.ProductMapper;
import com.shxy.w202350766.campusserviceplatform.mapper.UserMapper;
import com.shxy.w202350766.campusserviceplatform.service.ProductCollectService;
import com.shxy.w202350766.campusserviceplatform.service.ProductService;
import com.shxy.w202350766.campusserviceplatform.utils.JwtUtils;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
* @author 33046
* @description 针对表【product(商品表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:58
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{
    @Resource
    private UserMapper userMapper;
    @Resource
    private ProductCategoryMapper categoryMapper;
    @Resource
    private ProductCollectService productCollectService;
    @Resource
    private ProductCollectMapper productCollectMapper;
    
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 查询商品列表
     * @param page
     * @param limit
     * @param categoryId
     * @param keyword
     * @param minPrice
     * @param maxPrice
     * @param condition
     * @param sort
     * @return
     */
    public Result<List<ProductListVO>> queryProductList(Integer page, Integer limit, String categoryId, String keyword, Float minPrice, Float maxPrice, String condition, String sort) {
        // 分页查询商品列表
        Page<Product> productPage = new Page<>(page, limit);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        // 构建查询条件
        if (StringUtils.hasText(categoryId)) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (StringUtils.hasText(keyword)) {
            queryWrapper.like("title", keyword);
        }
        if (minPrice != null) {
            queryWrapper.ge("price", minPrice);
        }
        if (maxPrice != null) {
            queryWrapper.le("price", maxPrice);
        }
        if (StringUtils.hasText(condition)) {
            queryWrapper.eq("status", condition);
        }
        if (StringUtils.hasText(sort)) {
            if (sort.equals("price-asc")) {
                queryWrapper.orderByAsc("price");
            } else if (sort.equals("price-desc")) {
                queryWrapper.orderByDesc("price");
            }
        }
        List<Product> list = this.list(productPage, queryWrapper);
        // 转换为VO列表
        List<ProductListVO> productListVOList = list.stream().map(product -> {
            ProductListVO productListVO = new ProductListVO();
            BeanUtils.copyProperties(product, productListVO);
            
            if (product.getImages() != null) {
                try {
                    if (product.getImages() instanceof String) {
                        productListVO.setImages(objectMapper.readValue((String) product.getImages(),
                                new TypeReference<List<String>>() {}));
                    } else if (product.getImages() instanceof List) {
                        productListVO.setImages((List<String>) product.getImages());
                    } else {
                        productListVO.setImages(new ArrayList<>());
                    }
                } catch (Exception e) {
                    productListVO.setImages(new ArrayList<>());
                }
            } else {
                productListVO.setImages(new ArrayList<>());
            }
            
            if (productListVO.getImages() != null && !productListVO.getImages().isEmpty()) {
                productListVO.setThumbnail(productListVO.getImages().get(0));
            }
            
            productListVO.setUsername(userMapper.selectById(product.getUserId()).getNickname());
            productListVO.setCategoryName(categoryMapper.selectById(product.getCategoryId()).getName());
            return productListVO;
        }).toList();
        return Result.success(productListVOList);
    }
    
    /**
     * 获取热门商品列表
     * @param limit 热门商品数量
     * @return 热门商品列表
     */
    public Result<List<ProductVO>> queryHotProductList(Integer limit) {
        // 分页查询热门商品列表
        Page<Product> productPage = new Page<>(1, limit);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        // 构建查询条件
        queryWrapper.orderByDesc("view_count");
       //执行查询
        List<Product> list = this.list(productPage, queryWrapper);
        List<ProductVO> productVOList = list.stream().map(product -> {
            //转换为VO
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(product, productVO);
            
            if (product.getImages() != null) {
                try {
                    if (product.getImages() instanceof String) {
                        List<String> imageList = objectMapper.readValue((String) product.getImages(),
                                new TypeReference<List<String>>() {});
                        productVO.setImages(imageList);
                    } else if (product.getImages() instanceof List) {
                        productVO.setImages((List<String>) product.getImages());
                    } else {
                        productVO.setImages(new ArrayList<>());
                    }
                } catch (Exception e) {
                    // 解析失败，设置为空列表
                    productVO.setImages(new ArrayList<>());
                }
            } else {
                productVO.setImages(new ArrayList<>());
            }
            
            productVO.setCategoryName(categoryMapper.selectById(product.getCategoryId()).getName());
            return productVO;
        }).toList();
        return Result.success(productVOList);
    }

    /**
     * 查询商品详情
     * @param id 商品ID
     * @return 商品详情
     */
    @Override
    public Result<ProductDetailVO> queryProductDetail(Long id,String token) {
        //根据ID查询商品详情
        Product product = this.getById(id);
        if (product == null) {
            return Result.fail(404, "商品不存在");
        }
        //转换为VO
        ProductDetailVO productDetailVO = new ProductDetailVO();
        BeanUtils.copyProperties(product, productDetailVO);
        
        // 处理图片字段 - 将JSON字符串转换为List<String>
        if (product.getImages() != null) {
            try {
                if (product.getImages() instanceof String) {
                    //解析为JSON数组
                    List<String> imageList = objectMapper.readValue((String) product.getImages(), 
                            new TypeReference<List<String>>() {});
                    productDetailVO.setImages(imageList);
                } else if (product.getImages() instanceof List) {
                    productDetailVO.setImages((List<String>) product.getImages());
                } else {
                    productDetailVO.setImages(new ArrayList<>());
                }
            } catch (Exception e) {
                productDetailVO.setImages(new ArrayList<>());
            }
        } else {
            productDetailVO.setImages(new ArrayList<>());
        }
        
        if (productDetailVO.getImages() != null && !productDetailVO.getImages().isEmpty()) {
            productDetailVO.setThumbnail(productDetailVO.getImages().get(0));
        }

        //检验当前用户是否收藏了该商品
        Long userId = JwtUtils.parseToken(token);
        ProductCollect productCollect = productCollectService.getOne(new QueryWrapper<ProductCollect>().eq("product_id", id).eq("user_id", userId));
        productDetailVO.setIsCollected(productCollect != null);
        
        productDetailVO.setCategoryName(categoryMapper.selectById(product.getCategoryId()).getName());
        return Result.success(productDetailVO);
    }

    /**
     * 收藏或取消收藏商品
     * @param id 商品ID
     * @param token 用户token
     * @return 操作结果
     */
    public Result<Void> collectProduct(Long id, String token) {
        //1.判断商品是否存在
        Product product = this.getById(id);
        if (product == null) {
            return Result.fail(404, "商品不存在");
        }
        //2.根据用户id和商品id查询关联的收藏表
        Long userId = JwtUtils.parseToken(token);
        ProductCollect productCollect = productCollectService.getOne(new QueryWrapper<ProductCollect>().eq("product_id", id).eq("user_id", userId));
        if (productCollect == null) {
            //2.1不存在，说明操作是收藏
            productCollect = new ProductCollect();
            productCollect.setProductId(id);
            productCollect.setUserId(userId);
            productCollect.setCreateTime(LocalDateTime.now());
            productCollectService.save(productCollect);
            product.setCollectCount(product.getCollectCount() + 1);
            this.updateById(product);
        } else {
            //2.2存在，说明操作是取消收藏
            productCollectService.removeById(productCollect.getId());
            product.setCollectCount(product.getCollectCount() - 1);
            this.updateById(product);
        }
        return Result.success(null);
    }
    
    /**
     * 取消收藏商品
     * @param id 商品ID
     * @param token 用户token
     * @return 操作结果
     */
    public Result<Void> deleteCollectProduct(Long id, String token) {
        //1.判断商品是否存在
        Product product = this.getById(id);
        if (product == null) {
            return Result.fail(404, "商品不存在");
        }
        //2.根据用户id和商品id查询关联的收藏表
        Long userId = JwtUtils.parseToken(token);
        ProductCollect productCollect = productCollectService.getOne(new QueryWrapper<ProductCollect>().eq("product_id", id).eq("user_id", userId));
        if (productCollect != null) {
            //存在，删除收藏记录
            productCollectService.removeById(productCollect.getId());
            product.setCollectCount(product.getCollectCount() - 1);
            this.updateById(product);
        }
        return Result.success(null);
    }
    
    /**
     * 发布商品
     * @param product 商品信息
     * @param token 用户token
     * @return 发布结果
     */
    public Result<ProductDetailVO> publishProduct(Product product, String token) {
        // 1.验证必填字段
        if (!StringUtils.hasText(product.getTitle()) || 
            !StringUtils.hasText(product.getDescription()) || 
            product.getPrice() == null ||
            product.getCategoryId() == null) {
            return Result.fail(400, "缺少必填字段");
        }
        
        // 2.验证分类是否存在
        ProductCategory category = categoryMapper.selectById(product.getCategoryId());
        if (category == null) {
            return Result.fail(400, "商品分类不存在");
        }
        
        // 3.从token中获取用户ID
        Long userId = JwtUtils.parseToken(token);
        product.setUserId(userId);
        
        // 4.设置默认值
        product.setViewCount(0);
        product.setCollectCount(0);
        product.setStatus(ProductStatusEnum.WAITING_AUDIT); // 待审核状态
        
        if (product.getImages() != null) {
            try {
                if (product.getImages() instanceof List) {
                    product.setImages(objectMapper.writeValueAsString(product.getImages()));
                } else if (product.getImages() instanceof String) {
                } else {
                    product.setImages("[]");
                }
            } catch (Exception e) {
                product.setImages("[]");
            }
        } else {
            product.setImages("[]");
        }
        
        // 6.保存商品
        this.save(product);
        
        // 7.返回商品详情
        return queryProductDetail(product.getId(), token);
    }
}