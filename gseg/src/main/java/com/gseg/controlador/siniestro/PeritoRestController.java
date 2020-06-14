package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.siniestro.Perito;
import com.gseg.servicio.siniestro.PeritoService;

/**
 * RestController de perito.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class PeritoRestController {

	/**
	 * Servicio de perito.
	 */
	@Autowired
	PeritoService peritoService;

	/**
	 * Obtiene una lista de todos los peritos.
	 * 
	 * @return Devuelve un listado de todos los peritos en formato JSON.
	 */
	@RequestMapping(path = "/listaPeritos", method = RequestMethod.GET)
	public List<Perito> listaPeritos() {
		return peritoService.findAll();
	}

}
