package br.com.janadev.ecommerce.domain.cliente.endereco;

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
