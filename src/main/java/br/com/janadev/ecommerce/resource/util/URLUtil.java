package br.com.janadev.ecommerce.resource.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URLUtil {

    public static String decodeParam(String string){
        return URLDecoder.decode(string, StandardCharsets.UTF_8);
    }

    public static List<Integer> decodeIntList(String listInString){
        return Arrays.stream(listInString.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }
}
