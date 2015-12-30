package com.lakala.module.order.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.service.ShouhuoService;
import com.lakala.module.order.vo.ShouhuoInput;
import com.lakala.module.order.vo.ShouhuoOutput;
import com.lakala.util.ReturnMsg;

@Controller
@RequestMapping("/order/shouhuo")
public class ShouhuoController {
	
	Logger logger = Logger.getLogger(ShouhuoController.class);
	@Autowired
	private ShouhuoService ss;
	//店主收货查询
	@ResponseBody
	@RequestMapping(value="/selectexpress")
	public ObjectOutput<ShouhuoOutput> selectexpress(ShouhuoInput si) throws IOException{
		ObjectOutput<ShouhuoOutput> info = null;
		try{
			info = ss.getShouhuoInfo(si);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<ShouhuoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	/*@RequestMapping(value="/selectdistribution", method=RequestMethod.POST)
	public String selectdistribution(@RequestParam(value = "logno", required =false) String logno,
			HttpServletRequest request) throws IOException{
		return "/jsp/youhuozc.jsp";
	}*/
	@ResponseBody
	@RequestMapping(value="/confirmsh")
	public ObjectOutput<ShouhuoOutput> confirmsh(ShouhuoInput si, HttpServletRequest request) throws IOException{
		ObjectOutput<ShouhuoOutput> info = null;
		try{
			ObjectOutput<List<Integer>> logidlistinfo = ss.updateShouhuo(si, request);
			if(logidlistinfo.get_ReturnCode().equals(ReturnMsg.CODE_SUCCESS)){
				info = ss.getConfirmInfo(si,logidlistinfo.get_ReturnData());
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<ShouhuoOutput>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	/*@RequestMapping(value="/confirmshzc", method=RequestMethod.POST)
	public String confirmshzc(HttpServletRequest request) throws IOException{
		return "/jsp/zcshouhuo.jsp";
	}*/
}
