package br.com.janadev.ecommerce.resource;

import br.com.janadev.ecommerce.domain.Categoria;
import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.services.CategoriaService;
import br.com.janadev.ecommerce.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarCategoriaPorId(@PathVariable Integer id){
        Cliente cliente = service.buscaCategoriaPorId(id);
        return ResponseEntity.ok(cliente);
    }

}
