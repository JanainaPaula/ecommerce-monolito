package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Pedido;
import br.com.janadev.ecommerce.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido buscaPedidoPorId(Integer id){
        Optional<Pedido> pedidoBuscado = repository.findById(id);
        return pedidoBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Pedido.class.getName()));
    }
}
