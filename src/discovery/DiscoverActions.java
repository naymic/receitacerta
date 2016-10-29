package discovery;

import java.util.ArrayList;

import enums.DiscoveryType;
import interfaces.IController;
import interfaces.IExtendedDiscovery;
import model.Model;

public class DiscoverActions implements IExtendedDiscovery<DescribeAction, IController> {
	
	public DiscoverActions(Model model){
		
		
	}

	@Override
	public DiscoveryType getDiscoveryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DescribeAction> getTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDiscoveryObject(IController object) {
		// TODO Auto-generated method stub
		
	}
	
}
