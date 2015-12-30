package com.lakala.module.approval.service;

import com.lakala.base.model.Approval;
import com.lakala.exception.LakalaException;

/**
 * Created by HOT.LIU on 2015/3/6.
 */
public interface ApprovalService {
    /**
     * 插入审批记录表.
     *
     * @param record the record
     * @return the long
     * @throws LakalaException the lakala exception
     */
    public Long insertSelective(Approval record);
}
