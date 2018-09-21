package com.ao.scanElectricityBis.entity;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * �豸��Ӧ��mongoʵ����
 * @author aohanhe
 *
 */
@Document
public class DeviceMongoEntry {
	@Id
	int id;
	int status;
	Date lastUpTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

}
