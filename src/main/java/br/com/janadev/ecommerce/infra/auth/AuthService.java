package br.com.janadev.ecommerce.infra.auth;

import br.com.janadev.ecommerce.domain.cliente.Cliente;
import br.com.janadev.ecommerce.infra.error.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.domain.cliente.ClienteRepository;
import br.com.janadev.ecommerce.infra.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder cryptPasswordEncoder;

    @Autowired
    private EmailService emailService;

    private Random random = new Random();

    public void sendNewPassword(String email){
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null){
            throw new ObjectNotFoundException("Email não encontrado");
        }
        String newPass = newPassword();
        cliente.setSenha(cryptPasswordEncoder.encode(newPass));
        clienteRepository.save(cliente);

        emailService.sendNewPasswordEmail(cliente, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i<10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = random.nextInt(3);
        if (opt == 0){ //gera digíto
            return (char) (random.nextInt(10) + 48);
        } else if (opt == 1) { //gera letra maiúscula
            return (char) (random.nextInt(26) + 65);
        } else { // gera letra minúscula
            return (char) (random.nextInt(26) + 97);
        }
    }
}
