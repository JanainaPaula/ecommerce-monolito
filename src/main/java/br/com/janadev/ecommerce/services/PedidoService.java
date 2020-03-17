package br.com.janadev.ecommerce.services;

import br.com.janadev.ecommerce.domain.ItemPedido;
import br.com.janadev.ecommerce.domain.PagamentoComBoleto;
import br.com.janadev.ecommerce.domain.Pedido;
import br.com.janadev.ecommerce.enums.EstadoPagamento;
import br.com.janadev.ecommerce.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.repositories.ItemPedidoRepository;
import br.com.janadev.ecommerce.repositories.PagamentoRepository;
import br.com.janadev.ecommerce.repositories.PedidoRepository;
import br.com.janadev.ecommerce.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido buscaPedidoPorId(Integer id){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
        return pedidoBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, pedido.getInstante());
        }
        pedido =  pedidoRepository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido item : pedido.getItens()){
            item.setDesconto(0.0);
            item.setPreco(produtoRepository.findById(item.getProduto().getId()).get().getPreco());
            item.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        return pedido;
    }
}
