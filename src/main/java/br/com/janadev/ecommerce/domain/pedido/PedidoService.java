package br.com.janadev.ecommerce.domain.pedido;

import br.com.janadev.ecommerce.domain.cliente.Cliente;
import br.com.janadev.ecommerce.domain.pagamento.PagamentoComBoleto;
import br.com.janadev.ecommerce.domain.pagamento.EstadoPagamento;
import br.com.janadev.ecommerce.infra.error.exception.AuthorizationException;
import br.com.janadev.ecommerce.infra.error.exception.ObjectNotFoundException;
import br.com.janadev.ecommerce.domain.pagamento.PagamentoRepository;
import br.com.janadev.ecommerce.domain.produto.ProdutoRepository;
import br.com.janadev.ecommerce.infra.auth.user.UserSS;
import br.com.janadev.ecommerce.domain.pagamento.BoletoService;
import br.com.janadev.ecommerce.domain.cliente.ClienteService;
import br.com.janadev.ecommerce.infra.email.EmailService;
import br.com.janadev.ecommerce.infra.auth.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EmailService emailService;

    public Pedido buscaPedidoPorId(Integer id){
        Optional<Pedido> pedidoBuscado = pedidoRepository.findById(id);
        return pedidoBuscado.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado. Id: " +
                id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido insert(Pedido pedido) {
        pedido.setId(null);
        pedido.setCliente(clienteService.buscaClientePorId(pedido.getCliente().getId()));
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
            item.setProduto(produtoRepository.findById(item.getProduto().getId()).get());
            item.setPreco(item.getProduto().getPreco());
            item.setPedido(pedido);
        }
        itemPedidoRepository.saveAll(pedido.getItens());
        emailService.sendOrderConfirmationEmail(pedido);
        return pedido;
    }

    public Page<Pedido> buscaPaginaPedidos(Integer page, Integer size, String direction, String orderBy){
        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationException("Acessso Negado");
        }
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        Cliente cliente = clienteService.buscaClientePorId(user.getId());
        return pedidoRepository.findByCliente(cliente, pageRequest);

    }
}
