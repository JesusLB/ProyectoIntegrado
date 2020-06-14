package com.gseg.servicio.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.poliza.ContactoDepartamento;
import com.gseg.modelo.poliza.Departamento;
import com.gseg.repositorio.poliza.ContactoDepartamentoRepository;

/**
 * Servicio contacto de departamento.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class ContactoDepartamentoService {

	/**
	 * Repositorio contacto de departamento.
	 */
	@Autowired
	ContactoDepartamentoRepository contactoDepartamentoRepository;

	/**
	 * Guarda una entidad contacto departamento.
	 * 
	 * @param contactoDepartamento - Entidad contacto departamento.
	 * @return Devuelve la entidad guardada.
	 */
	public ContactoDepartamento saveContactoDepartamento(final ContactoDepartamento contactoDepartamento) {
		Long idContacto = contactoDepartamentoRepository.findMaxId();		
		if (idContacto == null) {
			// Si no hay contactos, asignamos al primer id 1
			idContacto = (long) 1;
			contactoDepartamento.setId(idContacto);
		} else {
			if (contactoDepartamento.getId() == 0) {
				// Si hay contactos y es un alta, incrementamos en 1 el último id
				idContacto++;
				contactoDepartamento.setId(idContacto);
			}
		}		
		return contactoDepartamentoRepository.save(contactoDepartamento);
	}

	/**
	 * Elimina una entidad contacto departamento.
	 * 
	 * @param contactoDepartamento - Entidad contacto departamento.
	 */
	public void deleteContactoDepartamento(final ContactoDepartamento contactoDepartamento) {
		contactoDepartamentoRepository.delete(contactoDepartamento);
	}

	/**
	 * Localiza un contacto de departamento por su identificador.
	 * 
	 * @param id - Identificador de contacto departamento.
	 * @return Devuelve una entidad contacto departamento.
	 */
	public ContactoDepartamento findById(final long id) {
		return contactoDepartamentoRepository.findById(id);
	}

	/**
	 * Obtiene una lista de contactos de departamento filtrada por departamento.
	 * 
	 * @param departamento - Entidad departamento.
	 * @return Devuelve una lista de entidades de contacto.
	 */
	public List<ContactoDepartamento> findByDepartamento(final Departamento departamento) {
		return contactoDepartamentoRepository.findByDepartamento(departamento);
	}

}
