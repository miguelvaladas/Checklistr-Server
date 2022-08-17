package checklist.com.server.BestCheckListEver.daos;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import checklist.com.server.BestCheckListEver.models.Activity;

public class ActivityDao implements Dao<Activity> {

	private EntityManager entityManager;


	public Activity getById(Integer id){
		return entityManager.find(Activity.class,id);
	}

	public List<Activity> getAll(){

		TypedQuery<Activity> query = entityManager.createQuery("SELECT t from activities t", Activity.class);

		List<Activity> list = query.getResultList();
		return list;
	}

	public void save(Activity activity){
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			entityManager.persist(activity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
	}

	public void update(Activity activity){
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			entityManager.merge(activity);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		}
	}

	public void delete(Activity activity){
		EntityTransaction tx = entityManager.getTransaction();

		try {
			tx.begin();
			entityManager.remove(activity);
			tx.commit();
			
		} catch (Exception e) {
			tx.rollback();
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
