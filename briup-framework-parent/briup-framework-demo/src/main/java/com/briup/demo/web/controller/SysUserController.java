package com.briup.demo.web.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.BaseUser;
import com.briup.common.domain.extend.BaseUserExtend;
import com.briup.common.logging.LoggingAccess;
import com.briup.common.util.Response;
import com.briup.common.util.SecurityUtils;
import com.briup.demo.service.IBaseUserServcie;
import com.briup.demo.web.vm.BookVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(description  = "后台人员信息相关接口")
@Validated
@RestController
//@RequestMapping("/BaseUser")
public class SysUserController {
	
	@Autowired
	private IBaseUserServcie baseUserService;
    @ApiOperation(value = "获取登录用户信息",notes = "获取登录用户信息")
    @GetMapping("/user/info")
    public Response<BaseUserExtend> info() {
    	BaseUserExtend baseUserExtend = SecurityUtils.getCurrentUserInfo();
    	System.out.println(baseUserExtend);
        return Response.ok(baseUserExtend);
    }
    
//    @RequestMapping("/info")
//    public Principal me(Principal principal) {
//        return principal;
//    }
//	 @GetMapping("/info")
//		@ApiOperation("登陆后获取用户信息")
//		public Response<BaseUserExtend>findUserByName(@RequestParam String username){
//			return Response.ok(baseUserService.findUserByName(username));
//		}
	 
    @GetMapping("/baseuser/cascadeFindAll")
	@ApiOperation("级联查询所有后台人员信息")
	 public Response<List<BaseUserExtend>> cascadeFindAll() {
        return Response.ok(baseUserService.cascadeFindAll());
    }
	
}
