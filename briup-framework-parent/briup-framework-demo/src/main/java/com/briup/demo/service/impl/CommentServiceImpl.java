package com.briup.demo.service.impl;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Comment;
import com.briup.common.mapper.basic.AddressMapper;
import com.briup.common.mapper.basic.CommentMapper;
import com.briup.demo.service.IAddressService;
import com.briup.demo.service.ICommentService;
@Service
public class CommentServiceImpl implements ICommentService{
	@Autowired
	private CommentMapper commentMapper;
	@Override
	public List<Comment> findAll() {
		// TODO Auto-generated method stub
		return commentMapper.selectAll();
	}

	@Override
	public void save(@Valid Comment comment) {
		commentMapper.insert(comment);
	}

	@Override
	public void deleteById(@NotNull Long id) {
		commentMapper.deleteByPrimaryKey(id);
	}
	//批量删除ID
	 @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	@Override
	public void batchDelete(Long[] ids) {
		for (Long id : ids) {
			commentMapper.deleteByPrimaryKey(id);
		}
	}

}
