package discovery;

import interfaces.IController;

public class DescribeUsecase {
	String usecaseName;
	
	public DescribeUsecase(IController controller){
		this.setUsecaseName(controller.getClass().getSimpleName());
	}

	public String getUsecaseName() {
		return usecaseName;
	}

	public void setUsecaseName(String usecaseName) {
		this.usecaseName = usecaseName;
	}
}
