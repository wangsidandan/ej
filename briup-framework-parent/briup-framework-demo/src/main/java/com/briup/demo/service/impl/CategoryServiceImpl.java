package com.briup.demo.service.impl;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.briup.common.domain.basic.Category;
import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.mapper.basic.CategoryMapper;
import com.briup.common.mapper.basic.CustomerMapper;
import com.briup.common.mapper.extend.CategroyExtendMapper;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.ICustomerService;
@Service
public class CategoryServiceImpl implements ICategoryService{
	@Autowired
	private CategoryMapper categoryMapper;
	
	@Autowired
	private CategroyExtendMapper categoryExtendMapper;
	@Override
	public List<Category> findAll() {
		return categoryMapper.selectAll();
	}
	@Override
	public void saveOrUpdate(@Valid Category category) {
		if(category.getId()!=null) {
			categoryMapper.updateByPrimaryKey(category);
		}else {
			categoryMapper.insert(category);
		}
		
	}
	@Override
	public void deleteById(@NotNull Long id) {
		categoryMapper.deleteByPrimaryKey(id);
	}
	 @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	    @Override
	    public void batchDelete(Long[] ids) {
	        for (long id:ids) {
	            this.deleteById(id);
	        }
	    }
	@Override
	public List<CategoryExtend> cascadeFindById(@NotNull Long id) {
		return categoryExtendMapper.cascadeFindById(id);
	}

}
