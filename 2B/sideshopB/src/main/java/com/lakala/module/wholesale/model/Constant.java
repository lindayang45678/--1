package com.lakala.module.wholesale.model;

/**
 * <p/>
 * Created by HOT.LIU on 2015/3/2.
 */
public class Constant {
    public static final String DUBBO_PARAMETER_PSAM_PREFIX = "prolist_psam_"; //dubbo服务psam参数前缀
    
    public static final String DUBBO_PARAMETER_AD_PSAM_PREFIX = "adlist_psam_"; //dubbo服务psam参数前缀

    public static final String DUBBO_PARAMETER_PSAM_PREFIX_NEW = "prolist_"; //dubbo服务psam参数前缀

    public static final String DUBBO_PARAMETER_PSAM_ALL_PREFIX_NEW = "prolist_all_"; //dubbo服务psam参数前缀

    //疯狂抢购频道商品列表参数
    public static final String KDB_ACTIVITY_FAST_PARAMETER = "activity_fast_default";

    //每周一团频道商品列表参数
    public static final String KDB_ACTIVITY_GROUP_PARAMETER = "activity_group_default";

    public static final String ALL_HEADER="alllist";//全国

    public static final String AREA_HEADER="arealist";//区

    public static final String PSAM_HEADER="psamlist";//终端

    //本地热销频道ID
    public static final String KDB_ACTIVITY_LOCAL_SALES = "25";

    //特色专场频道ID
    public static final String KDB_ACTIVITY_CHARACTERISTICS_SPECIAL = "30";

    //批发进货ID
    public static final String KDB_ACTIVITY_PURCHASE_WHOLESALE = "27";

    //疯狂抢购频道ID
    public static final String KDB_FAST_PURCHASE_CHANNEL = "28";

    //每周一团频道ID
    public static final String KDB_ACTIVITY_GROUP_CHANNEL = "29";

    //其他频道的商品是否属于疯抢
    public static final String KDB_PRODUCT_DATAIL_ACTIVITY_FAST_FLAG = "1";

    //其他频道的商品是否属于每团
    public static final String KDB_PRODUCT_DATAIL_ACTIVITY_GROUP_FLAG = "2";

    public static final Integer PRODUCT_PLATOR_SELF_FLAG = 453;

    public static final Integer PROMOTION_FLAG_STRAIGHT_DOWN_FLAG = 70;

    //APP
    public static final Integer PROMOTION_FLAG_RELEASE_CHANNEL_FLAG = 11;

    public static final String SPLIT_EXPAND_AREA = ",";//发布商品：渠道和地区分隔符号

}
