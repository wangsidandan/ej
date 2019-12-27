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
import com.briup.common.domain.basic.Product;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.domain.extend.ProductExtend;
import com.briup.common.mapper.basic.CategoryMapper;
import com.briup.common.mapper.basic.CustomerMapper;
import com.briup.common.mapper.basic.ProductMapper;
import com.briup.common.mapper.extend.CategroyExtendMapper;
import com.briup.common.mapper.extend.ProductExtendMapper;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.service.IProductService;
@Service
public class ProductServiceImpl implements IProductService{
	//逆向生成的基本productMapper
	@Autowired
	private ProductMapper productMapper;
	//手动写的扩展procuctExtendMapper
	@Autowired
	private ProductExtendMapper productExtendMapper;

	@Override
	public List<ProductExtend> findAll() {
		return productExtendMapper.findAll();
	}
	/*
	 * 如果传入的pojo类的id有值，则为更新操作，否则为插入操作
	 */
	@Override
	public void saveOrUpdate(@Valid Product product) {
		if(product.getId()!=null) {
			productMapper.updateByPrimaryKey(product);
		}else {
			productMapper.insert(product);
		}
		
	}
	@Override
	public void deleteById(@NotNull Long id) {
		productMapper.deleteByPrimaryKey(id);
	}
		/*
		 * 此处选择在service批量删除，为了手动控制事务
		 * 也可在xml/sql语句中传入数组批量删除，没有事务效果
		 */
	 @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	    @Override
	    public void batchDelete(Long[] ids) {
	        for (long id:ids) {
	            this.deleteById(id);
	        }
	    }
	 /*
	  * 联立ej_product和ej_category表
	  * 根据目录查询属于该目录下的所有产品
	  */
	@Override
	public List<Product> findProductByCategoryId(@NotNull Long categoryId) {
		List<Product> productList = productExtendMapper.findProductByCategoryId(categoryId);
		System.out.println("根据ID查出的产品集合"+productList);
		return productList;
	}
	 /*
	  * 联立ej_product和ej_category表
	  * 级联查村产品以及所在目录
	  */
	@Override
	public Category findCategoryByProductId(@NotNull Long productId) {
		// TODO Auto-generated method stub
		return productExtendMapper.findCategoryByProductId(productId);
	}
	@Override
	public Product findProductById(@NotNull Long id) {
		// TODO Auto-generated method stub
		return productMapper.selectByPrimaryKey(id);
	}

	
	

}
