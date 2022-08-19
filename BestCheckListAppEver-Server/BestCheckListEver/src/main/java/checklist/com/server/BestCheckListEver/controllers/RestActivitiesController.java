package checklist.com.server.BestCheckListEver.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.services.ActivitiesService;
import checklist.com.server.BestCheckListEver.services.UsersService;

@CrossOrigin ( origins = "*", maxAge = 3600 )
@RestController
@RequestMapping(method = RequestMethod.GET, path = "/checklist")
public class RestActivitiesController {

	private ActivitiesService activitiesService;
	private UsersService usersService;

	@RequestMapping(method = RequestMethod.GET, path = "/activities")
	public ResponseEntity<List<Activity>> showAllActivities(){
		List<Activity> list = activitiesService.getAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}/activities")
	public ResponseEntity<List<Activity>> getActivitiesFromUser(@PathVariable Integer id){
		List<Activity> list = activitiesService.getAllActivitiesFromUser(id);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path ="/activities/add/desc={description}/user={userId}")
	public ResponseEntity<HttpStatus> addActivity(@PathVariable String description, @PathVariable Integer userId){
		activitiesService.addActivity(userId,description);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.PUT, path="/activities/update/id={activityId}&desc={description}")
	public ResponseEntity<HttpStatus> updateActivity(@PathVariable Integer activityId, @PathVariable String description){
		activitiesService.updateActivity(activityId,description);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, path = "/activities/delete/id={activityId}")
	public ResponseEntity<HttpStatus> deleteActivity(@PathVariable Integer activityId){
		activitiesService.remove(activityId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Autowired
	public void setActivitiesService(ActivitiesService activitiesService) {
		this.activitiesService = activitiesService;
	}

	public ActivitiesService getActivitiesService() {
		return activitiesService;
	}

	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public UsersService getUsersService() {
		return usersService;
	}
}
