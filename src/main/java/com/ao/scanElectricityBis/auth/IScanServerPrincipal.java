package com.ao.scanElectricityBis.auth;

import com.ao.scanElectricityBis.auth.ScanServerPrincipal.UserType;

public interface IScanServerPrincipal {
	
	
	
	public int getUser() ;
	public UserType getUserType() ;
	public String getName();
	public Integer getOperatorId() ;
	public String getOperatorName() ;
	public String getOpenId() ;
	public String getPhone() ;
	public boolean isAdminUser() ;
	public String getRightAreaCode() ;
	

}
