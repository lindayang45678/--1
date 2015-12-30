package com.lakala.module.order.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lakala.module.comm.ObjectInput;

public class AddOrderInput extends ObjectInput {
	
	private String orderid;
	
	private String source;
	
	private String coupons;
	
	private String custelno;
	
	private String cusname;
	
	private String phonename;
	
	private String phoneidcard;
	
	private String deviceno;
	
	private String userid;
	
	private BigDecimal logisfee;
	
	private String billtype;
	
	private String billtitle;
	
	private String ispay;
	
	private String paychanel;
	
	private String paytype;
	
	private String paystage;
	
	private String bankid;
	
	private String provinceid;
	
	private String cityid;
	
	private String areaid;
	
	private String addressfull;
	
	private String code;
	
	private String weekdevice;
	
	private String devicetype;
	
	private String requiretime;
	
	private BigDecimal returnmoney;
	
	private String remark;
	
	private BigDecimal goodsTotal; //商品原价总和(商品享受促销之前) 
	
	private BigDecimal needTotal; //商品促销后的总和 = 订单促销之前
	
	private BigDecimal actualTotal; //订单促销后的金额
	
	private BigDecimal couponPrice; //优惠券金额（没有使用优惠券则为空）
	
	private BigDecimal favAmount;//总的优惠金额
	
	private BigDecimal total; //最终交易金额（所有优惠促销之后）
	
	private String json; //促销规则
	
	private String activityId; // 活动ID
	
	private String activityName; //活动名
	
	private String activityType; //活动类型 (0:无活动 1:团购 2:抢购3:买乐送)
	
	private List<Map> items;
	
	private String orderTime;
	
	private String payTime;
	
	private String isUseCoupon;
	
	private String paytoken;//支付token
	
	private String customer;//顾客姓名(WAP商城，快递到店时，保存顾客姓名)
	
	private String membername;//会员用户名
	
	private List<Map> coulist;
	
	private String password;

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername;
	}

	public String getPaytoken() {
		return paytoken;
	}

	public void setPaytoken(String paytoken) {
		this.paytoken = paytoken;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getIsUseCoupon() {
		return isUseCoupon;
	}

	public void setIsUseCoupon(String isUseCoupon) {
		this.isUseCoupon = isUseCoupon;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getReturnmoney() {
		return returnmoney;
	}

	public void setReturnmoney(BigDecimal returnmoney) {
		this.returnmoney = returnmoney;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCoupons() {
		return coupons;
	}

	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}

	public String getCustelno() {
		return custelno;
	}

	public void setCustelno(String custelno) {
		this.custelno = custelno;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
	}

	public String getPhonename() {
		return phonename;
	}

	public void setPhonename(String phonename) {
		this.phonename = phonename;
	}

	public String getPhoneidcard() {
		return phoneidcard;
	}

	public void setPhoneidcard(String phoneidcard) {
		this.phoneidcard = phoneidcard;
	}

	public String getDeviceno() {
		return deviceno;
	}

	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getBilltitle() {
		return billtitle;
	}

	public void setBilltitle(String billtitle) {
		this.billtitle = billtitle;
	}

	public String getIspay() {
		return ispay;
	}

	public void setIspay(String ispay) {
		this.ispay = ispay;
	}

	public String getPaychanel() {
		return paychanel;
	}

	public void setPaychanel(String paychanel) {
		this.paychanel = paychanel;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public String getPaystage() {
		return paystage;
	}

	public void setPaystage(String paystage) {
		this.paystage = paystage;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	public String getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getAreaid() {
		return areaid;
	}

	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}

	public String getAddressfull() {
		return addressfull;
	}

	public void setAddressfull(String addressfull) {
		this.addressfull = addressfull;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWeekdevice() {
		return weekdevice;
	}

	public void setWeekdevice(String weekdevice) {
		this.weekdevice = weekdevice;
	}

	public String getDevicetype() {
		return devicetype;
	}

	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
	}

	public String getRequiretime() {
		return requiretime;
	}

	public void setRequiretime(String requiretime) {
		this.requiretime = requiretime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public BigDecimal getLogisfee() {
		return logisfee;
	}

	public void setLogisfee(BigDecimal logisfee) {
		this.logisfee = logisfee;
	}

	public BigDecimal getGoodsTotal() {
		return goodsTotal;
	}

	public void setGoodsTotal(BigDecimal goodsTotal) {
		this.goodsTotal = goodsTotal;
	}

	public BigDecimal getNeedTotal() {
		return needTotal;
	}

	public void setNeedTotal(BigDecimal needTotal) {
		this.needTotal = needTotal;
	}

	public BigDecimal getActualTotal() {
		return actualTotal;
	}

	public void setActualTotal(BigDecimal actualTotal) {
		this.actualTotal = actualTotal;
	}

	public BigDecimal getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(BigDecimal couponPrice) {
		this.couponPrice = couponPrice;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	

	public List<Map> getItems() {
		return items;
	}

	public void setItems(List<Map> items) {
		this.items = items;
	}

	public List<Map> getCoulist() {
		return coulist;
	}

	public void setCoulist(List<Map> coulist) {
		this.coulist = coulist;
	}

	public BigDecimal getFavAmount() {
		return favAmount;
	}

	public void setFavAmount(BigDecimal favAmount) {
		this.favAmount = favAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

}
