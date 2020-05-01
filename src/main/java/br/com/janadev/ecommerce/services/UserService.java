package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserSS authenticated(){
        try {
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception ex){
            return null;
        }
    }
}
