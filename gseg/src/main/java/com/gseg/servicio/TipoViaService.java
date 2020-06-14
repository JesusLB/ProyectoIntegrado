package com.gseg.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.TipoVia;
import com.gseg.repositorio.TipoViaRepository;

/**
 * Servicio tipo de via.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class TipoViaService {

	/**
	 * Repositorio de tipo de vía.
	 */
	@Autowired
	TipoViaRepository repositorio;

	/**
	 * Localiza una entidad tipo vía por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad de tipo vía.
	 */
	public TipoVia findById(final long id) {
		return repositorio.findById(id);
	}

	/**
	 * Localiza una entidad tipo vía por su nombre.
	 * 
	 * @param nombre - Nombre de tipo de vía.
	 * @return Devuelve una entidad de tipo vía.
	 */
	public TipoVia findByNombre(final String nombre) {
		return repositorio.findByNombre(nombre);
	}

	/**
	 * Obtiene un listado de todos los tipos de vías.
	 * 
	 * @return Devuelve un listado de todas las entidades tipo de vía.
	 */
	public List<TipoVia> findAll() {
		return repositorio.findAll();
	}

}
