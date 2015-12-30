package com.lakala.mapper.r.user;

import com.lakala.model.user.Tverificationcode;


/**
 * 验证码
 * @author zjj
 * @date 2015年3月2日
 */
public interface TverificationcodeMapper {

    Tverificationcode selectByExample(Tverificationcode code);

}