package discovery;

import java.lang.reflect.Method;
import java.util.ArrayList;

import enums.EDiscoveryType;
import interfaces.IController;
import interfaces.IExtendedDiscovery;
import model.Model;
import reflection.ReflectionController;

public class DiscoverActions implements IExtendedDiscovery<DescribeAction, IController> {
	
	IController controller;
	
	public DiscoverActions(IController controller){
		this.setDiscoveryObject(controller);
	}

	@Override
	public EDiscoveryType getDiscoveryType() {
		return EDiscoveryType.USECASE_ACTIONS;
	}

	@Override
	public ArrayList<DescribeAction> getTypes() {
		ReflectionController rc = new ReflectionController(controller);
		ArrayList<Method> actionList = rc.getControllerActions();
		ArrayList<DescribeAction> describeActionList = new ArrayList<>();
		
		for(Method method : actionList){
			DescribeAction da = new DescribeAction(rc, method);
			describeActionList.add(da);
		}
		
		return describeActionList;
	}

	@Override
	public void setDiscoveryObject(IController controller) {
		this.controller = controller;
	}
	
	private IController getUsecasae(){
		return controller;
	}
	
}
