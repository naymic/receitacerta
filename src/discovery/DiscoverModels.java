package discovery;

import java.util.ArrayList;

import annotations.AModelClasses;
import enums.EDiscoveryType;
import exceptions.NoPackageException;
import interfaces.IDiscovery;
import model.Model;
import reflection.GenericReflection;
import reflection.ReflectionDAO;
import reflection.ReflectionPackage;

public class DiscoverModels implements IDiscovery<DescribeModel>{


	@Override
	public EDiscoveryType getDiscoveryType() {
		// TODO Auto-generated method stub
		return EDiscoveryType.MODEL;
	}

	@Override
	public ArrayList<DescribeModel> getTypes()throws Exception {
		ArrayList<DescribeModel> describeModelList = new ArrayList<>();
		ArrayList<Class<?>> modelClassList = getModels();

		for(Class<?> cl: modelClassList){
			if(cl.isAnnotationPresent(AModelClasses.class)){
			Model model = (Model)GenericReflection.instanciateByClass(cl);
				if(model != null && model.getClass().isAnnotationPresent(AModelClasses.class)){
					ReflectionDAO rd = new ReflectionDAO(model);
					DescribeModel dm = new DescribeModel(rd);
					describeModelList.add(dm);
				}
			}
		}

		return describeModelList;
	}


	private ArrayList<Class<?>> getModels() throws ClassNotFoundException, NoPackageException{

		ArrayList<Class<?>> modelClassList = ReflectionPackage.getClassesInPackage("model");

		for(int i = 0; i<modelClassList.size(); i++){
			Class<?> cl = modelClassList.get(i);

			try{
				if(!Model.class.isAssignableFrom(cl) && cl.getAnnotation(AModelClasses.class) == null){
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
