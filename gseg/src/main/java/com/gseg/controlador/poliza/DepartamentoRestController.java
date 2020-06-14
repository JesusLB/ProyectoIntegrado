package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Departamento;
import com.gseg.servicio.poliza.CiaService;
import com.gseg.servicio.poliza.DepartamentoService;

/**
 * RestController de departamento.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class DepartamentoRestController {

	/**
	 * Servicio de departamento.
	 */
	@Autowired
	DepartamentoService departamentoService;

	/**
	 * Servicio de cia.
	 */
	@Autowired
	CiaService ciaService;

	/**
	 * Obtiene una lista de departamentos filtrado por compañía.
	 * 
	 * @param idCia - Identificador de compañía.
	 * @return Devuelve una lista de departamentos filtrado por compañía en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaDepartamentos/{idCia}", method = RequestMethod.GET)
	public List<Departamento> listaDepartamentos(@PathVariable("idCia") final long idCia) {
		final Cia cia = ciaService.findById(idCia);
		return departamentoService.findByCia(cia);
	}

}
