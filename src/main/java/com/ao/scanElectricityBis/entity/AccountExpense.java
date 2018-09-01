package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the account_expense database table.
 * 
 */
@Entity
@Table(name="account_expense")
@NamedQuery(name="AccountExpense.findAll", query="SELECT a FROM AccountExpense a")
public class AccountExpense extends BaseOnlyIdEntity implements Serializable {
	private static final long serialVersionUID = 2388007214188422750L;

	private float afterMoney;

	private float beforeMoney;

	private float cost;

	private int costminute;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int plugid;

	private int userid;

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

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}