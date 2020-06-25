package br.com.janadev.ecommerce.domain.cliente;

import br.com.janadev.ecommerce.domain.cliente.Cidade;
import br.com.janadev.ecommerce.domain.cliente.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    public CidadeRepository cidadeRepository;

    public List<Cidade> findCidades(Integer estadoId){
        return cidadeRepository.findCidades(estadoId);
    }
}
