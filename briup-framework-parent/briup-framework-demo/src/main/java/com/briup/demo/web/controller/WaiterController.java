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

import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.basic.Waiter;
import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.util.Response;
import com.briup.common.util.SecurityUtils;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.service.IWaiterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="服务员管理相关接口")
@Validated
@RestController
@RequestMapping("/waiter")
public class WaiterController {
	@Autowired
	private IWaiterService waiterService;
	
	@GetMapping("/findAll")
	@ApiOperation("查询所有服务员信息")
	 public Response<List<Waiter>> findAll() {
        return Response.ok(waiterService.findAll());
    }
	
	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有服务员信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) {
		PageHelper.startPage(page, size);
		List<Waiter> waiterList = waiterService.findAll();
		PageInfo pageInfo=new PageInfo(waiterList);
        return Response.ok(pageInfo);
    }
	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存或者更新服务员信息")
	public Response<String>saveOrUpdate(@Valid @ModelAttribute Waiter waiter){
		waiterService.saveOrUpdate(waiter);
		return Response.ok("操作成功");
	}
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除服务员信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id){
		waiterService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除服务员信息")
	public Response<String> batchDelete(long[] ids) throws Exception {
		waiterService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
}
