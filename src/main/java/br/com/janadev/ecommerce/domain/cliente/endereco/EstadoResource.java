package br.com.janadev.ecommerce.domain.cliente.endereco;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @ApiOperation(value = "Busca todos os Estados do Brasil")
    @GetMapping
    public ResponseEntity<List<EstadoDTO>> findAll(){
        List<Estado> estados = estadoService.findAll();
        List<EstadoDTO> estadosDTO = estados.stream().map(EstadoDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadosDTO);
    }

    @ApiOperation(value = "Busca todos as cidades de um Estado")
    @GetMapping("/{estado-id}/cidades")
    public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable(name = "estado-id") Integer estadoId){
        List<Cidade> cidades = cidadeService.findCidades(estadoId);
        List<CidadeDTO> cidadesDTO = cidades.stream().map(CidadeDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadesDTO);
    }
}
