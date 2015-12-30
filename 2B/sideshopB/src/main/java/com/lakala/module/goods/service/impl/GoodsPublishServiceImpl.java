package com.lakala.module.goods.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.lakala.base.model.GoodsPublishForKDB;
import com.lakala.base.model.GoodsPublishForKDBCondition;
import com.lakala.base.model.GoodsPublishForKDBOfSKU;
import com.lakala.base.model.GoodsPublishForKDBOfSKUO2O;
import com.lakala.base.model.GoodsPublishForKDBOfSKU_RedisVO;
import com.lakala.base.model.GoodsPublishOfGoodsIdAndSKUIds;
import com.lakala.base.model.GoodsPublishParamForType;
import com.lakala.base.model.GoodsPublishTerminalCondition;
import com.lakala.base.model.SDBMediaStatistics;
import com.lakala.base.model.Tgoodinfo;
import com.lakala.base.model.Tgoodskuinfo;
import com.lakala.base.model.json.SKUAndO2O;
import com.lakala.exception.LakalaException;
import com.lakala.module.comm.Constant;
import com.lakala.module.comm.redis.RedisInterface;
import com.lakala.module.goods.service.GoodsPublishService;
import com.lakala.module.goods.vo.GoodsPublishInput;
import com.lakala.module.order.service.Mongo4GoodsService;

/**
 * 商品上架 下架   
 * @author sunjiabo
 */
@Service
public class GoodsPublishServiceImpl implements GoodsPublishService {
	
	@Resource
	private com.lakala.mapper.r.goods.TgoodsinfoMapper TgoodinfoMapper_R;
	@Resource
	private com.lakala.mapper.w.goods.TgoodsinfoMapper TgoodinfoMapper_W;
	
	@Resource
	private com.lakala.mapper.r.goods.TgoodskuinfoMapper tgoodskuinfoMapper_R;
	@Resource
	private com.lakala.mapper.w.goods.TgoodskuinfoMapper tgoodskuinfoMapper_W;
	
//	@Resource
//	private com.lakala.mapper.r.goods.GoodsPublishForKDBMapper kdbGoodsMapper_R;
	@Resource
	private com.lakala.mapper.w.goods.GoodsPublishForKDBMapper kdbGoodsMapper_W;
	
	@Resource
	private com.lakala.mapper.r.goods.GoodsPublishForKDBOfSKUMapper kdbSKUMapper_R;
	@Resource
	private com.lakala.mapper.w.goods.GoodsPublishForKDBOfSKUMapper kdbSKUMapper_W;
	
	@Resource
	private com.lakala.mapper.r.goods.GoodsPublishForKDBOfSKUO2OMapper o2oMapper_R;
	@Resource
	private com.lakala.mapper.w.goods.GoodsPublishForKDBOfSKUO2OMapper o2oMapper_W;
	
	@Resource
	private com.lakala.mapper.w.goods.GoodsPublishForKDBConditionMapper conditionMapper_W;
	
	@Resource
	private com.lakala.mapper.r.sdbmediastatistics.SdbMediaStatisticsMapper smsMapper_R;
	
//	@Resource
//	private RedisInterface redisInterface;  zhiziwei 2015.06.30 屏蔽REDIS 接口 
	
	@Autowired
	private Mongo4GoodsService mongo4GoodsService;
	
	private static Gson gson=new Gson();
	
