package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.services.ActivitiesService;
import checklist.com.server.BestCheckListEver.services.UsersService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(method = RequestMethod.GET, path = "/api")
public class RestActivitiesController {

	private ActivitiesService activitiesService;
	private UsersService usersService;

	@RequestMapping(method = RequestMethod.GET, path = "/admin/activities")
	public ResponseEntity<List<Activity>> showAllActivities() {
		List<Activity> list = activitiesService.getAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/activities/username={username}")
	public ResponseEntity<List<Activity>> getActivitiesFromUser(@PathVariable String username) {
		List<Activity> list = activitiesService.getAllActivitiesFromUser(username);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/activities/{description}")
	public ResponseEntity<Activity> getActivityByDescription(@PathVariable String description) {
		Activity activity = activitiesService.getActivityByDescription(description);
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/activities")
	public ResponseEntity<HttpStatus> addActivity(@RequestBody String response)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(response);
		HashMap<String, String> map = objectMapper.readValue(response, HashMap.class);
		activitiesService.addActivity(map.get("username"), map.get("description"));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.PUT, path = "/activities")
	public ResponseEntity<HttpStatus> updateActivity(@RequestBody String response)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, ?> map = objectMapper.readValue(response, HashMap.class);
		activitiesService.updateActivity((Integer) map.get("id"), (String) map.get("description"));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, path = "/activities")
	public ResponseEntity<HttpStatus> deleteActivity(@RequestBody String response)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, Integer> map = objectMapper.readValue(response, HashMap.class);
		activitiesService.remove(map.get("id"));
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
