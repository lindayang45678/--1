package com.lakala.module.goods.vo;

import java.io.Serializable;
import java.util.List;

import com.lakala.module.comm.ObjectInput;

/**
 * 批发进货入参实体
 * <p/>
 * Created by zuoyb
 */
public class SideShopGoodsListInput extends ObjectInput implements Serializable {

    private static final long serialVersionUID = 8392613581668620546L;


    private String virtualpsam;//虚拟psam，自营商品虚拟psam

    private String psam;//平台商品psam

    private String channelid;//频道ID

    private List<String> virtualcatidlist;//虚拟分类ID
    
    private String virtualcatid;//虚拟分类ID

    private String searchparm;//查询参数

    private String goodsid;//商品ID
    
    private String type;//0-自营；1-平台
    
    private String level;//虚分类等级
    
    

    public String getVirtualcatid() {
		return virtualcatid;
	}

	public void setVirtualcatid(String virtualcatid) {
		this.virtualcatid = virtualcatid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVirtualpsam() {
        return virtualpsam;
    }

    public void setVirtualpsam(String virtualpsam) {
        this.virtualpsam = virtualpsam;
    }

    public String getPsam() {
        return psam;
    }

    public void setPsam(String psam) {
        this.psam = psam;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }


    public List<String> getVirtualcatidlist() {
		return virtualcatidlist;
	}

	public void setVirtualcatidlist(List<String> virtualcatidlist) {
		this.virtualcatidlist = virtualcatidlist;
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
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}
