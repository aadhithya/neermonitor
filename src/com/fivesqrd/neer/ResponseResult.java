package com.fivesqrd.neer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseResult {
	private String status;
	private String message;
	
	public ResponseResult(){}
	
	public ResponseResult(String status,String message){
		this.status=status;
		this.message=message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
