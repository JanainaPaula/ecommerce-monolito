package br.com.janadev.ecommerce.domain.cliente.validation;

import br.com.janadev.ecommerce.domain.cliente.Cliente;
import br.com.janadev.ecommerce.domain.cliente.ClienteNewDTO;
import br.com.janadev.ecommerce.domain.cliente.TipoCliente;
import br.com.janadev.ecommerce.infra.error.FieldMessage;
import br.com.janadev.ecommerce.domain.cliente.ClienteRepository;
import br.com.janadev.ecommerce.domain.cliente.validation.util.BRDocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) &&
                !BRDocumentUtil.isValidCPF(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if (clienteNewDTO.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) &&
                !BRDocumentUtil.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        Cliente clienteExistente = repository.findByEmail(clienteNewDTO.getEmail());
        if (clienteExistente != null){
            list.add(new FieldMessage("email", "Esse email já existe."));
        }

        for (FieldMessage fieldMessage : list){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
