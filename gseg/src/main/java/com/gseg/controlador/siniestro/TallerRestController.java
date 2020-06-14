package com.gseg.controlador.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.siniestro.Taller;
import com.gseg.servicio.siniestro.TallerService;

/**
 * RestController de taller.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class TallerRestController {

	/**
	 * Servicio de taller.
	 */
	@Autowired
	TallerService tallerService;

	/**
	 * Obtiene una lista de todos los talleres.
	 * 
	 * @return Devuelve un listado de todos los talleres en formato JSON.
	 */
	@RequestMapping(path = "/listaTalleres", method = RequestMethod.GET)
	public List<Taller> listaTalleres() {
		return tallerService.findAll();
	}

}
