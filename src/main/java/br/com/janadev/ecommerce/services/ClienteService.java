package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.dto.ClienteDTO;
import br.com.janadev.ecommerce.exception.DataIntegrityException;
import br.com.janadev.ecommerce.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.repositories.ClienteRepository;
import br.com.janadev.ecommerce.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente buscaClientePorId(Integer id){
        Optional<Cliente> clienteBuscado = repository.findById(id);
        return clienteBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insereCliente(Cliente cliente) {
        enderecoRepository.saveAll(cliente.getEnderecos());
        return repository.save(cliente);
    }

    public Cliente atualizaCliente(Cliente cliente) {
        Cliente clienteBuscado = buscaClientePorId(cliente.getId());
        updateData(clienteBuscado, cliente);
        return repository.save(clienteBuscado);
    }

    private void updateData(Cliente clienteBuscado, Cliente clienteAtualizado) {
        clienteBuscado.setNome(clienteAtualizado.getNome());
        clienteBuscado.setEmail(clienteAtualizado.getEmail());
    }

    public void deletaCliente(Integer id) {
        buscaClientePorId(id);
        try {
            repository.deleteById(id);
        }catch (DataIntegrityViolationException ex){
            throw new DataIntegrityException("Não foi possível deletar pois há pedidos relacionados.");
        }

    }

    public List<ClienteDTO> buscaTodasClientes() {
        return repository.findAll().stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    public Page<Cliente> buscaPaginaClientes(Integer page, Integer size, String direction, String orderBy){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        return repository.findAll(pageRequest);
    }
}
