package com.ao.entity;

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
public class AccountRecharge implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private float afterMoney;

	private float beforeMoney;

	private float charge;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int creator;

	private int modifier;

	@Temporal(TemporalType.TIMESTAMP)
	private Date moditime;

	private int status;

	private int userid;

	public AccountRecharge() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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