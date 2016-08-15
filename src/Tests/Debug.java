package Tests;

public class Debug {
	
	boolean debugtest;
	static Debug debug = null;
	
	private Debug(){
		debugtest = false;
	}
	
	public static Debug getInstance(){
		if(debug == null){
			debug = new Debug();
		}
		return debug;
	}
	
	public void setDebugTrue(){
		debugtest=true;
	}
	
	public boolean isDebug(){
		return debugtest;
	}
}

