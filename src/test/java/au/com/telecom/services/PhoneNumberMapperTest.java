package au.com.telecom.services;

import au.com.telecom.entities.PhoneNumberEntity;
import au.com.telecom.dto.PhoneNumber;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/***
 * Test class to test PhoneNumberMapperTest
 * @author Kuladeep
 */
class PhoneNumberMapperTest {

    private final PhoneNumberMapper mapper = new PhoneNumberMapper();

    @Test
    void testToDto() {
        // Given
        PhoneNumberEntity entity = new PhoneNumberEntity();
        entity.setNumber("0412345678");
        entity.setActivated(true);

        // When
        PhoneNumber dto = mapper.toDto(entity);

        // Then
        assertNotNull(dto);
        assertEquals("0412345678", dto.getNumber());
        assertTrue(dto.getActivated());
    }

    @Test
    void testToEntity() {
        // Given
        PhoneNumber dto = new PhoneNumber("0498765432", false);

        // When
        PhoneNumberEntity entity = mapper.toEntity(dto);

        // Then
        assertNotNull(entity);
        assertEquals("0498765432", entity.getNumber());
        assertFalse(entity.getActivated());
    }

    @Test
    void testToDtoList() {
        // Given
        PhoneNumberEntity entity1 = new PhoneNumberEntity();
        entity1.setNumber("0412345678");
        entity1.setActivated(true);

        PhoneNumberEntity entity2 = new PhoneNumberEntity();
        entity2.setNumber("0498765432");
        entity2.setActivated(false);

        List<PhoneNumberEntity> entities = Arrays.asList(entity1, entity2);

        // When
        List<PhoneNumber> dtos = mapper.toDtoList(entities);

        // Then
        assertNotNull(dtos);
        assertEquals(2, dtos.size());

        assertEquals("0412345678", dtos.get(0).getNumber());
        assertTrue(dtos.get(0).getActivated());

        assertEquals("0498765432", dtos.get(1).getNumber());
        assertFalse(dtos.get(1).getActivated());
    }

    @Test
    void testToDtoListWithEmptyList() {
        // Given
        List<PhoneNumberEntity> emptyList = List.of();

        // When
        List<PhoneNumber> dtos = mapper.toDtoList(emptyList);

        // Then
        assertNotNull(dtos);
        assertTrue(dtos.isEmpty());
    }

    @Test
    void testNullInputHandling() {
        // Test null entity to dto
        assertThrows(NullPointerException.class, () -> mapper.toDto(null));

        // Test null dto to entity
        assertThrows(NullPointerException.class, () -> mapper.toEntity(null));

        // Test null list to dto list
        assertThrows(NullPointerException.class, () -> mapper.toDtoList(null));
    }

    @Test
    void testServiceAnnotation() {
        // Verify the class is annotated with @Service
        assertNotNull(PhoneNumberMapper.class.getAnnotation(org.springframework.stereotype.Service.class));
    }
}
