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
import checklist.com.server.BestCheckListEver.services.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(method = RequestMethod.GET, path = "/api")
public class RestActivitiesController {

	private ActivityService activityService;
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, path = "/admin/activities")
	public ResponseEntity<List<Activity>> showAllActivities() {
		List<Activity> list = activityService.getAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/activities/username={username}")
	public ResponseEntity<List<Activity>> getActivitiesFromUser(@PathVariable String username) {
		List<Activity> list = activityService.getAllActivitiesFromUser(username);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/activities")
	public ResponseEntity<Activity> addActivity(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, String> map = objectMapper.readValue(request, HashMap.class);
		Activity activity = activityService.addActivity(map.get("username"), map.get("description"));
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.PUT, path = "/activities")
	public ResponseEntity<HttpStatus> updateActivity(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, ?> map = objectMapper.readValue(request, HashMap.class);
		activityService.updateActivity((Integer) map.get("id"), (String) map.get("description"));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, path = "/activities")
	public ResponseEntity<Activity> deleteActivity(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, Integer> map = objectMapper.readValue(request, HashMap.class);
		Activity activity = activityService.remove(map.get("id"));
		return new ResponseEntity<>(activity, HttpStatus.OK);
	}

	@Autowired
	public void setActivityService(ActivityServiceImpl activityService) {
		this.activityService = activityService;
	}

	public ActivityService getActivityService() {
		return activityService;
	}

	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
}
