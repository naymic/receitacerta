package jresponseclasses;

import java.util.ArrayList;

import enums.DiscoveryType;
import interfaces.IDiscovery;

public class JDiscovery {
	private DiscoveryType discoveryType;
	private ArrayList<IDiscovery<?>> discoveryList;
	
	public JDiscovery(DiscoveryType discoverType, ArrayList<IDiscovery<?>> discoveryList){
		this.setDiscoveryList(discoveryList);
		this.setDiscoveryType(discoverType);
	}

	public DiscoveryType getDiscoveryType() {
		return discoveryType;
	}

	public void setDiscoveryType(DiscoveryType discoveryType) {
		this.discoveryType = discoveryType;
	}

	public ArrayList<IDiscovery<?>> getDiscoveryList() {
		return discoveryList;
	}

	public void setDiscoveryList(ArrayList<IDiscovery<?>> discoveryList) {
		this.discoveryList = discoveryList;
	}
	
}
