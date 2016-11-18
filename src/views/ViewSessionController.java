package views;

import javax.servlet.http.HttpSession;

import interfaces.IApplicationSession;

public class ViewSessionController implements IApplicationSession<HttpSession>{
	
	HttpSession session;
	
	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return (HttpSession)session;
	}

	@Override
	public void setSession(HttpSession session) {
		this.session = ((HttpSession)session);
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
		return this.session.getAttribute(name) != null;
	}

}
