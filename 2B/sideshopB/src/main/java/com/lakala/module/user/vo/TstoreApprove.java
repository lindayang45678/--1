package com.lakala.module.user.vo;

/**
 * 小店审批model
 * @author yangjunguo
 *
 */
public class TstoreApprove {

	private Integer id;//主键
	private String netno; // 网点编号
	private String phoneno;// 手机号
	private String electronno;//电商网点编号
	private String shopkeeper;//店主姓名
	private String remark; //描述
	private Integer state;//数据状态（1可用 0不可用）
	private String applydate; // 申请时间
	private Integer approvestate; //6、审批状态（0待审批，1、审批通过，2、审批不通过）
	private String approvedate; //审批时间
	private String approver; //审批人
	private Integer orgid; //组织id
	private String psam;
	
	//店主身份证
	private String id_no;
	//店主身份证正面
	private String idimage_positive;
	//店主身份证反面
	private String idimage_negative;
	//店主身份证正面文件名
	private String positive_file;
	//店主身份证反面文件名
	private String negative_file;
	//店铺名称
	private String shopname;
	//店铺地区
	private String district;
	//店铺社区
	private String community;
	//店铺地址
	private String address;
	//店铺经度
	private String longitude;
	//店铺维度
	private String latitude;
	//营业执照
	private String businessLicence;
	//营业执照名称
	private String licence_file;
	//小店类型
	private String shopType;
	//小店升级审批结果描述
	private String approveRemark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNetno() {
		return netno;
	}
	public void setNetno(String netno) {
		this.netno = netno;
	}
	
	public String getElectronno() {
		return electronno;
	}
	public void setElectronno(String electronno) {
		this.electronno = electronno;
	}
	public String getShopkeeper() {
		return shopkeeper;
	}
	public void setShopkeeper(String shopkeeper) {
		this.shopkeeper = shopkeeper;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getApplydate() {
		return applydate;
	}
	public void setApplydate(String applydate) {
		this.applydate = applydate;
	}
	public Integer getApprovestate() {
		return approvestate;
	}
	public void setApprovestate(Integer approvestate) {
		this.approvestate = approvestate;
	}
	public String getApprovedate() {
		return approvedate;
	}
	public void setApprovedate(String approvedate) {
		this.approvedate = approvedate;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public Integer getOrgid() {
		return orgid;
	}
	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}
	public String getPsam() {
		return psam;
	}
	public void setPsam(String psam) {
		this.psam = psam;
	}
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
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
	public String getPositive_file() {
		return positive_file;
	}
	public void setPositive_file(String positive_file) {
		this.positive_file = positive_file;
	}
	public String getNegative_file() {
		return negative_file;
	}
	public void setNegative_file(String negative_file) {
		this.negative_file = negative_file;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
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
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getBusinessLicence() {
		return businessLicence;
	}
	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	public String getLicence_file() {
		return licence_file;
	}
	public void setLicence_file(String licence_file) {
		this.licence_file = licence_file;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getApproveRemark() {
		return approveRemark;
	}
	public void setApproveRemark(String approveRemark) {
		this.approveRemark = approveRemark;
	}
	
	
	
}
