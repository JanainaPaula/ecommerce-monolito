package br.com.janadev.ecommerce.resource;

import br.com.janadev.ecommerce.domain.Pedido;
import br.com.janadev.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscaPedidoPorId(@PathVariable Integer id){
        Pedido pedido = service.buscaPedidoPorId(id);
        return ResponseEntity.ok(pedido);
    }

}
