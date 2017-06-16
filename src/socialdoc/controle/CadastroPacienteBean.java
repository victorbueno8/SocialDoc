package socialdoc.controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import socialdoc.model.Paciente;
import socialdoc.model.Usuario;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
public class CadastroPacienteBean {
	private Paciente paciente = new Paciente();
	private String confirmPass;
	private String oldPassword;
	private String newPassword;
	
	
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
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
		if(paciente.getPassword().equals(confirmPass)) {
			repository.adiciona(paciente);
			
			FacesMessage fm = new FacesMessage("Usuário cadastrado com sucesso!");
			fm.setSeverity(FacesMessage.SEVERITY_INFO);
			fc.addMessage(null, fm);
			
			return "/index";
		} else {
			FacesMessage fm = new FacesMessage("Senhas não são iguais!");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
			
			return "/registrar_paciente";
		}
	}
	
	public String updatePaciente(Usuario usuario){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		if(!newPassword.isEmpty()){
			if(oldPassword.equals(usuario.getPassword()) && confirmPass.equals(newPassword)){
				usuario.setPassword(newPassword);
			} else {
				FacesMessage fm = new FacesMessage("Senhas não conferem!");
				fm.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(null, fm);
				return "/medico_editar";
			}
		}
		
		repository.atualiza(usuario);
		
		
		
		return "paciente_editar";
	}
	
	public String removePaciente(Usuario usuario){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		repository.remove(usuario);
		
		return "admin";
	}
	
	public List<Paciente> getPacientes(){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		
		return repository.getPacientes();
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
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
