package socialdoc.controle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import socialdoc.model.Consulta;
import socialdoc.model.Medico;
import socialdoc.model.Usuario;
import socialdoc.repository.ConsultaRepository;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
@SessionScoped
public class MarcarConsultaBean {
	private Usuario medico;
	private Usuario paciente;
	private Date data_consulta;
	private String diagnostico;
	private String observacoes;
	
	private Consulta consultaComReceita;
	
	public String medicoMarcar(String paciente) {
		AutenticadorBean autenticador = new AutenticadorBean();
		this.setMedico(autenticador.getDadosUsuario());
		UsuarioRepository repository = new UsuarioRepository(getEntityManager());
		this.setPaciente(repository.getPaciente(paciente));
		return "medico_marcar";
	}
	
	public String pacienteMarcar(String medico) {
		AutenticadorBean autenticador = new AutenticadorBean();
		this.setPaciente(autenticador.getDadosUsuario());
		UsuarioRepository repository = new UsuarioRepository(getEntityManager());
		this.setMedico(repository.getMedico(medico));
		return "paciente_marcar";
	}
	
	public String marcar() throws ParseException {
		Consulta consulta = new Consulta();
		consulta.setMedico(medico);
		consulta.setPaciente(paciente);
		consulta.setData_consulta(data_consulta);
		consulta.setObservacoes(observacoes);
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		repository.adiciona(consulta);
		
		return "medico_consultas";
	}
	
	public String p_marcar() throws ParseException {
		Consulta consulta = new Consulta();
		consulta.setMedico(medico);
		consulta.setPaciente(paciente);
		consulta.setData_consulta(data_consulta);
		consulta.setObservacoes(observacoes);
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		repository.adiciona(consulta);
		
		return "paciente_consultas";
	}
	
	public List<Consulta> getMyConsultas() {
		AutenticadorBean autenticador = new AutenticadorBean();
		Usuario me = autenticador.getDadosUsuario();
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		return repository.getConsultas(me.getUsuario());
	}
	
	public List<Consulta> getMyConsultasWithPrescription() {
		AutenticadorBean autenticador = new AutenticadorBean();
		Usuario me = autenticador.getDadosUsuario();
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		List<Consulta> consultas = new ArrayList<Consulta>();
		for (Consulta consulta : repository.getConsultas(me.getUsuario())) {
			if(consulta.getDiagnostico() != null){
				consultas.add(consulta);
			}
		}
		
		return consultas;
	}
	
	public List<Consulta> getMyConsultasWithoutPrescription() {
		AutenticadorBean autenticador = new AutenticadorBean();
		Usuario me = autenticador.getDadosUsuario();
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		List<Consulta> consultas = new ArrayList<Consulta>();
		for (Consulta consulta : repository.getConsultas(me.getUsuario())) {
			if(consulta.getDiagnostico() == null){
				consultas.add(consulta);
			}
		}
		
		return consultas;
	}
	
	public List<Consulta> getTodayConsultas() {
		AutenticadorBean autenticador = new AutenticadorBean();
		Usuario me = autenticador.getDadosUsuario();
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		List<Consulta> consultasHoje = new ArrayList<Consulta>();
		for (Consulta c : repository.getConsultas(me.getUsuario())) {
			if(c.getData_consulta().equals(new Date())) consultasHoje.add(c);
		}
		
		return consultasHoje;
	}
	
	public List<Consulta> getConsultas() {
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		return repository.getConsultas();
	}
	
	public void removeConsulta(Consulta c) {
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		repository.remove(c);
	}
	
	public String medicoReceitar(long id) {
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		this.setConsultaComReceita(repository.getConsulta(id));
		return "medico_receita";
	}
	
	public String receitar() throws ParseException {
		consultaComReceita.setDiagnostico(diagnostico);
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		repository.atualiza(consultaComReceita);
		
		return "medico_consultas";
	}
	
	private EntityManager getEntityManager() {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) ec.getRequest();
		EntityManager manager = (EntityManager) request.getAttribute("EntityManager");
		return manager;
	}

	public Usuario getMedico() {
		return medico;
	}

	public void setMedico(Usuario usuario) {
		this.medico = usuario;
	}

	public Usuario getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuario paciente) {
		this.paciente = paciente;
	}

	public Date getData_consulta() {
		return data_consulta;
	}

	public void setData_consulta(Date data_consulta) {
		this.data_consulta = data_consulta;
	}

	public String getDiagnostico() {
		return diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public List<String> getMedicoDataDisp() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		UsuarioRepository rep = new UsuarioRepository(getEntityManager());
		Medico med = rep.getMedico(medico.getUsuario());
		List<String> str = new ArrayList<String>();
		for (Date dia : med.getPeriodoDisponivel()) {
			str.add(format.format(dia));
		}
		return str;
	}

	public Consulta getConsultaComReceita() {
		return consultaComReceita;
	}

	public void setConsultaComReceita(Consulta consultaComReceita) {
		this.consultaComReceita = consultaComReceita;
	}

}
