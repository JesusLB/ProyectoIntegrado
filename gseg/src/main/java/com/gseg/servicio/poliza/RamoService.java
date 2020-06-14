package com.gseg.servicio.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.poliza.Ramo;
import com.gseg.repositorio.poliza.RamoRepository;

/**
 * Servicio de ramo.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class RamoService {

	/**
	 * Repositorio de ramo.
	 */
	@Autowired
	RamoRepository ramoRepository;

	/**
	 * Guarda una entidad ramo.
	 * 
	 * @param ramo - Entidad ramo.
	 * @return Devuelve la entidad guardada.
	 */
	public Ramo saveRamo(final Ramo ramo) {
		Long idRamo = ramoRepository.findMaxId();
		if (idRamo == null) {
			// Si no hay ramos, asignamos al primer id 1
			idRamo = (long) 1;
			ramo.setId(idRamo);
		} else {
			if (ramo.getId() == 0) {
				// Si hay ramos y es un alta, incrementamos en 1 el último id
				idRamo++;
				ramo.setId(idRamo);
			}
		}
		return ramoRepository.save(ramo);
	}

	/**
	 * Localiza un ramo por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad ramo.
	 */
	public Ramo findById(final long id) {
		return ramoRepository.findById(id);
	}

	/**
	 * Obtiene una lista de todos los ramos.
	 * 
	 * @return Devuelve una lista de entidades ramo.
	 */
	public List<Ramo> findAll() {
		return ramoRepository.findAll();
	}

}
