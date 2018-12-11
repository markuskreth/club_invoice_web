package de.kreth.clubinvoice.data;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class UserTest<T extends BaseEntity<T>> {

	@ParameterizedTest
	@MethodSource("createEntities")
	void testDataEquals(T testData) throws CloneNotSupportedException {
		assertEquals(testData, testData);
		assertNotEquals(testData, null);
		assertNotEquals(testData, "");
		T clone = testData.clone();

		assertNotSame(testData, clone);
		assertEquals(testData, clone);
		assertEquals(testData.hashCode(), clone.hashCode());
	}

	@ParameterizedTest
	@MethodSource("createUser")
	void testUserNotEquals(User testData) throws CloneNotSupportedException {

		assertNotEquals(testData, null);
		User clone = testData.clone();
		clone.setId(testData.getId());
		clone.setLoginName("login");
		assertNotEquals(testData, clone);
	}

	@ParameterizedTest
	@MethodSource("createUser")
	void testUserToString(User testData) {
		assertTrue(testData.toString().contains(testData.getLoginName()));
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private static <T extends BaseEntity<T>> Stream<T> createEntities() {
		TestData data = new TestData();
		return (Stream<T>) Stream.of(data.user, data.adress, data.bank, data.article, data.invoice, data.item);
	}

	@SuppressWarnings("unused")
	private static Stream<User> createUser() {
		TestData data = new TestData();
		return Stream.of(data.user);
	}
}
