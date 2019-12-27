package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Category;
import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.basic.Product;
import com.briup.common.domain.extend.ProductExtend;

public interface IProductService {
	List<ProductExtend> findAll();

	void saveOrUpdate(@Valid Product product);

	void deleteById(@NotNull Long id);

	void batchDelete(Long[] ids);

	List<Product> findProductByCategoryId(@NotNull Long categoryId);

	Category findCategoryByProductId(@NotNull Long productId);
	
	Product findProductById(@NotNull Long id);
}
