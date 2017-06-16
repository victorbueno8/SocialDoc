package socialdoc.controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import socialdoc.model.Medico;
import socialdoc.model.Usuario;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
public class CadastroMedicoBean {
	private Medico medico = new Medico();
	private String confirmPass;
	private String oldPassword;
	private String newPassword;
	
	public String cadastrar() {
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		if(medico.getPassword().equals(confirmPass)) {
			repository.adiciona(medico);
			
			FacesMessage fm = new FacesMessage("Usu�rio cadastrado com sucesso!");
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
			fc.addMessage(null, fm);
			
			return "/index";
		} else {
			FacesMessage fm = new FacesMessage("Senhas n�o s�o iguais!");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
			
			return "/registrar_medico";
		}
	}
	
	public String updateMedico(Usuario usuario){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if(!newPassword.isEmpty()){
			System.out.println("entro");
			if(oldPassword.equals(usuario.getPassword()) && confirmPass.equals(newPassword)){
				usuario.setPassword(newPassword);
				System.out.println("atualizo");
			} else {
				FacesMessage fm = new FacesMessage("Senhas n�o conferem!");
				fm.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(null, fm);
				System.out.println("naodeu");
				return "/medico_editar";
			}
		}
		
		repository.atualiza(usuario);
		
		return "medico_editar";
	}
	
	public String removeMedico(Usuario usuario){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		repository.remove(usuario);
		
		return "admin";
	}
	
	public List<Medico> getMedicos(){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		return repository.getMedicos();
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}
	
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	
}
