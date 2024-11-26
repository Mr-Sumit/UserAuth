
package com.authentication.util;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.springframework.stereotype.Component;

@Component
public class UserPasswordEncrypter {

	public static String encrypteUserPassword(String reqUserPassword) {
		byte[] salt = new byte[16];
		KeySpec spec = new PBEKeySpec(reqUserPassword.toCharArray(), salt, 256, 128);
		SecretKey sKey = null;
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			sKey = factory.generateSecret(spec);

		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		System.out.println("encrypted Password = " + sKey.getEncoded().toString());
		return sKey.getEncoded().toString();
	}

	public static String decryptUserPassword(String encryptedUserPassword) {
		return encryptedUserPassword;
	}
}
