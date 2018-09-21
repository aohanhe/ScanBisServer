package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the station_plugInfo database table.
 * 
 */
@Entity
@Table(name="Station_pluginfo")
@NamedQuery(name = "StationPlugInfo.findAll", query = "SELECT s FROM StationPlugInfo s")
public class StationPlugInfo extends BaseBisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String code; 
	
	
	private int deviceid;
	
	private boolean isfault;
	
	// 在设备中的位置
	private int deviceindex;
	
	@Transient
	private String deviceCode;
	
	@Transient
	private int stationId;
	
	@Transient
	private String station;
	
	@Transient
	private float price;
	
	@Transient
	private String regioncode;
	
	@Transient
	private String address;
	
	@Transient
	private String operator;
	
	@Transient
	private int operatorId;
	
	@Transient
	private int curStatus;
	
	@Transient
	private Date lastUpTime;
	
	@Transient
	private boolean isWorking;
	
	
	public StationPlugInfo() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(int deviceid) {
		this.deviceid = deviceid;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getRegioncode() {
		return regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getDeviceindex() {
		return deviceindex;
	}

	public void setDeviceindex(int deviceindex) {
		this.deviceindex = deviceindex;
	}

	

	public int getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}

	public boolean isIsfault() {
		return isfault;
	}

	public void setIsfault(boolean isfault) {
		this.isfault = isfault;
	}

	public int getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(int curStatus) {
		this.curStatus = curStatus;
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

}