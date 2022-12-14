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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import checklist.com.server.BestCheckListEver.exceptions.*;
import checklist.com.server.BestCheckListEver.security.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import checklist.com.server.BestCheckListEver.models.*;

import org.springframework.http.HttpStatus;
import checklist.com.server.BestCheckListEver.services.*;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(method = RequestMethod.GET, path = "/api")
public class RestUsersController {

	private UserService userService;
	private ActivityService activityService;

	@RequestMapping(method = RequestMethod.GET, path = "/admin/users")
	@ResponseBody
	public ResponseEntity<List<AppUser>> showAllUsers() {
		List<AppUser> list = userService.getAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/users")
	public ResponseEntity<AppUser> addUser(@RequestBody String response)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = objectMapper.readValue(response, Map.class);
		System.out.println("Username: " + map.get("username"));
		AppUser user = userService.add(map.get("username"), map.get("password"));
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/users")
	public ResponseEntity<AppUser> deleteUser(@RequestBody String response)
			throws JsonProcessingException, JsonMappingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Integer> map = objectMapper.readValue(response, Map.class);
			AppUser user = userService.remove(map.get("id"));
			return new ResponseEntity<>(user, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/users")
	public ResponseEntity<AppUser> updateUser(@RequestBody String response)
			throws JsonMappingException, JsonProcessingException {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			HashMap<String, ?> map = objectMapper.readValue(response, HashMap.class);
			AppUser user = userService.update((Integer) map.get("id"), (String) map.get("username"),
					(String) map.get("password"));
			return new ResponseEntity<>(user, HttpStatus.OK);

		} catch (UserNotFoundException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(method = RequestMethod.GET, path = "/refresh/token")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			TokenManager tokenManager = new TokenManagerImpl();
			tokenManager.refreshAccessToken(request, response, userService);

		} catch (Exception exception) {
			throw new RuntimeException("Refresh token not found.");
		}
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
