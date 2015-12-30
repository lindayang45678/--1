package com.lakala.module.poster.vo;

import com.lakala.module.comm.ObjectInput;

/**
 * 
 * @ClassName: PosterInput
 * @Description: 广告模块入参实体
 * @author zhiziwei
 * @date 2015-3-19 下午4:15:22
 * 
 */
public class PosterInput extends ObjectInput {
	private String psam;

	private String channelcode;

	public String getPsam() {
		return psam;
	}

	public void setPsam(String psam) {
		this.psam = psam;
	}

	public String getChannelcode() {
		return channelcode;
	}

	public void setChannelcode(String channelcode) {
		this.channelcode = channelcode;
	}
	
	//覆盖toString()
	@Override
	public String toString() {
		return "{\"token\":\"" + this.getToken() 
			 + "\",\"mobile\":\""+ this.getMobile()
			 + "\",\"page\":\"" + this.getPage() 
			 + "\",\"pageSize\":\"" + this.getPageSize() 
			 + "\",\"psam\":\""  + this.getPsam()
			 + "\",\"channelcode\":\""  + this.getChannelcode() + "\"}";
	}
}
