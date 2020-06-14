package com.gseg.repositorio.poliza;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.poliza.Ramo;

/**
 * Repositorio de ramo.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface RamoRepository extends JpaRepository<Ramo, Long> {

	/**
	 * Localiza un ramo por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad ramo.
	 */
	Ramo findById(long id);

	/**
	 * Cuenta el número total de clientes que existen en base de datos.
	 * 
	 * @return Devuelve el número total de clientes.
	 */
	@Query(value = "SELECT MAX(ID_RAMO) FROM RAMOS", nativeQuery = true)
	Long findMaxId();

}
