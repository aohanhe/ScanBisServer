package com.ao.scanElectricityBis.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the base_accounts database table.
 * 
 */
@Entity
@Table(name="base_accounts")
@NamedQuery(name="BaseAccount.findAll", query="SELECT b FROM BaseAccount b")
public class BaseAccount extends BaseBisEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private int operatorId;

	private String phone;

	private String pwd;

	private String regionCode;

	@Column(columnDefinition="status")
	private boolean status;

	private String userName;

	public BaseAccount() {
	}

	
	public int getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
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

	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public boolean getStatus() {
		return this.status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}