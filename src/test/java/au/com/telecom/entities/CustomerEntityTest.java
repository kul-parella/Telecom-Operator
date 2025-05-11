package au.com.telecom.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.persistence.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class CustomerEntityTest {

    private CustomerEntity customer;
    private PhoneNumberEntity phoneNumber1;
    private PhoneNumberEntity phoneNumber2;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setId(1L);
        customer.setName("John Doe");

        phoneNumber1 = new PhoneNumberEntity();
        phoneNumber1.setId(1L);
        phoneNumber1.setNumber("0412345678");
        phoneNumber1.setActivated(true);

        phoneNumber2 = new PhoneNumberEntity();
        phoneNumber2.setId(2L);
        phoneNumber2.setNumber("0498765432");
        phoneNumber2.setActivated(false);
    }

    @Test
    void testEntityAnnotations() {
        // Test class-level annotations
        assertNotNull(CustomerEntity.class.getAnnotation(Entity.class));
        Table tableAnnotation = CustomerEntity.class.getAnnotation(Table.class);
        assertNotNull(tableAnnotation);
        assertEquals("customers", tableAnnotation.name());
    }

    @Test
    void testIdFieldAnnotations() throws NoSuchFieldException {
        // Test ID field annotations
        Field idField = CustomerEntity.class.getDeclaredField("id");

        assertNotNull(idField.getAnnotation(Id.class));
        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertNotNull(generatedValue);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy());
    }

    @Test
    void testOneToManyRelationship() {
        // Test the one-to-many relationship
        customer.getPhoneNumbers().add(phoneNumber1);
        customer.getPhoneNumbers().add(phoneNumber2);

        assertEquals(2, customer.getPhoneNumbers().size());
        assertEquals(phoneNumber1, customer.getPhoneNumbers().get(0));
        assertEquals(phoneNumber2, customer.getPhoneNumbers().get(1));
    }

    @Test
    void testCascadeAndOrphanRemoval() throws NoSuchFieldException {
        // Test relationship annotations
        Field phoneNumbersField = CustomerEntity.class.getDeclaredField("phoneNumbers");
        OneToMany oneToMany = phoneNumbersField.getAnnotation(OneToMany.class);

        assertNotNull(oneToMany);
        assertEquals("customer", oneToMany.mappedBy());
        assertEquals(CascadeType.ALL, oneToMany.cascade()[0]);
        assertTrue(oneToMany.orphanRemoval());
    }

    @Test
    void testGettersAndSetters() {
        // Test basic getters and setters
        assertEquals(1L, customer.getId());
        assertEquals("John Doe", customer.getName());

        customer.setId(2L);
        customer.setName("Jane Smith");

        assertEquals(2L, customer.getId());
        assertEquals("Jane Smith", customer.getName());
    }

    @Test
    void testNoArgsConstructor() {
        CustomerEntity emptyCustomer = new CustomerEntity();

        assertNull(emptyCustomer.getId());
        assertNull(emptyCustomer.getName());
        assertNotNull(emptyCustomer.getPhoneNumbers());
        assertTrue(emptyCustomer.getPhoneNumbers().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        List<PhoneNumberEntity> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(phoneNumber1);

        CustomerEntity newCustomer = new CustomerEntity(3L, "Alice Johnson", phoneNumbers);

        assertEquals(3L, newCustomer.getId());
        assertEquals("Alice Johnson", newCustomer.getName());
        assertEquals(1, newCustomer.getPhoneNumbers().size());
        assertEquals(phoneNumber1, newCustomer.getPhoneNumbers().get(0));
    }

    @Test
    void testEqualsAndHashCode() {
        CustomerEntity customer1 = new CustomerEntity(1L, "John Doe", new ArrayList<>());
        CustomerEntity customer2 = new CustomerEntity(1L, "John Doe", new ArrayList<>());
        CustomerEntity customer3 = new CustomerEntity(2L, "Jane Smith", new ArrayList<>());

        assertEquals(customer1, customer2);
        assertNotEquals(customer1, customer3);
        assertEquals(customer1.hashCode(), customer2.hashCode());
        assertNotEquals(customer1.hashCode(), customer3.hashCode());
    }

    @Test
    void testToString() {
        String toString = customer.toString();

        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("name=John Doe"));
        assertTrue(toString.contains("phoneNumbers="));
    }

    @Test
    void testAddPhoneNumber() {
        List<PhoneNumberEntity> list = new ArrayList<>();
        list.add(phoneNumber1);
        customer.setPhoneNumbers(list);

        assertEquals(1, customer.getPhoneNumbers().size());
        assertEquals(phoneNumber1, customer.getPhoneNumbers().get(0));
    }

    @Test
    void testRemovePhoneNumber() {
        List<PhoneNumberEntity> list = new ArrayList<>();
        list.add(phoneNumber1);
        customer.setPhoneNumbers(list);

        assertEquals(1, customer.getPhoneNumbers().size());
        assertNull(phoneNumber1.getCustomer());
    }
}
