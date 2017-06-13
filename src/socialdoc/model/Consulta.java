package socialdoc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Consulta {
	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private Usuario medico;
	@ManyToOne
	private Usuario paciente;
	@Temporal(TemporalType.DATE)
	private Date data_consulta;
	private String diagnostico;
	private String observacoes;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Usuario getMedico() {
		return medico;
	}
	public void setMedico(Usuario medico) {
		this.medico = medico;
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
	
}
