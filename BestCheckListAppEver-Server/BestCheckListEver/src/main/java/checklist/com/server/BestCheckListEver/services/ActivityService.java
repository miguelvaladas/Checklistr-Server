package checklist.com.server.BestCheckListEver.services;

import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;

public interface ActivityService {

	List<Activity> getAllActivitiesFromUser(String username);

	Activity getById(Integer id);

	List<Activity> getAll();

	Activity updateActivity(Integer activityId, String description, String status);

	Activity addActivity(String username, String description);

	Activity remove(Integer activityId);

}
