package com.gabriel.paiva.cursomc.cursomc.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gabriel.paiva.cursomc.cursomc.enums.Perfil;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
    @JsonIgnore
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Endereco> enderecos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name="TELEFONE")
    private Set<String> telefones = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "PERFIS")
    private Set<Integer> perfis = new HashSet<>();

    public Cliente() {
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfOuCnpj = cpfOuCnpj;
        this.tipoCliente = tipoCliente.getCode();
        this.senha = senha;
        addPerfil(Perfil.CLIENTE);
    }

    public Cliente(Integer id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        addPerfil(Perfil.CLIENTE);
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public void addPerfil(Perfil perfil){
        this.perfis.add(perfil.getCode());
    }

    public Set<Perfil> getPerfis(){
        return this.perfis.stream().map(code -> Perfil.toEnum(code)).collect(Collectors.toSet());
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
