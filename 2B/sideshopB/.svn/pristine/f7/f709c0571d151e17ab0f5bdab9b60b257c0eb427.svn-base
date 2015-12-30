package com.lakala.model.profit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



public class Torderprovider implements Serializable {
	/**
	 * 订单按供应商二级子表（其中自营商品的供应商id是本公司ID）
	 */
	private static final long serialVersionUID = -6687219245716432562L;
    /**合并的主键*/
	private String torderproviderid;
	/**订单id*/
	private String orderid;
	/**供应商id*/
	private Integer providerid;
	/**供应商名称*/
	private String providername;
	/**分摊的物流费用*/
	private BigDecimal logiscalfee;
	/**优惠金额*/
	private BigDecimal favorrulemoney;
	/**最终销售价（合计所有费用：优惠、物流等）*/
	private BigDecimal actualamount;
	/**纯销售价总金额（不含物流费用、优惠费）*/
	private BigDecimal orderamount;
	/**该笔订单供应商结算价*/
	private BigDecimal payoffamount;
	/**商品种类数目*/
	private Integer goodstypescount;
	/**订单是否支付  149:未支付;150:已支付*/
	private Integer ispay;
	/**收货人手机*/
	private String custelno;
	/**收货人姓名*/
	private String cusname;
	/**订单状态  (99:未发货;100:发货中;101:部分发货;102:已发货;103:部分签收;104:已签收;216:已拒收;)*/
	private Integer state;
	/**订单合单状态  146:未合单;147:合单*/
	private Integer mergestate;
	/**要求到货时间*/
	private Date requiretime;
	/**是否周末送货*/
	private Integer isweekenddeliver;
	/**订单配送方式 86:快递到店;87:快递到家*/
	private Integer isdelivertohome;
	/**支付时间*/
	private Date paytime;
	/**每期费用*/
	private BigDecimal payfee;
	/**分期期数*/
	private Integer paystage;
	/**80订单支付类型(82:全额;83:分期)*/
	private Integer paytype;
	/**236订单支付手段  (237:拉卡拉;238:银行;239:支付宝;240:货到付款;432:微信支付;241:其他)*/
	private Integer paychanel;
	/**手续费*/
	private BigDecimal payhandingcharge;
	/**订单发票类型  89:不开发票;90:个人;91:公司*/
	private Integer billtype;
	/**开票抬头*/
	private String billtitle;
	/**销售点按单返利*/
	private BigDecimal storeprofits;
	/**是否已给供应商导出*/
	private Integer toproviderstate;
	/**省*/
	private String addressprovincename;
	/**发货地址省编码*/
	private String addressprovince;
	/**市*/
	private String addresscityname;
	/**发货地址市编码*/
	private String addresscity;
	/**区*/
	private String addressareaname;
	/**发货地址区编码*/
	private String addressarea;
	/**发货地址*/
	private String addressfull;
	/**邮编*/
	private String code;
	/**订单来源  93:手机商城;94:微信商城;95:开店宝;96:PC商城;97:其他;*/
	private Integer source;
	/**活动id*/
	private Integer ruleid;
	/**活动名称*/
	private String rulename;
	/**活动时间*/
	private Date ordertime;
	/**订单备注信息*/
	private String remark;
	/**导出未发货订单状态，0-未导出，1-导出*/
	private Integer exportstatus;
	/**订单取消状态  136:未取消;137:已取消*/
	private Integer cancelstate;
	/**最后修改时间*/
	private Date lastmodifytime;
	/**会员用户名*/
	private String membername;
	/**收货人电话*/
	private String custcall;
	/**订单锁定状态  133:未锁定;134:已锁定*/
	private Integer lockstate;
	/**供应商添加的订单备注信息*/
	private String providerremark;
	/**供应商结算状态，(78:未结算;79:已结算;242:部分结算)*/
	private Integer providersettlestate;
	/**供应商结算金额*/
	private BigDecimal providersettlemoney;
	/**店主商结算状态，(78:未结算;79:已结算;242:部分结算)*/
	private Integer shipsettlestate;
	/**店主商结算金额*/
	private BigDecimal shipsettlemoney;
	/**基础类*/  
	private TgoodsproviderWithBLOBs providerinfo;
	/**三级订单*/
	private List<Torderitems> torderitemsList;
	
	private Integer ordertype;
	
	private String skbsalemobilename;
	// 新添物流主表两个字段
	// 物流单号
	private String logisticscode;

	// 物流公司id
	private Integer tlogisticcorpid;

	// 物流表主键
	private String logisticsid;

	// 终端编号,用于合单
	private String deviceno;

	// 添加备注用户的用户名
	private String username;

