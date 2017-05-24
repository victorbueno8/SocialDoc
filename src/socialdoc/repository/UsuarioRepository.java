package socialdoc.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import socialdoc.model.Usuario;

public class UsuarioRepository {
	private EntityManager manager;
	
	public UsuarioRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public void adiciona(Usuario usuario) {
		manager.persist(usuario);
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
}
