package com.lakala.module.sreturn.model;

import com.lakala.module.comm.ObjectInput;

import java.io.Serializable;

/**
 * Created by HOT.LIU on 2015/2/28.
 */
public class ReturnInput extends ObjectInput implements Serializable {
    private static final long serialVersionUID = 8046275715607345907L;

    private String orderid;

    private String treturnid;

    private String treturnitemsid;

    private boolean isagree = true;

    private String remarks;

    private String psam;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTreturnid() {
        return treturnid;
    }

    public void setTreturnid(String treturnid) {
        this.treturnid = treturnid;
    }

    public String getTreturnitemsid() {
        return treturnitemsid;
    }

    public void setTreturnitemsid(String treturnitemsid) {
        this.treturnitemsid = treturnitemsid;
    }

    public boolean getIsagree() {
        return isagree;
    }

    public void setIsagree(boolean isagree) {
        this.isagree = isagree;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPsam() {
        return psam;
    }

    public void setPsam(String psam) {
        this.psam = psam;
    }
}
