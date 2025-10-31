package com.shxy.w202350766.suiestation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.suiestation.domain.Product;
import com.shxy.w202350766.suiestation.service.ProductService;
import com.shxy.w202350766.suiestation.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
* @author 33046
* @description 针对表【product(商品表)】的数据库操作Service实现
* @createDate 2025-10-30 17:00:58
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService{

}




