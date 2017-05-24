package socialdoc.controle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import socialdoc.model.Medico;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
public class CadastroMedicoBean {
	private Medico medico = new Medico();
	private String confirmPass;
	
	public Medico getMedico() {
		return medico;
	}
	public void setmedico(Medico medico) {
		this.medico = medico;
	}
	public String getConfirmPass() {
		return confirmPass;
	}
	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	
	public String cadastrar() {
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		if(medico.getPassword().equals(confirmPass)) {
			repository.adiciona(medico);
			
			FacesMessage fm = new FacesMessage("Usuário cadastrado com sucesso!");
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
			fc.addMessage(null, fm);
			
			return "/login";
		} else {
			FacesMessage fm = new FacesMessage("Senhas não são iguais!");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
			
			return "/registrar_medico";
		}
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}
}
