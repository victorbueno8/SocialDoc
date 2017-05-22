package socialdoc.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = "medico")
public class Medico extends Usuario {
	private String crm;
	private String especialidade;
	@ElementCollection
	private List<Calendar> periodoDisponivel = new ArrayList<Calendar>();
	
	public String getCrm() {
		return crm;
	}
	public void setCrm(String crm) {
		this.crm = crm;
	}
	public String getEspecialidade() {
		return especialidade;
	}
	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
	public List<Calendar> getPeriodoDisponivel() {
		return periodoDisponivel;
	}
}
