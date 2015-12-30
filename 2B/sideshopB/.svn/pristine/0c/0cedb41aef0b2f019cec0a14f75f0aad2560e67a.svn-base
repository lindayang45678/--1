package com.lakala.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.*;

/**
 * <p/>
 * Created by HOT.LIU on 2015/3/2.
 */
public class TerminalProduct implements Serializable {

    private static final long serialVersionUID = -7767509913185429967L;

    @JSONField(name = "a")
    private List<ChannelVirtualCat> channelVirtualCat; //终端所有虚拟类目

    @JSONField(name = "b")
    private List<TerminalChannel> terminalChannels; //终端所有商品

    public List<ChannelVirtualCat> getChannelVirtualCat() {
        return channelVirtualCat;
    }

    public void setChannelVirtualCat(List<ChannelVirtualCat> channelVirtualCat) {
        this.channelVirtualCat = (channelVirtualCat == null ? new ArrayList<ChannelVirtualCat>() : channelVirtualCat);
    }

    public List<TerminalChannel> getTerminalChannels() {
        return terminalChannels;
    }

    public void setTerminalChannels(List<TerminalChannel> terminalChannels) {
        this.terminalChannels = (terminalChannels == null ? new ArrayList<TerminalChannel>() : terminalChannels);
    }

    // 遍历频道商品集合，取出虚拟类目并拼接
    public void setChannelVirtualCatByTerminalChannel() {
        // 频道和虚拟类目词典map，key为id（频道id，类目id），值为名称
        Map<String, String> idsAndNames = new HashMap<String, String>();
        // 频道对应root节点的map，值使用map是为了方便去重
        Map<String, Map<String, String>> channelAndRoot = new HashMap<String, Map<String, String>>();
        // 虚拟类目父id对应子id集合，值使用map是为了方便去重
        Map<String, Map<String, String>> parentAndChild = new HashMap<String, Map<String, String>>();
        Map<String, Integer> catSort = new HashMap<String, Integer>();
        for (int i = 0; i < terminalChannels.size(); i++) {
            // 频道下商品信息详情
            TerminalChannel terminalChannel = terminalChannels.get(i);
            List<ProductDetail> productList = terminalChannel.getProductList();
            for (int j = 0; j < productList.size(); j++) {
                ProductDetail productDetail = productList.get(j);
                String[] virtualCatIds = productDetail.getVirtualCatIds();
                String[] virtualCatNames = productDetail.getVirtualCatNames();
                Integer[] virtualCatSort = productDetail.getVirtualCatSort();
                for (int k = 0; k < virtualCatIds.length; k++) {
                    String id = virtualCatIds[k];
                    String name = virtualCatNames[k];
                    Integer sort = virtualCatSort[k];
                    idsAndNames.put("cat" + id, name);
                    catSort.put("sort" + id, sort);
                    Map<String, String> map = new HashMap<String, String>();
                    if (k == 0) {
                        // root节点
                        if (channelAndRoot.containsKey(terminalChannel.getNetChannelId())) {
                            map = channelAndRoot.get(terminalChannel.getNetChannelId());
                        }
                        map.put(id, id);
                        channelAndRoot.put(terminalChannel.getNetChannelId(), map);
                    }
                    map = new HashMap<String, String>();
                    if (parentAndChild.containsKey(id)) {
                        map = parentAndChild.get(id);
                    }
                    String child = null;
                    // 当数组越界会报异常
                    try {
                        child = virtualCatIds[k + 1];
                    } catch (Exception e) {
                    }
                    if (child != null) {
                        map.put(child, child);
                        parentAndChild.put(id, map);
                    }

                }
            }
        }
        List<ChannelVirtualCat> channelVirtualCat = new ArrayList<ChannelVirtualCat>();
        Object[] channels = channelAndRoot.keySet().toArray();
        // 组装虚拟类目json
        for (int i = 0; i < channels.length; i++) {
            String netChannelId = channels[i].toString();
            ChannelVirtualCat virtualCat = new ChannelVirtualCat();
            virtualCat.setNetChannelId(netChannelId);
            // root虚拟类目
            Map<String, String> map = channelAndRoot.get(netChannelId);
            virtualCat.setVirtualCat(this.setRoot(map, idsAndNames, parentAndChild, catSort));
            channelVirtualCat.add(virtualCat);
        }
        this.setChannelVirtualCat(channelVirtualCat);
    }

    private List<VirtualCat> setRoot(Map<String, String> map, Map<String, String> idsAndNames, Map<String, Map<String, String>> parentAndChild, Map<String, Integer> catSort) {
        List<VirtualCat> cats = new ArrayList<VirtualCat>();
        //如果需要排序，首先要把顺序和虚分类一样放在商品详情里，在遍历商品列表的时候，把顺序也放成一个map。key为分类id，value为顺序值
        //把虚分类的顺序通过id取出来。并放入新map。key为id，value为虚分类id
        //排序新map的key。然后再进行下面的for遍历。但是root值要通过新map的顺序去取
        Object[] roots = map.keySet().toArray();
        Map<Integer, String> sortIds = new HashMap<Integer, String>();
        for (int i = 0; i < roots.length; i++) {
            String root = roots[i].toString();
            sortIds.put(catSort.get("sort" + root), root);
        }
        Object[] sorts = sortIds.keySet().toArray();
        Arrays.sort(sorts);
        List<String> catIds = new ArrayList<String>();
        for (int i = sorts.length - 1; i >= 0; i--) {
            catIds.add(sortIds.get(Integer.parseInt(sorts[i] + "")));
        }


        for (int i = 0; i < catIds.size(); i++) {
            String root = catIds.get(i);
            VirtualCat cat = new VirtualCat();
            cat.setVirtualCateId(root);
            cat.setVirtualCateDisc(idsAndNames.get("cat" + root));
            // 下级目录
            Map<String, String> map2 = parentAndChild.get(root);
            if (map2 != null && map2.size() != 0)
                cat.setChild(this.setRoot(map2, idsAndNames, parentAndChild, catSort));
            cats.add(cat);
        }
        return cats;
    }

}
