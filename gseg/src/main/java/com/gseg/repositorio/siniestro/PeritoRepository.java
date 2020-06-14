package com.gseg.repositorio.siniestro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.siniestro.Perito;

/**
 * Repositorio perito.
 * 
 * @author Jesús López Barragán.
 *
 */
@Repository
public interface PeritoRepository extends JpaRepository<Perito, Long> {

	/**
	 * Localiza un perito por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad perito.
	 */
	Perito findById(long id);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_PERITO) FROM PERITOS", nativeQuery = true)
	Long findMaxId();

}
