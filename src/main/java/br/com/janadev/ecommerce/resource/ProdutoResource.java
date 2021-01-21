package br.com.janadev.ecommerce.resource;

import br.com.janadev.ecommerce.domain.Categoria;
import br.com.janadev.ecommerce.domain.Pedido;
import br.com.janadev.ecommerce.domain.Produto;
import br.com.janadev.ecommerce.dto.CategoriaDTO;
import br.com.janadev.ecommerce.dto.ProdutoDTO;
import br.com.janadev.ecommerce.resource.util.URLUtil;
import br.com.janadev.ecommerce.services.PedidoService;
import br.com.janadev.ecommerce.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscaProdutoPorId(@PathVariable Integer id){
        Produto produto = service.buscaProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscaPaginaCategoria(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) throws UnsupportedEncodingException {
        Page<Produto> paginaProdutos = service.search(URLUtil.decodeParam(nome), URLUtil.decodeIntList(categorias),
                page, size, direction, orderBy);
        Page<ProdutoDTO> paginaProdutosDTO = paginaProdutos.map(ProdutoDTO::new);
        return ResponseEntity.ok(paginaProdutosDTO);
    }

}
