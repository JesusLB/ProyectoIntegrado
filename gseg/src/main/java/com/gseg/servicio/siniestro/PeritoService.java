package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.siniestro.Perito;
import com.gseg.repositorio.siniestro.PeritoRepository;

/**
 * Servicio de perito.
 * 
 * @author Jesús López Barragán.
 *
 */
@Service
public class PeritoService {

	/**
	 * Repositorio de perito.
	 */
	@Autowired
	PeritoRepository peritoRepository;

	/**
	 * Guarda una entidad perito.
	 * 
	 * @param perito - Entidad perito.
	 * @return Devuelve la entidad guardada.
	 */
	public Perito savePerito(final Perito perito) {
		Long idPerito = peritoRepository.findMaxId();
		if (idPerito == null) {
			// Si no hay peritos, asignamos al primer id 1
			idPerito = (long) 1;
			perito.setId(idPerito);
		} else {
			if (perito.getId() == 0) {
				// Si hay peritos y es un alta, incrementamos en 1 el último id
				idPerito++;
				perito.setId(idPerito);
			}
		}		
		return peritoRepository.save(perito);
	}

	/**
	 * Elimina una entidad perito.
	 * 
	 * @param perito - Entidad perito.
	 */
	public void deletePerito(final Perito perito) {
		peritoRepository.delete(perito);
	}

	/**
	 * Localiza un perito por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad perito.
	 */
	public Perito findById(final long id) {
		return peritoRepository.findById(id);
	}

	/**
	 * Obtiene un listado de todos los peritos.
	 * 
	 * @return Devuelve un listado con todas las entidades perito.
	 */
	public List<Perito> findAll() {
		return peritoRepository.findAll();
	}

}
