package socialdoc.controle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import socialdoc.model.Administrador;
import socialdoc.repository.AdminRepository;

@ManagedBean
public class AdminBean {
	private String oldPassword;
	private String password;
	private String passwordConfirm;
	
	public void checkAdminExists(){
		AdminRepository adminRepository = new AdminRepository(getEntityManager());
		
		if(adminRepository.existsAdmin() == false) {
			Administrador adm = new Administrador();
			adm.setAdmin("admin");
			adm.setPassword("123");
			adminRepository.generateAdmin(adm);
		}
	}
	
	public boolean checkPassword(String password){
		AdminRepository rep = new AdminRepository(getEntityManager());
		
		return rep.verificaAdmin(password);
	}
	
	public String changePassword() {
		AdminRepository rep = new AdminRepository(getEntityManager());
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(rep.verificaAdmin(oldPassword) && passwordConfirm.equals(password)){
			Administrador admin = rep.getAdmin();
			admin.setPassword(password);
			
			rep.update(admin);
			
			return "/admin";
		} else {
			FacesMessage fm = new FacesMessage("Senha antiga ou confirmação incorreta.");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
			return "/admin";
			
		}
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
}
