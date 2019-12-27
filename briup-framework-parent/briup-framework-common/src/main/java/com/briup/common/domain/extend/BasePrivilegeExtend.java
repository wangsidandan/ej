package com.briup.common.domain.extend;

import java.util.List;

import com.briup.common.domain.basic.BasePrivilege;

public class BasePrivilegeExtend extends BasePrivilege{
    private List<BasePrivilege> children;

    public List<BasePrivilege> getChildren() {
        return children;
    }

    public void setChildren(List<BasePrivilege> children) {
        this.children = children;
    }
}
