package discovery;

import interfaces.IController;
import model.Describe;

public class DescribeUsecase extends Describe{

	public DescribeUsecase(IController controller){
		String controllerName = controller.getClass().getSimpleName();
		int i = controllerName.indexOf("Controller");
		
		
		this.dsetName(controllerName.substring(0, i));
	}


}
