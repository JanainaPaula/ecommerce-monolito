package br.com.janadev.ecommerce.resource;

import br.com.janadev.ecommerce.domain.Categoria;
import br.com.janadev.ecommerce.dto.CategoriaDTO;
import br.com.janadev.ecommerce.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Integer id){
        Categoria categoria = service.buscaCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> insereCategoria(@RequestBody Categoria categoria){
        Categoria categoriaCriada = service.insereCategoria(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoriaCriada.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizaCategoria(@RequestBody Categoria categoria, @PathVariable Integer id){
        categoria.setId(id);
        service.atualizaCategoria(categoria);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaCategoria(@PathVariable Integer id){
        service.deletaCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listaCategoria(){
        List<CategoriaDTO> categorias = service.buscaTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

}
