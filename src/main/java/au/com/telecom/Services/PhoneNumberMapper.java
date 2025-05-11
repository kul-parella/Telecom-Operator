package au.com.telecom.services;

import au.com.telecom.entities.PhoneNumberEntity;
import au.com.telecom.dto.PhoneNumber;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberMapper {

    public PhoneNumber toDto(PhoneNumberEntity entity) {
        return new PhoneNumber(
                entity.getNumber(),
                entity.getActivated()
        );
    }

    public PhoneNumberEntity toEntity(PhoneNumber dto) {
        PhoneNumberEntity e = new PhoneNumberEntity();
        e.setNumber(dto.getNumber());
        e.setActivated(dto.getActivated());
        return e;
    }

    public List<PhoneNumber> toDtoList(List<PhoneNumberEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}