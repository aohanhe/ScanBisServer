package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the station_stationInfo database table.
 * 
 */
@Entity
@Table(name="Station_stationinfo")
@NamedQuery(name="StationStationInfo.findAll", query="SELECT s FROM StationStationInfo s")
public class StationStationInfo extends BaseBisEntity implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 8998103228404163602L;



	private String address;

	

	private String name;

	private int operatorid;

	private String point;

	private float price;

	private String regioncode;
	
	@Column(name="sharingscale")
	private float sharingScale;

	private byte status;
	
	@Transient
	private String operator;

	public StationStationInfo() {
	}

	
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOperatorId() {
		return this.operatorid;
	}

	public void setOperatorId(int operatorId) {
		this.operatorid = operatorId;
	}

	public String getPoint() {
		return this.point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getRegioncode() {
		return this.regioncode;
	}

	public void setRegioncode(String regioncode) {
		this.regioncode = regioncode;
	}

	public float getSharingScale() {
		return this.sharingScale;
	}

	public void setSharingScale(float sharingScale) {
		this.sharingScale = sharingScale;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}

}