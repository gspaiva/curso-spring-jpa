package com.gabriel.paiva.cursomc.cursomc.services.validators;

import com.gabriel.paiva.cursomc.cursomc.domains.Cliente;
import com.gabriel.paiva.cursomc.cursomc.domains.FieldMessage;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteDTO;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteNewDTO;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.repositories.ClienteRepository;
import com.gabriel.paiva.cursomc.cursomc.services.validators.utils.BR;
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
    private ClienteRepository clienteRepository;

    @Override
    public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext constraintValidatorContext) {


        Map<String,String> attributes = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(attributes.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        Cliente clienteWithEmail = clienteRepository.findByEmail(clienteDTO.getEmail());

        Boolean alreadyUsedEmail = clienteWithEmail != null && !anotherClienteWithSameEmail(uriId, clienteWithEmail);

        if(alreadyUsedEmail){
            list.add(new FieldMessage("email", "E-mail já usado. Por favor, insira outro e-mail."));
        }

        for(FieldMessage f : list){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(f.getFieldMessage())
                    .addPropertyNode(f.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }

    private boolean anotherClienteWithSameEmail(Integer idUpdatingCliente, Cliente clienteWithEmail) {
        return idUpdatingCliente.equals(clienteWithEmail.getId());
    }
}
