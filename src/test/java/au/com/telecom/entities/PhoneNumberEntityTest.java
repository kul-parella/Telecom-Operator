package au.com.telecom.entities;

import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;


class PhoneNumberEntityTest {

    private PhoneNumberEntity phoneNumber;
    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("John Doe");

        phoneNumber = new PhoneNumberEntity();
        phoneNumber.setId(1L);
        phoneNumber.setNumber("0412345678");
        phoneNumber.setActivated(true);
        phoneNumber.setCustomer(customer);
    }

    @Test
    void testEntityAnnotations() {
        // Test class-level annotations
        assertNotNull(PhoneNumberEntity.class.getAnnotation(Entity.class));
        Table tableAnnotation = PhoneNumberEntity.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("phone_numbers", tableAnnotation.name());
    }

    @Test
    void testIdFieldAnnotations() throws NoSuchFieldException {
        // Test ID field annotations
        Field idField = PhoneNumberEntity.class.getDeclaredField("id");

        assertNotNull(idField.getAnnotation(Id.class));
        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertNotNull(generatedValue);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy());
    }

    @Test
    void testManyToOneRelationship() throws NoSuchFieldException {
        // Test relationship annotations
        Field customerField = PhoneNumberEntity.class.getDeclaredField("customer");
        ManyToOne manyToOne = customerField.getAnnotation(ManyToOne.class);
        JoinColumn joinColumn = customerField.getAnnotation(JoinColumn.class);

        assertNotNull(manyToOne);
        assertNotNull(joinColumn);
        assertEquals("customer_id", joinColumn.name());
        assertFalse(joinColumn.nullable());
    }

    @Test
    void testGettersAndSetters() {
        // Test basic getters and setters
        assertEquals(1L, phoneNumber.getId());
        assertEquals("0412345678", phoneNumber.getNumber());
        assertTrue(phoneNumber.getActivated());
        assertEquals(customer, phoneNumber.getCustomer());

        // Test setters
        phoneNumber.setId(2L);
        phoneNumber.setNumber("0498765432");
        phoneNumber.setActivated(false);

        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setId(2L);
        phoneNumber.setCustomer(newCustomer);

        assertEquals(2L, phoneNumber.getId());
        assertEquals("0498765432", phoneNumber.getNumber());
        assertFalse(phoneNumber.getActivated());
        assertEquals(newCustomer, phoneNumber.getCustomer());
    }

    @Test
    void testNoArgsConstructor() {
        PhoneNumberEntity emptyPhoneNumber = new PhoneNumberEntity();

        assertNull(emptyPhoneNumber.getId());
        assertNull(emptyPhoneNumber.getNumber());
        assertNull(emptyPhoneNumber.getActivated());
        assertNull(emptyPhoneNumber.getCustomer());
    }

    @Test
    void testAllArgsConstructor() {
        PhoneNumberEntity newPhoneNumber = new PhoneNumberEntity(
                3L, "0422334455", false, customer);

        assertEquals(3L, newPhoneNumber.getId());
        assertEquals("0422334455", newPhoneNumber.getNumber());
        assertFalse(newPhoneNumber.getActivated());
        assertEquals(customer, newPhoneNumber.getCustomer());
    }

    @Test
    void testEqualsAndHashCode() {
        PhoneNumberEntity phone1 = new PhoneNumberEntity(1L, "0412345678", true, customer);
        PhoneNumberEntity phone2 = new PhoneNumberEntity(1L, "0412345678", true, customer);
        PhoneNumberEntity phone3 = new PhoneNumberEntity(2L, "0498765432", false, customer);

        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
        assertEquals(phone1.hashCode(), phone2.hashCode());
        assertNotEquals(phone1.hashCode(), phone3.hashCode());
    }

    @Test
    void testToString() {
        String toString = phoneNumber.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("number=0412345678"));
        assertTrue(toString.contains("activated=true"));
        assertTrue(toString.contains("customer=" + customer.toString()));
    }

    @Test
    void testBidirectionalRelationshipManagement() {
        // Test that adding phone number to customer maintains the relationship
        customer.getPhoneNumbers().add(phoneNumber);

        assertEquals(1, customer.getPhoneNumbers().size());
        assertEquals(phoneNumber, customer.getPhoneNumbers().get(0));
        assertEquals(customer, phoneNumber.getCustomer());
    }

    @Test
    void testNonNullCustomerConstraint() {
        // The @JoinColumn(nullable = false) should be validated by JPA
        // This test just verifies the annotation exists
        try {
            Field customerField = PhoneNumberEntity.class.getDeclaredField("customer");
            JoinColumn joinColumn = customerField.getAnnotation(JoinColumn.class);
            assertFalse(joinColumn.nullable());
        } catch (NoSuchFieldException e) {
            fail("Customer field not found");
        }
    }
}
