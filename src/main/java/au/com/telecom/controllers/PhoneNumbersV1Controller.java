package au.com.telecom.controllers;

import java.util.List;
import au.com.telecom.services.PhoneNumberServiceImpl;
import au.com.telecom.dto.PhoneNumber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/***
 * PhoneNumbersV1Controller class handles API class to manage phone numbers of customers.
 * Spec for this controller can be accessed at - http://localhost:8080/swagger-ui/index.html#/
 * @author :Kuladeep Parella
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "Phone Number Management", description = "Manage customer phone numbers")
public class PhoneNumbersV1Controller {

    // Autowired service class to handle service calls for phone Numbers
    @Autowired
    private PhoneNumberServiceImpl service;

    /***
     * Get all phone numbers with pagination.
     * @param pageable page, size, sort criteria
     * optional query params for pagination /api/v1/phone-numbers?page=2&size=50&sort=number,desc
     * @return paged list of Phone Numbers.
     */
    @Operation(summary = "Get all phone numbers (paginated)")
    @GetMapping("/phone-numbers")
    public ResponseEntity<Page<PhoneNumber>> getAllPhoneNumbers(
            @PageableDefault(page = 0, size = 5, sort = "number", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<PhoneNumber> page = service.getAllPhoneNumbers(pageable);
        return ResponseEntity.ok(page);
    }

    /***
     * Get request mapped to handle /customers/{customerId}/phone-numbers to pull customer specific phone numbers.
     * @param customerId Long.
     * @return List of Phone Numbers of customerId passed in input.
     */
    @Operation(summary = "Get phone numbers for a customer")
    @GetMapping("/customers/{customerId}/phone-numbers")
    public List<PhoneNumber> getPhoneNumbersByCustomer(@PathVariable Long customerId) {
        return service.getPhoneNumbersByCustomer(customerId);
    }


    /***
     * Post request mapped to handle /{number}/activate to activate a phone number.
     * @param number String to activate.
     * @return boolean to represent activation status.
     */
    @Operation(summary = "Activate a phone number")
    @PostMapping("/{number}/activate")
    public ResponseEntity<String> activatePhoneNumber(@PathVariable String number) {
        boolean result = service.activatePhoneNumber(number);
        if (result) {
            return ResponseEntity.ok("Phone number activated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Phone number not found");
        }
    }


}

