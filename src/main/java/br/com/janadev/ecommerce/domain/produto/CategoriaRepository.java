package br.com.janadev.ecommerce.domain.produto;

import br.com.janadev.ecommerce.domain.produto.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
