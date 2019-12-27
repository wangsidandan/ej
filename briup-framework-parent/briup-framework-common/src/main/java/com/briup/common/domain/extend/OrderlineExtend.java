package com.briup.common.domain.extend;

import com.briup.common.domain.basic.Orderline;
import com.briup.common.domain.basic.Product;

public class OrderlineExtend extends Orderline{
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}
