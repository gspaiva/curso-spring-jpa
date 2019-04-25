package com.gabriel.paiva.cursomc.cursomc.Exceptions;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String msg){
        super(msg);
    }

    public ObjectNotFoundException(String msg, Throwable e){
        super(msg,e);
    }


}
