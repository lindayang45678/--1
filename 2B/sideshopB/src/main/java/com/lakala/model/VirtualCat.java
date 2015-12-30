package com.lakala.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

//虚拟类目
public class VirtualCat implements Serializable {

    private static final long serialVersionUID = 1795380015335466510L;

    @JSONField(name = "a")
    private String virtualCateId;

    @JSONField(name = "b")
    private String virtualCateDisc;

    @JSONField(name = "c")
    private List<VirtualCat> child;

    public String getVirtualCateId() {
        return virtualCateId;
    }

    public void setVirtualCateId(String virtualCateId) {
        this.virtualCateId = virtualCateId;
    }

    public String getVirtualCateDisc() {
        return virtualCateDisc;
    }

    public void setVirtualCateDisc(String virtualCateDisc) {
        this.virtualCateDisc = virtualCateDisc;
    }

    public List<VirtualCat> getChild() {
        return child;
    }

    public void setChild(List<VirtualCat> child) {
        this.child = child;
    }

}
