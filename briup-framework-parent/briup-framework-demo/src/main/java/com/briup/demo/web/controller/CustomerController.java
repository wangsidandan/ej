package com.briup.demo.web.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.util.Response;
import com.briup.common.util.SecurityUtils;
import com.briup.demo.service.ICustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="顾客管理相关接口")
@Validated
@RestController
@RequestMapping("/user/customer")
public class CustomerController {
	@Autowired
	private ICustomerService customerService;
	/*
	 * An Authentication object was not found in the SecurityContext	
	 * 已有权限：/customer/findAll	
	 * 权限需要认证才能生效
	 * 不写@PreAuthorize注解 默认都有权限访问
	 */	
	@PreAuthorize("hasAuthority('/customer/findAll')")
	@GetMapping("/findAll")
	@ApiOperation("查询所有顾客信息")
	 public Response<List<Customer>> findAll() {
        return Response.ok(customerService.findAll());
    }
	
	@GetMapping("/findCustomerById")
	@ApiOperation("根据id查询顾客信息")
	 public Response<Customer> findCustomerById(@NotNull @RequestParam Long id) {
        return Response.ok(customerService.findCustomerById(id));
    }
	
	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有顾客信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer pageSize) {
		//分页插件设置起始页和每页显示的页数
		PageHelper.startPage(page, pageSize);
		List<Customer> customerList = customerService.findAll();
		PageInfo pageInfo=new PageInfo(customerList);
        return Response.ok(pageInfo);
    }
	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存或者更新顾客信息")
	public Response<String>saveOrUpdate(@Valid @ModelAttribute Customer customer){
		customerService.saveOrUpdate(customer);
		return Response.ok("操作成功");
	}
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除顾客信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id) throws Exception{
		customerService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除顾客信息")
	public Response<String> batchDelete(@RequestParam Long[] ids) throws Exception {
		customerService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
}
