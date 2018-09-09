package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;


import ao.jpaQueryHelper.annotations.QueryExpression;

import java.util.Date;


/**
 * The persistent class for the account_expense database table.
 * 
 */
@Entity
@Table(name="account_expense")
@NamedQuery(name="AccountExpense.findAll", query="SELECT a FROM AccountExpense a")
@NamedEntityGraph(name="AccountExpense.Join",includeAllAttributes=true)
@QueryExpression("select o,u.userName from AccountExpense o left join UserInfo u ")
public class AccountExpense extends BaseOnlyIdEntity implements Serializable {
	
	
	
	private static final long serialVersionUID = 2388007214188422750L;

	@Column(name="aftermoney")
	private float afterMoney;

	@Column(name="beforemoney")
	private float beforeMoney;

	private float cost;

	private int costminute;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int plugid;

	@Column(name="userid")
	private int userId;
	
	@Transient
	private String userName;
	
	@Transient
	private String phone;
	
	@Transient
	private int deviceId;
	
	@Transient
	private String devicecode;
	
	@Transient
	private String station;
	
	@Transient
	private int stationId;
	
	@Transient
	private String regionCode;
	
	@Transient
	private int operatorId;
	
	@Transient
	private String operator;
	
	
	
	
	
	public AccountExpense() {
	}
	

	public float getAfterMoney() {
		return this.afterMoney;
	}

	public void setAfterMoney(float afterMoney) {
		this.afterMoney = afterMoney;
	}

	public float getBeforeMoney() {
		return this.beforeMoney;
	}

	public void setBeforeMoney(float beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public int getCostminute() {
		return this.costminute;
	}

	public void setCostminute(int costminute) {
		this.costminute = costminute;
	}

	public Date getCreated() {
		return this.created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getPlugid() {
		return this.plugid;
	}

	public void setPlugid(int plugid) {
		this.plugid = plugid;
	}

	
	public String getUserName() {
		return userName;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public String getRegionCode() {
		return regionCode;
	}


	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}


	public String getDevicecode() {
		return devicecode;
	}


	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}


	public String getStation() {
		return station;
	}


	public void setStation(String station) {
		this.station = station;
	}


	public int getStationId() {
		return stationId;
	}


	public void setStationId(int stationId) {
		this.stationId = stationId;
	}


	


	public int getOperatorId() {
		return operatorId;
	}


	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}


	public String getOperator() {
		return operator;
	}


	public void setOperator(String operator) {
		this.operator = operator;
	}


		

}