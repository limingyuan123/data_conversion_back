package nnu.ogms.data_conversion_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DataConversionBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataConversionBackApplication.class, args);
    }

}
