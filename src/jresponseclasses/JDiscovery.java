package jresponseclasses;

import java.util.ArrayList;

import enums.EDiscoveryType;
import interfaces.IDiscovery;

public class JDiscovery {
	private EDiscoveryType discoveryType;
	private ArrayList<?> discoveryList;
	private String dicoveryObjectName;
	
	public JDiscovery(IDiscovery<?> discovery){
		
		
		try {
			this.setDiscoveryList(discovery.getTypes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setDiscoveryType(discovery.getDiscoveryType());
	}

	public EDiscoveryType getDiscoveryType() {
		return discoveryType;
	}

	public void setDiscoveryType(EDiscoveryType discoveryType) {
		this.discoveryType = discoveryType;
	}

	public ArrayList<?> getDiscoveryList() {
		return discoveryList;
	}

	public void setDiscoveryList(ArrayList<?> discoveryList) {
		this.discoveryList = discoveryList;
	}

	public String getDicoveryObjectName() {
		return dicoveryObjectName;
	}

	public void setDicoveryObjectName(String dicoveryObjectName) {
		this.dicoveryObjectName = dicoveryObjectName;
	}
	
}
