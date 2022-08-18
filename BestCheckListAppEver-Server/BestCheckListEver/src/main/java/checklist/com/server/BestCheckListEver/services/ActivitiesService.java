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
	private UsersService usersService;

	public List<Activity> getAllActivitiesFromUser(Integer userId){
		return activityDao.getAllUserActivities(userId);
	}

	public Activity getById(Integer id){
		return activityDao.getById(id);
	}

	public List<Activity> getAll(){
		return activityDao.getAll();
	}

	public void update(Activity activity){
		activityDao.update(activity);
	}

	public void updateActivity(Integer activityId, String description){
		Activity activity = activityDao.getById(activityId);
		activity.setDescription(description);
		activityDao.update(activity);
	}

	public void add(Activity activity){
		activityDao.save(activity);
	}

	public void addActivity(Integer userId, String description){
		User user = usersService.getById(userId);
		Activity activity = new Activity();
		activity.setUser(user);
		activity.setDescription(description);
		activityDao.save(activity);
	}

	public void remove(Activity activity){
		activityDao.delete(activity);
	}

	public void remove(Integer activityId){
		Activity activity = activityDao.getById(activityId);
		activityDao.delete(activity);
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	@Autowired
	public void setActivityDao(ActivityDao activityDao) {
		this.activityDao = activityDao;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}
}
