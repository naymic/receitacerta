package jresponseclasses;

import java.util.ArrayList;

import enums.DiscoveryType;
import interfaces.IDiscovery;

public class JDiscovery {
	private DiscoveryType discoveryType;
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

	public DiscoveryType getDiscoveryType() {
		return discoveryType;
	}

	public void setDiscoveryType(DiscoveryType discoveryType) {
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
