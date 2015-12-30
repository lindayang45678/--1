package com.lakala.module.update.vo;

import com.lakala.module.comm.ObjectInput;


public class VersionInput extends ObjectInput {
	
	private Integer id;
	private String preVersion;
	private Integer platformType;
	private Integer appType;
	private String tarVersion;
	private String url;
	private Integer isMandatory;
	private String title;
	private String description;
	private String header;
	private Integer parentId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getIsMandatory() {
		return isMandatory;
	}
	public void setIsMandatory(Integer isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getPlatformType() {
		return platformType;
	}
	public void setPlatformType(Integer platformType) {
		this.platformType = platformType;
	}
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
	
	public String getPreVersion() {
		return preVersion;
	}
	public void setPreVersion(String preVersion) {
		this.preVersion = preVersion;
	}
	
	public String getTarVersion() {
		return tarVersion;
	}
	public void setTarVersion(String tarVersion) {
		this.tarVersion = tarVersion;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
