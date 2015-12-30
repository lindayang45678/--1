//+++++++++++++++++++++++++ Core 层插件定义，该脚本必须是在所有其它插件脚本之前引入。 +++++++++++++++++++++
(function(){
    var require = window.cordova.require;
    var define  = window.cordova.define;

    require('cordova');

    cordova.define("cordova/_native", function(require, exports, module){});
    cordova._native = require('cordova/_native');
    var _native = cordova._native;

    //------------Navigation 插件定义-----------
    _native.navigation = {};

    //setTitle 方法
    _native.navigation.setTitle = function(title){
        cordova.exec(null,null,"Navigation","setTitle",[title]);
    };

    //goBack 方法
    //Web -> Native
    _native.navigation.goBack = function(){
        cordova.exec(null,null,"Navigation","goBack",[]);
    };

    //finish 方法
    _native.navigation.finish = function(){
        cordova.exec(null,null,"Navigation","finish",[]);
    };

    //showBackButton 方法
    _native.navigation.showBackButton = function(){
        cordova.exec(null,null,"Navigation","showBackButton",[]);
    };

    //hideBackButton 方法
    _native.navigation.hideBackButton = function(){
        cordova.exec(null,null,"Navigation","hideBackButton",[]);
    };

    //showActionButton 方法
    _native.navigation.showActionButton = function(action){
        cordova.exec(null,null,"Navigation","showActionButton",[action]);
    };

    //hideActionButton 方法
    _native.navigation.hideActionButton = function(){
        cordova.exec(null,null,"Navigation","hideActionButton",[]);
    };

    //actionClick 点击事件处理
    _native.navigation.actionClick = function(){
        Application.navigation.onAction();
    };

    //showMenu 方法
    _native.navigation.showMenu = function(titles, index){
        cordova.exec(null,null,"Navigation","showMenu",[titles, index]);
    };

    //hide SubMenu
    _native.navigation.hideMenu = function(){
        cordova.exec(null, null, "Navigation", "hideMenu", []);
    };

    /**
     * Native 调用方法，点击导航条的下拉列表item回调
     * @param index 选择的第几个
     */
    _native.navigation.menuCallback = function(index){
        Application.navigation.menuCallback(index);
    };

    /**
     * Native 调用的方法，用于询问Web端是否可以返回到上一个本地页面。
     * Native -> Web
     * webview下,按下导航栏< 键会调用此方法,android下按物理返回键也会调用此方法
     * @param triggerByBackKey  如果是android下back键触发,此值为true,否则为false或 undefined
     * @returns {boolean}  返回true 不关闭webview所在activity  返回false 则将关闭webview所在activty
     */
    _native.navigation.isWebGoBack = function(isTriggerByAndroidBackKey){
        if (Application.keyboard && Application.keyboard.current &&
            Application.keyboard.current.isHidden && !Application.keyboard.current.isHidden()) {
            //如果键盘显示,则先隐藏
            Application.keyboard.current.hide();

            if (window.location.href.indexOf('index-android.html') != -1 && isTriggerByAndroidBackKey){
                return true;
            }
        }

        return Application.navigation.nativeGoback();
    };

    //退出登陆
    _native.navigation.exitApp = function () {
        cordova.exec(null, null, "Navigation", "exitApp", []);
    };

    //------------NativeInfo 插件定义-----------
    _native.nativeinfo = {};

    /* device 设备信息
     * device.Platform  平台类型 如: IOS、Android
     * device.OSVersion 系统版本
     * device.DeviceName 设备名称
     * device.IMEI       手机的IMEI，IOS 设备此项为空。
     * device.AppID      IOS 6.0 以上有此信息
     * device.ADID       IOS 设备有此信息
     */
    _native.nativeinfo.device = {};
    /* app 应用信息
     * app.Name         应用的名称
     * app.Version      应用内部版本号，如：5.0.1.12034
     * app.ShortVersion 应用显示版本号，如：5.0
     * app.BuildDateTime  应用构建日期 YYYYMMDDhhmmss。
     */
    _native.nativeinfo.app = {};

    _native.nativeinfo.get=function(){
        cordova.exec(function(info){
            //获取设备、应用信息成功，设置相应的对象值
            var nativeinfo = cordova._native.nativeinfo;
            nativeinfo.device = info.device;
            nativeinfo.app = info.app;
        },null,"NativeInfo","get",[]);
    };
})();

