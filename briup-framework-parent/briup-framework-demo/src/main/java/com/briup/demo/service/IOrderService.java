package com.briup.demo.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.basic.Order;
import com.briup.common.domain.basic.Orderline;
import com.briup.common.domain.extend.OrderExtend;
import com.briup.common.domain.extend.OrderlineExtend;
import com.briup.common.domain.vm.OrderAndOrderLineVM;

public interface IOrderService {
	List<Order> findAll();

	void saveOrUpdate(@Valid OrderAndOrderLineVM orderAndOrderLineVM);

	void deleteById(@NotNull Long id);

	void batchDelete(Long[] ids);
	
	OrderExtend cascadeByCustomerId(Long id);
	
	List<OrderlineExtend>findOrdelineByOrderId(Long orderId);

	void takeOrder(Long id,Long waiterId);

	void finishOrder(Long id,Long waiterId);

	OrderExtend cascadeByCId(@NotNull Long id);
}
