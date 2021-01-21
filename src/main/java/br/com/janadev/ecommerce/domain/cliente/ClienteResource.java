package br.com.janadev.ecommerce.domain.cliente;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @ApiOperation(value = "Busca Cliente por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Integer id){
        Cliente cliente = service.buscaClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @ApiOperation(value = "Cadastra novo Cliente")
    @PostMapping
    public ResponseEntity<Void> insereCliente(@Valid @RequestBody ClienteNewDTO clienteDto){
        Cliente categoriaCriada = service.insereCliente(clienteDto.fromDTO());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoriaCriada.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualiza Cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizaCliente(@Valid @RequestBody ClienteDTO clienteDto, @PathVariable Integer id){
        clienteDto.setId(id);
        service.atualizaCliente(clienteDto.fromDTO());
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Remove Cliente")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCliente(@PathVariable Integer id){
        service.deletaCliente(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Busca Cliente por email")
    @GetMapping("/email")
    public ResponseEntity<Cliente> buscaPorEmail(@RequestParam(name = "value") String email){
        Cliente cliente = service.findByEmail(email);
        return ResponseEntity.ok(cliente);
    }

    @ApiOperation(value = "Busca todos os clientes")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listaCliente(){
        List<ClienteDTO> categorias = service.buscaTodasClientes();
        return ResponseEntity.ok(categorias);
    }

    @ApiOperation(value = "Busca todos os Clientes com paginação")
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

    @ApiOperation(value = "Faz upload da foto de perfil do Cliente")
    @PostMapping("/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile multipartFile){
        URI uri = service.uploadProfilePicture(multipartFile);
        return ResponseEntity.created(uri).build();
    }

}
