package socialdoc.controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import socialdoc.model.Medico;
import socialdoc.model.Paciente;
import socialdoc.model.Usuario;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
@SessionScoped
public class BuscarUsuariosBean {
	private String busca;
	private String tipo;
	private List<Usuario> resposta;
	
	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public List<Usuario> getResposta() {
		return resposta;
	}

	public void setResposta(List<Usuario> resposta) {
		this.resposta = resposta;
	}
	
	public String buscaPacientes(){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		resposta = new ArrayList<Usuario>();
		
		for (Paciente paciente : repository.getPacientes()) {
			if(paciente.getNome().contains(busca)) this.resposta.add(paciente);
		}
		
		return "medico_agendar";
	}
	
	public String medicoMarcar() {
		
		return "medico_marcar";
	}
	
	public String buscaMedico(){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		resposta = new ArrayList<Usuario>();
		for (Medico medico : repository.getMedicos()) {
			if(tipo.equalsIgnoreCase("medico") && medico.getNome().contains(busca)) 
				this.resposta.add(medico);
			else if(tipo.equalsIgnoreCase("especialidade") && medico.getEspecialidade().contains(busca))
				this.resposta.add(medico);
		}
		
		return "paciente_agendar";
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}

}
