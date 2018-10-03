package com.fivesqrd.neer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NfcTag {

	private String tagId;
	private String action;
	private String result;
	
	
	public NfcTag(String tagId, String action, String result) {
		super();
		this.tagId = tagId;
		this.action = action;
		this.result = result;
	}
	public NfcTag(String tagId, String action) {
		super();
		this.tagId = tagId;
		this.action = action;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public NfcTag() {
		super();
	}
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	
}
