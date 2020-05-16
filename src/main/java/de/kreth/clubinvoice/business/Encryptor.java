package de.kreth.clubinvoice.business;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {

	private static final Charset CHARSET = StandardCharsets.UTF_8;

	private static final String SECRET_KEY_1 = "mARku21kRe?thHan";

	private static final String SECRET_KEY_2 = "cLUb4rinVoiCE8s8";

	private IvParameterSpec ivParameterSpec;

	private SecretKeySpec secretKeySpec;

	private Cipher cipher;

	public Encryptor() {
		try {
			ivParameterSpec = new IvParameterSpec(
					SECRET_KEY_1.getBytes("UTF-8"));
			secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes(CHARSET),
					"AES");
			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		}
		catch (UnsupportedEncodingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new EncryptionException("Error initializing Encryptor", e);
		}
	}

	/**
	 * Encrypt the string with this internal algorithm.
	 *
	 * @param toBeEncrypt string object to be encrypt.
	 * @return returns encrypted string.
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public String encrypt(String toBeEncrypt)
			throws InvalidAlgorithmParameterException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] encrypted = cipher.doFinal(toBeEncrypt.getBytes(CHARSET));
		return Base64.encodeBase64String(encrypted);
	}

	/**
	 * Decrypt this string with the internal algorithm. The passed argument should be encrypted using
	 * {@link #encrypt(String) encrypt} method of this class.
	 *
	 * @param encrypted encrypted string that was encrypted using {@link #encrypt(String) encrypt} method.
	 * @return decrypted string.
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public String decrypt(String encrypted)
			throws InvalidAlgorithmParameterException, InvalidKeyException,
			BadPaddingException, IllegalBlockSizeException {
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
		return new String(decryptedBytes, CHARSET);
	}

	public class EncryptionException extends RuntimeException {

		public EncryptionException(String message, Throwable cause) {
			super(message, cause);
		}

		public EncryptionException(String message) {
			super(message);
		}

		public EncryptionException(Throwable cause) {
			super(cause);
		}

	}
}
