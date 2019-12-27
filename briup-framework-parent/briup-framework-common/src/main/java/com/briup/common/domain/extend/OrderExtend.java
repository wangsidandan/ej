package com.briup.common.domain.extend;

import java.util.List;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Customer;
import com.briup.common.domain.basic.Order;
import com.briup.common.domain.basic.Orderline;
import com.briup.common.domain.basic.Waiter;

public class OrderExtend extends Order{
	private Customer customer;
	private Waiter waiter;
	private Address address;
	private List<Orderline>orderlines;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Waiter getWaiter() {
		return waiter;
	}
	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Orderline> getOrderlines() {
		return orderlines;
	}
	public void setOrderlines(List<Orderline> orderlines) {
		this.orderlines = orderlines;
	}
	
}
