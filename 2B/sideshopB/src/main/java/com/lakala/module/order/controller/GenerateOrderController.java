package com.lakala.module.order.controller;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.lakala.base.model.Tshopkeeperaddr;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.model.AddOrderInput;
import com.lakala.module.order.model.CheckCouponInfo;
import com.lakala.module.order.model.CheckCouponReturn;
import com.lakala.module.order.model.ShopCommAddrInput;
import com.lakala.module.order.service.GenerateOrderService;
import com.lakala.util.ReturnMsg;

@Controller
@RequestMapping("/order")
public class GenerateOrderController {
	Logger logger = Logger.getLogger(GenerateOrderController.class);
	@Autowired
	private GenerateOrderService es;
	@ResponseBody
	@RequestMapping(value = "/checkcoupon")  
	public ObjectOutput<CheckCouponReturn> checkcoupon(CheckCouponInfo ei){
		ObjectOutput<CheckCouponReturn> info = null;
		try{
			info = es.checkcoupon(ei);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<CheckCouponReturn>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	@ResponseBody
	@RequestMapping(value = "/getshopcommaddr")  
	public ObjectOutput getshopcommaddr(ShopCommAddrInput ei){
		ObjectOutput info = null;
		System.out.println("test");
		try{
			info = es.getshopcommaddr(ei);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<CheckCouponReturn>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		System.out.println("info === " + info);
		return info;
	}
	@ResponseBody
	@RequestMapping(value = "/addaddress")  
	public ObjectOutput addaddress(Tshopkeeperaddr ska){
		ObjectOutput info = null;
		System.out.println("gggggggggggg");
		try{
			info = es.insertAddress(ska);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<CheckCouponReturn>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		System.out.println("info === " + info);
		return info;
	}
	@ResponseBody
	@RequestMapping(value = "/updateaddress")  
	public ObjectOutput updateaddress(Tshopkeeperaddr ska){
		ObjectOutput info = null;
		System.out.println("5555");
		try{
			info = es.updateAddress(ska);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<CheckCouponReturn>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		System.out.println("info === " + info);
		return info;
	}
	@ResponseBody
	@RequestMapping(value = "/deleteaddress")  
	public ObjectOutput deleteaddress(Tshopkeeperaddr ska){
		ObjectOutput info = null;
		System.out.println("555");
		try{
			info = es.deleteAddress(ska);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<CheckCouponReturn>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		System.out.println("info === " + info);
		return info;
	}
	@ResponseBody
	@RequestMapping(value = "/addorder")  
	public ObjectOutput addorder(AddOrderInput ei){
		Gson gson = new Gson();
		//logger.info("addorder AddOrderInput ============= >> " + gson.toJson(ei));
		/*String uri = request.getRequestURI();//返回请求行中的资源名称
		String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
		String callbackurl = url.replaceAll(uri, "");*/
		ObjectOutput info = null;
		try{
			/*ei.setCallbackurl(callbackurl);*/
			info = es.addorder(ei);
		}catch(Exception e){
			logger.error(e.getMessage());
			info = new ObjectOutput<CheckCouponReturn>();
			info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
		}
		return info;
	}
	public static void main(String[] args) {
		String url = "http://localhost:8080/sideshopB/order/addorder";
		String uri = "/sideshopB/order/addorder";
		url = url.replaceAll(uri, "");
		System.out.println(url);
	}
}
