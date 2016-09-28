package views;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import interfaces.IApplicationSession;

public class ViewSessionController implements IApplicationSession {
	
	public HttpSession session = null;
	
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

	@Override
	public boolean existMapAttribute(String name) {
		if(session == null)
			return false;
			
		Enumeration<String> it = session.getAttributeNames();
		
		while(it.hasMoreElements()){
			String s = it.nextElement();
			if(s.equalsIgnoreCase(name))
				return true;
		}
		
		return false;
		
		
	}


}
