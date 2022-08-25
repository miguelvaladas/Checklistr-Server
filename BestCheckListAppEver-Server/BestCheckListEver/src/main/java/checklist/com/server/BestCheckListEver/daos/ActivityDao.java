package checklist.com.server.BestCheckListEver.daos;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import checklist.com.server.BestCheckListEver.models.Activity;

@Transactional
@Repository
public class ActivityDao implements Dao<Activity> {

	@PersistenceContext
	private EntityManager entityManager;


	public List<Activity> getAllUserActivities(Integer userId){

		TypedQuery<Activity> query = entityManager.createQuery("SELECT activity from Activity activity WHERE activity.user_id = :user_id", Activity.class);
		query.setParameter("user_id", userId);
		return query.getResultList();
	}

	public Activity getById(Integer id){
		return entityManager.find(Activity.class,id);
	}

	public List<Activity> getAll(){

		TypedQuery<Activity> query = entityManager.createQuery("SELECT t from activities t", Activity.class);

		List<Activity> list = query.getResultList();
		return list;
	}

	public void save(Activity activity){
			entityManager.persist(activity);
	}

	public void update(Activity activity){
			entityManager.merge(activity);
	}

	public void delete(Activity activity){
			entityManager.remove(activity);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Autowired
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
