package jsonclasses;

/**
 * 
 * @author micha
 * 
 * Class user for set a redirection 
 * in the JSON Response
 *
 */
public class JRedirect{
	String usecase;
	String action;
	String classname;
	boolean redirect;
	
	/**
	 * Class construtor to set redirection to false 
	 * by default
	 */
	public JRedirect(){
		this.setRedirect(false);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getUsecase() {
		return usecase;
	}
	private void setUsecase(String usecase) {
		this.usecase = usecase;
	}
	public String getAction() {
		return action;
	}
	private void setAction(String action) {
		this.action = action;
	}
	public String getClassname() {
		return classname;
	}
	private void setClassname(String classname) {
		this.classname = classname;
	}
	
	public void setRedirection(String classname, String usecase, String action){
		this.setRedirect(true);
		this.setUsecase(usecase);
		this.setClassname(classname);
		this.setAction(action);
	}
	


	public boolean isRedirect() {
		return redirect;
	}

	private void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}
	
}
