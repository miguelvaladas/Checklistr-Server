package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping (method = RequestMethod.GET, path = "/checklist")
public class RestUsersController {

	private UsersService usersService;	
	private ActivitiesService activitiesService;

	@RequestMapping(method = RequestMethod.GET, path = "/users")
	@ResponseBody
	public ResponseEntity<List<User>> showAllUsers(){
		List<User> list = usersService.getAll();
		System.out.println("LIST:" + list);
		return new ResponseEntity<>(list,HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public ResponseEntity<Integer> loginUser(@RequestBody String response) throws JsonMappingException, JsonProcessingException {


		ObjectMapper objectMapper = new ObjectMapper();
		HashMap<String, String> map = objectMapper.readValue(response, HashMap.class);
		// ver o JWT token para retornar um token a sério. Permite aceder às propriedades gravadas do user através do JWT. Seguro o suff. https://jwt.io/
		System.out.println(map);
		System.out.println("username: " + map.get("username"));
		System.out.println("password: " + map.get("password"));
		Integer token = usersService.loginUser(map.get("username"), map.get("password"));
		return new ResponseEntity<>(token,HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/users/add/name={name}&pw={pw}")
	public ResponseEntity<HttpStatus> addUser(@PathVariable String name, @PathVariable String pw){
		usersService.add(name, pw);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.DELETE, path = "/users/delete/id={id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable Integer id){
		usersService.remove(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Transactional
	@RequestMapping(method = RequestMethod.PUT, path = "/users/update/id={id}&name={name}&pw={pw}")
	public ResponseEntity<HttpStatus> updateUser(@PathVariable Integer userId, @PathVariable String name, @PathVariable String pw){
		usersService.update(userId, name, pw);
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
