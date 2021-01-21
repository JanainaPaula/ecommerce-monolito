package br.com.janadev.ecommerce.config;

import br.com.janadev.ecommerce.infra.DBService;
import br.com.janadev.ecommerce.infra.email.EmailService;
import br.com.janadev.ecommerce.infra.email.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    private final DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Autowired
    public DevConfig(DBService dbService) {
        this.dbService = dbService;
    }

    @Bean
    public boolean initialiazeDatabase() throws ParseException {
        if (strategy.equals("create")){
            dbService.initializeDatabase();
            return true;
        }
        return false;
    }

    @Bean
    public EmailService emailService(){
        return new SmtpEmailService();
    }
}
