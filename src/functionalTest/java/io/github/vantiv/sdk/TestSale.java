package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;

//import com.cnp.sdk.generate.*;
import io.github.vantiv.sdk.generate.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSale {

	private static CnpOnline cnp;

	@BeforeClass
	public static void beforeClass() throws Exception {
		cnp = new CnpOnline();
	}
	
	@Test
	public void simpleSaleWithCard() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void simpleSaleWithBusinessIndicator() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		sale.setBusinessIndicator(BusinessIndicatorEnum.CONSUMER_BILL_PAYMENT);
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}
	
	@Test
	public void simpleSaleWithCardError() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4165851242543100");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Processing Network Unavailable", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void simpleSaleWithCardError2() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4165851242543850");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Tax Billing only allowed for MCC 9311", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}
	
	@Test
	public void simpleSaleWithPayPal() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		PayPal paypal = new PayPal();
		paypal.setPayerId("1234");
		paypal.setToken("1234");
		paypal.setTransactionId("123456");
		sale.setPaypal(paypal);
	    sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}
	
	@Test
    public void simpleSaleWithApplepayAndSecondaryAmount() throws Exception{
        Sale sale = new Sale();
        sale.setAmount(110L);
        sale.setSecondaryAmount(20L);
        sale.setCnpTxnId(123456L);
        sale.setOrderId("12347");
        sale.setOrderSource(OrderSourceType.ECOMMERCE);

        ApplepayType applepayType = new ApplepayType();
        ApplepayHeaderType applepayHeaderType = new ApplepayHeaderType();
        applepayHeaderType.setApplicationData("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setEphemeralPublicKey("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setPublicKeyHash("e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855");
        applepayHeaderType.setTransactionId("1234");
        applepayType.setHeader(applepayHeaderType);
        applepayType.setData("user");
        applepayType.setSignature("sign");
        applepayType.setVersion("12345");

        sale.setApplepay(applepayType);
        sale.setId("id");
        SaleResponse response = cnp.sale(sale);
        assertEquals("Insufficient Funds", response.getMessage());
        assertEquals(new Long(110),response.getApplepayResponse().getTransactionAmount());
		assertEquals("sandbox", response.getLocation());
    }
	
	@Test
	public void simpleSaleWithToken() throws Exception {
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardTokenType token = new CardTokenType();
		token.setCardValidationNum("349");
		token.setExpDate("1214");
		token.setCnpToken("1111222233334000");
		token.setType(MethodOfPaymentTypeEnum.VI);
		sale.setToken(token);
	    sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}



	@Test
	public void simpleSaleWithPinlessDebitRequest() throws Exception {
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardTokenType token = new CardTokenType();
		token.setCardValidationNum("349");
		token.setExpDate("1214");
		token.setCnpToken("1111222233334000");
		token.setType(MethodOfPaymentTypeEnum.VI);
		sale.setToken(token);
		sale.setId("id");
		PinlessDebitRequestType pinlessDebitRequest = new PinlessDebitRequestType();
		pinlessDebitRequest.setRoutingPreference(RoutingPreferenceEnum.REGULAR);
		PreferredDebitNetworksType preferredDebitNetwork = new PreferredDebitNetworksType();
		preferredDebitNetwork.getDebitNetworkNames().add("Visa");
		pinlessDebitRequest.setPreferredDebitNetworks(preferredDebitNetwork);
		sale.setPinlessDebitRequest(pinlessDebitRequest);

		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}
	
	@Test
	public void testSaleWithSEPA() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		SepaDirectDebitType sepa = new SepaDirectDebitType();
		sepa.setIban("ZZ79850503003100180568");
		sepa.setMandateProvider("Vantiv");
		sepa.setSequenceType("OneTime");
		sepa.setMandateUrl("http://mandate.url");
		sale.setSepaDirectDebit(sepa);
		sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("http://redirect.url.vantiv.com", response.getSepaDirectDebitResponse().getRedirectUrl());
		assertEquals("sandbox", response.getLocation());
	}
	
	@Test
	public void testSaleWithProcessingTypeAndOrigTxnIdAndAmount() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		sale.setProcessingType(ProcessingTypeEnum.INITIAL_INSTALLMENT);
		sale.setOriginalNetworkTransactionId("1029384756");
		sale.setOriginalTransactionAmount(4242l);
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void testSaleWithProcessingTypeCOF() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		sale.setProcessingType(ProcessingTypeEnum.INITIAL_COF);
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());

		sale.setProcessingType(ProcessingTypeEnum.MERCHANT_INITIATED_COF);
		response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());

		sale.setProcessingType(ProcessingTypeEnum.CARDHOLDER_INITIATED_COF);
		response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void testSaleWithIdeal() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		sale.setId("id");

		IdealType ideal = new IdealType();
		ideal.setPreferredLanguage(CountryTypeEnum.AD);
		sale.setIdeal(ideal);

		SaleResponse response = cnp.sale(sale);

		assertEquals("Approved", response.getMessage());
		assertEquals("http://redirect.url.vantiv.com", response.getIdealResponse().getRedirectUrl());
		assertEquals("jj2d1d372osmmt7tb8epm0a99q", response.getIdealResponse().getRedirectToken());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void testSaleWithGiropay() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		sale.setId("id");

		GiropayType giropay = new GiropayType();
		giropay.setPreferredLanguage(CountryTypeEnum.US);
		sale.setGiropay(giropay);

		SaleResponse response = cnp.sale(sale);

		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void testSaleWithSofort() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		sale.setId("id");

		SofortType sofort = new SofortType();
		sofort.setPreferredLanguage(CountryTypeEnum.US);
		sale.setSofort(sofort);

		SaleResponse response = cnp.sale(sale);

		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void simpleSaleWithCardSkipRealtimeAUTrue() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		sale.setSkipRealtimeAU(true);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}

	@Test
	public void simpleSaleWithCardSkipRealtimeAUFalse() throws Exception{
		Sale sale = new Sale();
		sale.setAmount(106L);
		sale.setCnpTxnId(123456L);
		sale.setOrderId("12344");
		sale.setOrderSource(OrderSourceType.ECOMMERCE);
		sale.setSkipRealtimeAU(false);
		CardType card = new CardType();
		card.setType(MethodOfPaymentTypeEnum.VI);
		card.setNumber("4100000000000000");
		card.setExpDate("1210");
		sale.setCard(card);
		sale.setId("id");
		SaleResponse response = cnp.sale(sale);
		assertEquals("Approved", response.getMessage());
		assertEquals("sandbox", response.getLocation());
	}
}
