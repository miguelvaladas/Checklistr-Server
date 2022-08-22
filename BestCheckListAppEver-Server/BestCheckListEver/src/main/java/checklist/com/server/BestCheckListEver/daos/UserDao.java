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
public class UserDao implements Dao<User> {

	@PersistenceContext
	private EntityManager entityManager;

	public User getById(Integer id){
		return entityManager.find(User.class,id);
	}

	public User getUserByNameAndPw(String name, String pw){
		TypedQuery<User> query = entityManager.createQuery("SELECT t from User t WHERE t.name = :name AND t.password = :pw", User.class);
		query.setParameter("name", name);
		query.setParameter("pw", pw);
	return query.getSingleResult();
}

	public List<User> getAll(){
		TypedQuery<User> query = entityManager.createQuery("SELECT t from User t", User.class);

		List<User> list = query.getResultList();
		return list;
	}

	public void save(User user){
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			entityManager.persist(user);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
	}

	public void update(User user){
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			entityManager.merge(user);
			tx.commit();
		} catch (Exception e) {
		tx.rollback();
		}
	}

	public void delete(User user){
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			entityManager.remove(user);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
	}

	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
