package au.com.telecom.Services;

import au.com.telecom.models.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {

     List<PhoneNumber> getAllPhoneNumbers();

     List<PhoneNumber> getPhoneNumbersByCustomer(String id);

     boolean activatePhoneNumber(String phonNumber);

}
