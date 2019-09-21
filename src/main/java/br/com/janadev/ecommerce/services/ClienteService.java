package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Categoria;
import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.repositories.CategoriaRepository;
import br.com.janadev.ecommerce.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente buscaCategoriaPorId(Integer id){
        Optional<Cliente> clienteBuscado = repository.findById(id);
        return clienteBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Cliente.class.getName()));
    }
}
