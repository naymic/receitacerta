package discovery;

import java.lang.reflect.Method;
import java.util.ArrayList;

import enums.DiscoveryType;
import interfaces.IExtendedDiscovery;
import model.Model;
import reflection.ReflectionDAO;

public class DiscoverAttributes implements IExtendedDiscovery<DescribeAttribute, Model>{

	Model object;
	
	@Override
	public DiscoveryType getDiscoveryType() {
		// TODO Auto-generated method stub
		return DiscoveryType.MODEL_ATTRIBUTES;
	}

	@Override
	public ArrayList<DescribeAttribute> getTypes() {
		ReflectionDAO rd = new ReflectionDAO(object);
		ArrayList<Method> methods = new ArrayList();
		try{
			methods = rd.getObjectMethodsForDiscoverAttributes("get");
		}catch(RuntimeException re){
			re.printStackTrace();
		}
		ArrayList<DescribeAttribute> describeAttributeList = new ArrayList<>();
		
		
		for(Method method: methods){
			DescribeAttribute da = new DescribeAttribute(rd, method);
			describeAttributeList.add(da);
		}
		
		return describeAttributeList;
	}

	@Override
	public void setDiscoveryObject(Model object) {
		this.object = object;
		
	}

}
