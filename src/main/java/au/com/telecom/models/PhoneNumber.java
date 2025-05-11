package au.com.telecom.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * PhoneNumber entity class to hold phone number related info.
 * @author  kuladeep.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PhoneNumber {

    @JsonProperty("phoneNumber")
    @JsonPropertyDescription("Phone Number")
    private  String number;

    @JsonProperty("active")
    @JsonPropertyDescription("Phone Number is active")
    private boolean active;

    @JsonPropertyDescription("Customer Id")
    private String customerId;

}
