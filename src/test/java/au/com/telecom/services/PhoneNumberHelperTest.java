package au.com.telecom.services;

import au.com.telecom.entities.PhoneNumberEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/***
 * Test class to test PhoneNumberHelper
 * @author Kuladeep
 */
class PhoneNumberHelperTest {

    private PhoneNumberEntity phone1;
    private PhoneNumberEntity phone2;
    private List<PhoneNumberEntity> testEntities;

    @BeforeEach
    void setUp() {
        // Initialize test data
        phone1 = new PhoneNumberEntity();
        phone1.setId(1L);
        phone1.setNumber("0412345678");
        phone1.setActivated(true);

        phone2 = new PhoneNumberEntity();
        phone2.setId(2L);
        phone2.setNumber("0498765432");
        phone2.setActivated(false);

        testEntities = Arrays.asList(phone1, phone2);

        // Clear any existing state before each test
        PhoneNumberHelper.setEntities(null);
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        PhoneNumberHelper.setEntities(null);
    }

    @Test
    void testGetEntitiesWhenNotSet() {
        assertNull(PhoneNumberHelper.getEntities(),
                "Should return null when entities not set");
    }

    @Test
    void testSetAndGetEntities() {
        PhoneNumberHelper.setEntities(testEntities);

        List<PhoneNumberEntity> result = PhoneNumberHelper.getEntities();
        assertNotNull(result, "Should not return null after entities are set");
        assertEquals(2, result.size(), "Should return correct number of entities");
        assertEquals(phone1, result.get(0), "First entity should match");
        assertEquals(phone2, result.get(1), "Second entity should match");
    }

    @Test
    void testSetEntitiesWithEmptyList() {
        PhoneNumberHelper.setEntities(Collections.emptyList());

        List<PhoneNumberEntity> result = PhoneNumberHelper.getEntities();
        assertNotNull(result, "Should not return null for empty list");
        assertTrue(result.isEmpty(), "Should return empty list");
    }

    @Test
    void testSetEntitiesWithNull() {
        PhoneNumberHelper.setEntities(testEntities); // First set to non-null
        PhoneNumberHelper.setEntities(null); // Then set to null

        assertNull(PhoneNumberHelper.getEntities(),
                "Should return null after setting to null");
    }


    @Test
    void testThreadSafetyConsideration() throws InterruptedException {
        // This test demonstrates that the implementation is NOT thread-safe
        PhoneNumberHelper.setEntities(testEntities);

        Thread thread = new Thread(() -> {
            PhoneNumberHelper.setEntities(Collections.singletonList(phone1));
        });
        thread.start();
        thread.join();

        assertEquals(1, PhoneNumberHelper.getEntities().size(),
                "Helper class is not thread-safe - state can be changed by other threads");
    }

    @Test
    void testSetEntitiesWithDuplicateNumbers() {
        PhoneNumberEntity duplicatePhone = new PhoneNumberEntity();
        duplicatePhone.setNumber("0412345678"); // Same as phone1

        List<PhoneNumberEntity> duplicates = Arrays.asList(phone1, duplicatePhone);
        PhoneNumberHelper.setEntities(duplicates);

        assertEquals(2, PhoneNumberHelper.getEntities().size(),
                "Should allow duplicates if they're different objects");
    }

    @Test
    void testSetEntitiesWithNullElement() {
        List<PhoneNumberEntity> listWithNull = Arrays.asList(phone1, null, phone2);
        PhoneNumberHelper.setEntities(listWithNull);

        assertEquals(3, PhoneNumberHelper.getEntities().size(),
                "Should handle null elements in the list");
    }

    @ParameterizedTest
    @MethodSource("providePhoneNumberLists")
    void testSetEntitiesWithVariousLists(List<PhoneNumberEntity> input, int expectedSize) {
        PhoneNumberHelper.setEntities(input);
        assertEquals(expectedSize, PhoneNumberHelper.getEntities().size());
    }

    private static Stream<Arguments> providePhoneNumberLists() {
        PhoneNumberEntity p1 = new PhoneNumberEntity();
        p1.setNumber("0411111111");

        PhoneNumberEntity p2 = new PhoneNumberEntity();
        p2.setNumber("0422222222");

        return Stream.of(
                Arguments.of(Collections.emptyList(), 0),
                Arguments.of(Collections.singletonList(p1), 1),
                Arguments.of(Arrays.asList(p1, p2), 2),
                Arguments.of(Arrays.asList(p1, p1), 2), // Duplicate objects
                Arguments.of(Arrays.asList(p1, null), 2) // Contains null
        );
    }

    @Test
    void testSetEntitiesOverwritesPreviousValue() {
        // First set
        PhoneNumberHelper.setEntities(testEntities);
        assertEquals(2, PhoneNumberHelper.getEntities().size());

        // Second set with different data
        PhoneNumberEntity newPhone = new PhoneNumberEntity();
        newPhone.setNumber("0433333333");
        PhoneNumberHelper.setEntities(Collections.singletonList(newPhone));

        assertEquals(1, PhoneNumberHelper.getEntities().size());
        assertEquals("0433333333",
                PhoneNumberHelper.getEntities().get(0).getNumber());
    }


}