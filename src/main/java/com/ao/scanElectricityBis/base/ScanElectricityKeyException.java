package com.ao.scanElectricityBis.base;

public class ScanElectricityKeyException extends ScanElectricityException{

	public ScanElectricityKeyException(String message) {
		super(message);
		
	}
	public ScanElectricityKeyException(String message,Exception ex) {
		super(message,ex);
		
	}
}
