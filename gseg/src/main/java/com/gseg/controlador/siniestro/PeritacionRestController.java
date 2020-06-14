package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.siniestro.Peritacion;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.servicio.siniestro.PeritacionService;
import com.gseg.servicio.siniestro.SiniestroService;

/**
 * RestController de peritación.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class PeritacionRestController {

	/**
	 * Servicio de peritacion.
	 */
	@Autowired
	PeritacionService peritacionService;

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

	/**
	 * Obtiene una lista de peritaciones filtrada por siniestro.
	 * 
	 * @param idSiniestro - Identificador de siniestro.
	 * @return Devuelve un listado de peritaciones filtrado por siniestro en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaPeritaciones/{idSiniestro}", method = RequestMethod.GET)
	public List<Peritacion> listaPeritaciones(@PathVariable("idSiniestro") final long idSiniestro) {
		final Siniestro siniestro = siniestroService.findById(idSiniestro);
		return peritacionService.findBySiniestro(siniestro);
	}

}
