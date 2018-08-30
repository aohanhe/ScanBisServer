package com.ao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the station_stationInfo database table.
 * 
 */
@Entity
@NamedQuery(name="Station_stationInfo.findAll", query="SELECT s FROM Station_stationInfo s")
public class Station_stationInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int creator;

	private int modifier;

	@Temporal(TemporalType.TIMESTAMP)
	private Date moditime;

	private String name;

	private int operatorId;

	private String point;

	private float price;

	private String regioncode;

	private float sharingScale;

	private byte status;

	public Station_stationInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
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

}