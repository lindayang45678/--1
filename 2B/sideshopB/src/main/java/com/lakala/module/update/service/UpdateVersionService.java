package com.lakala.module.update.service;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.update.vo.VersionInput;
import com.lakala.module.update.vo.VersionOutput;

public interface UpdateVersionService {
	
	public ObjectOutput checkUpdateVersion(VersionInput input) throws LakalaException;
	public ObjectOutput insertUpdateVersion(VersionInput input) throws LakalaException;
	public ObjectOutput updateBySelective(VersionInput input) throws LakalaException;	
	public ObjectOutput deleteBySelective(VersionInput input) throws LakalaException;
}
