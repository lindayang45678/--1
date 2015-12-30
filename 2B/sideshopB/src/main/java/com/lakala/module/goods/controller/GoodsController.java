package com.lakala.module.goods.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lakala.exception.LakalaException;
import com.lakala.model.ProductDetailedInformation;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.goods.service.GoodsPublishService;
import com.lakala.module.goods.service.GoodsService;
import com.lakala.module.goods.vo.CategoryInput;
import com.lakala.module.goods.vo.GoodsInput;
import com.lakala.module.goods.vo.GoodsListInput;
import com.lakala.module.goods.vo.GoodsListInputForm;
import com.lakala.module.goods.vo.GoodsPublishInput;
import com.lakala.module.goods.vo.MarketableInput;
import com.lakala.module.goods.vo.PropertyKeyVO;
import com.lakala.module.goods.vo.RealCateVO;
import com.lakala.module.goods.vo.RecommendGoodsOutput;
import com.lakala.module.goods.vo.TgoodsSkuInfoInput;
import com.lakala.module.goods.vo.VirtualCateInput;
import com.lakala.module.goods.vo.VirtualCateVO;
import com.lakala.module.wholesale.model.InputModel;
import com.lakala.module.wholesale.service.WholesaleService;
import com.lakala.util.Pagination;
import com.lakala.util.ReturnMsg;
import com.lakala.util.StringUtil;

/**
 * 只按照两种方式提供接口 1.返回JSON格式 1.1 以RETS风格请求，一般用于GET请求； 1.2 使用路径中定义参数风格获取数据； 1.3
 * 若为POST请求，则使用@RequestParam获取数据； 1.4 返回的数据都用DATA接收，并提供处理成功或返回标示；
 * <p/>
 * 2.返回对象 2.1 此种适用于直接使用JSP页面输出； 2.2 按照普通的@RequestParam获取参数； 2.3
 * 以modle返回数据，返回的字符串为JSP文件路径；
 * <p/>
 * liuguojie
 */

@Controller
@RequestMapping("/goods")
public class GoodsController {
    Logger logger = Logger.getLogger(GoodsController.class);
    @Autowired
    private GoodsService service;
    @Autowired
    private WholesaleService wholesaleService;
	@Autowired
	private GoodsPublishService goodsPublishService;

    /**
     * @param
     * @param goodsid
     * @param phone
     * @return map
     */
    @ResponseBody
    @RequestMapping(value = "/maketable")
    public ObjectOutput maketAbleGoods(MarketableInput input) {
        ObjectOutput info = null;
        try {
            info = service.maketAbleGoods(input);
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }
        return info;

    }

    @ResponseBody
    @RequestMapping(value = "/getcatinfo")
    public ObjectOutput getCatInfo(CategoryInput input) {
        ObjectOutput info = null;
        try {
            info = service.getCategory(input);

        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }
        return info;
    }

    @ResponseBody
    @RequestMapping(value = "/goodsList")
    public ObjectOutput goodsList(GoodsListInput input) {
        ObjectOutput info = null;
        try {
            info = service.getGoodsList(input);

        } catch (LakalaException e) {
            logger.error(e.getMessage());
            info = new ObjectOutput();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }
        return info;
    }

    @ResponseBody
    @RequestMapping(value = "/upgoodsinfo", method = RequestMethod.POST)
    public ObjectOutput<Integer> upGoodsInfo(TgoodsSkuInfoInput input, HttpServletRequest req) {
        ObjectOutput<Integer> info = null;
        input.setReq(req);
        try {
            info = service.upGoodsInfo(input);
            
            //模板商品，新增成功直接上架，自定义商品后台审核通过后上架
			if (StringUtil.verdict(input.gettGoodInfoPoolId())) {// 模板商品
				GoodsPublishInput gpi = new GoodsPublishInput();
				List<Integer> ids = new ArrayList<Integer>();
				ids.add(info.get_ReturnData());
				gpi.setGoodsIdList(ids);
				gpi.setOpt(208);
				// 调用上架接口
				goodsPublishService.updateGoods(gpi);
			}
        } catch (LakalaException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            info = new ObjectOutput<Integer>();
            info.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            
            if (e.getMessage().contains("NO_SDBMEDIASTATISTICS")) {
            	info.set_ReturnMsg("未能找到相应的终端数据！");
			} else if (e.getMessage().contains("NO_VIRTUALCATE")){
				info.set_ReturnMsg("未能找到相关的营销分类信息！");
			}else if (e.getMessage().contains("NO_APPROVAL")){
				info.set_ReturnMsg(e.getMessage());
			} else {
				info.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
			}
        }
        return info;
    }

