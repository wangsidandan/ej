package com.briup.common.domain.vm;

public class OrderVM {
	private Long orderId;
	private String customerName;
	private String address;
	private Double total;
	private Long orderTime;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Long getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Long orderTime) {
		this.orderTime = orderTime;
	}
	
}
