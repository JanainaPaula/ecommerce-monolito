package br.com.janadev.ecommerce.domain.produto;

import br.com.janadev.ecommerce.domain.produto.util.URLUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @ApiOperation(value = "Busca Produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscaProdutoPorId(@PathVariable Integer id){
        Produto produto = service.buscaProdutoPorId(id);
        return ResponseEntity.ok(produto);
    }

    @ApiOperation(value = "Busca todos os Produtos com paginação")
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> buscaPaginaProduto(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "24") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy){
        Page<Produto> paginaProdutos = service.search(URLUtil.decodeParam(nome), URLUtil.decodeIntList(categorias),
                page, size, direction, orderBy);
        Page<ProdutoDTO> paginaProdutosDTO = paginaProdutos.map(ProdutoDTO::new);
        return ResponseEntity.ok(paginaProdutosDTO);
    }

}
