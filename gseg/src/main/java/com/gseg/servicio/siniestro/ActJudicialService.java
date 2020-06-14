package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.siniestro.ActJudicial;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.repositorio.siniestro.ActJudicialRepository;

/**
 * Servicio actuaciones judiciales.
 * 
 * @author Jesús López Barragán.
 *
 */
@Service
public class ActJudicialService {

	/**
	 * Repositorio de actuación judicial.
	 */
	@Autowired
	ActJudicialRepository actJudicialRepository;

	/**
	 * Guarda una entidad actuación judicial.
	 * 
	 * @param cliente - Entidad actuación judicial.
	 * @return Devuelve la entidad guardada.
	 */
	public ActJudicial saveActJudicial(final ActJudicial actJucial) {
		Long idActJudicial = actJudicialRepository.findMaxId();
		if (idActJudicial == null) {
			// Si no hay actuaciones judiciales, asignamos al primer id 1
			idActJudicial = (long) 1;
			actJucial.setId(idActJudicial);
		} else {
			if (actJucial.getId() == 0) {
				// Si hay actuaciones judiciales y es un alta, incrementamos en 1 el último id
				idActJudicial++;
				actJucial.setId(idActJudicial);
			}
		}		
		return actJudicialRepository.save(actJucial);
	}

	/**
	 * Elimina una entidad actuación judicial.
	 * 
	 * @param actJudicial - Entidad actuación judicial.
	 */
	public void deleteActJudicial(final ActJudicial actJudicial) {
		actJudicialRepository.delete(actJudicial);
	}

	/**
	 * Localiza una actuación judicial por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad de actuación judicial.
	 */
	public ActJudicial findById(final long id) {
		return actJudicialRepository.findById(id);
	}

	/**
	 * Localiza una actuación judicial por su siniestro.
	 * 
	 * @param siniestro - Entidad Siniestro.
	 * @return Devuelve una entidad siniestro.
	 */
	public List<ActJudicial> findBySiniestro(final Siniestro siniestro) {
		return actJudicialRepository.findBySiniestro(siniestro);
	}

}
