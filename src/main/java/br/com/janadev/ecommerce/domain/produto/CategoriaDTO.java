package br.com.janadev.ecommerce.domain.produto;

import br.com.janadev.ecommerce.domain.produto.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoriaDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "O preenchimento do nome é obrigatório.")
    @Length(min = 5, max = 80, message = "O tamanho minímo é 5 e o máximo é 80.")
    private String nome;

    public CategoriaDTO(Categoria categoria){
        id = categoria.getId();
        nome = categoria.getNome();
    }

    public Categoria fromDTO(){
        return new Categoria(this.id, this.nome);
    }

    public CategoriaDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
