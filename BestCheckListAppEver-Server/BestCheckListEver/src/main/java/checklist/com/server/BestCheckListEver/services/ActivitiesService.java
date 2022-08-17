package checklist.com.server.BestCheckListEver.services;
import checklist.com.server.BestCheckListEver.models.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import checklist.com.server.BestCheckListEver.daos.*;
import checklist.com.server.BestCheckListEver.models.Activity;

@Service
public class ActivitiesService implements checklist.com.server.BestCheckListEver.services.Service <Activity> {

	private ActivityDao activityDao;

	// public List<Activity> getAllActivitiesFromUser(Integer userId){
		
	// }

	public Activity getById(Integer id){
		return activityDao.getById(id);
	}

	public List<Activity> getAll(){
		return activityDao.getAll();
	}

	public void update(Activity activity){
		activityDao.update(activity);
	}	

	public void add(Activity activity){
		activityDao.save(activity);
	}

	public void remove(Activity activity){
		activityDao.delete(activity);
	}

	@Autowired
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}
}
