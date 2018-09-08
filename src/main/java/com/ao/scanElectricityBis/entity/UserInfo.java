package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the user_userInfo database table.
 * 
 */
@Entity(name="UserInfo")
@Table(name="User_userinfo")
@NamedQuery(name="User_userInfo.findAll", query="SELECT u FROM UserInfo u")
public class UserInfo  extends BaseBisEntity implements Serializable{	
	
	private static final long serialVersionUID = -7005710369086986604L;

	@Temporal(TemporalType.TIMESTAMP)	
	@Column(name="lastacess")
	private Date lastAccess;
	
	private float money;

	private String openid;

	private String phone;

	private String pwd;

	@Column(name="username")
	private String name;

	public UserInfo() {
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






	public Date getLastAccess() {
		return lastAccess;
	}






	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}






	public String getName() {
		return name;
	}






	public void setName(String name) {
		this.name = name;
	}

	

}