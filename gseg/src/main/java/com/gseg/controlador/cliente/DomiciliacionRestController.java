package com.gseg.controlador.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.cliente.Domiciliacion;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.cliente.DomiciliacionService;

/**
 * RestController de domiciliación.
 * 
 * @author Jesús López Barragán.
 *
 */
@RestController
public class DomiciliacionRestController {

	/**
	 * Servicio de domiciliación.
	 */
	@Autowired
	DomiciliacionService domiciliacionService;

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Lista las domiciliaciones de un cliente.
	 * 
	 * @param idCliente - Identificador de cliente.
	 * @return Devuelve una lista de las domiciliaciones de un cliente en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaDomiciliaciones/{idCliente}", method = RequestMethod.GET)
	public List<Domiciliacion> listaDomiciliaciones(@PathVariable("idCliente") final long idCliente) {
		final Cliente cliente = clienteService.findById(idCliente);
		return domiciliacionService.findByCliente(cliente);
	}

}
