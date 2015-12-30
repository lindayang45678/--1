package com.lakala.module.wholesale.service;

import com.lakala.exception.LakalaException;
import com.lakala.model.ProductDetailedInformation;
import com.lakala.module.comm.ObjectOutput;

import java.util.List;

/**
 * Created by HOT.LIU on 2015/2/28.
 */
public interface WholesaleService {
    /**
     * 获得批发进货左侧树形分类.
     *
     * @param psam      the psam
     * @param channelid the channelid
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput left(String psam, String channelid) throws LakalaException;

    /**
     * 获得批发进货商品详情.
     *
     * @param psam      the psam
     * @param channelid the channelid
     * @param goodsid   the goodsid
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput detail(String psam, String channelid, String goodsid) throws LakalaException;

    /**
     * 根据psam获取平台推送所有商品.
     *
     * @param psam      the psam
     * @param channelid the channelid
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput getAllProductByPsam(String psam, String channelid) throws LakalaException;

    /**
     * 根据psam、频道、虚拟分类得到所有商品.
     *
     * @param psamCode     the psam code
     * @param netChannelId the net channel id
     * @param virtualCatId the virtual cat id
     * @return the object output
     */
    public ObjectOutput getGoodsByPsamAndVirtualCatId(String psamCode, String netChannelId, String virtualCatId) throws LakalaException;

    /**
     * 查询商品.
     *
     * @param psamCode     the psam code
     * @param netChannelId the net channel id
     * @param virtualCatId the virtual cat id
     * @param param        the param
     * @return the object output
     */
    public ObjectOutput searchGoods(String psamCode, String netChannelId, String virtualCatId, String param) throws LakalaException;

    /**
     * 获得每页商品信息的详细情况.
     *
     * @param list     the list
     * @param psamCode the psam code
     * @return the page product detile
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput getPageProductDetile(List<ProductDetailedInformation> list, String psamCode) throws LakalaException;


    /**
     * 获得下架商品列表.
     *
     * @param psamCode the psam code
     * @param searchparm the searchparm
     * @param page the page
     * @param rows the rows
     * @return the soldout list
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput getSoldoutList(String psamCode, String searchparm, int page, int rows) throws LakalaException;
}
