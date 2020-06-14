package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.poliza.Cia;
import com.gseg.servicio.poliza.CiaService;

/**
 * RestController de cia.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class CiaRestController {

	/**
	 * Servicio de cia.
	 */
	@Autowired
	CiaService ciaService;

	/**
	 * Lista todas las compañías.
	 * 
	 * @return Devuelve una lista de todas las compañías en formato JSON.
	 */
	@RequestMapping(path = "/listaCias", method = RequestMethod.GET)
	public List<Cia> listaCias() {
		return ciaService.findAll();
	}

}
