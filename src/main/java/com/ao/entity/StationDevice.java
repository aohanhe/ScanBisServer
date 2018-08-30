package com.ao.entity;

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
public class StationDevice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int creator;

	private int faultNumber;

	private String localAdress;

	private String localPoint;

	private int modifier;

	@Temporal(TemporalType.TIMESTAMP)
	private Date moditime;

	private int stationId;

	private byte status;

	private int totalNumber;

	private int usingNumber;

	public StationDevice() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getCreator() {
		return this.creator;
	}

	public void setCreator(int creator) {
		this.creator = creator;
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

	public int getModifier() {
		return this.modifier;
	}

	public void setModifier(int modifier) {
		this.modifier = modifier;
	}

	public Date getModitime() {
		return this.moditime;
	}

	public void setModitime(Date moditime) {
		this.moditime = moditime;
	}

	public int getStationId() {
		return this.stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
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

}