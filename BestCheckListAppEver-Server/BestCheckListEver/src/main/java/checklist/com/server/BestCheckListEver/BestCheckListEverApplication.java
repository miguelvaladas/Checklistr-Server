package checklist.com.server.BestCheckListEver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import checklist.com.server.BestCheckListEver.services.UsersService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@SpringBootApplication
public class BestCheckListEverApplication{

	public static void main(String[] args) {
		SpringApplication.run(BestCheckListEverApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// CommandLineRunner runner(UsersService usersService){
	// return args -> {	usersService.add("Mike1234", "Cunt");
		
	// };
	// }
}