//+++++++++++++++++++++++++++++ PaymentPlatform 层插件定义。++++++++++++++++++++++++++++++++++++
(function(){
    var _native  = cordova._native;

    //------------Dialog 插件定义-----------
    _native.dialog = {};

    /**
     *  显示只有一个按钮的对话框，当用户按下按钮时回调函数被调用。
     *
     *  @param title   标题 （string）
     *  @param message 提示文本 （string）
     *  @param callback 回调函数
     *
     *  @return void
     */
    _native.dialog.alert = function(title,message,callback){
        cordova.exec(callback,null,"Dialog","alert",[title,message]);
    };

    /**
     *  显示一个确认对话框（两个按钮），当用户按下按钮时回调函数被调用。
     *
     *  @param title   标题 （string）
     *  @param message 提示文本 （string）
     *  @param callback 回调函数 (function(buttonIndex){})
     *  @param but1Title (可选)第一个按钮的 Title，默认 “确定”;
     *  @param but2Title (可选)第二个按钮的 Title，默认 “取消”;
     *  @return void
     */
    _native.dialog.confirm = function(title,message,callback,but1Title,but2Title){
        cordova.exec(callback,null,"Dialog","confirm",[title,message,but1Title,but2Title]);
    };

    /**
     *  显示一个等待处理的（Loading)消息框。

     *  @param message 消息文本
     *
     *  @return void
     */
    _native.dialog.showProgress = function(message){
        cordova.exec(null,null,"Dialog","showProgress",[message]);
    };

    _native.dialog.hideProgress = function(){
        cordova.exec(null,null,"Dialog","hideProgress",[]);
    };

    /**
     *
     *显示一个输入验证码对话框
     */
    _native.dialog.commonInput = function(data,success,cancel){
        cordova.exec(success,cancel,"Dialog","commonInput",data);
    };

    _native.dialog.imageInput = function(data,imageData,success,cancel){
        cordova.exec(success,cancel,"Dialog","imageInput",[data,imageData]);
    };

    //支付密码输入
    _native.dialog.payPassword = function(data,success,cancel){
        cordova.exec(success,cancel,"Dialog","payPassword",[data]);
    };

    _native.dialog.passwordInput = function(data,success,cancel){
        cordova.exec(success,cancel,"Dialog","passwordInput",[data]);
    };

    _native.dialog.customImgDialog = function (title,img,msg,showInput,success) {
        cordova.exec(success , null , "Dialog" , "customImgDialog" , [title ,img, msg, showInput]);
    };

    /**
     *显示一个选择列表
     */
    _native.dialog.showSelect = function(title,mutilselect,data,success,cancel){
        cordova.exec(success,cancel,"Dialog","showSelect",[title,mutilselect,data]);
    };

    _native.dialog.selectPayment = function(title,data,success, cancel) {
        cordova.exec(success, cancel, "Dialog", "selectPayment", [title,data]);
    };
    /** webview对话框 */
    _native.dialog.webDialog = function (data,success){
        cordova.exec(success,null,"Dialog","webDialog",[data]);
    };
    /** 日期对话框 */
    _native.dialog.dateDialog = function (data,success,cancel){
        cordova.exec(success,cancel,"Dialog","dateDialog",[data]);
    };

    /**
     *  消息提示
     *
     *  @param message 消息文本
     *
     *  @return void
     */
    _native.dialog.toast = function(message){
        cordova.exec(null,null,"Dialog","toast",[message]);
    };

    //------------httprequest 插件定义-----------
    _native.httprequest = {};

    //发送http请求
    _native.httprequest.send = function(config,parameter,success,failed){
        cordova.exec(success,failed,"HttpRequest","request",[config,parameter]);
    };

    _native.httprequest.cancel = function(queueIndex){
        cordova.exec(null,null,"HttpRequest","cancel",[queueIndex]);
    };

    //------------ session 插件定义 --------------
    _native.session = {};
    _native.session.getSeries = function(cl){
        cordova.exec(cl,null,"Session","getSeries",[]);
    };

    _native.session.getSession = function(cl){
        cordova.exec(cl,null,"Session","getSession",[]);
    };

    _native.session.getUser = function(success){
        cordova.exec(success,null,"Session","getUser",[]);
    };

    _native.session.setUser = function(user){
        cordova.exec(null,null,"Session","setUser",[user]);
    };

    //-------BusinessParameter 插件定义-------------
    _native.businessparameter={};
    _native.businessparameter.getAction=function(success){
        cordova.exec(success,null,"BusinessParameter","getAction",[]);
    };

    _native.businessparameter.getData=function(result){
        cordova.exec(result,null,"BusinessParameter","getData",[]);
    };

    _native.businessparameter.getPageData=function(result){
        cordova.exec(result,null,"BusinessParameter","getPageData",[]);
    };

    //-------businesslauncher 插件定义-------------
    _native.businesslauncher={};
    _native.businesslauncher.startPage = function(id, data,isFinishSelf) {
        isFinishSelf = isFinishSelf === undefined ? false : isFinishSelf;
        cordova.exec(null, null, "BusinessLauncher", "startPage", [id, data,isFinishSelf]);
    };

    _native.businesslauncher.startPageForResult = function(id, data,result,requestCode) {
        cordova.exec(result, null, "BusinessLauncher", "startPageForResult", [id, data,requestCode]);
    };

    _native.businesslauncher.setResult = function(status,data) {
        cordova.exec(null, null, "BusinessLauncher", "setResult", [status, data]);
    };

    _native.businesslauncher.returnPage = function(level) {
        cordova.exec(null, null, "BusinessLauncher", "returnPage", [level]);
    };

    _native.businesslauncher.openWithNavigation = function(tag,data,index) {
        cordova.exec(null, null, "BusinessLauncher", "openWithNavigation", [tag,data,index]);
    };

    //------phoneBook 插件定义-----------------
    _native.phonebook={};
    _native.phonebook.get=function(args,success){
        cordova.exec(success,null,"PhoneBook","get",[args]);
    };
    _native.phonebook.call = function(data) {
        cordova.exec(null, null, "PhoneBook", "call", [data]);
    };
    _native.phonebook.getName = function(success,phoneNum){
        cordova.exec(success,null,"PhoneBook","getName",[phoneNum]);
    };

    //------------DateBase 插件定义-----------
    _native.database = {};

    //执行SQL操作
    _native.database.exec=function(cmd,args,success,error){
        cordova.exec(success,error,"DBPlugin","exec",[cmd,args]);
    };

    //查询数据库数据
    _native.database.select=function(cmd,args,success,error){
        cordova.exec(success,error,"DBPlugin","select",[cmd,args]);
    };

    //---------- Analytics 插件 ----------------
    _native.analytics = {};

    _native.analytics.event = function(eventId, label, acc, origin) {
        cordova.exec(null,null,"Analytics","event",[eventId, label, acc, origin]);
    };

    //---------- NotificationCenter 插件 ----------------
    _native.notificationcenter = {};
    _native.notificationcenter.commonNotify = function(id, data) {
        cordova.exec(null,null,"NotificationCenter","commonNotify",[id, data]);
    };
    //---------- 定位插件 ----------------
    _native.location = {};
    _native.location.location = function(success,cancel){
        cordova.exec(success,cancel,"Location","location",[]);
    };

    _native.location.locationDetail = function(success,cancel){
        cordova.exec(success,cancel,"Location","locationDetail",[]);
    };

    //---------------- 键盘插件 ----------------
    _native.softKeyBoard = {};
    _native.softKeyBoard.show = function(){
        cordova.exec(null,null,"SoftKeyBoard","show",[]);
    };
    _native.softKeyBoard.hide = function(){
        cordova.exec(null,null,"SoftKeyBoard","hide",[]);
    };

    //---------- Camera 插件 ----------------
    _native.camera = {};

    _native.camera.picker = function(dict, success, failure) {
        cordova.exec(success, failure, "Camera", "picker", [dict]);
    };

    _native.camera.clear = function() {
        cordova.exec(null, null, "Camera", "clear", []);
    };

    //------配置文件
    _native.config = {};
    /**
     * 配置文件读取
     *
     * @param filename 文件名
     * @param result   回调函数
     */
    _native.config.read = function(filename, result){
        cordova.exec(result, null, "ConfigFile", "read", [filename]);
    };

    //---------  系统插件 ----------
    _native.system = {};

    _native.system.shake = function(){
        cordova.exec(null,null,"System","shake",[]);
    };

    _native.system.sound = function(){
        cordova.exec(null,null,"System","sound",[]);
    };

    _native.system.token = function(result){
        cordova.exec(result,null,"System","token",[]);
    };

    _native.system.deviceInfo = function(result){
        cordova.exec(result,null,"System","deviceInfo",[]);
    };

    _native.system.remotePushInfo = function(result){
        cordova.exec(result,null,"System","remotePushInfo",[]);
    };

    //---------  保存信息插件 ----------
    _native.saveInfo = {};

    /**
     * 这对方法是不区分用户的
     */
    _native.saveInfo.set = function(key, value){
        cordova.exec(null, null, "SaveInfo", "set", [key, value]);
    };
    _native.saveInfo.get = function(key, result, defaultValue){
        cordova.exec(result, null, "SaveInfo", "get", [key, defaultValue]);
    };

    //---------- 分享插件 -------------
    _native.share = {};

    _native.share.share = function(title,text,imgUrl,result,error){
        cordova.exec(result,error,"Share","share",[title,text,imgUrl]);
    }

    //---------- 支付插件-------------
    _native.pay = {};

    _native.pay.pay = function(paychanel,paydata,result,error){
        cordova.exec(result,error,"Pay","pay",[paychanel,paydata]);
    }
})();