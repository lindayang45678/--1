package com.lakala.mapper.r.virtualcat;

import com.lakala.base.model.VirtualCate;
import com.lakala.model.virtualcate.VirtualCateMongo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface VirtualCateMapper {

    VirtualCate selectByPrimaryKey(Integer tvirtualcateid);

    VirtualCate selectByPrimaryKeyFor2C(Integer tvirtualcateid);

    List<VirtualCate> selectByParentId(Map<String, Object> map);

    /**
     * 获取所有2C使用的营销分类
     */
    List<VirtualCate> findAll4C();

    /**
     * 获取所有2B使用的营销分类
     */
    List<VirtualCate> findAll4B();

    /**
     * 根据父ID查询所有子分类
     */
    List<VirtualCate> findAllChildByParentId(Map<String, Object> params);

    /**
     * 通过实分类获取虚分类 zhiziwei
     */
    List<VirtualCate> findVirCatByRealCat(Integer rid);

    /**
     * Find by ids.
     *
     * @param ids the ids
     * @return the list
     */
    List<VirtualCateMongo> findByIds(@Param("ids") List<String> ids, @Param("channelid") String channelid);

    /**
     * 查询有产品库商品的2B下的营销分类 zhiziwei
     */
    List<Integer> selectVcsForHasGoodsPool(Map<String, Object> parm);

    /**
     * 查询指定店主的自营商品关联的2B下的营销分类 zhiziwei
     */
    List<Integer> selectVcsByGoods(Integer sid);

    /**
     * 查询关联了结算分类的2B下的营销分类 zhiziwei
     */
    List<Integer> selectVcsByRcs(List<VirtualCate> vcats);

    /**
     * 根据父类营销分类ID，查询有商品的子类 zhiziwei
     */
    List<VirtualCate> selectHasGoodsSubVcsd(List<VirtualCate> vcs);

}
