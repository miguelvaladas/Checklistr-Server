package checklist.com.server.BestCheckListEver.services;

import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;

public interface ActivityService {

	List<Activity> getAllActivitiesFromUser(String username);

	Activity getById(Integer id);

	List<Activity> getAll();

	void updateActivity(Integer activityId, String description);

	Activity addActivity(String username, String description);

	void remove(Integer activityId);

}
