package com.lakala.module.order.controller;

import java.io.IOException;




import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.service.QuhuoService;
import com.lakala.module.order.vo.QuhuoInput;
import com.lakala.module.order.vo.QuhuoOutput;
import com.lakala.util.Constants;
import com.lakala.util.ReturnMsg;


@Controller
@RequestMapping("/order/quhuo")
public class QuhuoController {
	Logger logger = Logger.getLogger(QuhuoController.class);
	@Autowired
	private QuhuoService qs;
	//顾客取货查询
	@ResponseBody
	@RequestMapping(value="/selectexpress")
	public ObjectOutput<QuhuoOutput> selectexpress(QuhuoInput qi) throws IOException{
		ObjectOutput<QuhuoOutput> info = null;
		try{
			info = qs.getQuhuoInfo(qi);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<QuhuoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	@ResponseBody
	@RequestMapping(value="/confirmsh")
	public ObjectOutput<QuhuoOutput> confirmsh(QuhuoInput qi) throws IOException{
		ObjectOutput<QuhuoOutput> info = null;
		try{
			info = qs.updateQuhuo2Sign(qi);
			if(info.get_ReturnCode().equals(ReturnMsg.CODE_SUCCESS)){
				qi.setState(Constants.TORDER_STATE_YQS);
				info = qs.getQuhuoInfo(qi);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<QuhuoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	@ResponseBody
	@RequestMapping(value="/jushou")
	public ObjectOutput<QuhuoOutput> jushou(QuhuoInput qi) throws IOException{
		ObjectOutput<QuhuoOutput> info = null;
		try{
			info = qs.reject(qi);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<QuhuoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
}
