package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.poliza.Ramo;
import com.gseg.servicio.poliza.RamoService;

/**
 * RestController de ramo.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class RamoRestController {

	/**
	 * Servicio de ramo.
	 */
	@Autowired
	RamoService ramoService;

	/**
	 * Obtiene una lista de todos los ramos.
	 * 
	 * @return Devuelve un listado de todos los ramos en formato JSON.
	 */
	@RequestMapping(path = "/listaRamos", method = RequestMethod.GET)
	public List<Ramo> listaRamos() {
		return ramoService.findAll();
	}

}
