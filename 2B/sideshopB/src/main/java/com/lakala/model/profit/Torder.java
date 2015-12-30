package com.lakala.model.profit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Torder implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6383441177299901232L;

	private String torderid;

	private String tel;

	private Integer isgrouppurchase;

	private Integer ordertype;

	private Integer goodstypecount;

	private Integer source;

	private String sourceStr;

	private BigDecimal amount;

	private BigDecimal payableamount;

	private BigDecimal actualamount;

	private String coupons;

	private BigDecimal couponsvalue;

	private Integer ruleid;

	private String rulename;

	private String custelno;

	private String cusname;

	private String siteno;

	private String deviceno;

	private BigDecimal favorrulemoney;

	private BigDecimal logiscalfee;

	private BigDecimal storeprofits;

	private Integer billtype;

	private String billtitle;

	private Integer ispay;

	private Date paytime;

	private Integer paychanel;

	private Integer paytype;

	private Integer paystage;

	private BigDecimal payfee;

	private BigDecimal payhandlingcharge;

	private String bankid;

	private String bankname;

	private String addressprovincename;

	private String addressprovince;

	private String addresscityname;

	private String addresscity;

	private String addressareaname;

	private String addressarea;

	private String addressfull;

	private String code;

	private Date ordertime;

	private Integer isweekenddeliver;

	private Integer isdelivertohome;

	private String isdelivertohomeStr;

	private Date requiretime;

	private Integer state;

	private String remark;

	private BigDecimal favorallmoney;

	private Integer cancelstate;

	private Date lastmodifytime;

	private String membername;

	private String custcall;

	private Integer lockstate;

	private Integer providersettlestate;

	private BigDecimal providersettlemoney;

	private Integer shipsettlestate;

	private BigDecimal shipsettlemoney;

	private String phonemembername;

	private String phonememberidcard;

	private Integer ischeckaccount;

	private String termfbtype;

	private String termfbtypestr;

	private String skbsalemobile;

	private BigDecimal frRatio;

	private BigDecimal reRatio;

	private BigDecimal sbRatio;

	private BigDecimal divideprofit;

	private BigDecimal returnamount;

	private BigDecimal subsidyamount;

	private Integer salersettlestate;

	private String fav;

	private String skbsalemobilename;

	private String orderearningid;

	private Integer statisticsFlg;

	private String settlementid;

	private String providerId;

	private String providerName;
	/** 支付费率 */
	private BigDecimal payRate;
	/** 商品自营平台标识 */
	private String platorSelf;

	private String returnFlg;
	/** 支付方式 */
	private String payChanelStr;

	public String getReturnFlg() {
		return returnFlg;
	}

	public void setReturnFlg(String returnFlg) {
		this.returnFlg = returnFlg;
	}

	public String getTorderid() {
		return torderid;
	}

	public String getPlatorSelf() {
		return platorSelf;
	}

	public void setPlatorSelf(String platorSelf) {
		this.platorSelf = platorSelf;
	}

	public String getSourceStr() {
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}

	public void setTorderid(String torderid) {
		this.torderid = torderid == null ? null : torderid.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public Integer getIsgrouppurchase() {
		return isgrouppurchase;
	}

	public void setIsgrouppurchase(Integer isgrouppurchase) {
		this.isgrouppurchase = isgrouppurchase;
	}

	public Integer getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	public Integer getGoodstypecount() {
		return goodstypecount;
	}

	public void setGoodstypecount(Integer goodstypecount) {
		this.goodstypecount = goodstypecount;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPayableamount() {
		return payableamount;
	}

	public void setPayableamount(BigDecimal payableamount) {
		this.payableamount = payableamount;
	}

	public BigDecimal getActualamount() {
		return actualamount;
	}

	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}

	public String getCoupons() {
		return coupons;
	}

	public void setCoupons(String coupons) {
		this.coupons = coupons == null ? null : coupons.trim();
	}

	public BigDecimal getCouponsvalue() {
		return couponsvalue;
	}

	public void setCouponsvalue(BigDecimal couponsvalue) {
		this.couponsvalue = couponsvalue;
	}

	public Integer getRuleid() {
		return ruleid;
	}

	public void setRuleid(Integer ruleid) {
		this.ruleid = ruleid;
	}

	public String getRulename() {
		return rulename;
	}

	public void setRulename(String rulename) {
		this.rulename = rulename == null ? null : rulename.trim();
	}

	public String getCustelno() {
		return custelno;
	}

	public void setCustelno(String custelno) {
		this.custelno = custelno == null ? null : custelno.trim();
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname == null ? null : cusname.trim();
	}

	public String getSiteno() {
		return siteno;
	}

	public void setSiteno(String siteno) {
		this.siteno = siteno == null ? null : siteno.trim();
	}

	public String getDeviceno() {
		return deviceno;
	}

	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno == null ? null : deviceno.trim();
	}

	public BigDecimal getFavorrulemoney() {
		return favorrulemoney;
	}

	public void setFavorrulemoney(BigDecimal favorrulemoney) {
		this.favorrulemoney = favorrulemoney;
	}

	public BigDecimal getLogiscalfee() {
		return logiscalfee;
	}

	public void setLogiscalfee(BigDecimal logiscalfee) {
		this.logiscalfee = logiscalfee;
	}

	public BigDecimal getStoreprofits() {
		return storeprofits;
	}

	public void setStoreprofits(BigDecimal storeprofits) {
		this.storeprofits = storeprofits;
	}

	public Integer getBilltype() {
		return billtype;
	}

	public void setBilltype(Integer billtype) {
		this.billtype = billtype;
	}

	public String getBilltitle() {
		return billtitle;
	}

	public void setBilltitle(String billtitle) {
		this.billtitle = billtitle == null ? null : billtitle.trim();
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public Integer getPaychanel() {
		return paychanel;
	}

	public void setPaychanel(Integer paychanel) {
		this.paychanel = paychanel;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public Integer getPaystage() {
		return paystage;
	}

	public void setPaystage(Integer paystage) {
		this.paystage = paystage;
	}

	public BigDecimal getPayfee() {
		return payfee;
	}

	public void setPayfee(BigDecimal payfee) {
		this.payfee = payfee;
	}

	public BigDecimal getPayhandlingcharge() {
		return payhandlingcharge;
	}

	public void setPayhandlingcharge(BigDecimal payhandlingcharge) {
		this.payhandlingcharge = payhandlingcharge;
	}

	public String getBankid() {
		return bankid;
	}

	public void setBankid(String bankid) {
		this.bankid = bankid == null ? null : bankid.trim();
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname == null ? null : bankname.trim();
	}

	public String getAddressprovincename() {
		return addressprovincename;
	}

	public void setAddressprovincename(String addressprovincename) {
		this.addressprovincename = addressprovincename == null ? null : addressprovincename.trim();
	}

	public String getAddressprovince() {
		return addressprovince;
	}

	public void setAddressprovince(String addressprovince) {
		this.addressprovince = addressprovince == null ? null : addressprovince.trim();
	}

	public String getAddresscityname() {
		return addresscityname;
	}

	public void setAddresscityname(String addresscityname) {
		this.addresscityname = addresscityname == null ? null : addresscityname.trim();
	}

	public String getAddresscity() {
		return addresscity;
	}

	public void setAddresscity(String addresscity) {
		this.addresscity = addresscity == null ? null : addresscity.trim();
	}

	public String getAddressareaname() {
		return addressareaname;
	}

	public void setAddressareaname(String addressareaname) {
		this.addressareaname = addressareaname == null ? null : addressareaname.trim();
	}

	public String getAddressarea() {
		return addressarea;
	}

	public void setAddressarea(String addressarea) {
		this.addressarea = addressarea == null ? null : addressarea.trim();
	}

	public String getAddressfull() {
		return addressfull;
	}

	public void setAddressfull(String addressfull) {
		this.addressfull = addressfull == null ? null : addressfull.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Integer getIsweekenddeliver() {
		return isweekenddeliver;
	}

	public void setIsweekenddeliver(Integer isweekenddeliver) {
		this.isweekenddeliver = isweekenddeliver;
	}

	public Integer getIsdelivertohome() {
		return isdelivertohome;
	}

	public void setIsdelivertohome(Integer isdelivertohome) {
		this.isdelivertohome = isdelivertohome;
	}

	public Date getRequiretime() {
		return requiretime;
	}

	public void setRequiretime(Date requiretime) {
		this.requiretime = requiretime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public BigDecimal getFavorallmoney() {
		return favorallmoney;
	}

	public void setFavorallmoney(BigDecimal favorallmoney) {
		this.favorallmoney = favorallmoney;
	}

	public Integer getCancelstate() {
		return cancelstate;
	}

	public void setCancelstate(Integer cancelstate) {
		this.cancelstate = cancelstate;
	}

	public Date getLastmodifytime() {
		return lastmodifytime;
	}

	public void setLastmodifytime(Date lastmodifytime) {
		this.lastmodifytime = lastmodifytime;
	}

	public String getMembername() {
		return membername;
	}

	public void setMembername(String membername) {
		this.membername = membername == null ? null : membername.trim();
	}

	public String getCustcall() {
		return custcall;
	}

	public void setCustcall(String custcall) {
		this.custcall = custcall == null ? null : custcall.trim();
	}

	public Integer getLockstate() {
		return lockstate;
	}

	public void setLockstate(Integer lockstate) {
		this.lockstate = lockstate;
	}

	public Integer getProvidersettlestate() {
		return providersettlestate;
	}

	public void setProvidersettlestate(Integer providersettlestate) {
		this.providersettlestate = providersettlestate;
	}

	public BigDecimal getProvidersettlemoney() {
		return providersettlemoney;
	}

	public void setProvidersettlemoney(BigDecimal providersettlemoney) {
		this.providersettlemoney = providersettlemoney;
	}

	public Integer getShipsettlestate() {
		return shipsettlestate;
	}

	public void setShipsettlestate(Integer shipsettlestate) {
		this.shipsettlestate = shipsettlestate;
	}

	public BigDecimal getShipsettlemoney() {
		return shipsettlemoney;
	}

	public void setShipsettlemoney(BigDecimal shipsettlemoney) {
		this.shipsettlemoney = shipsettlemoney;
	}

	public String getPhonemembername() {
		return phonemembername;
	}

	public void setPhonemembername(String phonemembername) {
		this.phonemembername = phonemembername == null ? null : phonemembername.trim();
	}

	public String getPhonememberidcard() {
		return phonememberidcard;
	}

	public void setPhonememberidcard(String phonememberidcard) {
		this.phonememberidcard = phonememberidcard == null ? null : phonememberidcard.trim();
	}

	public Integer getIscheckaccount() {
		return ischeckaccount;
	}

	public void setIscheckaccount(Integer ischeckaccount) {
		this.ischeckaccount = ischeckaccount;
	}

	public String getTermfbtype() {
		return termfbtype;
	}

	public void setTermfbtype(String termfbtype) {
		this.termfbtype = termfbtype == null ? null : termfbtype.trim();
	}

	public String getTermfbtypestr() {
		return termfbtypestr;
	}

	public void setTermfbtypestr(String termfbtypestr) {
		this.termfbtypestr = termfbtypestr == null ? null : termfbtypestr.trim();
	}

	public String getSkbsalemobile() {
		return skbsalemobile;
	}

	public void setSkbsalemobile(String skbsalemobile) {
		this.skbsalemobile = skbsalemobile;
	}

	public BigDecimal getFrRatio() {
		return frRatio;
	}

	public void setFrRatio(BigDecimal frRatio) {
		this.frRatio = frRatio;
	}

	public BigDecimal getReRatio() {
		return reRatio;
	}

	public void setReRatio(BigDecimal reRatio) {
		this.reRatio = reRatio;
	}

	public BigDecimal getSbRatio() {
		return sbRatio;
	}

	public void setSbRatio(BigDecimal sbRatio) {
		this.sbRatio = sbRatio;
	}

	public BigDecimal getDivideprofit() {
		return divideprofit;
	}

	public void setDivideprofit(BigDecimal divideprofit) {
		this.divideprofit = divideprofit;
	}

	public BigDecimal getReturnamount() {
		return returnamount;
	}

	public void setReturnamount(BigDecimal returnamount) {
		this.returnamount = returnamount;
	}

	public BigDecimal getSubsidyamount() {
		return subsidyamount;
	}

	public void setSubsidyamount(BigDecimal subsidyamount) {
		this.subsidyamount = subsidyamount;
	}

	public Integer getSalersettlestate() {
		return salersettlestate;
	}

	public void setSalersettlestate(Integer salersettlestate) {
		this.salersettlestate = salersettlestate;
	}

	public String getFav() {
		return fav;
	}

	public void setFav(String fav) {
		this.fav = fav == null ? null : fav.trim();
	}

	public String getSkbsalemobilename() {
		return skbsalemobilename;
	}

	public void setSkbsalemobilename(String skbsalemobilename) {
		this.skbsalemobilename = skbsalemobilename;
	}

	public String getOrderearningid() {
		return orderearningid;
	}

	public void setOrderearningid(String orderearningid) {
		this.orderearningid = orderearningid;
	}

	public Integer getStatisticsFlg() {
		return statisticsFlg;
	}

	public void setStatisticsFlg(Integer statisticsFlg) {
		this.statisticsFlg = statisticsFlg;
	}

	public String getSettlementid() {
		return settlementid;
	}

	public void setSettlementid(String settlementid) {
		this.settlementid = settlementid;
	}

	public String getIsdelivertohomeStr() {
		return isdelivertohomeStr;
	}

	public void setIsdelivertohomeStr(String isdelivertohomeStr) {
		this.isdelivertohomeStr = isdelivertohomeStr;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPayChanelStr() {
		return payChanelStr;
	}

	public void setPayChanelStr(String payChanelStr) {
		this.payChanelStr = payChanelStr;
	}

}