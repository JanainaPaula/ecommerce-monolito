package br.com.janadev.ecommerce.services.validation;

import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.dto.ClienteDTO;
import br.com.janadev.ecommerce.dto.ClienteNewDTO;
import br.com.janadev.ecommerce.enums.TipoCliente;
import br.com.janadev.ecommerce.handler.FieldMessage;
import br.com.janadev.ecommerce.repositories.ClienteRepository;
import br.com.janadev.ecommerce.services.validation.util.BRDocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repository;

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }

    @Override
    public boolean isValid(ClienteDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer id = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente clienteExistente = repository.findByEmail(clienteNewDTO.getEmail());
        if (clienteExistente != null && !clienteExistente.getId().equals(id)){
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
