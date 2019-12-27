package com.briup.demo.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.briup.common.domain.basic.Order;
import com.briup.common.domain.basic.Orderline;
import com.briup.common.domain.extend.OrderExtend;
import com.briup.common.domain.extend.OrderlineExtend;
import com.briup.common.domain.vm.OrderAndOrderLineVM;
import com.briup.common.mapper.basic.OrderMapper;
import com.briup.common.mapper.basic.OrderlineMapper;
import com.briup.common.mapper.extend.OrderExtendMapper;
import com.briup.common.mapper.extend.OrderlineExtendMapper;
import com.briup.demo.service.IOrderService;
@Service
public class OrderServiceImpl<K> implements IOrderService{
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderlineMapper orderlineMapper;
	@Autowired
	private OrderExtendMapper orderExtendMapper;
	@Autowired
	private OrderlineExtendMapper orderlineExtendMapper;
	@Override
	public List<Order> findAll() {
		return orderMapper.selectAll();
	}
	/*
	 * OrderAndOrderLineVM:页面上需要订单和订单项的结合体。
	 * 组合封装此类
	 */
	@Override
	public void saveOrUpdate(@Valid OrderAndOrderLineVM orderAndOrderLineVM) {
		Order o=new Order();
		//订单状态
		o.setStatus("待派单");
		//分装订单时间的包装类Long
		o.setOrderTime(Long.valueOf(new Date().getTime()));
		//页面上输入的customerId
		o.setCustomerId(orderAndOrderLineVM.getCustomerId());
		o.setAddressId(orderAndOrderLineVM.getAddressId());
		List<Orderline> orderlines = orderAndOrderLineVM.getOrderlines();
		double total=0D;//订单总金额=订单项金额之和
		for (Orderline orderline : orderlines) {
			//注意包装类的运算
			total+=orderline.getPrice().doubleValue()*orderline.getNumber().intValue();
		}
		o.setTotal(Double.valueOf(total));
		orderMapper.insert(o);
		//插入订单后会返回订单编号
		for (Orderline orderline : orderlines) {
			orderline.setOrderId(o.getId());
			orderlineMapper.insert(orderline);
		}
	}

	@Override
	public void deleteById(@NotNull Long id) {
		orderMapper.deleteByPrimaryKey(id);
	}
	//批量删除ID
	 @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
	@Override
	public void batchDelete(Long[] ids) {
		for (Long id : ids) {
			orderMapper.deleteByPrimaryKey(id);
		}
	}
	@Override
	public OrderExtend cascadeByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return orderExtendMapper.cascadeByCustomerId(customerId);
	}
	@Override
	public List<OrderlineExtend> findOrdelineByOrderId(Long orderId) {
		return orderlineExtendMapper.findOrderlineByOrderId(orderId);
	}
	@Override
	public void takeOrder(Long id,Long waiterId) {
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("orderId", id);
		map.put("waiterId", waiterId);
		orderExtendMapper.takeOrder(map);
	}
	@Override
	public void finishOrder(Long id, Long waiterId) {
		Map<String,Object>map=new HashMap<String, Object>();
		map.put("orderId", id);
		map.put("waiterId", waiterId);
		orderExtendMapper.finishOrder(map);
	}
	@Override
	public OrderExtend cascadeByCId(@NotNull Long id) {
		// TODO Auto-generated method stub
		return orderExtendMapper.cascadeByCId(id);
	}

}
