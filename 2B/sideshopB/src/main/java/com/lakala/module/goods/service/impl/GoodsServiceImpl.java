package com.lakala.module.goods.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lakala.base.model.Approval;
import com.lakala.base.model.Realcate;
import com.lakala.base.model.SDBMediaStatistics;
import com.lakala.base.model.Tgoodinfo;
import com.lakala.base.model.TgoodsinfopoolWithBLOBs;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.base.model.TgoodskuinfopoolWithBLOBs;
import com.lakala.base.model.TgoodslogWithBLOBs;
import com.lakala.base.model.Timages;
import com.lakala.base.model.Tpropertykey;
import com.lakala.base.model.Tpropertyvalue;
import com.lakala.base.model.Tregion;
import com.lakala.base.model.VirtualCate;
import com.lakala.exception.LakalaException;
import com.lakala.fileupload.bean.FileItem;
import com.lakala.fileupload.enums.TargetTypeEnum;
import com.lakala.fileupload.handler.MultipartHttpFileTransfer;
import com.lakala.module.approval.ApprovalConstant;
import com.lakala.module.approval.service.ApprovalService;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.service.GoodsPublishService;
import com.lakala.module.goods.service.GoodsService;
import com.lakala.module.goods.vo.CategoryInput;
import com.lakala.module.goods.vo.CategorySub;
import com.lakala.module.goods.vo.GoodsCol;
import com.lakala.module.goods.vo.GoodsInput;
import com.lakala.module.goods.vo.GoodsListInput;
import com.lakala.module.goods.vo.GoodsListInputForm;
import com.lakala.module.goods.vo.GoodsPublishInput;
import com.lakala.module.goods.vo.GoodsSKU;
import com.lakala.module.goods.vo.MarketableInput;
import com.lakala.module.goods.vo.PropertyKeyVO;
import com.lakala.module.goods.vo.PropertyValueVO;
import com.lakala.module.goods.vo.RealCateVO;
import com.lakala.module.goods.vo.RecommendGoodsInput;
import com.lakala.module.goods.vo.RecommendGoodsOutput;
import com.lakala.module.goods.vo.TgoodsSkuInfo;
import com.lakala.module.goods.vo.TgoodsSkuInfoInput;
import com.lakala.module.goods.vo.VirtualCateInput;
import com.lakala.module.goods.vo.VirtualCateVO;
import com.lakala.module.goods.vo.in.SideShopBListInPut;
import com.lakala.module.goods.vo.out.ListGoodsSKU;
import com.lakala.module.goods.vo.out.SideShopBDetailOutPut;
import com.lakala.module.order.service.Mongo4GoodsService;
import com.lakala.util.BussConst;
import com.lakala.util.NumberUtils;
import com.lakala.util.ReturnMsg;
import com.lakala.util.StringUtil;

@Service
public class GoodsServiceImpl implements GoodsService {
    private Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

	@Autowired
    private com.lakala.mapper.w.goods.TgoodsinfoMapper tgoodinfoMapper_W;
	@Autowired
    private com.lakala.mapper.w.goods.TgoodskuinfoMapper tgoodskuinfoMapper_W;
    @Autowired
    private com.lakala.mapper.r.goods.TgoodsinfoMapper tgoodinfoMapper_R;
    @Autowired
    private com.lakala.mapper.r.goods.TgoodskuinfoMapper tgoodskuinfoMapper_R;
    @Autowired
    private com.lakala.mapper.r.goods.TgoodsinfopoolMapper tgoodsinfopoolMapper_R;
    @Autowired
    private com.lakala.mapper.r.goods.TgoodskuinfopoolMapper tgoodskuinfopoolMapper_R;
    @Autowired
    private com.lakala.mapper.w.goods.TimagesMapper timagesMapper_W;
    @Autowired
    private com.lakala.mapper.r.goods.TimagesMapper timagesMapper_R;
    @Autowired
    private com.lakala.mapper.r.realcat.RealcateMapper realcateMapper_R;
    @Autowired
    private com.lakala.mapper.r.goods.TpropertykeyMapper tpropertykeyMapper_R;
    @Autowired
    private com.lakala.mapper.r.goods.TpropertyvalueMapper tpropertyvalueMapper_R;
    @Autowired
    private com.lakala.mapper.w.goods.TgoodslogMapper tgoodslogMapper_W;
    @Autowired
    private com.lakala.mapper.r.goods.TgoodslogMapper tgoodslogMapper_R;
    @Autowired
    private com.lakala.mapper.r.virtualcat.VirtualCateMapper virtualCateMapper_R;
    @Autowired
    private com.lakala.mapper.r.sdbmediastatistics.SdbMediaStatisticsMapper sdbMediaStatisticsMapper_R;
    @Autowired
    private com.lakala.mapper.r.approval.ApprovalMapper approvalMapper_R;
    @Autowired
    private com.lakala.mapper.w.approval.ApprovalMapper approvalMapper_W;
    @Autowired
    private com.lakala.mapper.r.user.TmemberMapper tmemberMapperr_R;
    @Autowired
	private com.lakala.mapper.r.dictionary.TregionMapper tregionMapper_R;
	@Autowired
    private ApprovalService approvalService ;
	@Autowired
	private GoodsPublishService goodsPublishService;
	@Autowired
	private Mongo4GoodsService mongo4GoodsService;
	
	@Resource
	private com.lakala.mapper.w.goods.GoodsPublishForKDBMapper kdbGoodsMapper_W;
	
	@Resource
	private com.lakala.mapper.w.goods.GoodsPublishForKDBOfSKUMapper kdbSKUMapper_W;
	
	
	@Autowired
	private MultipartHttpFileTransfer transfer;
	
	
	public ObjectOutput getCategory(CategoryInput cate) throws LakalaException {  
		ObjectOutput data = new ObjectOutput();		
		String psam = cate.getPsam();
		if(psam == null){
			data.set_ReturnCode(ReturnMsg.CODE_ERR_000002);
			data.set_ReturnMsg("对象为空!");
			return data;
		}
		//继续判断里面的必须参数
		CategorySub categorySub1 = new CategorySub();
		categorySub1.setCategoryId(1);
		categorySub1.setCategoryName("品类1");
		
		CategorySub categorySub2 = new CategorySub();
		categorySub2.setCategoryId(2);
		categorySub2.setCategoryName("品类2");
		
		CategorySub categorySub11 = new CategorySub();
		categorySub1.setCategoryId(11);
		categorySub1.setCategoryName("子品类1");
		
		CategorySub categorySub12 = new CategorySub();
		categorySub1.setCategoryId(12);
		categorySub1.setCategoryName("子品类2");
		List<CategorySub> sublists = new ArrayList<CategorySub>();
		sublists.add(categorySub11);
		sublists.add(categorySub12);
		
		categorySub1.setLists(sublists);
		categorySub2.setLists(sublists);
		
        List<CategorySub> lists = new ArrayList<CategorySub>();
        lists.add(categorySub1);
        lists.add(categorySub2);
		
		data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
		data.set_ReturnData(lists);
		return data;
	}
	
	
	public ObjectOutput maketAbleGoods(MarketableInput input) throws LakalaException{
		ObjectOutput data = new ObjectOutput();	
		data.set_ReturnData(true);
		return data;
	}
	
	public ObjectOutput getGoodsList(GoodsListInput input) throws LakalaException{
		ObjectOutput data = new ObjectOutput();	
		List<TgoodsSkuInfo> lists = new ArrayList<TgoodsSkuInfo>();
		TgoodsSkuInfo tgoodsSkuInfo1 = new TgoodsSkuInfo();
		tgoodsSkuInfo1.setGoodname("测试商品");
		tgoodsSkuInfo1.setSaleprice(new BigDecimal(123));
		tgoodsSkuInfo1.setSkustock(new BigDecimal(88));
		lists.add(tgoodsSkuInfo1);
		data.set_ReturnData(lists);
		return data;
	}
	
	/**
	 * 新增、修改商品
	 * @throws LakalaException
	 */
	public ObjectOutput<Integer> upGoodsInfo(TgoodsSkuInfoInput input) throws LakalaException{
		 //新增商品基本信息的主键
        Integer tgoodinfoid = input.getTgoodinfoid();
        if (tgoodinfoid != null) {
     		//判断商品是否自营商品（自营并且是下架状态才可以修改）
        	Tgoodinfo goodInfoBean = tgoodinfoMapper_R.selectByPrimaryKey(tgoodinfoid);
//    		if (goodInfoBean.getPlatorself()==452 && goodInfoBean.getGoodsstatus()==209) { // zhiziwei 
    		if (goodInfoBean.getPlatorself()==452) { // zhiziwei 上架状态的商品可修改
    			//写库:商品基本信息表(修改记录)
    			tgoodinfoid = updateGood(input);
    		}
        } else {
			//写库:商品基本信息表(增加记录)
        	tgoodinfoid = addGood(input);
        }
        
        ObjectOutput<Integer> data = new ObjectOutput<Integer>();	
        data.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
        data.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		data.set_ReturnData(tgoodinfoid);
		return data;
	}
	
