package com.lakala.model.profit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Torderitems implements Serializable {

	/**
	 * 订单商品三级字表数据
	 */
	private static final long serialVersionUID = -8279609209586865565L;

	/** 合并的主键 */
	private Integer torderitemsid;
	/** 二级子订单ID */
	private String orderproviderid;
	/** 订单id */
	private String orderid;
	/** 商品的id信息 */
	private Integer goodsid;
	/** 商品SKUO2OID */
	private Integer goodsskuid;
	/** 商品SKUO2OID，针对开店宝 */
	private Integer goodsskuo2oid;
	/** 商品名称 */
	private String goodsname;
	/** 商品外部编号 */
	private String goodsinprovidercode;
	/** 供应商id */
	private Integer providerid;
	/** 供应商名称 */
	private String providername;
	/** 物流主键 */
	private Integer logisticsid;
	/** 244订单商品性质 (246:自营;247:平台) */
	private Integer goodsisownorsupport;
	/** 所购商品数量 */
	private Integer goodscount;
	/** 参加优惠活动规则ID(只记录活动ID，不记促销ID) */
	private Integer ruleid;
	/** 促销名称(只记录促销名称，不记活动名称) */
	private String rulename;
	/** 商品参加的活动类型 */
	private Integer goodsruletype;
	/** 该商品活动优惠额 */
	private BigDecimal goodsfavorulemoney;
	/** 商品扣点（结算价来算销售价） */
	private Float goodsdeductionpoints;
	/** 商品结算价 */
	private BigDecimal goodspayoff;
	/** 销售价 */
	private BigDecimal goodssaleprice;
	/** 最终售价 */
	private BigDecimal goodsfinalprice;
	/** 毛利润 */
	private BigDecimal goodsprofits;
	/** 净利润 */
	private BigDecimal goodsretainedprofits;
	/** 销售点返利（最终售价要高于结算价,否则按单返利） */
	private BigDecimal storeprofits;
	/** 毛利润的分润基数 */
	private Float deductionbasepoints;
	/** 公司分润比例（分润扣点 */
	private Float companydiscount;
	/** 公司分润金额 */
	private BigDecimal companyprofit;
	/** 销售点分润比例（分润扣点 */
	private Float storediscount;
	/** 销售点分润金额 */
	private BigDecimal storeprofit;
	/** 运营商分润比例（分润扣点 */
	private Float operatordiscount;
	/** 运营商分润金额 */
	private BigDecimal operatorprofit;
	/** 返现金额 */
	private BigDecimal returnamount;
	/** 补贴金额 */
	private BigDecimal subsidyamount;
	/** 商品运输状态:0-未导出，1-已导出，2-已发货 */
	private Integer goodslogisticstate;
	/** 来自频道 */
	private String channelcode;
	/** 末级虚分类ID */
	private String virtualclassificationid;
	/** 商品实际分类 */
	private String goodsclassificationid;
	/** 物流状态信息 99:未发货;100:发货中;102:已发货;103:部分签收;104:已签收;216:已拒收;217:物流异常 */
	private Integer state;
	private String stateStr;
	/** 订单合单状态 146:未合单;147:合单 */
	private Integer mergestate;
	/** 下单时间 */
	private Date ordertime;
	/** 订单取消状态 136:未取消;137:已取消 */
	private Integer cancelstate;
	/** 最后修改时间 */
	private Date lastmodifytime;
	/** 商品规格 */
	private String norms;
	/** 订单锁定状态 133:未锁定;134:已锁定 */
	private Integer lockstate;
	/** 配比分润表ID */
	private Integer discountid;
	/** 导出未发货订单状态，0-未导出，1-导出 */
	private Integer exportstatus;
	/** (给店主用)订单是否已经进行过结算数据计算，78-未结算 79-已结算 */
	private Integer settlestate;
	/** 确认收到退货备注信息 */
	private String confirmreturnremark;
	/** 供应商结算时间 */
	private Date providersettletime;
	/** 供应商结算状态，78-未结算 79-已结算 */
	private Integer providersettlestate;
	/** 供应商结算金额 */
	private BigDecimal providersettlemoney;
	/** 店主结算状态，78-未结算 79-已结算 */
	private Integer shipsettlestate;
	/** 店主结算时间 */
	private Date shipsettletime;
	/** 店主结算金额 */
	private BigDecimal shipsettlemoney;
	/** 机主姓名,购买合约机时填写 */
	private String phonemembername;
	/** 机主身份证号，购买合约机时填写 */
	private String phonememberidcard;
	/** 是否赠品，202-商品;204-赠品 */
	private Integer giftstate;
	/** 赠品关联的商品ID */
	private String giftparentid;
	/** 发货性质(或退换货订单标识 ） 112:正常订单;113:拒收换新;114:退货;293:售后换新 */
	private Integer returnstate;
	/** 退换货状态(订单作废状态) 116:正常订单;117:换货中;118:已换货;119:退货中;120:已退货 */
	private Integer invalidstate;
	private String invalidstateStr;
	/** 由于换货生成的新的订单，关联的原始订单的明细订单ID（tOrderItemsId） */
	private Integer returnparentid;
	/** 订单库存是否重置 (275:是;276:否;-99:异常) */
	private Integer isrestore;
	/** 自提码(暂时给WAP商城用） */
	private Integer sincecode;
	/** 退货码(暂时给WAP商城用 */
	private Integer returncode;
	/** 营销活动{[id:XXX,type:1]|[id:XXX,type:3]} 其中，type：1.促销、3.优惠券、4.活动 */
	private String fav;
	/** 合并的主键 */
	private TgoodsinfoWithBLOBs tgoodsinfo;
	/** 组织ID */
	private String orgid;

	/** 签收时间 */
	private Date receivertime;

	private String logisticscode;

	private String corpname;

	private Date returntime;

	private BigDecimal frRatio;

	private BigDecimal reRatio;

	private BigDecimal sbRatio;

	private BigDecimal payRate;
	
	private Integer ispay;
	
	private Integer tallorderid;
	
	private Date paytime;
	
	private Integer paychanel;
	
	private Integer platorself;  //商品自营平台标识。自营-452，平台-453
	
	private String ecnetno; //电商网点编号

	public BigDecimal getPayRate() {
		return payRate;
	}

	public void setPayRate(BigDecimal payRate) {
		this.payRate = payRate;
	}

	public Integer getTorderitemsid() {
		return torderitemsid;
	}

	public void setTorderitemsid(Integer torderitemsid) {
		this.torderitemsid = torderitemsid;
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

	public Integer getGoodsid() {
		return goodsid;
	}

	public void setGoodsid(Integer goodsid) {
		this.goodsid = goodsid;
	}

	public Integer getGoodsskuid() {
		return goodsskuid;
	}

	public void setGoodsskuid(Integer goodsskuid) {
		this.goodsskuid = goodsskuid;
	}

	public Integer getGoodsskuo2oid() {
		return goodsskuo2oid;
	}

	public void setGoodsskuo2oid(Integer goodsskuo2oid) {
		this.goodsskuo2oid = goodsskuo2oid;
	}

	public String getGoodsname() {
		return goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	public String getGoodsinprovidercode() {
		return goodsinprovidercode;
	}

	public void setGoodsinprovidercode(String goodsinprovidercode) {
		this.goodsinprovidercode = goodsinprovidercode;
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

	public Integer getLogisticsid() {
		return logisticsid;
	}

	public void setLogisticsid(Integer logisticsid) {
		this.logisticsid = logisticsid;
	}

	public Integer getGoodsisownorsupport() {
		return goodsisownorsupport;
	}

	public void setGoodsisownorsupport(Integer goodsisownorsupport) {
		this.goodsisownorsupport = goodsisownorsupport;
	}

	public Integer getGoodscount() {
		return goodscount;
	}

	public void setGoodscount(Integer goodscount) {
		this.goodscount = goodscount;
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

	public Integer getGoodsruletype() {
		return goodsruletype;
	}

	public void setGoodsruletype(Integer goodsruletype) {
		this.goodsruletype = goodsruletype;
	}

	public BigDecimal getGoodsfavorulemoney() {
		return goodsfavorulemoney;
	}

	public void setGoodsfavorulemoney(BigDecimal goodsfavorulemoney) {
		this.goodsfavorulemoney = goodsfavorulemoney;
	}

	public Float getGoodsdeductionpoints() {
		return goodsdeductionpoints;
	}

	public void setGoodsdeductionpoints(Float goodsdeductionpoints) {
		this.goodsdeductionpoints = goodsdeductionpoints;
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

	public BigDecimal getGoodsfinalprice() {
		return goodsfinalprice;
	}

	public void setGoodsfinalprice(BigDecimal goodsfinalprice) {
		this.goodsfinalprice = goodsfinalprice;
	}

	public BigDecimal getGoodsprofits() {
		return goodsprofits;
	}

	public void setGoodsprofits(BigDecimal goodsprofits) {
		this.goodsprofits = goodsprofits;
	}

	public BigDecimal getGoodsretainedprofits() {
		return goodsretainedprofits;
	}

	public void setGoodsretainedprofits(BigDecimal goodsretainedprofits) {
		this.goodsretainedprofits = goodsretainedprofits;
	}

	public BigDecimal getStoreprofits() {
		return storeprofits;
	}

	public void setStoreprofits(BigDecimal storeprofits) {
		this.storeprofits = storeprofits;
	}

	public Float getDeductionbasepoints() {
		return deductionbasepoints;
	}

	public void setDeductionbasepoints(Float deductionbasepoints) {
		this.deductionbasepoints = deductionbasepoints;
	}

	public Float getCompanydiscount() {
		return companydiscount;
	}

	public void setCompanydiscount(Float companydiscount) {
		this.companydiscount = companydiscount;
	}

	public BigDecimal getCompanyprofit() {
		return companyprofit;
	}

	public void setCompanyprofit(BigDecimal companyprofit) {
		this.companyprofit = companyprofit;
	}

	public Float getStorediscount() {
		return storediscount;
	}

	public void setStorediscount(Float storediscount) {
		this.storediscount = storediscount;
	}

	public BigDecimal getStoreprofit() {
		return storeprofit;
	}

	public void setStoreprofit(BigDecimal storeprofit) {
		this.storeprofit = storeprofit;
	}

	public Float getOperatordiscount() {
		return operatordiscount;
	}

	public void setOperatordiscount(Float operatordiscount) {
		this.operatordiscount = operatordiscount;
	}

	public BigDecimal getOperatorprofit() {
		return operatorprofit;
	}

	public void setOperatorprofit(BigDecimal operatorprofit) {
		this.operatorprofit = operatorprofit;
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

	public Integer getGoodslogisticstate() {
		return goodslogisticstate;
	}

	public void setGoodslogisticstate(Integer goodslogisticstate) {
		this.goodslogisticstate = goodslogisticstate;
	}

	public String getChannelcode() {
		return channelcode;
	}

	public void setChannelcode(String channelcode) {
		this.channelcode = channelcode;
	}

	public String getVirtualclassificationid() {
		return virtualclassificationid;
	}

	public void setVirtualclassificationid(String virtualclassificationid) {
		this.virtualclassificationid = virtualclassificationid;
	}

	public String getGoodsclassificationid() {
		return goodsclassificationid;
	}

	public void setGoodsclassificationid(String goodsclassificationid) {
		this.goodsclassificationid = goodsclassificationid;
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

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
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

	public String getNorms() {
		return norms;
	}

	public void setNorms(String norms) {
		this.norms = norms;
	}

	public Integer getLockstate() {
		return lockstate;
	}

	public void setLockstate(Integer lockstate) {
		this.lockstate = lockstate;
	}

	public Integer getDiscountid() {
		return discountid;
	}

	public void setDiscountid(Integer discountid) {
		this.discountid = discountid;
	}

	public Integer getExportstatus() {
		return exportstatus;
	}

	public void setExportstatus(Integer exportstatus) {
		this.exportstatus = exportstatus;
	}

	public Integer getSettlestate() {
		return settlestate;
	}

	public void setSettlestate(Integer settlestate) {
		this.settlestate = settlestate;
	}

	public String getConfirmreturnremark() {
		return confirmreturnremark;
	}

	public void setConfirmreturnremark(String confirmreturnremark) {
		this.confirmreturnremark = confirmreturnremark;
	}

	public Date getProvidersettletime() {
		return providersettletime;
	}

	public void setProvidersettletime(Date providersettletime) {
		this.providersettletime = providersettletime;
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

	public Date getShipsettletime() {
		return shipsettletime;
	}

	public void setShipsettletime(Date shipsettletime) {
		this.shipsettletime = shipsettletime;
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

	public Integer getGiftstate() {
		return giftstate;
	}

	public void setGiftstate(Integer giftstate) {
		this.giftstate = giftstate;
	}

	public String getGiftparentid() {
		return giftparentid;
	}

	public void setGiftparentid(String giftparentid) {
		this.giftparentid = giftparentid;
	}

	public Integer getReturnstate() {
		return returnstate;
	}

	public void setReturnstate(Integer returnstate) {
		this.returnstate = returnstate;
	}

	public Integer getInvalidstate() {
		return invalidstate;
	}

	public void setInvalidstate(Integer invalidstate) {
		this.invalidstate = invalidstate;
	}

	public Integer getReturnparentid() {
		return returnparentid;
	}

	public void setReturnparentid(Integer returnparentid) {
		this.returnparentid = returnparentid;
	}

	public Integer getIsrestore() {
		return isrestore;
	}

	public void setIsrestore(Integer isrestore) {
		this.isrestore = isrestore;
	}

	public Integer getSincecode() {
		return sincecode;
	}

	public void setSincecode(Integer sincecode) {
		this.sincecode = sincecode;
	}

	public Integer getReturncode() {
		return returncode;
	}

	public void setReturncode(Integer returncode) {
		this.returncode = returncode;
	}

	public String getFav() {
		return fav;
	}

	public void setFav(String fav) {
		this.fav = fav;
	}

	public Date getReceivertime() {
		return receivertime;
	}

	public void setReceivertime(Date receivertime) {
		this.receivertime = receivertime;
	}

	public String getLogisticscode() {
		return logisticscode;
	}

	public void setLogisticscode(String logisticscode) {
		this.logisticscode = logisticscode;
	}

	public String getCorpname() {
		return corpname;
	}

	public void setCorpname(String corpname) {
		this.corpname = corpname;
	}

	public Date getReturntime() {
		return returntime;
	}

	public void setReturntime(Date returntime) {
		this.returntime = returntime;
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

	public TgoodsinfoWithBLOBs getTgoodsinfo() {
		return tgoodsinfo;
	}

	public void setTgoodsinfo(TgoodsinfoWithBLOBs tgoodsinfo) {
		this.tgoodsinfo = tgoodsinfo;
	}

	public String getStateStr() {
		return stateStr;
	}

	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
	}

	public String getInvalidstateStr() {
		return invalidstateStr;
	}

	public void setInvalidstateStr(String invalidstateStr) {
		this.invalidstateStr = invalidstateStr;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public Integer getIspay() {
		return ispay;
	}

	public void setIspay(Integer ispay) {
		this.ispay = ispay;
	}

	public Integer getTallorderid() {
		return tallorderid;
	}

	public void setTallorderid(Integer tallorderid) {
		this.tallorderid = tallorderid;
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

	public Integer getPlatorself() {
		return platorself;
	}

	public void setPlatorself(Integer platorself) {
		this.platorself = platorself;
	}

	public String getEcnetno() {
		return ecnetno;
	}

	public void setEcnetno(String ecnetno) {
		this.ecnetno = ecnetno;
	}

}