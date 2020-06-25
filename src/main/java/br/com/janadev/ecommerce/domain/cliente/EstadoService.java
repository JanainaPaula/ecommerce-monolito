package br.com.janadev.ecommerce.domain.cliente;

import br.com.janadev.ecommerce.domain.cliente.Estado;
import br.com.janadev.ecommerce.domain.cliente.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll(){
        return estadoRepository.findAllByOrderByNome();
    }
}
