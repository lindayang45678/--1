package com.lakala.module.update.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.sreturn.controller.ReturnController;
import com.lakala.module.sreturn.model.ReturnInput;
import com.lakala.module.sreturn.service.ReturnService;
import com.lakala.module.update.service.UpdateVersionService;
import com.lakala.module.update.vo.VersionInput;
import com.lakala.util.ReturnMsg;

@Controller
@RequestMapping("/update")
public class UpdateController {
    
	Logger logger = Logger.getLogger(UpdateController.class);	
	@Autowired
    private UpdateVersionService updateVersionService;

    /**
     * 获取应用更新版本.
     *
     * @param returnInput the return input
     * @return the object output
     */
    @ResponseBody
    @RequestMapping(value = "/curversion", method = RequestMethod.POST)
    public ObjectOutput getCurversion(VersionInput versionInput) {
        ObjectOutput info = null;
        try {
            info = updateVersionService.checkUpdateVersion(versionInput);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(e.getMessage());
            return info;
        }
        return info;
    }
    
    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ObjectOutput insertVersion(VersionInput versionInput) {
        ObjectOutput info = null;
        try {
            info = updateVersionService.insertUpdateVersion(versionInput);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(e.getMessage());
            return info;
        }
        return info;
    }
    
    @ResponseBody
    @RequestMapping(value = "/updateversion", method = RequestMethod.POST)
    public ObjectOutput updateVersion(VersionInput versionInput) {
        ObjectOutput info = null;
        try {
            info = updateVersionService.updateBySelective(versionInput);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(e.getMessage());
            return info;
        }
        return info;
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ObjectOutput deleteVersionInfo(VersionInput versionInput) {
        ObjectOutput info = null;
        try {
            info = updateVersionService.deleteBySelective(versionInput);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(e.getMessage());
            return info;
        }
        return info;
    }

}
