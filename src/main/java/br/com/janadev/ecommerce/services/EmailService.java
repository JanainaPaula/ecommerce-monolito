package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage message);
}
