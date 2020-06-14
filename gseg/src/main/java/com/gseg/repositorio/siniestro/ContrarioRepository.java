package com.gseg.repositorio.siniestro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.siniestro.Contrario;
import com.gseg.modelo.siniestro.Siniestro;

/**
 * Repositorio de contrario.
 * 
 * @author Jesús López Barragán.
 *
 */
@Repository
public interface ContrarioRepository extends JpaRepository<Contrario, Long> {

	/**
	 * Localiza un contrario por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad contrario.
	 */
	Contrario findById(long id);

	/**
	 * Obtiene una lista de contrarios filtrado por siniestro.
	 * 
	 * @param siniestro - Entidad Siniestro.
	 * @return Devuelve una lista de entidades contrario filtrado por siniestro.
	 */
	List<Contrario> findBySiniestro(Siniestro siniestro);

	/**
	 * Consulta nativa del identificador máximo de contrarios.
	 * 
	 * @return Devuelve el identificaor máximo de todos los contrarios.
	 */
	@Query(value = "SELECT MAX(ID_CONTRARIO) FROM CONTRARIOS", nativeQuery = true)
	Long findMaxId();

}
