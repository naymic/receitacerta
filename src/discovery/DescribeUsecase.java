package discovery;

import interfaces.IController;
import model.Describe;

public class DescribeUsecase extends Describe{

	public DescribeUsecase(IController controller){
		this.dsetName(controller.getClass().getSimpleName());
	}


}
