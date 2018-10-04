package com.fivesqrd.neer;

public class Device {
	private String name;
	private String id;
	private double total;
	private String start_date;
	private String end_date;
	private double limit;
	

	
	public Device(String name, String id) {
		super();
		this.name = name;
		this.id = id;
	}


	public Device(String name, String id, double total, String start_date, String end_date, double limit) {
		super();
		this.name = name;
		this.id = id;
		this.total = total;
		this.start_date = start_date;
		this.end_date = end_date;
		this.limit = limit;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getLimit() {
		return limit;
	}
	public void setLimit(double limit) {
		this.limit = limit;
	}
	
	

}
