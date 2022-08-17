package checklist.com.server.BestCheckListEver.services;
import checklist.com.server.BestCheckListEver.models.*;
import java.util.List;
import checklist.com.server.BestCheckListEver.daos.UserDao;

public class UsersService implements Service<User> {

	private UserDao userDao;

	public User getById(Integer id){
		return userDao.getById(id);
	}	

	public List<User> getAll(){
		return userDao.getAll();
	}

	public void update(User user){
		userDao.update(user);
	}

	public void add(User user){
		userDao.save(user);
	}

	public void remove(User user){
		userDao.delete(user);
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}
}
