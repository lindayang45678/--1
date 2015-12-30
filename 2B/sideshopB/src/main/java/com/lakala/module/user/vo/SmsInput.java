package com.lakala.module.user.vo;

import com.lakala.module.comm.ObjectInput;

public class SmsInput extends ObjectInput{
	
	private String phone;
	
	private String type;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
