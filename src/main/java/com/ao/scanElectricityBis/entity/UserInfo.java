package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_userInfo database table.
 * 
 */
@Entity(name="UserInfo")
@Table(name="User_userInfo")
@NamedQuery(name="User_userInfo.findAll", query="SELECT u FROM UserInfo u")
public class UserInfo  extends BaseBisEntity implements Serializable{	
	
	private static final long serialVersionUID = -7005710369086986604L;

	@Temporal(TemporalType.TIMESTAMP)	
	private Date lastAcess;
	
	private float money;

	private String openid;

	private String phone;

	private String pwd;

	private String userName;

	public UserInfo() {
	}

	

	public Date getLastAcess() {
		return this.lastAcess;
	}

	public void setLastAcess(Date lastAcess) {
		this.lastAcess = lastAcess;
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