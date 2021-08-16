package br.com.cvc.banktransfer.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.Locale;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import br.com.cvc.banktransfer.domain.TransferType;
import br.com.cvc.banktransfer.domain.entity.Transfer;
import br.com.cvc.banktransfer.infra.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class TransferControllerTest {

	@Autowired
	private MockMvc mockMvc;

	private TransferDTO request;
	
	private ObjectMapper mapper;
	
	@BeforeEach
	public void doRequest() {
		
		Locale.setDefault(Locale.US);
		
		mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		request = new TransferDTO();
		request.setOriginAccount("672633");
		request.setDestinationAccount("672633");
		request.setValue(new BigDecimal("100"));
		request.setTransferDate(LocalDate.now());
	}	

	@Test
	public void shouldAddOneBookTransferTypeA() throws Exception {
				
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);		

		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.A, transfer.getType());
		Assert.assertTrue(new BigDecimal("6").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeAWithOriginAccountWithValidAlphanumeric() throws Exception {
		request.setOriginAccount("XPTO33");
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.A, transfer.getType());
		Assert.assertTrue(new BigDecimal("6").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeAWithDestionationAccountWithValidAlphanumeric() throws Exception {
		request.setDestinationAccount("233ABC");
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.A, transfer.getType());
		Assert.assertTrue(new BigDecimal("6").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeB() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(2));
		
		URI uri = new URI(TransferController.TRANSFERS);
		MockHttpServletResponse mockResponse = mockMvc.perform(MockMvcRequestBuilders
												.post(uri)
												.content(request.toString())
												.contentType(MediaType.APPLICATION_JSON))
											.andReturn()
											.getResponse();
		

		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.B, transfer.getType());
		Assert.assertTrue(new BigDecimal("24").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeBAnd10Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(10));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.B, transfer.getType());
		Assert.assertTrue(new BigDecimal("120").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCEquals11Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(11));
		
		URI uri = new URI(TransferController.TRANSFERS);
		MockHttpServletResponse mockResponse = mockMvc.perform(MockMvcRequestBuilders
												.post(uri)
												.content(request.toString())
												.contentType(MediaType.APPLICATION_JSON))
											.andReturn()
											.getResponse();
		

		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("8").compareTo(transfer.getFare()) == 0);	
	}

	@Test
	public void shouldAddOneBookTransferTypeCEquals20Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(20));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("8").compareTo(transfer.getFare()) == 0);	
	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCAndDaysBetween11And20() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(15));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("8").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCEquals21Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(21));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("6").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCEquals30Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(30));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("6").compareTo(transfer.getFare()) == 0);	
		
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCAndDaysBetween21And30() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(25));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("6").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCEquals31Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(31));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("4").compareTo(transfer.getFare()) == 0);	
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCEquals40Days() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(40));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("4").compareTo(transfer.getFare()) == 0);	
		
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCAndDaysBetween31And40() throws Exception {
		
		request.setTransferDate(LocalDate.now().plusDays(35));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("4").compareTo(transfer.getFare()) == 0);	
	}

	@Test
	public void shouldAddOneBookTransferTypeCGreaterThan40DaysAndValueGreaterThan100000() throws Exception {
		
		request.setValue(new BigDecimal("100001"));
		request.setTransferDate(LocalDate.now().plusDays(41));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("2000").compareTo(transfer.getFare()) == 0);	
		
	}
	
	@Test
	public void shouldAddOneBookTransferTypeCGreaterThan40DaysAndDecimalValue() throws Exception {
		
		request.setValue(new BigDecimal("100001.37"));
		request.setTransferDate(LocalDate.now().plusDays(41));
		
		MockHttpServletResponse mockResponse = post(new URI(TransferController.TRANSFERS), request);
		
		
		Transfer transfer = extractTransferFrom(mockResponse);
		Assert.assertEquals(TransferType.C, transfer.getType());
		Assert.assertTrue(new BigDecimal("2000").compareTo(transfer.getFare()) == 0);	
		
	}
	
	@Test
	public void shouldAddOneBookTransferWithoutFare() throws Exception {
		
		request.setValue(new BigDecimal("100000"));
		request.setTransferDate(LocalDate.now().plusDays(41));		
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
		
	}
	
	@Test
	public void shouldNotAddOneBookTransferWitInvalidOriginAccount() throws Exception {
		
		request.setOriginAccount("abc1234");
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));		
	}
	
	@Test
	public void shouldNotAddOneBookTransferWitOriginAccountLessThan6Digits() throws Exception {
		
		request.setOriginAccount("ab234");
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));		
	}
	
	@Test
	public void shouldNotAddOneBookTransferWitOriginAccountNull() throws Exception {
		
		request.setOriginAccount(null);	
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));		
	}
	
	@Test
	public void shouldNotAddOneBookTransferWitInvalidDestinationAccount() throws Exception {
		
		request.setDestinationAccount("abc1234");	
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));		
	}
	
	@Test
	public void shouldNotAddOneBookTransferWitDestinationAccountLessThan6Digits() throws Exception {
		
		request.setDestinationAccount("ab234");
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));		
	}
	
	@Test
	public void shouldNotAddOneBookTransferWitDestinationAccountNull() throws Exception {
		
		request.setDestinationAccount(null);	
		
		mockMvc.perform(MockMvcRequestBuilders
				.post(new URI(TransferController.TRANSFERS))
				.content(request.toString())
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));		
	}
	
	@Test
	public void shouldFindOneBookedTransfer() throws Exception {
		
		request.setOriginAccount("333444");		
		
		MockHttpServletResponse mockAddResponse = post(new URI(TransferController.TRANSFERS), request);
		Transfer added = extractTransferFrom(mockAddResponse);
		
		MockHttpServletResponse mockResponse = get(new URI(TransferController.TRANSFERS + "/" + added.getOriginAccount()));
		Transfer transfer = extractTransferFrom(mockResponse);

		Assert.assertNotNull(added);
		Assert.assertNotNull(transfer);
		Assert.assertTrue(transfer.equals(added));	
		
	}
	
	@Test
	public void shouldFindAllBookedTransfer() throws Exception {
	
		MockHttpServletResponse mockResponse = get(new URI(TransferController.TRANSFERS));
		Response response = mapper.readValue(mockResponse.getContentAsString(), Response.class);		
		
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getData());
		Assert.assertTrue(response.getData().size() > 0);
		
	}

	@Test
	public void shouldNotFindABookedTransfer() throws Exception {
		
		request.setOriginAccount("333555");			
		
		mockMvc.perform(MockMvcRequestBuilders
				.get(new URI(TransferController.TRANSFERS + "/" + request.getOriginAccount()))				
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.NO_CONTENT.value()));
		
	}
	
	
	private Transfer extractTransferFrom(MockHttpServletResponse mockResponse)
			throws JsonProcessingException, JsonMappingException, UnsupportedEncodingException {
		Response response = mapper.readValue(mockResponse.getContentAsString(), Response.class);
		Transfer transfer = response.getData().get(0);
		return transfer;
	}
	
	private MockHttpServletResponse post(URI uri, TransferDTO transferDTO) throws Exception {
		MockHttpServletResponse mockResponse = mockMvc.perform(MockMvcRequestBuilders
				.post(uri)
				.content(transferDTO.toString())
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		return mockResponse;
	}
	
	private MockHttpServletResponse get(URI uri) throws Exception {
		MockHttpServletResponse mockResponse = mockMvc.perform(MockMvcRequestBuilders
				.get(uri)				
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		return mockResponse;
	}
	
}
