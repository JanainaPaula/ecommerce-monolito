package br.com.janadev.ecommerce.domain.produto;

import br.com.janadev.ecommerce.infra.error.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto buscaProdutoPorId(Integer id){
        Optional<Produto> produtoBuscado = produtoRepository.findById(id);
        return produtoBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer size, String direction, String orderBy){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return produtoRepository.search(nome, categorias, pageRequest);
    }
}
