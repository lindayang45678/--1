package com.lakala.mapper.w.user;

import java.util.Map;

public interface TmemberMapper {

	int insertTmemberB(Map<String, Object> map);
	int insertMedia(Map<String, Object> map);
	
    int updateTmemberPwdById(Map<String, Object> map);
    
    /**
     * 如果不同手机号terminalCode重复，则清空其他手机号的terminalCode
     * @param map
     * @return
     */
    int updateTmemberTCodeIfRepeat(Map<String, Object> map);
    
    int updateTmemberTCodeById(Map<String, Object> map);
    int updateTmemberRegister(Map<String, Object> map);
    int updateTmemberUID(Map<String, Object> map);
}