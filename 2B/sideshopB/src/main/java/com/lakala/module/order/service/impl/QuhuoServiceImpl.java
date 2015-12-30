package com.lakala.module.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;














import com.lakala.model.QuhuoOutputSql;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.order.service.AfterSalesService;
import com.lakala.module.order.service.QuhuoCommonService;
import com.lakala.module.order.service.QuhuoService;
import com.lakala.module.order.vo.QuhuoInput;
import com.lakala.module.order.vo.QuhuoOutput;
import com.lakala.module.order.vo.QuhuoOutputChild;
import com.lakala.util.Constants;
import com.lakala.util.Msg2App2CUtil;
import com.lakala.util.ReturnMsg;
/**
 * 取货查询服务
 * @author ls
 *
 */
@Service
public class QuhuoServiceImpl implements QuhuoService {
	Logger logger = Logger.getLogger(QuhuoServiceImpl.class);
	@Autowired
	private com.lakala.mapper.r.order.QuhuoMapper quhuoMapper_r;
	@Autowired
	private com.lakala.mapper.w.order.QuhuoMapper quhuoMapper_w;
	@Autowired
	private AfterSalesService afterSalesService;
	@Autowired
	private QuhuoCommonService quhuoCommonService;
	@Override
	public ObjectOutput<QuhuoOutput> getQuhuoInfo(QuhuoInput qi) {
		ObjectOutput<QuhuoOutput> data = new ObjectOutput<QuhuoOutput>();
		if(qi == null || qi.getSincecode() == null || qi.getSincecode().equals("")){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		qi.setOrderitemsidflag("0");
		//根据取货码和手机号联合查询
		QuhuoOutput qo = new QuhuoOutput();
		List<QuhuoOutputSql> qoslist = quhuoMapper_r.getQuhuoInfo(qi);
		if(qoslist != null && qoslist.size() > 0){
			QuhuoOutputSql qos = qoslist.get(0);
			qo.setActualamount(qos.getActualamount());
			qo.setCusname(qos.getCusname());
			qo.setCustelno(qos.getCustelno());
			qo.setOrderproviderid(qos.getOrderproviderid());
			qo.setPaychanel(qos.getPaychanel());
			qo.setState(qos.getState());
			qo.setReturnstate(qos.getReturnstate());
			qo.setProvidername(qos.getProvidername());
			qo.setOrdertime(qos.getOrdertime());
			List<QuhuoOutputChild> qoclist = new ArrayList<QuhuoOutputChild>();
			for(QuhuoOutputSql qosfor : qoslist){
				QuhuoOutputChild qoc = new QuhuoOutputChild();
				qoc.setGoodsid(qosfor.getGoodsid());
				qoc.setGoodsname(qosfor.getGoodsname());
				qoc.setGoodsnum(qosfor.getGoodsnum());
				qoc.setNorms(qosfor.getNorms());
				qoc.setGoodbigpic(qosfor.getGoodbigpic());
				qoc.setTgoodinfopoolid(qosfor.getTgoodinfopoolid());
				qoclist.add(qoc);
			}
			qo.setQoclist(qoclist);
			data.set_ReturnData(qo);
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		}else{
			data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			data.set_ReturnMsg("查询为空!");
		}
		return data;
	}

	@Override
	public ObjectOutput<QuhuoOutput> updateQuhuo2Sign(QuhuoInput qi) {
		ObjectOutput<QuhuoOutput> data = new ObjectOutput<QuhuoOutput>();
		if(qi == null || qi.getSincecode() == null || qi.getSincecode().equals("")){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		qi.setOrderitemsidflag("1");//设置为1,只会查询出部分信息
		List<QuhuoOutputSql> qoslist = quhuoMapper_r.getQuhuoInfo(qi);
		QuhuoOutputSql qosorder = null;
		int paychannel = -1;
		if(qoslist != null && qoslist.size() > 0){
			qosorder = qoslist.get(0);
			if(qosorder != null){
				if(qosorder.getState().toString().equals(String.valueOf(Constants.TORDER_STATE_YQS))
						||qosorder.getState().toString().equals(String.valueOf(Constants.TORDER_STATE_BFQS))){
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnMsg("已签收,请勿重复操作!");
					return data;
				}
				if(qosorder.getState().toString().equals(String.valueOf(Constants.TORDER_STATE_YJS))){
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnMsg("已拒收,不允许签收!");
					return data;
				}
				paychannel = qosorder.getPaychanel();
			}
		}
		if(qosorder != null){
			//更新torderitems的订单状态和支付状态
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("cuspickupstate", Constants.CUSPICKUPSTATE_YQS);
			map.put("state", Constants.TORDER_STATE_YQS);
			int ispayflag = 0;
			if(paychannel == Constants.TORDER_PAYCHANNEL_HDFK 
					&& qosorder.getPlatorself() == Constants.PLATORSELF_ZY){
				map.put("ispay", Constants.TORDER_ISPAY_YZF);
				ispayflag = Constants.TORDER_PAYCHANNEL_HDFK;
			}
			for(QuhuoOutputSql qos : qoslist){
				map.put("torderitemsid", qos.getOrderitemsid());
				quhuoMapper_w.updategkinorderitem(map);
				//更新物流子表的订单状态
				quhuoMapper_w.updatedzstateinlogitem(map);
			}
			//更新torderprovider的订单状态和支付状态
			String torderproviderid = qosorder.getOrderproviderid();
			if(torderproviderid != null && !torderproviderid.equals("")){
				int providerstate = quhuoCommonService.getProviderState(torderproviderid);
				quhuoCommonService.updateOrderProviderState(torderproviderid,providerstate,ispayflag);
			}
			//更新torder的订单状态和支付状态
			String orderid = qosorder.getOrderid();
			if(orderid != null && !orderid.equals("")){
				int orderstate = quhuoCommonService.getOrderState(orderid);
				quhuoCommonService.updateOrderState(orderid,orderstate,ispayflag);
			}
			//更新tallorder的支付状态
			if(paychannel == Constants.TORDER_PAYCHANNEL_HDFK 
					&& qosorder.getPlatorself() == Constants.PLATORSELF_ZY){
				Integer allorderid = qosorder.getAllorderid();
				if(allorderid != null){
					int allorderstate = quhuoCommonService.getAllOrderPayState(allorderid);
					if(allorderstate == Constants.TORDER_ISPAY_YZF){
						quhuoCommonService.updateAllOrderPayState(allorderid);
					}
				}
			}
			Integer logid = qosorder.getLogid();
			if(logid != null){
				Map<String,Object> loginfomap = new HashMap<String,Object>();
				loginfomap.put("state", Constants.TORDER_STATE_YQS);
				loginfomap.put("logid", logid);
				quhuoMapper_w.updatestateinloginfo(loginfomap);
			}
			// push2C
			for(QuhuoOutputSql qos : qoslist){
				call2c(qos);
			}
		}
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		return data;
	}
	public void call2c(QuhuoOutputSql item){
		System.out.println("============顾客取货push2C=====start=============");
		System.out.println("判断订单来源："+item.getSource()+":ps:467表示2c");
		if(item.getSource().equals(Msg2App2CUtil.TORDER_SOURCE_2C)){
			try {
				String ret = Msg2App2CUtil.syncmsg2app2c(Msg2App2CUtil.OPTTYPE_URL_SIGNED, "tOrderItemsId", item.getOrderitemsid().toString());
				System.out.println("调2C返回结果:"+ret);
				if(ret.equals("000000")){
					System.out.println("子单push成功");
				}else{
					System.out.println("子单push失败");
				}
			} catch (Exception e) {
				System.out.println("子单发货消息同步至APP2C失败!");
				e.printStackTrace();
			}
		}
		System.out.println("============顾客取货push2C=====end=============");
	}
	@Override
	public ObjectOutput<QuhuoOutput> reject(QuhuoInput qi) {
		ObjectOutput<QuhuoOutput> data = new ObjectOutput<QuhuoOutput>();
		if(qi == null || qi.getSincecode() == null || qi.getSincecode().equals("")){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		qi.setOrderitemsidflag("1");//设置为1,只会查询出部分信息
		List<QuhuoOutputSql> qoslist = quhuoMapper_r.getQuhuoInfo(qi);
		if(qoslist != null && qoslist.size() > 0){
			QuhuoOutputSql qosorder = qoslist.get(0);
			if(qosorder != null){
				if(qosorder.getState().toString().equals(String.valueOf(Constants.TORDER_STATE_YQS))
						||qosorder.getState().toString().equals(String.valueOf(Constants.TORDER_STATE_BFQS))){
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnMsg("已签收,不允许拒收!");
					return data;
				}
				if(qosorder.getState().toString().equals(String.valueOf(Constants.TORDER_STATE_YJS))){
					data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
					data.set_ReturnMsg("已拒收,请勿重复操作!");
					return data;
				}
			}
			String orderid = qoslist.get(0).getOrderid();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("orderid", orderid);
			//通过orderid查询items子表中itemid的数量
			int itemidcount = quhuoMapper_r.getItemidCountByOrderid(map);
			if(itemidcount != qoslist.size()){
				data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
				data.set_ReturnMsg("多笔订单不允许拒收,请联系客服!");
				return data;
			}
			//处理拒收,单独的方法放到事务里
			String paychanel = qoslist.get(0).getPaychanel().toString();
			String torderproviderid = qoslist.get(0).getOrderproviderid();
			//售后
			if(paychanel.equals("240")){
				//货到付款
				afterSalesService.updateHDFK(torderproviderid);
			}else{
				//在线支付
				afterSalesService.updateJsInline(torderproviderid, qi.getMobile());
			}
			data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			return data;
		}
		data.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
		data.set_ReturnMsg("查询为空!");
		return data;
	}
}
