package com.gabriel.paiva.cursomc.cursomc.exceptions;

public class DataIntegrityException extends RuntimeException{

    public DataIntegrityException(String msg){
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable e){
        super(msg,e);
    }


}
