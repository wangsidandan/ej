package com.briup.common.mapper.extend;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Category;
import com.briup.common.domain.basic.Product;
import com.briup.common.domain.extend.ProductExtend;

public interface ProductExtendMapper {
	//产品的扩展mapper
	List<Product>findProductByCategoryId(Long categoryId);
	Category findCategoryByProductId(@NotNull Long productId);
	List<ProductExtend> findAll();
}
