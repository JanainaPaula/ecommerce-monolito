package br.com.janadev.ecommerce.infra.email;

import br.com.janadev.ecommerce.domain.cliente.Cliente;
import br.com.janadev.ecommerce.domain.pedido.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage message);

    void sendOrderConfirmationHtmlEmail(Pedido pedido) throws MessagingException;

    void sendHtmlEmail(MimeMessage mimeMessage);

    void sendNewPasswordEmail(Cliente cliente, String newPass);


}
