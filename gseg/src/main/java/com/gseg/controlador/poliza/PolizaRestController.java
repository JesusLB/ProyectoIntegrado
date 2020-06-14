package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.poliza.PolizaService;

/**
 * RestController de poliza
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class PolizaRestController {

	/**
	 * Servicio de poliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Obtiene un listado de todas las pólizas.
	 * 
	 * @return Devuelve un listado de todas las pólizas en formato JSON.
	 */
	@RequestMapping(path = "/listaPolizas", method = RequestMethod.GET)
	public List<Poliza> listaPolizas() {
		return polizaService.findAll();
	}

	/**
	 * Obtiene un listado de pólizas filtrado por cliente.
	 * 
	 * @param idCliente - Identificador de cliente.
	 * @return Devuelve un listado de pólizas filtrado por cliente en formato JSON.
	 */
	@RequestMapping(path = "/listaPolizas/{idCliente}", method = RequestMethod.GET)
	public List<Poliza> listaPolizas(@PathVariable("idCliente") final long idCliente) {
		final Cliente cliente = clienteService.findById(idCliente);
		return polizaService.findByCliente(cliente);
	}

}
