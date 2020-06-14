package com.gseg.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.Provincia;

/**
 * Repositorio provincia
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

	/**
	 * Localiza una provincia por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad provincia.
	 */
	Provincia findById(long id);

	/**
	 * Localiza una provincia por su nombre.
	 * 
	 * @param nombre - Nombre de la provincia.
	 * @return Devuelve una entidad provincia.
	 */
	Provincia findByNombre(String nombre);

}
