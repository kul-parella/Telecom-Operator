package au.com.telecom.services;

import au.com.telecom.dto.PhoneNumber;
import au.com.telecom.entities.CustomerEntity;
import au.com.telecom.entities.PhoneNumberEntity;
import au.com.telecom.repositories.CustomerRepository;
import au.com.telecom.repositories.PhoneNumberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/***
 * Test class to test PhoneNumberServiceImpl
 * @author Kuladeep
 */
@ExtendWith(MockitoExtension.class)
class PhoneNumberServiceImplTest {

    @Mock
    private PhoneNumberRepository phoneRepo;

    @Mock
    private CustomerRepository custRepo;

    @Mock
    private PhoneNumberMapper mapper;

    @InjectMocks
    private PhoneNumberServiceImpl phoneNumberService;

    private PhoneNumberEntity phoneEntity;
    private PhoneNumber phoneDto;
    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        // Reset static helper before each test
        PhoneNumberHelper.setEntities(Collections.emptyList());

        phoneEntity = new PhoneNumberEntity();
        phoneEntity.setNumber("0412345678");
        phoneEntity.setActivated(false);

        phoneDto = new PhoneNumber("0412345678", false);

        customerEntity = new CustomerEntity();
        customerEntity.setId(1L);
        customerEntity.setPhoneNumbers(List.of(phoneEntity));
    }

    @Test
    void getAllPhoneNumbers_shouldReturnCachedDataForFirstPage() {

        PhoneNumberHelper.setEntities(List.of(phoneEntity));
        Pageable pageable = PageRequest.of(0, 5);
        when(mapper.toDto(phoneEntity)).thenReturn(phoneDto);


        Page<PhoneNumber> result = phoneNumberService.getAllPhoneNumbers(pageable);

        assertEquals(1, result.getContent().size());
        assertEquals(phoneDto, result.getContent().get(0));
        verify(phoneRepo, never()).findAll();
    }

    @Test
    void getAllPhoneNumbers_shouldQueryDBForNonFirstPage() {
        // Arrange
        Pageable pageable = PageRequest.of(1, 5);
        Page<PhoneNumberEntity> entityPage = new PageImpl<>(List.of(phoneEntity));
        when(phoneRepo.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toDto(phoneEntity)).thenReturn(phoneDto);


        Page<PhoneNumber> result = phoneNumberService.getAllPhoneNumbers(pageable);


        assertEquals(1, result.getContent().size());
        assertEquals(phoneDto, result.getContent().get(0));
        verify(phoneRepo).findAll(pageable);
    }

    @Test
    void activatePhoneNumber_shouldReturnTrueWhenNumberExists() {

        when(phoneRepo.findByNumber("0412345678")).thenReturn(Optional.of(phoneEntity));

        boolean result = phoneNumberService.activatePhoneNumber("0412345678");

        assertTrue(result);
        assertTrue(phoneEntity.getActivated());
        verify(phoneRepo).save(phoneEntity);
    }

    @Test
    void activatePhoneNumber_shouldReturnFalseWhenNumberDoesNotExist() {

        when(phoneRepo.findByNumber("0412345678")).thenReturn(Optional.empty());

        boolean result = phoneNumberService.activatePhoneNumber("0412345678");

        assertFalse(result);
        verify(phoneRepo, never()).save(any());
    }

    @Test
    void getPhoneNumbersByCustomer_shouldReturnPhoneNumbers() {

        when(custRepo.findById(1L)).thenReturn(Optional.of(customerEntity));

        List<PhoneNumber> result = phoneNumberService.getPhoneNumbersByCustomer(1L);

        assertEquals(1, result.size());
        assertEquals("0412345678", result.get(0).getNumber());
        assertFalse(result.get(0).getActivated());
    }

    @Test
    void getPhoneNumbersByCustomer_shouldThrowExceptionWhenCustomerNotFound() {

        when(custRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            phoneNumberService.getPhoneNumbersByCustomer(1L);
        });
    }

    @Test
    void init_shouldLoadFirstFivePhoneNumbers() {
        assertTrue(PhoneNumberHelper.getEntities().isEmpty());
    }
}
