package socialdoc.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "paciente")
public class Paciente extends Usuario {
	private String numero_sus;
	private String data_nascimento;
	
	public String getNumero_sus() {
		return numero_sus;
	}
	public void setNumero_sus(String numero_sus) {
		this.numero_sus = numero_sus;
	}
	public String getData_nascimento() {
		return data_nascimento;
	}
	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

}
