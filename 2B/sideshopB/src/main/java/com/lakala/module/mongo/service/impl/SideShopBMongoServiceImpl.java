package com.lakala.module.mongo.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lakala.exception.LakalaException;
import com.lakala.mapper.r.virtualcat.VirtualCateMapper;
import com.lakala.model.ProductDetailedInformation;
import com.lakala.model.virtualcate.VirtualCateMongo;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.vo.in.SideShopBDetailInPut;
import com.lakala.module.goods.vo.in.SideShopBLeftInPut;
import com.lakala.module.goods.vo.in.SideShopBListForKeyInPut;
import com.lakala.module.goods.vo.in.SideShopBListInPut;
import com.lakala.module.goods.vo.out.*;
import com.lakala.module.mongo.service.SideShopBMongoService;
import com.lakala.module.promotion.vo.Goodsku;
import com.lakala.module.promotion.vo.in.PromotionListInPut;
import com.lakala.module.promotion.vo.out.PromotionListOutPut;
import com.lakala.module.wholesale.model.*;
import com.lakala.util.HttpUtil;
import com.lakala.util.MongoInterfaceUrl;
import com.lakala.util.ReturnMsg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by HOT.LIU on 2015/5/20.
 */
@Service
public class SideShopBMongoServiceImpl implements SideShopBMongoService {

    private static final Logger logger = Logger
            .getLogger(SideShopBMongoServiceImpl.class);

    @Autowired
    private MongoInterfaceUrl mongoInterfaceUrl;

    @Autowired
    private VirtualCateMapper virtualCateMapper_R;

    @Autowired
    private com.lakala.mapper.r.sdbmediastatistics.SdbMediaStatisticsMapper sdbMediaStatisticsMapper_R;

    @Override
    public ObjectOutput left_mongo(String psam, String channelid)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        SideShopBLeftInPut in = new SideShopBLeftInPut();
        List<VirtualCateMongo> cateMongoList = new ArrayList<VirtualCateMongo>();
        try {
            Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
                    .selectSdbMediaStatisticsByPsam(psam);
            if (sdbMap == null && sdbMap.isEmpty()) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端所对应的网点信息！");
                return data;
            }

            in.setPsam(psam);
            in.setChannelid(Long.parseLong(channelid));
            in.setAllArea(true);
            in.setNet((String) sdbMap.get("ec_net_no"));
            in.setRegion((String) sdbMap.get("city_area_no"));
            in.setProvince((String) sdbMap.get("prov_no"));
            in.setCity((String) sdbMap.get("city_no"));
            in.setSection((String) sdbMap.get("community_no"));
            in.setExpandChannel((String) sdbMap.get("agentId"));
            in.setExpandType(String.valueOf(sdbMap.get("channelType")));
            in.setPlatorself(Constant.PRODUCT_PLATOR_SELF_FLAG);

