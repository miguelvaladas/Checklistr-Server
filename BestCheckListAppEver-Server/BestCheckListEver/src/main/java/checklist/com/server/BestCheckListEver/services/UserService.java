package checklist.com.server.BestCheckListEver.services;

import java.util.List;

import checklist.com.server.BestCheckListEver.models.AppUser;

public interface UserService {

	AppUser getById(Integer id);

	AppUser getByName(String username);

	List<AppUser> getAll();

	AppUser update(Integer userId, String name, String pw);

	AppUser add(String name, String pw);

	AppUser remove(Integer userId);

}
