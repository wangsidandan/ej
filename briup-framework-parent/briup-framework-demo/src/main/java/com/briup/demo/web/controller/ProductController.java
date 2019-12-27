package com.briup.demo.web.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.common.domain.basic.Category;
import com.briup.common.domain.basic.Product;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.domain.extend.ProductExtend;
import com.briup.common.util.Response;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.IProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="产品管理相关接口")
@Validated
@RestController
@RequestMapping("/product")
public class ProductController {
	@Autowired
	private IProductService productService;
	
	
	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存或者更新产品信息")
	public Response<String>saveOrUpdate(@Valid @ModelAttribute Product product){
		productService.saveOrUpdate(product);
		return Response.ok("操作成功");
	}
	@GetMapping("/findProductById")
	@ApiOperation("根据id查看产品")
	public Response<Product> findProductById(@NotNull @RequestParam Long id) throws Exception {
		return Response.ok(productService.findProductById(id));
	}
	@GetMapping("/findAll")
	@ApiOperation("级联查询所有产品")
	 public Response<List<ProductExtend>> findAll() {
        return Response.ok(productService.findAll());
    }

	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有产品信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) {
		//第一个参数表示当前页数，第二个参数表示当前页显示的产品数量
		PageHelper.startPage(page, size);
		List<ProductExtend> productList = productService.findAll();
		PageInfo pageInfo=new PageInfo(productList);
        return Response.ok(pageInfo);
    }
	
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除产品信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id){
		productService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除产品信息")
	public Response<String> batchDelete(Long[] ids) throws Exception {
		productService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
	
	@GetMapping("/findProductByCategoryId")
	@ApiOperation("根据目录查产品")
	public Response<List<Product>> findProductByCategoryId(@NotNull @RequestParam Long categoryId) throws Exception {
		return Response.ok(productService.findProductByCategoryId(categoryId));
	}
	@GetMapping("/findCategoryByProductId")
	@ApiOperation("根据产品查所在目录")
	public Response<Category> findCategoryByProductId(@NotNull @RequestParam Long productId) throws Exception {
		return Response.ok(productService.findCategoryByProductId(productId));
	}
}