package com.ao.scanElectricityBis.base;

public class ScanElectricityException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2424449025170580540L;

	public ScanElectricityException(String message,Exception ex) {
		super(message, ex);
	}
	
	public ScanElectricityException(String message) {
		super(message);
	}

}
