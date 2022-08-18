package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.services.ActivitiesService;
import checklist.com.server.BestCheckListEver.services.UsersService;

@RequestMapping(path = "/app")
@CrossOrigin ( origins = "*", maxAge = 3600 )
@RestController
public class RestActivitiesController {

	private ActivitiesService activitiesService;
	private UsersService usersService;

	//Quero um GET ALL activities from User e devolve uma lista
	
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
	
	@RequestMapping(method = RequestMethod.POST, path ="/activities/add/desc={description}/user={userId}")
	public ResponseEntity<HttpStatus> addActivity(@PathVariable String description, @PathVariable Integer userId){
		User user = usersService.getById(userId); 

		Activity activity = new Activity();
		activity.setUser(user);
		activity.setDescription(description);
		activitiesService.add(activity);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, path="/activities/update/id={activityId}&des={description}&user={userId}")
	public ResponseEntity<HttpStatus> updateActivity(@PathVariable Integer activityId, @PathVariable String description, @PathVariable Integer userId){
		
		Activity activity = activitiesService.getById(activityId);
		activity.setDescription(description);
		activitiesService.update(activity);
		
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
