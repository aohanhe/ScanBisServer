package com.ao.scanElectricityBis.auth;
/**
 * 返回当前用户接口
 * @author aohanhe
 *
 */
public interface CurrentUserAware {
	ScanServerPrincipal currentUser();
}
