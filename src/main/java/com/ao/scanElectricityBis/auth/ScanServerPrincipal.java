package com.ao.scanElectricityBis.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 用户帐号信息
 * @author aohanhe
 *
 */

public class ScanServerPrincipal  implements IScanServerPrincipal,Serializable{
	
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	/**
	 *  
	 */
	private static final long serialVersionUID = 4597354917498076853L;
	
	
	public static enum UserType {
		/**
		 * 管理后台用户
		 */
		MangerUser,
		/**
		 * 小程序用户
		 */
		WxMinAppUser
	}
	/**
	 * 用户的ID值
	 */
	private int user;
	
	/**
	 * 用户类型
	 */
	private UserType userType;
	
	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 运营商ID
	 */
	private Integer operatorId;
	/**
	 * 运营商名称
	 */
	private String operatorName;
	
	private String openId;
	
	private String phone;
	
	private boolean adminUser;
	/**
	 * 管理区域
	 */
	private String rightAreaCode;
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public boolean isAdminUser() {
		return adminUser;
	}
	public void setAdminUser(boolean adminUser) {
		this.adminUser = adminUser;
	}
	public String getRightAreaCode() {
		return rightAreaCode;
	}
	public void setRightAreaCode(String rightAreaCode) {
		this.rightAreaCode = rightAreaCode;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
