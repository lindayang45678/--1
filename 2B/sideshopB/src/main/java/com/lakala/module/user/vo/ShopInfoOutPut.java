package com.lakala.module.user.vo;

import java.math.BigDecimal;
import java.util.List;

import com.lakala.model.user.TShopService;


public class ShopInfoOutPut {

	/** 验证令牌 */
	protected String token;
	
	private Long id;

    private String province;
    
    private String provinceCode;

	private String city;
	
	private String cityCode;

    private String cityarea;
    
    private String cityareaCode;

    private String shopname;

    private String shoppername;

    private String phone;
    
    private String weixin_no;
    
    private String announcement;
    
    private Integer homeDeliver;//是否支持送货上门(0：否； 1：是)
    
	private Integer is_pickup; //到店自提(0：否； 1：是)
    
    private float distince;//距离

	private String address;
	
	private String longitude; //经度
	
	private String latitude;//纬度
	
	private String imagePath;
	
	private String psam;
	
	private String netNo;
	//是否支持货到付款和在线支付；0：表示只支持在线支付，1：表示在线支付和货到付款都支持
	private Integer is_homepay;
	
	private Integer is_onehour;//是否支持1小时送货(0：否； 1：是)
	
	//营业状态
	private int businessState;
	//营业开始时间
	private String businessStartTime;
	//营业结束时间
	private String businessEndTime;
	//起送金额（元）
	private BigDecimal minAmount;
	//运费（元）
	private BigDecimal transportExpense;
	//单笔满额免运费（元）
	private BigDecimal singleFreeExpense;
	
	//商户类型
	private int bizType;
	//小店类型
	private int shopType;
    //身份证号
    private String identityNo;
    //身份证正面
    private String identityFront; 
    //身份证反面
    private String identityReverse; 
    //营业执照
    private String businessLicence;
	

	public Integer getIs_homepay() {
		return is_homepay;
	}

	public void setIs_homepay(Integer is_homepay) {
		this.is_homepay = is_homepay;
	}

	public Integer getIs_onehour() {
		return is_onehour;
	}

	public void setIs_onehour(Integer is_onehour) {
		this.is_onehour = is_onehour;
	}

	public Integer getIs_pickup() {
		return is_pickup;
	}

	public void setIs_pickup(Integer is_pickup) {
		this.is_pickup = is_pickup;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityareaCode() {
		return cityareaCode;
	}

	public void setCityareaCode(String cityareaCode) {
		this.cityareaCode = cityareaCode;
	}

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}

	public String getNetNo() {
		return netNo;
	}

	public void setNetNo(String netNo) {
		this.netNo = netNo;
	}

	private List<TShopService> dispatcherlist;

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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

	public String getWeixin_no() {
		return weixin_no;
	}

	public void setWeixin_no(String weixin_no) {
		this.weixin_no = weixin_no;
	}

	public String getAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public List<TShopService> getDispatcherlist() {
		return dispatcherlist;
	}

	public void setDispatcherlist(List<TShopService> dispatcherlist) {
		this.dispatcherlist = dispatcherlist;
	}

	public Integer getHomeDeliver() {
		return homeDeliver;
	}

	public void setHomeDeliver(Integer homeDeliver) {
		this.homeDeliver = homeDeliver;
	}

	public float getDistince() {
		return distince;
	}

	public void setDistince(float distince) {
		this.distince = distince;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityarea() {
		return cityarea;
	}

	public void setCityarea(String cityarea) {
		this.cityarea = cityarea;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShoppername() {
		return shoppername;
	}

	public void setShoppername(String shoppername) {
		this.shoppername = shoppername;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getBusinessState() {
		return businessState;
	}

	public void setBusinessState(int businessState) {
		this.businessState = businessState;
	}

	public String getBusinessStartTime() {
		return businessStartTime;
	}

	public void setBusinessStartTime(String businessStartTime) {
		this.businessStartTime = businessStartTime;
	}

	public String getBusinessEndTime() {
		return businessEndTime;
	}

	public void setBusinessEndTime(String businessEndTime) {
		this.businessEndTime = businessEndTime;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getTransportExpense() {
		return transportExpense;
	}

	public void setTransportExpense(BigDecimal transportExpense) {
		this.transportExpense = transportExpense;
	}

	public BigDecimal getSingleFreeExpense() {
		return singleFreeExpense;
	}

	public void setSingleFreeExpense(BigDecimal singleFreeExpense) {
		this.singleFreeExpense = singleFreeExpense;
	}

	public int getBizType() {
		return bizType;
	}

	public void setBizType(int bizType) {
		this.bizType = bizType;
	}

	public int getShopType() {
		return shopType;
	}

	public void setShopType(int shopType) {
		this.shopType = shopType;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}

	public String getIdentityFront() {
		return identityFront;
	}

	public void setIdentityFront(String identityFront) {
		this.identityFront = identityFront;
	}

	public String getIdentityReverse() {
		return identityReverse;
	}

	public void setIdentityReverse(String identityReverse) {
		this.identityReverse = identityReverse;
	}

	public String getBusinessLicence() {
		return businessLicence;
	}

	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	
	
	
}
