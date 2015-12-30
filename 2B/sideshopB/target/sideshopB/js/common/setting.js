/* 全局时间戳 */
var t = new Date().getTime();

/* 全局的localStorage */
var storage = window.localStorage;

/* PSAM */
var psam = storage.getItem("psam") || "";

/* 店主手机号 */
var mobile = storage.getItem("mobile") || "";

/* 网点编号 */
var netNo = storage.getItem("netNo") || "";

/* 用户token */
var userToken = storage.getItem("userToken") || "";

/* 平台：ios, android */
var platform = storage.getItem("platform") || "";

/* 设备编号/设备ID */
var deviceId = storage.getItem("deviceId") || "";

/* 子渠道号 */
var subChannelId = storage.getItem("subChannelId") || "";

/* 对象ID */
var objectId = storage.getItem("objectId") || "";

/* 推送token */
var pushToken = storage.getItem("pushToken") || "";

/* 小店ID */
var shopId = storage.getItem("shopId") || "";

/* 商品key prefix */
var productPrefix = "product_";

/* 是否登录key--1:已经登录; 0:尚未登录 */
var hasLogined = "hasLogined";

var nonBackUrl = ["login.html", "homepage/index.html", "paysuccess.html", "payfail.html","payconfirm.html"];

/* 显示消息的个数 */
var newsCount = 30;

/* 图片的宽度 */
var imgw100 = 100;
var imgw300 = 300;
var imgw600 = 600;
var imgw800 = 800;

/* 订单类型--42:APP-C频道; 43:批发订单; 28:零售订单; */
var channelcode2C = 42;
var channelcodePF = 43;
var channelcodeLS = 28;

/* 订单状态--99:未发货; 100:发货中; 101:部分发货; 102:已发货; 103:部分签收; 104:已签收; 216:已拒收; 217:物流异常 */
var stateWFH = 99;
var stateFHZ = 100;
var stateBFFH = 101;
var stateYFH = 102;
var stateBFQS = 103;
var stateYQS = 104;
var stateYJS = 216;
var stateWLYC = 217;

/* 售后状态--退货待审核298、退货审核不通过299、待退货300、已退货301、退款待审核303、退款审核不通过304、待退款305、已退款306 */
var returntstateThdsh = 298;
var returntstateThshbtg = 299;
var returntstateDth = 300;
var returntstateYth = 301;
var returntstateTkdsh = 303;
var returntstateTkshbtg = 304;
var returntstateDtk = 305;
var returntstateYtk = 306;

/* 订单来源--93:店主; 94:销售员; */
var ordersrcDZ = "";
var ordersrcXSY = "";

/* 来源渠道--93:手机商城; 94:微信商城; 95:开店宝; 96:PC商城; 97:其他; 334:收款宝; 357:wap商城; 466:身边app-2B; 467:身边app-2C */
var sourcePhone = 93;
var sourceWebchat = 94;
var sourceKdb = 95;
var sourcePC = 96;
var sourceOther = 97;
var sourceSkb = 334;
var sourceWap = 357;
var sourceSbApp2B = 466;
var sourceSbApp2C = 467;

/* 订单发票类型 --89:不开发票; 90:个人; 91:公司 */
var billtypeNone = 89;
var billtypePersonal = 90;
var billtypeBusiness = 91;

/* 订单支付手段--237:拉卡拉快捷/钱包支付; 238:银行; 239:支付宝; 240:货到付款; 241:其他; 432:微信支付; 441:收款宝支付 ; 490:拉卡拉刷卡支付*/
var paychannelLkl = 237;
var paychannelLklCard = 490;
var paychanelBnk = 238;
var paychanelZfb = 239;
var paychanelCod = 240;
var paychanelOther = 241;
var paychanelWx = 432;
var paychanelSkb = 441;

/* 订单支付类型--82:全额; 83:分期  */
var paytypeFull = 82;
var paytypeInstallment = 83;

/* 配送方式--86:快递到店; 87:快递到家 */
var devicetypeShop = 86;
var devicetypeHome = 87;

/* 周末配送标志--275:是; 276:否 */
var weekdeviceY = 275;
var weekdeviceN = 276;

