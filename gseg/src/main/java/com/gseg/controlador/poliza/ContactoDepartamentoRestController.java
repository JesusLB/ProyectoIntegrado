package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.poliza.ContactoDepartamento;
import com.gseg.modelo.poliza.Departamento;
import com.gseg.servicio.poliza.ContactoDepartamentoService;
import com.gseg.servicio.poliza.DepartamentoService;

/**
 * RestController de contacto de departamento.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class ContactoDepartamentoRestController {

	/**
	 * Servicio de contacto de departamento.
	 */
	@Autowired
	ContactoDepartamentoService contactoDepartamentoService;

	/**
	 * Servicio de departamento.
	 */
	@Autowired
	DepartamentoService departamentoService;

	/**
	 * Lista los contactos de un departamento.
	 * 
	 * @param idDepartamento - Identificador de departamento.
	 * @return Devuelve una lista de los contactos filtrados por departamento en
	 *         formato JSON.
	 */
	@RequestMapping(path = "/listaContactoDepartamento/{idDepartamento}", method = RequestMethod.GET)
	public List<ContactoDepartamento> listaContactoDepartamentos(
			@PathVariable("idDepartamento") final long idDepartamento) {
		final Departamento departamento = departamentoService.findById(idDepartamento);
		return contactoDepartamentoService.findByDepartamento(departamento);
	}

}
