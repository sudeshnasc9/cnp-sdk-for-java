package io.github.vantiv.sdk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import io.github.vantiv.sdk.generate.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Calendar;

public class TestCredit {

    private static CnpOnline cnp;

    @BeforeClass
    public static void beforeClass() throws Exception {
        cnp = new CnpOnline();
    }

    @Test
    public void simpleCreditWithCard() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setOrderId("12344");
        credit.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000001");
        card.setExpDate("1210");
        credit.setCard(card);
        credit.setId("id");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void simpleCreditWithBusinessIndicator() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setOrderId("12344");
        credit.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000001");
        card.setExpDate("1210");
        credit.setCard(card);
        credit.setId("id");
        credit.setBusinessIndicator(BusinessIndicatorEnum.CONSUMER_BILL_PAYMENT);
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void simpleCreditWithPaypal() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setOrderId("123456");
        credit.setOrderSource(OrderSourceType.ECOMMERCE);
        Credit.Paypal paypal = new Credit.Paypal();
        paypal.setPayerId("1234");
        credit.setPaypal(paypal);
        credit.setId("id");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void simpleCreditWithCardAndSecondaryAmount() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setSecondaryAmount(20L);
        credit.setOrderId("12344");
        credit.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000001");
        card.setExpDate("1210");
        credit.setCard(card);
        credit.setId("id");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void simpleCreditWithTxnAndSecondaryAmount() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setSecondaryAmount(20L);
        credit.setCnpTxnId(1234L);
        credit.setId("id");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void simpleCreditWithOrderId() throws Exception {
        Credit credit = new Credit();
        credit.setOrderId("12344");
        credit.setAmount(106L);
        credit.setSecondaryAmount(20L);
        credit.setCnpTxnId(1234L);
        credit.setId("id");

        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void paypalNotes() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setOrderId("12344");
        credit.setPayPalNotes("Hello");
        credit.setOrderSource(OrderSourceType.ECOMMERCE);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000001");
        card.setExpDate("1210");
        credit.setCard(card);
        credit.setId("id");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }

    @Test
    public void processingInstructionAndAmexData() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(2000L);
        credit.setOrderId("12344");
        credit.setOrderSource(OrderSourceType.ECOMMERCE);
        ProcessingInstructions processinginstructions = new ProcessingInstructions();
        processinginstructions.setBypassVelocityCheck(true);
        credit.setProcessingInstructions(processinginstructions);
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000001");
        card.setExpDate("1210");
        credit.setCard(card);
        credit.setId("id");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }
    
    @Test
    public void testCreditWithPin() throws Exception {
        Credit credit = new Credit();
        credit.setAmount(106L);
        credit.setSecondaryAmount(20L);
        credit.setCnpTxnId(1234L);
        credit.setId("id");
        credit.setPin("1234");
        CreditResponse response = cnp.credit(credit);
        assertEquals("Approved", response.getMessage());
        assertEquals("sandbox", response.getLocation());
    }
    @Test
    public  void simpleCreditWithPassengerTransportData() throws Exception{
        Authorization authorization = new Authorization();
        authorization.setReportGroup("русский中文");
        authorization.setOrderId("12344");
        authorization.setAmount(106L);
        authorization.setOrderSource(OrderSourceType.ECOMMERCE);
        authorization.setId("id");
        CardType card = new CardType();
        card.setType(MethodOfPaymentTypeEnum.VI);
        card.setNumber("4100000000000000");
        card.setExpDate("1210");
        authorization.setCard(card);
        authorization.setPassengerTransportData(passengerTransportData());
        AuthorizationResponse response = cnp.authorize(authorization);
        assertEquals("русский中文",response.getReportGroup());
        assertEquals("sandbox", response.getLocation());
    }

    private PassengerTransportData passengerTransportData(){
        PassengerTransportData passengerTransportData = new PassengerTransportData();
        passengerTransportData.setPassengerName("PassengerName");
        passengerTransportData.setTicketNumber("9876543210");
        passengerTransportData.setIssuingCarrier("str4");
        passengerTransportData.setCarrierName("CarrierName");
        passengerTransportData.setRestrictedTicketIndicator("RestrictedIndicator");
        passengerTransportData.setNumberOfAdults(8);
        passengerTransportData.setNumberOfChildren(1);
        passengerTransportData.setCustomerCode("CustomerCode");
        passengerTransportData.setArrivalDate(Calendar.getInstance());
        passengerTransportData.setIssueDate(Calendar.getInstance());
        passengerTransportData.setTravelAgencyCode("TravCode");
        passengerTransportData.setTravelAgencyName("TravelAgencyName");
        passengerTransportData.setComputerizedReservationSystem(ComputerizedReservationSystemEnum.DATS);
        passengerTransportData.setCreditReasonIndicator(CreditReasonIndicatorEnum.C);
        passengerTransportData.setTicketChangeIndicator(TicketChangeIndicatorEnum.C);
        passengerTransportData.setTicketIssuerAddress("IssuerAddress");
        passengerTransportData.setExchangeAmount(new Long(110));
        passengerTransportData.setExchangeFeeAmount(new Long(112));
        passengerTransportData.setExchangeTicketNumber("ExchangeNumber");
        passengerTransportData.getTripLegDatas().add(addTripLegData());
        return  passengerTransportData;
    }

    private TripLegData addTripLegData(){
        TripLegData tripLegData = new TripLegData();
        tripLegData.setTripLegNumber(new BigInteger("4"));
        tripLegData.setDepartureCode("DeptC");
        tripLegData.setCarrierCode("CC");
        tripLegData.setServiceClass(ServiceClassEnum.BUSINESS);
        tripLegData.setStopOverCode("N");
        tripLegData.setDestinationCode("DestC");
        tripLegData.setFareBasisCode("FareBasisCode");
        tripLegData.setDepartureDate(Calendar.getInstance());
        tripLegData.setOriginCity("OCity");
        tripLegData.setTravelNumber("TravN");
        tripLegData.setDepartureTime("DepartureTime");
        tripLegData.setArrivalTime("ArrivalTime");
        tripLegData.setRemarks("Remarks");
        return  tripLegData;
    }
}
