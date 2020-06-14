package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.poliza.PolizaService;
import com.gseg.servicio.siniestro.SiniestroService;

/**
 * RestController de siniestro.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class SiniestroRestController {

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

	/**
	 * Servicio de póliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Obtiene una lista de todos los siniestros.
	 * 
	 * @return Devuelve listado de todos los siniestros en formato JSON.
	 */
	@RequestMapping(path = "/listaSiniestros", method = RequestMethod.GET)
	public List<Siniestro> listaSiniestros() {
		return siniestroService.findAll();
	}

	/**
	 * Obtiene una lista de los siniestros filtrado por póliza.
	 * 
	 * @param idPoliza - Identificador.
	 * @return Devuelve un listado de siniestros filtrado por póliza en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaSiniestros/{idPoliza}", method = RequestMethod.GET)
	public List<Siniestro> listaSiniestrosPorPoliza(@PathVariable("idPoliza") final long idPoliza) {
		final Poliza poliza = polizaService.findById(idPoliza);
		return siniestroService.findByPoliza(poliza);
	}

	/**
	 * Obtiene una lista de los siniestros filtrado por cliente.
	 * 
	 * @param idCliente - Identificador.
	 * @return Devuelve un listado de siniestros filtrado por cliente en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaSiniestros/{idCliente}", method = RequestMethod.GET)
	public List<Siniestro> listaSiniestrosPorCliente(@PathVariable("idCliente") final long idCliente) {
		final Cliente cliente = clienteService.findById(idCliente);
		return siniestroService.findByCliente(cliente);
	}

}
