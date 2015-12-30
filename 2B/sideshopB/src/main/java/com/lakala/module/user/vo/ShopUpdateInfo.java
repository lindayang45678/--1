package com.lakala.module.user.vo;


/**
 * 店铺升级输入信息
 * @author lakala
 *
 */
public class ShopUpdateInfo {

	//店铺所属区域ID
	private String orgid;
	//店主名称
	private String ownername;
	//店主手机
	private String ownermobile;
	//店主身份证
	private String id;
	//店主身份证正面
	private String idimage_positive;
	//店主身份证反面
	private String idimage_negative;
	//店铺名称
	private String name;
	//店铺地区
	private String district;
	//店铺社区
	private String community;
	//店铺地址
	private String address;
	//店铺经度
	private long longitude;
	//店铺维度
	private long latitude;
	
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getOwnername() {
		return ownername;
	}
	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}
	public String getOwnermobile() {
		return ownermobile;
	}
	public void setOwnermobile(String ownermobile) {
		this.ownermobile = ownermobile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdimage_positive() {
		return idimage_positive;
	}
	public void setIdimage_positive(String idimage_positive) {
		this.idimage_positive = idimage_positive;
	}
	public String getIdimage_negative() {
		return idimage_negative;
	}
	public void setIdimage_negative(String idimage_negative) {
		this.idimage_negative = idimage_negative;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCommunity() {
		return community;
	}
	public void setCommunity(String community) {
		this.community = community;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	
	
	
}