    /**
     * @Description 获取2C/2B使用的所有虚分类（即营销分类）只返回有商品的分类
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/queryvirtualcat", method = RequestMethod.POST)
    public ObjectOutput<List<VirtualCateVO>> queryVirtualCat(VirtualCateInput input) {
        ObjectOutput<List<VirtualCateVO>> res = null;

        try {
            res = service.queryVirtualCat4B(input);
        } catch (LakalaException e) {
            logger.error(e.getMessage(), e.fillInStackTrace());

            res = new ObjectOutput<List<VirtualCateVO>>();
            res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }

        return res;
    }
    
    /**
     * @Description 获取2C/2B使用的所有虚分类（即营销分类）只返回关联了结算分类的
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/queryallvirtualcat", method = RequestMethod.POST)
    public ObjectOutput<List<VirtualCateVO>> queryAllVirtualCat4B() {
    	ObjectOutput<List<VirtualCateVO>> res = null;
    	
    	try {
    		res = service.queryAllVirtualCat4B();
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<List<VirtualCateVO>>();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
    	}
    	
    	return res;
    }
    
    /**
     * @Description 根据虚分类获取其关联的实分类
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/queryrealcatbyvirtualcat", method = RequestMethod.POST)
    public ObjectOutput<List<RealCateVO>> queryRealCatByVirtualcat(GoodsInput input) {
    	ObjectOutput<List<RealCateVO>> res = null;
    	
    	try {
    		res = service.queryRealCatByVirtualcat(input);
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<List<RealCateVO>> ();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
    	}
    	
    	return res;
    }

    /**
     * @Description 根据四级实分类获取sku属性数据
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/querygoodproperty", method = RequestMethod.POST)
    public ObjectOutput<List<PropertyKeyVO>> queryGoodProperty(GoodsInput input) {
        ObjectOutput<List<PropertyKeyVO>> res = null;

        try {
            res = service.queryGoodProperty(input);
        } catch (LakalaException e) {
            logger.error(e.getMessage(), e.fillInStackTrace());

            res = new ObjectOutput<List<PropertyKeyVO>>();
            res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }

        return res;
    }
    
	/**
	 * @Description 删除指定商品
	 * @author zhiziwei
	 */
    @ResponseBody
    @RequestMapping(value = "/deletegoods", method = RequestMethod.POST)
    public ObjectOutput<String> deleteGoods(GoodsInput input) {
    	ObjectOutput<String> res = null;
    	
    	try {
    		res = service.deleteGoods(input);
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<String>();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
    	}
    	
    	return res;
    }
    
