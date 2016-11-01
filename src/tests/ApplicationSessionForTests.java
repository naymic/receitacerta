package tests;

import java.util.HashMap;

import interfaces.IApplicationSession;

public class ApplicationSessionForTests implements IApplicationSession<HashMap<String, Object>> {
	
	HashMap<String, Object> session;
	
	
	public ApplicationSessionForTests(){
		this.session = new HashMap<>();
	}
	
	
	@Override
	public HashMap<String, Object> getSession() {
		// TODO Auto-generated method stub
		return this.session;
	}
	
	public HashMap<String, Object> getSessionHM() {
		// TODO Auto-generated method stub
		return this.session;
	}

	@Override
	public void setSession(HashMap<String, Object> session) {
		this.session = session;

	}
	
	

	@Override
	public void setMapAttribute(String name, Object value) {
		this.getSession().put(name, value);

	}

	@Override
	public boolean existMapAttribute(String name) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Object getMapAttribute(String name) {
		// TODO Auto-generated method stub
		return this.getSession().get(name);
	}

}
