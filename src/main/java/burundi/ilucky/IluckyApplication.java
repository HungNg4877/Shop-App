package burundi.ilucky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IluckyApplication {

	public static void main(String[] args) {
		SpringApplication.run(IluckyApplication.class, args);
	}

}
