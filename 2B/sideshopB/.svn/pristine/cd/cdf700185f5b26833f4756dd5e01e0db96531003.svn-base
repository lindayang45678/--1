package com.lakala.module.poster.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 保存设备终端得最终频道广告集合
 *
 * @author VINCE
 */
public class TerminalChannel implements Serializable {

    private static final long serialVersionUID = 4897173113600523228L;

    @JSONField(name = "a")
    private String netChannelId;// 频道id

    @JSONField(name = "b")
    private List<Advertinfo> advertList;

    public String getNetChannelId() {
        return netChannelId;
    }

    public void setNetChannelId(String netChannelId) {
        this.netChannelId = netChannelId == null ? "" : netChannelId;
    }

    public List<Advertinfo> getAdvertList() {
        return advertList;
    }

    public void setAdvertList(List<Advertinfo> advertList) {
        this.advertList = advertList == null ? new ArrayList<Advertinfo>() : advertList;
    }
}
