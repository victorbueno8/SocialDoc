package socialdoc.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import socialdoc.model.Medico;
import socialdoc.model.Paciente;
import socialdoc.model.Usuario;

public class UsuarioRepository {
	private EntityManager manager;
	
	public UsuarioRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public void adiciona(Usuario usuario) {
		manager.persist(usuario);
	}
	
	public void atualiza(Usuario usuario){
		manager.merge(usuario);
	}
	
	public void remove(Usuario usuario){
		manager.remove(manager.merge(usuario));
	}
	
	public Usuario getUsuario(String usuario){
		return (Usuario) manager.find(Usuario.class, usuario);
	}
	
	public boolean verificaUsuario(String usuario, String password) {
		Query query = manager.createQuery("select c from Usuario c where c.usuario = ?1 and c.password = ?2");
		query.setParameter(1, usuario);
		query.setParameter(2, password);
		try {
			query.getSingleResult();
			return true;
		} catch(NoResultException e) {
			return false;
		}
	}
	
	public String getTipoUsuario(String usuario){
		Query query = manager.createQuery("select c from Usuario c where c.usuario = ?1");
		query.setParameter(1, usuario);
		try {
			return query.getSingleResult().getClass().getSimpleName();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public Medico getMedico(String usuario) {
		return manager.find(Medico.class, usuario);
	}
	
	public List<Medico> getMedicos() {
		Query query = manager.createQuery("select c from Medico c",Medico.class);
		try {
			return query.getResultList();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	public Paciente getPaciente(String usuario) {
		return manager.find(Paciente.class, usuario);
	}
	
	public List<Paciente> getPacientes() {
		Query query = manager.createQuery("select c from Paciente c", Paciente.class);
		try {
			return query.getResultList();
		} catch(NoResultException e) {
			return null;
		}
	}
	
}
