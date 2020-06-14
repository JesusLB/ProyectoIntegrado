package com.gseg.repositorio.siniestro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.siniestro.ActJudicial;
import com.gseg.modelo.siniestro.Siniestro;

/**
 * Repositorio actuaciones judiciales.
 * 
 * @author Jesús López Barragán.
 *
 */
@Repository
public interface ActJudicialRepository extends JpaRepository<ActJudicial, Long> {

	/**
	 * Localiza una actuación judicial por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad de actuación judicial.
	 */
	ActJudicial findById(long id);

	/**
	 * Localiza una actuación judicial por su siniestro.
	 * 
	 * @param siniestro - Entidad Siniestro.
	 * @return Devuelve una entidad siniestro.
	 */
	List<ActJudicial> findBySiniestro(Siniestro siniestro);

	/**
	 * Consulta nativa del identificador máximo de siniestros.
	 * 
	 * @return Devuelve el identificaor máximo de todos los siniestros.
	 */
	@Query(value = "SELECT MAX(ID_JUICIO) FROM ACT_JUDICIALES", nativeQuery = true)
	Long findMaxId();

}
