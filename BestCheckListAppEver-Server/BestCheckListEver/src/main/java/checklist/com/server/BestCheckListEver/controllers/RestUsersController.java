package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import checklist.com.server.BestCheckListEver.models.*;
import org.springframework.http.HttpStatus;
import checklist.com.server.BestCheckListEver.services.ActivitiesService;
import checklist.com.server.BestCheckListEver.services.UsersService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping (method = RequestMethod.GET, path = "/api")
public class RestUsersController {

	private UsersService usersService;	
	private ActivitiesService activitiesService;

	@RequestMapping(method = RequestMethod.GET, path = "/admin/users")
	@ResponseBody
	public ResponseEntity<List<AppUser>> showAllUsers(){
		List<AppUser> list = usersService.getAll();
		return ResponseEntity.ok().body(list);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "users/login")
	public ResponseEntity<Integer> loginUser(@RequestBody String response) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, String> map = objectMapper.readValue(response, HashMap.class);
		System.out.println(map);
		System.out.println("username: " + map.get("username"));
		System.out.println("password: " + map.get("password"));
		Integer token = usersService.loginUser(map.get("username"), map.get("password"));
		return new ResponseEntity<>(token,HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/users")
	public ResponseEntity<HttpStatus> addUser(@RequestBody String response) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(response, Map.class);
		System.out.println("Username: " + map.get("username"));

		usersService.add(map.get("username"), map.get("password"));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, path = "/users")
	public ResponseEntity<HttpStatus> deleteUser(@RequestBody String response) throws JsonProcessingException, JsonMappingException{	
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Integer> map = objectMapper.readValue(response, Map.class);
		usersService.remove(map.get("id"));
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.PUT, path = "/users")
	public ResponseEntity<HttpStatus> updateUser(@RequestBody String response) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, ?> map = objectMapper.readValue(response, HashMap.class);	
		usersService.update((Integer) map.get("id"), (String) map.get("username"), (String) map.get("password"));
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
