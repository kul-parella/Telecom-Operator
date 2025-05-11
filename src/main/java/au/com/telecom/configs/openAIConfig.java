package au.com.telecom.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.Schema;


/***
 * OpenAIConfig is used by OpenAPI dependencies to render the API spec
 *  @author :Kuladeep Parella
 */
@Configuration
public class openAIConfig {

    /***
     *
     * @return OpenAPI object needed to create API specs
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ErrorResponse", new Schema<String>()
                                .type("object")
                                .addProperty("message", new StringSchema())
                        )
                )
                .info(new Info()
                        .title("Telecom Phone Number API")
                        .version("1.0")
                        .description("REST API to manage phone numbers and customer data"));
    }
}

