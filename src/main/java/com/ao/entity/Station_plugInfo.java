package com.ao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the station_plugInfo database table.
 * 
 */
@Entity
@NamedQuery(name="Station_plugInfo.findAll", query="SELECT s FROM Station_plugInfo s")
public class Station_plugInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int deviceId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date moditime;

	public Station_plugInfo() {
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

	public int getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public Date getModitime() {
		return this.moditime;
	}

	public void setModitime(Date moditime) {
		this.moditime = moditime;
	}

}