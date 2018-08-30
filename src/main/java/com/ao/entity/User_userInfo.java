package com.ao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_userInfo database table.
 * 
 */
@Entity
@NamedQuery(name="User_userInfo.findAll", query="SELECT u FROM User_userInfo u")
public class User_userInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	private int creator;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastAcess;

	private int modifier;

	@Temporal(TemporalType.TIMESTAMP)
	private Date moditime;

	private float money;

	private String openid;

	private String phone;

	private String pwd;

	private String userName;

	public User_userInfo() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getLastAcess() {
		return this.lastAcess;
	}

	public void setLastAcess(Date lastAcess) {
		this.lastAcess = lastAcess;
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

	public float getMoney() {
		return this.money;
	}

	public void setMoney(float money) {
		this.money = money;
	}

	public String getOpenid() {
		return this.openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}