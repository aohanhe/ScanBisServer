package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the account_recharge database table.
 * 
 */
@Entity
@Table(name="account_recharge")
@NamedQuery(name="AccountRecharge.findAll", query="SELECT a FROM AccountRecharge a")
public class AccountRecharge extends BaseBisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private float afterMoney;

	private float beforeMoney;

	private float charge;

	private String code;

	

	private int status;

	private int userid;

	public AccountRecharge() {
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

	public float getCharge() {
		return this.charge;
	}

	public void setCharge(float charge) {
		this.charge = charge;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}