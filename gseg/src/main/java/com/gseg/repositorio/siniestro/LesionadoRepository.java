package com.gseg.repositorio.siniestro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.siniestro.Lesionado;
import com.gseg.modelo.siniestro.Siniestro;

/**
 * Repositorio lesionado.
 * 
 * @author Jesús López Barragán.
 *
 */
@Repository
public interface LesionadoRepository extends JpaRepository<Lesionado, Long> {

	/**
	 * Repositorio de lesionado.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad lesionado.
	 */
	Lesionado findById(long id);

	/**
	 * Obtiene un listado de lesionados filtrado por siniestro.
	 * 
	 * @param siniestro - Entidad siniestro.
	 * @return Devuelve un listado de lesionados.
	 */
	List<Lesionado> findBySiniestro(Siniestro siniestro);

	/**
	 * Consulta nativa del identificador máximo de lesionados.
	 * 
	 * @return Devuelve el identificaor máximo de todos los lesionados.
	 */
	@Query(value = "SELECT MAX(ID_LESIONADO) FROM LESIONADOS", nativeQuery = true)
	Long findMaxId();

}
