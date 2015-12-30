package com.lakala.module.sreturn.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by HOT.LIU on 2015/3/6.
 */
public class ReturnMoble implements Serializable {
    private static final long serialVersionUID = -3914319686063391038L;

    private Integer treturnid;//售后申请单号
    private Integer treturnitemsid;//售后申请子单号即退款单号
    private String orderid;//订单编号
    private Integer returntype;//售后类型
    private String returntypename;//售后类型中文
    private Integer providerid;//供应商id
    private Date returntime;//售后申请时间
    private Date receipttime;//售后收货时间
    private Integer goodsid;//商品ID
    private String goodsname;//商品名称
    private String goodbigpic;
    private String skufrontdisnamestr;
    private String returnmoneystatus;//退款状态
    private String returngoodstatus;//退货状态
    private int goodscount;//商品数量
    private Date ordertime;//下单时间
    private BigDecimal goodsfinalprice;//商品订单总额
    private BigDecimal logisticsfee;//物流分单费用
    private int paychanel;//支付方式
    private int ispay;//是否支付
    private int state;//订单状态
    private int is3h;//是否为生鲜
    private String orderproviderid; //供应商级订单号

    public Integer getTreturnid() {
        return treturnid;
    }

    public void setTreturnid(Integer treturnid) {
        this.treturnid = treturnid;
    }

    public Integer getTreturnitemsid() {
        return treturnitemsid;
    }

    public void setTreturnitemsid(Integer treturnitemsid) {
        this.treturnitemsid = treturnitemsid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Integer getReturntype() {
        return returntype;
    }

    public void setReturntype(Integer returntype) {
        this.returntype = returntype;
    }

    public String getReturntypename() {
        return returntypename;
    }

    public void setReturntypename(String returntypename) {
        this.returntypename = returntypename;
    }

    public Integer getProviderid() {
        return providerid;
    }

    public void setProviderid(Integer providerid) {
        this.providerid = providerid;
    }

    public Date getReturntime() {
        return returntime;
    }

    public void setReturntime(Date returntime) {
        this.returntime = returntime;
    }

    public Date getReceipttime() {
        return receipttime;
    }

    public void setReceipttime(Date receipttime) {
        this.receipttime = receipttime;
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

    public String getGoodbigpic() {
        return goodbigpic;
    }

    public void setGoodbigpic(String goodbigpic) {
        this.goodbigpic = goodbigpic;
    }

    public String getSkufrontdisnamestr() {
        return skufrontdisnamestr;
    }

    public void setSkufrontdisnamestr(String skufrontdisnamestr) {
        this.skufrontdisnamestr = skufrontdisnamestr;
    }

    public String getReturnmoneystatus() {
        return returnmoneystatus;
    }

    public void setReturnmoneystatus(String returnmoneystatus) {
        this.returnmoneystatus = returnmoneystatus;
    }

    public String getReturngoodstatus() {
        return returngoodstatus;
    }

    public void setReturngoodstatus(String returngoodstatus) {
        this.returngoodstatus = returngoodstatus;
    }

    public int getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(int goodscount) {
        this.goodscount = goodscount;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    public BigDecimal getGoodsfinalprice() {
        return goodsfinalprice;
    }

    public void setGoodsfinalprice(BigDecimal goodsfinalprice) {
        this.goodsfinalprice = goodsfinalprice;
    }

    public BigDecimal getLogisticsfee() {
        return logisticsfee;
    }

    public void setLogisticsfee(BigDecimal logisticsfee) {
        this.logisticsfee = logisticsfee;
    }

    public int getPaychanel() {
        return paychanel;
    }

    public void setPaychanel(int paychanel) {
        this.paychanel = paychanel;
    }

    public int getIspay() {
        return ispay;
    }

    public void setIspay(int ispay) {
        this.ispay = ispay;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIs3h() {
        return is3h;
    }

    public void setIs3h(int is3h) {
        this.is3h = is3h;
    }

	public String getOrderproviderid() {
		return orderproviderid;
	}

	public void setOrderproviderid(String orderproviderid) {
		this.orderproviderid = orderproviderid;
	}
}
