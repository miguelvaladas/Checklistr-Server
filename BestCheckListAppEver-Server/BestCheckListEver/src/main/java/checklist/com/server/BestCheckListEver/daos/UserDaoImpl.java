package checklist.com.server.BestCheckListEver.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager entityManager;

	public AppUser getById(Integer id) {
		return entityManager.find(AppUser.class, id);
	}

	public AppUser getUserByName(String name) {
		TypedQuery<AppUser> query = entityManager.createQuery("SELECT t from AppUser t WHERE t.name = :name",
				AppUser.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

	public List<AppUser> getAll() {
		TypedQuery<AppUser> query = entityManager.createQuery("SELECT t from AppUser t", AppUser.class);
		List<AppUser> list = query.getResultList();
		return list;
	}

	@Transactional
	public AppUser save(AppUser user) {
		entityManager.persist(user);
		entityManager.flush();
		return user;
	}

	@Transactional
	public void update(AppUser user) {
		entityManager.merge(user);
	}

	@Transactional
	public void delete(AppUser user) {
		entityManager.remove(user);
	}

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
