package com.fivesqrd.neer;

public class DeviceData {
	
	private String date;
	private double total;
	
	public DeviceData(String date, double total) {
		this.date = date;
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	
	
}
