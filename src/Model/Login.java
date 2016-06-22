package Model;

import Reflection.Entity;

public class Login extends Model {
	
	private Integer id;
	private String user;
	private String password;
	
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "login";
	}

	
	@Entity(attributeName="id", pk=true)
	public Integer dgetId() {
		return id;
	}

	public void dsetId(Integer id) {
		this.id = id;
	}

	@Entity(attributeName="user")
	public String dgetUser() {
		return user;
	}


	public void dsetUser(String user) {
		this.user = user;
	}

	@Entity(attributeName="password")
	public String dgetPassword() {
		return password;
	}

	public void dsetPassword(String password) {
		this.password = password;
	}
}
