package com.gabriel.paiva.cursomc.cursomc.services.validators;

import com.gabriel.paiva.cursomc.cursomc.domains.FieldMessage;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteNewDTO;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.services.validators.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {
        List<FieldMessage> list = new ArrayList<>();

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCode()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CPF inserido é inválido."));
        }

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCode()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())){
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inserido é inválido."));
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
