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
import com.briup.common.domain.basic.Comment;
import com.briup.common.domain.extend.CategoryExtend;
import com.briup.common.util.Response;
import com.briup.demo.service.IAddressService;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.service.ICommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description="评论相关接口")
@Validated
@RestController
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private ICommentService commentService;
	
	@GetMapping("/findAll")
	@ApiOperation("查询所有评论信息")
	 public Response<List<Comment>> findAll() {
        return Response.ok(commentService.findAll());
    }

	
	@GetMapping("/pageFindAll")
	@ApiOperation("分页查询所有评论信息")
	 public Response<PageInfo> pageFindAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "pageSize",required = true,defaultValue = "4")Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Comment> addressList = commentService.findAll();
		PageInfo pageInfo=new PageInfo(addressList);
        return Response.ok(pageInfo);
    }
	@PostMapping("/save")
	@ApiOperation("保存评论")
	public Response<String>save(@Valid @ModelAttribute Comment comment){
		commentService.save(comment);
		return Response.ok("操作成功");
	}
	
	@GetMapping("/deleteById")
	@ApiOperation("根据ID删除评论信息")
	public Response<String>deleteById(@NotNull @RequestParam Long id){
		commentService.deleteById(id);
		return Response.ok("删除成功");
	}
	
	@PostMapping("/batchDelete")
	@ApiOperation("批量删除评论信息")
	public Response<String> batchDelete(Long[] ids) throws Exception {
		commentService.batchDelete(ids);
		return Response.ok("批量删除成功");
	}
	}