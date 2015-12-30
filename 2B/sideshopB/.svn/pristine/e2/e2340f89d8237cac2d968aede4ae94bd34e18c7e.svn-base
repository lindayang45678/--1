package com.lakala.mapper.r.realcat;

import java.util.List;

import com.lakala.base.model.Realcate;
import com.lakala.base.model.VirtualCate;

public interface RealcateMapper {
    Realcate selectByPrimaryKey(Integer trealcateid);

    List<Realcate> selectAll();
    
    List<Realcate> selectRcByVirtualCate(Integer tVirtualCateId);
    
    List<Realcate> selectRcByVirtualCates(List<VirtualCate> rcats);
    
    /**
     * 根据虚分类获取对应的实分类（多虚分类，只取末级）,且过滤掉没有关联C端虚分类的分类  zhiziwei
     */
    List<Realcate> selectHasGoodsRcByVirtualCates(List<VirtualCate> rcats);

    List<Realcate> selectByVirtualCates();
    
    List<Realcate> selectByFatherCateId(Integer fatherRealCateId);
    
    List<Realcate> selectByFatherCateIds(List<Realcate> rcats);

    /**
     * 根据指定营销分类关联的结算分类 zhiziwei
     */
    List<Integer> selectRidsByVid(Integer vid);
    
    List<Realcate> selectByVirtualCateByRealCate(Integer tVirtualCateId);
}
