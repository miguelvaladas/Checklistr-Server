package checklist.com.server.BestCheckListEver.daos;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

public class UserDao implements Dao<User> {
	
	private EntityManager entityManager;

	public User getById(Integer id){
		return entityManager.find(User.class,id);
	}

	public List<User> getAll(){
		TypedQuery<User> query = entityManager.createQuery("SELECT t from users t", User.class);

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

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

}
