package com.gabriel.paiva.cursomc.cursomc.exceptions;

public class AuthorizationException extends RuntimeException{

    public AuthorizationException(String msg){
        super(msg);
    }

    public AuthorizationException(String msg, Throwable e){
        super(msg,e);
    }


}
