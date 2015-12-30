package com.lakala.module.goods.vo;

import java.util.List;

import com.lakala.module.comm.ObjectOutput;



/**
 * 虚分类信息
 * @author liuguojie
 *
 */
public class CategorySub implements Comparable{
	private String categoryName;
	private int categoryId;
	private List<CategorySub> lists;
	private int sort;
	
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public List<CategorySub> getLists() {
		return lists;
	}
	public void setLists(List<CategorySub> lists) {
		this.lists = lists;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	@Override
	public String toString() {
		return "CategorySub [categoryName=" + categoryName + ", categoryId="
				+ categoryId + ", lists=" + lists + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryId;
		result = prime * result
				+ ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((lists == null) ? 0 : lists.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategorySub other = (CategorySub) obj;
		if (categoryId != other.categoryId)
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (lists == null) {
			if (other.lists != null)
				return false;
			if(other.lists == null)
				return true;
		} else if (!lists.equals(other.lists))
			return false;
		return true;
	}
	public int compareTo(Object o) {
		return ((CategorySub) o).getCategoryId() - this.getCategoryId();
	}
		
}


