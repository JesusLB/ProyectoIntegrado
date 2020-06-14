package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.siniestro.Lesionado;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.repositorio.siniestro.LesionadoRepository;

/**
 * Servicio de lesionado.
 * 
 * @author Jesús López Barragán.
 *
 */
@Service
public class LesionadoService {

	/**
	 * Repositorio de lesionado.
	 */
	@Autowired
	LesionadoRepository lesionadoRepository;

	/**
	 * Guarda una entidad lesionado.
	 * 
	 * @param cliente - Entidad lesionado.
	 * @return Devuelve la entidad guardada.
	 */
	public Lesionado saveLesionado(final Lesionado lesionado) {
		Long idLesionado = lesionadoRepository.findMaxId();
		lesionado.setIdentificador(lesionado.getIdentificador().toUpperCase());
		if (idLesionado == null) {
			// Si no hay lesionados, asignamos al primer id 1
			idLesionado = (long) 1;
			lesionado.setId(idLesionado);
		} else {
			if (lesionado.getId() == 0) {
				// Si hay lesionados y es un alta, incrementamos en 1 el último id
				idLesionado++;
				lesionado.setId(idLesionado);
			}
		}		
		return lesionadoRepository.save(lesionado);
	}

	/**
	 * Elimina una entidad lesionado.
	 * 
	 * @param lesionado - Entidad lesionado.
	 */
	public void deleteLesionado(final Lesionado lesionado) {
		lesionadoRepository.delete(lesionado);
	}

	/**
	 * Repositorio de lesionado.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad lesionado.
	 */
	public Lesionado findById(final long id) {
		return lesionadoRepository.findById(id);
	}

	/**
	 * Obtiene un listado de lesionados filtrado por siniestro.
	 * 
	 * @param siniestro - Entidad siniestro.
	 * @return Devuelve un listado de lesionados.
	 */
	public List<Lesionado> findBySiniestro(final Siniestro siniestro) {
		return lesionadoRepository.findBySiniestro(siniestro);
	}

}
