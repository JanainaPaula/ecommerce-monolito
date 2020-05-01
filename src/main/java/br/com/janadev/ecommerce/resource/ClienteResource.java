package br.com.janadev.ecommerce.resource;

import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.dto.ClienteDTO;
import br.com.janadev.ecommerce.dto.ClienteNewDTO;
import br.com.janadev.ecommerce.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer id){
        Cliente cliente = service.buscaClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Void> insereCliente(@Valid @RequestBody ClienteNewDTO clienteDto){
        Cliente categoriaCriada = service.insereCliente(clienteDto.fromDTO());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoriaCriada.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizaCliente(@Valid @RequestBody ClienteDTO clienteDto, @PathVariable Integer id){
        clienteDto.setId(id);
        service.atualizaCliente(clienteDto.fromDTO());
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCliente(@PathVariable Integer id){
        service.deletaCliente(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listaCliente(){
        List<ClienteDTO> categorias = service.buscaTodasClientes();
        return ResponseEntity.ok(categorias);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>> buscaPaginaCliente(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy){
        Page<Cliente> paginaCliente = service.buscaPaginaClientes(page, size, direction, orderBy);
        Page<ClienteDTO> paginaClientesDTO = paginaCliente.map(ClienteDTO::new);
        return ResponseEntity.ok(paginaClientesDTO);
    }

}
