package com.lakala.module.news.vo;

import com.lakala.module.comm.ObjectInput;

public class MemberInput extends ObjectInput {
	private String memberName;
	private Integer newstype;
	private Integer page;
	private Integer pageSize;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getNewstype() {
		return newstype;
	}
	public void setNewstype(Integer newstype) {
		this.newstype = newstype;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
