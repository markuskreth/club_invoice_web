package de.kreth.clubinvoice.business;

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
					SECRET_KEY_1.getBytes(StandardCharsets.UTF_8));
			secretKeySpec = new SecretKeySpec(SECRET_KEY_2.getBytes(CHARSET),
					"AES");
			cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
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
	public String encrypt(String toBeEncrypt) {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		}
		catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new EncryptionException("Error initializing ecnryption", e);
		}
		byte[] encrypted;
		try {
			encrypted = cipher.doFinal(toBeEncrypt.getBytes(CHARSET));
		}
		catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new EncryptionException("Error encrypting input", e);
		}
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
	public String decrypt(String encrypted) {
		try {
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		}
		catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			throw new EncryptionException("Error initializing ecnryption", e);
		}
		byte[] decryptedBytes;
		try {
			decryptedBytes = cipher.doFinal(Base64.decodeBase64(encrypted));
		}
		catch (IllegalBlockSizeException | BadPaddingException e) {
			throw new EncryptionException("Error decrypting input", e);
		}
		return new String(decryptedBytes, CHARSET);
	}

	public class EncryptionException extends RuntimeException {

		private static final long serialVersionUID = -6541647856964208706L;

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
