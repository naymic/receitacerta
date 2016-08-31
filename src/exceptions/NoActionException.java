package exceptions;

public class NoActionException extends Exception {
	
	public NoActionException(String usecase, String actionName){
		super("This action: "+ actionName +" dont exit in the following usecase: " + usecase);
	}
	
}
