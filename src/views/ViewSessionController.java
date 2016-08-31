package views;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import interfaces.IApplicationSession;

public class ViewSessionController implements IApplicationSession {
	
	HttpSession session;
	
	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSession(HttpSession session) {
		this.session = session;
	}


	@Override
	public void setMapAttribute(String name, Object value) {
		this.session.setAttribute(name, value);
	}

	@Override
	public Object getMapAttribute(String attributeName) {
		
		return this.session.getAttribute(attributeName);
	}


}
