package com.lakala.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.*;

/**
 * <p/>
 * Created by HOT.LIU on 2015/3/2.
 */
public class TerminalChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JSONField(name = "a")
    private String netChannelId;// 频道id

    @JSONField(name = "b")
    private List<ProductDetail> productList = new ArrayList<ProductDetail>();// 全部商品集合

    public String getNetChannelId() {
        return netChannelId;
    }

    public void setNetChannelId(String netChannelId) {
        this.netChannelId = (netChannelId == null ? "" : netChannelId);
    }

    public List<ProductDetail> getProductList() {
        productList = (productList == null ? new ArrayList<ProductDetail>() : productList);
        Collections.sort(productList, new Comparator<ProductDetail>() {
            public int compare(ProductDetail arg0, ProductDetail arg1) {
                return arg1.getSort().compareTo(arg0.getSort());
            }
        });
        return productList;
    }

    public void setProductList(List<ProductDetail> productList) {
        productList = (productList == null ? new ArrayList<ProductDetail>() : productList);
        Collections.sort(productList, new Comparator<ProductDetail>() {
            public int compare(ProductDetail arg0, ProductDetail arg1) {
                return arg1.getSort().compareTo(arg0.getSort());
            }
        });
        this.productList = productList;
    }
}
