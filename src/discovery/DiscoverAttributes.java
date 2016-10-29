package discovery;

import java.util.ArrayList;

import enums.DiscoveryType;
import interfaces.IExtendedDiscovery;
import model.Model;

public class DiscoverAttributes implements IExtendedDiscovery<DescribeAttribute, Model>{

	@Override
	public DiscoveryType getDiscoveryType() {
		// TODO Auto-generated method stub
		return DiscoveryType.MODEL_ATTRIBUTES;
	}

	@Override
	public ArrayList<DescribeAttribute> getTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscoveryObject(Model object) {
		// TODO Auto-generated method stub
		
	}

}
