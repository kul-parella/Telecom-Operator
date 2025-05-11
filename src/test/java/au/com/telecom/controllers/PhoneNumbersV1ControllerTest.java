package au.com.telecom.controllers;

import au.com.telecom.dto.PhoneNumber;
import au.com.telecom.services.PhoneNumberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/***
 * Test class to test PhoneNumbersV1Controller
 *  * @author Kuladeep
 */
@ExtendWith(MockitoExtension.class)
class PhoneNumbersV1ControllerTest {

    @Mock
    private PhoneNumberServiceImpl phoneNumberService;

    @InjectMocks
    private PhoneNumbersV1Controller phoneNumbersV1Controller;

    private PhoneNumber phoneNumber1;
    private PhoneNumber phoneNumber2;
    private Page<PhoneNumber> phoneNumberPage;

    @BeforeEach
    void setUp() {
        phoneNumber1 = new PhoneNumber("0412345678", false);
        phoneNumber2 = new PhoneNumber("0498765432", true);

        List<PhoneNumber> phoneNumbers = Arrays.asList(phoneNumber1, phoneNumber2);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("number"));
        phoneNumberPage = new PageImpl<>(phoneNumbers, pageable, phoneNumbers.size());
    }

    @Test
    void getAllPhoneNumbers_shouldReturnPaginatedResults() {

        when(phoneNumberService.getAllPhoneNumbers(any(Pageable.class))).thenReturn(phoneNumberPage);

        Page<PhoneNumber> result = phoneNumbersV1Controller.getAllPhoneNumbers(0, 5, new String[]{"number,asc"});

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(phoneNumberService, times(1)).getAllPhoneNumbers(any(Pageable.class));
    }

    @Test
    void getPhoneNumbersByCustomer_shouldReturnCustomerNumbers() {

        Long customerId = 1L;
        when(phoneNumberService.getPhoneNumbersByCustomer(customerId))
                .thenReturn(Arrays.asList(phoneNumber1));

        List<PhoneNumber> result = phoneNumbersV1Controller.getPhoneNumbersByCustomer(customerId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(phoneNumber1, result.get(0));
        verify(phoneNumberService, times(1)).getPhoneNumbersByCustomer(customerId);
    }

    @Test
    void activatePhoneNumber_shouldReturnOkWhenSuccessful() {

        String number = "0412345678";
        when(phoneNumberService.activatePhoneNumber(number)).thenReturn(true);

        ResponseEntity<String> response = phoneNumbersV1Controller.activatePhoneNumber(number);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Phone number activated", response.getBody());
        verify(phoneNumberService, times(1)).activatePhoneNumber(number);
    }

    @Test
    void activatePhoneNumber_shouldReturnNotFoundWhenNumberDoesNotExist() {

        String number = "0412345678";
        when(phoneNumberService.activatePhoneNumber(number)).thenReturn(false);

        ResponseEntity<String> response = phoneNumbersV1Controller.activatePhoneNumber(number);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Phone number not found", response.getBody());
        verify(phoneNumberService, times(1)).activatePhoneNumber(number);
    }

    @Test
    void getAllPhoneNumbers_shouldUseDefaultValuesWhenNoneProvided() {

        when(phoneNumberService.getAllPhoneNumbers(any(Pageable.class))).thenReturn(phoneNumberPage);

        Page<PhoneNumber> result = phoneNumbersV1Controller.getAllPhoneNumbers(0, 5, new String[]{"number,asc"});

        assertNotNull(result);
        verify(phoneNumberService).getAllPhoneNumbers(argThat(pageable ->
                pageable.getPageNumber() == 0 &&
                        pageable.getPageSize() == 5 &&
                        pageable.getSort().getOrderFor("number").getDirection().isAscending()
        ));
    }
}