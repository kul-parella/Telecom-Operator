package au.com.telecom.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/***
 * TelecomOperatorApplication class runs the app
 * @author kuladeep.
 */
@SpringBootApplication
@EnableJpaRepositories("au.com.telecom.repositories")
@EntityScan("au.com.telecom.entities")
@ComponentScan(basePackages = {"au.com.telecom"})
public class TelecomOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecomOperatorApplication.class, args);
	}

}
