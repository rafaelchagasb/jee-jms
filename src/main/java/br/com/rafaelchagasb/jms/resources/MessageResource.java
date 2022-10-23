package br.com.rafaelchagasb.jms.resources;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import br.com.rafaelchagasb.jms.ejb.MessageService;

@Path("messages")
public class MessageResource {
	
	@Inject
	MessageService service;
	
	@Path("{message}")
	@GET
	public Response send(@PathParam("message") String message) {
		try {
			service.sendMessage(message);
			return Response.ok().build();
		} catch (JMSException e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
