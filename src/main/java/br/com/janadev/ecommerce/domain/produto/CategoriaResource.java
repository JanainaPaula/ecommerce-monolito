package br.com.janadev.ecommerce.domain.produto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/api/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @ApiOperation(value = "Busca Categoria por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Integer id){
        Categoria categoria = service.buscaCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @ApiOperation(value = "Cria uma nova Categoria")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insereCategoria(@Valid @RequestBody CategoriaDTO categoria){
        Categoria categoriaCriada = service.insereCategoria(categoria.fromDTO());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoriaCriada.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualiza Categoria existente")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizaCategoria(@Valid @RequestBody CategoriaDTO categoria, @PathVariable Integer id){
        categoria.setId(id);
        service.atualizaCategoria(categoria.fromDTO());
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Remove Categoria")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Não é possível excluir uma Categria que possui produtos."),
            @ApiResponse(code = 404, message = "Código inexistente.")
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCategoria(@PathVariable Integer id){
        service.deletaCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Busca todas Categorias")
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listaCategoria(){
        List<CategoriaDTO> categorias = service.buscaTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @ApiOperation(value = "Busca todas Categorias com paginação")
    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> buscaPaginaCategoria(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy){
        Page<Categoria> paginaCategoria = service.buscaPaginaCategorias(page, size, direction, orderBy);
        Page<CategoriaDTO> paginaCategoriasDTO = paginaCategoria.map(CategoriaDTO::new);
        return ResponseEntity.ok(paginaCategoriasDTO);
    }

}
