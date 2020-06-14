package com.gseg.servicio.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.poliza.Cia;
import com.gseg.repositorio.poliza.CiaRepository;

/**
 * Servicio compañía.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class CiaService {

	/**
	 * Repositorio de compañía.
	 */
	@Autowired
	CiaRepository ciaRepository;

	/**
	 * Guarda una entidad compañía.
	 * 
	 * @param cia - Entidad cia.
	 * @return Devuelve la entidad guardada.
	 */
	public Cia saveCia(final Cia cia) {
		Long idCia = ciaRepository.findMaxId();
		cia.setIdentificador(cia.getIdentificador().toUpperCase());
		if (idCia == null) {
			// Si no hay compañías, asignamos al primer id 1
			idCia = (long) 1;
			cia.setId(idCia);
		} else {
			// Si hay compañías y es un alta, incrementamos en 1 el último id
			if (cia.getId() == 0) {
				idCia++;
				cia.setId(idCia);
			}
		}		
		return ciaRepository.save(cia);
	}

	/**
	 * Localiza una compañía por su identificador
	 * 
	 * @param id - Identificador de la compañía.
	 * @return Devuelve una entidad cia.
	 */
	public Cia findById(final long id) {
		return ciaRepository.findById(id);
	}

	/**
	 * Localiza una compañía por su identificador (cif).
	 * 
	 * @param identificador - Identificador de la compañía.
	 * @return Devuelve una entidad cia.
	 */
	public List<Cia> findAll() {
		return ciaRepository.findAll();
	}

	/**
	 * Cuenta el número total de cias que existen en base de datos.
	 * 
	 * @return Devuelve el número total de cias.
	 */
	public Cia findByIdentificador(final String identificador) {
		return ciaRepository.findByIdentificador(identificador);
	}

}