    /**
     * @Description 获取该用户所属商品的虚拟分类(2B下的营销分类)
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/queryvircatebygoods", method = RequestMethod.POST)
    public ObjectOutput<List<VirtualCateVO>> queryVirCateByGoods(GoodsInput input) {
    	ObjectOutput<List<VirtualCateVO>> res = null;
    	
    	try {
    		res = service.queryVirCate4BByGoods(input);
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<List<VirtualCateVO>>();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		if (e.getMessage().contains("NO_MEDIA")) {
    			res.set_ReturnMsg("未能找到相应的终端数据！");
			}else {
				res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
			}
    	}
    	
    	return res;
    }
    
    /**
     * @Description 获取所有的结算分类
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/queryrealcat", method = RequestMethod.POST)
    public ObjectOutput<List<RealCateVO>> queryRealCat() {
    	ObjectOutput<List<RealCateVO>> res = null;
    	try {
    		res = service.queryRealCat();
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<List<RealCateVO>>();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		if (e.getMessage().contains("NO_MEDIA")) {
    			res.set_ReturnMsg("未能找到相应的终端数据！");
    		}else {
    			res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
    		}
    	}
    	return res;
    }
    
    /**
     * @Description 我的小店，推荐销量前50位的批发进货商品：最多推荐50个，当前小店已上架的商品，不推荐
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/recommendgoods", method = RequestMethod.POST)
    public ObjectOutput<List<RecommendGoodsOutput>> recommendGoods(@RequestParam String psam) {
    	ObjectOutput<List<RecommendGoodsOutput>> res = null;
    	try {
    		res = service.recommendGoods(psam);
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<List<RecommendGoodsOutput>>();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		if (e.getMessage().contains("NO_MEDIA")) {
    			res.set_ReturnMsg("未能找到相应的终端数据！");
    		}else {
    			res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
    		}
    	}
    	return res;
    }
    
    /**
     * @Description 新增推荐商品
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/addbatchrecommendgoods", method = RequestMethod.POST)
    public ObjectOutput<String> addBatchRecommendGoods(GoodsListInputForm input, HttpServletRequest req) {
    	ObjectOutput<String> res = null;
    	try {
    		List<Integer> ids = service.addBatchRecommendGoods(input, req);
    		
			//新增成功后，自动上架
    		if (null != ids && ids.size() > 0) {
    			service.autoOnsale(ids);
			}
    		res = new ObjectOutput<String>();
    		res.set_ReturnCode(ReturnMsg.CODE_SUCCESS);
    		res.set_ReturnMsg(ReturnMsg.MSG_SUCCESS_DEFAULT);
    		res.set_ReturnData("成功新增&nbsp;" + ids.size() + "&nbsp;个商品！");
    	} catch (LakalaException e) {
    		logger.error(e.getMessage(), e.fillInStackTrace());
    		
    		res = new ObjectOutput<String>();
    		res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
    		if (e.getMessage().contains("NO_MEDIA")) {
    			res.set_ReturnMsg("未能找到相应的终端数据！");
    		}else {
    			res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
    		}
    	}
    	return res;
    }
    
    
    /**
     * @Description 获取商品列表接口
     * @author zhiziwei
     */
    @ResponseBody
    @RequestMapping(value = "/querygoodslist")
    public ObjectOutput<List<ProductDetailedInformation>> queryGoodsList(InputModel inputModel) {
        ObjectOutput<List<ProductDetailedInformation>> res = null;

        int pageCount = 0;
        try {
            res = wholesaleService.getGoodsByPsamAndVirtualCatId(inputModel.getPsam(), inputModel.getChannelid(), inputModel.getVirtualcatid());

            if (ReturnMsg.CODE_SUCCESS.equals(res.get_ReturnCode())) {
                List<ProductDetailedInformation> list = res.get_ReturnData();
                if (list != null) {
                    if ((list.size() % inputModel.getPageSize()) == 0) {
                        pageCount = (list.size() / inputModel.getPageSize());
                    } else {
                        pageCount = (list.size() / inputModel.getPageSize()) + 1;
                    }
                    if (inputModel.getPage() <= pageCount) {
                        Pagination pagination = new Pagination(inputModel.getPage(), inputModel.getPageSize(), list);
                        list = pagination.doPagination();
                        res = wholesaleService.getPageProductDetile(list, inputModel.getPsam());
                    } else {
                        list = new ArrayList<ProductDetailedInformation>();
                        res.set_ReturnData(list);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            res = new ObjectOutput<List<ProductDetailedInformation>>();
            res.set_ReturnCode(ReturnMsg.CODE_ERR_DEFAULT);
            res.set_ReturnMsg(ReturnMsg.MSG_ERR_DEFAULT);
        }

        return res;
    }
    
}
