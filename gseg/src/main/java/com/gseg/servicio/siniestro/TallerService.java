package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.siniestro.Taller;
import com.gseg.repositorio.siniestro.TallerRepository;

/**
 * Servicio de taller.
 * 
 * @author Jesús López Barragán.
 *
 */
@Service
public class TallerService {

	/**
	 * Repositorio de taller.
	 */
	@Autowired
	TallerRepository tallerRepository;

	/**
	 * Guarda una entidad taller.
	 * 
	 * @param taller - Entidad taller.
	 * @return Devuelve la entidad guardada.
	 */
	public Taller saveTaller(final Taller taller) {
		Long idTaller = tallerRepository.findMaxId();
		taller.setIdentificador(taller.getIdentificador().toUpperCase());
		if (idTaller == null) {
			// Si no hay talleres, asignamos al primer id 1
			idTaller = (long) 1;
			taller.setId(idTaller);
		} else {
			if (taller.getId() == 0) {
				// Si hay talleres y es un alta, incrementamos en 1 el último id
				idTaller++;
				taller.setId(idTaller);
			}
		}		
		return tallerRepository.save(taller);
	}

	/**
	 * Elimina una entidad taller.
	 * 
	 * @param taller - Entidad taller.
	 */
	public void deleteTaller(final Taller taller) {
		tallerRepository.delete(taller);
	}

	/**
	 * Localiza un taller por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad taller.
	 */
	public Taller findById(final long id) {
		return tallerRepository.findById(id);
	}

	/**
	 * Obtiene un listado de todos los talleres.
	 * 
	 * @return Devuelve un listado de todas las entidades taller.
	 */
	public List<Taller> findAll() {
		return tallerRepository.findAll();
	}

}
