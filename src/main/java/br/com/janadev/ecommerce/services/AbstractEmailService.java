package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.domain.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendOrderConfirmationEmail(Pedido pedido){
        SimpleMailMessage mailMessage = prepareSimpleMailMessageFromPedido(pedido);
        sendEmail(mailMessage);
    }

    @Override
    public void sendNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage mailMessage = prepareNewPasswordEmail(cliente, newPass);
        sendEmail(mailMessage);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(cliente.getEmail());
        mailMessage.setFrom(sender);
        mailMessage.setSubject("Solicitação de nova senha");
        mailMessage.setText(String.valueOf(new Date(System.currentTimeMillis())));
        mailMessage.setText("Nova senha: " + newPass);
        return mailMessage;
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(pedido.getCliente().getEmail());
        mailMessage.setFrom(sender);
        mailMessage.setSubject("Pedido Confirmado! Código: " + pedido.getId());
        mailMessage.setText(String.valueOf(new Date(System.currentTimeMillis())));
        mailMessage.setText(pedido.toString());
        return mailMessage;
    }

}
