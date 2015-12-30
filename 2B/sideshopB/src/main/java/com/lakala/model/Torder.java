package com.lakala.model;

import java.math.BigDecimal;
import java.util.Date;

public class Torder {
    private String torderid;

    private Integer tallorderid;

    private String tel;

    private Integer isgrouppurchase;

    private Integer ordertype;

    private Integer goodstypecount;

    private Integer source;

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

    private String paytoken;

    private String customer;

    private String fav;
    
    private Boolean exist3h;
    
	private String devprov;     //开店宝终端对应的 省份
	
	private String devprovno;   //开店宝终端对应的 省份代码
	
	private String devcity;     //开店宝终端对应的 市
	
	private String devcityno;   //开店宝终端对应的 市代码
	
	private String devcityarea; //开店宝终端对应的 区
	
	private String devcityareano; //开店宝终端对应的 区代码
	
	private String devaddr;  //开店宝终端对应的  终端地址
	
	private String devcontactname1;  //开店宝终端对应的  终端店主联系人
	
	private String devmobile;   //开店宝终端对应的  终端店主手机号
    
    private Boolean iszyckfh;
    
    private String netname;
    
    private Integer towmsfalg;  //向WMS同步订单状态  自有仓库的终端下的批发进货的商品订单，该状态为276.若同步过WMS系统，该状态为275。非自有仓库的终端下的批发进货的商品订单该标识为空
    
    private Integer servicetype;
	
	private String ecnetno; //电商网点编号
	
	private Integer orgid;
	
	private String deliverymanname;   //配送员姓名
	 
	private String deliverymanphone;  //配送员手机号

    public String getDeliverymanname() {
		return deliverymanname;
	}

	public void setDeliverymanname(String deliverymanname) {
		this.deliverymanname = deliverymanname;
	}

	public String getDeliverymanphone() {
		return deliverymanphone;
	}

	public void setDeliverymanphone(String deliverymanphone) {
		this.deliverymanphone = deliverymanphone;
	}

	public String getTorderid() {
        return torderid;
    }

    public void setTorderid(String torderid) {
        this.torderid = torderid;
    }

    public Integer getTallorderid() {
        return tallorderid;
    }

    public void setTallorderid(Integer tallorderid) {
        this.tallorderid = tallorderid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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
        this.coupons = coupons;
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
        this.rulename = rulename;
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

    public String getSiteno() {
        return siteno;
    }

    public void setSiteno(String siteno) {
        this.siteno = siteno;
    }

    public String getDeviceno() {
        return deviceno;
    }

    public void setDeviceno(String deviceno) {
        this.deviceno = deviceno;
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
        this.billtitle = billtitle;
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
        this.bankid = bankid;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAddressprovincename() {
        return addressprovincename;
    }

    public void setAddressprovincename(String addressprovincename) {
        this.addressprovincename = addressprovincename;
    }

    public String getAddressprovince() {
        return addressprovince;
    }

    public void setAddressprovince(String addressprovince) {
        this.addressprovince = addressprovince;
    }

    public String getAddresscityname() {
        return addresscityname;
    }

    public void setAddresscityname(String addresscityname) {
        this.addresscityname = addresscityname;
    }

    public String getAddresscity() {
        return addresscity;
    }

    public void setAddresscity(String addresscity) {
        this.addresscity = addresscity;
    }

    public String getAddressareaname() {
        return addressareaname;
    }

    public void setAddressareaname(String addressareaname) {
        this.addressareaname = addressareaname;
    }

    public String getAddressarea() {
        return addressarea;
    }

    public void setAddressarea(String addressarea) {
        this.addressarea = addressarea;
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
        this.remark = remark;
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
        this.membername = membername;
    }

    public String getCustcall() {
        return custcall;
    }

    public void setCustcall(String custcall) {
        this.custcall = custcall;
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
        this.phonemembername = phonemembername;
    }

    public String getPhonememberidcard() {
        return phonememberidcard;
    }

    public void setPhonememberidcard(String phonememberidcard) {
        this.phonememberidcard = phonememberidcard;
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
        this.termfbtype = termfbtype;
    }

    public String getTermfbtypestr() {
        return termfbtypestr;
    }

    public void setTermfbtypestr(String termfbtypestr) {
        this.termfbtypestr = termfbtypestr;
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

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

	public Boolean getExist3h() {
		return exist3h;
	}

	public void setExist3h(Boolean exist3h) {
		this.exist3h = exist3h;
	}

	public String getDevprov() {
		return devprov;
	}

	public void setDevprov(String devprov) {
		this.devprov = devprov;
	}

	public String getDevprovno() {
		return devprovno;
	}

	public void setDevprovno(String devprovno) {
		this.devprovno = devprovno;
	}

	public String getDevcity() {
		return devcity;
	}

	public void setDevcity(String devcity) {
		this.devcity = devcity;
	}

	public String getDevcityno() {
		return devcityno;
	}

	public void setDevcityno(String devcityno) {
		this.devcityno = devcityno;
	}

	public String getDevcityarea() {
		return devcityarea;
	}

	public void setDevcityarea(String devcityarea) {
		this.devcityarea = devcityarea;
	}

	public String getDevcityareano() {
		return devcityareano;
	}

	public void setDevcityareano(String devcityareano) {
		this.devcityareano = devcityareano;
	}

	public String getDevaddr() {
		return devaddr;
	}

	public void setDevaddr(String devaddr) {
		this.devaddr = devaddr;
	}

	public String getDevcontactname1() {
		return devcontactname1;
	}

	public void setDevcontactname1(String devcontactname1) {
		this.devcontactname1 = devcontactname1;
	}

	public String getDevmobile() {
		return devmobile;
	}

	public void setDevmobile(String devmobile) {
		this.devmobile = devmobile;
	}

	public Boolean getIszyckfh() {
		return iszyckfh;
	}

	public void setIszyckfh(Boolean iszyckfh) {
		this.iszyckfh = iszyckfh;
	}

	public String getNetname() {
		return netname;
	}

	public void setNetname(String netname) {
		this.netname = netname;
	}

	public Integer getTowmsfalg() {
		return towmsfalg;
	}

	public void setTowmsfalg(Integer towmsfalg) {
		this.towmsfalg = towmsfalg;
	}

	public Integer getServicetype() {
		return servicetype;
	}

	public void setServicetype(Integer servicetype) {
		this.servicetype = servicetype;
	}

	public String getEcnetno() {
		return ecnetno;
	}

	public void setEcnetno(String ecnetno) {
		this.ecnetno = ecnetno;
	}

	public Integer getOrgid() {
		return orgid;
	}

	public void setOrgid(Integer orgid) {
		this.orgid = orgid;
	}
	
}