package de.kreth.clubinvoice.business;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import de.kreth.clubinvoice.data.User;

public class UserTestBase extends AbstractTestDatabaseSession {

	protected static LocalDateTime now;
	CookieStore cookies;
	protected UserRegister userBusiness;

	@BeforeAll
	static void createNow() {
		now = LocalDateTime.now();
	}

	@BeforeEach
	void setUp() throws Exception {
		cookies = mock(CookieStore.class);
		userBusiness = new UserRegister(session, propStore, cookies);
	}

	public void insertUsers(final int count) {
		for (int i = 1; i < count + 1; i++) {
			User u1 = createUserIncrement(i);
			userBusiness.save(u1);
			assertTrue(u1.getId() > 0);
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
