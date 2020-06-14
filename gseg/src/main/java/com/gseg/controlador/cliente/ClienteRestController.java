package com.gseg.controlador.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.servicio.cliente.ClienteService;

/**
 * RestController de cliente.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class ClienteRestController {

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Lista todos los clientes.
	 * 
	 * @return Devuelve una lista de todos los clientes en formato JSON.
	 */
	@RequestMapping(path = "/listaClientes", method = RequestMethod.GET)
	public List<Cliente> listaClientes() {
		return clienteService.findAll();
	}

}
