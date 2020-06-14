package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.siniestro.Contrario;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.repositorio.siniestro.ContrarioRepository;

/**
 * Servicio contrario.
 * 
 * @author Jesús López Barragán.
 *
 */
@Service
public class ContrarioService {

	/**
	 * Repositorio de contrario.
	 */
	@Autowired
	ContrarioRepository contrarioRepository;

	/**
	 * Guarda una entidad contrario.
	 * 
	 * @param cliente - Entidad contrario.
	 * @return Devuelve la entidad guardada.
	 */
	public Contrario saveContrario(final Contrario contrario) {
		Long idContrario = contrarioRepository.findMaxId();
		contrario.setIdentificador(contrario.getIdentificador().toUpperCase());
		if (idContrario == null) {
			// Si no hay contrarios, asignamos al primer id 1
			idContrario = (long) 1;
			contrario.setId(idContrario);
		} else {
			if (contrario.getId() == 0) {
				// Si hay contrarios y es un alta, incrementamos en 1 el último id
				idContrario++;
				contrario.setId(idContrario);
			}
		}		
		return contrarioRepository.save(contrario);
	}

	/**
	 * Elimina una entidad contrario.
	 * 
	 * @param contrario - Entidad contrario.
	 */
	public void deleteContrario(final Contrario contrario) {
		contrarioRepository.delete(contrario);
	}

	/**
	 * Localiza un contrario por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad contrario.
	 */
	public Contrario findById(final long id) {
		return contrarioRepository.findById(id);
	}

	/**
	 * Obtiene una lista de contrarios filtrado por siniestro.
	 * 
	 * @param siniestro - Entidad Siniestro.
	 * @return Devuelve una lista de entidades contrario filtrado por siniestro.
	 */
	public List<Contrario> findBySiniestro(final Siniestro siniestro) {
		return contrarioRepository.findBySiniestro(siniestro);
	}

}
