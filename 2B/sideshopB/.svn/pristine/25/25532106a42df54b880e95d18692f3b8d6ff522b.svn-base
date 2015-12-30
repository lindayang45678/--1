package com.lakala.module.goods.cache.ehcache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.lakala.base.model.Realcate;
import com.lakala.base.model.VirtualCate;
import com.lakala.exception.LakalaException;
import com.lakala.module.goods.cache.CategoryExt;
@Component
public class CategoryExtEhcache implements CategoryExt{
	
	@Autowired
    private com.lakala.mapper.r.virtualcat.VirtualCateMapper virtualCateMapper_R;
	
	@Autowired
    private com.lakala.mapper.r.realcat.RealcateMapper  realcateMapper_R;

	/**
	 * 根据虚分类获取所有子分类对应的实分类，用于模板商品列表，点击分类时，查询商品
	 * @param virtualcatid
	 * @return
	 * @throws LakalaException
	 */
	@Override
	@CategoryCache
	public List<Realcate> getRealCats(String virtualcatid)
			throws LakalaException {
		//根据页面选中分类，获取下级子类，包含当前选种分类 zhiziwei
		List<VirtualCate> vcs = new ArrayList<VirtualCate>();
		List<VirtualCate> vp = new ArrayList<VirtualCate>();
		VirtualCate vc = new VirtualCate();
		vc.setTvirtualcateid(Integer.parseInt(virtualcatid));
		vp.add(vc);
		vcs.add(vc);
		getSubVitureCate(vcs, vp);
		
		//获取以上分类关联的结算分类
		List<Realcate> realcatelist = realcateMapper_R.selectHasGoodsRcByVirtualCates(vcs);
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
	
	/**
	 * 获取有产品的所有的B端营销分类，用于店主APP，模板商品列表，商品搜索
	 * @param virtualcatid
	 * @return
	 * @throws LakalaException
	 */
	@Override
	@Cacheable(value="allcategoryCache")
	public List<Realcate> getAllRealCats() throws LakalaException {
		//取出所有APP2B可用虚分类，并获取关联的实分类
		List<VirtualCate> vc = virtualCateMapper_R.findAll4B();
		//取出实分类
		List<Realcate> rcats = realcateMapper_R.selectHasGoodsRcByVirtualCates(vc);
		return rcats;
	}
}
