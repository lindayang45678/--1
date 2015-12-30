package com.lakala.module.goods.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.lakala.base.model.Realcate;
import com.lakala.base.model.Tgoodinfo;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.base.model.TgoodskuinfopoolWithBLOBs;
import com.lakala.base.model.Timages;
import com.lakala.base.model.VirtualCate;
import com.lakala.exception.LakalaException;
import com.lakala.fileupload.handler.MultipartHttpFileTransfer;
import com.lakala.model.ProductDetail;
import com.lakala.model.ProductDetailedInformation;
import com.lakala.model.TerminalChannel;
import com.lakala.model.TerminalProduct;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.comm.RedisServiceImpl;
import com.lakala.module.goods.cache.CategoryExt;
import com.lakala.module.goods.service.SideshopGoodsService;
import com.lakala.module.goods.vo.GoodsPoolInfoVO;
import com.lakala.module.goods.vo.GoodsPoolSkuInfoVO;
import com.lakala.module.goods.vo.ImageInfoVO;
import com.lakala.module.goods.vo.SelfGoodsDetailInput;
import com.lakala.module.goods.vo.SideShopGoodsListInput;
import com.lakala.module.goods.vo.Tdiscount;
import com.lakala.module.goods.vo.in.SideShopBListForKeyInPut;
import com.lakala.module.mongo.service.SideShopBMongoService;
import com.lakala.module.promotion.vo.Goodsku;
import com.lakala.module.promotion.vo.in.PromotionListInPut;
import com.lakala.module.promotion.vo.out.PromotionListOutPut;
import com.lakala.module.wholesale.model.Constant;
import com.lakala.module.wholesale.model.OutListModel;
import com.lakala.module.wholesale.service.WholesaleService;
import com.lakala.util.BussConst;
import com.lakala.util.HttpUtil;
import com.lakala.util.MongoInterfaceUrl;
import com.lakala.util.ReturnMsg;
import com.lakala.util.StringUtil;

/**
 * Created by HOT.LIU on 2015/2/28.
 */
@Service
public class SideshopGoodsServiceImpl extends RedisServiceImpl implements SideshopGoodsService {
    Logger logger = Logger.getLogger(SideshopGoodsServiceImpl.class);
    
    @Autowired
    private WholesaleService wholesaleService;
    

    @Autowired
    private com.lakala.mapper.r.sdbmediastatistics.SdbMediaStatisticsMapper sdbMediaStatisticsMapper_R;
    
    @Autowired
    private com.lakala.mapper.r.goodspublishkdbskuo2o.TgoodsPublishKdbSkuO2oMapper tgoodsPublishKdbSkuO2oMapper_R;

    @Autowired
    private com.lakala.mapper.r.goodspublishforkdb.GoodsPublishForKDBMapper goodsPublishForKDBMapper_R;

    @Autowired
    private com.lakala.mapper.r.goods.TgoodskuinfoMapper tgoodskuinfoMapper_R;
    
    @Autowired
    private com.lakala.mapper.r.goods.TdiscountMapper tdiscountMapper_R;
    
    @Autowired
    private com.lakala.mapper.r.goods.TgoodsinfoMapper tgoodsinfoMapper_R;
    
    @Autowired
	private com.lakala.mapper.r.goods.TimagesMapper timagesMapper_R;
    @Autowired
    private com.lakala.mapper.r.goods.TgoodskuinfopoolMapper tgoodskuinfopoolMapper_R;
    
    @Autowired
    private SideShopBMongoService sideShopBMongoService;
    
    @Autowired
    private com.lakala.mapper.r.realcat.RealcateMapper  realcateMapper_R;

    @Autowired
    private com.lakala.mapper.r.virtualcat.VirtualCateMapper virtualCateMapper_R;
    
    @Autowired
    private CategoryExt categoryExt;
    
    @Autowired
    private MongoInterfaceUrl mongoInterfaceUrl;
    @Autowired
    private MultipartHttpFileTransfer transfer;

    @Override
	public ObjectOutput getGoodsByPsamAndVirtualCatId(SideShopGoodsListInput inputModel) throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        List<ProductDetailedInformation> list = new ArrayList<ProductDetailedInformation>();
        List<Map<String, ProductDetailedInformation>> list1 = new ArrayList<Map<String, ProductDetailedInformation>>();
        List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();

