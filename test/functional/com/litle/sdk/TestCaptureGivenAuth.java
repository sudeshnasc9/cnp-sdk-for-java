package com.litle.sdk;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.Calendar;

import org.junit.Test;

import com.litle.sdk.generate.AuthInformation;
import com.litle.sdk.generate.CaptureGivenAuth;
import com.litle.sdk.generate.CaptureGivenAuthResponse;
import com.litle.sdk.generate.CardTokenType;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.Contact;
import com.litle.sdk.generate.FraudResult;
import com.litle.sdk.generate.ProcessingInstructions;
import com.sun.msv.datatype.xsd.ConcreteType;
import com.sun.msv.datatype.xsd.TokenType;
import com.sun.xml.xsom.impl.scd.Token;


public class TestCaptureGivenAuth {

	@Test
	public void simpleCaptureGivenAuthWithCard() throws Exception{
		CaptureGivenAuth capturegivenauth = new CaptureGivenAuth();
		capturegivenauth.setAmount(BigInteger.valueOf(106L));
		capturegivenauth.setOrderId("12344");
		AuthInformation authInfo = new AuthInformation();
		Calendar authDate = Calendar.getInstance();
		authDate.set(2002, Calendar.OCTOBER, 9);
		authInfo.setAuthDate(authDate);
		authInfo.setAuthCode("543216");
		authInfo.setAuthAmount(BigInteger.valueOf(12345L));
		capturegivenauth.setAuthInformation(authInfo);
		capturegivenauth.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		capturegivenauth.setCard(card);
		CaptureGivenAuthResponse response = new LitleOnline().captureGivenAuth(capturegivenauth);
		assertEquals("Approved", response.getMessage());
	}
	
	@Test
	public void simpleCaptureGivenAuthWithToken() throws Exception{
		CaptureGivenAuth capturegivenauth = new CaptureGivenAuth();
		capturegivenauth.setAmount(BigInteger.valueOf(106L));
		capturegivenauth.setOrderId("12344");
		AuthInformation authInfo = new AuthInformation();
		Calendar authDate = Calendar.getInstance();
		authDate.set(2002, Calendar.OCTOBER, 9);
		authInfo.setAuthDate(authDate);
		authInfo.setAuthCode("543216");
		authInfo.setAuthAmount(BigInteger.valueOf(12345L));
		capturegivenauth.setAuthInformation(authInfo);
		capturegivenauth.setOrderSource("ecommerce");
		CardTokenType cardtoken = new CardTokenType();
		cardtoken.setLitleToken("123456789101112");
		cardtoken.setExpDate("1210");
		cardtoken.setCardValidationNum("555");
		cardtoken.setType("VI");
		capturegivenauth.setToken(cardtoken);
		CaptureGivenAuthResponse response = new LitleOnline().captureGivenAuth(capturegivenauth);
		assertEquals("Approved", response.getMessage());
	}
	
	@Test
	public void complexCaptureGivenAuth() throws Exception{
		CaptureGivenAuth capturegivenauth = new CaptureGivenAuth();
		capturegivenauth.setAmount(BigInteger.valueOf(106L));
		capturegivenauth.setOrderId("12344");
		AuthInformation authInfo = new AuthInformation();
		Calendar authDate = Calendar.getInstance();
		authDate.set(2002, Calendar.OCTOBER, 9);
		authInfo.setAuthDate(authDate);
		authInfo.setAuthCode("543216");
		authInfo.setAuthAmount(BigInteger.valueOf(12345L));
		capturegivenauth.setAuthInformation(authInfo);
		Contact contact = new Contact();
		contact.setName("Bob");
		contact.setCity("lowell");
		contact.setState("MA");
		contact.setEmail("litle.com");
		capturegivenauth.setBillToAddress(contact);
		ProcessingInstructions processinginstructions = new ProcessingInstructions();
		processinginstructions.setBypassVelocityCheck(true);
		capturegivenauth.setProcessingInstructions(processinginstructions);
		capturegivenauth.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		capturegivenauth.setCard(card);
		CaptureGivenAuthResponse response = new LitleOnline().captureGivenAuth(capturegivenauth);
		assertEquals("Approved", response.getMessage());
	}
	@Test
	public void authInfo() throws Exception{
		CaptureGivenAuth capturegivenauth = new CaptureGivenAuth();
		capturegivenauth.setAmount(BigInteger.valueOf(106L));
		capturegivenauth.setOrderId("12344");
		AuthInformation authInfo = new AuthInformation();
		Calendar authDate = Calendar.getInstance();
		authDate.set(2002, Calendar.OCTOBER, 9);
		authInfo.setAuthDate(authDate);
		authInfo.setAuthCode("543216");
		authInfo.setAuthAmount(BigInteger.valueOf(12345L));
		FraudResult fraudresult = new FraudResult();
		fraudresult.setAvsResult("12");
		fraudresult.setCardValidationResult("123");
		fraudresult.setAuthenticationResult("1");
		fraudresult.setAdvancedAVSResult("123");
		authInfo.setFraudResult(fraudresult);
		capturegivenauth.setAuthInformation(authInfo);
		capturegivenauth.setOrderSource("ecommerce");
		CardType card = new CardType();
		card.setType("VI");
		card.setNumber("4100000000000001");
		card.setExpDate("1210");
		capturegivenauth.setCard(card);
		CaptureGivenAuthResponse response = new LitleOnline().captureGivenAuth(capturegivenauth);
		assertEquals("Approved", response.getMessage());
	}

}

