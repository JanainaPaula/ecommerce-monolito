package br.com.janadev.ecommerce.resource;

import br.com.janadev.ecommerce.domain.Categoria;
import br.com.janadev.ecommerce.domain.Pedido;
import br.com.janadev.ecommerce.dto.CategoriaDTO;
import br.com.janadev.ecommerce.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

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

    @PostMapping
    public ResponseEntity<Void> inserePedido(@Valid @RequestBody Pedido pedido){
        Pedido pedidoSalvo = service.insert(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(pedidoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<Page<Pedido>> buscaPaginaCategoria(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy){
        Page<Pedido> paginaPedido = service.buscaPaginaCategorias(page, size, direction, orderBy);
        return ResponseEntity.ok(paginaPedido);
    }

}
