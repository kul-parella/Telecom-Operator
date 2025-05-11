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
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits with no letters or symbols")
    @NotNull
    private  String number;

    @JsonProperty("active")
    @JsonPropertyDescription("Phone Number is active")
    @NotNull
    private boolean active;

    @JsonPropertyDescription("Customer Id")
    @Pattern(regexp = "//d{1,3}", message = "Customer Id between 1-3 digits with no letters or symbols")
    @NotNull
    private String customerId;

}
