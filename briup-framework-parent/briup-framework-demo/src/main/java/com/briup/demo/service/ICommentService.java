package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Comment;

public interface ICommentService {
	List<Comment> findAll();

	void save(@Valid Comment comment);

	void deleteById(@NotNull Long id);

	void batchDelete(Long[] ids);
}
