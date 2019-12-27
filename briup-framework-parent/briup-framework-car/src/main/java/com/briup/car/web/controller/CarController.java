package com.briup.car.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.briup.car.service.CarService;
import com.briup.car.web.vm.CarVM;
import com.briup.common.domain.sys.User;
import com.briup.common.logging.LoggingAccess;
import com.briup.common.util.Response;
import com.briup.common.util.SecurityUtils;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "车模块")
@LoggingAccess("车模块")
@RestController
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@GetMapping("/test")
	public String test() {
		return "hello test!";
	}
	
	@GetMapping("/car")
	public Response<List<CarVM>> findAllBook() {
		return Response.ok(carService.findAllCars());
	}
	
//	@ApiOperation(value = "查找指定编号的车信息",notes = "查找指定编号的车信息，需要传入车编号")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "id",value = "车编号",required = true,paramType = "path"),
//    })
////	@PreAuthorize("hasRole('ADMIN')")
//	@PreAuthorize("hasAuthority('ROLE_USER')")
//	@GetMapping("/user/car/{id}")
//	public Response<CarVM> findCarById(@PathVariable String id) {
//		return Response.ok(carService.findCarById(id));
//	}
	
    @ApiOperation(value = "获取登录用户信息",notes = "获取登录用户信息")
    @GetMapping("/user/me")
    public Response<User> me() {
        return Response.ok(SecurityUtils.getCurrentUserInfo());
    }
	
}