/* 是否支付标志--150:是; 149:否 */
var ispayY = 150;
var ispayN = 149;

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

/* 售后状态--正常订单:116; 换货中:117; 已换货:118; 退货中:119; 已退货:120  */
var invalidstateNormal = 116;
var invalidstateHHZ = 117;
var invalidstateYHH = 118;
var invalidstateTHZ = 119;
var invalidstateYTH = 120;

/* 商户类型--加盟型:459; 标准型:460; 旗舰型:461; */
var bizTypeJMX = 459;
var bizTypeBZX = 460;
var bizTypeQJX = 461;

/* 商户类型--加盟型:459; 标准型:460; 旗舰型:461; */
var bizType = storage.getItem("bizType") || bizTypeJMX;

/* 服务器端系统时间 */
var sysDate = "";

/* 当前窗口可视区域宽 */
var windowWidth = $(window).width();
/* 当前窗口可视区域高 */
var windowHeight = storage.getItem("windowHeight") || $(window).height();

/* 防止两次点击 */
var ghostClickTime1 = null;
var ghostClickTimeout = 500;

/* 全国批发券 */
var QGPF_Coupon = "全国批发券";
/* 本地批发券 */
var BDPF_Coupon = "本地批发券";

/* 返回成功编码 */
var returnCodeSuccess = "000000";
/* 内部错误 */
var returnCodeError = "000001";
/* 数据检验不通过 */
var returnCodeValidate = "000002";
/* 批发进货验证及数据提示 */
var returnCodeWholesale = "000003";
/* 很抱歉，该手机号未开通电商业务，请联系拉卡拉客户经理开通！ */
var returnCode100001 = "100001";
/* 很抱歉，该手机号不是店主手机号，请检查后重新输入！*/
var returnCode100002 = "100002";
/* 很抱歉，该手机号未激活，请先激活！*/
var returnCode100003 = "100003";
/* 很抱歉，您输入的手机号或密码错误，请检查后重新输入！*/
var returnCode100004 = "100004";
/* 该手机号已经登录！*/
var returnCode100005 = "100005";
/* 密码必须由数字或者字母或者数字字母的组合，且长度必须大于等于6小于等于20!*/
var returnCode100006 = "100006";
/* 很抱歉，您输入的验证码错误，请确认后重新输入！*/
var returnCode100007 = "100007";
/* 登录成功！*/
var returnCode100008 = "100008";
/* 激活成功！*/
var returnCode100009 = "100009";
/* 登出成功！ */
var returnCode100010 = "100010";
/* webservice服务出错了！*/
var returnCode100011 = "100011";
/* 更新密码成功*/
var returnCode100012 = "100012";
/* 恭喜您，激活成功，请设置登录密码！*/
var returnCode100013 = "100013";
/*  该手机号已激活，无需重复激活*/
var returnCode100014 = "100014";
/* 恭喜您，激活成功！您可用拉卡拉的账号密码登录！*/
var returnCode100015 = "100015";
/* 返回密码成功*/
var returnCode100016 = "100016";
/* 拒绝访问*/
var returnCode100017 = "100017";

