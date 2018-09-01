package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the station_plugInfo database table.
 * 
 */
@Entity
@Table(name="Station_plugInfo")
@NamedQuery(name = "StationPlugInfo.findAll", query = "SELECT s FROM StationPlugInfo s")
public class StationPlugInfo extends BaseBisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String code; 

	public StationPlugInfo() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}