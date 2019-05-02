package com.gabriel.paiva.cursomc.cursomc.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ValidationError  extends StandardError implements Serializable {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timestamp) {
        super(status, msg, timestamp);
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }

    public void addErrors(FieldMessage error) {
        this.errors.add(error);
    }
}
