package socialdoc.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import socialdoc.model.Administrador;

public class AdminRepository {
	private EntityManager manager;
	
	public AdminRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public void generateAdmin(Administrador administrador) {
		manager.persist(administrador);
	}
	
	public void update(Administrador administrador) {
		manager.merge(administrador);
	}
	
	public boolean verificaAdmin(String password) {
		Query query = manager.createQuery("select c from Administrador c where c.password = ?1");
		query.setParameter(1, password);
		try {
			query.getSingleResult();
			return true;
		} catch(NoResultException e) {
			return false;
		}
	}
	
	public boolean existsAdmin() {
		Query query = manager.createQuery("select c from Administrador c");
		try {
			query.getSingleResult();
			return true;
		} catch(NoResultException e) {
			return false;
		}
	}
	
	public Administrador getAdmin(){
		return manager.find(Administrador.class, "admin");
	}
	
}
