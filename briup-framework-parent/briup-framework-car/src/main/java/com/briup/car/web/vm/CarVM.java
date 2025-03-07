package com.briup.car.web.vm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("车工程信息模型")
public class CarVM {
	
	@ApiModelProperty(value = "编号")
	private String id;

	@ApiModelProperty(value = "车名")
    private String name;

	@ApiModelProperty(value = "价钱")
    private Integer price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
