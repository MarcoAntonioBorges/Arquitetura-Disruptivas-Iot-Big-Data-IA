package br.fiap.iot.restful_example;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
@Path("cliente")
public class MyClienteResource {
	@GET @Path("{id: [1-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Cliente getCliente(@PathParam("id") long id) {	
		//A classe Cliente poderia se tratar de uma entidade JPA recuperada do BD.
		Cliente cliente = new Cliente();
		cliente.setId(id); //O id é o mesmo procurado
		cliente.setNascimento(new Date()); //Data de agora
		cliente.setNome("Nome Aleatório");
		///////////////////////////////////////////////////////////////////////
		return cliente;
    }
	@GET @Path("all")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Cliente> getClientesList() {	
		//Poderíamos estar recuperando a lista de clientes a partir do banco,
		// mas aqui a lista retorna vazia
		return new ArrayList<Cliente>();
    }
	@POST @Consumes(MediaType.APPLICATION_JSON)
	public Cliente cadastro(Cliente cliente) {
		//Salva os dados do cliente em algum lugar
		// e era o novo ID
		cliente.setId(1000);
		return cliente;
	}
}
