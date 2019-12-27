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

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Category;
import com.briup.common.domain.extend.AddressExtend;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.util.Response;
import com.briup.demo.service.IAddressService;
import com.briup.demo.service.ICategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="地址管理相关接口")
@Validated
@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	private IAddressService addressService;
	
	@GetMapping("/findAll")
	@ApiOperation("查询所有地址信息")
	 public Response<List<Address>> findAll() {
        return Response.ok(addressService.findAll());
    }

	
	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有地址信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) {
		PageHelper.startPage(page, size);
		List<Address> addressList = addressService.findAll();
		PageInfo pageInfo=new PageInfo(addressList);
        return Response.ok(pageInfo);
    }
	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存或者更新地址信息")
	public Response<String>saveOrUpdate(@Valid @ModelAttribute Address address){
		addressService.saveOrUpdate(address);
		return Response.ok("操作成功");
	}
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除地址信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id){
		addressService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除地址信息")
	public Response<String> batchDelete(@RequestParam Long[] ids) throws Exception {
		addressService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
	@PostMapping("/findByCustomerId")
	@ApiOperation("通过顾客id查地址")
	public Response<Address> findByCustomerId(@RequestParam Long id) throws Exception {
		return Response.ok(addressService.findByCustomerId(id));
	}
	
	@PostMapping("/findAllAddressWithCustomer")
	@ApiOperation("级联地址查出顾客信息")
	public Response<AddressExtend> findAllAddressWithCustomer() throws Exception {
		return Response.ok(addressService.findAllAddressWithCustomer());
	}
	
	}