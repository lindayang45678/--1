package com.lakala.module.messagebase.controller;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.messagebase.model.MessageBaseInput;
import com.lakala.module.messagebase.service.MessageBaseService;
import com.lakala.util.ReturnMsg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by HOT.LIU on 2015/3/10.
 */
@Controller
@RequestMapping("/message")
public class MessageBaseController {
    Logger logger = Logger.getLogger(MessageBaseController.class);

    @Autowired
    private MessageBaseService messageBaseService;

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public ObjectOutput detail(MessageBaseInput messageBaseInput) {
        ObjectOutput info = null;
        try {
            info = messageBaseService.selectByPrimaryKey(messageBaseInput);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }
        return info;
    }
}
