package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Category;
import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.extend.CategoryExtend;

public interface ICategoryService {
	List<Category> findAll();

	void saveOrUpdate(@Valid Category category);

	void deleteById(@NotNull Long id);

	void batchDelete(Long[] ids);
	
	List<CategoryExtend>cascadeFindById(@NotNull Long id);
}
