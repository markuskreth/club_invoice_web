package de.kreth.clubinvoice.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class UserTest<T extends InvoiceEntity> {

	@ParameterizedTest
	@MethodSource("createEntities")
	void testDataEquals(T testData) throws CloneNotSupportedException {
		assertEquals(testData, testData);
		assertNotEquals(testData, null);
		assertNotEquals(testData, "");
		T clone = makeClone(testData);

		assertNotSame(testData, clone);
		assertEquals(testData, clone);
		assertEquals(testData.hashCode(), clone.hashCode());
	}

	@ParameterizedTest
	@MethodSource("createUser")
	void testUserNotEquals(User testData) throws CloneNotSupportedException {

		assertNotEquals(testData, null);
		User clone = makeClone(testData);
		clone.setId(testData.getId());
		clone.setLoginName("login");
		assertNotEquals(testData, clone);
	}

	@ParameterizedTest
	@MethodSource("createUser")
	void testUserToString(User testData) {
		String string = testData.toString();
		assertTrue(string.contains(testData.getLoginName()));
		assertFalse(string.contains(testData.getPassword()));
	}

	@SuppressWarnings({ "unchecked" })
	private static <T extends InvoiceEntity> Stream<T> createEntities() {
		TestData data = new TestData();
		return (Stream<T>) Stream.of(data.user, data.adress, data.bank, data.article, data.invoice, data.item);
	}

	private static Stream<User> createUser() {
		TestData data = new TestData();
		return Stream.of(data.user);
	}

	@SuppressWarnings("unchecked")
	<O extends InvoiceEntity> O makeClone(O obj) {
		if (obj instanceof User) {
			return (O) new User((User) obj);
		}
		else if (obj instanceof UserAdress) {
			return (O) new UserAdress((UserAdress) obj);
		}
		else if (obj instanceof UserBank) {
			return (O) new UserBank((UserBank) obj);
		}
		else if (obj instanceof Article) {
			return (O) new Article((Article) obj);
		}
		else if (obj instanceof Invoice) {
			return (O) new Invoice((Invoice) obj);
		}
		else if (obj instanceof InvoiceItem) {
			return (O) new InvoiceItem((InvoiceItem) obj);
		}
		else {
			throw new IllegalArgumentException("Type not cloneable: " + obj.getClass());
		}
	}
}