	/**
	 * 商品新增处理
	 * @throws LakalaException 
	 */
	public int addGood(TgoodsSkuInfoInput input) throws LakalaException{
		Date now = new Date(System.currentTimeMillis());//当前时间
		Integer tgoodinfoid = null;
		try {
			//根据psam获取供应商信息
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByAPPPsam(input.getDeviceno());
			if (null == statistics) {
				throw new LakalaException("NO_SDBMEDIASTATISTICS");
			}
			input.setSupplierid(statistics.getId().intValue());
			input.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
			input.setOrgid(statistics.getOrgId());
			
			/* =================页面直接选择结算分类，屏蔽之前处理===================
			//添加模板商品时，后台读取虚分类信息
			if (!StringUtil.verdict(input.getVirtualcateids())) {
				String virCateStr = getVirCatByRealCat(input.getTrealcateid());
				if (!StringUtil.verdict(virCateStr)) {
					throw new LakalaException("NO_VIRTUALCATE");
				}
				input.setVirtualcateids(virCateStr);
			} else {//自定义上传自营商品
				String virCateStr = input.getVirtualcateids();
				input.setVirtualcateids(BussConst.APP_2C_FREQUENCYID + "-" + virCateStr.substring(0, virCateStr.length() - 1));
			}
			*/
			
			// 一键上传商品，取生鲜标记
			if (StringUtil.verdict(input.gettGoodInfoPoolId())) {
				TgoodsinfopoolWithBLOBs pGoods = tgoodsinfopoolMapper_R.selectByPrimaryKey(
						Integer.valueOf(input.gettGoodInfoPoolId()));
				input.setIsfreshfood(pGoods.getIsfreshfood());
			}
			
			// 根据实分类获取虚分类
			String virCateStr = getVirCatByRealCat(input.getTrealcateid());
			if (!StringUtil.verdict(virCateStr)) {
				throw new LakalaException("NO_VIRTUALCATE");
			}
			input.setVirtualcateids(virCateStr);
			
			//获取结算分类四级描述
			StringBuffer sb = new StringBuffer();
			getRcTreeDisc(sb, input.getTrealcateid());
			input.setTrealcatetreedisc(sb.toString().substring(0, sb.length() - 1));
			
			//封装商品基本信息数据
			Tgoodinfo goodInfoBean = new Tgoodinfo();
			setTgoodsinfoWithBLOBs(goodInfoBean, input);
			goodInfoBean.setCreateddate(now);
			goodInfoBean.setLastupdatedate(now);
			
			//写库:商品基本信息表
			tgoodinfoMapper_W.insertSelective(goodInfoBean);
			
			//新增商品基本信息的主键
			tgoodinfoid = goodInfoBean.getTgoodinfoid();
			
			/**
			 * 处理商品图片：
			 * targetType = 3，表示引用的商品池的图片，此时，将图片信息复制一份，更新targetId
			 * targetType = null，表示供应商新增图片，此时，只更新targetId
			 */
			String tempImgsStr = "";//商品表图片字段数据格式：文件名,多张图片之间用“;”隔开
			String[] imgs = StringUtil.verdict(input.getImgInfoList()) ?  String.valueOf(input.getImgInfoList())
					.split(";") : null;//页面图片数据
			for (int i = 0; null != imgs && imgs.length > 0 && i < imgs.length; i++) {
				
				//关联上传商品图片(多张图片之间用“;”隔开，页面传入图片数据包含图片id，及排序：图片id_排序)
				String[] imgInfo = imgs[i].split("_");
				Timages imgObj = timagesMapper_R.selectByPrimaryKey(Integer.valueOf(imgInfo[0]));
				//非一键上传的商品需要如下处理
				if (goodInfoBean.getTgoodinfopoolid() == null) {
					
					/** wujx 2015-6-15图片操作升级改造 */
					FileItem item = transfer.transferToFtp(transfer.getTempPath(imgObj.getImagename(),String.valueOf(imgObj.getTargetid()), TargetTypeEnum.valueOf(imgObj.getTargettype())), 
							tgoodinfoid.toString(), TargetTypeEnum.IMAGE_TARGETTYPE_GOODS, true);
					
					//移动文件：从临时路径移动到正式路径
					//String newUrl = ImageUtil.moveMainPic(imgObj.getUrl(), tgoodinfoid.toString(), 
					//		BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS, input.getReq(), true, true);
					
					imgObj.setUrl(item.getUrl());
				}
				imgObj.setSort(Integer.valueOf(imgInfo[1]));
				imgObj.setTargetid(Long.valueOf(tgoodinfoid.toString()));
				imgObj.setTargettype(BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS);
				//写库
				timagesMapper_W.updateByPrimaryKeySelective(imgObj);
				
				//整理商品表图片字段数据
				tempImgsStr += getImgNamesStr(i, imgObj.getUrl(), imgs.length);
			}
			
			//更新商品基本信息图片信息
	        goodInfoBean.setGoodbigpic(tempImgsStr);
	        tgoodinfoMapper_W.updateByPrimaryKeySelective(goodInfoBean);
	        
	        //调用mongo接口：初始化入参
	        GoodsCol col = new GoodsCol();
	        col.setSkus(new ArrayList<GoodsSKU>());
			mongo4GoodsService.setGoodsCol(col, goodInfoBean, null);
			
	        //删除图片临时存放路径
			/** wujx 2015-6-15图片操作升级改造 */
	        //ImageUtil.deltemp(input.getReq(), input.getMobile());
	        
			/**
			 * 封装商品SKU信息数据：页面传入的sku信息包含sku属性、销售价、市场价、库存，数据格式：
			 * 属性ID键值对(属性ID:属性值ID)_属性描述(属性值描述1x属性值描述2)_销售价_市场价_库存
			 * #属性ID键值对(属性ID:属性值ID)_属性描述(属性值描述1x属性值描述2)_销售价_市场价_库存
			 */
			String[] skus = input.getSkuidstr().split("#");
			for (int i = 0; i < skus.length; i++) {
				Tgoodskuinfo goodSkuInfoBean = new Tgoodskuinfo();
				setTgoodskuinfoWithBLOBs(goodSkuInfoBean, input);
				goodSkuInfoBean.setTgoodinfoid(tgoodinfoid);
				//分解sku属性、销售价、市场价、库存，并记录在商品信息中
				String[] temp = skus[i].split("_");
				goodSkuInfoBean.setSkuidstr(temp[0]);
				
				// zhiziwei 2015.07.09 自营商品结算价字段记录销售价，供下单时使用
				goodSkuInfoBean
				.setBalanceprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
						temp[2]) : new BigDecimal("0"));//结算价
				
				goodSkuInfoBean
				.setSaleprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
						temp[2]) : new BigDecimal("0"));//销售价
				
				goodSkuInfoBean
				.setMarketprice(StringUtil.isNumber(temp[3]) ? new BigDecimal(
						temp[3]) : new BigDecimal("0"));//市场价
				
				goodSkuInfoBean
				.setSkustock(StringUtil.isNumber(temp[4]) ? new BigDecimal(
						temp[4]) : new BigDecimal("0"));//库存
				
				goodSkuInfoBean.setSkufrontdisnamestr("NONE".equals(temp[0]) ? ""
						: getSkuDisc(temp[0]));
				
				goodSkuInfoBean.setGoodbigpic(tempImgsStr);//图片
				
				goodSkuInfoBean.setCreateddate(now);
				goodSkuInfoBean.setLastupdatedate(now);
				
				//写库：商品SKU信息表
				tgoodskuinfoMapper_W.insertSelective(goodSkuInfoBean);
				
				//获取本次操作日志批次号
				Integer batNo = getBatNo(tgoodinfoid);
				//记录日志
				writeGoodsLog(goodSkuInfoBean, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_NEW, 
							BussConst.GOODS_LOG_OPREATESRC_PROVIDERADD, batNo);
				
				//调用mongo接口：初始化入参
				mongo4GoodsService.setGoodsCol(col, null, goodSkuInfoBean);
			}
			
			//调用mongo接口：调用接口新增
			mongo4GoodsService.writeGoodsToMongo(col, goodInfoBean.getVirtualcateids(), null, input.getLoginusername(), input.getReq().getRemoteHost(), "add");
			
			//模板商品，新增成功直接上架，自定义商品后台审核通过后上架
			if (null == goodInfoBean.getTgoodinfopoolid()) {//自定义商品
				try {
					Approval approval = new Approval();
					approval.setUserId(BussConst.APP2B_GOODS_PROPOSER);
					approval.setObjType(ApprovalConstant.OBJTYPE_ADD_GOODS);//审批模板数据类型
					approval.setDocentry(String.valueOf(tgoodinfoid));
					approval.setOwnerRemark("商品新增成功，请审批！");
					approval.setStatus(ApprovalConstant.WAIT);//审批状态：163   待审批
					approval.setUrl("goods/showgoodsdetail/"+goodInfoBean.getTgoodinfoid());//商品详情页
					//写库：审批模板配置表
					approvalService.insertSelective(approval);
				} catch (Exception e) {
					throw new LakalaException("NO_APPROVAL");
				}
			}
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return tgoodinfoid;
	}
	
	/**
	 * 商品新增处理 （版本备份：2015年6月1日）
	 * @throws LakalaException 
	 */
	public int addGoodV1(TgoodsSkuInfoInput input) throws LakalaException{
		Date now = new Date(System.currentTimeMillis());//当前时间
		Integer tgoodinfoid = null;
		try {
			//根据psam获取供应商信息
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByAPPPsam(input.getDeviceno());
			if (null == statistics) {
				throw new LakalaException("NO_SDBMEDIASTATISTICS");
			}
			input.setSupplierid(statistics.getId().intValue());
			input.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
			input.setOrgid(statistics.getOrgId());
			
			/* =================页面直接选择结算分类，屏蔽之前处理===================
			//添加模板商品时，后台读取虚分类信息
			if (!StringUtil.verdict(input.getVirtualcateids())) {
				String virCateStr = getVirCatByRealCat(input.getTrealcateid());
				if (!StringUtil.verdict(virCateStr)) {
					throw new LakalaException("NO_VIRTUALCATE");
				}
				input.setVirtualcateids(virCateStr);
			} else {//自定义上传自营商品
				String virCateStr = input.getVirtualcateids();
				input.setVirtualcateids(BussConst.APP_2C_FREQUENCYID + "-" + virCateStr.substring(0, virCateStr.length() - 1));
			}
			 */
			
			// 根据实分类获取虚分类
			String virCateStr = getVirCatByRealCat(input.getTrealcateid());
			if (!StringUtil.verdict(virCateStr)) {
				throw new LakalaException("NO_VIRTUALCATE");
			}
			input.setVirtualcateids(virCateStr);
			
			//获取结算分类四级描述
			StringBuffer sb = new StringBuffer();
			getRcTreeDisc(sb, input.getTrealcateid());
			input.setTrealcatetreedisc(sb.toString().substring(0, sb.length() - 1));
			
			//封装商品基本信息数据
			Tgoodinfo goodInfoBean = new Tgoodinfo();
			setTgoodsinfoWithBLOBs(goodInfoBean, input);
			goodInfoBean.setCreateddate(now);
			goodInfoBean.setLastupdatedate(now);
			
			//写库:商品基本信息表
			tgoodinfoMapper_W.insertSelective(goodInfoBean);
			
			//新增商品基本信息的主键
			tgoodinfoid = goodInfoBean.getTgoodinfoid();
			
			/**
			 * 处理商品图片：
			 * targetType = 3，表示引用的商品池的图片，此时，将图片信息复制一份，更新targetId
			 * targetType = null，表示供应商新增图片，此时，只更新targetId
			 */
			String tempImgsStr = "";//商品表图片字段数据格式：文件名,多张图片之间用“;”隔开
			String[] imgs = StringUtil.verdict(input.getImgInfoList()) ?  String.valueOf(input.getImgInfoList())
					.split(";") : null;//页面图片数据
					for (int i = 0; null != imgs && imgs.length > 0 && i < imgs.length; i++) {
						
						//关联上传商品图片(多张图片之间用“;”隔开，页面传入图片数据包含图片id，及排序：图片id_排序)
						String[] imgInfo = imgs[i].split("_");
						Timages imgObj = timagesMapper_R.selectByPrimaryKey(Integer.valueOf(imgInfo[0]));
						
						
						/** wujx 2015-6-15图片操作升级改造 */
						FileItem item = transfer.transferToFtp(transfer.getTempPath(imgObj.getImagename(),String.valueOf(imgObj.getTargetid()), TargetTypeEnum.valueOf(imgObj.getTargettype())), 
								tgoodinfoid.toString(), TargetTypeEnum.IMAGE_TARGETTYPE_GOODS, true);
						
						//移动文件：从临时路径移动到正式路径
//						String newUrl = ImageUtil.moveMainPic(imgObj.getUrl(), tgoodinfoid.toString(), 
//								BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS, input.getReq(), true, true);
						
						imgObj.setUrl(item.getUrl());
						imgObj.setSort(Integer.valueOf(imgInfo[1]));
						imgObj.setTargetid(Long.valueOf(tgoodinfoid.toString()));
						//写库
						timagesMapper_W.updateByPrimaryKeySelective(imgObj);
						
						//整理商品表图片字段数据
						tempImgsStr += getImgNamesStr(i, imgObj.getUrl(), imgs.length);
					}
					
					//更新商品基本信息图片信息
					goodInfoBean.setGoodbigpic(tempImgsStr);
					tgoodinfoMapper_W.updateByPrimaryKeySelective(goodInfoBean);
					
					/** wujx 2015-6-16图片操作升级改造 */
					//删除图片临时存放路径
					//ImageUtil.deltemp(input.getReq(), input.getMobile());
					
					/**
					 * 封装商品SKU信息数据：页面传入的sku信息包含sku属性、销售价、市场价、库存，数据格式：
					 * 属性ID键值对(属性ID:属性值ID)_属性描述(属性值描述1x属性值描述2)_销售价_市场价_库存
					 * #属性ID键值对(属性ID:属性值ID)_属性描述(属性值描述1x属性值描述2)_销售价_市场价_库存
					 */
					String[] skus = input.getSkuidstr().split("#");
					for (int i = 0; i < skus.length; i++) {
						Tgoodskuinfo goodSkuInfoBean = new Tgoodskuinfo();
						setTgoodskuinfoWithBLOBs(goodSkuInfoBean, input);
						goodSkuInfoBean.setTgoodinfoid(tgoodinfoid);
						//分解sku属性、销售价、市场价、库存，并记录在商品信息中
						String[] temp = skus[i].split("_");
						goodSkuInfoBean.setSkuidstr(temp[0]);
						
						goodSkuInfoBean
						.setSaleprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
								temp[2]) : new BigDecimal("0"));//销售价
						
						goodSkuInfoBean
						.setMarketprice(StringUtil.isNumber(temp[3]) ? new BigDecimal(
								temp[3]) : new BigDecimal("0"));//市场价
						
						goodSkuInfoBean
						.setSkustock(StringUtil.isNumber(temp[4]) ? new BigDecimal(
								temp[4]) : new BigDecimal("0"));//库存
						
						goodSkuInfoBean.setSkufrontdisnamestr("NONE".equals(temp[0]) ? ""
								: getSkuDisc(temp[0]));
						
						goodSkuInfoBean.setGoodbigpic(tempImgsStr);//图片
						
						goodSkuInfoBean.setCreateddate(now);
						goodSkuInfoBean.setLastupdatedate(now);
						
						//写库：商品SKU信息表
						tgoodskuinfoMapper_W.insertSelective(goodSkuInfoBean);
						
						//获取本次操作日志批次号
						Integer batNo = getBatNo(tgoodinfoid);
						//记录日志
						writeGoodsLog(goodSkuInfoBean, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_NEW, 
								BussConst.GOODS_LOG_OPREATESRC_PROVIDERADD, batNo);
					}
					
					//模板商品，新增成功直接上架，自定义商品后台审核通过后上架
					if (null == goodInfoBean.getTgoodinfopoolid()) {//自定义商品
						try {
							Approval approval = new Approval();
							approval.setUserId(BussConst.APP2B_GOODS_PROPOSER);
							approval.setObjType(ApprovalConstant.OBJTYPE_ADD_GOODS);//审批模板数据类型
							approval.setDocentry(String.valueOf(tgoodinfoid));
							approval.setOwnerRemark("商品新增成功，请审批！");
							approval.setStatus(ApprovalConstant.WAIT);//审批状态：163   待审批
							approval.setUrl("goods/showgoodsdetail/"+goodInfoBean.getTgoodinfoid());//商品详情页
							//写库：审批模板配置表
							approvalService.insertSelective(approval);
						} catch (Exception e) {
							throw new LakalaException("NO_APPROVAL");
						}
					}
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return tgoodinfoid;
	}
	
	/**
	 * 
	 * @Title: getRcTreeDisc
	 * @Description: 获取结算分类四级描述
	 * @param rid 结算分类id
	 * @throws
	 */
	private void getRcTreeDisc(StringBuffer sb, Integer rid) throws LakalaException{
		try {
			Realcate rc = realcateMapper_R.selectByPrimaryKey(rid);
			if (rc.getFatherrealcateid().intValue() != 0) {
				sb.insert(0, rc.getRealcatedisc() + "》");
				getRcTreeDisc(sb, rc.getFatherrealcateid());
			} else {
				sb.insert(0, rc.getRealcatedisc() + "》");
			}
		} catch (Exception e) {
			throw new LakalaException(e.fillInStackTrace());
		}
	}
	
	
	/**
	 * 根据实分类，获取关联的虚分类
	 */
	private String getVirCatByRealCat(Integer tRealCateId) {
		List<String> temp = new ArrayList<String>();
		//查询所有关联的虚分类
		List<VirtualCate> vcs = virtualCateMapper_R.findVirCatByRealCat(tRealCateId);
		
		//遍历所有虚分类
		for (VirtualCate vc : vcs) {
			String virCateStr = vc.getTvirtualcateid().toString();
			temp.add(goForward(virCateStr, vc.getFathervirtualcateid()));
		}

		//整理数据：转成字符串，并拼接上频道信息
		String _virCateStr = "";
		for (int i = 0; i < temp.size(); i++) {
			_virCateStr += (i == temp.size() - 1 ?BussConst.APP_2C_FREQUENCYID + "-" + temp.get(i) 
					: BussConst.APP_2C_FREQUENCYID + "-" + temp.get(i) + ",");
		}
		return _virCateStr;
	}

	/**
	 * 向前查找父类 zhiziwei
	 */
	private String goForward(String str, Integer fatherCateId){
		VirtualCate vc = virtualCateMapper_R.selectByPrimaryKey(fatherCateId);
		if (null != vc) {
			//拼分类ID
			str = vc.getTvirtualcateid().toString() + "_" + str;
			//递归调用，查询前边的父类
			if (vc.getFathervirtualcateid().intValue() != 0) {
				str = goForward(str, vc.getFathervirtualcateid());
			}
		}
		return str;
	}
	
	/**
	 * 修改商品
	 * @throws LakalaException 
	 */
	public int updateGood(TgoodsSkuInfoInput input) throws LakalaException{
		Date now = new Date(System.currentTimeMillis());//当前时间
		Integer tgoodInfoId = Integer.valueOf(input.getTgoodinfoid());//商品基本信息id
		
		try {
			//根据psam获取供应商信息
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByAPPPsam(input.getDeviceno());
			if (null == statistics) {
				throw new LakalaException("NO_SDBMEDIASTATISTICS");
			}
			input.setSupplierid(statistics.getId().intValue());
			input.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
			input.setOrgid(statistics.getOrgId());
			
			/* =================页面直接选择结算分类，屏蔽之前处理===================
			//添加模板商品时，后台读取虚分类信息
			if (!StringUtil.verdict(input.getVirtualcateids())) {
				String virCateStr = getVirCatByRealCat(input.getTrealcateid());
				if (!StringUtil.verdict(virCateStr)) {
					throw new LakalaException("NO_VIRTUALCATE");
				}
				input.setVirtualcateids(virCateStr);
			} else {//自定义上传自营商品
				String virCateStr = input.getVirtualcateids();
				input.setVirtualcateids(BussConst.APP_2C_FREQUENCYID + "-" + virCateStr.substring(0, virCateStr.length() - 1));
			}
			*/
			
			// 一键上传商品，取生鲜标记
			if (StringUtil.verdict(input.gettGoodInfoPoolId())) {
				TgoodsinfopoolWithBLOBs pGoods = tgoodsinfopoolMapper_R.selectByPrimaryKey(
						Integer.valueOf(input.gettGoodInfoPoolId()));
				input.setIsfreshfood(pGoods.getIsfreshfood());
			}

			// 获取虚分类
			String virCateStr = getVirCatByRealCat(input.getTrealcateid());
			if (!StringUtil.verdict(virCateStr)) {
				throw new LakalaException("NO_VIRTUALCATE");
			}
			input.setVirtualcateids(virCateStr);
			
			//获取结算分类四级描述
			StringBuffer sb = new StringBuffer();
			getRcTreeDisc(sb, input.getTrealcateid());
			input.setTrealcatetreedisc(sb.toString().substring(0, sb.length() - 1));
			
			//封装商品基本信息数据
			Tgoodinfo goodInfoBean = tgoodinfoMapper_R.selectByPrimaryKey(tgoodInfoId);
			
			//取出商品当前上/下架状态，为后边处理做准备
			Integer uds = goodInfoBean.getGoodsstatus();
			
			//处理商品基本信息
			setTgoodsinfoWithBLOBs(goodInfoBean, input);
			
			//处理商品图片信息
			//修改前该商品关联的图片
			Map<String, Object> imgParm = new HashMap<String, Object>();
			imgParm.put("goodsId", Long.valueOf(tgoodInfoId.toString()));
			imgParm.put("targetType", BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS);
			List<Timages> imgsBeforeUpdate = timagesMapper_R.queryImgByGoodsId(imgParm);
			
			//修改后该商品关联的图片(多张图片之间用“;”隔开，页面传入图片数据包含图片id，及排序：图片id_排序)
			String[] imgs = StringUtil.verdict(input.getImgInfoList()) ?  String.valueOf(input.getImgInfoList())
					.split(";") : null;//页面图片数据
			//商品表图片字段数据格式：文件名,多张图片之间用“;”隔开
			String tempImgsStr = "";
			//缓存该商品用到的图片
			List<Integer> usedImgs = new ArrayList<Integer>();
			for (int i = 0; null != imgs && imgs.length > 0 && i < imgs.length; i++) {
				boolean flag = false;//更新标记,默认未更新
				//处理原来就存在的图片
				for (Timages timages : imgsBeforeUpdate) {
					String[] imgInfo = imgs[i].split("_");
					if (timages.getTimageid().equals(Integer.valueOf(imgInfo[0]))) {// 更新图片数据
						timages.setSort(Integer.valueOf(imgInfo[1]));
						// 写库
						timagesMapper_W.updateByPrimaryKeySelective(timages);
						//更新更新标记
						flag = true;
						//整理图片文件名字符串
						tempImgsStr += getImgNamesStr(i, timages.getUrl(), imgs.length);
						usedImgs.add(timages.getTimageid());
					}
				}
				//处理新增的图片
				if(!flag){//未更新，说明是新增的图片
					String[] imgInfo = imgs[i].split("_");
					Timages imgObj = timagesMapper_R.selectByPrimaryKey(Integer.valueOf(imgInfo[0]));
					
					/** wujx 2015-6-15图片操作升级改造 */
					FileItem item = transfer.transferToFtp(transfer.getTempPath(imgObj.getImagename(),String.valueOf(imgObj.getTargetid()), TargetTypeEnum.valueOf(imgObj.getTargettype())), 
							tgoodInfoId.toString(), TargetTypeEnum.IMAGE_TARGETTYPE_GOODS, true);
					
					//移动文件：从临时路径移动到正式路径，并返回最新url
//					String newUrl = ImageUtil.moveMainPic(imgObj.getUrl(), tgoodInfoId.toString(), 
//							BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS, input.getReq(), true, true);
					imgObj.setUrl(item.getUrl());
					imgObj.setSort(Integer.valueOf(imgInfo[1]));
					imgObj.setTargetid(Long.valueOf(tgoodInfoId.toString()));
					//写库
					timagesMapper_W.updateByPrimaryKeySelective(imgObj);
					//整理图片文件名字符串
					tempImgsStr += getImgNamesStr(i, imgObj.getUrl(), imgs.length);
					usedImgs.add(imgObj.getTimageid());
				}
			}
			//删除不用的图片
			for (Timages img : imgsBeforeUpdate) {
				boolean delFlag = true; //删除标记，默认删除
				for (Integer usedImg : usedImgs) {
					if (img.getTimageid().intValue() == usedImg.intValue()) {
						delFlag = false;
					}
				}
				if (delFlag) {
					timagesMapper_W.deleteByPrimaryKey(img.getTimageid());
				}
			}
			
			//更新商品基本信息图片信息
			goodInfoBean.setGoodbigpic(tempImgsStr);
			goodInfoBean.setLastupdatedate(now);
			input.setGoodbigpic(tempImgsStr);
			tgoodinfoMapper_W.updateByPrimaryKeySelective(goodInfoBean);
			
			//调用mongo接口：初始化入参
	        GoodsCol col = new GoodsCol();
	        col.setSkus(new ArrayList<GoodsSKU>());
			mongo4GoodsService.setGoodsCol(col, goodInfoBean, null);
			
			/** wujx 2015-6-16图片操作升级改造 */
			//删除图片临时存放路径
	        //ImageUtil.deltemp(input.getReq(), input.getMobile());
			
			//获取本次操作日志批次号
			Integer batNo = getBatNo(tgoodInfoId);
			
			/**
			 * 处理商sku信息：
			 * 数据：
			 *     封装商品SKU信息数据：页面传入的sku信息包含sku属性、销售价、市场价、库存，数据格式：
			 *     属性1:属性值1,属性2:属性值1_销售价_市场价_库存#
			 *     属性1:属性值2,属性2:属性值2_销售价_市场价_库存
			 * 处理：
			 *     修改：修改前后都存在的sku；
			 *     删除：修改前存在，修改后不存在的sku
			 *     新增：修改前不存在，修改后存在的sku       */
			//修改前的sku信息
			List<Tgoodskuinfo> goodSkuInfoBeans = tgoodskuinfoMapper_R
					.selectSKUByGoodsId(tgoodInfoId);
			List<String> skusBeforeUpdate = new ArrayList<String>();
			for (Tgoodskuinfo blog : goodSkuInfoBeans) {
				skusBeforeUpdate.add(blog.getSkuidstr());
			}
			
			//修改后的sku信息
			String[] skus = input.getSkuidstr().split("#");
			List<String> skusAfterUpdate = new ArrayList<String>();
			for (String s : skus) {
				skusAfterUpdate.add(s.split("_")[0]);
			}
			
			List<String> skusForMod = new ArrayList<String>();
			List<String> skusForDel = new ArrayList<String>();
			List<String> skusForAdd = new ArrayList<String>();
			
			//修改前存在
			for (String s : skusBeforeUpdate) {
				if (skusAfterUpdate.contains(s)) {//修改后存在
					skusForMod.add(s);
				}else{//修改后不存在
					skusForDel.add(s);
				}
			}
			
			//修改后存在
			for (String s : skusAfterUpdate) {
				//修改前不存在
				if (!skusBeforeUpdate.contains(s)) {
					skusForAdd.add(s);
				}
			}
			
			//修改：修改前存在，修改后存在
			for (String s : skusForMod) {
				for (int i = 0; i < skus.length; i++) {
					//分解属性、销售价、市场价、最低销售价、销售价、库存，并记录在商品信息中
					String[] temp = skus[i].split("_");
					if(s.equals(temp[0])){
						Tgoodskuinfo blob = getGoodsSkuBean(goodSkuInfoBeans, s);
						setTgoodskuinfoWithBLOBs(blob, input);
						blob.setSaleprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
								temp[2]) : new BigDecimal("0"));
						blob.setMarketprice(StringUtil.isNumber(temp[3]) ? new BigDecimal(
								temp[3]) : new BigDecimal("0"));
						// zhiziwei 2015.07.09 自营商品结算价字段记录销售价，供下单时使用
						blob.setBalanceprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
								temp[2]) : new BigDecimal("0"));//结算价
						
						//更新的sku：一键上传生鲜商品（前端传入库存非0.00）更新页面传入的库存，除此之外的商品，不更新库存
						if (StringUtil.isNumber(temp[4]) && !"0.00".equals(temp[4])) {
							blob.setSkustock(StringUtil.isNumber(temp[4]) ? new BigDecimal(
									temp[4]) : new BigDecimal("0"));
						}
						blob.setGoodbigpic(input.getGoodbigpic());
						blob.setLastupdatedate(now);
						//写库：更新商品SKU信息表
						tgoodskuinfoMapper_W.updateByPrimaryKeySelective(blob);
						
						//记录日志
						writeGoodsLog(blob, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_MOD, 
								BussConst.GOODS_LOG_OPREATESRC_PROVIDERMOD, batNo);
						
						//调用Mongo接口：修改sku
						mongo4GoodsService.writeSkusToMongo(blob, null, input.getLoginusername(), input.getReq().getRemoteHost(), "mod");
						mongo4GoodsService.setGoodsCol(col, null, blob);
					}
				}
			}
			
			//删除：修改前存在，修改后不存在
			for (String s : skusForDel) {
				Tgoodskuinfo blob = getGoodsSkuBean(goodSkuInfoBeans, s);
				//写库：删除商品SKU信息表
				tgoodskuinfoMapper_W.deleteByPrimaryKey(blob.getTgoodskuinfoid());
				
				//记录日志
				writeGoodsLog(blob, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_DEL, 
						BussConst.GOODS_LOG_OPREATESRC_PROVIDERMOD, batNo);
			}
			
			//新增：修改前不存在，修改后存在
			for (String s : skusForAdd) {
				for (int i = 0; i < skus.length; i++) {
					//分解属性、销售指导价，并记录在商品信息中
					String[] temp = skus[i].split("_");
					if(s.equals(temp[0])){
						Tgoodskuinfo skuBean = new Tgoodskuinfo();
						setTgoodskuinfoWithBLOBs(skuBean, input);
						skuBean.setTgoodinfoid(tgoodInfoId);
						skuBean.setSkuidstr(temp[0]);
						
						skuBean.setSaleprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
								temp[2]) : new BigDecimal("0"));
						skuBean.setMarketprice(StringUtil.isNumber(temp[3]) ? new BigDecimal(
								temp[3]) : new BigDecimal("0"));
						
						//新增的sku：一键上传生鲜商品（前端传入库存非0.00）更新页面传入的库存，除此之外的商品，库存初始化为1000000
						if (StringUtil.isNumber(temp[4]) && !"0.00".equals(temp[4])) {
							skuBean.setSkustock(StringUtil.isNumber(temp[4]) ? new BigDecimal(
									temp[4]) : new BigDecimal("0"));
						} else {
							skuBean.setSkustock(new BigDecimal(1000000));
						}
						
						skuBean.setSupplierid(goodInfoBean.getSupplierid());
						skuBean.setSuppliername(goodInfoBean.getSuppliername());
						skuBean.setVirtualcateids(goodInfoBean.getVirtualcateids());
						skuBean.setGoodbigpic(input.getGoodbigpic());
						skuBean.setSkufrontdisnamestr("NONE".equals(temp[0]) ? "" : getSkuDisc(temp[0]));
						skuBean.setCreateddate(now);
						skuBean.setLastupdatedate(now);
						
						//写库：商品SKU信息表
						tgoodskuinfoMapper_W.insertSelective(skuBean);
						
						//记录日志
						writeGoodsLog(skuBean, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_NEW, 
								BussConst.GOODS_LOG_OPREATESRC_PROVIDERMOD, batNo);
						
						//调用Mongo接口：新增sku
						mongo4GoodsService.writeSkusToMongo(skuBean, null, input.getLoginusername(), input.getReq().getRemoteHost(), "add");
						mongo4GoodsService.setGoodsCol(col, null, skuBean);
					}
				}
			}
			
			/** 
			 * 模板商品，新增成功直接上架，自定义商品后台审核通过后上架；上架商品可编辑：模板商品，修改成功后下架，在重新上架，
			 * 自定义商品，修改成功后下架，重新审批，上架
			 */
			// 已上架商品，先下架
			if (uds.intValue() == BussConst.GOODS_GOODSSTATUS_ONSALE) {
				GoodsPublishInput gpi = new GoodsPublishInput();
				List<Integer> ids = new ArrayList<Integer>();
				ids.add(tgoodInfoId);
				gpi.setGoodsIdList(ids);
				gpi.setOpt(BussConst.GOODS_GOODSSTATUS_DOWNSALE);
				// 调用上/下架接口
				goodsPublishService.updateGoods(gpi);
				
				// 调用Mongo接口：下架
				mongo4GoodsService.downPublish(tgoodInfoId, null, input.getLoginusername(), input.getReq().getRemoteHost());
				
			}
			
			//调用Mongo接口：修改商品数据
			mongo4GoodsService.writeGoodsToMongo(col, goodInfoBean.getVirtualcateids(), null, input.getLoginusername(), input.getReq().getRemoteHost(), "mod");
			
			if (null == goodInfoBean.getTgoodinfopoolid()) {//自定义商品
				List<Approval> apps = approvalMapper_R.getAppListByDocentry(tgoodInfoId.toString());
				//插入新的审批数据前，将审批数据中待审核的数据关闭：isreturn字段置为1
				for (Approval app : apps) {
					if (app.getStatus().intValue() == ApprovalConstant.WAIT.intValue()) {
						app.setIsreturn(1);
						app.setStatus(ApprovalConstant.NOT_AGREE);
						app.setRemarks("店主修改待审核商品，重新提交了审核申请，该条申请作废！");
						approvalMapper_W.updateSelective(app);
					}
				}
				
				try {
					//商品信息，修改成功后，配置该商品审批数据
					Approval approval = new Approval();
					approval.setUserId(BussConst.APP2B_GOODS_PROPOSER);
					approval.setObjType(ApprovalConstant.OBJTYPE_ADD_GOODS);//审批模板数据类型
					approval.setDocentry(tgoodInfoId.toString());
					approval.setOwnerRemark("商品修改成功，请审批！");
					approval.setStatus(ApprovalConstant.WAIT);//审批状态：163   待审批
					approval.setUrl("goods/showgoodsdetail/"+goodInfoBean.getTgoodinfoid());//商品详情页
					//写库：审批模板配置表
					approvalService.insertSelective(approval);
				} catch (Exception e) {
					throw new LakalaException("NO_APPROVAL");
				}
			}
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return tgoodInfoId;
	}
	/**
	 * 修改商品（版本备份：2015年6月1日）
	 */
	public int updateGoodV1(TgoodsSkuInfoInput input) throws LakalaException{
		Date now = new Date(System.currentTimeMillis());//当前时间
		Integer tgoodInfoId = Integer.valueOf(input.getTgoodinfoid());//商品基本信息id
		
		try {
			//根据psam获取供应商信息
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByAPPPsam(input.getDeviceno());
			if (null == statistics) {
				throw new LakalaException("NO_SDBMEDIASTATISTICS");
			}
			input.setSupplierid(statistics.getId().intValue());
			input.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
			input.setOrgid(statistics.getOrgId());
			
			/* =================页面直接选择结算分类，屏蔽之前处理===================
			//添加模板商品时，后台读取虚分类信息
			if (!StringUtil.verdict(input.getVirtualcateids())) {
				String virCateStr = getVirCatByRealCat(input.getTrealcateid());
				if (!StringUtil.verdict(virCateStr)) {
					throw new LakalaException("NO_VIRTUALCATE");
				}
				input.setVirtualcateids(virCateStr);
			} else {//自定义上传自营商品
				String virCateStr = input.getVirtualcateids();
				input.setVirtualcateids(BussConst.APP_2C_FREQUENCYID + "-" + virCateStr.substring(0, virCateStr.length() - 1));
			}
			 */
			
			// 获取虚分类
			String virCateStr = getVirCatByRealCat(input.getTrealcateid());
			if (!StringUtil.verdict(virCateStr)) {
				throw new LakalaException("NO_VIRTUALCATE");
			}
			input.setVirtualcateids(virCateStr);
			
			//获取结算分类四级描述
			StringBuffer sb = new StringBuffer();
			getRcTreeDisc(sb, input.getTrealcateid());
			input.setTrealcatetreedisc(sb.toString().substring(0, sb.length() - 1));
			
			//封装商品基本信息数据
			Tgoodinfo goodInfoBean = tgoodinfoMapper_R.selectByPrimaryKey(tgoodInfoId);
			//处理商品基本信息
			setTgoodsinfoWithBLOBs(goodInfoBean, input);
			goodInfoBean.setLastupdatedate(now);
			
			//写库:商品基本信息表
			tgoodinfoMapper_W.updateByPrimaryKeySelective(goodInfoBean);
			
			//处理商品图片信息
			//修改前该商品关联的图片
			Map<String, Object> imgParm = new HashMap<String, Object>();
			imgParm.put("goodsId", Long.valueOf(tgoodInfoId.toString()));
			imgParm.put("targetType", BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS);
			List<Timages> imgsBeforeUpdate = timagesMapper_R.queryImgByGoodsId(imgParm);
			
			//修改后该商品关联的图片(多张图片之间用“;”隔开，页面传入图片数据包含图片id，及排序：图片id_排序)
			String[] imgs = StringUtil.verdict(input.getImgInfoList()) ?  String.valueOf(input.getImgInfoList())
					.split(";") : null;//页面图片数据
					//商品表图片字段数据格式：文件名,多张图片之间用“;”隔开
					String tempImgsStr = "";
					//缓存该商品用到的图片
					List<Integer> usedImgs = new ArrayList<Integer>();
					for (int i = 0; null != imgs && imgs.length > 0 && i < imgs.length; i++) {
						boolean flag = false;//更新标记,默认未更新
						//处理原来就存在的图片
						for (Timages timages : imgsBeforeUpdate) {
							String[] imgInfo = imgs[i].split("_");
							if (timages.getTimageid().equals(Integer.valueOf(imgInfo[0]))) {// 更新图片数据
								timages.setSort(Integer.valueOf(imgInfo[1]));
								// 写库
								timagesMapper_W.updateByPrimaryKeySelective(timages);
								//更新更新标记
								flag = true;
								//整理图片文件名字符串
								tempImgsStr += getImgNamesStr(i, timages.getUrl(), imgs.length);
								usedImgs.add(timages.getTimageid());
							}
						}
						//处理新增的图片
						if(!flag){//未更新，说明是新增的图片
							String[] imgInfo = imgs[i].split("_");
							Timages imgObj = timagesMapper_R.selectByPrimaryKey(Integer.valueOf(imgInfo[0]));
							
							/** wujx 2015-6-15图片操作升级改造 */
							FileItem item = transfer.transferToFtp(transfer.getTempPath(imgObj.getImagename(),String.valueOf(imgObj.getTargetid()), TargetTypeEnum.valueOf(imgObj.getTargettype())), 
									tgoodInfoId.toString(), TargetTypeEnum.IMAGE_TARGETTYPE_GOODS, true);
							
							//移动文件：从临时路径移动到正式路径，并返回最新url
//							String newUrl = ImageUtil.moveMainPic(imgObj.getUrl(), tgoodInfoId.toString(), 
//									BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS, input.getReq(), true, true);
							imgObj.setUrl(item.getUrl());
							imgObj.setSort(Integer.valueOf(imgInfo[1]));
							imgObj.setTargetid(Long.valueOf(tgoodInfoId.toString()));
							//写库
							timagesMapper_W.updateByPrimaryKeySelective(imgObj);
							//整理图片文件名字符串
							tempImgsStr += getImgNamesStr(i, imgObj.getUrl(), imgs.length);
							usedImgs.add(imgObj.getTimageid());
						}
					}
					//删除不用的图片
					for (Timages img : imgsBeforeUpdate) {
						boolean delFlag = true; //删除标记，默认删除
						for (Integer usedImg : usedImgs) {
							if (img.getTimageid().intValue() == usedImg.intValue()) {
								delFlag = false;
							}
						}
						if (delFlag) {
							timagesMapper_W.deleteByPrimaryKey(img.getTimageid());
						}
					}
					
					//更新商品基本信息图片信息
					goodInfoBean.setGoodbigpic(tempImgsStr);
					input.setGoodbigpic(tempImgsStr);
					tgoodinfoMapper_W.updateByPrimaryKeySelective(goodInfoBean);
					
					/** wujx 2015-6-16图片操作升级改造 */
					//删除图片临时存放路径
					//ImageUtil.deltemp(input.getReq(), input.getMobile());
					
					//获取本次操作日志批次号
					Integer batNo = getBatNo(tgoodInfoId);
					
					/**
					 * 处理商sku信息：
					 * 数据：
					 *     封装商品SKU信息数据：页面传入的sku信息包含sku属性、销售价、市场价、库存，数据格式：
					 *     属性1:属性值1,属性2:属性值1_销售价_市场价_库存#
					 *     属性1:属性值2,属性2:属性值2_销售价_市场价_库存
					 * 处理：
					 *     修改：修改前后都存在的sku；
					 *     删除：修改前存在，修改后不存在的sku
					 *     新增：修改前不存在，修改后存在的sku       */
					//修改前的sku信息
					List<Tgoodskuinfo> goodSkuInfoBeans = tgoodskuinfoMapper_R
							.selectSKUByGoodsId(tgoodInfoId);
					List<String> skusBeforeUpdate = new ArrayList<String>();
					for (Tgoodskuinfo blog : goodSkuInfoBeans) {
						skusBeforeUpdate.add(blog.getSkuidstr());
					}
					
					//修改后的sku信息
					String[] skus = input.getSkuidstr().split("#");
					List<String> skusAfterUpdate = new ArrayList<String>();
					for (String s : skus) {
						skusAfterUpdate.add(s.split("_")[0]);
					}
					
					List<String> skusForMod = new ArrayList<String>();
					List<String> skusForDel = new ArrayList<String>();
					List<String> skusForAdd = new ArrayList<String>();
					
					//修改前存在
					for (String s : skusBeforeUpdate) {
						if (skusAfterUpdate.contains(s)) {//修改后存在
							skusForMod.add(s);
						}else{//修改后不存在
							skusForDel.add(s);
						}
					}
					
					//修改后存在
					for (String s : skusAfterUpdate) {
						//修改前不存在
						if (!skusBeforeUpdate.contains(s)) {
							skusForAdd.add(s);
						}
					}
					
					//修改：修改前存在，修改后存在
					for (String s : skusForMod) {
						for (int i = 0; i < skus.length; i++) {
							//分解属性、销售价、市场价、最低销售价、销售价、库存，并记录在商品信息中
							String[] temp = skus[i].split("_");
							if(s.equals(temp[0])){
								Tgoodskuinfo blob = getGoodsSkuBean(goodSkuInfoBeans, s);
								setTgoodskuinfoWithBLOBs(blob, input);
								blob.setSaleprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
										temp[2]) : new BigDecimal("0"));
								blob.setMarketprice(StringUtil.isNumber(temp[3]) ? new BigDecimal(
										temp[3]) : new BigDecimal("0"));
								
								//更新的sku：一键上传生鲜商品（前端传入库存非0.00）更新页面传入的库存，除此之外的商品，不更新库存
								if (StringUtil.isNumber(temp[4]) && !"0.00".equals(temp[4])) {
									blob.setSkustock(StringUtil.isNumber(temp[4]) ? new BigDecimal(
											temp[4]) : new BigDecimal("0"));
								}
								blob.setGoodbigpic(input.getGoodbigpic());
								blob.setLastupdatedate(now);
								//写库：更新商品SKU信息表
								tgoodskuinfoMapper_W.updateByPrimaryKeySelective(blob);
								
								//记录日志
								writeGoodsLog(blob, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_MOD, 
										BussConst.GOODS_LOG_OPREATESRC_PROVIDERMOD, batNo);
							}
						}
					}
					
					//删除：修改前存在，修改后不存在
					for (String s : skusForDel) {
						Tgoodskuinfo blob = getGoodsSkuBean(goodSkuInfoBeans, s);
						//写库：删除商品SKU信息表
						tgoodskuinfoMapper_W.deleteByPrimaryKey(blob.getTgoodskuinfoid());
						
						//记录日志
						writeGoodsLog(blob, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_DEL, 
								BussConst.GOODS_LOG_OPREATESRC_PROVIDERMOD, batNo);
					}
					
					//新增：修改前不存在，修改后存在
					for (String s : skusForAdd) {
						for (int i = 0; i < skus.length; i++) {
							//分解属性、销售指导价，并记录在商品信息中
							String[] temp = skus[i].split("_");
							if(s.equals(temp[0])){
								Tgoodskuinfo skuBean = new Tgoodskuinfo();
								setTgoodskuinfoWithBLOBs(skuBean, input);
								skuBean.setTgoodinfoid(tgoodInfoId);
								skuBean.setSkuidstr(temp[0]);
								
								skuBean.setSaleprice(StringUtil.isNumber(temp[2]) ? new BigDecimal(
										temp[2]) : new BigDecimal("0"));
								skuBean.setMarketprice(StringUtil.isNumber(temp[3]) ? new BigDecimal(
										temp[3]) : new BigDecimal("0"));
								
								//新增的sku：一键上传生鲜商品（前端传入库存非0.00）更新页面传入的库存，除此之外的商品，库存初始化为1000000
								if (StringUtil.isNumber(temp[4]) && !"0.00".equals(temp[4])) {
									skuBean.setSkustock(StringUtil.isNumber(temp[4]) ? new BigDecimal(
											temp[4]) : new BigDecimal("0"));
								} else {
									skuBean.setSkustock(new BigDecimal(1000000));
								}
								
								skuBean.setSupplierid(goodInfoBean.getSupplierid());
								skuBean.setSuppliername(goodInfoBean.getSuppliername());
								skuBean.setVirtualcateids(goodInfoBean.getVirtualcateids());
								skuBean.setGoodbigpic(input.getGoodbigpic());
								skuBean.setSkufrontdisnamestr("NONE".equals(temp[0]) ? "" : getSkuDisc(temp[0]));
								skuBean.setCreateddate(now);
								skuBean.setLastupdatedate(now);
								
								//写库：商品SKU信息表
								tgoodskuinfoMapper_W.insertSelective(skuBean);
								
								//记录日志
								writeGoodsLog(skuBean, input.getLoginusername(), BussConst.GOODS_LOG_ACTIONTYPE_NEW, 
										BussConst.GOODS_LOG_OPREATESRC_PROVIDERMOD, batNo);
							}
						}
					}
					
					//模板商品，新增成功直接上架，自定义商品后台审核通过后上架
					if (null == goodInfoBean.getTgoodinfopoolid()) {//自定义商品
						List<Approval> apps = approvalMapper_R.getAppListByDocentry(tgoodInfoId.toString());
						//插入新的审批数据前，将审批数据中待审核的数据关闭：isreturn字段置为1
						for (Approval app : apps) {
							if (app.getStatus().intValue() == ApprovalConstant.WAIT.intValue()) {
								app.setIsreturn(1);
								app.setStatus(ApprovalConstant.NOT_AGREE);
								app.setRemarks("店主修改待审核商品，重新提交了审核申请，该条申请作废！");
								approvalMapper_W.updateSelective(app);
							}
						}
						
						try {
							//商品信息，修改成功后，配置该商品审批数据
							Approval approval = new Approval();
							approval.setUserId(BussConst.APP2B_GOODS_PROPOSER);
							approval.setObjType(ApprovalConstant.OBJTYPE_ADD_GOODS);//审批模板数据类型
							approval.setDocentry(tgoodInfoId.toString());
							approval.setOwnerRemark("商品新增成功，请审批！");
							approval.setStatus(ApprovalConstant.WAIT);//审批状态：163   待审批
							approval.setUrl("goods/showgoodsdetail/"+goodInfoBean.getTgoodinfoid());//商品详情页
							//写库：审批模板配置表
							approvalService.insertSelective(approval);
						} catch (Exception e) {
							throw new LakalaException("NO_APPROVAL");
						}
					}
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return tgoodInfoId;
	}
	
	
	/**
	 * 获取商品主图文件名字符串，用“;”分割 
	 */
	private String getImgNamesStr(int index, String imgUrl, int length){
		String temp = "";
		if (index == length - 1)
			temp += imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.length());
		else
			temp += (imgUrl.substring(imgUrl.lastIndexOf("/") + 1, imgUrl.length()) + ";");
		return temp;
	}
	
	/**
	 * 根据sku属性获取bean
	 */
	private Tgoodskuinfo getGoodsSkuBean(List<Tgoodskuinfo> beans, String skuIdStr){
		Tgoodskuinfo obj = null;
		for (Tgoodskuinfo blob : beans) {
			if (skuIdStr.equals(blob.getSkuidstr())) {
				obj = blob;
			}
		}
		return obj;
	}
	
	/**
	 * 获取sku属性值描述
	 */
	private String getSkuDisc(String skuId){
		String result = "";
		String[] pros = skuId.split(",");
		for (int i = 0; i < pros.length; i++) {
			String temp = tpropertyvalueMapper_R
					.selectByPrimaryKey(Integer.valueOf(pros[i].split(":")[1])).getPropertyvalue();
			if(i != pros.length - 1){
				result += (temp + "x");
			}else{
				result += temp;
			}
		}
		return result;
	}
	
    /**
     * @Title: setTgoodsinfoWithBLOBs
     * @Description: 封装页面数据（TgoodsSkuInfoInput）
     */
    private Tgoodinfo setTgoodsinfoWithBLOBs(Tgoodinfo bean, TgoodsSkuInfoInput parm) {
        //页面数据
    	bean.setTrealcateid(parm.getTrealcateid());
    	bean.setTrealcatedisc(parm.getTrealcatedisc());
    	bean.setTrealcatetreedisc(parm.getTrealcatetreedisc());
    	bean.setSupplierid(parm.getSupplierid());
    	bean.setSuppliername(parm.getSuppliername());
    	bean.setTbrandid(parm.getTbrandid());
    	bean.setBrandname(StringUtil.verdict(parm.getBrandname()) ? parm.getBrandname(): "");
    	bean.setGoodname(StringUtil.verdict(parm.getGoodname()) ? parm.getGoodname().trim() : "");
    	bean.setGoodbarcode(StringUtil.verdict(parm.getGoodbarcode()) ? parm.getGoodbarcode() : "");
    	bean.setGoodintroduce(StringUtil.verdict(parm.getGoodintroduce()) ? parm.getGoodintroduce().trim() : "");
    	bean.setIspayafterarrival(parm.getIspayafterarrival());
    	bean.setIsnoreasonreturn(0); // 默认否
    	bean.setIscontractmachine(0); // 默认否
    	bean.setIsexpresstohome(parm.getIsexpresstohome());
    	bean.setDistributionflag(362); // 默认 362  一小时送达
        bean.setMeasureunits("件"); // 默认 【件】
        bean.setIsforeigngoods(0); //默认否
        bean.setStoragecondition(1); //默认常温
        bean.setDistributioncondition(1);//默认常温
        bean.setGoodtype(202);//默认 202 正常商品
        bean.setBalanceway(1);//默认 1 按扣点结算
        bean.setTempstoregoodsflag(380);//380 不暂存
        bean.setPlatorself(BussConst.GOODS_PLATORSELF_SELF);//自营-452，平台-453
        bean.setDeflag(BussConst.GOODS_DELFLAG_VALID); //删除标记，默认有效
        bean.setDeviceno(parm.getDeviceno());
        bean.setOrgid(parm.getOrgid());
        if (null != parm.getIsfreshfood()) {//生鲜标记
			bean.setIsfreshfood(parm.getIsfreshfood());
		}
        if (StringUtil.verdict(parm.getVirtualcateids())) {
        	bean.setVirtualcateids(parm.getVirtualcateids());
		}
        if (StringUtil.verdict(parm.gettGoodInfoPoolId())) {
        	bean.setTgoodinfopoolid(Integer.valueOf(parm.gettGoodInfoPoolId()));
        	bean.setGoodstatus(197);//197: 审核通过
        	bean.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_ONSALE);//商品销售状态（在售:208/已下架:209）
		} else {
			bean.setGoodstatus(196);//196: 待审核
			bean.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_DOWNSALE);//商品销售状态（在售:208/已下架:209）
		}
        
        return bean;
    }
    
    /**
     * @Title: setTgoodskuinfoWithBLOBs
     * @Description: 封装页面数据（TgoodsSkuInfoInput）
     */
    private Tgoodskuinfo setTgoodskuinfoWithBLOBs(Tgoodskuinfo bean, TgoodsSkuInfoInput parm) {
        //页面数据
    	bean.setTrealcateid(parm.getTrealcateid());
    	bean.setTrealcatedisc(parm.getTrealcatedisc());
    	bean.setTrealcatetreedisc(parm.getTrealcatetreedisc());
    	bean.setSupplierid(parm.getSupplierid());
    	bean.setSuppliername(parm.getSuppliername());
    	bean.setTbrandid(parm.getTbrandid());
		bean.setBrandname(StringUtil.verdict(parm.getBrandname()) ? parm.getBrandname() : "");
    	bean.setGoodname(StringUtil.verdict(parm.getGoodname()) ? parm.getGoodname().trim() : "");
		bean.setGoodbarcode(StringUtil.verdict(parm.getGoodbarcode()) ? parm.getGoodbarcode() : "");
		bean.setGoodintroduce(StringUtil.verdict(parm.getGoodintroduce()) ? parm.getGoodintroduce().trim() : "");
    	bean.setIspayafterarrival(parm.getIspayafterarrival());
     	bean.setIsnoreasonreturn(0); // 默认否
    	bean.setIscontractmachine(0); // 默认否
    	bean.setIsexpresstohome(parm.getIsexpresstohome());
    	bean.setDistributionflag(362); // 默认 362  一小时送达
        bean.setMeasureunits("件"); // 默认 【件】
        bean.setIsforeigngoods(0); // 默认否
        bean.setStoragecondition(1); // 默认常温
        bean.setDistributioncondition(1);// 默认常温
        bean.setGoodtype(202);// 默认 202 正常商品
        bean.setBalanceway(1);// 默认 1 按扣点结算
        bean.setTempstoregoodsflag(380);//380 不暂存
        bean.setPlatorself(BussConst.GOODS_PLATORSELF_SELF);//自营-452，平台-453
        bean.setDeviceno(parm.getDeviceno());
        bean.setDeflag(BussConst.GOODS_DELFLAG_VALID); //删除标记，默认有效
        bean.setOrgid(parm.getOrgid());
        if (null != parm.getIsfreshfood()) {//生鲜标记
			bean.setIsfreshfood(parm.getIsfreshfood());
		}
        if (StringUtil.verdict(parm.getVirtualcateids())) {
        	bean.setVirtualcateids(parm.getVirtualcateids());
		}
        if (StringUtil.verdict(parm.gettGoodInfoPoolId())) {
        	bean.setTgoodinfopoolid(Integer.valueOf(parm.gettGoodInfoPoolId()));
        	bean.setGoodstatus(197);//197: 审核通过
        	bean.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_ONSALE);//商品销售状态（在售:208/已下架:209）
		} else {
			bean.setGoodstatus(196);//196: 待审核
			bean.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_DOWNSALE);//商品销售状态（在售:208/已下架:209）
		}
        
        return bean;
    }

	/**
	 * @Title: getShopperRegion
	 * 整理店主的地区信息
	 */
    private List<String> getShopperRegion(VirtualCateInput input) throws LakalaException{
    	List<String> regions = new ArrayList<String>();
    	try {
    		//终端所属省市区
			SDBMediaStatistics media = sdbMediaStatisticsMapper_R.selectByPsam(input.getPsam());
			if (!StringUtil.verdict(media.getCityAreaNo())
					|| !StringUtil.verdict(media.getCityNo()) || !StringUtil.verdict(media.getProvNo())) {
				throw new LakalaException("终端省市区未维护！");
			}
			// 过滤出可在店主所在省、市、区，或片区售卖的商品
			regions.add("%all%"); // 全国
			regions.add("%" + media.getProvNo() + "%");
			regions.add("%" + media.getCityNo() + "%");
			regions.add("%" + media.getCityAreaNo() + "%");

			// 获取店主所属区下的片区信息
			if (StringUtil.verdict(media.getCommunityNo())) {
				regions.add("%" + media.getCommunityNo() + "%");
			} else {
				List<Tregion> ss = tregionMapper_R.selectSsByRsCode(media
						.getCityAreaNo());
				if (null != ss) {
					for (Tregion s : ss) {
						regions.add("%" + s.getCode() + "%");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
    	return regions;
    }
	/**
	 * @Description 获取2C使用的所有虚分类（即营销分类），只返回有商品的分类。
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<VirtualCateVO>> queryVirtualCat4C(VirtualCateInput input) throws LakalaException{
		logger.info("获取所有虚分类....");
		
		ObjectOutput<List<VirtualCateVO>> result = new ObjectOutput<List<VirtualCateVO>>();
		result._ReturnData = new ArrayList<VirtualCateVO>();
		
		try {
			//读取所有实分类数据
			List<VirtualCate> vcs = virtualCateMapper_R.findAll4C();
			
			//整理查询参数
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("region", getShopperRegion(input));
			parm.put("vcs", vcs);
			
			//查询有商品的2C下的营销分类
			List<Integer> vids = virtualCateMapper_R.selectVcsForHasGoodsPool(parm);
			
			//过滤掉没有商品的营销分类
			Set<VirtualCate> _vcs = new HashSet<VirtualCate>();
			for (VirtualCate vc : vcs) {
				if (vids.contains(vc.getTvirtualcateid())) {
					_vcs.add(vc);
					//递归取出所有上级父分类
					setFvcs(vc, vcs, _vcs);
				}
			}
			
			//整理返回值
			for (VirtualCate r : _vcs) {
				VirtualCateVO vo = new VirtualCateVO();
				vo.settVirtualCateId(r.getTvirtualcateid());
				vo.setVirtualCateDisc(r.getVirtualcatedisc());
				vo.setFatherVirtualCateId(r.getFathervirtualcateid());
				result._ReturnData.add(vo);
			}
			
			//排序
			Collections.sort(result._ReturnData, new Comparator<VirtualCateVO>() {
				@Override
				public int compare(VirtualCateVO o1, VirtualCateVO o2) {
					return o1.gettVirtualCateId().compareTo(o2.gettVirtualCateId());
				}
			});
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return result;
	}
	
	/**
	 * @Description 获取2B使用的所有虚分类（即营销分类）只返回有产品库商品的分类。
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<VirtualCateVO>> queryVirtualCat4B(VirtualCateInput input) throws LakalaException{
		logger.info("获取2B使用的所有虚分类....");
		
		ObjectOutput<List<VirtualCateVO>> result = new ObjectOutput<List<VirtualCateVO>>();
		result._ReturnData = new ArrayList<VirtualCateVO>();
		
		try {
			//读取所有B端营销分类数据
			List<VirtualCate> vcs = virtualCateMapper_R.findAll4B();
			
			//整理查询参数
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("region", getShopperRegion(input));
			parm.put("vcs", vcs);
			//查询有商品的2B下的营销分类
			List<Integer> vids = virtualCateMapper_R.selectVcsForHasGoodsPool(parm);
			
			//过滤掉没有商品的营销分类
			Set<VirtualCate> _vcs = new HashSet<VirtualCate>();
			for (VirtualCate vc : vcs) {
				if (vids.contains(vc.getTvirtualcateid())) {
					_vcs.add(vc);
					//递归取出所有上级父分类
					setFvcs(vc, vcs, _vcs);
				}
			}
			
			//整理返回值
			for (VirtualCate r : _vcs) {
				// 生产环境屏蔽“本周爆款”
				if (null == r.getTvirtualcateid() || r.getTvirtualcateid().compareTo(40673) == 0) 
					continue;
					
				VirtualCateVO vo = new VirtualCateVO();
				vo.settVirtualCateId(r.getTvirtualcateid());
				vo.setVirtualCateDisc(r.getVirtualcatedisc());
				vo.setFatherVirtualCateId(r.getFathervirtualcateid());
				result._ReturnData.add(vo);
			}
			
			//排序
			Collections.sort(result._ReturnData, new Comparator<VirtualCateVO>() {
				@Override
				public int compare(VirtualCateVO o1, VirtualCateVO o2) {
					return o1.gettVirtualCateId().compareTo(o2.gettVirtualCateId());
				}
			});
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return result;
	}
	
	/**
	 * @Description 获取2B使用的营销分类，只返回关联结算分类的分类。
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<VirtualCateVO>> queryAllVirtualCat4B() throws LakalaException{
		logger.info("获取2B使用的营销分类，只返回关联结算分类的分类....");
		
		ObjectOutput<List<VirtualCateVO>> result = new ObjectOutput<List<VirtualCateVO>>();
		result._ReturnData = new ArrayList<VirtualCateVO>();
		
		try {
			//读取所有B端营销分类数据
			List<VirtualCate> vcs = virtualCateMapper_R.findAll4B();
			
			//查询关联了结算分类的2B下的营销分类
			List<Integer> vids = virtualCateMapper_R.selectVcsByRcs(vcs);
			
			//过滤掉没有商品的营销分类
			Set<VirtualCate> _vcs = new HashSet<VirtualCate>();
			for (VirtualCate vc : vcs) {
				if (vids.contains(vc.getTvirtualcateid())) {
					_vcs.add(vc);
					//递归取出所有上级父分类
					setFvcs(vc, vcs, _vcs);
				}
			}
			
			//整理返回值
			for (VirtualCate r : _vcs) {
				// 生产环境屏蔽“本周爆款”
				if (null == r.getTvirtualcateid() || r.getTvirtualcateid().compareTo(40673) == 0) 
					continue;
				
				VirtualCateVO vo = new VirtualCateVO();
				vo.settVirtualCateId(r.getTvirtualcateid());
				vo.setVirtualCateDisc(r.getVirtualcatedisc());
				vo.setFatherVirtualCateId(r.getFathervirtualcateid());
				result._ReturnData.add(vo);
			}
			
			//排序
			Collections.sort(result._ReturnData, new Comparator<VirtualCateVO>() {
				@Override
				public int compare(VirtualCateVO o1, VirtualCateVO o2) {
					return o1.gettVirtualCateId().compareTo(o2.gettVirtualCateId());
				}
			});
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return result;
	}
	
	/**
	 * 递归取出所有上级父分类
	 */
	private void setFvcs(VirtualCate vc, List<VirtualCate> vcs, Set<VirtualCate> _vcs) {
		// 一级分类时结束方法
		if (vc.getFathervirtualcateid().intValue() == 0) {
			return;
		}
		
		// 非一级分类
		for (VirtualCate _vc : vcs) {
			if (vc.getFathervirtualcateid().compareTo(_vc.getTvirtualcateid()) == 0) {
				_vcs.add(_vc);
				setFvcs(_vc, vcs, _vcs);
			}
		}
	}

	/**
	 * @Description 根据四级实分类获取sku属性数据
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<PropertyKeyVO>> queryGoodProperty(
			GoodsInput input) throws LakalaException {
		logger.info("根据四级实分类获取sku属性数据：input = " + input.toString());
	
		//取出请求参数
		String trealcateid = input.gettRealCateId();
		//定义返回值
		ObjectOutput<List<PropertyKeyVO>> result = new ObjectOutput<List<PropertyKeyVO>>();
		result._ReturnData = new ArrayList<PropertyKeyVO>();

		// 异常判断
		if (!StringUtil.verdict(trealcateid)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}

		try {
			// 业务处理
			List<Tpropertykey> proKeyList = tpropertykeyMapper_R
					.getGoodProperty(Integer.valueOf(trealcateid));
			
			// 遍历属性，并查询每个属性的值
			for (Tpropertykey tk : proKeyList) {
				
				PropertyKeyVO keyVo = new PropertyKeyVO();
				keyVo.settPropertyKeyId(tk.getTpropertykeyid());
				keyVo.setPropertyName(tk.getPropertyname());
				
				// 读取属性值
				List<Tpropertyvalue> values = tpropertyvalueMapper_R
						.queryByPropertyId(tk.getTpropertykeyid());
				
				//没有属性值的属性过滤掉，不返回
				if (null == values || values.size() == 0) {
					continue;
				}
				
				List<PropertyValueVO> valueVos = new ArrayList<PropertyValueVO>();
				for (Tpropertyvalue value : values) {
					PropertyValueVO valueVo = new PropertyValueVO();
					valueVo.settPropertyValueId(value.getTpropertyvalueid());
					valueVo.setPropertyValue(value.getPropertyvalue());
					valueVo.setOrderBy(value.getOrderby());
					
					valueVos.add(valueVo);
				}
				keyVo.setPropertyValues(valueVos);
				
				result._ReturnData.add(keyVo);
			}
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return result;
	}
	
	/**
	 * @Description: 记录商品日志
	 * @author zhiziwei
	 */
	private void writeGoodsLog(Tgoodskuinfo sku, String opreater,
			Integer actionType, Integer opreateSrc, Integer batNo) throws Exception{

		// 使用BeanUtils.copyProperties,bean中如果有BigDecimal属性，要注册默认值
        BigDecimalConverter bd = new BigDecimalConverter(new BigDecimal(0));
        ConvertUtils.register(bd, java.math.BigDecimal.class);
        ConvertUtils.register(null, java.util.Date.class);
        
		TgoodslogWithBLOBs logBean = new TgoodslogWithBLOBs();
		
		//复制商品数据
		BeanUtils.copyProperties(logBean, sku);
		
		logBean.setBatno(batNo);
		logBean.setOpreatesrc(opreateSrc);
		logBean.setActiontype(actionType);
		logBean.setOpreater(opreater);
		
		tgoodslogMapper_W.insertSelective(logBean);
	}
	
	/**
	 * @Description: 获取本次操作日志批次号
	 * @author zhiziwei
	 */
	private Integer getBatNo(Integer tgoodinfoid) {
		Integer batNo = tgoodslogMapper_R.getMaxBatNoByGoodsId(tgoodinfoid);
		return null == batNo ? 1 : batNo + 1;
	}

	/**
	 * @Description 根据虚分类获取其关联的实分类（只返回末级分类）
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<RealCateVO>> queryRealCatByVirtualcat(
			GoodsInput input) throws LakalaException {
		logger.info("根据虚分类获取其关联的实分类：input = " + input.toString());

		// 取出请求参数
		String tVirtualCateId = input.gettVirtualCateId();
		// 定义返回值
		ObjectOutput<List<RealCateVO>> result = new ObjectOutput<List<RealCateVO>>();
		result._ReturnData = new ArrayList<RealCateVO>();

		// 异常判断
		if (!StringUtil.verdict(tVirtualCateId)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}
		
		try {
			//获取与其关联的实分类（实分类末级分类）
			List<Realcate> rcats = realcateMapper_R.selectByVirtualCateByRealCate(
					Integer.valueOf(tVirtualCateId));
			
			//验证：是否有关联的实分类
			if (null == rcats || rcats.size() == 0) {
				result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
				result.set_ReturnMsg("未关联实分类！");
				return result;
			}
			
			for (Realcate rcat : rcats) {
				//该实分类以及上级分类数据
				List<Realcate> list = new ArrayList<Realcate>();
				list.add(rcat);
				//分类描述
				StringBuffer treeCateDisc = new StringBuffer("》" + rcat.getRealcatedisc());
				
				Integer fCateId = rcat.getFatherrealcateid();
				
				//获取该分类的所有上级分类，并逐级拼接分类描述
				getParentCate(list, fCateId, treeCateDisc);
				
				//整理返回数据
				RealCateVO vo = new RealCateVO();
				
				vo.settRealCateId(rcat.getTrealcateid());
				vo.setFatherRealCateId(rcat.getFatherrealcateid());
				vo.setRealCateDisc(rcat.getRealcatedisc());
				vo.settRealCateTreeDisc(treeCateDisc.toString());
				result._ReturnData.add(vo);
				
			}
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return result;
	}

	/**
	 * 
	 * @Title: getParentCate
	 * @Description: 递归获取父级分类
	 * @author zhiziwei
	 */
	private void getParentCate(List<Realcate> list, Integer parentCateId, StringBuffer treeCateDisc){
		//获取父分类数据
		Realcate cate = realcateMapper_R.selectByPrimaryKey(parentCateId);
		if (null == cate) 
			return;
		list.add(cate);
		
		//递归获取上级分类
		if (cate.getFatherrealcateid().intValue() != 0) {
			treeCateDisc.insert(0, "》" + cate.getRealcatedisc());
			//递归调用
			getParentCate(list, cate.getFatherrealcateid(), treeCateDisc);
		}else {
			treeCateDisc.insert(0, cate.getRealcatedisc());
		}
	}

	/**
	 * @Description 删除指定商品
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<String> deleteGoods(GoodsInput input)
			throws LakalaException {
		
		logger.info("删除指定商品：input = " + input.toString());

		// 取出请求参数
		String tGoodsInfoId = input.gettGoodsInfoId();
		// 定义返回值
		ObjectOutput<String> result = new ObjectOutput<String>();

		// 异常判断
		if (!StringUtil.verdict(tGoodsInfoId)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}
		
		try {
			Integer goodsId = Integer.valueOf(tGoodsInfoId);
			//更新商品主表
			Tgoodinfo goodInfo = tgoodinfoMapper_R.selectByPrimaryKey(goodsId);
			
			if (BussConst.GOODS_GOODSSTATUS_ONSALE == goodInfo.getGoodsstatus()
					.intValue()) {//在售商品校验：在售商品不可删除
				result.set_ReturnData("商品在售，不可删除！");
			} else if(BussConst.GOODS_PLATORSELF_PLAT == goodInfo.getPlatorself()
					.intValue()){//是否是自营商品校验：非自营商品，不可删除
				result.set_ReturnData("非自营商品，不可删除！");
			} else {
				//符合删除条件
				goodInfo.setDeflag(BussConst.GOODS_DELFLAG_INVALID);
				tgoodinfoMapper_W.updateByPrimaryKeySelective(goodInfo);
				
				//更新SKU表
				List<Tgoodskuinfo> skus = tgoodskuinfoMapper_R.selectSKUByGoodsId(goodsId);
				
				for (Tgoodskuinfo sku : skus) {
					sku.setDeflag(BussConst.GOODS_DELFLAG_INVALID);
					tgoodskuinfoMapper_W.updateByPrimaryKeySelective(sku);
				}
				
				result.set_ReturnData("删除成功！");
			}
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
 		} catch (Exception e) {
 			throw new LakalaException(e);
		}
		
		return result;
	}

    /**
     * @Description 获取该用户所属商品的虚拟分类（2C下的营销分类）
     * @author zhiziwei
     */
	@Override
	public ObjectOutput<List<VirtualCateVO>> queryVirCate4CByGoods(GoodsInput input)
			throws LakalaException {
		// 取出请求参数
		String psam = input.getPsam();
		// 定义返回值
		ObjectOutput<List<VirtualCateVO>> result = new ObjectOutput<List<VirtualCateVO>>();
		result._ReturnData = new ArrayList<VirtualCateVO>();

		// 异常判断
		if (!StringUtil.verdict(psam)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}
		
		try {
			//根据手机号获取供应商信息
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByAPPPsam(psam);
			if (null == statistics) {
				throw new LakalaException("NO_MEDIA");
			}
			//获取指定供应商的所有自营商品
			List<Tgoodinfo> goods = tgoodinfoMapper_R.selectBySupplierId(statistics.getId().intValue());
			
			//遍历取出所有虚分类ID，过滤去重
			Set<Integer> tempSet = new HashSet<Integer>();
			for (Tgoodinfo tg : goods) {
				//取每个商品的虚分类信息：频道ID-虚分类1_虚分类2...
				String[] vircateStrs = tg.getVirtualcateids().split(",");
				for (String vircateStr : vircateStrs) {
					if (StringUtil.verdict(vircateStr)) {
						String[] _vircateStr = vircateStr.split("-")[1].split("_");
						//缓存并过滤虚分类ID
						for (int i = 0; i < _vircateStr.length; i++) {
							tempSet.add(Integer.valueOf(_vircateStr[i]));
						}
					}
				}
			}
			
			//查询分类信息
			Iterator<Integer> it = tempSet.iterator();
			while (it.hasNext()) {
				VirtualCate vc = virtualCateMapper_R.selectByPrimaryKeyFor2C(it.next());
				if (null == vc || vc.getTvirtualcateid().intValue() == 754)
					continue;
				VirtualCateVO vo = new VirtualCateVO();
				vo.settVirtualCateId(vc.getTvirtualcateid());
				vo.setVirtualCateDisc(vc.getVirtualcatedisc());
				vo.setFatherVirtualCateId(vc.getFathervirtualcateid());
				
				result._ReturnData.add(vo);
			}
			
			//排序
			Collections.sort(result._ReturnData, new Comparator<VirtualCateVO>() {
				@Override
				public int compare(VirtualCateVO o1, VirtualCateVO o2) {
					return o1.gettVirtualCateId().compareTo(o2.gettVirtualCateId());
				}
			});
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return result;
	}
	
	/**
	 * @Description 获取该用户所属商品的虚拟分类（2B下的营销分类）
	 * @author zhiziwei
	 */
	@Override
	public ObjectOutput<List<VirtualCateVO>> queryVirCate4BByGoods(GoodsInput input)
			throws LakalaException {
		// 取出请求参数
		String psam = input.getPsam();
		// 定义返回值
		ObjectOutput<List<VirtualCateVO>> result = new ObjectOutput<List<VirtualCateVO>>();
		result._ReturnData = new ArrayList<VirtualCateVO>();
		
		// 异常判断
		if (!StringUtil.verdict(psam)) {
			result.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
			result.set_ReturnMsg(ReturnMsg.MSG_PARAMS_ERROR);
			return result;
		}
		
		try {
			//根据手机号获取供应商信息
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R.selectSdbMediaStatisticsByAPPPsam(psam);
			if (null == statistics) {
				throw new LakalaException("NO_MEDIA");
			}
			
			//读取所有B端营销分类数据
			List<VirtualCate> vcs = virtualCateMapper_R.findAll4B();
			
			//获取指定供应商的所有自营商品
			List<Integer> vids = virtualCateMapper_R.selectVcsByGoods(Integer.valueOf(statistics.getId().intValue()));
			
			//过滤掉没有商品的营销分类
			Set<VirtualCate> _vcs = new HashSet<VirtualCate>();
			for (VirtualCate vc : vcs) {
				if (vids.contains(vc.getTvirtualcateid())) {
					_vcs.add(vc);
					//递归取出所有上级父分类
					setFvcs(vc, vcs, _vcs);
				}
			}
			
			//查询分类信息
			Iterator<VirtualCate> it = _vcs.iterator();
			while (it.hasNext()) {
				VirtualCate vc = it.next();
				//过滤掉 指定分类“社区商城”、“本周爆款”，不在页面显示
				if (null == vc || vc.getTvirtualcateid().intValue() == 754 || vc.getTvirtualcateid().intValue() == 40673)
					continue;
				VirtualCateVO vo = new VirtualCateVO();
				vo.settVirtualCateId(vc.getTvirtualcateid());
				vo.setVirtualCateDisc(vc.getVirtualcatedisc());
				vo.setFatherVirtualCateId(vc.getFathervirtualcateid());
				
				result._ReturnData.add(vo);
			}
			
			//排序
			Collections.sort(result._ReturnData, new Comparator<VirtualCateVO>() {
				@Override
				public int compare(VirtualCateVO o1, VirtualCateVO o2) {
					return o1.gettVirtualCateId().compareTo(o2.gettVirtualCateId());
				}
			});
			
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		return result;
	}

	/**
	 * @Title: autoAddGoods
	 * @Description: 批发转零售接口：类似一键上传，新增的商品自动上架，关联相应的产品库商品。
     * 首次批发，新增商品，并自动上架，非首次批发，如果商品是生鲜商品，则在已存在的商品上累加库存，非生鲜商品，不处理。
	 * @param in 批发的商品数据：产品库商品ID，sku ID，店主psam，生鲜标记，库存，销售价
	 * @return ObjectOutput<List<Integer>> 操作状态，以及上架成功的新商品ID
	 * @throws
     * @author zhiziwei
	 */
	@Override
	public List<Integer> autoAddGoods(TgoodsSkuInfoInput in, HttpServletRequest req) throws LakalaException{
		Date now = new Date(System.currentTimeMillis());// 当前时间
		Integer newgoodsId = null;
		List<Integer> ids = new ArrayList<Integer>();
		
		logger.debug("店主批发转零售:" + "{Deviceno=" + in.getDeviceno() + ",tGoodInfoPoolId=" + in.gettGoodInfoPoolId()
					+ ",Saleprice=" + in.getSaleprice() + ",GoodsSkuID=" + in.getGoodsSkuID()
					+ ",Isfreshfood=" + in.getIsfreshfood() + ",Skustock=" + in.getSkustock() + "}");
		
		//参数校验
		if (!StringUtil.verdict(in.getDeviceno())
				|| !StringUtil.verdict(in.gettGoodInfoPoolId()) || null == in.getGoodsSkuID()
				|| null == in.getIsfreshfood() || null == in.getSkustock()) {
			throw new LakalaException("参数不合法，以下参数必须：{Deviceno=" + in.getDeviceno()
					+ ",tGoodInfoPoolId=" + in.gettGoodInfoPoolId() + ",Saleprice=" + in.getSaleprice()
					+ ",GoodsSkuID=" + in.getGoodsSkuID() + ",Isfreshfood=" + in.getIsfreshfood()
					+ ",Skustock=" + in.getSkustock() + "}");
		}
		
		try {
			// 使用BeanUtils.copyProperties,bean中如果有BigDecimal属性，要注册默认值
	        BigDecimalConverter bd = new BigDecimalConverter(new BigDecimal(0));
	        ConvertUtils.register(bd, java.math.BigDecimal.class);
	        
	        // 判断该店主是否上架此零售商品，根据psam判断
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R
					.selectSdbMediaStatisticsByAPPPsam(in.getDeviceno());
			if (null == statistics) {
				throw new LakalaException("未能找到相应的终端数据！");
			}
			
			//商户类型校验:此店必须是标准店(460)或者旗舰店(461)，基础店(459)不自动上传商品！
	        String mobile = statistics.getMobile();
	        Integer bizType = tmemberMapperr_R.selectTmemberById(mobile).getBizType();
			
	        if (null == bizType || bizType.intValue() == 459) {
	        	throw new LakalaException("此小店不允许自动上架商品：mobile = " + mobile + ", bizType = " + bizType);
			}
	        
			// 店主网点主键
			Long id = statistics.getId();
			
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("tGoodInfoPoolId", in.gettGoodInfoPoolId());
			parm.put("supplierId", id.intValue());
			List<Tgoodskuinfo> shopperGoods = tgoodskuinfoMapper_R.selectShopperGoods(parm);
			
			// 判断店主自营商品是已存在当前批发的商品的Sku，如果存在，返回已存在的sku属性ID，否则，返回null
			String skuIdStr = isExistedSku(shopperGoods, in.gettGoodInfoPoolId(), in.getGoodsSkuID());
			
			if (StringUtil.verdict(skuIdStr) && in.getIsfreshfood().compareTo(1) == 0) {
				/** 非首次上架的生鲜商品，更新价格，及库存 */
				for (Tgoodskuinfo skuBlob : shopperGoods) {
					// 非当前批发商品sku，不处理
					if (!skuIdStr.equals(skuBlob.getSkuidstr())) 
						continue;
					
					Tgoodskuinfo sku = new Tgoodskuinfo();
					sku.setTgoodinfoid(skuBlob.getTgoodinfoid());
					sku.setTgoodskuinfoid(skuBlob.getTgoodskuinfoid());
					sku.setSaleprice(null == in.getSaleprice() ? skuBlob.getSaleprice() : in.getSaleprice());
					sku.setSkustock(skuBlob.getSkustock().add(in.getSkustock()));
					sku.setSkufrontdisnamestr(skuBlob.getSkufrontdisnamestr());
					sku.setSkuidstr(skuBlob.getSkuidstr());
					sku.setTrealcateid(skuBlob.getTrealcateid());
					// zhiziwei 2015.07.09 自营商品结算价字段记录销售价，供下单时使用
					sku.setBalanceprice(null == in.getSaleprice() ? skuBlob.getSaleprice() : in.getSaleprice());
					
					tgoodskuinfoMapper_W.updateByPrimaryKeySelective(sku);
					
					//调用Mongo接口：修改SKU
					mongo4GoodsService.writeSkusToMongo(sku, null, mobile, req.getRemoteHost(), "mod");
					
					if (skuBlob.getGoodsstatus().intValue() == 208) {
						//库存、销售价更新后，之前已是上架状态的，重新上架
						ids.add(skuBlob.getTgoodinfoid());
						
						//重新上架
						sku.setGoodname(skuBlob.getGoodname());
						sku.setDeviceno(skuBlob.getDeviceno());
						sku.setVirtualcateids(skuBlob.getVirtualcateids());
						invokeGoodsUp(sku, null, mobile, req.getRemoteHost());
					}
				}
			} else if (StringUtil.verdict(skuIdStr) && in.getIsfreshfood().compareTo(0) == 0) {
				/** 非首次上架的非生鲜商品，更新价格 */
				for (Tgoodskuinfo skuBlob : shopperGoods) {
					// 非当前批发商品sku，不处理
					if (!skuIdStr.equals(skuBlob.getSkuidstr())) 
						continue;
					
					Tgoodskuinfo sku = new Tgoodskuinfo();
					sku.setTgoodinfoid(skuBlob.getTgoodinfoid());
					sku.setTgoodskuinfoid(skuBlob.getTgoodskuinfoid());
					sku.setSaleprice(null == in.getSaleprice() ? skuBlob.getSaleprice() : in.getSaleprice());
					sku.setSkustock(skuBlob.getSkustock().add(new BigDecimal(0)));
					sku.setSkufrontdisnamestr(skuBlob.getSkufrontdisnamestr());
					sku.setSkuidstr(skuBlob.getSkuidstr());
					sku.setTrealcateid(skuBlob.getTrealcateid());
					// zhiziwei 2015.07.09 自营商品结算价字段记录销售价，供下单时使用
					sku.setBalanceprice(null == in.getSaleprice() ? skuBlob.getSaleprice() : in.getSaleprice());

					tgoodskuinfoMapper_W.updateByPrimaryKeySelective(sku);
					
					//调用Mongo接口：修改SKU
					mongo4GoodsService.writeSkusToMongo(sku, null, mobile, req.getRemoteHost(), "mod");
					
					if (skuBlob.getGoodsstatus().intValue() == 208) {
						//销售价更新后，之前已是上架状态的，重新上架
						ids.add(skuBlob.getTgoodinfoid());
						
						//重新上架
						sku.setGoodname(skuBlob.getGoodname());
						sku.setDeviceno(skuBlob.getDeviceno());
						sku.setVirtualcateids(skuBlob.getVirtualcateids());
						invokeGoodsUp(sku, null, mobile, req.getRemoteHost());
					}
				}
			} else {
				/** 首次上架，自动新增零售商品，并上架 */
				//价格为空校验
				if (null == in.getSaleprice()) {
					throw new LakalaException("首次上架，价格必输！");
				}
				// 获取商品基本信息
				TgoodsinfopoolWithBLOBs goodsInfoSrc = tgoodsinfopoolMapper_R.selectByPrimaryKey(
								Integer.valueOf(in.gettGoodInfoPoolId()));
				
				Tgoodinfo goods = new Tgoodinfo();
				// 根据实分类，获取其关联的虚分类
				String virCateStr = getVirCatByRealCat(goodsInfoSrc.getTrealcateid());
				if (!StringUtil.verdict(virCateStr)) {
					throw new LakalaException("未能找到相关的营销分类信息！");
				}
				BeanUtils.copyProperties(goods, goodsInfoSrc);
				goods.setVirtualcateids(virCateStr);
				//新增零售商品，置空原商品id
				goods.setTgoodinfoid(null);
				goods.setSupplierid(statistics.getId().intValue());
				goods.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
				goods.setOrgid(statistics.getOrgId());
				goods.setDeviceno(in.getDeviceno());
				goods.setCreateddate(now);
				goods.setLastupdatedate(now);
				goods.setTgoodinfopoolid(Integer.valueOf(in.gettGoodInfoPoolId()));
				goods.setPlatorself(BussConst.GOODS_PLATORSELF_SELF);
				goods.setGoodstatus(197);// 197：审核通过
				goods.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_DOWNSALE);
				//默认字段
				goods.setIsnoreasonreturn(0); // 默认否
				goods.setIscontractmachine(0); // 默认否
				goods.setDistributionflag(362); // 默认 362  一小时送达
				goods.setMeasureunits("件"); // 默认 【件】
				goods.setIsforeigngoods(0); //默认否
				goods.setStoragecondition(1); //默认常温
				goods.setDistributioncondition(1);//默认常温
				goods.setGoodtype(202);//默认 202 正常商品
				goods.setBalanceway(1);//默认 1 按扣点结算
				goods.setTempstoregoodsflag(380);//380 不暂存
				goods.setDeflag(BussConst.GOODS_DELFLAG_VALID); //删除标记，默认有效
				goods.setCarton(1);
				goods.setCartonunit("个");
				
				tgoodinfoMapper_W.insertSelective(goods);
				//取出新的商品ID
				newgoodsId = goods.getTgoodinfoid();
				
				// 为新商品下载图片
				Map<String, Object> imgParm = new HashMap<String, Object>();
				imgParm.put("goodsId", Long.valueOf(in.gettGoodInfoPoolId()));
				imgParm.put("targetType", BussConst.IMAGE_TARGETTYPE_POOL_GOODS);
				
				List<Timages> imgs = timagesMapper_R.queryImgByGoodsId(imgParm);
				String imgstr = "";
				for (int i = 0; null != imgs && i < imgs.size() ; i++) {
					try {
						Timages img = imgs.get(i);
						String[] _temp = img.getUrl().split("/");
						
						img.setTimageid(null);
						img.setTargetid(goods.getTgoodinfoid().longValue());
						img.setTargettype(BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS);
						img.setLastmoddate(now);
						timagesMapper_W.insertSelective(img);
						
						imgstr += (i != imgs.size() - 1 ? _temp[_temp.length - 1] + ";" : _temp[_temp.length - 1]);
						
					} catch (Exception e) {
					}
				}
				
				//更新图片数据（处理后移，更新商品名称，拼上sku属性）
//				goods.setGoodbigpic(imgstr);
//				tgoodinfoMapper_W.updateByPrimaryKeySelective(goods);
				
				//获取商品sku信息
				Map<String, Object> par = new HashMap<String, Object>();
				par.put("tGoodInfoPoolId", in.gettGoodInfoPoolId());
				par.put("tGoodSkuInfoId", in.getGoodsSkuID());
				Tgoodskuinfo sku = new Tgoodskuinfo();
				TgoodskuinfopoolWithBLOBs skuInfoSrc = tgoodskuinfopoolMapper_R.selectSkuByPoolGoodsId(par);
				
				//商品名称：商品名称 + sku属性
				String newName = goods.getGoodname() + 
						(StringUtil.verdict(skuInfoSrc.getSkufrontdisnamestr()) ? "  " + skuInfoSrc.getSkufrontdisnamestr() : "");
				
				//更新图片数据（处理后移，更新商品名称，拼上sku属性）
				goods.setGoodbigpic(imgstr);
				goods.setGoodname(newName);
				tgoodinfoMapper_W.updateByPrimaryKeySelective(goods);
				
				BeanUtils.copyProperties(sku, skuInfoSrc);
				sku.setGoodname(newName);
				sku.setTgoodskuinfoid(null);
				sku.setTgoodinfoid(newgoodsId);
				sku.setSupplierid(statistics.getId().intValue());
				sku.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
				sku.setOrgid(statistics.getOrgId());
				sku.setDeviceno(in.getDeviceno());
				sku.setSaleprice(in.getSaleprice());
				// zhiziwei 2015.07.09 自营商品结算价字段记录销售价，供下单时使用
				sku.setBalanceprice(in.getSaleprice());
				sku.setSkustock(in.getSkustock());
				sku.setSoldSkuStock(new BigDecimal(0));// 初始销量为0
				sku.setVirtualcateids(virCateStr);
				sku.setCreateddate(now);
				sku.setLastupdatedate(now);
				sku.setGoodbigpic(imgstr);
				sku.setTgoodinfopoolid(Integer.valueOf(in.gettGoodInfoPoolId()));
				sku.setPlatorself(BussConst.GOODS_PLATORSELF_SELF);
				sku.setGoodstatus(197);// 197：审核通过
				sku.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_DOWNSALE);
				//默认字段
				sku.setIsnoreasonreturn(0); // 默认否
				sku.setIscontractmachine(0); // 默认否
				sku.setDistributionflag(362); // 默认 362  一小时送达
				sku.setMeasureunits("件"); // 默认 【件】
				sku.setIsforeigngoods(0); //默认否
				sku.setStoragecondition(1); //默认常温
				sku.setDistributioncondition(1);//默认常温
				sku.setGoodtype(202);//默认 202 正常商品
				sku.setBalanceway(1);//默认 1 按扣点结算
				sku.setTempstoregoodsflag(380);//380 不暂存
				sku.setDeflag(BussConst.GOODS_DELFLAG_VALID); //删除标记，默认有效
				sku.setCarton(1);
				sku.setCartonunit("个");
				
				tgoodskuinfoMapper_W.insertSelective(sku);
				
				//获取本次操作日志批次号
				Integer batNo = getBatNo(newgoodsId);
				//记录日志
				writeGoodsLog(sku, mobile, BussConst.GOODS_LOG_ACTIONTYPE_NEW, BussConst.GOODS_LOG_OPREATESRC_PROVIDERADD, batNo);
				//待上架商品ID
				ids.add(newgoodsId);
				
				//调用mongo接口：调用接口新增
		        GoodsCol col = new GoodsCol();
		        col.setSkus(new ArrayList<GoodsSKU>());
				mongo4GoodsService.setGoodsCol(col, goods, sku);
				mongo4GoodsService.writeGoodsToMongo(col, virCateStr, null, mobile, req.getRemoteHost(), "add");
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
		
		return ids;
	}
	
	/**
	 * 调用上架接口（单sku）
	 */
	private void invokeGoodsUp(Tgoodskuinfo skuBlob, String userid, String mobile, String ip)
			throws LakalaException{
		try {
			GoodsCol col = new GoodsCol();
			List<GoodsSKU> skus = new ArrayList<GoodsSKU>();
			// sku信息
			GoodsSKU gsku = new GoodsSKU();
			gsku.setSkuGoodsId(skuBlob.getTgoodskuinfoid().longValue());
			gsku.setSalePrice(skuBlob.getSaleprice().doubleValue());
			
			// 终端信息
			List<com.lakala.module.goods.vo.VirtualCate> vcs =
					new ArrayList<com.lakala.module.goods.vo.VirtualCate>();
			com.lakala.module.goods.vo.VirtualCate vc = new com.lakala.module.goods.vo.VirtualCate();
			HashMap<String, Double> psam = new HashMap<String, Double>(); // 终端号:销售价
			psam.put(skuBlob.getDeviceno(), skuBlob.getSaleprice().doubleValue());
			vc.setPsam(psam);
			vcs.add(vc);
			gsku.setVc(vcs);
			skus.add(gsku);
			
			col.setSkus(skus);
			col.set_id(skuBlob.getTgoodinfoid());
			col.setGoodsName(skuBlob.getGoodname());
			
			//调用上架接口
			mongo4GoodsService.upPublish(col, skuBlob.getVirtualcateids(), userid, mobile, ip);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
	}
	
	/**
	 *  判断店主自营商品是已存在当前批发的商品的Sku，如果存在，返回已存在的sku属性ID，否则，返回null
	 * @param shopperGoods 店主自营商品
	 * @param gettGoodInfoPoolId 产品商品ID
	 * @param goodsSkuID 当前批发商品的skuId
	 */
	private String isExistedSku(List<Tgoodskuinfo> shopperGoods, String gettGoodInfoPoolId, 
			Integer goodsSkuID) throws LakalaException{
		try {
			// 未找到符合条件的店主自营商品
			if (null == shopperGoods || shopperGoods.size() == 0) {
				return null;
			}
			
			// 获取当前批发的sku关联的产品SKU
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("tGoodInfoPoolId", gettGoodInfoPoolId);
			parm.put("tGoodSkuInfoId", goodsSkuID);
			TgoodskuinfopoolWithBLOBs skuInfoSrc1 = tgoodskuinfopoolMapper_R.selectSkuByPoolGoodsId(parm);
			
			for (Tgoodskuinfo shopperGood : shopperGoods) {
				// 比较sku属性，存在相同属性，返回true
				if (StringUtil.verdict(shopperGood.getSkuidstr()) 
						&& shopperGood.getSkuidstr().equals(skuInfoSrc1.getSkuidstr())) {
					return shopperGood.getSkuidstr();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
		return null;
	}


	/**
	 * @Title: autoAddGoods (版本备份：2015年6月2日)
	 * @Description: 批发转零售接口：类似一键上传，新增的商品自动上架，关联相应的产品库商品。
     * 首次批发，新增商品，并自动上架，非首次批发，如果商品是生鲜商品，则在已存在的商品上累加库存，非生鲜商品，不处理。
	 * @param in 批发的商品数据：产品库商品ID，sku ID，店主psam，生鲜标记，库存，销售价
	 * @return ObjectOutput<List<Integer>> 操作状态，以及上架成功的新商品ID
	 * @throws
     * @author zhiziwei
	 */
	public List<Integer> autoAddGoodsV1(TgoodsSkuInfoInput in, HttpServletRequest req) throws LakalaException{
		Date now = new Date(System.currentTimeMillis());// 当前时间
		Integer newgoodsId = null;
		List<Integer> ids = new ArrayList<Integer>();
		
		//参数校验
		if (!StringUtil.verdict(in.getDeviceno())
				|| !StringUtil.verdict(in.gettGoodInfoPoolId()) || null == in.getGoodsSkuID()
				|| null == in.getIsfreshfood() || null == in.getSkustock()) {
			throw new LakalaException("参数不合法，以下参数必须：{Deviceno=" + in.getDeviceno()
					+ ",tGoodInfoPoolId=" + in.gettGoodInfoPoolId()
					+ ",Saleprice=" + in.getSaleprice()
					+ ",GoodsSkuID=" + in.getGoodsSkuID()
					+ ",Isfreshfood=" + in.getIsfreshfood()
					+ ",Skustock=" + in.getSkustock() + "}");
		}
		
		try {
			// 使用BeanUtils.copyProperties,bean中如果有BigDecimal属性，要注册默认值
	        BigDecimalConverter bd = new BigDecimalConverter(new BigDecimal(0));
	        ConvertUtils.register(bd, java.math.BigDecimal.class);
	        
	        // 判断该店主是否上架此零售商品，根据psam判断
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R
					.selectSdbMediaStatisticsByAPPPsam(in.getDeviceno());
			if (null == statistics) {
				throw new LakalaException("未能找到相应的终端数据！");
			}
			
			//商户类型校验:此店必须是标准店(460)或者旗舰店(461)，基础店(459)不自动上传商品！
	        String mobile = statistics.getMobile();
	        Integer bizType = tmemberMapperr_R.selectTmemberById(mobile).getBizType();
			
	        if (null == bizType || bizType.intValue() == 459) {
	        	throw new LakalaException("此小店不允许自动上架商品：mobile = " + mobile + ", bizType = " + bizType);
			}
	        
			// 店主网点主键
			Long id = statistics.getId();
			
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("tGoodInfoPoolId", in.gettGoodInfoPoolId());
			parm.put("supplierId", id.intValue());
			List<Tgoodskuinfo> shopperGoods = tgoodskuinfoMapper_R.selectShopperGoods(parm);
			
			if (null != shopperGoods && shopperGoods.size() > 0 && in.getIsfreshfood().compareTo(1) == 0) {
				/** 非首次上架的生鲜商品，更新价格，及库存 */
				for (Tgoodskuinfo skuBlob : shopperGoods) {
					Tgoodskuinfo sku = new Tgoodskuinfo();
					sku.setTgoodskuinfoid(skuBlob.getTgoodskuinfoid());
					sku.setSaleprice(null == in.getSaleprice() ? skuBlob.getSaleprice() : in.getSaleprice());
					sku.setSkustock(skuBlob.getSkustock().add(in.getSkustock()));
					
					tgoodskuinfoMapper_W.updateByPrimaryKeySelective(sku);
					
					if (skuBlob.getGoodsstatus().intValue() == 208) {
						//库存、销售价更新后，之前已是上架状态的，重新上架
						ids.add(skuBlob.getTgoodinfoid());
					}
				}
			} else if (null != shopperGoods && shopperGoods.size() > 0 && in.getIsfreshfood().compareTo(0) == 0) {
				/** 非首次上架的生鲜商品，更新价格 */
				for (Tgoodskuinfo skuBlob : shopperGoods) {
					Tgoodskuinfo sku = new Tgoodskuinfo();
					sku.setTgoodskuinfoid(skuBlob.getTgoodskuinfoid());
					sku.setSaleprice(null == in.getSaleprice() ? skuBlob.getSaleprice() : in.getSaleprice());
					
					tgoodskuinfoMapper_W.updateByPrimaryKeySelective(sku);
					
					if (skuBlob.getGoodsstatus().intValue() == 208) {
						//销售价更新后，之前已是上架状态的，重新上架
						ids.add(skuBlob.getTgoodinfoid());
					}
				}
			} else {
				/** 首次上架，自动新增零售商品，并上架 */
				//价格为空校验
				if (null == in.getSaleprice()) {
					throw new LakalaException("首次上架，价格必输！");
				}
				// 获取商品基本信息
				TgoodsinfopoolWithBLOBs goodsInfoSrc = tgoodsinfopoolMapper_R.selectByPrimaryKey(
								Integer.valueOf(in.gettGoodInfoPoolId()));
				
				Tgoodinfo goods = new Tgoodinfo();
				// 根据实分类，获取其关联的虚分类
				String virCateStr = getVirCatByRealCat(goodsInfoSrc.getTrealcateid());
				if (!StringUtil.verdict(virCateStr)) {
					throw new LakalaException("未能找到相关的营销分类信息！");
				}
				BeanUtils.copyProperties(goods, goodsInfoSrc);
				goods.setVirtualcateids(virCateStr);
				//新增零售商品，置空原商品id
				goods.setTgoodinfoid(null);
				goods.setSupplierid(statistics.getId().intValue());
				goods.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
				goods.setOrgid(statistics.getOrgId());
				goods.setDeviceno(in.getDeviceno());
				goods.setCreateddate(now);
				goods.setLastupdatedate(now);
				goods.setTgoodinfopoolid(Integer.valueOf(in.gettGoodInfoPoolId()));
				goods.setPlatorself(BussConst.GOODS_PLATORSELF_SELF);
				goods.setGoodstatus(197);// 197：审核通过
				goods.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_DOWNSALE);
				//默认字段
				goods.setIsnoreasonreturn(0); // 默认否
				goods.setIscontractmachine(0); // 默认否
				goods.setDistributionflag(362); // 默认 362  一小时送达
				goods.setMeasureunits("件"); // 默认 【件】
				goods.setIsforeigngoods(0); //默认否
				goods.setStoragecondition(1); //默认常温
				goods.setDistributioncondition(1);//默认常温
				goods.setGoodtype(202);//默认 202 正常商品
				goods.setBalanceway(1);//默认 1 按扣点结算
				goods.setTempstoregoodsflag(380);//380 不暂存
				goods.setDeflag(BussConst.GOODS_DELFLAG_VALID); //删除标记，默认有效
				goods.setCarton(1);
				goods.setCartonunit("个");
				
				tgoodinfoMapper_W.insertSelective(goods);
				//取出新的商品ID
				newgoodsId = goods.getTgoodinfoid();
				
				// 为新商品下载图片
				Map<String, Object> imgParm = new HashMap<String, Object>();
				imgParm.put("goodsId", Long.valueOf(in.gettGoodInfoPoolId()));
				imgParm.put("targetType", BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS);
				
				List<Timages> imgs = timagesMapper_R.queryImgByGoodsId(imgParm);
				String imgstr = "";
				for (int i = 0; null != imgs && i < imgs.size() ; i++) {
					Timages img = imgs.get(i);
					
					/** wujx 2015-6-16图片操作升级改造 */
					FileItem item = transfer.transferTo(new URL(img.getUrl()), goods.getTgoodinfoid().toString(), TargetTypeEnum.IMAGE_TARGETTYPE_GOODS);
					
					//String url = ImageUtil.downLoadImgFromSer(img.getUrl(), goods.getTgoodinfoid().toString(), false,
					//		BussConst.IMAGE_TARGETTYPE_SUPPLIER_GOODS, req);
					//String[] _temp = url.split("/");
					
					img.setTimageid(null);
					img.setTargetid(goods.getTgoodinfoid().longValue());
					img.setTargettype(TargetTypeEnum.IMAGE_TARGETTYPE_GOODS.getId());
					img.setLastmoddate(now);
					img.setUrl(item.getUrl());
					//img.setImagename(_temp[_temp.length - 1]);
					img.setImagename(item.getFileName());
					
					timagesMapper_W.insertSelective(img);
					//imgstr += (i != imgs.size() - 1 ? _temp[_temp.length - 1] + ";" : _temp[_temp.length - 1]);
					imgstr += (i != imgs.size() - 1 ? item.getFileName() + ";" : item.getFileName());
				}
				
				//更新图片数据（该处理后移，商品名称拼上sku属性）
//				goods.setGoodbigpic(imgstr);
//				tgoodinfoMapper_W.updateByPrimaryKeySelective(goods);
				
				//获取商品sku信息
				Map<String, Object> par = new HashMap<String, Object>();
				par.put("tGoodInfoPoolId", in.gettGoodInfoPoolId());
				par.put("tGoodSkuInfoId", in.getGoodsSkuID());
				Tgoodskuinfo sku = new Tgoodskuinfo();
				TgoodskuinfopoolWithBLOBs skuInfoSrc = tgoodskuinfopoolMapper_R.selectSkuByPoolGoodsId(par);
				
				//更新图片数据（该处理后移，商品名称拼上sku属性）
				goods.setGoodbigpic(imgstr);
				goods.setGoodname(goods.getGoodname() + 
						(StringUtil.verdict(skuInfoSrc.getSkufrontdisnamestr()) ? "  " + skuInfoSrc.getSkufrontdisnamestr() : ""));
				tgoodinfoMapper_W.updateByPrimaryKeySelective(goods);
				
				BeanUtils.copyProperties(sku, skuInfoSrc);
				sku.setGoodname(goods.getGoodname() + 
						(StringUtil.verdict(skuInfoSrc.getSkufrontdisnamestr()) ? "  " + skuInfoSrc.getSkufrontdisnamestr() : ""));
				sku.setTgoodskuinfoid(null);
				sku.setTgoodinfoid(newgoodsId);
				sku.setSupplierid(statistics.getId().intValue());
				sku.setSuppliername(StringUtil.verdict(statistics.getNetCustomName()) ? statistics.getNetSname() : statistics.getNetCustomName());
				sku.setOrgid(statistics.getOrgId());
				sku.setDeviceno(in.getDeviceno());
				sku.setSaleprice(in.getSaleprice());
				sku.setSkustock(in.getSkustock());
				sku.setSoldSkuStock(new BigDecimal(0));// 初始销量为0
				sku.setVirtualcateids(virCateStr);
				sku.setCreateddate(now);
				sku.setLastupdatedate(now);
				sku.setGoodbigpic(imgstr);
				sku.setTgoodinfopoolid(Integer.valueOf(in.gettGoodInfoPoolId()));
				sku.setPlatorself(BussConst.GOODS_PLATORSELF_SELF);
				sku.setGoodstatus(197);// 197：审核通过
				sku.setGoodsstatus(BussConst.GOODS_GOODSSTATUS_DOWNSALE);
				//默认字段
				sku.setIsnoreasonreturn(0); // 默认否
				sku.setIscontractmachine(0); // 默认否
				sku.setDistributionflag(362); // 默认 362  一小时送达
				sku.setMeasureunits("件"); // 默认 【件】
				sku.setIsforeigngoods(0); //默认否
				sku.setStoragecondition(1); //默认常温
				sku.setDistributioncondition(1);//默认常温
				sku.setGoodtype(202);//默认 202 正常商品
				sku.setBalanceway(1);//默认 1 按扣点结算
				sku.setTempstoregoodsflag(380);//380 不暂存
				sku.setDeflag(BussConst.GOODS_DELFLAG_VALID); //删除标记，默认有效
				sku.setCarton(1);
				sku.setCartonunit("个");
				
				tgoodskuinfoMapper_W.insertSelective(sku);
				//获取本次操作日志批次号
				Integer batNo = getBatNo(newgoodsId);
				//记录日志
				writeGoodsLog(sku, statistics.getMobile(), BussConst.GOODS_LOG_ACTIONTYPE_NEW, 
						BussConst.GOODS_LOG_OPREATESRC_PROVIDERADD, batNo);
				//待上架商品ID
				ids.add(newgoodsId);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
		
		return ids;
	}
	
	/**
	 * @Title: isExistLSGoods
	 * @Description: 店主是否有相同的零售商品上架
	 * @param psam 店主终端号
	 * @param tGoodInfoPoolId 产品库商品ID
	 * @return boolean true：存在零售商品;false：不存在零售商品
	 * @throws
     * @author zhiziwei
	 */
	@Override
	public List<Tgoodskuinfo> isExistLSGoods(String psam, String tGoodInfoPoolId) throws LakalaException{
		List<Tgoodskuinfo> shopperGoods = null;
		try {
			// 判断该店主是否上架此零售商品，根据psam判断
			SDBMediaStatistics statistics = sdbMediaStatisticsMapper_R
					.selectSdbMediaStatisticsByAPPPsam(psam);
			if (null == statistics) {
				throw new LakalaException("未能找到相应的终端数据！");
			}
			// 店主网点主键
			Long id = statistics.getId();
			
			Map<String, Object> parm = new HashMap<String, Object>();
			parm.put("tGoodInfoPoolId", tGoodInfoPoolId);
			parm.put("supplierId", id.intValue());
			shopperGoods = tgoodskuinfoMapper_R.selectShopperGoods(parm);
			
		} catch (Exception e) {
			throw new LakalaException(e.fillInStackTrace());
		}
		return shopperGoods;
	}
	
	/**
	 * @Title: autoOnsale
	 * @Description: 自动上架
	 * @param ids 待上架的商品ID
	 * @return 上架成功的商品ID
	 * @throws
	 * @author zhiziwei
	 */
	@Override
	public List<Integer> autoOnsale(List<Integer> ids) throws LakalaException{
		try {
			// 自动上架商品
			if (null != ids && ids.size() > 0) {
				// 零售商品新增成功直接上架
				GoodsPublishInput gpi = new GoodsPublishInput();
				gpi.setGoodsIdList(ids);
				gpi.setOpt(BussConst.GOODS_GOODSSTATUS_ONSALE);
				// 调用上架接口
				goodsPublishService.updateGoods(gpi);
			}
		} catch (Exception e) {
			throw new LakalaException(e.fillInStackTrace());
		}
		return ids;
	}

	/**
     * @Description 获取所有APP2C下的虚分类关联的结算分类
     * @author zhiziwei
     */
	@Override
	public ObjectOutput<List<RealCateVO>> queryRealCat() throws LakalaException{
		logger.info("获取所有的结算分类....");
		
		ObjectOutput<List<RealCateVO>> result = new ObjectOutput<List<RealCateVO>>();
		result._ReturnData = new ArrayList<RealCateVO>();
		
		try {
			//读取APP2C下虚分类关联的实分类
			List<Realcate> rcats = realcateMapper_R.selectByVirtualCates();
			System.err.println(rcats.size());
			for (Realcate rcat : rcats) {
				RealCateVO vo = new RealCateVO();
				
				//分类描述，记录在末级分类上
				vo.settRealCateId(rcat.getTrealcateid());
				vo.setFatherRealCateId(rcat.getFatherrealcateid());
				vo.setRealCateDisc(rcat.getRealcatedisc());
				result._ReturnData.add(vo);
			}
			System.err.println(result._ReturnData.size());
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			throw new LakalaException(e);
		}
		
		return result;
	}
	
	/**
	 * 将sku属性id串，转换成中文描述，数据格式： 属性1:属性值1,属性2:属性值1...
	 */
	public String transeSkuToDisc(String Skufrontdisnamestr, String skuIdStr, String tRealCatId) {
		if (!StringUtil.verdict(skuIdStr) || ("NONE".equals(skuIdStr) && !StringUtil.verdict(Skufrontdisnamestr))) {
			return "";
		}

		String tempStr = "";

		try {
			// 获取指定分类下所有属性信息
			List<Tpropertykey> keys = tpropertykeyMapper_R.getGoodProperty(Integer.valueOf(tRealCatId));

			Map<String, String> m_keys = new HashMap<String, String>();
			List<Integer> keyIds = new ArrayList<Integer>();
			for (Tpropertykey key : keys) {
				m_keys.put(key.getTpropertykeyid().toString(), key.getPropertyname());
				keyIds.add(key.getTpropertykeyid());
			}

			// 获取指定属性的属性值
			List<Tpropertyvalue> values = tpropertyvalueMapper_R.queryByPropertyIds(keyIds);

			Map<String, String> m_values = new HashMap<String, String>();
			for (Tpropertyvalue value : values) {
				m_values.put(value.getTpropertyvalueid().toString(), value.getPropertyvalue());
			}

			// 整理属性字符串
			String[] _skuIdStr = skuIdStr.split(",");
			for (int i = 0; i < _skuIdStr.length; i++) {
				String[] s = _skuIdStr[i].split(":");
				if (m_keys.containsKey(s[0]) && m_keys.containsKey(s[1])) {
					if (i == (_skuIdStr.length - 1)) {
						tempStr += (m_keys.get(s[0]) + "：" + m_values.get(s[1]));
					} else {
						tempStr += (m_keys.get(s[0]) + "：" + m_values.get(s[1]) + ", ");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 老EC导入数据，会存在skuIdStr=NONE，Skufrontdisnamestr有值的情况，此处的判断，解决这个数据问题
		return StringUtil.verdict(tempStr) ? tempStr : StringUtil.verdict(Skufrontdisnamestr)? Skufrontdisnamestr: "";
	}

	/**
     * @Description 我的小店，推荐销量前50位的批发进货商品：最多推荐50个，当前小店已上架的商品，不推荐
     * @author zhiziwei
     */
	@Override
	public ObjectOutput<List<RecommendGoodsOutput>> recommendGoods(String psam) throws LakalaException {
		logger.info("我的小店，推荐销量前50位的批发进货商品...");
		
		ObjectOutput<List<RecommendGoodsOutput>> result = new ObjectOutput<List<RecommendGoodsOutput>>();
		result.set_ReturnData(new ArrayList<RecommendGoodsOutput>());
		
		try {
			//终端所属省市区
			SDBMediaStatistics media = sdbMediaStatisticsMapper_R.selectByPsam(psam);
			if (null == media) {
				throw new LakalaException("未找到终端！");
			}
			if (!StringUtil.verdict(media.getCityAreaNo())
					|| !StringUtil.verdict(media.getCityNo()) || !StringUtil.verdict(media.getProvNo())) {
				throw new LakalaException("终端省市区未维护！");
			}
			
			SideShopBListInPut input = new SideShopBListInPut();
			
			//频道-批发进货
			input.setChannelid(44l);
			//类型-平台商品
			input.setPlatorself(453);
			//拓展类型：自营（508）,代理公司（509），行业合作（510），加盟（511），个人代理（512）
			input.setExpandType("508,509,510,511,512");
			//地区信息
			input.setProvince(media.getProvNo());
			input.setCity(media.getCityNo());
			input.setRegion(media.getCityAreaNo());
			input.setAllArea(true);
			
			//获取店主所属区下的片区信息
			if (StringUtil.verdict(media.getCommunityNo())) {
				input.setSection(media.getCommunityNo());
			} else {
				List<Tregion> ss = tregionMapper_R.selectSsByRsCode(media.getCityAreaNo());
				if (null != ss) {
					String _ss = "";
					for (int i = 0; ss.size() > i; i++) {
						_ss += ss.get(i).getCode() + ",";
					}
					
					input.setSection(StringUtil.verdict(_ss) ? _ss.substring(0, _ss.lastIndexOf(",")) : null);
				}
			}
			
			//调用Mongo接口，获取符合条件的批发进货的商品
			List<SideShopBDetailOutPut> goods = mongo4GoodsService.queryPFJH(input);
			
			/** 
			 * 推荐的商品返回的是批发进货的商品关联的产品库商品信息：
			 * 1、批发进货的商品应该满足批发转零售的条件；
			 * 2、已在本小店上架的商品不推荐；
			 * 3、按批发进货的商品的销量的倒序排序，最多返回50个；
			 */
			//1、过滤掉不满足批发转零售的条件的商品
			List<SideShopBDetailOutPut> enablePFZLS = new ArrayList<SideShopBDetailOutPut>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("goodslist", goods);
			List<Tgoodinfo> _enablePFZLS = tgoodinfoMapper_R.queryEnablePFZLS(map);
			for (Tgoodinfo ti : _enablePFZLS) {
				for (SideShopBDetailOutPut sdo : goods) {
					if (ti.getTgoodinfoid().intValue() == sdo.get_id().intValue()) {
						sdo.settGoodInfoPoolId(ti.getTgoodinfopoolid());
						enablePFZLS.add(sdo);
					}
				}
			}
			
			//2、过滤掉已在本小店上架商品，并取出商品关联的产品库商品
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("supplierId", media.getId());
			map1.put("goodslist", enablePFZLS);
			List<TgoodskuinfopoolWithBLOBs> rGoods = tgoodskuinfopoolMapper_R.getRecommendGoods(map1);
			
			//3、排序，倒序
			Collections.sort(enablePFZLS, new Comparator<SideShopBDetailOutPut>() {
				@Override
				public int compare(SideShopBDetailOutPut o1,SideShopBDetailOutPut o2) {
					//计算销量
					BigDecimal o1Stock = new BigDecimal(0);
					List<ListGoodsSKU> o1Sku = o1.getSkus();
					for (ListGoodsSKU os1 : o1Sku) {
						o1Stock = o1Stock.add(new BigDecimal(os1.getSoldSkuStock()));
					}
					BigDecimal o2Stock = new BigDecimal(0);
					List<ListGoodsSKU> o2Sku = o2.getSkus();
					for (ListGoodsSKU os2 : o2Sku) {
						o2Stock = o2Stock.add(new BigDecimal(os2.getSoldSkuStock()));
					}
					return o2Stock.compareTo(o1Stock);
				}
			});
			//整理返回数据
			for (SideShopBDetailOutPut sdo : enablePFZLS) {
				//当前版本，过滤掉多SKU的商品，不在推荐之列
				if (sdo.getSkus().size() > 1) {
					continue;
				}
				
				//获取店主毛利率
				Realcate rc = realcateMapper_R.selectByPrimaryKey(sdo.getRc().getForId());
				//异常数据处理：商品上绑定的分类，在数据库中查不到。
				if(null == rc){
					continue;
				}
				
				//计算零售价
				BigDecimal retailPrice = new BigDecimal(sdo.getSkus().get(0)
						.getSalePrice()).divide(new BigDecimal(sdo.getCarton()), 2,BigDecimal.ROUND_HALF_UP);
				retailPrice = retailPrice.add(retailPrice.multiply(
						NumberUtils.IsNullToZero(rc.getGrossProfitRatio())).divide(BigDecimal.valueOf(100)));
				
				for (TgoodskuinfopoolWithBLOBs blob : rGoods) {
					if (sdo.gettGoodInfoPoolId().intValue() == blob.getTgoodinfopoolid().intValue()) {
						RecommendGoodsOutput rgo = new RecommendGoodsOutput();
						rgo.settGoodInfoPoolId(blob.getTgoodinfopoolid());
						rgo.setGoodName(blob.getGoodname());
						rgo.setIsfreshfood(sdo.getIsfreshfood()); // 生鲜标记取自批发进货的商品
						rgo.setGoodBigPic(blob.getGoodbigpic());
						rgo.settGoodInfoId(sdo.get_id().intValue());
						rgo.setSalePrice(blob.getSaleprice().setScale(2, BigDecimal.ROUND_HALF_UP).toString()); // 统一销售价
						rgo.setRetailPrice(retailPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
						
						result._ReturnData.add(rgo);
					}
				}
			}
			result.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
			result.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
		
		return result;
	}

	/**
	 * @Description 新增推荐商品
	 * @author zhiziwei
	 */
	@Override
	public List<Integer> addBatchRecommendGoods(GoodsListInputForm input, HttpServletRequest req)
			throws LakalaException {
		logger.info("新增推荐商品...");
		
		List<Integer> ids = new ArrayList<Integer>();
		
		if (null == input || null == input.getRgoods() || input.getRgoods().size() == 0) {
			throw new LakalaException("无可新增的商品！");
		}
		
		try {
			for (Map<String, String> rgi : input.getRgoods()) {
				String tGoodInfoPoolId =  rgi.get("tGoodInfoPoolId");
				String isfreshfood =  rgi.get("isfreshfood");
				String tGoodInfoId =  rgi.get("tGoodInfoId");
				String salePrice =  rgi.get("salePrice");
				String stock =  rgi.get("stock");
				
				//校验参数
				if (!StringUtil.verdict(input.getDeviceno()) 
						|| !(StringUtil.verdict(tGoodInfoPoolId) && StringUtil.isNumber(tGoodInfoPoolId))
						|| !(StringUtil.verdict(isfreshfood) && StringUtil.isNumber(isfreshfood))
						|| !(StringUtil.verdict(tGoodInfoId) && StringUtil.isNumber(tGoodInfoId))
						|| !(StringUtil.verdict(salePrice) && StringUtil.isNumber(salePrice))
						|| !(StringUtil.verdict(stock) && StringUtil.isNumber(stock))) {
					throw new LakalaException("参数不合法：" + rgi);
				}
				
				TgoodsSkuInfoInput tii = new TgoodsSkuInfoInput();
				tii.setDeviceno(input.getDeviceno());
				tii.settGoodInfoPoolId(tGoodInfoPoolId);
				tii.setIsfreshfood(Integer.valueOf(isfreshfood));
				tii.setSkustock(Integer.parseInt(isfreshfood) == 1 ? new BigDecimal(stock) : BussConst.DEFAULT_STOCK);
				tii.setSaleprice(new BigDecimal(salePrice));
				
				List<Tgoodskuinfo> skus = tgoodskuinfoMapper_R.selectSKUByGoodsId(Integer.valueOf(tGoodInfoId));
				for (Tgoodskuinfo sku : skus) {
					tii.setGoodsSkuID(sku.getTgoodskuinfoid());
					//调用批发转零售接口
					List<Integer> _ids = autoAddGoods(tii, req);
					ids.addAll(_ids);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new LakalaException(e.fillInStackTrace());
		}
		return ids;
	}

	@Override
	public String updateShopname(int id, String name) throws LakalaException{
		// TODO Auto-generated method stub
		if(id <=0 || name == null || name.equalsIgnoreCase("")){
			return "error";
		}
		
		try{
			
			Map map = new HashMap();
			map.put("id", id);
			map.put("name", name);
			
			this.tgoodinfoMapper_W.updateSupplierNameBySupplierId(map);
			this.tgoodskuinfoMapper_W.updateSupplierNameBySupplierId(map);
			this.kdbGoodsMapper_W.updateSupplierNameBySupplierId(map);
			this.kdbSKUMapper_W.updateSupplierNameBySupplierId(map);
			
			return "success";
		}catch(Exception e){
			e.printStackTrace();
			return "error";
		}
		
	}
	
	public static void main(String[] args) throws IOException, LakalaException {
		
		System.out.println("--------------------");
		/*RedisServiceImpl rt = context.getBean(RedisServiceImpl.class);
		String value = rt.getRedisValueByKey("t");
		System.out.println("value:"+value);*/
	}

	
}
