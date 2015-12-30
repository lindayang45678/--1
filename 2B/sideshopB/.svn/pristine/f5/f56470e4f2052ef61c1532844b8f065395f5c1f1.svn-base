package com.lakala.mapper.r.user;

import java.util.List;
import java.util.Map;

import com.lakala.exception.LakalaException;
import com.lakala.model.user.Tmedia;
import com.lakala.model.user.Tmember;
import com.lakala.module.user.vo.Tagent;
import com.lakala.module.user.vo.Tcustomermanager;

public interface TmemberMapper {

    Tmember selectTmemberById(String membername);
    
    List<Tmedia> selectSdbMediaByCriteria(String mobile);
    
    Tcustomermanager selectTcustomermanager(Map<String, String> map);
    Tcustomermanager selectTcustomermanagerByCode(String registerCode);
    Tagent selectTagent(String registerCode);
    
    String selectRegisterCode(Map<String, String> map);
    String selectRegisterCode2(Map<String, String> map);
    String selectRegisterCode3(Map<String, String> map);

    /**
     * 用户注册验证业务,注册app，通过组织直接找客户经理，并且是负责人
     * 2015-06-23 yangjunguo
     * @param map
     * @return
     */
    String selectRegisterCode4(Map<String, String> map);
    /**
     * 用户注册验证业务,注册app，通过组织直接找客户经理，并且不是负责人
     * 2015-06-23 yangjunguo
     * @param map
     * @return
     */
    String selectRegisterCode5(Map<String, String> map);

    List<Tmedia> querySdbMediaByCriteria(String mobile);
    
    int checkRegisterCodeType(String registerCode);
    
	/**
	 * 获取网点渠道的类型
	 * @param agentNo
	 * @return
	 */
	public Integer seleteNetPointAgentType(String agentId);
}