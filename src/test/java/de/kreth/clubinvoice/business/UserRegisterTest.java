package de.kreth.clubinvoice.business;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.clubinvoice.data.User;
import de.kreth.clubinvoice.testutils.MockPropertyStore;

class UserRegisterTest extends AbstractTestDatabaseSession {

	private static LocalDateTime now;
	MockPropertyStore propStore;
	CookieStore cookies;
	private UserRegister userBusiness;

	@BeforeAll
	static void createNow() {
		now = LocalDateTime.now();
	}

	@BeforeEach
	void setUp() throws Exception {
		propStore = new MockPropertyStore();
		cookies = mock(CookieStore.class);
		session = sessionFactory.openSession();
		userBusiness = new UserRegister(session, propStore, cookies);
	}

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
		insertUsers(2);
		Map<String, Object> values = propStore.getValues();
		assertEquals(1, values.size());
		assertTrue(values.keySet().contains(PropertyStore.LOGGED_IN_USER));

		verify(cookies, times(2)).store(eq(CookieStore.USER_NAME), anyString());
		verify(cookies, times(2)).store(eq(CookieStore.PASSWORD), anyString());
		List<User> allUsers = userBusiness.loadAll();
		assertEquals(2, allUsers.size());
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

	public void insertUsers(final int count) {
		for (int i = 1; i < count + 1; i++) {
			User u1 = createUserIncrement(i);
			userBusiness.save(u1);
		}
	}

	public User createUserIncrement(int i) {
		User u1 = new User();
		u1.setLoginName("test_" + i);
		u1.setPrename("Test_" + i);
		u1.setSurname("Test_" + i);
		u1.setPassword("test_" + i);
		u1.setChangeDate(now);
		u1.setCreatedDate(now);
		return u1;
	}

}
