package au.com.telecom.repositories;

import au.com.telecom.dto.PhoneNumber;
import au.com.telecom.entities.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/***
 * Basic JPA repository interface for CURD ops.
 * @author  kuladeep.
 */
@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {

    Optional<Object> findByNumber(String number);

}

