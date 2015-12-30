package com.lakala.module.sreturn.controller;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.sreturn.model.ReturnInput;
import com.lakala.module.sreturn.service.ReturnService;
import com.lakala.util.ReturnMsg;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by HOT.LIU on 2015/2/28.
 */
@Controller
@RequestMapping("/return")
public class ReturnController {
    Logger logger = Logger.getLogger(ReturnController.class);

    @Autowired
    private ReturnService returnService;

    /**
     * 获得该店主所有的售后信息.
     *
     * @param returnInput the return input
     * @return the object output
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ObjectOutput list(ReturnInput returnInput) {
        ObjectOutput info = null;
        try {
            info = returnService.list(returnInput);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }
        return info;
    }

    /**
     * 售后审批.
     *
     * @param returnInput the return input
     * @return the object output
     */
    @ResponseBody
    @RequestMapping(value = "/approval", method = RequestMethod.POST)
    public ObjectOutput approval(ReturnInput returnInput) {
        ObjectOutput info = null;
        try {
            info = returnService.approval(returnInput.getTreturnid(), returnInput.getTreturnitemsid(), returnInput.getMobile(), returnInput.getIsagree(), returnInput.getRemarks());
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }
        return info;
    }
}
