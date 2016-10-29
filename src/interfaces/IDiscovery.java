package interfaces;

import java.util.ArrayList;

import enums.DiscoveryType;

public interface IDiscovery<E> {
	
	public DiscoveryType getDiscoveryType();
	
	public ArrayList<E> getTypes();
}
