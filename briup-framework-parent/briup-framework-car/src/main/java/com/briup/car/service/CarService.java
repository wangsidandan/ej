package com.briup.car.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.car.web.vm.CarVM;
import com.briup.common.domain.basic.Car;
import com.briup.common.mapper.basic.CarMapper;
import com.briup.common.util.EntityUtils;

@Service
public class CarService {
	@Autowired
	private CarMapper carMapper;
	public List<CarVM> findAllCars() {
		List<CarVM>vms=null;
		List<Car> list = carMapper.selectAll();
		if(list!=null) {
			vms=EntityUtils.entity2VMList(list, CarVM.class);
		}
		return vms;
	}
	

//	public CarVM findCarById(String id) {
//		CarVM vm=null;
//		carMapper.se
//		return null;
//	}

}
