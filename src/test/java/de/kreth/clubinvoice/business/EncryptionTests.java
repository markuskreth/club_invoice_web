package de.kreth.clubinvoice.business;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EncryptionTests {

	@Test
	void testEncryptor() throws Exception {

		String strClearText = "DeveloperKey";
		Encryptor e = new Encryptor();
		String encrypted = e.encrypt(strClearText);
		assertEquals(strClearText, e.decrypt(encrypted));
	}

	@Test
	void testEncryptorWithSonderzeichen() throws Exception {

		String strClearText = "m!§$%_-üö*ß?";
		Encryptor e = new Encryptor();
		String encrypted = e.encrypt(strClearText);
		assertEquals(strClearText, e.decrypt(encrypted));
	}
}
