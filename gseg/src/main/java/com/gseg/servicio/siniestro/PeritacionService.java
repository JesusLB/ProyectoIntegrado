package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.siniestro.Peritacion;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.repositorio.siniestro.PeritacionRepository;

/**
 * Servicio de peritación.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class PeritacionService {

	/**
	 * Repositorio de peritacion.
	 */
	@Autowired
	PeritacionRepository peritacionRepository;

	/**
	 * Guarda una entidad peritación.
	 * 
	 * @param cliente - Entidad peritacion.
	 * @return Devuelve la entidad guardada.
	 */
	public Peritacion savePeritacion(final Peritacion peritacion) {
		Long idPeritacion = peritacionRepository.findMaxId();
		if (idPeritacion == null) {
			// Si no hay peritaciones, asignamos al primer id 1
			idPeritacion = (long) 1;
			peritacion.setId(idPeritacion);
		} else {
			if (peritacion.getId() == 0) {
				// Si hay peritaciones y es un alta, incrementamos en 1 el último id
				idPeritacion++;
				peritacion.setId(idPeritacion);
			}
		}		
		return peritacionRepository.save(peritacion);
	}

	/**
	 * Elimina una entidad peritación.
	 * 
	 * @param peritacion - Entidad peritación.
	 */
	public void deletePeritacion(final Peritacion peritacion) {
		peritacionRepository.delete(peritacion);
	}

	/**
	 * Localiza una peritación por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad peritación.
	 */
	public Peritacion findById(final long id) {
		return peritacionRepository.findById(id);
	}

	/**
	 * Obtiene un listado de peritaciones filtradas por siniestro.
	 * 
	 * @param siniestro - Entidad siniestro.
	 * @return Devuelve un listado de entidades peritacion filtrado por siniestro.
	 */
	public List<Peritacion> findBySiniestro(final Siniestro siniestro) {
		return peritacionRepository.findBySiniestro(siniestro);
	}

}
