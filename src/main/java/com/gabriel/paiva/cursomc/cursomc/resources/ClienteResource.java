package com.gabriel.paiva.cursomc.cursomc.resources;

import com.gabriel.paiva.cursomc.cursomc.domains.Cliente;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteDTO;
import com.gabriel.paiva.cursomc.cursomc.dtos.ClienteNewDTO;
import com.gabriel.paiva.cursomc.cursomc.security.IsAdmin;
import com.gabriel.paiva.cursomc.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService ClienteService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Cliente Cliente = ClienteService.find(id);
        return ResponseEntity.ok().body(Cliente);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO clienteNewDTO){
        Cliente cliente = ClienteService.fromDTO(clienteNewDTO);
        cliente = ClienteService.insert(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO ClienteDTO, @PathVariable Integer id){
        Cliente cliente = ClienteService.fromDTO(ClienteDTO);
        cliente.setId(id);
        ClienteService.update(cliente);
        return ResponseEntity.noContent().build();
    }
    @IsAdmin
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Void> delete (@PathVariable Integer id){
        ClienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @IsAdmin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll(){
        List<Cliente> Clientes = ClienteService.findAll();
        List<ClienteDTO> ClienteDTOS = Clientes.stream().map(Cliente -> new ClienteDTO(Cliente))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(ClienteDTOS);
    }
    @IsAdmin
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(name="page", defaultValue="0") Integer page,
            @RequestParam(name="linesPerPage", defaultValue="5") Integer linesPerPage,
            @RequestParam(name="orderBy", defaultValue="nome") String orderBy,
            @RequestParam(name="direction", defaultValue="ASC") String direction
    ){
        Page<Cliente> Clientes = ClienteService.findPage(page,linesPerPage,orderBy,direction);
        Page<ClienteDTO> ClienteDTOS = Clientes.map(Cliente -> new ClienteDTO(Cliente));
        return ResponseEntity.ok().body(ClienteDTOS);
    }

}
