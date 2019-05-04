package com.gabriel.paiva.cursomc.cursomc.services.validators;

import com.gabriel.paiva.cursomc.cursomc.domains.FieldMessage;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteNewDTO;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.repositories.ClienteRepository;
import com.gabriel.paiva.cursomc.cursomc.services.validators.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCode()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inserido é inválido."));
        }

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCode()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inserido é inválido."));
        }

        Boolean alreadyUsedEmail = clienteRepository.findByEmail(clienteNewDTO.getEmail()) != null;

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
}
