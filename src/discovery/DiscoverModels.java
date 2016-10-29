package discovery;

import java.util.ArrayList;

import enums.DiscoveryType;
import interfaces.IDiscovery;
import model.Model;

public class DiscoverModels implements IDiscovery<DescribeModel>{

	String modelName;
	
	public DiscoverModels(Model model){
		
		
	}

	@Override
	public DiscoveryType getDiscoveryType() {
		// TODO Auto-generated method stub
		return DiscoveryType.MODEL;
	}

	@Override
	public ArrayList<DescribeModel> getTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
