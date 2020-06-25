package br.com.janadev.ecommerce.infra.email;

import br.com.janadev.ecommerce.domain.cliente.Cliente;
import br.com.janadev.ecommerce.domain.pedido.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage message);

    void sendNewPasswordEmail(Cliente cliente, String newPass);
}
