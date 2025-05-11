package au.com.telecom.dto;

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

    @JsonProperty("number")
    @JsonPropertyDescription("Phone Number")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits with no letters or symbols")
    @NotNull
    private  String number;


    @JsonProperty("activated")
    @JsonPropertyDescription("Phone Number activated")
    @NotNull
    private Boolean activated;


}