/* 用户手机号不能为空！ */
var msgMobileNotEmpty = "店主手机号不能为空！";
/* 很抱歉，您输入的手机号错误，请重新输入！ */
var msgMobileError = "很抱歉，您输入的手机号错误，请重新输入！";
/*很抱歉，该手机号不是店主手机号，请检查后重新输入*/
var msgshopMobile = "很抱歉，该手机号不是店主手机号，请检查后重新输入！";
/* 登录密码不能为空！ */
var msgPwdNotEmpty = "登录密码不能为空！";
/* 很抱歉，您输入的手机号或密码有误，请检查后重新输入！ */
var msgLoginError = "很抱歉，您输入的手机号或密码有误，请检查后重新输入！";
/* 验证码不能为空  */
var msgVerificationEmpty = "验证码不能为空！";
/* 密码格式不对*/
var msgPawFormat = "很抱歉，密码只能是6-20位的数字、字母、或数字和字母的组合，请重新输入！";
/* 库存不足*/
var msgFailProductStore = "库存不足";
/* 请输入有效商品数量*/
var msgProductNotNumber = "请输入有效商品数量";
/* 获取店铺失败，请返回后重试！ */
var msgGetShopsFail = "获取店铺失败，请返回后重试！";
/* 本店暂无现金券*/
var msgCouponempty ="本店暂无现金券卡!";
/* 本店暂无已使用现金券*/
var msgCouponused ="暂无已使用现金券卡!";
/* 本店暂无已使用现金券*/
var msgCouponunuse ="暂无未使用现金券卡!";
/* 本店暂无已过期现金券*/
var msgCouponexpired ="本店暂无已过期现金券卡!";
/* couponuse:1 抱歉，您购物车中的商品不能使用该现金券 */
var msgCouponsNotForProduct = "抱歉，您购物车中的商品不能使用该现金券";
/* couponuse:2 抱歉，您选择的现金券总面值超出最大可用范围*/
var msgCouponsOverPrice = "抱歉，您选择的现金券总面值超出最大可用范围";
/* couponuse:3 抱歉，该现金券已被使用 */
var msgCouponsUsed = "抱歉，该现金券已被使用";
/* couponuse:4 抱歉，您的手机号未绑定该现金券，不能使用*/
var msgCouponsNotForPhone = "抱歉，您的手机号未绑定该现金券，不能使用";
/* couponuse:5 抱歉，该现金券不存在 */
var msgCouponsNotExist = "抱歉，该现金券不存在";
/* 抱歉，验证不通过！ */
var msgCouponsNotthrough = "抱歉，验证不通过！";
/* 您未选择现金券，请选择！*/
var msgCouponsNotChoose = "您未选择现金券，请选择！";
/* 请输入现金券号*/
var msgCouponsNotEmpty = "请输入现金券号";
/* 请选择收货地址*/
var msgAddressNotChoose = "请选择收货地址";
/* 收货人手机号不能为空*/
var msgAddressPhoneNotEmpty = "收货人手机号不能为空";
/* 收货人手机号不正确*/
var msgAddressPhoneError = "很抱歉，您输入的手机号错误，请重新输入";
/* 很抱歉，该功能暂时只对旗舰小店开通！ */
var msgFuncNonOpened = "很抱歉，该功能暂时只对旗舰店开放！";
/* 公司名称不能为空*/
var msgCompanyempty = "公司名称不能为空！";
/* 获取消息通知失败，请稍后重试！ */
var msgNewsInfoFail = "获取消息通知失败，请稍后重试！";
/* 暂无消息通知！ */
var msgNoneNews = "暂无消息通知！";
/* 获取消息订单记录为空！ */
var msgNoOrder = "暂无该状态的订单!";
/* 获取消息配送单信息为空！ */
var msgNoDelivery = "很抱歉，该物流或快递单信息获取失败！";
/* 无批发进货商品！ */
var msgNoWholesale = "&nbsp;暂未向本店发布批发商品！";
/* 获取消息配送单信息为空！ */
var msgOrderAllSigned = "该订单已全部收货！";
/* 小店近期商品为空*/
var msgShopGoodsEmpty = "暂无小店近期批发商品！"
/* 小店公告*/
var msgShopAnnouncement = "欢迎光临！身边小二在这恭候多时啦！";
/* 配送客服不能为空！*/
var msgServiceNameEmpty = "配送客服不能为空！";
/* 客服电话不能为空！*/
var msgServicePhoneEmpty = "客服电话不能为空！";
/* 很抱歉，您输入的客服电话错误，请重新输入！ */
var msgServicePhoneError = "很抱歉，您输入的客服电话错误，请重新输入！";
/* 下架成功*/
var msgDownShelf = "下架成功！";
/* 下架失败*/
var msgDownShelfFail = "下架失败！";
/* 很抱歉，该手机号未开通电商业务，请联系拉卡拉客户经理开通！ */
var msgReturn100001 = "很抱歉，该手机号未开通电商业务，请联系拉卡拉客户经理开通！";
/* 很抱歉，该手机号不是店主手机号，请检查后重新输入！*/
var msgReturn100002 = "很抱歉，该手机号不是店主手机号，请检查后重新输入！";
/* 很抱歉，该手机号未激活，请先激活！*/
var msgReturn100003 = "很抱歉，该手机号未激活，请先激活！";
/* 很抱歉，您输入的手机号或密码错误，请检查后重新输入！*/
var msgReturn100004 = "很抱歉，您输入的手机号或密码错误，请检查后重新输入！";
/* 该手机号已经登录！*/
var msgReturn100005 = "该手机号已经登录！";
/* 密码必须由数字或者字母或者数字字母的组合，且长度必须大于等于6小于等于20!*/
var msgReturn100006 = "密码必须由数字或者字母或者数字字母的组合，且长度必须大于等于6小于等于20!";
/* 很抱歉，您输入的验证码错误，请确认后重新输入！*/
var msgReturn100007 = "很抱歉，您输入的验证码错误，请确认后重新输入！";
/* 登录成功！*/
var msgReturn100008 = "登录成功！";
/* 激活成功！*/
var msgReturn100009 = "激活成功！";
/* 登出成功！ */
var msgReturn100010 = "登出成功！";
/* webservice服务出错了！*/
var msgReturn100011 = "很抱歉，该功能暂停服务，正在紧急修复！";
/* 更新密码成功*/
var msgReturn100012 = "更新密码成功";
/* 恭喜您，激活成功，请设置登录密码！*/
var msgReturn100013 = "恭喜您，激活成功，请设置登录密码！";
/*  该手机号已激活，无需重复激活*/
var msgReturn100014 = "该手机号已激活，无需重复激活！";
/* 恭喜您，激活成功！您可用拉卡拉的账号密码登录！*/
var msgReturn100015 = "恭喜您，激活成功！您可用拉卡拉的账号密码登录！";
/* 返回密码成功*/
var msgReturn100016 = "返回密码成功";
/* 拒绝访问*/
var msgReturn100017 = "很抱歉，系统异常，请稍后登陆！";
/* 您还未添加本店商品！*/
var msgNoAddGoods = "您还未添加本店商品！";
/* 该分类下无模板商品！*/
var msgNoTemplateGoods = "该分类下无模板商品！";
/* 地址切换--本机地址:1; 测试地址:2; 生产地址:3; */
var globalStatus = 1;
if(globalStatus == 1) {
	/* 图片服务器地址*/
	var urlImage = "http://img.lakalaec.com/product";
	/* 广告图片服务器地址*/
	var urlAdvert = "http://img.lakalaec.com/ad";
	/* 总收益接口地址  */
	var urlToProfitHome = "../../profit/toProfitHome";
	/* 收益查询接口地址  */
	var urlGetEarningsData = "../../profit/getEarningsData";
	/* 订单周期收益接口地址  */
	var urlGetEarningsOrderList = "../../profit/getEarningsOrderList";
	/* 订单收益详情页接口地址  */
	var urlGetOrderDetail = "../../profit/getOrderDetail";
	/* 单个月收益详细查询接口地址  */
	var urlGetEarningsOrderData = "../../profit/getEarningsOrderData";
	/* 登录接口地址 */
	var urlLogin = "../../user/login";
	/* 登出接口地址 */
	var urlLogout = "../../user/logout";
	/* 忘记登录密码接口地址 */
	var urlForgetpwd = "../../user/forgetpwd";
	/* 用户激活，设置密码*/
	var urlRegisterpwd = "../../user/register2";
	/* 获取验证码 */
	var urlGetCode = "../../auth/getCode";
	/* 用户注册接口地址-xwl */
	var urlRegister = "../../user/register1";
	/* 优惠券列表接口地址 */
	var urlFindCoupon = "../../coupon/findcoupon";
	/* 批发进货左侧分类接口*/
	var urlWholesaleLeft = "../../sideshop/wholesale/left";
	/* 批发进货广告列表*/
	var urlWholesalePoster = "../../poster/getposterlist";
	/* 批发进货获得商品列表*/
	var urlWholesaleList = "../../sideshop/wholesale/list"; 
	/* 批发进货商品详情*/
	var urlWholesaleDetail = "../../sideshop/wholesale/detail";
	/* 批发进货验证优惠券接口*/
	var  urlCheckCoupon= "../../order/checkcoupon";
	/* 批发进货生成订单接口*/
	var urlAddOrder = "../../order/addorder";
	/* 店主取货验证码接口地址 */
	var urlreceipt = "../../order/shouhuo/selectexpress";
	/* 店主确认取货接口地址 */
	var urlReceiptConfirm = "../../order/shouhuo/confirmsh";
	/* 顾客取货验证码接口地址 */
	var urlCustreceipt = "../../order/quhuo/selectexpress";
	/* 顾客确认取货接口地址 */
	var urlCustReceiptConfirm = "../../order/quhuo/confirmsh";
	/* 顾客拒绝取货接口地址 */
	var urlCustReceiptReject = "../../order/quhuo/jushou";
	/* 订单物流信息查看接口地址 */
	var urlOrderExpressSearch = "../../order/express/search";
	/* 订单查询接口地址 */
	var urlOrderQuery = "../../order/multiquery";
	/* 获取常用人收货地址接口 */
	var urlShopCommAddr = "../../order/getshopcommaddr";
	/*添加收货人地址*/
	var urlshopAddAddress = "../../order/addaddress";
	/*编辑收货人地址*/
	var updateAddress = "../../order/updateaddress";
	/*删除收货人地址*/
    var deleteAddress = "../../order/deleteaddress";
    /*小店管理 获取商品信自营息*/
    var sideshopgoodsList = "../../sideshopgoods/list";
	/*商品搜索接口地址*/
//	var urlSearchGoods = "../../";
	/* 获取店主信息接口地址*/
	var urlShopInfo = "../../shop/shopinfo";
	/* 编辑小店信息接口地址 */
	var urlEditshop="../../shop/editshopinfo";
	/* 客户列表 */
	var urlCustomerList = "../../customer/customerlist";
	/* 批发进货优惠券验证接口地址 */
	var urlCouponTest = "../../order/checkcoupon";
	/* 订单管理查订单总数 */
	var urlOrderCnt = "../../order/entrycnt";
	/* 订单管理查订单列表 */
	var urlOrderList = "../../order/entrylist";
	/* 店铺列表接口地址 */
	var urlShopList = "../../shop/shoplist";
	/* 售后列表接口地址  */
	var urlReturnList = "../../return/list";
	/* 店主审批 */
	var urlreturnapproval = "../../return/approval";
	/* 本店近期批发 */
	var urlGetGoodslistByPsam = "../../goodspool/getgoodslistbypsam";
	/* 获取店主信息 */
	var urlShopInfo = "../../shop/shopinfo";
	/* 商品上架 接口地址 */
	var urlUpShelf = "../../goodspublish/upshelf";
	/* 商品下架 接口地址 */
	var urlDownShelf = "../../goodspublish/downshelf";
	/* 获取指定用户自营商品的虚拟分类  */
	var urlQueryVircateByGoods = "../../goods/queryvircatebygoods";
	/* 获取使用的所有虚分类（即营销分类）(2B) */
	var urlgoodsqueryvirtualcat = "../../goods/queryvirtualcat";
	/* 获得平台商品详情  */
	var urlGetgoodsdetail = "../../goodspool/getgoodsdetail";
	/* 订单全部收货  */
	var urlConfirmShAll = "../../order/shouhuoall/confirmshall";
	/* 根据虚分类获取其关联的实分类  */
	var urlgoodsqueryrealcatbyvirtualcat = "../../goods/queryrealcatbyvirtualcat";
	/* 获得平台商品详情  */
	var urlQuerygoodproperty = "../../goods/querygoodproperty";
	/* 根据虚分类，获取平台商品列表 */
	var urlgoodspoollist = "../../goodspool/getgoodspoollist";
	/* 新增商品接口  */
	var urlAddGoods = "../../goods/upgoodsinfo";
	/* 消息通知接口 */
	var urlGetNewsInfo = "../../news/getInfo";
	/* 上传图片  */
	var urlUploadImg = "../../upload/uploadimg";
	/*小店管理获取本店商品列表*/
	var urlsideshopgoodslist = "../../sideshopgoods/goodslist";
	/*获取自营商品详情*/
	var urlselfgoodsdetail = "../../sideshopgoods/getselfgoodsdetail";
} else if(globalStatus == 2) {

} else if(globalStatus == 3) {
	
}