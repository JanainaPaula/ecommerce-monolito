package br.com.janadev.ecommerce.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaResource {

    @GetMapping
    public String listar(){
        return "O REST está funcionando";
    }

}
