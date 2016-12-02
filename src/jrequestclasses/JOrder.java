package jrequestclasses;

import enums.EOrderType;

public class JOrder {
	private String name;
	private EOrderType ordertype;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public EOrderType getOrderType() {
		return ordertype;
	}
	public void setOrderType(EOrderType orderType) {
		this.ordertype = orderType;
	}
	
	
}
