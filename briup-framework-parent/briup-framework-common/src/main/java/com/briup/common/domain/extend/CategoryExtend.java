package com.briup.common.domain.extend;

import java.util.List;

import com.briup.common.domain.basic.Category;

public class CategoryExtend extends Category{
	private List<Category>children;

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}
	
}
