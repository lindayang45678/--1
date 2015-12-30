/* 全局时间戳 */
var t = new Date().getTime();

/* 全局的localStorage */
var storage = window.localStorage;

/* 商品key prefix */
var product_prefix = "sb2b_product_";

/* 订单来源--93:手机商城; 94:微信商城; 95:开店宝; 96:PC商城; 334:收款宝; 97:其他 */
var source = "";

/* 订单发票类型 --89:不开发票; 90:个人; 91:公司 */
var billtypeNone = "89";
var billtypePersonal = "90";
var billtypeBusiness = "91";

/* 订单支付手段--237:拉卡拉; 238:银行; 239:支付宝; 240:货到付款; 241:其他 */
var paychannelLkl = "237";
var paychanelBnk = "238";
var paychanelZfb = "239";
var paychanelCod = "240";
var paychanelOther = "241";

/* 订单支付类型--82:全额; 83:分期  */
var paytypeFull = "82";
var paytypeInstallment = "83";

/* 配送方式--86:快递到店; 87:快递到家 */
var devicetypeShop = "86";
var devicetypeHome = "87";

/* 周末配送标志--275:是; 276:否 */
var weekdeviceY = "275";
var weekdeviceN = "276";

/* 是否支付标志--150:是; 149:否 */
var ispayY = "150";
var ispayN = "149";

/* 配送标示 */
var ship1hCode = 362;
var ship1hDesc = "【一小时送达】";
var ship3hCode = 363;
var ship3hDesc = "【三到五小时送达】";
var ship3dCode = 364;
var ship3dDesc = "【三到五天送达】";
var ship5dCode = 365;
var ship5dDesc = "【五天以上送达】";

/* 商品自营平台标识--自营:452; 平台:453 */
var selfGoods = "452";
var platGoods = "453";

/* 服务器端系统时间 */
var sysDate = "";

/* 当前窗口可视区域宽 */
var windowWidth = $(window).width();
/* 当前窗口可视区域高 */
var windowHeight = $(window).height();

/* 防止两次点击 */
var ghostClickTime1 = null;
var ghostClickTimeout = 500;

/* 全国批发券 */
var QGPF_Coupon = "全国批发券";
/* 本地批发券 */
var BDPF_Coupon = "本地批发券";

/* 专网地址和测试地址的切换--测试地址:1; 专网地址:2; 生产地址:3;*/
var globalStatus = 1;
if(globalStatus == 1) {
	
} else if(globalStatus == 2) {
	
} else if(globalStatus == 3) {
	
}