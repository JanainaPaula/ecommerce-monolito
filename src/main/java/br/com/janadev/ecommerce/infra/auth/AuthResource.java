package br.com.janadev.ecommerce.infra.auth;

import br.com.janadev.ecommerce.infra.auth.user.UserSS;
import br.com.janadev.ecommerce.infra.auth.user.UserService;
import br.com.janadev.ecommerce.infra.email.EmailDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @ApiOperation(value = "Renova JWT Token")
    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Cria nova senha para usuário")
    @PostMapping("/forgot")
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO){
        authService.sendNewPassword(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
