package au.com.telecom.repositories;

import au.com.telecom.entities.CustomerEntity;
import au.com.telecom.entities.PhoneNumberEntity;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/***
 * Basic JPA repository interface for CURD ops.
 * @author  kuladeep.
 */
@Generated
@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {


}

