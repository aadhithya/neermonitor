package com.fivesqrd.neer;

public class GetUpdateObj {

	private String deviceid;
	private double temp;
	private double counter;
	private double lim_left;
	
	
	public GetUpdateObj(String deviceid, double temp, double counter, double lim_left) {
		super();
		this.deviceid = deviceid;
		this.temp = temp;
		this.counter = counter;
		this.lim_left = lim_left;
	}


	public String getDeviceid() {
		return deviceid;
	}


	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}


	public double getTemp() {
		return temp;
	}


	public void setTemp(double temp) {
		this.temp = temp;
	}


	public double getCounter() {
		return counter;
	}


	public void setCounter(double counter) {
		this.counter = counter;
	}


	public double getLim_left() {
		return lim_left;
	}


	public void setLim_left(double lim_left) {
		this.lim_left = lim_left;
	}
	
	
	
}
