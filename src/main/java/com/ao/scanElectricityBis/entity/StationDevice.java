package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;



import java.util.Date;


/**
 * The persistent class for the station_device database table.
 * 
 */
@Entity
@Table(name="station_device")
@NamedQuery(name="StationDevice.findAll", query="SELECT s FROM StationDevice s")
public class StationDevice extends BaseBisEntity implements Serializable {	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5228031139857842384L;

	private String code;
	
	@Column(name="faultnumber")
	private int faultNumber;

	@Column(name="localadress")
	private String localAdress;

	@Column(name="localpoint")
	private String localPoint;	

	@Column(name="stationid")
	private int stationId;

	private int status;
	@Column(name="totalnumber")
	private int totalNumber;
	@Column(name="usingnumber")
	private int usingNumber;
	
	@Column(name="lastuptime")
	private Date lastUpTime;
	
	@Transient
	private String regionCode;
	
	@Transient
	private int operatorid;
	
	@Transient
	private String operator;
	@Transient
	private String stationName;
	
	@Transient
	private float  price;
	
	@Transient
	private float  sharingScale;
	

	public StationDevice() {
	}

	

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public int getFaultNumber() {
		return this.faultNumber;
	}

	public void setFaultNumber(int faultNumber) {
		this.faultNumber = faultNumber;
	}

	public String getLocalAdress() {
		return this.localAdress;
	}

	public void setLocalAdress(String localAdress) {
		this.localAdress = localAdress;
	}

	public String getLocalPoint() {
		return this.localPoint;
	}

	public void setLocalPoint(String localPoint) {
		this.localPoint = localPoint;
	}

	

	public int getStationId() {
		return this.stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTotalNumber() {
		return this.totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public int getUsingNumber() {
		return this.usingNumber;
	}

	public void setUsingNumber(int usingNumber) {
		this.usingNumber = usingNumber;
	}

	public String getRegionCode() {
		return regionCode;
	}



	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}



	public int getOperatorId() {
		return operatorid;
	}



	public void setOperatorId(int operatorId) {
		this.operatorid = operatorId;
	}



	public String getOperator() {
		return operator;
	}



	public void setOperator(String operator) {
		this.operator = operator;
	}



	public Date getLastUpTime() {
		return lastUpTime;
	}



	public void setLastUpTime(Date lastUpTime) {
		this.lastUpTime = lastUpTime;
	}



	public String getStationName() {
		return stationName;
	}



	public void setStationName(String stationName) {
		this.stationName = stationName;
	}



	public int getOperatorid() {
		return operatorid;
	}



	public void setOperatorid(int operatorid) {
		this.operatorid = operatorid;
	}



	public float getPrice() {
		return price;
	}



	public void setPrice(float price) {
		this.price = price;
	}



	public float getSharingScale() {
		return sharingScale;
	}



	public void setSharingScale(float sharingScale) {
		this.sharingScale = sharingScale;
	}



	

}