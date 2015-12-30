package com.lakala.mapper.w.update;

import com.lakala.module.update.vo.VersionInput;

public interface UpdateVersionMapper {
    public Integer insertUpdateVersion(VersionInput input);
  	public Integer updateBySelective(VersionInput input);
  	public Integer deleteBySelective(VersionInput input);
}