            String url = mongoInterfaceUrl.getLeft();
            String inStr = JSON.toJSONString(in);
            String json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);
            if (output == null) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端下所属的分类！");
                return data;
            }
            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() == null
                        || "".equals(output.get_ReturnData())) {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到该终端下所属的分类！");
                    return data;
                }
                List<SideShopBLeftOutPut> list = JSON
                        .parseArray(output.get_ReturnData().toString(),
                                SideShopBLeftOutPut.class);
                Map<Integer, Object> map = new HashMap<Integer, Object>();
                for (SideShopBLeftOutPut outPut : list) {
                    for (LeftGoodsSKU leftGoodsSKU : outPut.getSkus()) {
                        if (leftGoodsSKU.getVc() != null
                                && leftGoodsSKU.getVc().size() > 0) {
                            for (LeftVirtualCate leftVirtualCate : leftGoodsSKU
                                    .getVc()) {
                                List idList = new ArrayList(leftVirtualCate
                                        .getIds().values());
                                for (Object obj : idList) {
                                    for (Integer id : (Integer[]) obj) {
                                        map.put(id, id);
                                    }
                                }
                            }
                        }
                    }
                }
                List idList = new ArrayList(map.values());
                List<VirtualCateMongo> virtualCateMongoList = virtualCateMapper_R
                        .findByIds(idList, channelid);
                VirtualCateMongo virtualCateMongo = new VirtualCateMongo(
                        virtualCateMongoList);
                String childJson = JSON.toJSONString(virtualCateMongo
                        .getChild());
                if (childJson.indexOf("本市批发") > -1) {
                    childJson = childJson.replaceAll("本市批发",
                            (String) sdbMap.get("city") + "批发");
                }
                if (childJson.indexOf("本省") > -1) {
                    childJson = childJson.replaceAll("本省批发",
                            (String) sdbMap.get("prov") + "批发");
                }
                if (childJson.indexOf("本区") > -1) {
                    childJson = childJson.replaceAll("本区批发",
                            (String) sdbMap.get("city_area") + "批发");
                }
                cateMongoList = JSON.parseArray(childJson,
                        VirtualCateMongo.class);
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询左侧菜单数据出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
        data.set_ReturnData(cateMongoList);
        return data;
    }

    @Override
    public ObjectOutput list_mongo(String psam, String channelid,
                                   String virtualcatid, String searchparm, Integer pageIndex,
                                   Integer pageSize, Integer level, Integer platorself)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        SideShopBListInPut in = new SideShopBListInPut();
        PromotionListInPut pin = new PromotionListInPut();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();
        List<ProductDetailedInformation> returnList = new ArrayList<ProductDetailedInformation>();
        OutListModel outListModel = new OutListModel();
        int count = 0;
        if (StringUtils.isEmpty(psam) || StringUtils.isEmpty(channelid)) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }
        try {
            Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
                    .selectSdbMediaStatisticsByPsam(psam);
            if (sdbMap == null && sdbMap.isEmpty()) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端所对应的网点信息！");
                return data;
            }
            in.setPsam(psam);
            in.setChannelid(Long.parseLong(channelid));
            if (virtualcatid != null && !"".equals(virtualcatid)
                    && !"all".equals(virtualcatid)) {
                in.setVirtualcatid(Long.parseLong(virtualcatid));
                in.setLevel(level);
            }
            if (searchparm != null && !"".equals(searchparm)) {
                in.setSearchparm(searchparm);
            }
            if (pageIndex != null) {
                in.setPageIndex(pageIndex);
            }
            if (pageSize != null) {
                in.setPageSize(pageSize);
            }
            in.setAllArea(true);
            in.setNet((String) sdbMap.get("ec_net_no"));
            in.setRegion((String) sdbMap.get("city_area_no"));
            in.setProvince((String) sdbMap.get("prov_no"));
            in.setCity((String) sdbMap.get("city_no"));
            in.setSection((String) sdbMap.get("community_no"));
            in.setExpandChannel((String) sdbMap.get("agentId"));
            in.setExpandType(String.valueOf(sdbMap.get("channelType")));
            in.setPlatorself(platorself);

            String url = mongoInterfaceUrl.getList();
            String inStr = JSON.toJSONString(in);
            String json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);
            url = mongoInterfaceUrl.getCount();
            json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput countoutput = JSON.parseObject(json,
                    ObjectOutput.class);

            //mongo促销列表
            pin.setSource(Constant.PROMOTION_FLAG_RELEASE_CHANNEL_FLAG);
            pin.setChannel(Integer.parseInt(channelid));
            pin.setAllArea("1");
            pin.setPsam(psam);
            pin.setProvince((String) sdbMap.get("prov_no"));
            pin.setCity((String) sdbMap.get("city_no"));
            pin.setRegion((String) sdbMap.get("city_area_no"));
            pin.setSection((String) sdbMap.get("community_no"));
            pin.setNet((String) sdbMap.get("ec_net_no"));
            pin.setExpandChannel((String) sdbMap.get("agentId"));
            pin.setExpandType(String.valueOf(sdbMap.get("channelType")));
            pin.setType(Constant.PROMOTION_FLAG_STRAIGHT_DOWN_FLAG);

            url = mongoInterfaceUrl.getPromotion();
            inStr = JSON.toJSONString(pin);
            json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput promotionoutput = JSON.parseObject(json, ObjectOutput.class);

            if (ReturnMsg.CODE_SUCCESS.equals(countoutput.get_ReturnCode())) {
                if (countoutput.get_ReturnData() != null
                        && !"".equals(countoutput.get_ReturnData())) {
                    count = Integer.parseInt(countoutput.get_ReturnData()
                            .toString());
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(outListModel);
                    return data;
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数量出错！");
                return data;
            }

            if (!ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询促销信息出错！");
                return data;
            }

            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
                        && !"".equals(output.get_ReturnData())) {
                    List<SideShopBDetailOutPut> list = JSON.parseArray(output
                                    .get_ReturnData().toString(),
                            SideShopBDetailOutPut.class);
                    for (SideShopBDetailOutPut detailOutPut : list) {
                        if (detailOutPut.getSkus() != null
                                && !detailOutPut.getSkus().isEmpty()) {
                            int skuSize = detailOutPut.getSkus().size();
                            for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
                                ProductDetailedInformation detailedInformation = new ProductDetailedInformation();
                                Double saleprice = 0.00;
                                if (goodsSKU.getVc() != null
                                        && goodsSKU.getVc().size() > 0) {
                                    for (ListVirtualCate virtualCate : goodsSKU
                                            .getVc()) {
                                        if (virtualCate.getPsam() != null
                                                && !virtualCate.getPsam()
                                                .isEmpty()) {
                                            if (virtualCate.getPsam().get(
                                                    psam.toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getPsam()
                                                        .get(psam.toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getNet() != null
                                                && !virtualCate.getNet()
                                                .isEmpty()) {
                                            if (virtualCate.getNet().get(
                                                    ((String) sdbMap
                                                            .get("ec_net_no"))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getNet()
                                                        .get(((String) sdbMap
                                                                .get("ec_net_no"))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        //社区
                                        if (virtualCate.getExpandChannel() != null
                                                && !virtualCate.getExpandChannel()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandChannel().get(
                                                    (String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("community_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandChannel()
                                                        .get((String.valueOf(sdbMap
                                                                .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("community_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandType() != null
                                                && !virtualCate.getExpandType()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandType().get(
                                                    (String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("community_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandType()
                                                        .get((String.valueOf(sdbMap
                                                                .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("community_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        //区
                                        if (virtualCate.getExpandChannel() != null
                                                && !virtualCate.getExpandChannel()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandChannel().get(
                                                    (String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_area_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandChannel()
                                                        .get((String.valueOf(sdbMap
                                                                .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("city_area_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandType() != null
                                                && !virtualCate.getExpandType()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandType().get(
                                                    (String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_area_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandType()
                                                        .get((String.valueOf(sdbMap
                                                                .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("city_area_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        //市
                                        if (virtualCate.getExpandChannel() != null
                                                && !virtualCate.getExpandChannel()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandChannel().get(
                                                    (String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandChannel()
                                                        .get((String.valueOf(sdbMap
                                                                .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("city_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandType() != null
                                                && !virtualCate.getExpandType()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandType().get(
                                                    (String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandType()
                                                        .get((String.valueOf(sdbMap
                                                                .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("city_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        //省
                                        if (virtualCate.getExpandChannel() != null
                                                && !virtualCate.getExpandChannel()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandChannel().get(
                                                    (String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("prov_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandChannel()
                                                        .get((String.valueOf(sdbMap
                                                                .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("prov_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandType() != null
                                                && !virtualCate.getExpandType()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandType().get(
                                                    (String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("prov_no")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandType()
                                                        .get((String.valueOf(sdbMap
                                                                .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                                .get("prov_no")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        //全国
                                        if (virtualCate.getExpandChannel() != null
                                                && !virtualCate.getExpandChannel()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandChannel().get(
                                                    (String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA)
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandChannel()
                                                        .get((String.valueOf(sdbMap
                                                                .get("agentId")) + Constant.SPLIT_EXPAND_AREA)
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandType() != null
                                                && !virtualCate.getExpandType()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandType().get(
                                                    (String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA)
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandType()
                                                        .get((String.valueOf(sdbMap
                                                                .get("channelType")) + Constant.SPLIT_EXPAND_AREA)
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                    }
                                }
                                if (saleprice == 0) {
                                    skuSize = skuSize - 1;
                                    continue;
                                }

                                if (ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                                    if (promotionoutput.get_ReturnData() != null
                                            && !"".equals(promotionoutput.get_ReturnData())) {
                                        List<PromotionListOutPut> listOutPuts = JSON.parseArray(promotionoutput
                                                        .get_ReturnData().toString(),
                                                PromotionListOutPut.class);
                                        for (PromotionListOutPut outPut : listOutPuts) {
                                            if (outPut.getGoods() != null) {
                                                goodsSku:
                                                for (Goodsku goodsku : outPut.getGoods()) {
                                                    if (outPut.getData() != null) {
                                                        if (outPut.getData().get("10") != null || outPut.getData().get("9") != null) {
                                                            if (outPut.getData().get("10") != null) {
                                                                if (outPut.getData().get("10").contains(String.valueOf(sdbMap.get("agentId")))) {
                                                                    if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                                        if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                            detailedInformation.setType(outPut.getType());
                                                                        }
                                                                        detailedInformation.setPromotionPrice(new BigDecimal(saleprice));
                                                                        detailedInformation.setStartTime(outPut.getStartTime());
                                                                        detailedInformation.setEndTime(outPut.getEndTime());
                                                                        if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                                            if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                                detailedInformation.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                                                detailedInformation.setType(outPut.getType());
                                                                            }
                                                                            if (goodsku.getCount() > 0) {
                                                                                detailedInformation.setPurchaseCount(goodsku.getCount());
                                                                            }
                                                                            break goodsSku;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (outPut.getData().get("9") != null) {
                                                                if (outPut.getData().get("9").contains(String.valueOf(sdbMap.get("channelType")))) {
                                                                    if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                                        if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                            detailedInformation.setType(outPut.getType());
                                                                        }
                                                                        detailedInformation.setPromotionPrice(new BigDecimal(saleprice));
                                                                        detailedInformation.setStartTime(outPut.getStartTime());
                                                                        detailedInformation.setEndTime(outPut.getEndTime());
                                                                        if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                                            if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                                detailedInformation.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                                            }
                                                                            if (goodsku.getCount() > 0) {
                                                                                detailedInformation.setPurchaseCount(goodsku.getCount());
                                                                            }
                                                                            break goodsSku;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                                if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                    detailedInformation.setType(outPut.getType());
                                                                }
                                                                detailedInformation.setPromotionPrice(new BigDecimal(saleprice));
                                                                detailedInformation.setStartTime(outPut.getStartTime());
                                                                detailedInformation.setEndTime(outPut.getEndTime());
                                                                if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                                    if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                        detailedInformation.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                                    }
                                                                    if (goodsku.getCount() > 0) {
                                                                        detailedInformation.setPurchaseCount(goodsku.getCount());
                                                                    }
                                                                    break goodsSku;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                detailedInformation.setTgoodskuinfoid(String
                                        .valueOf(goodsSKU.getSkuGoodsId()));
                                detailedInformation.setSaleprice_o2o(saleprice
                                        .toString());
                                detailedInformation.setMarketprice(String
                                        .valueOf(goodsSKU.getMarketPrice()));
                                detailedInformation.setMinPrice(saleprice
                                        .toString());
                                //判断如果是多sku，只有其中只部分sku参加直降，那么就需要把没有参加直降的sku的直降价格赋为销售价
                                if (String.valueOf(Constant.PROMOTION_FLAG_STRAIGHT_DOWN_FLAG).equals(String.valueOf(detailedInformation.getType()))) {
                                    if (detailedInformation.getPromotionPrice().compareTo(new BigDecimal("0")) == 0) {
                                        detailedInformation.setMinPrice(saleprice.toString());
                                        detailedInformation.setPromotionPrice(new BigDecimal(saleprice));
                                        detailedInformation.setSaleprice_o2o(detailedInformation.getMarketprice());
                                    } else {//为了列表显示最低价格的sku需要把没有多sku商品，没有参加直降的销售价格改为直降价，为了现在最低价
                                        detailedInformation.setMinPrice(detailedInformation.getPromotionPrice()
                                                .toString());
                                    }
                                }
                                detailedInformation.setTgoodinfoid(String
                                        .valueOf(detailOutPut.get_id()));
                                detailedInformation
                                        .setGoodname(detailOutPut
                                                .getPublishGoodsName() != null ? detailOutPut
                                                .getPublishGoodsName()
                                                : detailOutPut.getGoodsName());
                                detailedInformation
                                        .setGoodbigpic(org.apache.commons.lang.StringUtils
                                                .join(detailOutPut.getPics()
                                                        .toArray(), ","));
                                detailedInformation
                                        .setGoodintroduce(detailOutPut
                                                .getGoodIntroduce());
                                detailedInformation.setStore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSkuStock())));
                                detailedInformation.setSoldstore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSoldSkuStock())));
                                detailedInformation
                                        .setIsforeigngoods(detailOutPut
                                                .getIsForeigngoods());
                                detailedInformation
                                        .setTempstoregoodsflag(detailOutPut
                                                .getTempStoreGoodsFlag());
                                detailedInformation.setSort(detailOutPut
                                        .getGoodsSort() == null ? 0
                                        : detailOutPut.getGoodsSort());
                                detailedInformation.setPlatorself(detailOutPut
                                        .getPlatorself());
                                detailedInformation
                                        .setDistributionFlag(detailOutPut
                                                .getDistributionFlag());
                                detailedInformation.setDeductPercent(goodsSKU
                                        .getDeductPercent());
                                detailedInformation
                                        .setDistributeProfitBase(goodsSKU
                                                .getDistributeProfitBase());
                                detailedInformation.setBalancePrice(goodsSKU
                                        .getBalancePrice());
                                detailedInformation.setIsfreshfood(detailOutPut
                                        .getIsfreshfood());
                                detailedInformation
                                        .settGoodInfoPoolId(detailOutPut
                                                .gettGoodInfoPoolId());
                                detailedInformation.setSaleflag(goodsSKU
                                        .getUlf() == 208 ? 0 : 1);
                                if (goodsSKU.getSkuStock() > 0) {
                                    detailedInformation.setIssoldout(1);
                                } else {
                                    detailedInformation.setIssoldout(0);
                                }
                                if (skuSize > 1) {
                                    detailedInformation.setIsmoresku(1);
                                } else {
                                    detailedInformation.setIsmoresku(0);
                                }
                                detailedInformation
                                        .setOnekeyupload((detailOutPut
                                                .gettGoodInfoPoolId() != null && detailOutPut
                                                .getPlatorself() == 452) ? 1
                                                : 0);
                                detailedInformation
                                        .setFreshgoodsflag(detailOutPut
                                                .getIsfreshfood());
                                if (idMapping.get(detailedInformation
                                        .getTgoodinfoid()) == null) {
                                    idMapping.put(detailedInformation
                                                    .getTgoodinfoid(),
                                            detailedInformation);
                                } else {
                                    if (new BigDecimal(idMapping.get(
                                            detailedInformation
                                                    .getTgoodinfoid())
                                            .getMinPrice())
                                            .compareTo(new BigDecimal(
                                                    detailedInformation
                                                            .getMinPrice())) == 1) {
                                        idMapping.put(detailedInformation
                                                        .getTgoodinfoid(),
                                                detailedInformation);
                                    }
                                }
                            }
                        }
                    }
                    // 多个sku相同只取价格最低显示
                    returnList = new ArrayList<ProductDetailedInformation>(
                            idMapping.values());
                    Collections.sort(returnList,
                            new Comparator<ProductDetailedInformation>() {
                                public int compare(
                                        ProductDetailedInformation arg0,
                                        ProductDetailedInformation arg1) {
                                    return arg1.getSort().compareTo(
                                            arg0.getSort());
                                }
                            });
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    outListModel.setList(returnList);
                    outListModel.setCount(count);
                    data.set_ReturnData(outListModel);
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(outListModel);
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数据出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        return data;
    }

    @Override
    public ObjectOutput detail_mongo(String psam, String channelid,
                                     String goodid, String virtualcatid, Integer level)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        SideShopBDetailInPut in = new SideShopBDetailInPut();
        PromotionListInPut pin = new PromotionListInPut();
        List<SkuDetails> skuDetailsList = new ArrayList<SkuDetails>();
        ProductDetailForProductList productDetailForProductList = new ProductDetailForProductList();
        if (StringUtils.isEmpty(psam) || StringUtils.isEmpty(channelid)) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }
        try {
            Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
                    .selectSdbMediaStatisticsByPsam(psam);
            if (sdbMap == null && sdbMap.isEmpty()) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端所对应的网点信息！");
                return data;
            }

            if (virtualcatid != null && !"".equals(virtualcatid)
                    && !"all".equals(virtualcatid)) {
                in.setVirtualcatid(Long.parseLong(virtualcatid));
                in.setLevel(level);
            }
            in.setPsam(psam);
            in.setChannelid(Long.parseLong(channelid));
            in.setGoodid(Long.parseLong(goodid));
            in.setAllArea(true);
            in.setNet((String) sdbMap.get("ec_net_no"));
            in.setRegion((String) sdbMap.get("city_area_no"));
            in.setProvince((String) sdbMap.get("prov_no"));
            in.setCity((String) sdbMap.get("city_no"));
            in.setSection((String) sdbMap.get("community_no"));
            in.setExpandChannel((String) sdbMap.get("agentId"));
            in.setExpandType(String.valueOf(sdbMap.get("channelType")));

            String url = mongoInterfaceUrl.getDetail();
            String inStr = JSON.toJSONString(in);
            String json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);

            //mongo促销列表
            pin.setSource(Constant.PROMOTION_FLAG_RELEASE_CHANNEL_FLAG);
            pin.setChannel(Integer.parseInt(channelid));
            pin.setAllArea("1");
            pin.setPsam(psam);
            pin.setProvince((String) sdbMap.get("prov_no"));
            pin.setCity((String) sdbMap.get("city_no"));
            pin.setRegion((String) sdbMap.get("city_area_no"));
            pin.setSection((String) sdbMap.get("community_no"));
            pin.setNet((String) sdbMap.get("ec_net_no"));
            pin.setExpandChannel((String) sdbMap.get("agentId"));
            pin.setExpandType(String.valueOf(sdbMap.get("channelType")));
            pin.setType(Constant.PROMOTION_FLAG_STRAIGHT_DOWN_FLAG);

            url = mongoInterfaceUrl.getPromotion();
            inStr = JSON.toJSONString(pin);
            json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput promotionoutput = JSON.parseObject(json, ObjectOutput.class);

            if (!ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询促销信息出错！");
                return data;
            }

            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
                        && !"".equals(output.get_ReturnData())) {
                    SideShopBDetailOutPut detailOutPut = JSON.parseObject(
                            output.get_ReturnData().toString(),
                            SideShopBDetailOutPut.class);
                    GoodsPublishForKDB publishForKDB = new GoodsPublishForKDB();
                    publishForKDB.setTgoodinfoid(detailOutPut.get_id()
                            .intValue());
                    publishForKDB.setSupplierid(detailOutPut.getSupplierId());
                    publishForKDB.setSuppliername(detailOutPut
                            .getSupplierName());
                    publishForKDB.setGoodname(detailOutPut
                            .getPublishGoodsName() != null ? detailOutPut
                            .getPublishGoodsName() : detailOutPut
                            .getGoodsName());
                    publishForKDB.setGoodintroduce(detailOutPut
                            .getGoodIntroduce());
                    publishForKDB.setIscontractmachine(detailOutPut
                            .getIscontractmachine());
                    publishForKDB.setIsexpresstohome(detailOutPut
                            .getIsExpressToHome());
                    publishForKDB.setDistributionflag(detailOutPut
                            .getDistributionFlag());
                    publishForKDB
                            .setGoodbigpic(org.apache.commons.lang.StringUtils
                                    .join(detailOutPut.getPics().toArray(), ","));
                    publishForKDB.setIsforeigngoods(detailOutPut
                            .getIsForeigngoods());
                    publishForKDB.setTempstoregoodsflag(detailOutPut
                            .getTempStoreGoodsFlag());
                    publishForKDB.setClientServiceTel(detailOutPut.getTel());
                    publishForKDB.setReturnprovincename(detailOutPut
                            .getAddress());
                    if (detailOutPut.getSkus() != null
                            && !detailOutPut.getSkus().isEmpty()) {
                        for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
                            Double saleprice = 0.00;
                            if (goodsSKU.getVc() != null
                                    && goodsSKU.getVc().size() > 0) {
                                for (ListVirtualCate virtualCate : goodsSKU
                                        .getVc()) {
                                    if (virtualCate.getPsam() != null
                                            && !virtualCate.getPsam().isEmpty()) {
                                        if (virtualCate.getPsam().get(
                                                psam.toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getPsam().get(
                                                            psam.toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getNet() != null
                                            && !virtualCate.getNet().isEmpty()) {
                                        if (virtualCate.getNet().get(
                                                ((String) sdbMap
                                                        .get("ec_net_no"))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getNet()
                                                    .get(((String) sdbMap
                                                            .get("ec_net_no"))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    //社区
                                    if (virtualCate.getExpandChannel() != null
                                            && !virtualCate.getExpandChannel()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandChannel().get(
                                                (String.valueOf(sdbMap
                                                        .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("community_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandChannel()
                                                    .get((String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("community_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandType() != null
                                            && !virtualCate.getExpandType()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandType().get(
                                                (String.valueOf(sdbMap
                                                        .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("community_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandType()
                                                    .get((String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("community_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    //区
                                    if (virtualCate.getExpandChannel() != null
                                            && !virtualCate.getExpandChannel()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandChannel().get(
                                                (String.valueOf(sdbMap
                                                        .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("city_area_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandChannel()
                                                    .get((String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_area_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandType() != null
                                            && !virtualCate.getExpandType()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandType().get(
                                                (String.valueOf(sdbMap
                                                        .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("city_area_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandType()
                                                    .get((String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_area_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    //市
                                    if (virtualCate.getExpandChannel() != null
                                            && !virtualCate.getExpandChannel()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandChannel().get(
                                                (String.valueOf(sdbMap
                                                        .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("city_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandChannel()
                                                    .get((String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandType() != null
                                            && !virtualCate.getExpandType()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandType().get(
                                                (String.valueOf(sdbMap
                                                        .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("city_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandType()
                                                    .get((String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("city_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    //省
                                    if (virtualCate.getExpandChannel() != null
                                            && !virtualCate.getExpandChannel()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandChannel().get(
                                                (String.valueOf(sdbMap
                                                        .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("prov_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandChannel()
                                                    .get((String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("prov_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandType() != null
                                            && !virtualCate.getExpandType()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandType().get(
                                                (String.valueOf(sdbMap
                                                        .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                        .get("prov_no")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandType()
                                                    .get((String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA + ((String) sdbMap
                                                            .get("prov_no")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    //全国
                                    if (virtualCate.getExpandChannel() != null
                                            && !virtualCate.getExpandChannel()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandChannel().get(
                                                (String.valueOf(sdbMap
                                                        .get("agentId")) + Constant.SPLIT_EXPAND_AREA)
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandChannel()
                                                    .get((String.valueOf(sdbMap
                                                            .get("agentId")) + Constant.SPLIT_EXPAND_AREA)
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandType() != null
                                            && !virtualCate.getExpandType()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandType().get(
                                                (String.valueOf(sdbMap
                                                        .get("channelType")) + Constant.SPLIT_EXPAND_AREA)
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandType()
                                                    .get((String.valueOf(sdbMap
                                                            .get("channelType")) + Constant.SPLIT_EXPAND_AREA)
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                }
                                if (saleprice == 0) {
                                    continue;
                                }
                                SkuDetails skuDetails = new SkuDetails();
                                if (ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                                    if (promotionoutput.get_ReturnData() != null
                                            && !"".equals(promotionoutput.get_ReturnData())) {
                                        List<PromotionListOutPut> listOutPuts = JSON.parseArray(promotionoutput
                                                        .get_ReturnData().toString(),
                                                PromotionListOutPut.class);
                                        for (PromotionListOutPut outPut : listOutPuts) {
                                            if (outPut.getGoods() != null) {
                                                goodsSku:
                                                for (Goodsku goodsku : outPut.getGoods()) {
                                                    if (outPut.getData() != null) {
                                                        if (outPut.getData().get("10") != null || outPut.getData().get("9") != null) {
                                                            if (outPut.getData().get("10") != null) {
                                                                if (outPut.getData().get("10").contains(String.valueOf(sdbMap.get("agentId")))) {
                                                                    if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                                        if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                                            if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                                skuDetails.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                                                skuDetails.setType(outPut.getType());
                                                                            }
                                                                            if (goodsku.getCount() > 0) {
                                                                                skuDetails.setPurchaseCount(goodsku.getCount());
                                                                            }
                                                                            skuDetails.setStartTime(outPut.getStartTime());
                                                                            skuDetails.setEndTime(outPut.getEndTime());
                                                                            break goodsSku;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            if (outPut.getData().get("9") != null) {
                                                                if (outPut.getData().get("9").contains(String.valueOf(sdbMap.get("channelType")))) {
                                                                    if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                                        if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                                            if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                                skuDetails.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                                                skuDetails.setType(outPut.getType());
                                                                            }
                                                                            if (goodsku.getCount() > 0) {
                                                                                skuDetails.setPurchaseCount(goodsku.getCount());
                                                                            }
                                                                            skuDetails.setStartTime(outPut.getStartTime());
                                                                            skuDetails.setEndTime(outPut.getEndTime());
                                                                            break goodsSku;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                                if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                                    if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                        skuDetails.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                                        skuDetails.setType(outPut.getType());
                                                                    }
                                                                    if (goodsku.getCount() > 0) {
                                                                        skuDetails.setPurchaseCount(goodsku.getCount());
                                                                    }
                                                                    skuDetails.setStartTime(outPut.getStartTime());
                                                                    skuDetails.setEndTime(outPut.getEndTime());
                                                                    break goodsSku;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                                skuDetails.setMarketPrice(String
                                        .valueOf(goodsSKU.getMarketPrice()));
                                skuDetails.setSalePrice(saleprice.toString());
                                skuDetails.setSkuFrontDisNameStr(goodsSKU
                                        .getSkuNames());
                                skuDetails.setSkuIdStr(goodsSKU.getSkuIds());
                                skuDetails.setSoldStore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSoldSkuStock())));
                                skuDetails.setStore(Double.parseDouble(String
                                        .valueOf(goodsSKU.getSkuStock())));
                                skuDetails.setTGoodInfoId(detailOutPut.get_id()
                                        .intValue());
                                skuDetails.setTGoodSkuInfoId(goodsSKU
                                        .getSkuGoodsId().intValue());
                                skuDetailsList.add(skuDetails);
                            }
                        }
                    }
                    productDetailForProductList.setGoods(publishForKDB);
                    productDetailForProductList
                            .setGoodsSkuO2OList((SkuDetails[]) skuDetailsList
                                    .toArray(new SkuDetails[skuDetailsList
                                            .size()]));
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    data.set_ReturnData(productDetailForProductList);
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(data);
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数据出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        return data;
    }

    @Override
    public ObjectOutput shopgoodslist(SideShopBListForKeyInPut inp)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        List<SkuDetails> skuDetailsList = new ArrayList<SkuDetails>();
        ProductDetailForProductList productDetailForProductList = new ProductDetailForProductList();
        List<ProductDetailedInformation> returnList = new ArrayList<ProductDetailedInformation>();
        OutListModel outListModel = new OutListModel();
        SideShopBListInPut in = new SideShopBListInPut();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();
        int count = 0;
        try {

            String url = mongoInterfaceUrl.getSideshop_goodslist();
            String inStr = JSON.toJSONString(inp);

            System.out.println("mongo请求参数=====================" + inStr);

            String json = HttpUtil.httpPostConnetion(url, inStr);

            System.out.println("mongo返回值=====================" + json);

            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);
            ObjectOutput countoutput = JSON.parseObject(json,
                    ObjectOutput.class);
            if (ReturnMsg.CODE_SUCCESS.equals(countoutput.get_ReturnCode())) {

            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数量出错！");
                return data;
            }


            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
                        && !"".equals(output.get_ReturnData())) {
//					List<SideShopBDetailOutPut> list = JSONArray.parseArray(output
//							.get_ReturnData().toString(),
//							SideShopBDetailOutPut.class);
                    Gson gson = new Gson();
                    List<SideShopBDetailOutPut> list = gson.fromJson(output.get_ReturnData().toString(),
                            new TypeToken<List<SideShopBDetailOutPut>>() {
                            }.getType());

                    for (SideShopBDetailOutPut detailOutPut : list) {
                        if (detailOutPut.getSkus() != null
                                && !detailOutPut.getSkus().isEmpty()) {
                            for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
                                ProductDetailedInformation detailedInformation = new ProductDetailedInformation();
                                Double saleprice = 0.00;
                                /*
                                 * if (saleprice == 0) { break; }
								 */
                                saleprice = goodsSKU.getVc().get(0).getPsam().get(inp.getPsam().toUpperCase());
                                detailedInformation.setTgoodskuinfoid(String
                                        .valueOf(goodsSKU.getSkuGoodsId()));
                                detailedInformation.setSaleprice_o2o(saleprice
                                        .toString());
                                detailedInformation.setTgoodinfoid(String
                                        .valueOf(detailOutPut.get_id()));
                                detailedInformation
                                        .setGoodname(detailOutPut
                                                .getPublishGoodsName() != null ? detailOutPut
                                                .getPublishGoodsName()
                                                : detailOutPut.getGoodsName());
                                detailedInformation.setMarketprice(String
                                        .valueOf(goodsSKU.getMarketPrice()));
                                detailedInformation
                                        .setGoodbigpic(org.apache.commons.lang.StringUtils
                                                .join(detailOutPut.getPics()
                                                        .toArray(), ";"));
                                detailedInformation
                                        .setGoodintroduce(detailOutPut
                                                .getGoodIntroduce());
                                detailedInformation.setStore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSkuStock())));
                                detailedInformation.setSoldstore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSoldSkuStock())));
                                detailedInformation
                                        .setIsforeigngoods(detailOutPut
                                                .getIsForeigngoods());
                                detailedInformation
                                        .setTempstoregoodsflag(detailOutPut
                                                .getTempStoreGoodsFlag());
                                detailedInformation.setSort(detailOutPut
                                        .getGoodsSort() == null ? 0
                                        : detailOutPut.getGoodsSort());
                                detailedInformation.setPlatorself(detailOutPut
                                        .getPlatorself());
                                detailedInformation
                                        .setDistributionFlag(detailOutPut
                                                .getDistributionFlag());
                                detailedInformation.setDeductPercent(goodsSKU
                                        .getDeductPercent());
                                detailedInformation
                                        .setDistributeProfitBase(goodsSKU
                                                .getDistributeProfitBase());
                                detailedInformation.setBalancePrice(goodsSKU
                                        .getBalancePrice());
                                detailedInformation.setIsfreshfood(detailOutPut
                                        .getIsfreshfood());

                                detailedInformation.setFreshgoodsflag(detailOutPut
                                        .getIsfreshfood());

                                if (null != detailedInformation.gettGoodInfoPoolId()
                                        && detailedInformation.gettGoodInfoPoolId() > 0) {
                                    detailedInformation.setOnekeyupload(1);
                                } else {
                                    detailedInformation.setOnekeyupload(0);
                                }
                                detailedInformation
                                        .settGoodInfoPoolId(detailOutPut
                                                .gettGoodInfoPoolId());
                                detailedInformation.setSaleflag(goodsSKU
                                        .getUlf() == 208 && goodsSKU.getGf() == 197 ? 0 : 1);
                                if (goodsSKU.getSkuStock() > 0) {
                                    detailedInformation.setIssoldout(1);
                                } else {
                                    detailedInformation.setIssoldout(0);
                                }
                                if (detailOutPut.getSkus().size() > 1) {
                                    detailedInformation.setIsmoresku(1);
                                } else {
                                    detailedInformation.setIsmoresku(0);
                                }
                                detailedInformation
                                        .setOnekeyupload((detailOutPut
                                                .gettGoodInfoPoolId() != null && detailOutPut
                                                .getPlatorself() == 452) ? 1
                                                : 0);
                                detailedInformation
                                        .setFreshgoodsflag(detailOutPut
                                                .getIsfreshfood());
                                if (idMapping.get(detailedInformation
                                        .getTgoodinfoid()) == null) {
                                    idMapping.put(detailedInformation
                                                    .getTgoodinfoid(),
                                            detailedInformation);
                                } else {
                                    if (new BigDecimal(idMapping.get(
                                            detailedInformation
                                                    .getTgoodinfoid())
                                            .getSaleprice_o2o())
                                            .compareTo(new BigDecimal(
                                                    detailedInformation
                                                            .getSaleprice_o2o())) == 1) {
                                        idMapping.put(detailedInformation
                                                        .getTgoodinfoid(),
                                                detailedInformation);
                                    }
                                }
                            }
                        }
                    }
                    // 多个sku相同只取价格最低显示
                    returnList = new ArrayList<ProductDetailedInformation>(
                            idMapping.values());
                    Collections.sort(returnList,
                            new Comparator<ProductDetailedInformation>() {
                                public int compare(
                                        ProductDetailedInformation arg0,
                                        ProductDetailedInformation arg1) {
                                    return arg1.getSort().compareTo(
                                            arg0.getSort());
                                }
                            });
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    System.out.println("对象转换后返回值=====================" + JSON.toJSONString(returnList));
                    outListModel.setList(returnList);
                    outListModel.setCount(count);
                    data.set_ReturnData(outListModel);
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(outListModel);
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数据出错！");
            }


			/*if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
						&& !"".equals(output.get_ReturnData())) {
					//List<SideShopBDetailOutPut> detailOutPutlist = (List<SideShopBDetailOutPut>) output.get_ReturnData();
					List<SideShopBDetailOutPut> detailOutPutlist = JSON.parseArray(output.get_ReturnData().toString(),SideShopBDetailOutPut.class);
					if(detailOutPutlist != null && detailOutPutlist.size() > 0){
						for(SideShopBDetailOutPut detailOutPut : detailOutPutlist){
							GoodsPublishForKDB publishForKDB = new GoodsPublishForKDB();
							publishForKDB.setTgoodinfoid(detailOutPut.get_id()
									.intValue());
							publishForKDB.setSupplierid(detailOutPut.getSupplierId());
							publishForKDB.setSuppliername(detailOutPut
									.getSupplierName());
							publishForKDB.setGoodname(detailOutPut
									.getPublishGoodsName() != null ? detailOutPut
									.getPublishGoodsName() : detailOutPut
									.getGoodsName());
							publishForKDB.setGoodintroduce(detailOutPut
									.getGoodIntroduce());
							publishForKDB.setIscontractmachine(detailOutPut
									.getIscontractmachine());
							publishForKDB.setIsexpresstohome(detailOutPut
									.getIsExpressToHome());
							publishForKDB.setDistributionflag(detailOutPut
									.getDistributionFlag());
							publishForKDB
									.setGoodbigpic(org.apache.commons.lang.StringUtils
											.join(detailOutPut.getPics().toArray(), ","));
							publishForKDB.setIsforeigngoods(detailOutPut
									.getIsForeigngoods());
							publishForKDB.setTempstoregoodsflag(detailOutPut
									.getTempStoreGoodsFlag());
							publishForKDB.setClientServiceTel(detailOutPut.getTel());
							publishForKDB.setReturnprovincename(detailOutPut
									.getAddress());
							if (detailOutPut.getSkus() != null
									&& !detailOutPut.getSkus().isEmpty()) {
								for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
									Double saleprice = 0.00;
									if (goodsSKU.getVc() != null
											&& goodsSKU.getVc().size() > 0) {

										SkuDetails skuDetails = new SkuDetails();
										skuDetails.setMarketPrice(String
												.valueOf(goodsSKU.getMarketPrice()));
										skuDetails.setSalePrice(saleprice.toString());
										skuDetails.setSkuFrontDisNameStr(goodsSKU
												.getSkuNames());
										skuDetails.setSkuIdStr(goodsSKU.getSkuIds());
										skuDetails.setSoldStore(Double
												.parseDouble(String.valueOf(goodsSKU
														.getSoldSkuStock())));
										skuDetails.setStore(Double.parseDouble(String
												.valueOf(goodsSKU.getSkuStock())));
										skuDetails.setTGoodInfoId(detailOutPut.get_id()
												.intValue());
										skuDetails.setTGoodSkuInfoId(goodsSKU
												.getSkuGoodsId().intValue());
										skuDetailsList.add(skuDetails);
									}
								}
							}
							productDetailForProductList.setGoods(publishForKDB);
							productDetailForProductList
									.setGoodsSkuO2OList((SkuDetails[]) skuDetailsList
											.toArray(new SkuDetails[skuDetailsList
													.size()]));
						}

					}

					data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
					data.set_ReturnData(productDetailForProductList);
				} else {
					data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
					data.set_ReturnMsg("没有找到商品！");
					data.set_ReturnData(data);
				}

			} else {
				data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
				data.set_ReturnMsg("从mongo查询商品数据出错！");
			}*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        return data;
    }

    /*@Override
    public ObjectOutput left_mongo(String psam, String channelid)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        SideShopBLeftInPut in = new SideShopBLeftInPut();
        List<VirtualCateMongo> cateMongoList = new ArrayList<VirtualCateMongo>();
        try {
            Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
                    .selectSdbMediaStatisticsByPsam(psam);
            if (sdbMap == null && sdbMap.isEmpty()) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端所对应的网点信息！");
                return data;
            }

            in.setPsam(psam);
            in.setChannelid(Long.parseLong(channelid));
            in.setAllArea(true);
            in.setNet((String) sdbMap.get("ec_net_no"));
            in.setRegion((String) sdbMap.get("city_area_no"));
            in.setProvince((String) sdbMap.get("prov_no"));
            in.setCity((String) sdbMap.get("city_no"));
            in.setSection((String) sdbMap.get("community_no"));
            in.setExpandChannel((String) sdbMap.get("agentId"));
            in.setExpandType(String.valueOf(sdbMap.get("channelType")));
            in.setPlatorself(Constant.PRODUCT_PLATOR_SELF_FLAG);

            String url = mongoInterfaceUrl.getLeft();
            String inStr = JSON.toJSONString(in);
            String json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);
            if (output == null) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端下所属的分类！");
                return data;
            }
            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() == null
                        || "".equals(output.get_ReturnData())) {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到该终端下所属的分类！");
                    return data;
                }
                List<SideShopBLeftOutPut> list = JSON
                        .parseArray(output.get_ReturnData().toString(),
                                SideShopBLeftOutPut.class);
                Map<Integer, Object> map = new HashMap<Integer, Object>();
                for (SideShopBLeftOutPut outPut : list) {
                    for (LeftGoodsSKU leftGoodsSKU : outPut.getSkus()) {
                        if (leftGoodsSKU.getVc() != null
                                && leftGoodsSKU.getVc().size() > 0) {
                            for (LeftVirtualCate leftVirtualCate : leftGoodsSKU
                                    .getVc()) {
                                List idList = new ArrayList(leftVirtualCate
                                        .getIds().values());
                                for (Object obj : idList) {
                                    for (Integer id : (Integer[]) obj) {
                                        map.put(id, id);
                                    }
                                }
                            }
                        }
                    }
                }
                List idList = new ArrayList(map.values());
                List<VirtualCateMongo> virtualCateMongoList = virtualCateMapper_R
                        .findByIds(idList, channelid);
                VirtualCateMongo virtualCateMongo = new VirtualCateMongo(
                        virtualCateMongoList);
                String childJson = JSON.toJSONString(virtualCateMongo
                        .getChild());
                if (childJson.indexOf("本市批发") > -1) {
                    childJson = childJson.replaceAll("本市批发",
                            (String) sdbMap.get("city") + "批发");
                }
                if (childJson.indexOf("本省") > -1) {
                    childJson = childJson.replaceAll("本省批发",
                            (String) sdbMap.get("prov") + "批发");
                }
                if (childJson.indexOf("本区") > -1) {
                    childJson = childJson.replaceAll("本区批发",
                            (String) sdbMap.get("city_area") + "批发");
                }
                cateMongoList = JSON.parseArray(childJson,
                        VirtualCateMongo.class);
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询左侧菜单数据出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
        data.set_ReturnData(cateMongoList);
        return data;
    }

    @Override
    public ObjectOutput list_mongo(String psam, String channelid,
                                   String virtualcatid, String searchparm, Integer pageIndex,
                                   Integer pageSize, Integer level, Integer platorself)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        SideShopBListInPut in = new SideShopBListInPut();
        PromotionListInPut pin = new PromotionListInPut();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();
        List<ProductDetailedInformation> returnList = new ArrayList<ProductDetailedInformation>();
        OutListModel outListModel = new OutListModel();
        int count = 0;
        if (StringUtils.isEmpty(psam) || StringUtils.isEmpty(channelid)) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }
        try {
            Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
                    .selectSdbMediaStatisticsByPsam(psam);
            if (sdbMap == null && sdbMap.isEmpty()) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端所对应的网点信息！");
                return data;
            }
            in.setPsam(psam);
            in.setChannelid(Long.parseLong(channelid));
            if (virtualcatid != null && !"".equals(virtualcatid)
                    && !"all".equals(virtualcatid)) {
                in.setVirtualcatid(Long.parseLong(virtualcatid));
                in.setLevel(level);
            }
            if (searchparm != null && !"".equals(searchparm)) {
                in.setSearchparm(searchparm);
            }
            if (pageIndex != null) {
                in.setPageIndex(pageIndex);
            }
            if (pageSize != null) {
                in.setPageSize(pageSize);
            }
            in.setAllArea(true);
            in.setNet((String) sdbMap.get("ec_net_no"));
            in.setRegion((String) sdbMap.get("city_area_no"));
            in.setProvince((String) sdbMap.get("prov_no"));
            in.setCity((String) sdbMap.get("city_no"));
            in.setSection((String) sdbMap.get("community_no"));
            in.setExpandChannel((String) sdbMap.get("agentId"));
            in.setExpandType(String.valueOf(sdbMap.get("channelType")));
            in.setPlatorself(platorself);

            String url = mongoInterfaceUrl.getList();
            String inStr = JSON.toJSONString(in);
            String json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);
            url = mongoInterfaceUrl.getCount();
            json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput countoutput = JSON.parseObject(json,
                    ObjectOutput.class);

            //mongo促销列表
            pin.setSource(Constant.PROMOTION_FLAG_RELEASE_CHANNEL_FLAG);
            pin.setChannel(Integer.parseInt(channelid));
            pin.setAllArea("1");
            pin.setPsam(psam);
            pin.setProvince((String) sdbMap.get("prov_no"));
            pin.setCity((String) sdbMap.get("city_no"));
            pin.setRegion((String) sdbMap.get("city_area_no"));
            pin.setSection((String) sdbMap.get("community_no"));
            pin.setNet((String) sdbMap.get("ec_net_no"));
            pin.setExpandChannel((String) sdbMap.get("agentId"));
            pin.setExpandType(String.valueOf(sdbMap.get("channelType")));
            pin.setType(Constant.PROMOTION_FLAG_STRAIGHT_DOWN_FLAG);

            url = mongoInterfaceUrl.getPromotion();
            inStr = JSON.toJSONString(pin);
            json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput promotionoutput = JSON.parseObject(json, ObjectOutput.class);

            if (ReturnMsg.CODE_SUCCESS.equals(countoutput.get_ReturnCode())) {
                if (countoutput.get_ReturnData() != null
                        && !"".equals(countoutput.get_ReturnData())) {
                    count = Integer.parseInt(countoutput.get_ReturnData()
                            .toString());
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(outListModel);
                    return data;
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数量出错！");
                return data;
            }

            if (!ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询促销信息出错！");
                return data;
            }

            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
                        && !"".equals(output.get_ReturnData())) {
                    List<SideShopBDetailOutPut> list = JSON.parseArray(output
                                    .get_ReturnData().toString(),
                            SideShopBDetailOutPut.class);
                    for (SideShopBDetailOutPut detailOutPut : list) {
                        if (detailOutPut.getSkus() != null
                                && !detailOutPut.getSkus().isEmpty()) {
                            int skuSize = detailOutPut.getSkus().size();
                            for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
                                ProductDetailedInformation detailedInformation = new ProductDetailedInformation();
                                Double saleprice = 0.00;
                                if (goodsSKU.getVc() != null
                                        && goodsSKU.getVc().size() > 0) {
                                    for (ListVirtualCate virtualCate : goodsSKU
                                            .getVc()) {
                                        if (virtualCate.getPsam() != null
                                                && !virtualCate.getPsam()
                                                .isEmpty()) {
                                            if (virtualCate.getPsam().get(
                                                    psam.toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getPsam()
                                                        .get(psam.toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getNet() != null
                                                && !virtualCate.getNet()
                                                .isEmpty()) {
                                            if (virtualCate.getNet().get(
                                                    ((String) sdbMap
                                                            .get("ec_net_no"))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getNet()
                                                        .get(((String) sdbMap
                                                                .get("ec_net_no"))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandChannel() != null
                                                && !virtualCate.getExpandChannel()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandChannel().get(
                                                    (String.valueOf(sdbMap
                                                            .get("agentId")))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandChannel()
                                                        .get((String.valueOf(sdbMap
                                                                .get("agentId")))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getExpandType() != null
                                                && !virtualCate.getExpandType()
                                                .isEmpty()) {
                                            if (virtualCate.getExpandType().get(
                                                    (String.valueOf(sdbMap
                                                            .get("channelType"))
                                                            .toUpperCase())) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getExpandType()
                                                        .get((String.valueOf(sdbMap
                                                                .get("channelType"))
                                                                .toUpperCase()));
                                                break;
                                            }
                                        }
                                        if (virtualCate.getSection() != null
                                                && !virtualCate.getSection()
                                                .isEmpty()) {
                                            if (virtualCate
                                                    .getSection()
                                                    .get(((String) sdbMap
                                                            .get("community_no"))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getSection()
                                                        .get(((String) sdbMap
                                                                .get("community_no"))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getRegion() != null
                                                && !virtualCate.getRegion()
                                                .isEmpty()) {
                                            if (virtualCate
                                                    .getRegion()
                                                    .get(((String) sdbMap
                                                            .get("city_area_no"))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getRegion()
                                                        .get(((String) sdbMap
                                                                .get("city_area_no"))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getCity() != null
                                                && !virtualCate.getCity()
                                                .isEmpty()) {
                                            if (virtualCate.getCity().get(
                                                    ((String) sdbMap
                                                            .get("city_no"))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getCity()
                                                        .get(((String) sdbMap
                                                                .get("city_no"))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getProvince() != null
                                                && !virtualCate.getProvince()
                                                .isEmpty()) {
                                            if (virtualCate.getProvince().get(
                                                    ((String) sdbMap
                                                            .get("prov_no"))
                                                            .toUpperCase()) != null) {
                                                saleprice = (Double) virtualCate
                                                        .getProvince()
                                                        .get(((String) sdbMap
                                                                .get("prov_no"))
                                                                .toUpperCase());
                                                break;
                                            }
                                        }
                                        if (virtualCate.getAllArea() != null) {
                                            saleprice = virtualCate
                                                    .getAllArea();
                                            break;
                                        }
                                    }
                                }
                                if (saleprice == 0) {
                                    skuSize = skuSize - 1;
                                    continue;
                                }

                                if (ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                                    if (promotionoutput.get_ReturnData() != null
                                            && !"".equals(promotionoutput.get_ReturnData())) {
                                        List<PromotionListOutPut> listOutPuts = JSON.parseArray(promotionoutput
                                                        .get_ReturnData().toString(),
                                                PromotionListOutPut.class);
                                        for (PromotionListOutPut outPut : listOutPuts) {
                                            if (outPut.getGoods() != null) {
                                                for (Goodsku goodsku : outPut.getGoods()) {
                                                    if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                        if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                            detailedInformation.setType(outPut.getType());
                                                        }
                                                        detailedInformation.setStartTime(outPut.getStartTime());
                                                        detailedInformation.setEndTime(outPut.getEndTime());
                                                        if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                            if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                detailedInformation.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                            }
                                                            if (goodsku.getCount() > 0) {
                                                                detailedInformation.setPurchaseCount(goodsku.getCount());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                detailedInformation.setTgoodskuinfoid(String
                                        .valueOf(goodsSKU.getSkuGoodsId()));
                                detailedInformation.setSaleprice_o2o(saleprice
                                        .toString());
                                detailedInformation.setTgoodinfoid(String
                                        .valueOf(detailOutPut.get_id()));
                                detailedInformation
                                        .setGoodname(detailOutPut
                                                .getPublishGoodsName() != null ? detailOutPut
                                                .getPublishGoodsName()
                                                : detailOutPut.getGoodsName());
                                detailedInformation.setMarketprice(String
                                        .valueOf(goodsSKU.getMarketPrice()));
                                detailedInformation
                                        .setGoodbigpic(org.apache.commons.lang.StringUtils
                                                .join(detailOutPut.getPics()
                                                        .toArray(), ","));
                                detailedInformation
                                        .setGoodintroduce(detailOutPut
                                                .getGoodIntroduce());
                                detailedInformation.setStore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSkuStock())));
                                detailedInformation.setSoldstore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSoldSkuStock())));
                                detailedInformation
                                        .setIsforeigngoods(detailOutPut
                                                .getIsForeigngoods());
                                detailedInformation
                                        .setTempstoregoodsflag(detailOutPut
                                                .getTempStoreGoodsFlag());
                                detailedInformation.setSort(detailOutPut
                                        .getGoodsSort() == null ? 0
                                        : detailOutPut.getGoodsSort());
                                detailedInformation.setPlatorself(detailOutPut
                                        .getPlatorself());
                                detailedInformation
                                        .setDistributionFlag(detailOutPut
                                                .getDistributionFlag());
                                detailedInformation.setDeductPercent(goodsSKU
                                        .getDeductPercent());
                                detailedInformation
                                        .setDistributeProfitBase(goodsSKU
                                                .getDistributeProfitBase());
                                detailedInformation.setBalancePrice(goodsSKU
                                        .getBalancePrice());
                                detailedInformation.setIsfreshfood(detailOutPut
                                        .getIsfreshfood());
                                detailedInformation
                                        .settGoodInfoPoolId(detailOutPut
                                                .gettGoodInfoPoolId());
                                detailedInformation.setSaleflag(goodsSKU
                                        .getUlf() == 208 ? 0 : 1);
                                if (goodsSKU.getSkuStock() > 0) {
                                    detailedInformation.setIssoldout(1);
                                } else {
                                    detailedInformation.setIssoldout(0);
                                }
                                if (skuSize > 1) {
                                    detailedInformation.setIsmoresku(1);
                                } else {
                                    detailedInformation.setIsmoresku(0);
                                }
                                detailedInformation
                                        .setOnekeyupload((detailOutPut
                                                .gettGoodInfoPoolId() != null && detailOutPut
                                                .getPlatorself() == 452) ? 1
                                                : 0);
                                detailedInformation
                                        .setFreshgoodsflag(detailOutPut
                                                .getIsfreshfood());
                                if (idMapping.get(detailedInformation
                                        .getTgoodinfoid()) == null) {
                                    idMapping.put(detailedInformation
                                                    .getTgoodinfoid(),
                                            detailedInformation);
                                } else {
                                    if (new BigDecimal(idMapping.get(
                                            detailedInformation
                                                    .getTgoodinfoid())
                                            .getSaleprice_o2o())
                                            .compareTo(new BigDecimal(
                                                    detailedInformation
                                                            .getSaleprice_o2o())) == 1) {
                                        idMapping.put(detailedInformation
                                                        .getTgoodinfoid(),
                                                detailedInformation);
                                    }
                                }
                            }
                        }
                    }
                    // 多个sku相同只取价格最低显示
                    returnList = new ArrayList<ProductDetailedInformation>(
                            idMapping.values());
                    Collections.sort(returnList,
                            new Comparator<ProductDetailedInformation>() {
                                public int compare(
                                        ProductDetailedInformation arg0,
                                        ProductDetailedInformation arg1) {
                                    return arg1.getSort().compareTo(
                                            arg0.getSort());
                                }
                            });
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    outListModel.setList(returnList);
                    outListModel.setCount(count);
                    data.set_ReturnData(outListModel);
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(outListModel);
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数据出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        return data;
    }

    @Override
    public ObjectOutput detail_mongo(String psam, String channelid,
                                     String goodid, String virtualcatid, Integer level)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        SideShopBDetailInPut in = new SideShopBDetailInPut();
        PromotionListInPut pin = new PromotionListInPut();
        List<SkuDetails> skuDetailsList = new ArrayList<SkuDetails>();
        ProductDetailForProductList productDetailForProductList = new ProductDetailForProductList();
        if (StringUtils.isEmpty(psam) || StringUtils.isEmpty(channelid)) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }
        try {
            Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
                    .selectSdbMediaStatisticsByPsam(psam);
            if (sdbMap == null && sdbMap.isEmpty()) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该终端所对应的网点信息！");
                return data;
            }

            if (virtualcatid != null && !"".equals(virtualcatid)
                    && !"all".equals(virtualcatid)) {
                in.setVirtualcatid(Long.parseLong(virtualcatid));
                in.setLevel(level);
            }
            in.setPsam(psam);
            in.setChannelid(Long.parseLong(channelid));
            in.setGoodid(Long.parseLong(goodid));
            in.setAllArea(true);
            in.setNet((String) sdbMap.get("ec_net_no"));
            in.setRegion((String) sdbMap.get("city_area_no"));
            in.setProvince((String) sdbMap.get("prov_no"));
            in.setCity((String) sdbMap.get("city_no"));
            in.setSection((String) sdbMap.get("community_no"));
            in.setExpandChannel((String) sdbMap.get("agentId"));
            in.setExpandType(String.valueOf(sdbMap.get("channelType")));

            String url = mongoInterfaceUrl.getDetail();
            String inStr = JSON.toJSONString(in);
            String json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);

            //mongo促销列表
            pin.setSource(Constant.PROMOTION_FLAG_RELEASE_CHANNEL_FLAG);
            pin.setChannel(Integer.parseInt(channelid));
            pin.setAllArea("1");
            pin.setPsam(psam);
            pin.setProvince((String) sdbMap.get("prov_no"));
            pin.setCity((String) sdbMap.get("city_no"));
            pin.setRegion((String) sdbMap.get("city_area_no"));
            pin.setSection((String) sdbMap.get("community_no"));
            pin.setNet((String) sdbMap.get("ec_net_no"));
            pin.setExpandChannel((String) sdbMap.get("agentId"));
            pin.setExpandType(String.valueOf(sdbMap.get("channelType")));
            pin.setType(Constant.PROMOTION_FLAG_STRAIGHT_DOWN_FLAG);

            url = mongoInterfaceUrl.getPromotion();
            inStr = JSON.toJSONString(pin);
            json = HttpUtil.httpPostConnetion(url, inStr);
            ObjectOutput promotionoutput = JSON.parseObject(json, ObjectOutput.class);

            if (!ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询促销信息出错！");
                return data;
            }

            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
                        && !"".equals(output.get_ReturnData())) {
                    SideShopBDetailOutPut detailOutPut = JSON.parseObject(
                            output.get_ReturnData().toString(),
                            SideShopBDetailOutPut.class);
                    GoodsPublishForKDB publishForKDB = new GoodsPublishForKDB();
                    publishForKDB.setTgoodinfoid(detailOutPut.get_id()
                            .intValue());
                    publishForKDB.setSupplierid(detailOutPut.getSupplierId());
                    publishForKDB.setSuppliername(detailOutPut
                            .getSupplierName());
                    publishForKDB.setGoodname(detailOutPut
                            .getPublishGoodsName() != null ? detailOutPut
                            .getPublishGoodsName() : detailOutPut
                            .getGoodsName());
                    publishForKDB.setGoodintroduce(detailOutPut
                            .getGoodIntroduce());
                    publishForKDB.setIscontractmachine(detailOutPut
                            .getIscontractmachine());
                    publishForKDB.setIsexpresstohome(detailOutPut
                            .getIsExpressToHome());
                    publishForKDB.setDistributionflag(detailOutPut
                            .getDistributionFlag());
                    publishForKDB
                            .setGoodbigpic(org.apache.commons.lang.StringUtils
                                    .join(detailOutPut.getPics().toArray(), ","));
                    publishForKDB.setIsforeigngoods(detailOutPut
                            .getIsForeigngoods());
                    publishForKDB.setTempstoregoodsflag(detailOutPut
                            .getTempStoreGoodsFlag());
                    publishForKDB.setClientServiceTel(detailOutPut.getTel());
                    publishForKDB.setReturnprovincename(detailOutPut
                            .getAddress());
                    if (detailOutPut.getSkus() != null
                            && !detailOutPut.getSkus().isEmpty()) {
                        for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
                            Double saleprice = 0.00;
                            if (goodsSKU.getVc() != null
                                    && goodsSKU.getVc().size() > 0) {
                                for (ListVirtualCate virtualCate : goodsSKU
                                        .getVc()) {
                                    if (virtualCate.getPsam() != null
                                            && !virtualCate.getPsam().isEmpty()) {
                                        if (virtualCate.getPsam().get(
                                                psam.toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getPsam().get(
                                                            psam.toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getNet() != null
                                            && !virtualCate.getNet().isEmpty()) {
                                        if (virtualCate.getNet().get(
                                                ((String) sdbMap
                                                        .get("ec_net_no"))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getNet()
                                                    .get(((String) sdbMap
                                                            .get("ec_net_no"))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandChannel() != null
                                            && !virtualCate.getExpandChannel()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandChannel().get(
                                                (String.valueOf(sdbMap
                                                        .get("agentId")))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandChannel()
                                                    .get((String.valueOf(sdbMap
                                                            .get("agentId")))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getExpandType() != null
                                            && !virtualCate.getExpandType()
                                            .isEmpty()) {
                                        if (virtualCate.getExpandType().get(
                                                (String.valueOf(sdbMap
                                                        .get("channelType"))
                                                        .toUpperCase())) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getExpandType()
                                                    .get((String.valueOf(sdbMap
                                                            .get("channelType"))
                                                            .toUpperCase()));
                                            break;
                                        }
                                    }
                                    if (virtualCate.getSection() != null
                                            && !virtualCate.getSection()
                                            .isEmpty()) {
                                        if (virtualCate.getSection().get(
                                                ((String) sdbMap
                                                        .get("community_no"))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getSection()
                                                    .get(((String) sdbMap
                                                            .get("community_no"))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getRegion() != null
                                            && !virtualCate.getRegion()
                                            .isEmpty()) {
                                        if (virtualCate.getRegion().get(
                                                ((String) sdbMap
                                                        .get("city_area_no"))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getRegion()
                                                    .get(((String) sdbMap
                                                            .get("city_area_no"))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getCity() != null
                                            && !virtualCate.getCity().isEmpty()) {
                                        if (virtualCate.getCity()
                                                .get(((String) sdbMap
                                                        .get("city_no"))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getCity()
                                                    .get(((String) sdbMap
                                                            .get("city_no"))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getProvince() != null
                                            && !virtualCate.getProvince()
                                            .isEmpty()) {
                                        if (virtualCate.getProvince()
                                                .get(((String) sdbMap
                                                        .get("prov_no"))
                                                        .toUpperCase()) != null) {
                                            saleprice = (Double) virtualCate
                                                    .getProvince()
                                                    .get(((String) sdbMap
                                                            .get("prov_no"))
                                                            .toUpperCase());
                                            break;
                                        }
                                    }
                                    if (virtualCate.getAllArea() != null) {
                                        saleprice = virtualCate.getAllArea();
                                        break;
                                    }
                                }
                                if (saleprice == 0) {
                                    continue;
                                }
                                SkuDetails skuDetails = new SkuDetails();
                                if (ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
                                    if (promotionoutput.get_ReturnData() != null
                                            && !"".equals(promotionoutput.get_ReturnData())) {
                                        List<PromotionListOutPut> listOutPuts = JSON.parseArray(promotionoutput
                                                        .get_ReturnData().toString(),
                                                PromotionListOutPut.class);
                                        for (PromotionListOutPut outPut : listOutPuts) {
                                            if (outPut.getGoods() != null) {
                                                for (Goodsku goodsku : outPut.getGoods()) {
                                                    if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(detailOutPut.get_id()))) {
                                                        skuDetails.setStartTime(outPut.getStartTime());
                                                        skuDetails.setEndTime(outPut.getEndTime());
                                                        if (String.valueOf(goodsku.getSkuId()).equals(String.valueOf(goodsSKU.getSkuGoodsId()))) {
                                                            if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
                                                                skuDetails.setType(outPut.getType());
                                                                skuDetails.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
                                                            }
                                                            if (goodsku.getCount() > 0) {
                                                                skuDetails.setPurchaseCount(goodsku.getCount());
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }


                                skuDetails.setMarketPrice(String
                                        .valueOf(goodsSKU.getMarketPrice()));
                                skuDetails.setSalePrice(saleprice.toString());
                                skuDetails.setSkuFrontDisNameStr(goodsSKU
                                        .getSkuNames());
                                skuDetails.setSkuIdStr(goodsSKU.getSkuIds());
                                skuDetails.setSoldStore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSoldSkuStock())));
                                skuDetails.setStore(Double.parseDouble(String
                                        .valueOf(goodsSKU.getSkuStock())));
                                skuDetails.setTGoodInfoId(detailOutPut.get_id()
                                        .intValue());
                                skuDetails.setTGoodSkuInfoId(goodsSKU
                                        .getSkuGoodsId().intValue());
                                skuDetailsList.add(skuDetails);
                            }
                        }
                    }
                    productDetailForProductList.setGoods(publishForKDB);
                    productDetailForProductList
                            .setGoodsSkuO2OList((SkuDetails[]) skuDetailsList
                                    .toArray(new SkuDetails[skuDetailsList
                                            .size()]));
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    data.set_ReturnData(productDetailForProductList);
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(data);
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数据出错！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        return data;
    }

    @Override
    public ObjectOutput shopgoodslist(SideShopBListForKeyInPut inp)
            throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        List<SkuDetails> skuDetailsList = new ArrayList<SkuDetails>();
        ProductDetailForProductList productDetailForProductList = new ProductDetailForProductList();
        List<ProductDetailedInformation> returnList = new ArrayList<ProductDetailedInformation>();
        OutListModel outListModel = new OutListModel();
        SideShopBListInPut in = new SideShopBListInPut();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();
        int count = 0;
        try {

            String url = mongoInterfaceUrl.getSideshop_goodslist();
            String inStr = JSON.toJSONString(inp);

            System.out.println("mongo请求参数=====================" + inStr);

            String json = HttpUtil.httpPostConnetion(url, inStr);

            System.out.println("mongo返回值=====================" + json);

            ObjectOutput output = JSON.parseObject(json, ObjectOutput.class);
            ObjectOutput countoutput = JSON.parseObject(json,
                    ObjectOutput.class);
            if (ReturnMsg.CODE_SUCCESS.equals(countoutput.get_ReturnCode())) {

            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数量出错！");
                return data;
            }


            if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
                        && !"".equals(output.get_ReturnData())) {
//					List<SideShopBDetailOutPut> list = JSONArray.parseArray(output
//							.get_ReturnData().toString(),
//							SideShopBDetailOutPut.class);
                    Gson gson = new Gson();
                    List<SideShopBDetailOutPut> list = gson.fromJson(output.get_ReturnData().toString(),
                            new TypeToken<List<SideShopBDetailOutPut>>() {
                            }.getType());

                    for (SideShopBDetailOutPut detailOutPut : list) {
                        if (detailOutPut.getSkus() != null
                                && !detailOutPut.getSkus().isEmpty()) {
                            for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
                                ProductDetailedInformation detailedInformation = new ProductDetailedInformation();
                                Double saleprice = 0.00;
                                *//*
                                 * if (saleprice == 0) { break; }
								 *//*
                                saleprice = goodsSKU.getVc().get(0).getPsam().get(inp.getPsam().toUpperCase());
                                detailedInformation.setTgoodskuinfoid(String
                                        .valueOf(goodsSKU.getSkuGoodsId()));
                                detailedInformation.setSaleprice_o2o(saleprice
                                        .toString());
                                detailedInformation.setTgoodinfoid(String
                                        .valueOf(detailOutPut.get_id()));
                                detailedInformation
                                        .setGoodname(detailOutPut
                                                .getPublishGoodsName() != null ? detailOutPut
                                                .getPublishGoodsName()
                                                : detailOutPut.getGoodsName());
                                detailedInformation.setMarketprice(String
                                        .valueOf(goodsSKU.getMarketPrice()));
                                detailedInformation
                                        .setGoodbigpic(org.apache.commons.lang.StringUtils
                                                .join(detailOutPut.getPics()
                                                        .toArray(), ";"));
                                detailedInformation
                                        .setGoodintroduce(detailOutPut
                                                .getGoodIntroduce());
                                detailedInformation.setStore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSkuStock())));
                                detailedInformation.setSoldstore(Double
                                        .parseDouble(String.valueOf(goodsSKU
                                                .getSoldSkuStock())));
                                detailedInformation
                                        .setIsforeigngoods(detailOutPut
                                                .getIsForeigngoods());
                                detailedInformation
                                        .setTempstoregoodsflag(detailOutPut
                                                .getTempStoreGoodsFlag());
                                detailedInformation.setSort(detailOutPut
                                        .getGoodsSort() == null ? 0
                                        : detailOutPut.getGoodsSort());
                                detailedInformation.setPlatorself(detailOutPut
                                        .getPlatorself());
                                detailedInformation
                                        .setDistributionFlag(detailOutPut
                                                .getDistributionFlag());
                                detailedInformation.setDeductPercent(goodsSKU
                                        .getDeductPercent());
                                detailedInformation
                                        .setDistributeProfitBase(goodsSKU
                                                .getDistributeProfitBase());
                                detailedInformation.setBalancePrice(goodsSKU
                                        .getBalancePrice());
                                detailedInformation.setIsfreshfood(detailOutPut
                                        .getIsfreshfood());

                                detailedInformation.setFreshgoodsflag(detailOutPut
                                        .getIsfreshfood());

                                if (null != detailedInformation.gettGoodInfoPoolId()
                                        && detailedInformation.gettGoodInfoPoolId() > 0) {
                                    detailedInformation.setOnekeyupload(1);
                                } else {
                                    detailedInformation.setOnekeyupload(0);
                                }
                                detailedInformation
                                        .settGoodInfoPoolId(detailOutPut
                                                .gettGoodInfoPoolId());
                                detailedInformation.setSaleflag(goodsSKU
                                        .getUlf() == 208 && goodsSKU.getGf() == 197 ? 0 : 1);
                                if (goodsSKU.getSkuStock() > 0) {
                                    detailedInformation.setIssoldout(1);
                                } else {
                                    detailedInformation.setIssoldout(0);
                                }
                                if (detailOutPut.getSkus().size() > 1) {
                                    detailedInformation.setIsmoresku(1);
                                } else {
                                    detailedInformation.setIsmoresku(0);
                                }
                                detailedInformation
                                        .setOnekeyupload((detailOutPut
                                                .gettGoodInfoPoolId() != null && detailOutPut
                                                .getPlatorself() == 452) ? 1
                                                : 0);
                                detailedInformation
                                        .setFreshgoodsflag(detailOutPut
                                                .getIsfreshfood());
                                if (idMapping.get(detailedInformation
                                        .getTgoodinfoid()) == null) {
                                    idMapping.put(detailedInformation
                                                    .getTgoodinfoid(),
                                            detailedInformation);
                                } else {
                                    if (new BigDecimal(idMapping.get(
                                            detailedInformation
                                                    .getTgoodinfoid())
                                            .getSaleprice_o2o())
                                            .compareTo(new BigDecimal(
                                                    detailedInformation
                                                            .getSaleprice_o2o())) == 1) {
                                        idMapping.put(detailedInformation
                                                        .getTgoodinfoid(),
                                                detailedInformation);
                                    }
                                }
                            }
                        }
                    }
                    // 多个sku相同只取价格最低显示
                    returnList = new ArrayList<ProductDetailedInformation>(
                            idMapping.values());
                    Collections.sort(returnList,
                            new Comparator<ProductDetailedInformation>() {
                                public int compare(
                                        ProductDetailedInformation arg0,
                                        ProductDetailedInformation arg1) {
                                    return arg1.getSort().compareTo(
                                            arg0.getSort());
                                }
                            });
                    data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                    System.out.println("对象转换后返回值=====================" + JSON.toJSONString(returnList));
                    outListModel.setList(returnList);
                    outListModel.setCount(count);
                    data.set_ReturnData(outListModel);
                } else {
                    data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                    data.set_ReturnMsg("没有找到商品！");
                    data.set_ReturnData(outListModel);
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("从mongo查询商品数据出错！");
            }


			*//*if (ReturnMsg.CODE_SUCCESS.equals(output.get_ReturnCode())) {
                if (output.get_ReturnData() != null
						&& !"".equals(output.get_ReturnData())) {
					//List<SideShopBDetailOutPut> detailOutPutlist = (List<SideShopBDetailOutPut>) output.get_ReturnData();
					List<SideShopBDetailOutPut> detailOutPutlist = JSON.parseArray(output.get_ReturnData().toString(),SideShopBDetailOutPut.class);
					if(detailOutPutlist != null && detailOutPutlist.size() > 0){
						for(SideShopBDetailOutPut detailOutPut : detailOutPutlist){
							GoodsPublishForKDB publishForKDB = new GoodsPublishForKDB();
							publishForKDB.setTgoodinfoid(detailOutPut.get_id()
									.intValue());
							publishForKDB.setSupplierid(detailOutPut.getSupplierId());
							publishForKDB.setSuppliername(detailOutPut
									.getSupplierName());
							publishForKDB.setGoodname(detailOutPut
									.getPublishGoodsName() != null ? detailOutPut
									.getPublishGoodsName() : detailOutPut
									.getGoodsName());
							publishForKDB.setGoodintroduce(detailOutPut
									.getGoodIntroduce());
							publishForKDB.setIscontractmachine(detailOutPut
									.getIscontractmachine());
							publishForKDB.setIsexpresstohome(detailOutPut
									.getIsExpressToHome());
							publishForKDB.setDistributionflag(detailOutPut
									.getDistributionFlag());
							publishForKDB
									.setGoodbigpic(org.apache.commons.lang.StringUtils
											.join(detailOutPut.getPics().toArray(), ","));
							publishForKDB.setIsforeigngoods(detailOutPut
									.getIsForeigngoods());
							publishForKDB.setTempstoregoodsflag(detailOutPut
									.getTempStoreGoodsFlag());
							publishForKDB.setClientServiceTel(detailOutPut.getTel());
							publishForKDB.setReturnprovincename(detailOutPut
									.getAddress());
							if (detailOutPut.getSkus() != null
									&& !detailOutPut.getSkus().isEmpty()) {
								for (ListGoodsSKU goodsSKU : detailOutPut.getSkus()) {
									Double saleprice = 0.00;
									if (goodsSKU.getVc() != null
											&& goodsSKU.getVc().size() > 0) {

										SkuDetails skuDetails = new SkuDetails();
										skuDetails.setMarketPrice(String
												.valueOf(goodsSKU.getMarketPrice()));
										skuDetails.setSalePrice(saleprice.toString());
										skuDetails.setSkuFrontDisNameStr(goodsSKU
												.getSkuNames());
										skuDetails.setSkuIdStr(goodsSKU.getSkuIds());
										skuDetails.setSoldStore(Double
												.parseDouble(String.valueOf(goodsSKU
														.getSoldSkuStock())));
										skuDetails.setStore(Double.parseDouble(String
												.valueOf(goodsSKU.getSkuStock())));
										skuDetails.setTGoodInfoId(detailOutPut.get_id()
												.intValue());
										skuDetails.setTGoodSkuInfoId(goodsSKU
												.getSkuGoodsId().intValue());
										skuDetailsList.add(skuDetails);
									}
								}
							}
							productDetailForProductList.setGoods(publishForKDB);
							productDetailForProductList
									.setGoodsSkuO2OList((SkuDetails[]) skuDetailsList
											.toArray(new SkuDetails[skuDetailsList
													.size()]));
						}

					}

					data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
					data.set_ReturnData(productDetailForProductList);
				} else {
					data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
					data.set_ReturnMsg("没有找到商品！");
					data.set_ReturnData(data);
				}

			} else {
				data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
				data.set_ReturnMsg("从mongo查询商品数据出错！");
			}*//*
        } catch (Exception e) {
            e.printStackTrace();
            throw new LakalaException(e);
        }
        return data;
    }*/
}
