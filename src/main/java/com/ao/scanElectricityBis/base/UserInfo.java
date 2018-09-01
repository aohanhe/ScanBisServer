package com.ao.scanElectricityBis.base;

public interface UserInfo {
	/**
	 * 
	 * @return
	 */
	public String getName();
	/**
	 * 取得当前用户ID
	 * @return
	 */
	public int getUser();
	/**
	 * 用户头像
	 * @return
	 */
	public String getAvatar();
	/**
	 * 是否为管理员用户
	 * @return
	 */
	public boolean isAdminUser();
	
	
	/**
	 * 取得管理权限区域
	 * @return
	 */
	public String getRightAreaCode();
	
	/**
	 * 取得用户对应的运营商ID
	 * @return
	 */
	public Integer getOperatorId();
	
	/**
	 * 是否拥有对应权限项
	 * @param rightKey	 
	 * @return
	 */
	public boolean isHasRight(int rightKey);
	

}
