package au.com.telecom.dto;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void shouldCreatePhoneNumberWithNoArgsConstructor() {
        PhoneNumber phoneNumber = new PhoneNumber();

        assertNull(phoneNumber.getNumber());
        assertNull(phoneNumber.getActivated());
    }

    @Test
    void shouldCreatePhoneNumberWithAllArgsConstructor() {
        String testNumber = "0412345678";
        Boolean testStatus = true;
        PhoneNumber phoneNumber = new PhoneNumber(testNumber, testStatus);

        assertEquals(testNumber, phoneNumber.getNumber());
        assertEquals(testStatus, phoneNumber.getActivated());
    }

    @Test
    void shouldSetAndGetPropertiesCorrectly() {
        PhoneNumber phoneNumber = new PhoneNumber();
        String testNumber = "0498765432";
        Boolean testStatus = false;

        phoneNumber.setNumber(testNumber);
        phoneNumber.setActivated(testStatus);

        assertEquals(testNumber, phoneNumber.getNumber());
        assertEquals(testStatus, phoneNumber.getActivated());
    }

    @Test
    void shouldImplementEqualsAndHashCodeCorrectly() {
        PhoneNumber phone1 = new PhoneNumber("0412345678", true);
        PhoneNumber phone2 = new PhoneNumber("0412345678", true);
        PhoneNumber phone3 = new PhoneNumber("0498765432", false);

        assertEquals(phone1, phone2);
        assertNotEquals(phone1, phone3);
        assertEquals(phone1.hashCode(), phone2.hashCode());
        assertNotEquals(phone1.hashCode(), phone3.hashCode());
    }

    @Test
    void shouldGenerateCorrectToString() {
        PhoneNumber phoneNumber = new PhoneNumber("0412345678", true);
        String toString = phoneNumber.toString();

        assertTrue(toString.contains("0412345678"));
        assertTrue(toString.contains("true"));
    }

    @Test
    void shouldSerializeToJsonCorrectly() throws JsonProcessingException {
        PhoneNumber phoneNumber = new PhoneNumber("0412345678", true);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(phoneNumber);

        assertTrue(json.contains("\"number\":\"0412345678\""));
        assertTrue(json.contains("\"activated\":true"));
    }

    @Test
    void shouldDeserializeFromJsonCorrectly() throws JsonProcessingException {
        String json = "{\"number\":\"0498765432\",\"activated\":false}";
        ObjectMapper objectMapper = new ObjectMapper();
        PhoneNumber phoneNumber = objectMapper.readValue(json, PhoneNumber.class);

        assertEquals("0498765432", phoneNumber.getNumber());
        assertFalse(phoneNumber.getActivated());
    }

    @Test
    void shouldExcludeNullFieldsFromJsonWhenEmpty() throws JsonProcessingException {
        PhoneNumber phoneNumber = new PhoneNumber(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(phoneNumber);

        assertEquals("{}", json);
    }
}