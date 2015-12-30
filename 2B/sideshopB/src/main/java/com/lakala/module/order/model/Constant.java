package com.lakala.module.order.model;

import java.util.HashSet;
import java.util.Set;

public class Constant {
	/**
	 * @author: yhg
	 * @project:sideshopB
	 * @time: 2015年3月12日 下午6:14:04
	 * @todo: TODO
	 */
	
	//订单查询类型
	public static final Set<String> querytypeset = new HashSet<String>();
	
	
/*	 *  all    :全部订单;   
	 *  pfall  :全部订单-用于批发订单;
	 *  lsall  :全部订单-用于零售订单;
	 *  pfdfk  :待付款-用于批发订单;   
		pfdfh  :待发货-用于批发订单;
		pfyfh  :已发货-用于批发订单;
		pfbfsh :部分收货-用于批发订单;
		pfysh  :已收货-用于批发订单;
		lsdfh  :待发货-用于零售订单;
		lsyfh  :已发货 -用于零售订单;
		lsyqs  :已签收 -用于零售订单;
		lssh   :售后 -用于零售订单;
		sxxdzsh:需店主送货订单-用于需店主送货; 
	*/
	static{
		querytypeset.add("all");
		querytypeset.add("pfall");
		querytypeset.add("lsall");
		querytypeset.add("pfdfk");
		querytypeset.add("pfdfh");
		querytypeset.add("pfyfh");
		querytypeset.add("pfbfsh");
		querytypeset.add("pfysh");
		querytypeset.add("lsdqr");
		querytypeset.add("lsdzt");
		querytypeset.add("lsdsh");
		querytypeset.add("lsdfh");
		querytypeset.add("lsyfh");
		querytypeset.add("lsyqs");
		querytypeset.add("lsyqx");
		querytypeset.add("lssh");
		querytypeset.add("sxxdzsh");
	}
	
	//订单是否支付  149:未支付;150:已支付
	public final static int TORDER_ISPAY_DZF = 149; //订单是否支付
	public final static int TORDER_ISPAY_YZF = 150; //订单是否支付
	
	//订单状态  99:未发货;100:发货中;101:部分发货;102:已发货;103:部分签收;104:已签收;216:已拒收;217:物流异常
	public final static int TORDER_STATE_WFH = 99;
	public final static int TORDER_STATE_FHZ = 100;
	public final static int TORDER_STATE_BFFH = 101;
	public final static int TORDER_STATE_YFH = 102;
	public final static int TORDER_STATE_BFQS = 103;
	public final static int TORDER_STATE_YQS = 104;
	public final static int TORDER_STATE_YJS = 216;
	public final static int TORDER_STATE_WLYC = 217;
	
    //3H商品标识。配送标识：	一小时送达   362;三到五小时送达  363;三到五天送达  364;五天以上送达  365
	public static final int TORDER_IS3H_1H = 362;
  	public static final int TORDER_IS3H_3H5H = 363;
  	
    //订单配送方式 86:快递到店;87:快递到家
    public final static int TORDER_ISDELIVERTOHOME_KDDD = 86;  //快递到店
    public final static int TORDER_ISDELIVERTOHOME_KDDJ = 87;  //快递到家
    
    //订单取消状态  136:未取消;137:已取消
  	public final static int TORDER_CANCELSTATE_WQX = 136; 
  	public final static int TORDER_CANCELSTATE_YQX = 137; 
  	
    //未支付订单库存释放状态  276:未库存库存;275:已释放库存
  	public final static int TORDER_ISRESTORE_WTB = 276; 
  	public final static int TORDER_ISRESTORE_YTB = 275; 
  	
  	public static final int TORDER_SOURCE_APPB = 466;
  	public static final int TORDER_SOURCE_APPC = 467;
  	
  	
    //订单取消类型，493-运营取消，494-店主取消，495-供应商取消 ,496-顾客取消 ,497-系统后台取消
   	public static final int TORDER_CANCELTYPE_YYQX = 493;  //运营取消
   	public static final int TORDER_CANCELTYPE_DZQX = 494;  //店主取消
   	public static final int TORDER_CANCELTYPE_GYSQX = 495;  //供应商取消
   	public static final int TORDER_CANCELTYPE_GKQX = 496;  //顾客取消
   	public static final int TORDER_CANCELTYPE_XTHTQX = 497;  //系统后台取消
  	
 	 // 暂存商品：378 暂存入店、379 暂存入仓、380 不暂存
	public static final int TEMPSTOREGOODSFLAG_ZCRD = 378;
	public static final int TEMPSTOREGOODSFLAG_ZCRC = 379;
	public static final int TEMPSTOREGOODSFLAG_BZC = 380;
	
	//来源渠道 27-批发进货  APP2B下的订单也是批发进货
  	public static final String TORDER_CHANNELCODE_PFJH = "27";
  	public static final String TORDER_CHANNELCODE_APP2B = "44";
  	public static final String TORDER_CHANNELCODE_APP2B_LAST = "43";
  	
    //拉卡拉自配送物流公司主键
    public static final int LOGIST_CORP_LAKALAZPS = 128;
    
    //微信给店主信息提示关注微信号
    public static final String WEIXIN_SHOPKEEPER = "拉卡拉小店(lakalavip)";
    
    //商品自营平台标识。自营-452，平台-453
    public static final int TORDER_PLATORSELF_ZY = 452;
    public static final int TORDER_PLATORSELF_PT = 453;
    
    //236订单支付手段  (237:拉卡拉;238:银行;239:支付宝;240:货到付款;241:其他)
    public static final int TORDER_PAYCHANNEL_HDFK = 240;
  	
}
