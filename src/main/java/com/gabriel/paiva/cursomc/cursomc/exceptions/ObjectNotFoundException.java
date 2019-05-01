package com.gabriel.paiva.cursomc.cursomc.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String msg){
        super(msg);
    }

    public ObjectNotFoundException(String msg, Throwable e){
        super(msg,e);
    }


}
