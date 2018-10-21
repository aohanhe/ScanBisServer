package com.ao;

/**
 * 订单状�??
 * @author aohanhe
 *
 */
public enum OrderStates {
	PendingStart(1,"待开�?"),Charging(2,"充电�?"),Pausing(3,"暂停�?"),Finished(4,"已结�?")
	;
	private int stateId;
	private String stateName;
	
	private OrderStates(int id,String name) {
		this.stateId = id;
		this.stateName = name;
	}
	
	public int getStateId() {
		return stateId;
	}
	
	public String getStateName() {
		return stateName;
	}

}
