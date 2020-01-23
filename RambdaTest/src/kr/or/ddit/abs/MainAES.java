package kr.or.ddit.abs;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainAES {
	public static void main(String[] args) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		String key ="1234567820!!!!!!";
		AES256Util aes256 =new AES256Util(key);
		
		String text = "한나야 이게 AES256을 쓴 암호화라는거야. 알았니";
		String encText = aes256.aesEncode(text);
		String decText = aes256.aesDecode(encText);
		
		System.out.println("암호화할 문자 : " + text);
		System.out.println("암호화된 문자 : " + encText);
		System.out.println("복호화된 문자 : " + decText);
		
	}
	
}
