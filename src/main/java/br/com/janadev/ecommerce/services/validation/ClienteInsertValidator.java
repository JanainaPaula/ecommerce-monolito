package br.com.janadev.ecommerce.services.validation;

import br.com.janadev.ecommerce.dto.ClienteNewDTO;
import br.com.janadev.ecommerce.enums.TipoCliente;
import br.com.janadev.ecommerce.handler.FieldMessage;
import br.com.janadev.ecommerce.services.validation.util.BRDocumentUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

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

        for (FieldMessage fieldMessage : list){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
