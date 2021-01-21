package br.com.janadev.ecommerce.infra.email;

import br.com.janadev.ecommerce.domain.cliente.Cliente;
import br.com.janadev.ecommerce.domain.pedido.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

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

    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido pedido) throws MessagingException {
        MimeMessage mimeMessage = prepareMimeMessageFromPedido(pedido);
        sendHtmlEmail(mimeMessage);
    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(pedido.getCliente().getEmail());
        messageHelper.setFrom(sender);
        return mimeMessage;
    }

    protected String htmlFromTemplatePedido(Pedido pedido){
        Context context = new Context();
        context.setVariable("pedido", pedido);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

}
