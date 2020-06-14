package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.siniestro.ActJudicial;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.servicio.siniestro.ActJudicialService;
import com.gseg.servicio.siniestro.SiniestroService;

/**
 * RestController de actuación judicial.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class ActJudicialRestController {

	/**
	 * Servicio de actuación judicial.
	 */
	@Autowired
	ActJudicialService actJudicialService;

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

	/**
	 * Obtiene una lista de actuaciones judiciales filtrado por siniestro.
	 * 
	 * @param idSiniestro - Identificador de siniestro.
	 * @return Devuelve un listado de actuaciones judiciales filtrado por siniestro
	 *         en formato JSON.
	 */
	@RequestMapping(path = "/listaActJudiciales/{idSiniestro}", method = RequestMethod.GET)
	public List<ActJudicial> listaActJudiciales(@PathVariable("idSiniestro") final long idSiniestro) {
		final Siniestro siniestro = siniestroService.findById(idSiniestro);
		return actJudicialService.findBySiniestro(siniestro);
	}

}
