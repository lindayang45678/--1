package com.lakala.module.comm;

public interface Constant {
	
	public static final int GOODS_STATUS_ONSALE=208; //商品在售
	public static final int GOODS_STATUS_DWONSALE=209;  //商品下架
	
	public static final int GOODS_PLAT=453;  //平台商品
	public static final int GOODS_SELF=452;  //自营商品
	
	/**
	   * 收款宝参数
	   */
	  public static final String PAY_SKB_VER="20060301";
	  public static final String PAY_SKB_MACTYPE="2";//1、数字签名，2、MD5
	  public static final String PAY_SKB_URL = "lklmpos://pay?p=";//收款宝收银台地址

	  /**
	   * 收款宝测试
	   */
//	  public static final String PAY_SKB_PASSWORD="123456";
//	  public static final String PAY_SKB_MINCODE="50000300";
//	  public static final String PAY_SKB_CHANNELNO="LAKALAWAP";//渠道号
//	  public static final String PAY_SKB_MERID="test_zysd_code";
//	  public static final String PAY_SKB_KEY="12345678";


	  /**
	   * 收款宝生产
	   */
	  public static final String PAY_SKB_PASSWORD="LKLTY9IJKH4UVB8HUI3QHPHG0KAJG";
	  public static final String PAY_SKB_MERID="3I120000301";
	  public static final String PAY_SKB_MINCODE="310210";	  
	  public static final String PAY_SKB_KEY="Kk25c0sD";
	  public static final String PAY_SKB_CHANNELNO="3I120000301";//渠道号
//	  //拉卡拉收款宝刷卡支付---私钥（PKCS8格式）-------------测试
//	  public static String SKB_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJNnEgeFyBabMs6pyUChstaoyv3YklmP9V0hTFi3J4OHiVxzigULOZSWA5nbLyUYTi8fpa5ZtJHHWxeto4ANsM5cb3m+xzL+JyebxjASxvBQ5Lq9QOBbhl+zCbUJCCwvOZ2023rVBGLpLM7/ZqTX+vpm9Yn+dmctB1QWA1jHyZZ1AgMBAAECgYBJfkZ7AbT1sIPwjq8NTpIAfUBYuLafeSt5N7T9NrS428cdFW5nfWVENFevSp2C/U9eJ4VQHOHvHVrJv4WZvzm7p9iEsgj2NKdOxWsVtx00lEwOvJhSDwMZNohyDwVXwTXneQV88wNFBwoQDKk3aJtm4qxkweZrAtuZhzry8LLTMQJBANRXjMV4dRFTHwuGQ+GnHsscRkfzzXRrJ5XAEknD25RcBlGG0vDsGIdkrEW4k4Yq9sFiXCiedOQVRdhovrIFGX8CQQCxtX0j0fvGDSLBn7LDcyYHhG1+/ey0/OHcB2n3hfI57JjOf8Luh1ogewUe9FOt3I2sobFn0oSPh5Dt4Ffe4YILAkEAixdiRcPW42dQUas8ceaXvDIkM2OXNGgwgN5U3G7oObFiDitDp77vfljKo8z5DYy9IAW/GnhZV0I8AzrxlwjSHwJAMSW9Z0OyuxJ081HEcdONx/RzB/UYGvH5UQ3n26H63NQ3apzg/3EFlwgO4YG3BtWBhSXu14TcTomypmcuRiDeAwJAHY9aITvrAsrQcZag0QecKlzQfjee+XMLFtz9CXCFmamtZjvxBmgQ+a59L9Or8AfyTgVdeNWHb9cQIU2+6Q3Zew==";

	  //拉卡拉收款宝刷卡支付---私钥（PKCS8格式）-------------生产  
	  public static String SKB_PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAILtv/qdFR3YnrBBrurQVOA6cIW/iND//E0yhW9Vmh3V8yQippXs7KTMF9GJIrwP6Y6OodSX9S+/LCJVRjlu30ft8MPfhv05g6XV5fwu4j4si3DgqtxIAgYNBZOojz+1fXWarFw4QymygJoYBAuAWSO9rhCp7VkVtMFs6feI0GFFAgMBAAECgYBz35hFY1o59OnUkf0NYd2MKQxGwpGCbttLMwg21Wq1NxGdjHEkRO/Wla4KyrSL/gU1HnSI4wei/y0/IXsp/z7pY3DHrRtJUwaChBgPc5yGCBMPw3HvKlOQs2koMfezAgz6YZ4RFwT/oxD+z6uL55n69bc8f7uiKWufo0DBcDuGLQJBAP2syNmNxG/MUN8Qu9dctkSleWGKGhS/xEAe9q7R83sSBu+yRTXYVam/c6BzuZ5lQtPhc9/H+eu21vA0oyH79/MCQQCEIPULEk/ZJtHrE6VI+D2UpFcGXHXS+Yp6DTQ8zCnMQx2RTyXdmiLQeO7+FVwoODNPVOBOcA+lccjcDTwRpAfnAkAqP7O34A+eWxYBAMV4Kd3FcxXTM/ACEqo5z65gEWGO1sLG+xbhz+T8f4FfcMayZcUKitsEo0axhmMn4GxnBZMXAkAaLEUeGlSC2XO/szaMi8/2/87VTb9xOUk7AB6W5IJrGehjfmAr03qN/UaqQiTtOqS/VDrSjtQqm62EGYMKo4mFAkAasNm22K6jRP7dE711qrZ5r/a5+6/v5uNz0B8Ck8WGeoUCyw/bhP6MazVuK2MAzK0jWo/g5eHu1vni0bxNsL34";  

//	  //拉卡拉收款宝刷卡支付---公钥
//	  public static String SKB_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTZxIHhcgWmzLOqclAobLWqMr92JJZj/VdIUxYtyeDh4lcc4oFCzmUlgOZ2y8lGE4vH6WuWbSRx1sXraOADbDOXG95vscy/icnm8YwEsbwUOS6vUDgW4Zfswm1CQgsLzmdtNt61QRi6SzO/2ak1/r6ZvWJ/nZnLQdUFgNYx8mWdQIDAQAB";

}
