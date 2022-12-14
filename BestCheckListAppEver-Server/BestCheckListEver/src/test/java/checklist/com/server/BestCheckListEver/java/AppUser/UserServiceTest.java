package checklist.com.server.BestCheckListEver.java.AppUser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import checklist.com.server.BestCheckListEver.models.AppUser;

import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import checklist.com.server.BestCheckListEver.services.*;
import checklist.com.server.BestCheckListEver.daos.UserDaoImpl;
import checklist.com.server.BestCheckListEver.exceptions.UserNotFoundException;

import static org.mockito.Mockito.*;
import java.util.List;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserDaoImpl userDao;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserServiceImpl underTest;

	@Test
	void canGetUsersById() {
		// given
		AppUser user_1 = new AppUser();

		// when
		when(userDao.getById(anyInt())).thenReturn(user_1);
		AppUser user_2 = underTest.getById(1);

		// then
		assertEquals(user_1, user_2);
		verify(userDao, times(1)).getById(anyInt());
	}

	@Test
	void canGetUsersByIdThrowsException() {
		UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> underTest.getById(-1),
				"Expected .getById(-1) to throw UserNotFoundException, but it didn't.");
		assertTrue(thrown.getMessage().contains("User could not be found"));
	}

	@Test
	void canGetUserByName() {
		// given
		AppUser user_1 = new AppUser();

		// when
		when(userDao.getUserByName(anyString())).thenReturn(user_1);
		AppUser user_2 = underTest.getByName("");

		// then
		assertEquals(user_1, user_2);
		verify(userDao, times(1)).getUserByName(anyString());
	}

	@Test
	void canGetAllUsers() {
		// given
		List<AppUser> list_1 = new ArrayList<AppUser>();
		AppUser user_1 = new AppUser();
		AppUser user_2 = new AppUser();
		AppUser user_3 = new AppUser();

		list_1.add(user_1);
		list_1.add(user_2);
		list_1.add(user_3);

		// when
		when(userDao.getAll()).thenReturn(list_1);
		List<AppUser> list_2 = underTest.getAll();

		// then
		assertEquals(list_1, list_2);
		verify(userDao, times(1)).getAll();
	}

	@Test
	void canSaveUser() {
		// given
		AppUser user_1 = new AppUser();
		// when
		when(userDao.save(any(AppUser.class))).thenReturn(user_1);

		AppUser user_2 = underTest.add("asd", "a");

		System.out.println("user 2: " + user_2);
		// then
		assertEquals(user_1, user_2);
		verify(userDao, times(1)).save(any(AppUser.class));
	}

	@Test
	void canUpdateUser() {
		// given
		AppUser user_1 = new AppUser();

		// when
		when(userDao.update(any(AppUser.class))).thenReturn(user_1);
		when(userDao.getById(anyInt())).thenReturn(new AppUser());
		AppUser user_2 = underTest.update(0, "b", "c");

		// then
		assertEquals(user_1, user_2);
		verify(userDao, times(1)).update(any(AppUser.class));

	}

	@Test
	void canRemoveUser() {
		// given
		AppUser user_1 = new AppUser();

		// when
		when(userDao.getById(anyInt())).thenReturn(user_1);
		when(userDao.delete(any(AppUser.class))).thenReturn(user_1);
		AppUser user_2 = underTest.remove(1);

		// then
		assertEquals(user_1, user_2);
		verify(userDao, times(1)).delete(any(AppUser.class));
		verify(userDao, times(1)).getById(anyInt());
	}

}
