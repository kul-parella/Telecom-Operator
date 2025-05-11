package au.com.telecom.services;

import au.com.telecom.dto.PhoneNumber;
import au.com.telecom.entities.CustomerEntity;
import au.com.telecom.entities.PhoneNumberEntity;
import au.com.telecom.repositories.CustomerRepository;
import au.com.telecom.repositories.PhoneNumberRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/***
 * PhoneNumberService to handle service calls of phoneNumber requests.
 * @author  kuladeep.
 */
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    @Autowired
    private PhoneNumberRepository phoneRepo;

    @Autowired
    private CustomerRepository custRepo;

    @Autowired
    private au.com.telecom.services.PhoneNumberMapper mapper;

    private static final int DEFAULT_PAGE_SIZE = 5;

    /**
     * As per required, this PostConstruct method runs post bean initialization to load the first Default page size
     * phone numbers list into the static helpers entities.
     */
    @PostConstruct
    public void init() {
        Pageable firstFive = PageRequest.of(0, DEFAULT_PAGE_SIZE);
        Page<PhoneNumberEntity> page = phoneRepo.findAll(firstFive);
        List<PhoneNumberEntity> entities = page.getContent();
        PhoneNumberHelper.setEntities(entities);
    }

    @Override
    public Page<PhoneNumber> getAllPhoneNumbers(Pageable pageable) {
        // Use static data only if first page (0) and size matches
        if (pageable.getPageNumber() == 0 && pageable.getPageSize() == 5) {
            List<PhoneNumberEntity> cached = PhoneNumberHelper.getEntities();
            List<PhoneNumber> dtos = cached.stream()
                    .map(mapper::toDto)
                    .toList();
            return new PageImpl<>(dtos, pageable, 5);
        }

        // Fall back to DB for other pages
        Page<PhoneNumberEntity> entityPage = phoneRepo.findAll(pageable);
        return entityPage.map(mapper::toDto);
    }

    @Override
    public boolean activatePhoneNumber(String number) {

        Optional<Object> optionalPhoneNumber = phoneRepo.findByNumber(number);
        if (optionalPhoneNumber.isPresent()) {
            PhoneNumberEntity phoneNumber = (PhoneNumberEntity) optionalPhoneNumber.get();
            phoneNumber.setActivated(true);
            phoneRepo.save(phoneNumber);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<PhoneNumber> getPhoneNumbersByCustomer(Long id) {

        CustomerEntity customer = custRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<PhoneNumberEntity> phoneNumbers = customer.getPhoneNumbers();

        return phoneNumbers.stream()
                .map(this::mapToModel)
                .collect(Collectors.toList());
    }

    private PhoneNumber mapToModel(PhoneNumberEntity entity) {
        return new PhoneNumber(entity.getNumber(), entity.getActivated());
    }


}