	private static Logger logger = (Logger) LoggerFactory.getLogger(GoodsPublishServiceImpl.class);
	
	
	@Override
	public void updateGoods(GoodsPublishInput input) throws LakalaException {
		
		Date now=new Date();
		
		List<Integer> goodsIdList=input.getGoodsIdList();
		
		for(Integer goodsId : goodsIdList){
			Tgoodinfo goods=TgoodinfoMapper_R.selectByPrimaryKey(goodsId);
			List<Tgoodskuinfo> skuList=tgoodskuinfoMapper_R.selectSKUByGoodsId(goodsId);
			
			if(goods!=null&&skuList!=null&&skuList.size()>0){ //确认商品以及sku都存在
				
				if(goods.getPlatorself().intValue()!=Constant.GOODS_SELF){
					logger.error("不允许操作平台商品");
					throw new LakalaException("不允许操作平台商品");
				}
				
				Integer opt=input.getOpt();
				
				if(opt!=null&&opt.intValue()==Constant.GOODS_STATUS_ONSALE){ //上架
					goods.setGoodsstatus(Constant.GOODS_STATUS_ONSALE);
					goods.setOnsaletime(now);
					goods.setLastupdatedate(now);
					
				}else if(opt!=null&&opt.intValue()==Constant.GOODS_STATUS_DWONSALE){  //下架
					goods.setGoodsstatus(Constant.GOODS_STATUS_DWONSALE);
					goods.setDownsaletime(now);
					goods.setLastupdatedate(now);
					
					// 调用Mongo接口：下架
					mongo4GoodsService.downPublish(goodsId, null, input.getMobile(), null);
				}else{
					logger.error("参数错误");
					throw new LakalaException("参数错误");
				}
				
				TgoodinfoMapper_W.updateGoods4PublishByGoodsId(goods);
				tgoodskuinfoMapper_W.updateSKU4PublishByGoodsId(goods);
				
				operateO2OAndContion(goods,skuList,opt,now);
				
			}else{
				logger.error("商品或sku不存在  商品ID："+goodsId);
				throw new LakalaException("商品或sku不存在  商品ID："+goodsId);
			}
		}
	}
	
	
	private void operateO2OAndContion(Tgoodinfo goods, List<Tgoodskuinfo> skuList,
			Integer opt, Date now) throws LakalaException {
		
		if(opt.intValue()==Constant.GOODS_STATUS_ONSALE){  //上架
			
			/* 根据goodsId查询kdb_sku表
			 * 	    如果不存在：表示该商品首次上架，将商品和该商品的所有sku分别写入kdb kdb_sku表
			 *            goodsStatus设置为上架状态（208） platorself为店主自营   发布时间也记录下
			 * 	    如果存在：表示该商品非首次上架，更新商品状态为上架状态  更新时间也记录下（记录覆盖掉发布时间即可）
			 * 
			 * 操作o2o表  此时一个sku对应o2o一条记录应该才是正常
			 *     根据skuID查询o2o表
			 *        如果返回空或集合size为0，表示该商品没有发布过
			 *     		此时要插入一条记录，状态为上架（208），价格字段要使用tgoodskuinfo表的销售价字段！！ 
			 * 	   		MD5要使用terminalConditon+UUID或skuID防止重复！
			 *        如果返回结果，则表示该商品之前发布过，一般是已经下架了
			 *        	此时要更新o2o匹配的该商品的记录状态为上架，价格字段重新从tgoodskuinfo对象取
			 *          将修改结果更新回o2o表
			 *          
			 * 写condition表，状态为上架（ 208），terminalCondition就是o2o的字段值，MD5也是
			 * */
			
			processUpShelf(goods,skuList,now);
			
		}else if(opt.intValue()==Constant.GOODS_STATUS_DWONSALE){  //下架
			
			/* 此时商品应该已经存在于 kdb和kdb_sku表  o2o表也应该有每个sku对应的一个o2o记录
			 * 
			 * 1.根据goodsId更新kdb和kdb_sku表以及o2o表对应的记录状态为下架（209）
			 * 2.写condition  json和md5来自于o2o表   状态为下架（209）
			 * */
			
			processDownShelf(goods,skuList,now);
		}
		
	}
	
	
	/**【商品上架】 
	 * 根据goodsId查询kdb_sku表
	 * 	    如果不存在：表示该商品首次上架，将商品和该商品的所有sku分别写入kdb kdb_sku表
	 *            【注意】：goodsStatus设置为上架状态（208） platorself为店主自营   发布时间也记录下
	 * 	    如果存在：表示该商品非首次上架，更新商品状态为上架状态  更新时间也记录下（记录覆盖掉发布时间即可）
	 * 
	 * 操作o2o表  此时一个sku对应o2o一条记录应该才是正常
	 *     根据skuID查询o2o表
	 *        如果返回空或集合size为0，表示该商品没有发布过
	 *     		此时要插入一条记录，状态为上架（208），价格字段要使用tgoodskuinfo表的销售价字段！！ 
	 * 	   		MD5要使用terminalConditon+UUID或goodsId防止重复！
	 *        如果返回结果，则表示该商品之前发布过，一般是已经下架了
	 *        	此时要更新o2o匹配的该商品的记录状态为上架，价格字段重新从tgoodskuinfo对象取
	 *          将修改结果更新回o2o表
	 *          
	 * 写condition表，状态为上架（ 208），terminalCondition就是o2o的字段值，MD5也是
	 * @throws LakalaException 
	 * */
	private void processUpShelf(Tgoodinfo goods, List<Tgoodskuinfo> skuList,Date now) throws LakalaException{
		int goodsId=goods.getTgoodinfoid();
		//这里的查询目前貌似只查skuId即可
		List<GoodsPublishForKDBOfSKU> kdbSKUList=kdbSKUMapper_R.selectByGoodsId(goodsId);
		
		if(kdbSKUList!=null&&kdbSKUList.size()>0){
			/*根据goodsId查询kdb_sku表,如果存在：表示该商品非首次上架（此时应该是已经下架）
			 *所以，需要更新商品状态为上架状态  更新时间也记录下（记录覆盖掉发布时间即可）
			 *【注意】：还需要将tgoodskuinfo表和tgoodsinfo表数据重新复制到kdb和kdb_sku表
			 *因为重新上架的时候可能会编辑商品信息，此时就需要同步到kdb和kdb_sku表*/
			
			GoodsPublishForKDB kdbGoods=copyPropertiesForKDB(goods,now);
			kdbGoodsMapper_W.updateByPrimaryKeySelective(kdbGoods);
			
			for(Tgoodskuinfo sku : skuList){
				// TODO 可以改造成批量update
				GoodsPublishForKDBOfSKU kdbGoodsSKU=copyPropertiesForKDBSKU(sku,now);
				kdbSKUMapper_W.updateByPrimaryKeySelective(kdbGoodsSKU);
			}
			
		}else{
			/* 根据goodsId查询kdb_sku表  sku没有查到，表示该商品首次上架.此时将商品和该商品的所有sku分别写入kdb kdb_sku表，
			 * goodsStatus设置为上架状态（208） platorself为店主自营   发布时间也记录下*/
			
			//复制tgoodskuinfo一个商品的所有sku到tgoods_publish_kdb_sku表
			for(Tgoodskuinfo sku : skuList){
				// TODO 这里可以改成批量insert
				GoodsPublishForKDBOfSKU kdbGoodsSKU=copyPropertiesForKDBSKU(sku,now);
				kdbSKUMapper_W.insertSelective(kdbGoodsSKU);
			}
			
			//复制tgoodinfo一个商品到tgoods_publish_kdb表
			GoodsPublishForKDB kdbGoods=copyPropertiesForKDB(goods,now);
			kdbGoodsMapper_W.insertSelective(kdbGoods);
		}
		
		processO2OAndCondition(Constant.GOODS_STATUS_ONSALE,goods,skuList,now);
	}
	
	
	/**根据Tgoodinfo对象创建一个新的GoodsPublishForKDB对象
	 * 即从Tgoodinfo对象复制属性值到GoodsPublishForKDB对象的对应属性上
	 * 同时设置目标对象（商品）为上架状态，设置商品标识为店主自营*/
	private GoodsPublishForKDB copyPropertiesForKDB(Tgoodinfo goods,Date now){
		GoodsPublishForKDB kdbGoods=new GoodsPublishForKDB();
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
			BeanUtils.copyProperties(kdbGoods,goods);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		kdbGoods.setGoodsstatus(Constant.GOODS_STATUS_ONSALE);
		kdbGoods.setPlatorself(Constant.GOODS_SELF);
		kdbGoods.setPublishdate(now);
		kdbGoods.setLastupdatedate(now);
		
		return kdbGoods;
	}
	
