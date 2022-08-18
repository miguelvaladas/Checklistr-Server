package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;
import org.springframework.http.HttpStatus;

import checklist.com.server.BestCheckListEver.services.ActivitiesService;
import checklist.com.server.BestCheckListEver.services.UsersService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
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
