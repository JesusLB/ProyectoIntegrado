package com.gseg.servicio.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Departamento;
import com.gseg.repositorio.poliza.DepartamentoRepository;

/**
 * Servicio de departamento.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class DepartamentoService {

	/**
	 * Repositorio de departamento.
	 */
	@Autowired
	DepartamentoRepository departamentoRepository;

	/**
	 * Guarda una entidad departamento.
	 * 
	 * @param departamento - Entidad departamento.
	 * @return Devuelve la entidad guardada.
	 */
	public Departamento saveDepartamento(final Departamento departamento) {
		Long idDepartamento = departamentoRepository.findMaxId();		
		if (idDepartamento == null) {
			// Si no hay departamentos, asignamos al primer id 1
			idDepartamento = (long) 1;
			departamento.setId(idDepartamento);
		} else {
			if (departamento.getId() == 0) {
				// Si hay departamentos y es un alta, incrementamos en 1 el último id
				idDepartamento++;
				departamento.setId(idDepartamento);
			}
		}		
		return departamentoRepository.save(departamento);
	}

	/**
	 * Localiza un departamento por su identificador.
	 * 
	 * @param id - Identificador de departamento.
	 * @return Devuelve una entidad departamento.
	 */
	public Departamento findById(final long id) {
		return departamentoRepository.findById(id);
	}

	/**
	 * Obtiene una lista de departamentos filtrada por compañía.
	 * 
	 * @param cia - Entidad cia.
	 * @return Devuelve una lista de entidades departamentos.
	 */
	public List<Departamento> findByCia(final Cia cia) {
		return departamentoRepository.findByCia(cia);
	}

}
