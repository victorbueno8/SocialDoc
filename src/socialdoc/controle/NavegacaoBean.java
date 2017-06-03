package socialdoc.controle;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class NavegacaoBean {
	
	public String goToHome() {
		return "medico_home";
	}
	
	public String goToAgenda() {
		return "medico_agendar";
	}
}
