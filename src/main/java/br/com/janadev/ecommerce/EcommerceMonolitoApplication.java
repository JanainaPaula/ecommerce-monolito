package br.com.janadev.ecommerce;

import br.com.janadev.ecommerce.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceMonolitoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceMonolitoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
