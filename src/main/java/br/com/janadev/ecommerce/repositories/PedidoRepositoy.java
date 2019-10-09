package br.com.janadev.ecommerce.repositories;

import br.com.janadev.ecommerce.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepositoy extends JpaRepository<Pedido, Integer> {
}
