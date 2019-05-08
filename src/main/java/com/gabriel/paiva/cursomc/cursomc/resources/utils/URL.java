package com.gabriel.paiva.cursomc.cursomc.resources.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {



    public static List<Integer> getIdsFromStringParam(String param){
        List<Integer> ids = Arrays.stream(param.split(",")).map(id -> Integer.parseInt(id)).collect(Collectors.toList());
        return ids;
    }


}
