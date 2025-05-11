package au.com.telecom.models;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void testNoArgsConstructor() {
        PhoneNumber phoneNumber = new PhoneNumber();

        assertNull(phoneNumber.getNumber());
        assertFalse(phoneNumber.isActive());
        assertNull(phoneNumber.getCustomerId());
    }

    @Test
    void testAllArgsConstructor() {
        PhoneNumber phoneNumber = new PhoneNumber("0412345678", true, "cust123");

        assertEquals("0412345678", phoneNumber.getNumber());
        assertTrue(phoneNumber.isActive());
        assertEquals("cust123", phoneNumber.getCustomerId());
    }

    @Test
    void testGettersAndSetters() {
        PhoneNumber phoneNumber = new PhoneNumber();

        phoneNumber.setNumber("0498765432");
        phoneNumber.setActive(false);
        phoneNumber.setCustomerId("cust456");

        assertEquals("0498765432", phoneNumber.getNumber());
        assertFalse(phoneNumber.isActive());
        assertEquals("cust456", phoneNumber.getCustomerId());
    }

    @Test
    void testJsonPropertyAnnotations() throws JsonProcessingException {
        PhoneNumber phoneNumber = new PhoneNumber("0412345678", true, "cust123");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(phoneNumber);

        assertTrue(json.contains("\"phoneNumber\":\"0412345678\""));
        assertTrue(json.contains("\"active\":true"));
        assertTrue(json.contains("\"customerId\":\"cust123\""));
    }

    @Test
    void testEqualsAndHashCode() {
        PhoneNumber phone1 = new PhoneNumber("0412345678", true, "cust123");
        PhoneNumber phone2 = new PhoneNumber("0412345678", true, "cust123");
        PhoneNumber phone3 = new PhoneNumber("0498765432", false, "cust456");

        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
        assertEquals(phone1.hashCode(), phone2.hashCode());
        assertNotEquals(phone1.hashCode(), phone3.hashCode());
    }

    @Test
    void testToString() {
        PhoneNumber phoneNumber = new PhoneNumber("0412345678", true, "cust123");
        String toString = phoneNumber.toString();

        assertTrue(toString.contains("number=0412345678"));
        assertTrue(toString.contains("active=true"));
        assertTrue(toString.contains("customerId=cust123"));
    }

    @Test
    void testJsonPropertyDescriptionAnnotations() throws NoSuchFieldException {
        Field numberField = PhoneNumber.class.getDeclaredField("number");
        Field activeField = PhoneNumber.class.getDeclaredField("active");

        JsonPropertyDescription numberDesc = numberField.getAnnotation(JsonPropertyDescription.class);
        JsonPropertyDescription activeDesc = activeField.getAnnotation(JsonPropertyDescription.class);

        assertEquals("Phone Number", numberDesc.value());
        assertEquals("Phone Number is active", activeDesc.value());
    }

    @Test
    void testSerializationWithPartialData() throws JsonProcessingException {
        PhoneNumber phoneNumber = new PhoneNumber("0412345678", true, null);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(phoneNumber);

        assertTrue(json.contains("\"phoneNumber\":\"0412345678\""));
        assertTrue(json.contains("\"active\":true"));
        assertFalse(json.contains("customerId"));
    }
}
