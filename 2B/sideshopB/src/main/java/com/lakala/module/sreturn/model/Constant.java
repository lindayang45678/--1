package com.lakala.module.sreturn.model;

/**
 * Created by HOT.LIU on 2015/3/3.
 */
public class Constant {

    /**
     * 换货退货数据字典
     */
    public static final String RETURN_GOODS_TYPE = "296"; //退货
    public static final String EXCHANG_GOODS_TYPE = "295"; //换货
    public static final String RETURN_CANCEL_ORDER = "309"; //取消订单

    public static final String RETURN_GOODS_TYPE_CH = "退货"; //退货
    public static final String EXCHANG_GOODS_TYPE_CH = "换货"; //换货
    public static final String RETURN_CANCEL_ORDER_CH = "取消订单"; //取消订单

    /**
     * 售后主表备注
     */
    public static final String RETURN_DESCRIPTION = "APP订单售后申请";
    
    /**
     * 取消订单
     */
    public static final String RETURN_CANCEL_ORDER_DESCRIPTION = "APP取消订单";

    /**
     * 审批类型
     */
    public static final String CUSTOMER_SERVICE_REFUND_APPLICATION_APPROVAL = "289"; //售后退款申请审批

    /**
     * 售后申请取货方式
     */
    public static final String EXPRESS_PICK_UP = "快递上门取货";

    public static final String USER_SEND_BACK = "用户自行寄回";

    /**
     * 售后申请运费承担方
     */
    public static final String PROVIDER_BEAR = "供应商承担";

    public static final String USER_SHALL_BEAR = "用户承担";

    /**
     * 售后申请来源
     **/

    public static final String RETRURN_FROM = "APP";

    /**
     * 发货性质(或退换货订单标识 ）
     */
    public static final String DELIVERY_PROPERTIES_RETURN_GOODS = "114"; //退货
    public static final String DELIVERY_PROPERTIES_CUSTOMER_SERVICE_FOR_NEW = "293"; //售后换新

    /**
     * 退换货状态
     */
    public static final String RETURN_STATUS_REPLACEMENT_OF = "117"; //换货中
    public static final String RETURN_STATUS_HAVE_REPLACEMENT = "118"; //已换货
    public static final String RETURN_STATUS_RETURN_OF = "119"; //退货中
    public static final String RETURN_STATUS_HAS_BEEN_RETURNED = "120"; //已退货

    /**公用审批状态*/
    public static final Integer NOT_AGREE=165;  //审批不通过
    public static final Integer AGREE=164; //审批通过，同意该申请
    public static final Integer WAIT=163; //待审批

    public static final String AGREE_Y = "Y";
    public static final String NOT_ATREE_N = "N";

    /**
     * 售后审批状态字典编码
     * */
    public static final String RETURN_STATUS_THE_COMPLETED_CODE = "301";//已完结
    public static final String RETURN_STATUS_THROUGH_REVIEW_PENDING_RECEIPT_CODE = "300";//审核通过待收货
    public static final String RETURN_STATUS_CANCEL_USER_CODE = "302";//用户自行取消
    public static final String RETURN_STATUS_REFUND_EXAMINATION_NOT_THROUGH_CODE = "304";//退款审核不通过
    public static final String RETURN_STATUS_TO_BE_AUDITED_CODE = "303";//退款待审核
    public static final String RETURN_STATUS_REFUND_COMPLETED_CODE = "306";//退款完成
    public static final String RETURN_STATUS_TO_BE_REFUND_CODE = "305";//待退款
    public static final String RETURN_STATUS_PENDING_AUDIT_CODE = "298";//待审核
    public static final String RETURN_STATUS_EXAMINATION_NOT_THROUGH_CODE = "299";//审核不通过
    public static final String RETURN_STATUS_CANCEL_ORDER_CODE = "309";//取消订单
    public static final String PAYMENT_FOR_GOODS_CODE = "348";//货款
    public static final String RETURN_EXPRESS_FEE_CODE = "347";//退还快递费
    public static final String RETRURN_FROM_APP2B = "APP2B";//售后申请来源

    /**
     * 订单状态
     */
    public static final String ORDER_STATUS_NON_DELIVERY = "99"; //未发货

    /**
     * 订单为商品
     * */
    public static final String ORDER_GOODS_GIFT_STATE_GOODS = "202";//

    //生鲜暂存
    public static final Integer RETURN_STATUS_TEMP_STORE_GOODS_FLAG_CODE = 378; //暂存商品

    /**
     * 订单为赠品
     * */
    public static final String ORDER_GOODS_GIFT_STATE_GIFT = "204";//

    /**
     * 订单退货状态
     * */
    public static final String ORDER_RETURN_STATUS = "112";//

    /**
     * 订单有效状态
     * */
    public static final String ORDER_INVALID_STATE = "116";//

    /**
     *  订单取消状态
     * */
    public static final String ORDER_CANCEL_STATE = "136";


}
