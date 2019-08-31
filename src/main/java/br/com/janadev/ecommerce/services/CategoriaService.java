package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Categoria;
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
        return categoriaBuscada.orElse(null);
    }
}
