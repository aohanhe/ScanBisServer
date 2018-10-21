package com.ao.scanElectricityBis.entity;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.mongodb.core.mapping.Document;


/**
 * 用户消费帐单Mongodb实体类
 * @author aohanhe
 *
 */
//@Document
public class AccountExpenseMongoEntity {
	@Id
	private int id;
	
	private int costminute;
	
	private float cost;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;
	
	//加上了设备编码后的插头编码
	private long plugCode;
	
	//单价
	private float price;

	private int plugid; 
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;
	
	private int userId;
	
	private float beforeMoney;
	
	//计划充电时长
	private int planMinute;
	
	// 订单状态
	private int status;	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCostminute() {
		return costminute;
	}

	public void setCostminute(int costminute) {
		this.costminute = costminute;
	}

	
	public long getPlugCode() {
		return plugCode;
	}

	public void setPlugCode(long plugCode) {
		this.plugCode = plugCode;
	}

	public int getPlugid() {
		return plugid;
	}

	public void setPlugid(int plugid) {
		this.plugid = plugid;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public float getBeforeMoney() {
		return beforeMoney;
	}

	public void setBeforeMoney(float beforeMoney) {
		this.beforeMoney = beforeMoney;
	}

	public int getPlanMinute() {
		return planMinute;
	}

	public void setPlanMinute(int planMinute) {
		this.planMinute = planMinute;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getCost() {
		return cost;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
