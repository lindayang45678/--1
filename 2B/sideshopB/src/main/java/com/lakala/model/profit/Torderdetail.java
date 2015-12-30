package com.lakala.model.profit;

import java.math.BigDecimal;
import java.util.Date;

public class Torderdetail {
	private Integer id;

	private String orderitemsid;

	private String orderproviderid;

	private String orderid;

	private String orderdetailid;

	private Integer orderdetailtype;

	private Integer goodsid;

	private String goodsname;

	private String goodsclassificationid;

	private Integer providerid;

	private String providername;

	private BigDecimal goodspayoff;

	private BigDecimal goodssaleprice;

	private Integer goodscount;

	private BigDecimal goodsfinalprice;

	private BigDecimal orderfinalamount;

	private Date goodsouttime;

	private Date goodsintime;

	private String fav;

	private BigDecimal goodsfavorulemoney;

	private BigDecimal duepay;

	private BigDecimal tradecharges;

	private Float goodsdeductionpoints;

	private BigDecimal salecommissions;

	private Integer channelcode;

	private Integer goodchannelcode;

	private BigDecimal netprofit;

	private BigDecimal netreturnamount;

	private BigDecimal netsubsidyamount;

	private BigDecimal companyprofit;

	private BigDecimal companyreturnamount;

	private BigDecimal companysubsidyamount;

	private BigDecimal areaprofit;

	private BigDecimal areareturnamount;

	private BigDecimal areasubsidyamount;

	private Integer createbill;

	private String billid;

	private Date createbilltime;

	private Integer issettle;

	private Date receivetime;

	private Date paytime;

	private Date providersettletime;

	private Integer issettleprovideramount;

	private Date branchcompanysettletime;

	private Integer isbranchcompanyamount;

	private Date customermanagersettletime;

	private Integer iscustomermanageramount;

	private Date shopmanprofitsettletime;

	private Integer issettleshopmanprofit;

	private String termfbtype;

	private String termfbtypestr;

	private Date createtime;

	private Integer orderproperty;

	private Integer orderstatus;

	private String branchid;

	private String branchcompany;

	private Date ordertime;

	private Date returntime;

	private Integer netproperty;

	private String psam;

	private String netpoint;

	private String shoppersettlementid;

	private Date shopperpaytime;

	private BigDecimal providerdeliverfee;

	private Integer providerdelivertype;

	private BigDecimal salerprofit;

	private Date paymentdeadline;

	private Date billdeadline;

	private Integer payperiod;

	private Integer settlestateduedate;

	private Integer platorself;

	private Date refundtime;

	private BigDecimal payrate;

	private BigDecimal payrateamount;

	/** 支付方式 */
	private String payMode;

	/** 自营今日订单单数 */
	private Long ownOrderNum;
	/** 自营今日结算订单单数 */
	private Long ownSettleNum;
	/** 平台周期收益单数 */
	private Long terraceOrderNum;
	/** 平台周期结算单数 */
	private Long terraceSettleNum;

	private String invalidStateStr;
	private String stateStr;

	// 商品运费
	private BigDecimal logisticsfee;

	// 排序时间key
	private Date sortTime;

	// 商品图片
	private String goodBigPic;
	// 商品规格ID
	private String skuIdStr;
	// 商品规格描述
	private String skuFrontDisNameStr;
	// 实分类类目ID,继承商品基本信息表
	private String tRealCateId;
	private Integer tGoodInfoPoolId;
	
	// 用户支付金额
	private BigDecimal orderFinalPrice;
	
	// 订单流水
	private BigDecimal flowAmount;
	
	//参数直降活动标示：0-未参与，1-参与
	private Integer directflag;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderitemsid() {
		return orderitemsid;
	}

	public void setOrderitemsid(String orderitemsid) {
		this.orderitemsid = orderitemsid;
	}

	public String getOrderproviderid() {
		return orderproviderid;
	}

