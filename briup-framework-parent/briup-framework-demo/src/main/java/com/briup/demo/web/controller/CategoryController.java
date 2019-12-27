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
import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.util.Response;
import com.briup.common.util.SecurityUtils;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.ICustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="栏目管理相关接口")
@Validated
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping("/findAll")
	@ApiOperation("查询所有栏目")
	 public Response<List<Category>> findAll() {
        return Response.ok(categoryService.findAll());
    }
	@GetMapping("/findCategoryByParentId")
	@ApiOperation("查询所有子栏目")
	 public Response<List<CategoryExtend>> cascadeFindById(@NotNull @RequestParam Long id) {
        return Response.ok(categoryService.cascadeFindById(id));
    }
	
	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有目录信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) {
		PageHelper.startPage(page, size);
		List<Category> categoryList = categoryService.findAll();
		PageInfo pageInfo=new PageInfo(categoryList);
        return Response.ok(pageInfo);
    }
	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存或者更新目录信息")
	public Response<String>saveOrUpdate(@Valid @ModelAttribute Category category){
		categoryService.saveOrUpdate(category);
		return Response.ok("操作成功");
	}
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除目录信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id){
		categoryService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除目录信息")
	public Response<String> batchDelete(Long[] ids) throws Exception {
		categoryService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
}
