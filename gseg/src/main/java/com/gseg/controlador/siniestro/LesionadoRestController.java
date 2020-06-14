package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.siniestro.Lesionado;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.servicio.siniestro.LesionadoService;
import com.gseg.servicio.siniestro.SiniestroService;

/**
 * RestController de lesionado.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class LesionadoRestController {

	/**
	 * Servicio de lesionado.
	 */
	@Autowired
	LesionadoService lesionadoService;

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

	/**
	 * Obtiene una lista de lesionados filtrado por siniestro.
	 * 
	 * @param idSiniestro - Identificador de siniestro.
	 * @return Devuelve un listado de lesionado filtrado por siniestro en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaLesionados/{idSiniestro}", method = RequestMethod.GET)
	public List<Lesionado> listaLesionado(@PathVariable("idSiniestro") final long idSiniestro) {
		final Siniestro siniestro = siniestroService.findById(idSiniestro);
		return lesionadoService.findBySiniestro(siniestro);
	}

}