	public String getTorderproviderid() {
		return torderproviderid;
	}

	public void setTorderproviderid(String torderproviderid) {
		this.torderproviderid = torderproviderid == null ? null : torderproviderid.trim();
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid == null ? null : orderid.trim();
	}

	public Integer getProviderid() {
		return providerid;
	}

	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}

	public String getProvidername() {
		return providername;
	}

	public void setProvidername(String providername) {
		this.providername = providername == null ? null : providername.trim();
	}

	public BigDecimal getLogiscalfee() {
		return logiscalfee;
	}

	public void setLogiscalfee(BigDecimal logiscalfee) {
		this.logiscalfee = logiscalfee;
	}

	public BigDecimal getFavorrulemoney() {
		return favorrulemoney;
	}

	public void setFavorrulemoney(BigDecimal favorrulemoney) {
		this.favorrulemoney = favorrulemoney;
	}

	public BigDecimal getActualamount() {
		return actualamount;
	}

	public void setActualamount(BigDecimal actualamount) {
		this.actualamount = actualamount;
	}

	public BigDecimal getOrderamount() {
		return orderamount;
	}

	public void setOrderamount(BigDecimal orderamount) {
		this.orderamount = orderamount;
	}

	public BigDecimal getPayoffamount() {
		return payoffamount;
	}

	public void setPayoffamount(BigDecimal payoffamount) {
		this.payoffamount = payoffamount;
	}

	public Integer getGoodstypescount() {
		return goodstypescount;
	}

	public void setGoodstypescount(Integer goodstypescount) {
		this.goodstypescount = goodstypescount;
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getMergestate() {
		return mergestate;
	}

	public void setMergestate(Integer mergestate) {
		this.mergestate = mergestate;
	}

	public Date getRequiretime() {
		return requiretime;
	}

	public void setRequiretime(Date requiretime) {
		this.requiretime = requiretime;
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

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public BigDecimal getPayfee() {
		return payfee;
	}

	public void setPayfee(BigDecimal payfee) {
		this.payfee = payfee;
	}

	public Integer getPaystage() {
		return paystage;
	}

	public void setPaystage(Integer paystage) {
		this.paystage = paystage;
	}

	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}

	public Integer getPaychanel() {
		return paychanel;
	}

	public void setPaychanel(Integer paychanel) {
		this.paychanel = paychanel;
	}

	public BigDecimal getPayhandingcharge() {
		return payhandingcharge;
	}

	public void setPayhandingcharge(BigDecimal payhandingcharge) {
		this.payhandingcharge = payhandingcharge;
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

	public BigDecimal getStoreprofits() {
		return storeprofits;
	}

	public void setStoreprofits(BigDecimal storeprofits) {
		this.storeprofits = storeprofits;
	}

	public Integer getToproviderstate() {
		return toproviderstate;
	}

	public void setToproviderstate(Integer toproviderstate) {
		this.toproviderstate = toproviderstate;
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

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
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

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getExportstatus() {
		return exportstatus;
	}

	public void setExportstatus(Integer exportstatus) {
		this.exportstatus = exportstatus;
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

	public String getProviderremark() {
		return providerremark;
	}

	public void setProviderremark(String providerremark) {
		this.providerremark = providerremark == null ? null : providerremark.trim();
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

	public TgoodsproviderWithBLOBs getProviderinfo() {
		return providerinfo;
	}

	public void setProviderinfo(TgoodsproviderWithBLOBs providerinfo) {
		this.providerinfo = providerinfo;
	}

	public List<Torderitems> getTorderitemsList() {
		return torderitemsList;
	}

	public void setTorderitemsList(List<Torderitems> torderitemsList) {
		this.torderitemsList = torderitemsList;
	}

	public Integer getOrdertype() {
		return ordertype;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	public String getSkbsalemobilename() {
		return skbsalemobilename;
	}

	public void setSkbsalemobilename(String skbsalemobilename) {
		this.skbsalemobilename = skbsalemobilename;
	}

	public String getLogisticscode() {
		return logisticscode;
	}

	public void setLogisticscode(String logisticscode) {
		this.logisticscode = logisticscode;
	}

	public Integer getTlogisticcorpid() {
		return tlogisticcorpid;
	}

	public void setTlogisticcorpid(Integer tlogisticcorpid) {
		this.tlogisticcorpid = tlogisticcorpid;
	}

	public String getLogisticsid() {
		return logisticsid;
	}

	public void setLogisticsid(String logisticsid) {
		this.logisticsid = logisticsid;
	}

	public String getDeviceno() {
		return deviceno;
	}

	public void setDeviceno(String deviceno) {
		this.deviceno = deviceno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}