package com.lakala.module.mongo.service;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.vo.in.SideShopBListForKeyInPut;

/**
 * Created by HOT.LIU on 2015/5/20.
 */
public interface SideShopBMongoService {

    /**
     * 获得批发进货左侧树形分类.
     *
     * @param psam      the psam
     * @param channelid the channelid
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput left_mongo(String psam, String channelid) throws LakalaException;

    /**
     * List _ mongo.
     *
     * @param psam         the psam
     * @param channelid    the channelid
     * @param virtualcatid the virtualcatid
     * @param searchparm   the searchparm
     * @param pageIndex    the page index
     * @param pageSize     the page size
     * @param level        the level
     * @param platorself   the level
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput list_mongo(String psam, String channelid, String virtualcatid, String searchparm, Integer pageIndex, Integer pageSize, Integer level, Integer platorself) throws LakalaException;

    /**
     * Detail _ mongo.
     *
     * @param psam         the psam
     * @param channelid    the channelid
     * @param goodid       the goodid
     * @param virtualcatid the virtualcatid
     * @param level        the level
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput detail_mongo(String psam, String channelid, String goodid, String virtualcatid, Integer level) throws LakalaException;

	public ObjectOutput shopgoodslist(SideShopBListForKeyInPut inp) throws LakalaException;
}
