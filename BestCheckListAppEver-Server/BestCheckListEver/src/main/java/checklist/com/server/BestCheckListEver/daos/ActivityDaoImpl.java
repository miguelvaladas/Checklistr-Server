package checklist.com.server.BestCheckListEver.daos;

import checklist.com.server.BestCheckListEver.models.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import javax.persistence.TypedQuery;

import checklist.com.server.BestCheckListEver.models.Activity;

@Transactional
@Repository
public class ActivityDaoImpl implements ActivityDao {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Activity> getAllUserActivities(String username) {
		TypedQuery<AppUser> idQuery = entityManager
				.createQuery("SELECT user from AppUser user WHERE user.name = :username", AppUser.class);
		idQuery.setParameter("username", username);
		AppUser user = idQuery.getSingleResult();
		TypedQuery<Activity> query = entityManager
				.createQuery("SELECT activity from Activity activity WHERE activity.user = :user", Activity.class);
		query.setParameter("user", user);
		return query.getResultList();
	}

	public Activity getById(Integer id) {
		return entityManager.find(Activity.class, id);
	}

	public List<Activity> getAll() {

		TypedQuery<Activity> query = entityManager.createQuery("SELECT t from activities t", Activity.class);

		List<Activity> list = query.getResultList();
		return list;
	}

	@Transactional
	public Activity save(Activity activity) {
		entityManager.persist(activity);
		entityManager.flush();
		return activity;
	}

	@Transactional
	public void update(Activity activity) {
		entityManager.merge(activity);
	}

	@Transactional
	public void delete(Activity activity) {
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
