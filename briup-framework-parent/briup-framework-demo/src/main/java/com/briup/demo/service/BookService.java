//package com.briup.demo.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.briup.common.domain.basic.Book;
//import com.briup.common.mapper.basic.BookMapper;
//import com.briup.common.util.EntityUtils;
//import com.briup.demo.web.vm.BookVM;
//
//@Service
//public class BookService {
//	
//	@Autowired
//	private BookMapper bookMapper;
//	
//	public List<BookVM> findAllBook(){
//		
//		List<BookVM> vms = null;
//		
//		List<Book> list = bookMapper.selectAll();
//		if(list != null) {
//			vms = EntityUtils.entity2VMList(list, BookVM.class);
//		}
//		
//		return vms;
//	}
//	
//	public BookVM findBookById(String id){
//		
//		BookVM vm = null;
//		
//		Book book = bookMapper.selectByPrimaryKey(id);
//		
//		if(book != null) {
//			vm = EntityUtils.entity2VM(book, BookVM.class);
//		}
//		
//		return vm;
//	}
//	
//}
