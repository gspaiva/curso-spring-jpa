package com.gabriel.paiva.cursomc.cursomc.resources.utils;

import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {



    public static List<Integer> getIdsFromStringParam(String param){
        List<Integer> ids = Arrays.stream(param.split(",")).map(id -> Integer.parseInt(id)).collect(Collectors.toList());
        return ids;
    }

    public static String decodedParam(String param){
        try{
            return URLDecoder.decode(param,"UTF-8");
        } catch (Exception e){
            return "";
        }
    }

}
