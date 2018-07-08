package de.kreth.clubinvoice.business;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.User;

class UserRegisterTest extends UserTestBase {

	@Test
	void testLogin() {
		insertUsers(2);
		for (int i = 1; i < 3; i++) {
			assertTrue(userBusiness.login("test_" + i, "test_" + i),
					"Login failed for \"test_" + i + "\"");
		}
	}

	@Test
	void testSaveUserAndLoadAll() {

		assertEquals(1, userBusiness.loadAll().size());
		insertUsers(2);
		Map<String, Object> values = propStore.getValues();
		assertEquals(1, values.size());
		assertTrue(values.keySet().contains(PropertyStore.LOGGED_IN_USER));

		verify(cookies, times(2)).store(eq(CookieStore.USER_NAME), anyString());
		verify(cookies, times(2)).store(eq(CookieStore.PASSWORD), anyString());
		List<User> allUsers = userBusiness.loadAll();
		assertEquals(3, allUsers.size());
		Set<Integer> userIds = new HashSet<>();
		for (User u : allUsers) {
			userIds.add(u.getId());
		}
		assertEquals(3, userIds.size());
	}

	@Test
	public void testIsLoggedInAfterSave() {
		userBusiness.save(createUserIncrement(2));
		assertTrue(userBusiness.isLoggedIn());
	}

	@Test
	public void testInitiallyNotLogedIn() {
		assertFalse(userBusiness.isLoggedIn());
	}

	@Test
	public void testLoggedInAfterLogin() {
		User user = createUserIncrement(5);
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();

		assertTrue(userBusiness.login(user.getLoginName(), user.getPassword()));
		assertTrue(userBusiness.isLoggedIn());
	}

}
