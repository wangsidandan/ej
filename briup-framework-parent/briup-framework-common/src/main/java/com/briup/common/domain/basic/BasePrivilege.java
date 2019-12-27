package com.briup.common.domain.basic;

import java.io.Serializable;

public class BasePrivilege implements Serializable{
   
	private static final long serialVersionUID = 1L;

	private Long id;

    private String name;

    private String description;

    private String route;

    private String type;

    private String icon;

    private Long parentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

	@Override
	public String toString() {
		return "BasePrivilege [id=" + id + ", name=" + name + ", description=" + description + ", route=" + route
				+ ", type=" + type + ", icon=" + icon + ", parentId=" + parentId + "]";
	}
    
}