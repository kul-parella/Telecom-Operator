package au.com.telecom.services;

import au.com.telecom.dto.PhoneNumber;
import lombok.Generated;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
/***
 * PhoneNumberService interface.
 * @author  kuladeep.
 */
@Generated
public interface PhoneNumberService {

     Page<PhoneNumber> getAllPhoneNumbers(Pageable pageable);
     List<PhoneNumber> getPhoneNumbersByCustomer(Long customerId);
     boolean activatePhoneNumber(String number);


}
