package com.lakala.module.wholesale.model;

import com.lakala.model.ProductDetailedInformation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOT.LIU on 2015/3/16.
 */
public class OutListModel implements Serializable {

    private static final long serialVersionUID = -3675046672879271288L;

    private List<ProductDetailedInformation> list = new ArrayList<ProductDetailedInformation>();

    private int count = 0;

    public List<ProductDetailedInformation> getList() {
        return list;
    }

    public void setList(List<ProductDetailedInformation> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
