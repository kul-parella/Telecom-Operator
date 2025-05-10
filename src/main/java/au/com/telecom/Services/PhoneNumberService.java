package au.com.telecom.Services;

import au.com.telecom.entities.PhoneNumber;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/***
 * PhoneNumberService to handle service calls of phoneNumber requests.
 * @author  kuladeep.
 */
@Service
public class PhoneNumberService {

    public List<PhoneNumber> getAllPhoneNumbers() {

        return new ArrayList<PhoneNumber>();
    }

    public List<PhoneNumber> getPhoneNumbersByCustomer(Long id) {
        return new ArrayList<>();
    }

    public boolean activatePhoneNumber(String phonNumber) {
        return true;
    }


}
