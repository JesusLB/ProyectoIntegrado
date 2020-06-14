package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.recibo.Recibo;
import com.gseg.servicio.poliza.PolizaService;
import com.gseg.servicio.poliza.ReciboService;

/**
 * RestController de recibo.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class ReciboRestController {

	/**
	 * Servicio de recibo.
	 */
	@Autowired
	ReciboService reciboService;

	/**
	 * Servicio de póliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Obtiene una lista de todos los recibos.
	 * 
	 * @return Devuelve un listado de todos los recibos en formato JSON.
	 */
	@RequestMapping(path = "/listaRecibos", method = RequestMethod.GET)
	public List<Recibo> listaRecibos() {
		return reciboService.findAll();
	}

	/**
	 * Obtiene una lista de recibos filtrado por póliza.
	 * 
	 * @param idPoliza - Identificador de póliza.
	 * @return Devuelve un listado de recibos filtrado por póliza en forma JSON.
	 */
	@RequestMapping(path = "/listaRecibos/{idPoliza}", method = RequestMethod.GET)
	public List<Recibo> listaRecibos(@PathVariable("idPoliza") final long idPoliza) {
		final Poliza poliza = polizaService.findById(idPoliza);
		return reciboService.findByPoliza(poliza);
	}

}
