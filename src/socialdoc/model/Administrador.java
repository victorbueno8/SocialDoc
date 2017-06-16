package socialdoc.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrador {
	@Id
	private String admin;
	private String password;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
}
