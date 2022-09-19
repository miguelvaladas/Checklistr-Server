package checklist.com.server.BestCheckListEver.java.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import checklist.com.server.BestCheckListEver.models.*;
import checklist.com.server.BestCheckListEver.services.*;
import checklist.com.server.BestCheckListEver.daos.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ActivityServiceTest {

	@InjectMocks
	private ActivityServiceImpl underTest;

	@Mock
	private ActivityDao activityDao;

	@Mock
	private UserServiceImpl userService;

	@Test
	void canGetAllActivitiesFromUser() {
		// given
		Activity activity_1 = new Activity();
		Activity activity_2 = new Activity();
		Activity activity_3 = new Activity();
		List<Activity> list = new ArrayList<>();
		list.add(activity_1);
		list.add(activity_2);
		list.add(activity_3);

		// when

		when(userService.getByName(anyString())).thenReturn(new AppUser());
		when(activityDao.getAllUserActivities(anyString())).thenReturn(list);
		List<Activity> list_2 = underTest.getAllActivitiesFromUser("");

		// then
		assertEquals(list, list_2);
		verify(activityDao, times(1)).getAllUserActivities(anyString());
	}

}
