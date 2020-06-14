package com.gseg.repositorio.poliza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.poliza.Cia;

/**
 * Repositorio compañía
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface CiaRepository extends JpaRepository<Cia, Long> {

	/**
	 * Localiza una compañía por su identificador
	 * 
	 * @param id - Identificador de la compañía.
	 * @return Devuelve una entidad cia.
	 */
	Cia findById(long id);

	/**
	 * Localiza una compañía por su identificador (cif).
	 * 
	 * @param identificador - Identificador de la compañía.
	 * @return Devuelve una entidad cia.
	 */
	Cia findByIdentificador(String identificador);

	/**
	 * Consulta nativa del identificador mázimo de cias.
	 * 
	 * @return Devuelve el identificador máximo de todos las cias.
	 */
	@Query(value = "SELECT MAX(ID_CIA) FROM CIAS", nativeQuery = true)
	Long findMaxId();

}
