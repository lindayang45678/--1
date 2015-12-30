package com.lakala.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p/>
 * Created by HOT.LIU on 2015/3/2.
 */
public class ChannelVirtualCat implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2970248382458596369L;

    @JSONField(name = "a")
    private String netChannelId;// 频道id

    @JSONField(name = "b")
    private List<VirtualCat> virtualCat;

    public String getNetChannelId() {
        return netChannelId;
    }

    public void setNetChannelId(String netChannelId) {
        this.netChannelId = (netChannelId == null ? "" : netChannelId);
    }

    public List<VirtualCat> getVirtualCat() {
        return virtualCat;
    }

    public void setVirtualCat(List<VirtualCat> virtualCat) {
        this.virtualCat = (virtualCat == null ? new ArrayList<VirtualCat>() : virtualCat);
    }

}
