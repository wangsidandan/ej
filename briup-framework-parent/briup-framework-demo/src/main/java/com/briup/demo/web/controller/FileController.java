package com.briup.demo.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.briup.common.util.Response;
import com.briup.demo.service.IFileService;

import io.swagger.annotations.Api;

//@Api(description="文件上传管理相关接口")
//@Validated
//@RestController
//@RequestMapping("/file")
//public class FileController {
//	@Autowired
//	private IFileService fileServcie;
//	public Response<String>upload(@RequestParam("file") MultipartFile file,HttpServletRequest request){
//		//获取原始文件名称
//		String fileName=file.getOriginalFilename();
//		String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
//		if()
//		return null;
//		
//	}
//}
