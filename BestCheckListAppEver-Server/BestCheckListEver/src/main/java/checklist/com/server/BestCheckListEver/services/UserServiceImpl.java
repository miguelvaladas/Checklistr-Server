package checklist.com.server.BestCheckListEver.services;

import checklist.com.server.BestCheckListEver.models.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import checklist.com.server.BestCheckListEver.daos.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserDao userDao;
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userDao.getUserByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found in database.");
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
	}

	public AppUser getById(Integer id) {
		return userDao.getById(id);
	}

	public AppUser getByName(String username) {
		return userDao.getUserByName(username);
	}

	public List<AppUser> getAll() {
		return userDao.getAll();
	}

	public AppUser update(Integer userId, String name, String pw) {
		AppUser user = new AppUser();
		user.setId(userId);
		user.setName(name);
		user.setPassword(passwordEncoder.encode(pw));
		return userDao.save(user);
	}

	public AppUser add(String name, String pw) {
		AppUser user = new AppUser();
		user.setName(name);
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(pw));
		user.setActivitiesList(new ArrayList<Activity>());
		return userDao.save(user);
	}

	public AppUser remove(Integer userId) {
		AppUser user = userDao.getById(userId);
		return userDao.delete(user);
	}

	@Autowired
	public void setUserDao(UserDaoImpl userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public PasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}
}
