package socialdoc.controle;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import socialdoc.model.Usuario;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
public class AutenticadorBean {
	private String usuario;
	private String password;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String autentica() throws IOException {
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		AdminBean adminBean = new AdminBean();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		adminBean.checkAdminExists();
		
		if(repository.verificaUsuario(usuario, password)) {
			HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("usuario", usuario);
			
			if(repository.getTipoUsuario(usuario).equals("Paciente")){
				return "view/paciente/paciente_home";
			} else if (repository.getTipoUsuario(usuario).equals("Medico")){
				return "view/medico/medico_home";
			} else {
				FacesMessage fm = new FacesMessage("Erro na verificação das suas credenciais.");
				fm.setSeverity(FacesMessage.SEVERITY_ERROR);
				fc.addMessage(null, fm);
				return "/index";
			}
			
		} else if(usuario.equals("admin") && adminBean.checkPassword(password)) {
			HttpSession session = (HttpSession) ec.getSession(true);
			session.setAttribute("usuario", usuario);
			
			return "view/admin";
			
		} else {
			FacesMessage fm = new FacesMessage("Email ou senha incorreto.");
			fm.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(null, fm);
			return "/index";
			
		}
	}
	
	public Usuario getDadosUsuario(){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		String user = (String) session.getAttribute("usuario");
		return repository.getUsuario(user);
	}
	
	public String sair() throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(false);
		session.removeAttribute("usuario");
		session.invalidate();
		return "/index.xhtml";
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}
	
}


