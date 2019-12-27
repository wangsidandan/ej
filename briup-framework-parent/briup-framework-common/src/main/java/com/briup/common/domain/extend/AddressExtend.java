package com.briup.common.domain.extend;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Customer;

public class AddressExtend extends Address{
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
