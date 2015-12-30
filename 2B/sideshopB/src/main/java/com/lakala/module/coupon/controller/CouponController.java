package com.lakala.module.coupon.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.model.coupon.Coupon;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.coupon.service.CouponService;
import com.lakala.module.coupon.vo.CouponInput;
import com.lakala.module.coupon.vo.CouponOutput;
import com.lakala.util.ReturnMsg;

/**
 * 优惠券
 * @author sunjiabo
 *
 */

@Controller
@RequestMapping("/coupon")
public class CouponController {
	
	@Resource
	private CouponService couponService; 
	
	/**优惠券列表*/
	@RequestMapping("/findcoupon")
	public @ResponseBody ObjectOutput findCoupon(CouponInput couponInput){
		ObjectOutput out=new ObjectOutput();
		
		String token=couponInput.getToken();
		
		CouponOutput couponOutput=new CouponOutput();
		couponOutput.setToken(token);
		
		if(!validateQueryParams(couponInput)){
			out.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			out.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			out.set_ReturnData(couponOutput);
			
			return out;
		}
		
		List<Coupon> couponList=couponService.findCoupon(couponInput);
		
		//查询当前用户多个状态券的count
		
		couponInput.setStatus("unused");
		long unusedCount=couponService.getCouponCount(couponInput);
		
		couponInput.setStatus("used");
		long usedCount=couponService.getCouponCount(couponInput);
		
		couponOutput.setCouponList(couponList);
		couponOutput.setUnusedCount(unusedCount);
		couponOutput.setUsedCount(usedCount);
		
		out.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		out.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		out.set_ReturnData(couponOutput);
		
		return out;
	}
	
	
	private static List<String> statusList=new ArrayList<String>(Arrays.asList("outTime","used","unused"));
	
	private boolean validateQueryParams(CouponInput ci){
		
		int page=ci.getPage();
		int pageSize=ci.getPageSize();
		String status=ci.getStatus();
		
		if(page<1||pageSize<1||!statusList.contains(status)){
			return false;
		}
		
		return true;
	}
	
	
	
}