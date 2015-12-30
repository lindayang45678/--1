package com.lakala.module.upload.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 
 * @ClassName: UploadInfoInput
 * @Description: 上传图片参数数据实体
 * @author zhiziwei
 * @date 2015-3-25 下午3:22:04
 * 
 */
public class UploadInfoInput extends ObjectInput {
	private String isWriteTimage;
	
	private String data;
	
	private String fileName;
	
	private String osType;
	
	private String targetType;

	public String getIsWriteTimage() {
		return isWriteTimage;
	}

	public void setIsWriteTimage(String isWriteTimage) {
		this.isWriteTimage = isWriteTimage;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
}
