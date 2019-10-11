package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Categoria;
import br.com.janadev.ecommerce.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria buscaCategoriaPorId(Integer id){
        Optional<Categoria> categoriaBuscada = repository.findById(id);
        return categoriaBuscada.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insereCategoria(Categoria categoria) {
        return repository.save(categoria);
    }
}
