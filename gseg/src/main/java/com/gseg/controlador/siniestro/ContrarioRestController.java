package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.siniestro.Contrario;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.servicio.siniestro.ContrarioService;
import com.gseg.servicio.siniestro.SiniestroService;

/**
 * RestController de contrario.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class ContrarioRestController {

	/**
	 * Servicio de contrario.
	 */
	@Autowired
	ContrarioService contrarioService;

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

	/**
	 * Obtiene una lista de contrarios filtrado por siniestro.
	 * 
	 * @param idSiniestro - Identificador de siniestro.
	 * @return Devuelve un listado de contrarios filtrado por siniestro en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaContrarios/{idSiniestro}", method = RequestMethod.GET)
	public List<Contrario> listaContrarios(@PathVariable("idSiniestro") final long idSiniestro) {
		final Siniestro siniestro = siniestroService.findById(idSiniestro);
		return contrarioService.findBySiniestro(siniestro);
	}

}
