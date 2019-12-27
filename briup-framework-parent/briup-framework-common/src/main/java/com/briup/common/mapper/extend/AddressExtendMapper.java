package com.briup.common.mapper.extend;

import com.briup.common.domain.basic.Address;
import com.briup.common.domain.extend.AddressExtend;

public interface AddressExtendMapper {
	AddressExtend findAllAddressWithCustomer();
	Address findByCustomerId(Long id);
}
