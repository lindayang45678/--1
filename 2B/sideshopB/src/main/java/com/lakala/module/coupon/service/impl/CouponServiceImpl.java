package com.lakala.module.coupon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.lakala.model.coupon.Coupon;
import com.lakala.model.coupon.FavorableRuleKDB;
import com.lakala.model.coupon.FavorableruleCouponBatch;
import com.lakala.module.coupon.service.CouponService;
import com.lakala.module.coupon.vo.CouponInput;

@Service
public class CouponServiceImpl implements CouponService {
	
	
	@Resource
	private com.lakala.mapper.r.coupon.CouponMapper couponMapper_R;
	
	@Resource
	private com.lakala.mapper.r.coupon.FavorableruleCouponBatchMapper frcbMapper_R;
	
	@Resource
	private com.lakala.mapper.r.coupon.FavorableRuleKDBMapper frKDBMapper_R;
	
	
	@Override
	public List<Coupon> findCoupon(CouponInput couponInput) {
		
		Map<String,Object> params=new HashMap<String,Object>();
		
		int page=couponInput.getPage();
		int pageSize=couponInput.getPageSize();
		int pageIndex=(page-1)*pageSize;
		
		params.put("pageIndex",pageIndex);
		params.put("pageSize",pageSize);
		
		params.put("status",couponInput.getStatus());
		params.put("userMark",couponInput.getMobile());
		params.put("disabled",couponInput.getDisabled());
		
		List<Coupon> couponList=couponMapper_R.findCoupon(params);
		
		couponList=checkIsActiviyRule(couponList);
		
		return couponList;
	}
	
	
	/**验证券的订单限制和使用规则是否绑定到活动上，如果是则要取活动的规则*/
	private List<Coupon> checkIsActiviyRule(List<Coupon> couponList){
		
		for(Coupon c : couponList){
			
			FavorableruleCouponBatch frcb1=frcbMapper_R.selectByBatchId(c.getBatchtickeid());
		    if(frcb1!=null){  //确认是订单金额继承活动
		    	c.setOrderamount(frcb1.getOrderamount());
		    }
			
			if(c.getFrequencys()==null){ //券规则继承活动，获取活动的所有频道
				FavorableruleCouponBatch frcb=frcbMapper_R.selectByBatchId(c.getBatchtickeid());
				
				FavorableRuleKDB frKDB=null;
				if(frcb!=null){
					frKDB=frKDBMapper_R.selectByFavId(frcb.getFavid());
				}
				
				if(frKDB!=null&&frKDB.getData()!=null&&!"".equals(frKDB.getData().trim())){
					try {
						JSONObject data=new JSONObject(frKDB.getData());
						JSONArray jsonArr=new JSONArray(data.get("frequencys").toString());
						
						StringBuilder sb=new StringBuilder();
						for(int i=0;i<jsonArr.length();i++){
							sb.append(jsonArr.getJSONObject(i).get("frequencyname").toString()).append(",");
						}
						sb.deleteCharAt(sb.length()-1);
						
						c.setFrequencys(sb.toString());
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return couponList;
	}

	
	
	@Override
	public Long getCouponCount(CouponInput couponInput) {
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("status",couponInput.getStatus());
		params.put("userMark",couponInput.getMobile());
		params.put("disabled",couponInput.getDisabled());
		
		long count=couponMapper_R.getCouponCount(params);
		
		return count;
	}
	
}
