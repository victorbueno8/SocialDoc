package socialdoc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import socialdoc.model.Consulta;
import socialdoc.model.Usuario;

public class ConsultaRepository {
	private EntityManager manager;
	
	public ConsultaRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public void adiciona(Consulta consulta){
		manager.persist(consulta);
	}
	
	public void atualiza(Consulta consulta) {
		manager.merge(consulta);
	}
	
	public void remove(Consulta consulta){
		manager.remove(manager.merge(consulta));
	}
	
	public Consulta getConsulta(long id) {
		return (Consulta) manager.find(Consulta.class, id);
	}
	
	public List<Consulta> getConsultas() {
		Query query = manager.createQuery("select c from Consulta c",Consulta.class);
		try {
			return query.getResultList();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public List<Consulta> getConsultas(String usuario) {
		Query query = manager.createQuery("select c from Consulta c where c.medico.usuario = ?1 or c.paciente.usuario = ?1");
		query.setParameter(1, usuario);
		try {
			return query.getResultList();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public void removeConsulta(Consulta consulta) {
		manager.remove(manager.merge(consulta));
	}
}
