package checklist.com.server.BestCheckListEver.daos;

import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;

public interface ActivityDao {

	List<Activity> getAllUserActivities(String username);

	Activity getById(Integer id);

	List<Activity> getAll();

	Activity save(Activity activity);

	Activity update(Activity Activity);

	Activity delete(Activity Activity);

}
