package socialdoc.controle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import socialdoc.model.Medico;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
public class CadastrarPeriodoBean {
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private String periodo;
	
	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		String[] s = periodo.split("-|T|\\:");
		this.periodo = s[2]+"/"+s[1]+"/"+s[0]+" "+s[3]+":"+s[4];
		//Formatando o HTML5 datetime-local output para o formato novo
	}

	public String setHorasConsulta() throws ParseException{
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		String user = (String) session.getAttribute("usuario");
		
		Medico medico = repository.getMedico(user);
		
		Date data = format.parse(periodo);
		
		medico.getPeriodoDisponivel().add(data);
		
		return "/medico_home";
	}
	
	public List<String> getHorasConsulta(){
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		String user = (String) session.getAttribute("usuario");
		
		Medico medico = repository.getMedico(user);
		List<String> datas = new ArrayList<String>();
		
		for (Date data:medico.getPeriodoDisponivel()) {
			datas.add(format.format(data));
		}
		
		return datas;
	}
	
	public void removeHorasConsulta(String hora) throws ParseException {
		EntityManager manager = getEntityManager();
		UsuarioRepository repository = new UsuarioRepository(manager);
		
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpSession session = (HttpSession) ec.getSession(true);
		String user = (String) session.getAttribute("usuario");
		
		Medico medico = repository.getMedico(user);
		
		medico.getPeriodoDisponivel().remove(format.parse(hora));
		
		repository.atualiza(medico);
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}
}

//simpledateformat
//java.util.date
//java.sql.date
