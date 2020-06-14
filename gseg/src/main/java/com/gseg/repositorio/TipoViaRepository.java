package com.gseg.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.TipoVia;

/**
 * Repositorio tipo via.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface TipoViaRepository extends JpaRepository<TipoVia, Long> {

	/**
	 * Localiza una entidad tipo vía por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad de tipo vía.
	 */
	TipoVia findById(long id);

	/**
	 * Localiza una entidad tipo vía por su nombre.
	 * 
	 * @param nombre - Nombre de tipo de vía.
	 * @return Devuelve una entidad de tipo vía.
	 */
	TipoVia findByNombre(String nombre);

}
