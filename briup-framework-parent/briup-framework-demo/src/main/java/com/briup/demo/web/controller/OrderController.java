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
import com.briup.common.domain.basic.Order;
import com.briup.common.domain.basic.Orderline;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.domain.extend.OrderExtend;
import com.briup.common.domain.extend.OrderlineExtend;
import com.briup.common.domain.vm.OrderAndOrderLineVM;
import com.briup.common.util.Response;
import com.briup.demo.service.IAddressService;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.IOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="订单管理相关接口")
@Validated
@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private IOrderService orderService;
	
	/*
	 * 将字段更新为已接单
	 * 并且传入orderId和waiterId
	 */
	@GetMapping("/finishOrder")
	@ApiOperation("订单完成")
	public Response<String>finishOrder(@NotNull @RequestParam Long id,@NotNull @RequestParam Long waiterId){
		orderService.finishOrder(id,waiterId);
		return Response.ok("订单完成");
	}
	
	@GetMapping("/takeOrder")
	@ApiOperation("接单")
	public Response<String>takeOrder(@NotNull @RequestParam Long id,@NotNull @RequestParam Long waiterId){
		orderService.takeOrder(id,waiterId);
		return Response.ok("接单成功");
	}
	
	@GetMapping("/findOrdelineByOrderId")
	@ApiOperation("根据订单id查询所有订单项")
	public Response<List<OrderlineExtend>>findOrdelineByOrderId(@NotNull @RequestParam Long orderId){
		return Response.ok(orderService.findOrdelineByOrderId(orderId));
	}
	@GetMapping("/cascadeByCustomerId")
	@ApiOperation("根据顾客ID级联查询订单信息")
	 public Response<OrderExtend> cascadeByCustomerId(@NotNull @RequestParam Long id) {
        return Response.ok(orderService.cascadeByCustomerId(id));
    }
	@GetMapping("/cascadeByCId")
	@ApiOperation("根据订单ID级联查询订单信息")
	 public Response<OrderExtend> cascadeByCId(@NotNull @RequestParam Long id) {
        return Response.ok(orderService.cascadeByCId(id));
    }
	
	@GetMapping("/findAll")
	@ApiOperation("查询所有订单信息")
	 public Response<List<Order>> findAll() {
        return Response.ok(orderService.findAll());
    }

	
	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有订单信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "size",required = true,defaultValue = "4")Integer size) {
		PageHelper.startPage(page, size);
		List<Order> orderList = orderService.findAll();
		PageInfo pageInfo=new PageInfo(orderList);
        return Response.ok(pageInfo);
    }
	@PostMapping("/saveOrUpdate")
	@ApiOperation("保存或者更新订单信息")
	public Response<String>saveOrUpdate(@Valid @ModelAttribute OrderAndOrderLineVM orderAndOrderLineVM){
		orderService.saveOrUpdate(orderAndOrderLineVM);
		return Response.ok("操作成功");
	}
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除订单信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id){
		orderService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除订单信息")
	public Response<String> batchDelete(Long[] ids) throws Exception {
		orderService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
	
	}