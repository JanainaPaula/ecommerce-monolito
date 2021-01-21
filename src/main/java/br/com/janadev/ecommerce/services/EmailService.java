package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage message);

    void sendOrderConfirmationHtmlEmail(Pedido pedido);

    void sendHtmlEmail(MimeMessage mimeMessage);
}
