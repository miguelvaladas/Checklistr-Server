package checklist.com.server.BestCheckListEver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.services.ActivitiesService;

@RequestMapping(path = "/app")
@CrossOrigin ( origins = "*", maxAge = 3600 )
@RestController
public class RestActivitiesController {

	private ActivitiesService activitiesService;

	//Quero um GET ALL activities from User e devolve uma lista
	
	@RequestMapping(method = RequestMethod.GET, path = "/activities")
	public ResponseEntity<List<Activity>> showAllActivities(){
		List<Activity> list = activitiesService.getAll();

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@Autowired
	public void setActivitiesService(ActivitiesService activitiesService) {
		this.activitiesService = activitiesService;
	}

	public ActivitiesService getActivitiesService() {
		return activitiesService;
	}

}
