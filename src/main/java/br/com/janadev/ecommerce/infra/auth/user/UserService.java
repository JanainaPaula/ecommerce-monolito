package br.com.janadev.ecommerce.infra.auth.user;

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
