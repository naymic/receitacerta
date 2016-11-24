package discovery;

import java.util.ArrayList;

import annotations.AModelClasses;
import controllers.GenericController;
import enums.DiscoveryType;
import exceptions.NoPackageException;
import interfaces.IController;
import interfaces.IDiscovery;
import model.Model;
import reflection.GenericReflection;
import reflection.ReflectionPackage;

public class DiscoverUsecases  implements IDiscovery<DescribeUsecase>{

	@Override
	public DiscoveryType getDiscoveryType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<DescribeUsecase> getTypes() throws Exception {
		ArrayList<DescribeUsecase> descibeUsecaseList = new ArrayList<>();
		ArrayList<Class<?>> modelClassList = getControllers();
		
		for(Class<?> cl: modelClassList){
			IController controller = (IController)GenericReflection.instanciateByClass(cl);
			DescribeUsecase du = new DescribeUsecase(controller);
			descibeUsecaseList.add(du);
		}
		
		return descibeUsecaseList;
	}
	
	
	private ArrayList<Class<?>> getControllers() throws ClassNotFoundException, NoPackageException{
		ArrayList<Class<?>> modelClassList = ReflectionPackage.getClassesInPackage("controllers");

		for(int i = 0; i<modelClassList.size(); i++){
			Class<?> cl = modelClassList.get(i);
			
			try{
				if(!IController.class.isAssignableFrom(cl) || cl.getSimpleName().equals("controllers.GenericController")){
					modelClassList.remove(i);
					i--;
				}
			}catch(NullPointerException npe){
				modelClassList.remove(i);
				i--;
			}
			
		}
		
		return modelClassList;
	}

}
