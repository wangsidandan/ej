package com.briup.common.mapper.extend;

import java.util.List;

import com.briup.common.domain.basic.Orderline;
import com.briup.common.domain.extend.OrderExtend;
import com.briup.common.domain.extend.OrderlineExtend;

public interface OrderlineExtendMapper {
	List<OrderlineExtend> findOrderlineByOrderId(Long id);
}
