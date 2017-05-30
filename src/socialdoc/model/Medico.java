package socialdoc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "medico")
public class Medico extends Usuario {
	private String crm;
	private String especialidade;
	@ElementCollection
	private List<String> periodoDisponivel = new ArrayList<String>();
	
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
	public List<String> getPeriodoDisponivel() {
		return periodoDisponivel;
	}
}
