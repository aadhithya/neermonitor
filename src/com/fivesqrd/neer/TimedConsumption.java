package com.fivesqrd.neer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TimedConsumption {
	private String fromdate;
	private String todate;
	private String deviceid;
	private double totalconsumption;
	
	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public double getTotalconsumption() {
		return totalconsumption;
	}

	public void setTotalconsumption(double totalconsumption) {
		this.totalconsumption = totalconsumption;
	}

	public TimedConsumption(String fromdate,String todate, String deviceid, double totalconsumption) {
		this.fromdate = fromdate;
		this.todate = todate;
		this.deviceid = deviceid;
		this.totalconsumption = totalconsumption;
	}
}
