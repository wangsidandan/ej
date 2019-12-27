package com.briup.common.mapper.extend;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.briup.common.domain.extend.OrderExtend;

public interface OrderExtendMapper {
	OrderExtend cascadeByCustomerId(Long id);

	void takeOrder(Map<String,Object> map);

	void finishOrder(Map<String, Object> map);

	OrderExtend cascadeByCId(@NotNull Long id);
}
