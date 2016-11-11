package jrequestclasses;

import enums.EOrderType;

public class JOrder {
	private String name;
	private EOrderType orderType;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EOrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(EOrderType orderType) {
		this.orderType = orderType;
	}
	
	
}