        if (inputModel == null || StringUtils.isEmpty(inputModel.getPsam()) || StringUtils.isEmpty(inputModel.getChannelid()) 
        		|| StringUtils.isEmpty(inputModel.getVirtualcatid()) || StringUtils.isEmpty(inputModel.getType())) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }

        try {
            TerminalProduct tpd = this.getTerminalProductByParam(inputModel.getPsam(), inputModel.getChannelid());
            if (tpd != null) {
                List<TerminalChannel> terminalChannels = tpd.getTerminalChannels();
                for (TerminalChannel terminalChannel : terminalChannels) {
                    productDetails = terminalChannel.getProductList();
                    for (ProductDetail productDetail : productDetails) {
                        ProductDetailedInformation productDetailedInformation = productDetail.getProductDetailedInformation();
                     
                        if (!"all".equals(inputModel.getVirtualcatid())) {
                            Arrays.sort(productDetail.getVirtualCatIds());
                            int index = Arrays.binarySearch(productDetail.getVirtualCatIds(), inputModel.getVirtualcatid());
                            if (index > -1) {
                                if (idMapping.get(productDetailedInformation.getTgoodinfoid()) == null) {
                                    idMapping.put(productDetailedInformation.getTgoodinfoid(), productDetailedInformation);
                                } else {
                                    if (new BigDecimal(idMapping.get(productDetailedInformation.getTgoodinfoid()).getSaleprice_o2o()).compareTo(new BigDecimal(productDetailedInformation.getSaleprice_o2o())) == 1) {
                                        idMapping.put(productDetailedInformation.getTgoodinfoid(), productDetailedInformation);
                                    }
                                }
                            }
                            
                        } else {
                            if (idMapping.get(productDetailedInformation.getTgoodinfoid()) == null) {
                                idMapping.put(productDetailedInformation.getTgoodinfoid(), productDetailedInformation);
                            } else {
                                if (new BigDecimal(idMapping.get(productDetailedInformation.getTgoodinfoid()).getSaleprice_o2o()).compareTo(new BigDecimal(productDetailedInformation.getSaleprice_o2o())) == 1) {
                                    idMapping.put(productDetailedInformation.getTgoodinfoid(), productDetailedInformation);
                                }
                            }
                        }
                        
                        
                        
                        
                        	
                    }
                }
            } else {
                data.set_ReturnCode(ReturnMsg.CODE_ERR_000003);
                data.set_ReturnMsg("没有找到该频道下的商品！");
                return data;
            }
            //多个sku相同只取价格最低显示
            list = new ArrayList<ProductDetailedInformation>(idMapping.values());
          
            Collections.sort(list, new Comparator<ProductDetailedInformation>() {
                public int compare(ProductDetailedInformation arg0, ProductDetailedInformation arg1) {
                    return arg1.getSort().compareTo(arg0.getSort());
                }
            });
            
            
            List<ProductDetailedInformation> list3 = new ArrayList<ProductDetailedInformation>();
            
            int page = inputModel.getPage();
            int pagesize = inputModel.getPageSize();
            
            
            ObjectOutput info = null;
            
            
            info = this.getPageProductDetile(list, inputModel.getPsam(), inputModel.getType());
            
            List<ProductDetailedInformation> listn = (List<ProductDetailedInformation>) info.get_ReturnData();
            
            OutListModel outListModel = new OutListModel();
            outListModel.setCount(listn.size());
            if("".equals(inputModel.getSearchparm())){
            	if(listn == null || listn.size() <=0){
                  	 data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                       data.set_ReturnData(new ArrayList());
                       return data;
                  }
            	int num =pagesize;
            	if(page >1){
            		num  = page*pagesize;
            	}
            	
            	if(page == 1){
            		if(listn != null && listn.size() <= pagesize){
                    	   list3.addAll(listn);
        	   	        }else{
        	   	        	
        	   	        	if(listn.size() >= (page-1)*pagesize && listn.size() < page*pagesize){
                    			for(int n=(page-1)*pagesize ; n<listn.size() ; n++){
          	   	   	        		list3.add(listn.get(n));
          	   	   	        	}
                    		}else{
                    			for(int n=(page-1)*pagesize ; n<page*pagesize ; n++){
         	   	   	        		list3.add(listn.get(n));
         	   	   	        	}
                    		}
        	   	        	
        	   	        }
            	}else{
            		if(listn.size() <= (page-1)*pagesize){
            			 data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
            				outListModel.setList(new ArrayList());
                         data.set_ReturnData(outListModel);
                         return data;
            		}
            		if(listn.size() >= (page-1)*pagesize && listn.size() < page*pagesize){
            			for(int n=(page-1)*pagesize ; n<listn.size() ; n++){
  	   	   	        		list3.add(listn.get(n));
  	   	   	        	}
            		}else{
            			for(int n=(page-1)*pagesize ; n<page*pagesize ; n++){
 	   	   	        		list3.add(listn.get(n));
 	   	   	        	}
            		}
  	   	   	        	
            	}
                
                
                  
               	outListModel.setList(list3);
               	
            }else{

            	outListModel.setList(list);
            }
            
            data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
            data.set_ReturnData(outListModel);
            return data;
        } catch (LakalaException e) {
            throw new LakalaException(e);
        }
    }

	@Override
	public ObjectOutput searchGoods(SideShopGoodsListInput inputModel) throws LakalaException{
        ObjectOutput data = new ObjectOutput();
        List<ProductDetailedInformation> productDetailedInformationList = new ArrayList<ProductDetailedInformation>();

        if (StringUtils.isEmpty(inputModel.getPsam()) || StringUtils.isEmpty(inputModel.getChannelid()) || StringUtils.isEmpty(inputModel.getVirtualcatid())) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }

        try {
            data = this.getGoodsByPsamAndVirtualCatId(inputModel);
            Map<String, Object> map = new HashMap<String, Object>();
            /*List<ProductDetailedInformation> listss = null;
            map.put("psamCode", psamCode);
            map.put("searchparm", searchparm);
            if(type.equals("0")){
            	 listss = tgoodskuinfoMapper_R.getSoldoutList(map);
            }*/
           
            if (ReturnMsg.CODE_ERR_000003.equals(data.get_ReturnCode())) {
                return data;
            } else if (ReturnMsg.CODE_SUCCESS.equals(data.get_ReturnCode())) {
            	OutListModel outListModel = (OutListModel) data.get_ReturnData();
                List<ProductDetailedInformation> list = (List<ProductDetailedInformation>) outListModel.getList();
                for (ProductDetailedInformation productDetailedInformation : list) {
                    if (productDetailedInformation.getGoodname().indexOf(inputModel.getSearchparm()) != -1) {
                        productDetailedInformationList.add(productDetailedInformation);
                    }
                }
            }

            Collections.sort(productDetailedInformationList, new Comparator<ProductDetailedInformation>() {
                public int compare(ProductDetailedInformation arg0, ProductDetailedInformation arg1) {
                    return arg1.getSort().compareTo(arg0.getSort());
                }
            });
            OutListModel out = new OutListModel();
            out.setCount(productDetailedInformationList.size());
            out.setList(productDetailedInformationList);
            data.set_ReturnData(out);
            return data;
        } catch (LakalaException e) {
            throw new LakalaException(e);
        }
    }

	private TerminalProduct getTerminalProductByParam(String paramString, String channelCode) throws LakalaException {
        Map<Long, ProductDetail> idMapping = new HashMap<Long, ProductDetail>();
        Map<String, TerminalChannel> channelMapping = new HashMap<String, TerminalChannel>();
        idMapping.clear();
        channelMapping.clear();
        TerminalProduct allGoodsTpd = new TerminalProduct();
        TerminalProduct areaGoodsTpd = new TerminalProduct();
        TerminalProduct terminalGoodsTpd = new TerminalProduct();
        List<TerminalChannel> allTerminalChannels = null;
        List<TerminalChannel> areaTerminalChannels = null;
        List<TerminalChannel> terminalTerminalChannels = null;
        TerminalProduct returnGoodsTpd = new TerminalProduct();
        List<TerminalChannel> terminalChannelList = new ArrayList<TerminalChannel>();
        try {
            Map<String, Object> map = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByPsam(paramString);
            if (paramString.toLowerCase().startsWith("cbc8")) {
                allGoodsTpd = (TerminalProduct) this.selectObjectByKey(Constant.ALL_HEADER + map.get("term_fbtype"), Constant.DUBBO_PARAMETER_PSAM_PREFIX_NEW + channelCode + "_" + (String) map.get("term_fbtype") + "_all");
                areaGoodsTpd = (TerminalProduct) this.selectObjectByKey(Constant.AREA_HEADER + map.get("term_fbtype"), Constant.DUBBO_PARAMETER_PSAM_PREFIX_NEW + channelCode + "_" + (String) map.get("term_fbtype") + "_" + map.get("city_area_no"));
                terminalGoodsTpd = (TerminalProduct) this.selectObjectByKey(Constant.PSAM_HEADER + map.get("term_fbtype"), Constant.DUBBO_PARAMETER_PSAM_PREFIX_NEW + channelCode + "_" + (String) map.get("term_fbtype") + "_" + paramString);
            }
            if (paramString.toLowerCase().startsWith("app")) {
                terminalGoodsTpd = (TerminalProduct) this.selectObjectByKey(Constant.PSAM_HEADER + map.get("term_fbtype"), Constant.DUBBO_PARAMETER_PSAM_PREFIX_NEW + channelCode + "_" + (String) map.get("term_fbtype") + "_" + paramString);
            }

            // /如果为null的话就说明该终端没有开通MessageBaseInput
            if (allGoodsTpd == null && areaGoodsTpd == null && terminalGoodsTpd == null) {
                return null;
            }
            if (allGoodsTpd != null) {
                allTerminalChannels = allGoodsTpd.getTerminalChannels();
            }
            if (areaGoodsTpd != null) {
                areaTerminalChannels = areaGoodsTpd.getTerminalChannels();
            }
            if (terminalGoodsTpd != null) {
                terminalTerminalChannels = terminalGoodsTpd.getTerminalChannels();
            }

            //把所有的商品放到map中以skuid为key去重，最终以终端中的商品为准
            //把全部商品放入idMapping
            if (allTerminalChannels != null) {
                for (TerminalChannel terminalChannel : allTerminalChannels) {
                    List<ProductDetail> productDetails = terminalChannel.getProductList();
                    for (ProductDetail productDetail : productDetails) {
                        idMapping.put(productDetail.getGoodSkuInfoId(), productDetail);
                    }
                    List<ProductDetail> productDetailList = new ArrayList(idMapping.values());
                    terminalChannel.setProductList(productDetailList);
                    channelMapping.put(terminalChannel.getNetChannelId(), terminalChannel);
                }
            }

            if (areaTerminalChannels != null) {
                //把所在区的商品放到idMapping
                for (TerminalChannel terminalChannel : areaTerminalChannels) {
                    List<ProductDetail> productDetails = terminalChannel.getProductList();
                    for (ProductDetail productDetail : productDetails) {
                        idMapping.put(productDetail.getGoodSkuInfoId(), productDetail);
                    }
                    List<ProductDetail> productDetailList = new ArrayList(idMapping.values());
                    terminalChannel.setProductList(productDetailList);
                    channelMapping.put(terminalChannel.getNetChannelId(), terminalChannel);
                }
            }

            if (terminalTerminalChannels != null) {
                //把终端商品放到idMapping
                for (TerminalChannel terminalChannel : terminalTerminalChannels) {
                    List<ProductDetail> productDetails = terminalChannel.getProductList();
                    for (ProductDetail productDetail : productDetails) {
                        idMapping.put(productDetail.getGoodSkuInfoId(), productDetail);
                    }
                    List<ProductDetail> productDetailList = new ArrayList(idMapping.values());
                    terminalChannel.setProductList(productDetailList);
                    channelMapping.put(terminalChannel.getNetChannelId(), terminalChannel);
                }
            }
            idMapping.clear();
            for (TerminalChannel terminalChannel : channelMapping.values()) {
                List<ProductDetail> productDetails = terminalChannel.getProductList();
                for (ProductDetail productDetail : productDetails) {
                    productDetail.setProductDetailedInformation(null);
                    productDetail.getProductDetailedInformation().setSort(productDetail.getSort());
                    productDetail.getProductDetailedInformation().setId(String.valueOf(productDetail.getO2oid()));
                    productDetail.getProductDetailedInformation().setGoodname(productDetail.getGoodName());
                    productDetail.getProductDetailedInformation().setGoodintroduce(productDetail.getGoodIntroduce());
                    productDetail.getProductDetailedInformation().setGoodbigpic(productDetail.getGoodBigPic());
                    productDetail.getProductDetailedInformation().setPromotionPrice(productDetail.getPromotionPrice());
                    productDetail.getProductDetailedInformation().setMarketprice(String.valueOf(productDetail.getMarketPrice() == null ? new BigDecimal("0").setScale(2, BigDecimal.ROUND_HALF_UP) : productDetail.getMarketPrice().setScale(2, BigDecimal.ROUND_HALF_UP)));
                    productDetail.getProductDetailedInformation().setSaleprice_o2o(String.valueOf(productDetail.getSalePrice()));
                    productDetail.getProductDetailedInformation().setTgoodinfoid(String.valueOf(productDetail.getGoodInfoId()));
                    productDetail.getProductDetailedInformation().setTgoodskuinfoid(String.valueOf(productDetail.getGoodSkuInfoId()));
                    productDetail.setGoodName(null);
                    productDetail.setGoodBigPic(null);
                    productDetail.setSalePrice(null);
                    productDetail.setGoodIntroduce(null);
                    productDetail.setMarketPrice(null);
                }
            }

            terminalChannelList = new ArrayList<TerminalChannel>(channelMapping.values());
            returnGoodsTpd.setTerminalChannels(terminalChannelList);
            //生成终端所有虚拟类目
            returnGoodsTpd.setChannelVirtualCatByTerminalChannel();
            return returnGoodsTpd;
        } catch (Exception e) {
            throw new LakalaException(e);
        }
    }

	@Override
	public ObjectOutput getPageProductDetile(
			List<ProductDetailedInformation> list, String psamCode, String type)
			throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        List<String> o2oList = new ArrayList<String>();
        List<String> skuList = new ArrayList<String>();
        List<ProductDetailedInformation> returnList = new ArrayList<ProductDetailedInformation>();
        List<ProductDetailedInformation> ptList = new ArrayList<ProductDetailedInformation>();
        List<ProductDetailedInformation> zyList = new ArrayList<ProductDetailedInformation>();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();

        if (list.isEmpty() || list == null || StringUtils.isEmpty(psamCode) || StringUtils.isEmpty(type)) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }

        try {
        	
        	Tdiscount tdis = this.tdiscountMapper_R.selectTdiscount();
        	
        	BigDecimal store = new BigDecimal(tdis.getStore());
            /*for (ProductDetailedInformation productDetailedInformation : list) {
                o2oList.add("product_o2o_" + productDetailedInformation.getId());
                skuList.add(productDetailedInformation.getTgoodskuinfoid());
            }

            List<byte[]> bytes = redis.selectListByKeys(o2oList);
            for (byte[] productByte : bytes) {
                if (productByte != null) {
                    String productInfo = new String(productByte, "UTF-8");
                    ProductDetailedInformation productDetailedInformation = JSON.parseObject(productInfo, ProductDetailedInformation.class);
                    idMapping.put(productDetailedInformation.getId(), productDetailedInformation);
                }
            }
            for (ProductDetailedInformation productDetailedInformation : list) {
                if (idMapping.get(productDetailedInformation.getId()) != null) {
                    idMapping.get(productDetailedInformation.getId()).setSort(productDetailedInformation.getSort());
                }
            }
            list = new ArrayList<ProductDetailedInformation>(idMapping.values());*/

           // List<Map<String, Object>> listMap = tgoodsPublishKdbSkuO2oMapper_R.getSkuStockAndSoldSkuStock(skuList);
            for (ProductDetailedInformation productDetailedInformation : list) {
              //  for (Map<String, Object> map : listMap) {
           //         if (productDetailedInformation.getTgoodskuinfoid().equals(String.valueOf(map.get("tGoodSkuInfoId")))) {
                    	
                    	com.lakala.base.model.Tgoodskuinfo skuinfo = this.tgoodskuinfoMapper_R.selectByPrimaryKey(Integer.parseInt(String.valueOf(productDetailedInformation.getTgoodskuinfoid())));
//                        productDetailedInformation.setStore(map.get("store") == null ? 0 : ((BigDecimal) map.get("store")).doubleValue());
//                        productDetailedInformation.setSoldstore(map.get("soldStore") == null ? 0 : ((BigDecimal) map.get("soldStore")).doubleValue());
//                        productDetailedInformation.setPlatorself( (int) map.get("platorself"));
//                        productDetailedInformation.setDistributionFlag((int) map.get("distributionFlag"));
//                        productDetailedInformation.setTempstoregoodsflag((int) map.get("tempStoreGoodsFlag"));
                        productDetailedInformation.setFreshgoodsflag(skuinfo.getIsfreshfood());
                        
                      
                        if(null != skuinfo.getTgoodinfopoolid() && skuinfo.getTgoodinfopoolid() > 0){
                        	productDetailedInformation.setOnekeyupload(1);
                        }else{
                        	productDetailedInformation.setOnekeyupload(0);
                        }
                      //  int platform =  (int) map.get("platorself");
                        
                      //  BigDecimal jsj = (BigDecimal) map.get("balancePrice");
                        BigDecimal jsj = new BigDecimal(productDetailedInformation.getBalancePrice());
                        
                        BigDecimal xsj = new BigDecimal(productDetailedInformation.getSaleprice_o2o() == null ? "0.00" : productDetailedInformation.getSaleprice_o2o());
                        
                        BigDecimal frjs = new BigDecimal(productDetailedInformation.getDistributeProfitBase());
                        
                        BigDecimal yqsy = xsj.subtract(jsj).multiply(store.divide(new BigDecimal(100)).multiply(frjs.divide(new BigDecimal(100)))).setScale(2,   BigDecimal.ROUND_HALF_UP);
                        if(yqsy.compareTo(new BigDecimal(0)) < 0){
                        	yqsy = new BigDecimal(0).setScale(2,   BigDecimal.ROUND_HALF_UP);
                        }
                        productDetailedInformation.setYqsy(yqsy);
//                        productDetailedInformation.setSaleflag(0);
                        // 商品自营平台标识。自营-452，平台-453
                        if(productDetailedInformation.getPlatorself() == 452){
                        	
                        	zyList.add(productDetailedInformation);
                        }else{
                        	
                        	ptList.add(productDetailedInformation);
                        }
                        
                 //   }
                //}
            }

            Collections.sort(ptList, new Comparator<ProductDetailedInformation>() {
                public int compare(ProductDetailedInformation arg0, ProductDetailedInformation arg1) {
                    return arg1.getSort().compareTo(arg0.getSort());
                }
            });
            Collections.sort(zyList, new Comparator<ProductDetailedInformation>() {
                public int compare(ProductDetailedInformation arg0, ProductDetailedInformation arg1) {
                    return arg1.getSort().compareTo(arg0.getSort());
                }
            });
            data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
            if(type.equals("0")){
            	 data.set_ReturnData(zyList);
            }else{
            	 data.set_ReturnData(ptList);
            }
           
            return data;
        } catch (Exception e) {
            throw new LakalaException(e);
        }
    }

	@Override
	public ObjectOutput getSideShopGoodsByPsamAndVirtualCatId(
			SideShopGoodsListInput inputModel) throws LakalaException {
        ObjectOutput data = new ObjectOutput();
        List<ProductDetailedInformation> list = new ArrayList<ProductDetailedInformation>();
        List<ProductDetailedInformation> returnList = new ArrayList<ProductDetailedInformation>();
        List<Map<String, ProductDetailedInformation>> list1 = new ArrayList<Map<String, ProductDetailedInformation>>();
        List<ProductDetail> productDetails = new ArrayList<ProductDetail>();
        Map<String, ProductDetailedInformation> idMapping = new HashMap<String, ProductDetailedInformation>();

        if (inputModel == null || StringUtils.isEmpty(inputModel.getPsam()) || StringUtils.isEmpty(inputModel.getChannelid()) 
        		|| StringUtils.isEmpty(inputModel.getVirtualcatid()) || StringUtils.isEmpty(inputModel.getType())) {
            data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
            data.set_ReturnMsg("参数为空!");
            return data;
        }

        try {
        	
        	if ("new".equalsIgnoreCase(inputModel.getVirtualcatid())) {
        		List<ProductDetailedInformation> uplistn = new  ArrayList<ProductDetailedInformation>();
        		
            		Map<String, Object> map = new HashMap<String, Object>();
                    map.put("psamCode", inputModel.getPsam());
                   // map.put("virtualcatid", inputModel.getVirtualcatid());
                 
                    List<ProductDetailedInformation> uplist = tgoodskuinfoMapper_R.getSoldListByPsamAndVirtualcatid(map);
                    if(uplist != null && uplist.size() > 0){
                    	
                    	for(ProductDetailedInformation in: uplist){
                    		in.setFreshgoodsflag(in.getIsforeigngoods());
                    	
                    		if(null != in.gettGoodInfoPoolId() && in.gettGoodInfoPoolId() > 0){
                            	in.setOnekeyupload(1);
                            }else{
                            	in.setOnekeyupload(0);
                            }
                    		if(in.getGoodsStatus() == 208 || in.getGoodsStatus().equals(208)){
                    			in.setSaleflag(0);
                    		}else{
                    			in.setSaleflag(1);
                    		}
                    		in.setFreshgoodsflag(in.getIsfreshfood());
                    		uplistn.add(in);
                    	}
                    	
                    }
                    
                //zhiziwei 2015-07-08 过滤参加活动商品
                try {
                	getPromotionList(inputModel.getChannelid(), inputModel.getPsam(), uplistn);
				} catch (LakalaException e) {
					e.printStackTrace();
				}
                
        		OutListModel outListModel = new OutListModel();
                outListModel.setCount(30);
                outListModel.setList(uplistn);
                data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                data.set_ReturnData(outListModel);
                return data;
        		
        	}else{
        		/* 
        		String level = "1";
        		if("first".equalsIgnoreCase(inputModel.getLevel())){
        			level = "1";
        		}else if("second".equalsIgnoreCase(inputModel.getLevel())){
        			level = "2";
        		}else if("third".equalsIgnoreCase(inputModel.getLevel())){
        			level = "3";
        		}else if("fourth".equalsIgnoreCase(inputModel.getLevel())){
        			level = "4";
        		}else if("fifth".equalsIgnoreCase(inputModel.getLevel())){
        			level = "5";
        		}else if("sixth".equalsIgnoreCase(inputModel.getLevel())){
        			level = "6";
        		}
                 */
        		String realcatid = "";
        		
        		//获取以上分类关联的结算分类
//        		List<Realcate> realcatelist = this.realcateMapper_R.selectRcByVirtualCates(vcs);
        		List<Realcate> realcatelist = getRealCats(inputModel.getVirtualcatid());
//        		List<Realcate> realcatelist = categoryExt.getRealCats(inputModel.getVirtualcatid());
        		
        		if(realcatelist != null && realcatelist.size() > 0){
        			for(Realcate re : realcatelist){
        				realcatid+=String.valueOf(re.getTrealcateid())+","; 
        			}
        		}
        		
        		
        		
        		SideShopBListForKeyInPut inp = new SideShopBListForKeyInPut();
        		inp.setChannelid(Long.parseLong(inputModel.getChannelid()));
        		inp.setPsam(inputModel.getPsam());
        		inp.setForId(realcatid);
        		inp.setPlatorself(452);
        		inp.setPageIndex(1);
        		inp.setPageSize(10000);
        		ObjectOutput info1 = sideShopBMongoService.shopgoodslist(inp);
        		
                // ObjectOutput info1  = sideShopBMongoService.list_mongo(inputModel.getPsam(), inputModel.getChannelid(), inputModel.getVirtualcatid(), inputModel.getSearchparm(), 1, 10000, Integer.parseInt(level), 452);
                 OutListModel outList = new OutListModel();
                 if (ReturnMsg.CODE_SUCCESS.equals(info1.get_ReturnCode())) {	
                	 outList = (OutListModel) info1.get_ReturnData(); 
                	 returnList = outList.getList();
                 }
               
                 Map<String, Object> map = new HashMap<String, Object>();
                 map.put("psamCode", inputModel.getPsam());
                 map.put("virtualcatid", realcatelist);
                 List<ProductDetailedInformation> downlist = tgoodskuinfoMapper_R.getSoldoutListByPsamAndVirtualcatidByMap(map);
                 
                 ObjectOutput info = null;
                 
                 info = this.getPageProductDetile(returnList, inputModel.getPsam(), inputModel.getType());
                 
                 List<ProductDetailedInformation> listn = (List<ProductDetailedInformation>) info.get_ReturnData();
                 
                 List<ProductDetailedInformation> newlistn = new  ArrayList<ProductDetailedInformation>();
                 
                 if(listn != null && listn.size() > 0){
                 	newlistn.addAll(listn);
                 }
                 if(downlist != null && downlist.size() > 0){
                 	
                 	for(ProductDetailedInformation in: downlist){
                 		in.setFreshgoodsflag(in.getIsforeigngoods());
                 	
                 		if(null != in.gettGoodInfoPoolId() && in.gettGoodInfoPoolId() > 0){
                         	in.setOnekeyupload(1);
                         }else{
                         	in.setOnekeyupload(0);
                         }
                 		in.setFreshgoodsflag(in.getIsfreshfood());
                 		newlistn.add(in);
                 	}
                 	
                 }
                 
                 //zhiziwei 2015-07-08 过滤参加活动商品
                 try {
                	 getPromotionList(inputModel.getChannelid(), inputModel.getPsam(), newlistn);
				} catch (LakalaException e) {
					e.printStackTrace();
				}
                 
                 OutListModel outListModel = new OutListModel();
                 outListModel.setCount(newlistn.size());
                 outListModel.setList(newlistn);
                 data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
                 data.set_ReturnData(outListModel);
                 return data;
        	}
        } catch (LakalaException e) {
            throw new LakalaException(e);
        }
    }
	
	/**
	 * 过滤参加活动商品
	 * @author zhiziwei
	 */
	private void getPromotionList(String channelid, String psam, List<ProductDetailedInformation> newlistn)
			throws LakalaException{
		
		PromotionListInPut pin = new PromotionListInPut();
		try {
			
			Map<String, Object> sdbMap = sdbMediaStatisticsMapper_R
					.selectSdbMediaStatisticsByPsam(psam);
			if (sdbMap == null || sdbMap.isEmpty()) {
				throw new LakalaException("没有找到该终端所对应的网点信息！");
			}
			
			//mongo促销列表
			pin.setSource(Constant.PROMOTION_FLAG_RELEASE_CHANNEL_FLAG);
			pin.setChannel(Integer.parseInt(channelid));
			pin.setAllArea("1");
			pin.setPsam(psam);
			pin.setProvince((String) sdbMap.get("prov_no"));
			pin.setCity((String) sdbMap.get("city_no"));
			pin.setRegion((String) sdbMap.get("city_area_no"));
			pin.setSection((String) sdbMap.get("community_no"));
			pin.setNet((String) sdbMap.get("ec_net_no"));
			pin.setExpandChannel((String) sdbMap.get("agentId"));
			pin.setExpandType(String.valueOf(sdbMap.get("channelType")));
			pin.setType(Constant.PROMOTION_FLAG_STRAIGHT_DOWN_FLAG);
			
			String url = mongoInterfaceUrl.getPromotion();
			String inStr = JSON.toJSONString(pin);
			String json = HttpUtil.httpPostConnetion(url, inStr);
			ObjectOutput promotionoutput = JSON.parseObject(json, ObjectOutput.class);
			
			for (ProductDetailedInformation pdi : newlistn) {
				//实际销售价
				pdi.setFactsaleprice(pdi.getSaleprice_o2o());
				
				if (ReturnMsg.CODE_SUCCESS.equals(promotionoutput.get_ReturnCode())) {
					if (promotionoutput.get_ReturnData() != null 
							&& !"".equals(promotionoutput.get_ReturnData())) {
						List<PromotionListOutPut> listOutPuts = JSON.parseArray(promotionoutput
								.get_ReturnData().toString(), PromotionListOutPut.class);
						for (PromotionListOutPut outPut : listOutPuts) {
							if (outPut.getGoodPools() != null) {
								for (Goodsku goodsku : outPut.getGoodPools()) {
									if (outPut.getData().get("10") != null || outPut.getData().get("9") != null) {
                                        if (outPut.getData().get("10") != null) {
                                            if (outPut.getData().get("10").contains(String.valueOf(sdbMap.get("agentId")))) {
                                                if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(pdi.gettGoodInfoPoolId()))) {
													if (goodsku.getPrice() == null || goodsku .getPrice() > 0) {
														pdi.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
														pdi.setSaleprice_o2o(new BigDecimal(goodsku.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toEngineeringString());
														pdi.setType(outPut.getType());
													}
													pdi.setStartTime(outPut.getStartTime());
													pdi.setEndTime(outPut.getEndTime());
													break;
                                                }
                                            }
                                        }
                                        if (outPut.getData().get("9") != null) {
                                            if (outPut.getData().get("9").contains(String.valueOf(sdbMap.get("channelType")))) {
                                                if (String.valueOf(goodsku.getGoodsId()).equals(String.valueOf(pdi.gettGoodInfoPoolId()))) {
													if (goodsku.getPrice() == null || goodsku.getPrice() > 0) {
														pdi.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
														pdi.setSaleprice_o2o(new BigDecimal(goodsku.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toEngineeringString());
														pdi.setType(outPut.getType());
													}
													pdi.setStartTime(outPut.getStartTime());
													pdi.setEndTime(outPut.getEndTime());
													break;
                                                }
                                            }
                                        }
                                    } else {
                                    	if (null != pdi.gettGoodInfoPoolId() && pdi.gettGoodInfoPoolId().compareTo(goodsku.getGoodsId()) == 0) {
                                    		pdi.setPromotionPrice(new BigDecimal(goodsku.getPrice()));
											pdi.setSaleprice_o2o(new BigDecimal(goodsku.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).toEngineeringString());
                                    		pdi.setStartTime(outPut.getStartTime());
                                    		pdi.setEndTime(outPut.getEndTime());
                                    		pdi.setType(outPut.getType());
                                    	}
                                    }
								}
							}
						}
					}
				} else {
					throw new LakalaException("获取直降商品列表失败！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
	}
	/**
	 * 获取所选分类及子类关联的结算分类 zhiziwei
	 */
	private List<Realcate> getRealCats(String virtualcatid) throws LakalaException {
		//根据页面选中分类，获取下级子类，包含当前选种分类 zhiziwei
		List<VirtualCate> vcs = new ArrayList<VirtualCate>();
		List<VirtualCate> vp = new ArrayList<VirtualCate>();
		VirtualCate vc = new VirtualCate();
		vc.setTvirtualcateid(Integer.parseInt(virtualcatid));
		vp.add(vc);
		vcs.add(vc);
		getSubVitureCate(vcs, vp);
		
		//过滤掉没有商品的分类
		List<VirtualCate> _vcs = virtualCateMapper_R.selectHasGoodsSubVcsd(vcs);
		
		//获取以上分类关联的结算分类
		List<Realcate> realcatelist = this.realcateMapper_R.selectRcByVirtualCates(_vcs);
		return realcatelist;
	}

	/**
	 * 获取父虚分类下的子分类 zhiziwei
	 */
	private List<VirtualCate> getSubVitureCate(List<VirtualCate> list, List<VirtualCate> input) throws LakalaException{
		try {
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("parentid", input);
			List<VirtualCate> catList = virtualCateMapper_R.selectByParentId(parm);
			if (null != catList && catList.size() > 0) {
				//缓存查到的结果
				list.addAll(catList);
				//递归查询下面的子分类
				getSubVitureCate(list, catList);
			}
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return list;
	}
	
	@Override
	public ObjectOutput getselfgoodsdetail(SelfGoodsDetailInput inputModel)
			throws LakalaException {
		
		//取出请求参数
		String tGoodsInfoId = inputModel.getGoodsid();
		//定义返回值
		ObjectOutput<GoodsPoolInfoVO> res = new ObjectOutput<GoodsPoolInfoVO>();
		res._ReturnData = new GoodsPoolInfoVO();
				
		if(inputModel == null || !StringUtil.verdict(inputModel.getGoodsid())){
			res.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			res.set_ReturnMsg("很抱歉，未获取到商品信息，请重新进入！");
			return res;
		}
		
		//业务处理
		try {
				Tgoodinfo goodsInfo = this.tgoodsinfoMapper_R.selectByPrimaryKey(Integer.parseInt(inputModel.getGoodsid()));
				
				if(goodsInfo == null){
					res.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
					res.set_ReturnMsg("查询成功!");
					res.set_ReturnData(null);
				}
				
				
				List<Tgoodskuinfo> skulist = this.tgoodskuinfoMapper_R.selectSKUByGoodsId(Integer.parseInt(inputModel.getGoodsid()));
		
					
				res._ReturnData.settGoodInfoPoolId(goodsInfo.getTgoodinfopoolid());
				res._ReturnData.settGoodInfoId(goodsInfo.getTgoodinfoid());
				res._ReturnData.settRealCateId(goodsInfo.getTrealcateid());
				res._ReturnData.settRealCateDisc(goodsInfo.getTrealcatedisc());
				res._ReturnData.settRealCateTreeDisc(goodsInfo.getTrealcatetreedisc());
				res._ReturnData.setGoodName(goodsInfo.getGoodname());
				res._ReturnData.setGoodBigPic(goodsInfo.getGoodbigpic());
				res._ReturnData.setDistributionFlag(goodsInfo.getDistributionflag());
				res._ReturnData.setGoodIntroduce(goodsInfo.getGoodintroduce());
				res._ReturnData.setBalanceWay(goodsInfo.getBalanceway());
				res._ReturnData.setIsExpressToHome(goodsInfo.getIsexpresstohome());
				res._ReturnData.settBrandId(goodsInfo.getTbrandid());
				res._ReturnData.setBrandName(goodsInfo.getBrandname());
				res._ReturnData.setGoodBarCode(goodsInfo.getGoodbarcode());
				res._ReturnData.setIsfreshfood(goodsInfo.getIsfreshfood()); //zhiziwei 增加返回值字段：生鲜标记
				
				//如果是模板商品，获取其统一售价
				Integer tGoodInfoId = goodsInfo.getTgoodinfopoolid();
				if (null != tGoodInfoId && tGoodInfoId.intValue() > 0) {
					TgoodskuinfopoolWithBLOBs sku = tgoodskuinfopoolMapper_R.selectGoodSkuInfoByGoodId(
							tGoodInfoId).get(0);
					res._ReturnData.setSalePrice(sku.getSaleprice());
				}
				
				List<GoodsPoolSkuInfoVO> skus = new ArrayList<GoodsPoolSkuInfoVO>();
				for (Tgoodskuinfo skuInfo : skulist) {
					GoodsPoolSkuInfoVO gsv = new GoodsPoolSkuInfoVO();
					gsv.settGoodSkuInfoPoolId(skuInfo.getTgoodskuinfoid());
					gsv.setSkuIdStr(skuInfo.getSkuidstr());
					gsv.setSkuFrontDisNameStr(skuInfo.getSkufrontdisnamestr());
					gsv.setSaleprice(skuInfo.getSaleprice());
					gsv.setMarketprice(skuInfo.getMarketprice());
					gsv.setSkustock(skuInfo.getSkustock());
					skus.add(gsv);
				}
				res._ReturnData.setSkus(skus);
				
				
				/** wujx 2015-7-9 图片获取改用transfer*/
	           /* String pk = goodsInfo.getTgoodinfopoolid()!=null? String.valueOf(goodsInfo.getTgoodinfopoolid()) : inputModel.getGoodsid();
	            List<ImageInfoVO> imgsVo = new ArrayList<ImageInfoVO>();
	            if(null!=goodsInfo.getGoodbigpic()){
	            	String[] pics = goodsInfo.getGoodbigpic().split(";");
	            	ImageInfoVO img;
	                for(String pic : pics){
	                	img = new ImageInfoVO();
	                	img.setUrl(transfer.getServerUrl(pic, pk, TargetTypeEnum.IMAGE_TARGETTYPE_GOODS, true));
	                	imgsVo.add(img);
	                }
	            }
	            res._ReturnData.setImages(imgsVo);*/
				
				//图片数据
				Map<String, Object> imgParm = new HashMap<String, Object>();
				imgParm.put("goodsId", Long.valueOf(tGoodsInfoId));
				imgParm.put("targetType", BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS);
				List<Timages> imgs = timagesMapper_R.queryImgByGoodsId(imgParm);
				
				List<ImageInfoVO> imgsVo = new ArrayList<ImageInfoVO>();
				
				for (Timages img : imgs) {
					ImageInfoVO imgVo = new ImageInfoVO();
					
					imgVo.setTimageId(img.getTimageid());
					imgVo.setUrl(img.getUrl());
					imgVo.setSort(img.getSort());
					
					imgsVo.add(imgVo);
				}
				res._ReturnData.setImages(imgsVo);
				
				res.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				res.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
					
				} catch (Exception e) {
					throw new LakalaException(e);
				}
		
		return res;
	
	}
}
