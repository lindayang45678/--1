package com.lakala.module.wholesale.model;

import com.lakala.module.comm.ObjectInput;

import java.io.Serializable;

/**
 * 批发进货入参实体
 * <p/>
 * Created by HOT.LIU on 2015/3/2.
 */
public class InputModel extends ObjectInput implements Serializable {

    private static final long serialVersionUID = 8392613581668620546L;


    private String virtualpsam;//虚拟psam，自营商品虚拟psam

    private String psam;//平台商品psam

    private String channelid;//频道ID

    private String virtualcatid;//虚拟分类ID

    private String searchparm;//查询参数

    private String goodsid;//商品ID

    private String level;

    private Integer platorself = 453;

    public String getVirtualpsam() {
        return virtualpsam;
    }

    public void setVirtualpsam(String virtualpsam) {
        this.virtualpsam = virtualpsam;
    }

    public String getPsam() {
        return psam == null ? psam : psam.toUpperCase();
    }

    public void setPsam(String psam) {
        this.psam = psam.toUpperCase();
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getVirtualcatid() {
        return virtualcatid;
    }

    public void setVirtualcatid(String virtualcatid) {
        this.virtualcatid = virtualcatid;
    }

    public String getSearchparm() {
        return searchparm;
    }

    public void setSearchparm(String searchparm) {
        this.searchparm = searchparm;
    }

    public String getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(String goodsid) {
        this.goodsid = goodsid;
    }

    public String getLevel() {
        if ("first".equals(level)) {
            return "1";
        } else if ("second".equals(level)) {
            return "2";
        } else if ("third".equals(level)) {
            return "3";
        } else if ("fourth".equals(level)) {
            return "4";
        } else {
            return level;
        }
    }

    public void setLevel(String level) {
        if ("first".equals(level)) {
            this.level = "1";
        } else if ("second".equals(level)) {
            this.level = "2";
        } else if ("third".equals(level)) {
            this.level = "3";
        } else if ("fourth".equals(level)) {
            this.level = "4";
        }
    }

    public Integer getPlatorself() {
        return platorself;
    }

    public void setPlatorself(Integer platorself) {
        this.platorself = platorself;
    }
}
