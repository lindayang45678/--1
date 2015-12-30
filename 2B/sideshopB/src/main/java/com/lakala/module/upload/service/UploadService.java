package com.lakala.module.upload.service;

import javax.servlet.http.HttpServletRequest;

import com.lakala.exception.LakalaException;
import com.lakala.module.comm.ObjectOutput;
import com.lakala.module.upload.vo.UploadInfoInput;
import com.lakala.module.upload.vo.UploadInfoOutput;

/**
 * 图片上传接口
 * 
 * @author zhiziwei
 * 
 */
public interface UploadService {

	/**
	 * @Description 上传图片
	 * @author zhiziwei
	 */
	public ObjectOutput<UploadInfoOutput> uploadImg(UploadInfoInput input,
			HttpServletRequest req) throws LakalaException;

}
