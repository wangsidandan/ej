package com.briup.common.domain.extend;

import com.briup.common.domain.basic.Category;
import com.briup.common.domain.basic.Product;

public class ProductExtend extends Product{
	private Category category;

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
