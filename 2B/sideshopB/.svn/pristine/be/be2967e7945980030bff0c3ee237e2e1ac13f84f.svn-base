package com.lakala.module.messagebase.service.impl;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.messagebase.model.MessageBaseInput;
import com.lakala.module.messagebase.service.MessageBaseService;
import com.lakala.util.ReturnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Chenbo on 2014/11/3.
 */
@Service
public class MessageBaseServiceImpl implements MessageBaseService {

    @Autowired
    private com.lakala.mapper.r.messagebase.MessageBaseMapper messageBaseMapper_R;


    @Override
    public ObjectOutput selectByPrimaryKey(MessageBaseInput messageBaseInput) throws LakalaException {
        ObjectOutput data = new ObjectOutput();

        if (messageBaseInput == null) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数对象为空!");
            return data;
        }

        try {
            Map<String,Object> map = messageBaseMapper_R.selectByPrimaryKey(messageBaseInput.getId());

            data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
            data.set_ReturnData(map);
            return data;
        } catch (Exception e) {
            throw new LakalaException(e);
        }
    }
}
