package socialdoc.controle;

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

import socialdoc.model.Consulta;
import socialdoc.model.Medico;
import socialdoc.model.Usuario;
import socialdoc.repository.ConsultaRepository;
import socialdoc.repository.UsuarioRepository;

@ManagedBean
public class MarcarConsultaBean {
	private Usuario medico;
	private List<String> dataDisp = new ArrayList<String>();
	private Usuario paciente;
	private String data_consulta;
	private String diagnostico;
	private String observacoes;
	
	public String medicoMarcar(String paciente) {
		AutenticadorBean autenticador = new AutenticadorBean();
		this.setMedico(autenticador.getDadosUsuario());
		UsuarioRepository repository = new UsuarioRepository(getEntityManager());
		this.setPaciente(repository.getPaciente(paciente));
		return "medico_marcar";
	}
	
	public String marcar() throws ParseException {
		Consulta consulta = new Consulta();
		consulta.setMedico(medico);
		consulta.setPaciente(paciente);
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println(data_consulta);
		consulta.setData_consulta(formater.parse(data_consulta));
		consulta.setObservacoes(observacoes);
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		repository.adiciona(consulta);
		
		return "medico_consultas";
	}
	
	public List<Consulta> getMyConsultas() {
		AutenticadorBean autenticador = new AutenticadorBean();
		Usuario me = autenticador.getDadosUsuario();
		
		ConsultaRepository repository = new ConsultaRepository(getEntityManager());
		return repository.getConsultas(me.getUsuario());
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

	public String getData_consulta() {
		return data_consulta;
	}

	public void setData_consulta(String data_consulta) {
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

	public List<String> getDataDisp() {
		return dataDisp;
	}

	public void setDataDisp(List<String> dataDisp) {
		this.dataDisp = dataDisp;
	}

}
