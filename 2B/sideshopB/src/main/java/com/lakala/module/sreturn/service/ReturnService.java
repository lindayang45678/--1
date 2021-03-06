package com.lakala.module.sreturn.service;

import java.util.List;

import com.lakala.base.model.Torderitems;
import com.lakala.base.model.Torderprovider;
import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.sreturn.model.ReturnInput;

/**
 * Created by HOT.LIU on 2015/2/28.
 */
public interface ReturnService {

    /**
     * 获得该店主所有的售后信息.
     *
     * @param returnInput the return input
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput list(ReturnInput returnInput) throws LakalaException;

    /**
     * 售后审批.
     *
     * @param treturnid      the treturnid
     * @param treturnitemsid the treturnitemsid
     * @param mobile         the mobile
     * @param isagree        the isagree
     * @param remarks        the remarks
     * @return the object output
     * @throws LakalaException the lakala exception
     */
    public ObjectOutput approval(String treturnid, String treturnitemsid, String mobile, boolean isagree, String remarks) throws LakalaException;
    
    
    /**
     * 根据订单生成售后退款信息
     * @param torderprovider
     * @param torderitemsList
     * @throws Exception
     * @author: yhg 
     * @time: 2015年4月25日 下午2:37:07
     * @todo: TODO
     */
    public void updateReturnTKDataByOrder(Torderprovider torderprovider,List<Torderitems> torderitemsList,boolean isalldo) throws LakalaException;
}
