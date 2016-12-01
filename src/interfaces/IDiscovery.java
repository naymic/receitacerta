package interfaces;

import java.util.ArrayList;

import enums.EDiscoveryType;

public interface IDiscovery<E> {
	
	public EDiscoveryType getDiscoveryType();
	
	public ArrayList<E> getTypes()throws Exception;
}
