package com.briup.common.mapper.extend;

import java.util.List;

import com.briup.common.domain.basic.BasePrivilege;
import com.briup.common.domain.basic.BaseRole;
import com.briup.common.domain.basic.Category;
import com.briup.common.domain.extend.CategoryExtend;

public interface CategroyExtendMapper {
	List<CategoryExtend> cascadeFindById(Long id);
	List<Category>findCategoryByParentId(Long parentId);
	List<CategoryExtend> findCategory();

	 
}
