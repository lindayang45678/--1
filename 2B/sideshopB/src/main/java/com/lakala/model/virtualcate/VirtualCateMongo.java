package com.lakala.model.virtualcate;

import java.io.Serializable;
import java.util.*;

/**
 * Created by HOT.LIU on 2015/5/20.
 */
public class VirtualCateMongo implements Serializable {
    private static final long serialVersionUID = 2009336216203390784L;

    private final static Map<String, VirtualCateMongo> idMapping = new HashMap<String, VirtualCateMongo>();

    private Integer virtualCateId;

    private String virtualCateDisc;

    private Integer fathervirtualcateid;

    private Integer sort;

    private List<VirtualCateMongo> child = new ArrayList<VirtualCateMongo>();

    public VirtualCateMongo() {
        super();
    }

    public VirtualCateMongo(List<VirtualCateMongo> list) {
        setVirtualCateId(0);
        setVirtualCateDisc("ROOT");
        setFathervirtualcateid(-1);
        list.add(this);
        idMapping.clear();

        for (VirtualCateMongo virtualCateMongo : list) {
            idMapping.put(String.valueOf(virtualCateMongo.getVirtualCateId()),
                    virtualCateMongo);
        }

        for (VirtualCateMongo virtualCateMongo : idMapping.values()) {
            VirtualCateMongo parent = idMapping.get(String.valueOf(
                    virtualCateMongo.getFathervirtualcateid()));

            if (null != parent) {
                parent.getChild().add(virtualCateMongo);
            }
        }

        for (VirtualCateMongo virtualCateMongo : idMapping.values()) {
            Collections.sort(virtualCateMongo.getChild(),
                    new Comparator<VirtualCateMongo>() {
                        public int compare(VirtualCateMongo o1, VirtualCateMongo o2) {
                            if ((o1.getSort() != null) && (o2.getSort() != null)) {
                                return o1.getSort() - o2.getSort();
                            } else {
                                return 1;
                            }
                        }
                    });
        }
    }

    public Integer getVirtualCateId() {
        return virtualCateId;
    }

    public void setVirtualCateId(Integer virtualCateId) {
        this.virtualCateId = virtualCateId;
    }

    public String getVirtualCateDisc() {
        return virtualCateDisc;
    }

    public void setVirtualCateDisc(String virtualCateDisc) {
        this.virtualCateDisc = virtualCateDisc;
    }

    public Integer getFathervirtualcateid() {
        return fathervirtualcateid;
    }

    public void setFathervirtualcateid(Integer fathervirtualcateid) {
        this.fathervirtualcateid = fathervirtualcateid;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<VirtualCateMongo> getChild() {
        return child;
    }

    public void setChild(List<VirtualCateMongo> child) {
        this.child = child;
    }
}
