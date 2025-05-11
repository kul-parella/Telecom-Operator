package au.com.telecom.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Schema;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OpenAIConfigTest {

    @Test
    void customOpenAPI_shouldReturnConfiguredOpenAPI() {
        OpenAIConfig config = new OpenAIConfig();
        OpenAPI openAPI = config.customOpenAPI();

        // Basic assertions to validate the OpenAPI metadata
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals("Telecom Phone Number API", info.getTitle());
        assertEquals("1.0", info.getVersion());
        assertTrue(info.getDescription().contains("REST API"));

        Schema<?> errorResponseSchema = openAPI.getComponents().getSchemas().get("ErrorResponse");
        assertNotNull(errorResponseSchema);
        assertEquals("object", errorResponseSchema.getType());
        assertTrue(errorResponseSchema.getProperties().containsKey("message"));
    }
}

