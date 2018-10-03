package com.fivesqrd.neer;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NeerSense {
	
	private String deviceName;
	private String deviceId;
	private float temp;
	private float counter;
	private float waterLimit;
	
	public NeerSense(String deviceId, String temp, String counter) {
		this.deviceId = deviceId;
		this.temp = Float.parseFloat(temp);
		this.counter = Float.parseFloat(counter);
	}
	
	
	public String getDeviceName() {
		return this.deviceName;
	}
	
	public String getDeviceId() {
		return this.deviceId;
	}
	
	public float getTemp() {
		return this.temp;
	}
	
	public float getcounter() {
		return this.counter;
	}
	
	public float getLimit() {
		return this.waterLimit;
	}
	
	public boolean setDeviceName(String deviceName) {
		try {
			this.deviceName = deviceName;
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean setDeviceId(String deviceId) {
		try {
			this.deviceId = deviceId;
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean setTemp(float temp) {
		try {
			this.temp = temp;
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public boolean setcounter(float counter) {
		try {
			this.counter = counter;
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean setWaterLimit(float limit) {
		try {
			this.waterLimit = limit;
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	public boolean resetCounter() {
		try {
			this.counter = 0.0f;
			return true;
			
		}catch(Exception e){
			return false;
		}
	}
	
	public boolean isWaterLimitReached() {
		if(this.counter>=this.waterLimit) {
			return true;
		}else {
			return false;
		}
	}

	public float limitRemaining() {
		return this.waterLimit-this.counter;
	}
}
