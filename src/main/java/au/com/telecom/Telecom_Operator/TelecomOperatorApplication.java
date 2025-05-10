package au.com.telecom.Telecom_Operator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/***
 * TelecomOperatorApplication class runs the app
 * @author kuladeep.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"au.com.telecom"})
public class TelecomOperatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecomOperatorApplication.class, args);
	}

}
