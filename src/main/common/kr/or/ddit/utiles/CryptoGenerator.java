package kr.or.ddit.utiles;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class CryptoGenerator {
	// 암, 복호화 : 공개키 = 비밀키(생성시 동반 생성, 1회 활용하고 폐기
	// 반환값: 공개키(가수부, 지수부로 구분해서 맵에 저장후 반환) 어디에서 가수부와 지수부를 구분해서 받기를 원한다구?
	// 파라메터 : 비밀키 세션 저장
	public  Map<String, String> genneratePairKey(HttpSession session){
		// 공개키 + 비밀키 생성
		KeyPairGenerator keyGenerator = null;
		
		//취득, 생성된 공개키 + 비밀키
		KeyPair keyPair = null;
		
		// 공개키
		PublicKey publicKey = null;
		
		// 비밀키
		PrivateKey privateKey = null;
		
		// 공개키 = 가수부 + 지수부로 나눠주는 역할
		KeyFactory keyFactory = null;
		
		Map<String, String> publicKeyMap = new HashMap<String, String>();
		
		try {
			//공개키와 비밀키를 생성할 수 있는 키페어 인스턴스를 얻어낸다.
			keyGenerator = KeyPairGenerator.getInstance("RSA"); // RSA라는 암호 알고리즘을 사용하겠다.
			
			//공개키, 비밀키 생성시 사이즈 설정 : byte 단위
			//						      반드시 짝수 설정
			keyGenerator.initialize(2048); // 반드시 짝수로 파마리터 // 어떤 사이즈를 할지 
			
			// 생성된 공개키, 비밀키 취득
			keyPair = keyGenerator.generateKeyPair();
			
			// 공개키 취득
			publicKey = keyPair.getPublic();
			
			// 비밀키 취득
			privateKey = keyPair.getPrivate();
			
			// 비밀키 저장, 세션에 저장
			session.setAttribute("privateKey", privateKey);
			
			// 공개키(Dobule Type) : 가수부와 지수부로 구분해준다. => 클라이언트에 제공 
			// -143.12344556
			// float(32bit 단정도 소수)  : 부호비트 1bit(양수 0 | 음수 1) + 지수 8bit(소수점 자릿수) + 가수 23bit(실수 표현)
			// double(64bit 배정도 소수) : 부호비트 1bit(양수 0 | 음수 1) + 지수 11bit(수수점 자릿수) + 가수 52bit(실수 표현)
			keyFactory = keyFactory.getInstance("RSA");
			
			RSAPublicKeySpec publilcKeySpec = (RSAPublicKeySpec)keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
			
			// 공개키 가수부
			String publicModulus = publilcKeySpec.getModulus().toString(16); // 16진수 로 바꾼다.
			
			// 공개키 지수부   
			String publicExponent = publilcKeySpec.getPublicExponent().toString(16); // ? 여기 몇진수?
			
			publicKeyMap.put("publicModulus", publicModulus);
			publicKeyMap.put("publicExponent", publicExponent);
			
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (InvalidKeySpecException e2) {
			e2.printStackTrace();
		}
		
		return publicKeyMap;
	}
	
	// 암호문을 평문으로 복화
//	public static String decryptRSA(PrivateKey privateKey, String secureValue){
	   public  String decryptRSA(HttpSession session, String secureValue){
//		평문을 반환하도록 변수 선언
		String returnValue = "";
	      PrivateKey privateKey = (PrivateKey) session.getAttribute("privateKey");
		
		// 평문으로 바꿀때 사용
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey); // 복호화 할때 사용하는 것 : Cipher.DECRYPT_MODE
			
			// 암호문은 짝수 단위로 바이너리 코드가 존재한다. -> 평문으로 바꾸기 위한 바이트로 바꿔 버린다.
			byte[] targetByte = hextoByteArray(secureValue);
			
			//Stirng으로 변경하기 전 byte
			byte[] beforeString = cipher.doFinal(targetByte);
			
			returnValue = new String(beforeString, "UTF-8"); // 여기서 편문으로 바뀜 
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return returnValue;
	}

	// 암호문이 널이거나 짝수가 아니면 암호문이 잘못 암호화 된 것이다. 이상한 것 (암호화 에러)
	private  byte[] hextoByteArray(String secureValue) {
		
		// 짝수가 들어오도록 해야함
		if(secureValue == null || secureValue.length() % 2 != 0){
			return new byte[]{};
		}
		
//		두칸씩 접는다?
		byte[] bytes = new byte[secureValue.length()/2];

		for(int i=0; i<secureValue.length(); i+=2){
			byte value = (byte) Integer.parseInt(secureValue.substring(i, i+2), 16);
			bytes[(int) Math.floor(i/2)] = value;
		}
		return bytes;
	}
}
