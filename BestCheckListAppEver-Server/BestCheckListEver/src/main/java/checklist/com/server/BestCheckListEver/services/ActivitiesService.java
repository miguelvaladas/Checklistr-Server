package checklist.com.server.BestCheckListEver.services;
import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.daos.ActivityDao;
import java.util.List;

public class ActivitiesService implements Service<Activity> {

	private ActivityDao activityDao;

	public Activity getById(Integer id){
		return activityDao.getById(id);
	}

	public List<Activity> getAll(){
		return activityDao.getAll();
	}

	public void update(Activity activity){
		activityDao.update(activity);
	

	public void add(Activity activity){
		activityDao.save(activity);
	}

	public void remove(Activity activity){
		activityDao.delete(activity);
	}

	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}
}
