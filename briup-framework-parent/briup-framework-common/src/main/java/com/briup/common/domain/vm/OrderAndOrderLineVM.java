package com.briup.common.domain.vm;

import java.util.List;

import com.briup.common.domain.basic.Orderline;

import io.swagger.annotations.ApiParam;

public class OrderAndOrderLineVM {
	@ApiParam(value = "顾客ID",required = true)
	private Long customerId;
	@ApiParam(value = "服务地址ID",required = true)
	private Long addressId;
	private List<Orderline>orderlines;
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public List<Orderline> getOrderlines() {
		return orderlines;
	}
	public void setOrderlines(List<Orderline> orderlines) {
		this.orderlines = orderlines;
	}
	
}
