package com.lakala.mapper.r.user;


import java.util.List;

import com.lakala.module.user.vo.TstoreApprove;

public interface TShopUpdateMapper {
    
  /**
   * 新增小店更新信息
   * @param info
   */
  void shopUpdateInfo(TstoreApprove info);
  
  
  /**
   * 根据手机号查询网点信息
   * @param mobile
   * @return
   */
  List<TstoreApprove> selectNetInfo(String mobile);
  
  /**
   * 根据手机号查询小店是否存在
   * @param mobile
   * @return
   */
  int checkShopExists(String mobile);
  
  /**
   * 根据手机号查询小店是否存在
   * @param mobile
   * @return
   */
  int checkShopExistsInTmemberb(String mobile);
  
  
  /**
   * 根据手机号查询小店类型，是否是标准店
   * @param mobile
   * @return
   */
  int checkShopType(String mobile);
  
  /**
   * 根据手机号查询小店信息
   * @param mobile
   * @return
   */
  TstoreApprove getShopInfo(String mobile);
    
}