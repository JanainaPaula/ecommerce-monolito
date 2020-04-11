package br.com.janadev.ecommerce.config;

import br.com.janadev.ecommerce.services.DBService;
import br.com.janadev.ecommerce.services.EmailService;
import br.com.janadev.ecommerce.services.MockEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    private final DBService dbService;

    @Autowired
    public TestConfig(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public boolean initialiazeDatabase() throws ParseException {
        dbService.initializeDatabase();
        return true;
    }

    @Bean
    public EmailService emailService(){
        return new MockEmailService();
    }
}
