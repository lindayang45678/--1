package com.lakala.model.news;

public class TNewsLog {
	
	private Integer id;
	
	private Integer newsId;
	
	private String tel;
	
	private String terminalCode;//终端编码
	
	private Integer terminalType;//0:其他 1：安卓 2： 苹果
	
	private String SubChannelId;//APP标示
	
	private Integer source;
	
	private String title;
	
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public String getSubChannelId() {
		return SubChannelId;
	}

	public void setSubChannelId(String subChannelId) {
		SubChannelId = subChannelId;
	}
}