	/**根据Tgoodskuinfo对象创建一个新的GoodsPublishForKDBOfSKU对象
	 * 即从Tgoodskuinfo对象复制属性值到GoodsPublishForKDBOfSKU对象的对应属性上
	 * 同时设置目标对象（商品sku）为上架状态，设置商品标识为店主自营*/
	private GoodsPublishForKDBOfSKU copyPropertiesForKDBSKU(Tgoodskuinfo sku,Date now){
		GoodsPublishForKDBOfSKU kdbSKU=new GoodsPublishForKDBOfSKU();
		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class);
			BeanUtils.copyProperties(kdbSKU,sku);
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		kdbSKU.setGoodsstatus(Constant.GOODS_STATUS_ONSALE);
		kdbSKU.setPlatorself(Constant.GOODS_SELF);
		kdbSKU.setPublishdate(now);
		kdbSKU.setLastupdatedate(now);
		
		return kdbSKU;
	}
	

	/**【商品下架】
	 * 此时商品应该已经存在于 kdb和kdb_sku表  o2o表也应该有每个sku对应的一个o2o记录
	 * 
	 * 1.根据goodsId更新kdb和kdb_sku表为下架状态
	 * 2.调用processO2OAndCondition 更新o2o表该商品的状态为下架，以及写condition和删除redis的o2o详情
	 * @throws LakalaException 
	 * */
	private void processDownShelf(Tgoodinfo goods, List<Tgoodskuinfo> skuList,Date now) throws LakalaException{
		GoodsPublishForKDB kdbGoods=new GoodsPublishForKDB();
		kdbGoods.setTgoodinfoid(goods.getTgoodinfoid());
		kdbGoods.setGoodsstatus(Constant.GOODS_STATUS_DWONSALE);
		kdbGoods.setLastupdatedate(now);
		kdbGoodsMapper_W.updateByPrimaryKeySelective(kdbGoods);
		
		GoodsPublishForKDBOfSKU sku=new GoodsPublishForKDBOfSKU();
		sku.setTgoodinfoid(goods.getTgoodinfoid());
		sku.setGoodsstatus(Constant.GOODS_STATUS_DWONSALE);
		sku.setLastupdatedate(now);
		
		kdbSKUMapper_W.updateByGoodsIdSelective(sku);
		
		processO2OAndCondition(Constant.GOODS_STATUS_DWONSALE,goods,skuList,now);
	}

	
	/**
	 * 操作O2O表和condition表
	 * @param opt 208（上架）   209（下架）
	 * @param goods 要上架/下架的商品
	 * @param skuList  要上架/下架的商品的所有SKU
	 * @param now 当前时间
	 * @throws LakalaException
	 */
	private void processO2OAndCondition(Integer opt,Tgoodinfo goods, List<Tgoodskuinfo> skuList,Date now) throws LakalaException {
		
		Long o2oCount=o2oMapper_R.getCountByGoodsID(goods.getTgoodinfoid());
		
		if(o2oCount!=null&&o2oCount>0&&opt==Constant.GOODS_STATUS_ONSALE){
			/* 上架操作，并且该商品之前上架过（应该是已经下架了），则执行重新上架
			 * 1.根据商品ID更新o2o表状态为上架，时间字段最好也更新   【注意】：更新o2o时重新获取sku的销售价
			 * 2.将该商品的o2o记录组装为condition表需要的数据，写入condition表
			 * 3.写redis，将每个o2o详情写入redis，key为product_o2o_{o2oID} */
			
			for(Tgoodskuinfo sku : skuList){
				GoodsPublishForKDBOfSKUO2O o2o=new GoodsPublishForKDBOfSKUO2O();
				o2o.setSkuid(sku.getTgoodskuinfoid());
				o2o.setSaleprice(sku.getSaleprice().doubleValue());
				o2o.setSkustatus(Constant.GOODS_STATUS_ONSALE);
				o2o.setPublishdate(now);
				o2o.setRemark("ToB恢复上架");
				
				// TODO 可以改造成批量update
				o2oMapper_W.updateBySKUIDSelective(o2o);
			}
			
			//注意：这里一定要使用o2oMapper_W的方法，不能使用o2oMapper_R  两个数据源事务不相等！
			List<GoodsPublishForKDBOfSKUO2O> o2oList=o2oMapper_W.selectByGoodsID(goods.getTgoodinfoid());
			
			operateCondition(o2oList,now,opt,"ToB上架");
			
			operateRedis(opt,o2oList);
			
		}else if(o2oCount==null||o2oCount<1&&opt==Constant.GOODS_STATUS_ONSALE){
			/* 商品上架，并且o2o表没有该商品的记录，也就是该商品第一次上架！ 执行首次上架
			 * 1.将该商品的相关信息写到o2o表，状态为上架，注意MD5的生成最好在条件json基础上加商品ID
			 * 2.写conditon表，执行对该商品的上架，状态为上架  json来自1写入的json，MD5也是
			 * 3.写redis，将每个o2o详情写入redis key为product_o2o_{o2oId}*/
			
			String conditonJSON=generateConditionJSON(goods);
			String conditionJSON_MD5=md5(conditonJSON+goods.getTgoodinfoid());
			
			for(Tgoodskuinfo sku : skuList){
				GoodsPublishForKDBOfSKUO2O o2o=new GoodsPublishForKDBOfSKUO2O();
				o2o.setGoodsid(goods.getTgoodinfoid());
				o2o.setSkuid(sku.getTgoodskuinfoid());
				o2o.setSort(0);
				o2o.setSkustatus(Constant.GOODS_STATUS_ONSALE);
				o2o.setSaleprice(sku.getSaleprice().doubleValue());
				o2o.setStore(sku.getSkustock().doubleValue());
			//	o2o.setPublishperson(sku.get)  TODO 发布人
				o2o.setPublishdate(now);
				o2o.setRemark("ToB上架");
				
				String term=goods.getDeviceno();
				if(term==null||"".equals(term.trim())){
					throw new LakalaException("终端号不存在");
				}
				
				o2o.setTerminalcondition(conditonJSON);
				o2o.setTermconmd5(conditionJSON_MD5);
				
				//　TODO 这里也可以改造成批量insert
				o2oMapper_W.insertSelective(o2o);
			}
			
			//注意：这里一定要使用o2oMapper_W的方法，不能使用o2oMapper_R  两个数据源事务不相等！
			List<GoodsPublishForKDBOfSKUO2O> o2oList=o2oMapper_W.selectByGoodsID(goods.getTgoodinfoid());
			
			operateCondition(o2oList,now,opt,"ToB上架");
			
			operateRedis(opt,o2oList);
			
		}else if(opt==Constant.GOODS_STATUS_DWONSALE&&o2oCount!=null&&o2oCount>0){
			/* 商品下架 
			 * 1.更新该商品的o2o表记录为下架状态
			 * 2.写condition表，执行对该商品下架
			 * 3.从redis删除该商品的o2o详情*/
			
			GoodsPublishForKDBOfSKUO2O o2o=new GoodsPublishForKDBOfSKUO2O();
			o2o.setGoodsid(goods.getTgoodinfoid());
			o2o.setPublishdate(now);
			o2o.setSkustatus(Constant.GOODS_STATUS_DWONSALE);
			
			o2oMapper_W.updateO2OByGoodsIdSelective(o2o);
			
			//注意：这里一定要使用o2oMapper_W的方法，不能使用o2oMapper_R  两个数据源事务不相等！
			List<GoodsPublishForKDBOfSKUO2O> o2oList=o2oMapper_W.selectByGoodsID(goods.getTgoodinfoid());
			
			operateCondition(o2oList,now,opt,"ToB下架");
			
			operateRedis(opt,o2oList);
			
		}else{
			throw new LakalaException("上架/下架 失败   可能原因：o2o记录不存在！");
		}
	}
	
	
	/**获取o2o表里某个商品的发布条件json以及该（json+goodsId）的MD5
	 * 将该商品的id、skuID、o2oID，以及json和MD5生成一条记录写如condition表
	 * 状态为opt指定的（上架/下架）    此时condition表里published必须为0才能被【发布服务】执行到！*/
	private void operateCondition(List<GoodsPublishForKDBOfSKUO2O> o2oList,Date now,int opt,String remark){
		List<GoodsPublishOfGoodsIdAndSKUIds> idsList=generateIdsCondition(o2oList);
		
		GoodsPublishForKDBOfSKUO2O o2o=o2oList.get(0);
		String conditionJSON=o2o.getTerminalcondition();
		String conditionJSON_MD5=o2o.getTermconmd5();
		
		saveTermCon(idsList,conditionJSON,conditionJSON_MD5,opt,now,remark);
	}
	
	
	/**
	 * 商品上架，将商品的每个o2o详情写入redis　key为product_o2o_{o2oId}
	 * 商品下架，将商品的每个o2o详情从redis删除 
	 * */
	private void operateRedis(int opt,List<GoodsPublishForKDBOfSKUO2O> o2oList){
		
		if(opt==Constant.GOODS_STATUS_ONSALE){
			//上架：根据o2o查询每个o2o的商品详情，将得到的结果写入redis
			
			StringBuffer sb=new StringBuffer();
			for(GoodsPublishForKDBOfSKUO2O o2o : o2oList){
				sb.append(o2o.getId()).append(",");
			}
			String o2oIds=sb.deleteCharAt(sb.length()-1).toString();
			
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("o2oIds",o2oIds);
			params.put("skuStatus",Constant.GOODS_STATUS_ONSALE);
			
			List<GoodsPublishForKDBOfSKU_RedisVO> redisVOList=o2oMapper_W.selectDetailByO2OIds(params);
			Map<String,String> redisEntries=new HashMap<String, String>();
			for(GoodsPublishForKDBOfSKU_RedisVO redisVO : redisVOList){
				redisEntries.put("product_o2o_"+redisVO.getId(),gson.toJson(redisVO));
			}
			
//			redisInterface.batchInsertMap(redisEntries);   zhiziwei 2015.06.30 屏蔽REDIS 接口 
			
		}else if(opt==Constant.GOODS_STATUS_DWONSALE){
			//下架：根据o2o将每个对应的product_o2o_{o2oId}key从redis删除
//			for(GoodsPublishForKDBOfSKUO2O o2o : o2oList){
//				redisInterface.deleteByKey("product_o2o_"+o2o.getId());   zhiziwei 2015.06.30 屏蔽REDIS 接口 
//			}
		}
	}
	
	
	private String generateConditionJSON(Tgoodinfo goods) throws LakalaException{
		GoodsPublishTerminalCondition condition=new GoodsPublishTerminalCondition();
		
		//频道+虚分类 例如：  27-39_294_295  批发进货频道_全国批发-粮油调味-米. 如果有多个是以逗号分割
		String virtualIds=goods.getVirtualcateids();
		if(virtualIds==null||"".equals(virtualIds.trim())){
			throw new LakalaException("频道和虚分类不存在");
		}
		condition.setFrequencyAndChannel(virtualIds);
		
		String PSAM=goods.getDeviceno();
		SDBMediaStatistics sdb=smsMapper_R.getTermFbTypeByPSAM(goods.getDeviceno());
		String termFbtype=sdb.getTermFbtype();
		if(termFbtype==null||"".equals(termFbtype)){
			throw new LakalaException("网点类型未定义  终端号："+PSAM);
		}
		
		GoodsPublishParamForType publishType=new GoodsPublishParamForType();
		publishType.setTerminalIds(PSAM+"_"+termFbtype);
		
		condition.setPublishParmaForType(publishType);
		
		return gson.toJson(condition);
	}
	
	
	/**
	 * 根据o2oList 组装该商品的condition表的goodsIdAndSKUIds字段的数据
	 * 例如： [{"goodsId":4044,"skuIds":[{"skuId":10359,"o2oId":781},{"skuId":10360,"o2oId":782}]}]
	 * */
	private List<GoodsPublishOfGoodsIdAndSKUIds> generateIdsCondition(List<GoodsPublishForKDBOfSKUO2O> o2oList){
		List<GoodsPublishOfGoodsIdAndSKUIds> gsList = new 
				ArrayList<GoodsPublishOfGoodsIdAndSKUIds>();
		
		GoodsPublishOfGoodsIdAndSKUIds gs=new GoodsPublishOfGoodsIdAndSKUIds();
		gs.setGoodsId(o2oList.get(0).getGoodsid());
		
		List<SKUAndO2O> soList=new ArrayList<SKUAndO2O>();
		for(GoodsPublishForKDBOfSKUO2O o2o : o2oList){
			SKUAndO2O so=new SKUAndO2O();
			so.setSkuId(o2o.getSkuid());
			so.setO2oId(o2o.getId());
			soList.add(so);
		}
			
		gs.setSkuIds(soList);
		gsList.add(gs);
		
		return gsList;
	}
	
	
	public static String md5(String str){
		MessageDigest md;
		String md5=null;
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			BigInteger bi=new BigInteger(1,md.digest());
			md5=bi.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return md5;
	}
	

	private void saveTermCon(List<GoodsPublishOfGoodsIdAndSKUIds> goodsIdAndSKUIds,String termCon
			,String termConMD5,int opt,Date now,String remark){
		
		GoodsPublishForKDBCondition condition=new GoodsPublishForKDBCondition();
		condition.setGoodsidandskuids(gson.toJson(goodsIdAndSKUIds));
		condition.setTerminalcondition(termCon);
		condition.setTermconmd5(termConMD5);
		condition.setPublished(0);
//		condition.setPublishperson(user.getUsername());  TODO person待定 用于标识店主
		condition.setCreatedate(now);
		condition.setPublishtiming(null);
		condition.setRemark(remark);
		
		if(opt==Constant.GOODS_STATUS_DWONSALE){
			condition.setGoodsstatus(Constant.GOODS_STATUS_DWONSALE);
		}else if(opt==Constant.GOODS_STATUS_ONSALE){
			condition.setGoodsstatus(Constant.GOODS_STATUS_ONSALE);
		}
		
		conditionMapper_W.insertSelective(condition);
	}
	
	
}
