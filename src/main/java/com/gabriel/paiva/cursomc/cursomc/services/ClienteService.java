package com.gabriel.paiva.cursomc.cursomc.services;

import com.gabriel.paiva.cursomc.cursomc.domains.Cidade;
import com.gabriel.paiva.cursomc.cursomc.domains.Endereco;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteDTO;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteNewDTO;
import com.gabriel.paiva.cursomc.cursomc.enums.TipoCliente;
import com.gabriel.paiva.cursomc.cursomc.exceptions.DataIntegrityException;
import com.gabriel.paiva.cursomc.cursomc.exceptions.ObjectNotFoundException;
import com.gabriel.paiva.cursomc.cursomc.domains.Cliente;
import com.gabriel.paiva.cursomc.cursomc.repositories.ClienteRepository;
import com.gabriel.paiva.cursomc.cursomc.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository ClienteRepo;
    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id){
        Optional<Cliente> cliente = ClienteRepo.findById(id);
        return cliente.orElseThrow(() ->
                new ObjectNotFoundException("Objeto com ID (" +  id + ") não encontrado.")
        );
    }

    @Transactional
    public Cliente insert(Cliente cliente){
        cliente.setId(null);
        cliente = ClienteRepo.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }

    public Cliente update(Cliente obj){
        Cliente newObj = this.find(obj.getId());
        updateData(newObj, obj);
        return ClienteRepo.save(newObj);
    }

    public void delete(Integer id){
        this.find(id);
        try{
            ClienteRepo.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não foi possível excluir o cliente pois há entidades relacionadas.", e);
        }
    }

    public List<Cliente> findAll(){
        return ClienteRepo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return ClienteRepo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(),clienteDTO.getNome(),clienteDTO.getEmail());
    }

    public Cliente fromDTO(ClienteNewDTO clienteDTO){
        Cliente cli = new Cliente(null,clienteDTO.getNome(),clienteDTO.getEmail(),clienteDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteDTO.getTipoCliente()));
        Cidade cidade = new Cidade(clienteDTO.getCidadeId(),null, null);
        Endereco endereco = new Endereco(null,clienteDTO.getLogradouro(),clienteDTO.getNumero(),clienteDTO.getComplemento(),clienteDTO.getBairro(),clienteDTO.getCep(),cidade,cli);

        cli.getTelefones().add(clienteDTO.getTelefone1());

        if(clienteDTO.getTelefone2() != null)
            cli.getTelefones().add(clienteDTO.getTelefone2());

        if(clienteDTO.getTelefone3() != null)
            cli.getTelefones().add(clienteDTO.getTelefone3());

        cli.getEnderecos().add(endereco);

        return cli;
    }

    private void updateData(Cliente newObj,Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
