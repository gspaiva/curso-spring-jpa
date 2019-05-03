package com.gabriel.paiva.cursomc.cursomc.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min=5, max = 120, message = "O tamanho obrigatório é de 5 a 120 caracteres")
    private String nome;
    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "E-mail com formato inválido")
    private String email;
    private String cpfOuCnpj;
    private Integer tipoCliente;

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();


    public Cliente() {
    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipoCliente = tipoCliente.getCode();
    }

    public Cliente(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpfOuCnpj() {
        return cpfOuCnpj;
    }

    public void setCpfOuCnpj(String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public TipoCliente getTipoCliente() {
        return TipoCliente.toEnum(this.tipoCliente);
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente.getCode();
    }

    public void setTipoCliente(Integer tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return id.equals(cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
