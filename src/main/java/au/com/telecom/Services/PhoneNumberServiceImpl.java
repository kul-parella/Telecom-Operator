package au.com.telecom.Services;

import au.com.telecom.models.PhoneNumber;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/***
 * PhoneNumberService to handle service calls of phoneNumber requests.
 * @author  kuladeep.
 */
@Service
public class PhoneNumberServiceImpl implements  PhoneNumberService{

    private static List<PhoneNumber> phoneNumbers;

    static {
        phoneNumbers = new ArrayList<>();
        phoneNumbers.add(new PhoneNumber("1234567890", false, "123"));
        phoneNumbers.add(new PhoneNumber("9876543210", true, "123"));
        phoneNumbers.add(new PhoneNumber("5556667777", false, "147"));
        phoneNumbers.add(new PhoneNumber("1112223333", true, "147"));
        phoneNumbers.add(new PhoneNumber("4445556666", false, "999"));
    }

    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumbers;
    }


    public List<PhoneNumber> getPhoneNumbersByCustomer(String id) {
        return phoneNumbers.stream()
                .filter(p -> p.getCustomerId().equals(id))
                .collect(Collectors.toList());
    }

    public boolean activatePhoneNumber(String phonNumber) {
        return phoneNumbers.stream()
                .filter(p -> p.getNumber().equals(phonNumber))
                .findFirst()
                .map(p -> {
                    p.setActive(true);
                    return true;
                }).orElse(false);
    }







}
