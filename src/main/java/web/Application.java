package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@PropertySource("classpath:db-pcourse.properties")
public class Application
{
	public static void main(String[] args) {
		System.out.println("JAVA BACKEND --- START");
		SpringApplication.run(Application.class, args);
		System.out.println("JAVA BACKEND --- END");
	}
}
