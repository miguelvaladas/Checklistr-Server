package checklist.com.server.BestCheckListEver.services;

import checklist.com.server.BestCheckListEver.models.*;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import checklist.com.server.BestCheckListEver.daos.*;
import checklist.com.server.BestCheckListEver.models.Activity;
import checklist.com.server.BestCheckListEver.exceptions.*;
import javax.transaction.Transactional;

@Service
public class ActivityServiceImpl implements ActivityService {

	private ActivityDao activityDao;
	private UserService userService;

	public List<Activity> getAllActivitiesFromUser(String username) {
		if (userService.getByName(username) == null) {
			throw new UserNotFoundException("User could not be found in database.");
		}
		return activityDao.getAllUserActivities(username);
	}

	public Activity getById(Integer id) {
		Activity activity = activityDao.getById(id);
		if (activity == null) {
			throw new ActivityNotFoundException("Activity could not be found in database through the provided id.");
		}
		return activity;
	}

	public List<Activity> getAll() {
		return activityDao.getAll();
	}

	@Transactional
	public Activity updateActivity(Integer activityId, String description, String status) {
		Activity activity = activityDao.getById(activityId);
		if (activity == null) {
			throw new ActivityNotFoundException("Activity could not be found in database.");
		}
		activity.setDescription(description);
		switch (status) {
			case "DEFAULT":
				activity.setStatus(Status.DEFAULT);
				break;
			case "RED":
				activity.setStatus(Status.RED);
				break;
			case "GREEN":
				activity.setStatus(Status.GREEN);
				break;
			case "YELLOW":
				activity.setStatus(Status.YELLOW);
		}
		return activityDao.update(activity);
	}

	@Transactional
	public Activity addActivity(String username, String description) {
		AppUser user = userService.getByName(username);
		Activity activity = new Activity();
		activity.setUser(user);
		activity.setDescription(description);
		return activityDao.save(activity);
	}

	@Transactional
	public Activity remove(Integer activityId) {
		Activity activity = activityDao.getById(activityId);
		if (activity == null) {
			throw new ActivityNotFoundException("Activity could not be found in database.");
		}
		return activityDao.delete(activity);
	}

	@Autowired
	public void setUsersService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public UserService getUsersService() {
		return userService;
	}

	@Autowired
	public void setActivityDao(ActivityDaoImpl activityDao) {
		this.activityDao = activityDao;
	}

	public ActivityDao getActivityDao() {
		return activityDao;
	}
}