	public void setOrderproviderid(String orderproviderid) {
		this.orderproviderid = orderproviderid;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getOrderdetailid() {
		return orderdetailid;
	}

	public void setOrderdetailid(String orderdetailid) {
		this.orderdetailid = orderdetailid;
	}

	public Integer getOrderdetailtype() {
		return orderdetailtype;
	}

	public void setOrderdetailtype(Integer orderdetailtype) {
		this.orderdetailtype = orderdetailtype;
	}

	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsclassificationid() {
		return goodsclassificationid;
	}

	public void setGoodsclassificationid(String goodsclassificationid) {
		this.goodsclassificationid = goodsclassificationid;
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
		this.providername = providername;
	}

	public BigDecimal getGoodspayoff() {
		return goodspayoff;
	}

	public void setGoodspayoff(BigDecimal goodspayoff) {
		this.goodspayoff = goodspayoff;
	}

	public BigDecimal getGoodssaleprice() {
		return goodssaleprice;
	}

	public void setGoodssaleprice(BigDecimal goodssaleprice) {
		this.goodssaleprice = goodssaleprice;
	}

	public Integer getGoodscount() {
		return goodscount;
	}

	public void setGoodscount(Integer goodscount) {
		this.goodscount = goodscount;
	}

	public BigDecimal getGoodsfinalprice() {
		return goodsfinalprice;
	}

	public void setGoodsfinalprice(BigDecimal goodsfinalprice) {
		this.goodsfinalprice = goodsfinalprice;
	}

	public BigDecimal getOrderfinalamount() {
		return orderfinalamount;
	}

	public void setOrderfinalamount(BigDecimal orderfinalamount) {
		this.orderfinalamount = orderfinalamount;
	}

	public Date getGoodsouttime() {
		return goodsouttime;
	}

	public void setGoodsouttime(Date goodsouttime) {
		this.goodsouttime = goodsouttime;
	}

	public Date getGoodsintime() {
		return goodsintime;
	}

	public void setGoodsintime(Date goodsintime) {
		this.goodsintime = goodsintime;
	}

	public String getFav() {
		return fav;
	}

	public void setFav(String fav) {
		this.fav = fav;
	}

	public BigDecimal getGoodsfavorulemoney() {
		return goodsfavorulemoney;
	}

	public void setGoodsfavorulemoney(BigDecimal goodsfavorulemoney) {
		this.goodsfavorulemoney = goodsfavorulemoney;
	}

	public BigDecimal getDuepay() {
		return duepay;
	}

	public void setDuepay(BigDecimal duepay) {
		this.duepay = duepay;
	}

	public BigDecimal getTradecharges() {
		return tradecharges;
	}

	public void setTradecharges(BigDecimal tradecharges) {
		this.tradecharges = tradecharges;
	}

	public Float getGoodsdeductionpoints() {
		return goodsdeductionpoints;
	}

	public void setGoodsdeductionpoints(Float goodsdeductionpoints) {
		this.goodsdeductionpoints = goodsdeductionpoints;
	}

	public BigDecimal getSalecommissions() {
		return salecommissions;
	}

	public void setSalecommissions(BigDecimal salecommissions) {
		this.salecommissions = salecommissions;
	}

	public Integer getChannelcode() {
		return channelcode;
	}

	public void setChannelcode(Integer channelcode) {
		this.channelcode = channelcode;
	}

	public Integer getGoodchannelcode() {
		return goodchannelcode;
	}

	public void setGoodchannelcode(Integer goodchannelcode) {
		this.goodchannelcode = goodchannelcode;
	}

	public BigDecimal getNetprofit() {
		return netprofit;
	}

	public void setNetprofit(BigDecimal netprofit) {
		this.netprofit = netprofit;
	}

	public BigDecimal getNetreturnamount() {
		return netreturnamount;
	}

	public void setNetreturnamount(BigDecimal netreturnamount) {
		this.netreturnamount = netreturnamount;
	}

	public BigDecimal getNetsubsidyamount() {
		return netsubsidyamount;
	}

	public void setNetsubsidyamount(BigDecimal netsubsidyamount) {
		this.netsubsidyamount = netsubsidyamount;
	}

	public BigDecimal getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(BigDecimal companyprofit) {
		this.companyprofit = companyprofit;
	}

	public BigDecimal getCompanyreturnamount() {
		return companyreturnamount;
	}

	public void setCompanyreturnamount(BigDecimal companyreturnamount) {
		this.companyreturnamount = companyreturnamount;
	}

	public BigDecimal getCompanysubsidyamount() {
		return companysubsidyamount;
	}

	public void setCompanysubsidyamount(BigDecimal companysubsidyamount) {
		this.companysubsidyamount = companysubsidyamount;
	}

	public BigDecimal getAreaprofit() {
		return areaprofit;
	}

	public void setAreaprofit(BigDecimal areaprofit) {
		this.areaprofit = areaprofit;
	}

	public BigDecimal getAreareturnamount() {
		return areareturnamount;
	}

	public void setAreareturnamount(BigDecimal areareturnamount) {
		this.areareturnamount = areareturnamount;
	}

	public BigDecimal getAreasubsidyamount() {
		return areasubsidyamount;
	}

	public void setAreasubsidyamount(BigDecimal areasubsidyamount) {
		this.areasubsidyamount = areasubsidyamount;
	}

	public Integer getCreatebill() {
		return createbill;
	}

	public void setCreatebill(Integer createbill) {
		this.createbill = createbill;
	}

	public String getBillid() {
		return billid;
	}

	public void setBillid(String billid) {
		this.billid = billid;
	}

	public Date getCreatebilltime() {
		return createbilltime;
	}

	public void setCreatebilltime(Date createbilltime) {
		this.createbilltime = createbilltime;
	}

	public Integer getIssettle() {
		return issettle;
	}

	public void setIssettle(Integer issettle) {
		this.issettle = issettle;
	}

	public Date getReceivetime() {
		return receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}

	public Date getPaytime() {
		return paytime;
	}

	public void setPaytime(Date paytime) {
		this.paytime = paytime;
	}

	public Date getProvidersettletime() {
		return providersettletime;
	}

	public void setProvidersettletime(Date providersettletime) {
		this.providersettletime = providersettletime;
	}

	public Integer getIssettleprovideramount() {
		return issettleprovideramount;
	}

	public void setIssettleprovideramount(Integer issettleprovideramount) {
		this.issettleprovideramount = issettleprovideramount;
	}

	public Date getBranchcompanysettletime() {
		return branchcompanysettletime;
	}

	public void setBranchcompanysettletime(Date branchcompanysettletime) {
		this.branchcompanysettletime = branchcompanysettletime;
	}

	public Integer getIsbranchcompanyamount() {
		return isbranchcompanyamount;
	}

	public void setIsbranchcompanyamount(Integer isbranchcompanyamount) {
		this.isbranchcompanyamount = isbranchcompanyamount;
	}

	public Date getCustomermanagersettletime() {
		return customermanagersettletime;
	}

	public void setCustomermanagersettletime(Date customermanagersettletime) {
		this.customermanagersettletime = customermanagersettletime;
	}

	public Integer getIscustomermanageramount() {
		return iscustomermanageramount;
	}

	public void setIscustomermanageramount(Integer iscustomermanageramount) {
		this.iscustomermanageramount = iscustomermanageramount;
	}

	public Date getShopmanprofitsettletime() {
		return shopmanprofitsettletime;
	}

	public void setShopmanprofitsettletime(Date shopmanprofitsettletime) {
		this.shopmanprofitsettletime = shopmanprofitsettletime;
	}

	public Integer getIssettleshopmanprofit() {
		return issettleshopmanprofit;
	}

	public void setIssettleshopmanprofit(Integer issettleshopmanprofit) {
		this.issettleshopmanprofit = issettleshopmanprofit;
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

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Integer getOrderproperty() {
		return orderproperty;
	}

	public void setOrderproperty(Integer orderproperty) {
		this.orderproperty = orderproperty;
	}

	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getBranchcompany() {
		return branchcompany;
	}

	public void setBranchcompany(String branchcompany) {
		this.branchcompany = branchcompany;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Date getReturntime() {
		return returntime;
	}

	public void setReturntime(Date returntime) {
		this.returntime = returntime;
	}

	public Integer getNetproperty() {
		return netproperty;
	}

	public void setNetproperty(Integer netproperty) {
		this.netproperty = netproperty;
	}

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}

	public String getNetpoint() {
		return netpoint;
	}

	public void setNetpoint(String netpoint) {
		this.netpoint = netpoint;
	}

	public String getShoppersettlementid() {
		return shoppersettlementid;
	}

	public void setShoppersettlementid(String shoppersettlementid) {
		this.shoppersettlementid = shoppersettlementid;
	}

	public Date getShopperpaytime() {
		return shopperpaytime;
	}

	public void setShopperpaytime(Date shopperpaytime) {
		this.shopperpaytime = shopperpaytime;
	}

	public BigDecimal getProviderdeliverfee() {
		return providerdeliverfee;
	}

	public void setProviderdeliverfee(BigDecimal providerdeliverfee) {
		this.providerdeliverfee = providerdeliverfee;
	}

	public Integer getProviderdelivertype() {
		return providerdelivertype;
	}

	public void setProviderdelivertype(Integer providerdelivertype) {
		this.providerdelivertype = providerdelivertype;
	}

	public BigDecimal getSalerprofit() {
		return salerprofit;
	}

	public void setSalerprofit(BigDecimal salerprofit) {
		this.salerprofit = salerprofit;
	}

	public Date getPaymentdeadline() {
		return paymentdeadline;
	}

	public void setPaymentdeadline(Date paymentdeadline) {
		this.paymentdeadline = paymentdeadline;
	}

	public Date getBilldeadline() {
		return billdeadline;
	}

	public void setBilldeadline(Date billdeadline) {
		this.billdeadline = billdeadline;
	}

	public Integer getPayperiod() {
		return payperiod;
	}

	public void setPayperiod(Integer payperiod) {
		this.payperiod = payperiod;
	}

	public Integer getSettlestateduedate() {
		return settlestateduedate;
	}

	public void setSettlestateduedate(Integer settlestateduedate) {
		this.settlestateduedate = settlestateduedate;
	}

	public Integer getPlatorself() {
		return platorself;
	}

	public void setPlatorself(Integer platorself) {
		this.platorself = platorself;
	}

	public Date getRefundtime() {
		return refundtime;
	}

	public void setRefundtime(Date refundtime) {
		this.refundtime = refundtime;
	}

	public BigDecimal getPayrate() {
		return payrate;
	}

	public void setPayrate(BigDecimal payrate) {
		this.payrate = payrate;
	}

	public BigDecimal getPayrateamount() {
		return payrateamount;
	}

	public void setPayrateamount(BigDecimal payrateamount) {
		this.payrateamount = payrateamount;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public Long getOwnOrderNum() {
		return ownOrderNum;
	}

	public void setOwnOrderNum(Long ownOrderNum) {
		this.ownOrderNum = ownOrderNum;
	}

	public Long getOwnSettleNum() {
		return ownSettleNum;
	}

	public void setOwnSettleNum(Long ownSettleNum) {
		this.ownSettleNum = ownSettleNum;
	}

	public Long getTerraceOrderNum() {
		return terraceOrderNum;
	}

	public void setTerraceOrderNum(Long terraceOrderNum) {
		this.terraceOrderNum = terraceOrderNum;
	}

	public Long getTerraceSettleNum() {
		return terraceSettleNum;
	}

	public void setTerraceSettleNum(Long terraceSettleNum) {
		this.terraceSettleNum = terraceSettleNum;
	}

	public String getInvalidStateStr() {
		return invalidStateStr;
	}

	public void setInvalidStateStr(String invalidStateStr) {
		this.invalidStateStr = invalidStateStr;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public BigDecimal getLogisticsfee() {
		return logisticsfee;
	}

	public void setLogisticsfee(BigDecimal logisticsfee) {
		this.logisticsfee = logisticsfee;
	}

	public Date getSortTime() {
		return sortTime;
	}

	public void setSortTime(Date sortTime) {
		this.sortTime = sortTime;
	}

	public String getGoodBigPic() {
		return goodBigPic;
	}

	public void setGoodBigPic(String goodBigPic) {
		this.goodBigPic = goodBigPic;
	}

	public String getSkuIdStr() {
		return skuIdStr;
	}

	public void setSkuIdStr(String skuIdStr) {
		this.skuIdStr = skuIdStr;
	}

	public String getSkuFrontDisNameStr() {
		return skuFrontDisNameStr;
	}

	public void setSkuFrontDisNameStr(String skuFrontDisNameStr) {
		this.skuFrontDisNameStr = skuFrontDisNameStr;
	}

	public String gettRealCateId() {
		return tRealCateId;
	}

	public void settRealCateId(String tRealCateId) {
		this.tRealCateId = tRealCateId;
	}

	public Integer gettGoodInfoPoolId() {
		return tGoodInfoPoolId;
	}

	public void settGoodInfoPoolId(Integer tGoodInfoPoolId) {
		this.tGoodInfoPoolId = tGoodInfoPoolId;
	}

	public BigDecimal getOrderFinalPrice() {
		return orderFinalPrice;
	}

	public void setOrderFinalPrice(BigDecimal orderFinalPrice) {
		this.orderFinalPrice = orderFinalPrice;
	}

	public BigDecimal getFlowAmount() {
		return flowAmount;
	}

	public void setFlowAmount(BigDecimal flowAmount) {
		this.flowAmount = flowAmount;
	}

	public Integer getDirectflag() {
		return directflag;
	}

	public void setDirectflag(Integer directflag) {
		this.directflag = directflag;
	}
}