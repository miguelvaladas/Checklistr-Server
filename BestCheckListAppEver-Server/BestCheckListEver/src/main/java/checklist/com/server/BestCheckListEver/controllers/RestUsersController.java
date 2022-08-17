package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;
import org.springframework.http.HttpStatus;
import checklist.com.server.BestCheckListEver.services.UsersService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Transactional
public class RestUsersController {

	private UsersService usersService;	
	
	@RequestMapping(method = RequestMethod.GET, path = "/users")
	@ResponseBody
	public ResponseEntity<List<User>> showAllUsers(){

		List<User> list = usersService.getAll();

		System.out.println("LIST:" + list);

		return new ResponseEntity<>(list,HttpStatus.OK);

	}






	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public UsersService getUsersService() {
		return usersService;
	}
}
