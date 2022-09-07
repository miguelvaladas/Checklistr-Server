package checklist.com.server.BestCheckListEver.daos;

import java.util.List;

import checklist.com.server.BestCheckListEver.models.AppUser;

public interface UserDao {

	AppUser getById(Integer id);

	AppUser getUserByName(String name);

	List<AppUser> getAll();

	AppUser save(AppUser user);

	void update(AppUser user);

	void delete(AppUser user);

}
