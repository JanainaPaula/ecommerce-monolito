package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.Cliente;
import br.com.janadev.ecommerce.dto.ClienteDTO;
import br.com.janadev.ecommerce.enums.Perfil;
import br.com.janadev.ecommerce.exception.AuthorizationException;
import br.com.janadev.ecommerce.exception.DataIntegrityException;
import br.com.janadev.ecommerce.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.repositories.ClienteRepository;
import br.com.janadev.ecommerce.repositories.EnderecoRepository;
import br.com.janadev.ecommerce.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer imageSize;

    public Cliente buscaClientePorId(Integer id){

        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())){
            throw new AuthorizationException("Acesso Negado");
        }

        Optional<Cliente> clienteBuscado = repository.findById(id);
        return clienteBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insereCliente(Cliente cliente) {
        String senhaEncriptada = encryptSenha(cliente.getSenha());
        cliente.setSenha(senhaEncriptada);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return repository.save(cliente);
    }

    private String encryptSenha(String senha) {
        return passwordEncoder.encode(senha);
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

    public Cliente findByEmail(String email){
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())){
            throw new AuthorizationException("Acesso negado");
        }

        Cliente cliente = repository.findByEmail(email);
        if (cliente == null){
            throw new ObjectNotFoundException("Objeto não encontrado! ID: " + user.getId() +
                    ", Tipo: " + Cliente.class.getName());
        }

        return cliente;
    }

    public URI uploadProfilePicture(MultipartFile multipartFile){
        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, this.imageSize);
        String fileName = this.prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
