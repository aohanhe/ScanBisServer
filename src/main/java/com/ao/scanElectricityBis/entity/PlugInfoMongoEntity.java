package com.ao.scanElectricityBis.entity;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class PlugInfoMongoEntity {
	@Id
	private long id;
	
	private int deviceId;
	
	private int deviceIndex;
	
	private int status;
	
	private Date lastUpTime;
	
	private boolean isWorking;
	
	/**
	 * µ±«∞’ µ•∫≈
	 */
	@Indexed
	private Integer curBillId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastUpTime() {
		return lastUpTime;
	}

	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}

	public boolean isWorking() {
		return isWorking;
	}

	public void setWorking(boolean isWorking) {
		this.isWorking = isWorking;
	}

	public Integer getCurBillId() {
		return curBillId;
	}

	public void setCurBillId(Integer curBillId) {
		this.curBillId = curBillId;
	}

	public int getDeviceIndex() {
		return deviceIndex;
	}

	public void setDeviceIndex(int deviceIndex) {
		this.deviceIndex = deviceIndex;
	}

	

}
