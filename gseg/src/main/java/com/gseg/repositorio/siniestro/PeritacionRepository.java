package com.gseg.repositorio.siniestro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gseg.modelo.siniestro.Peritacion;
import com.gseg.modelo.siniestro.Siniestro;

public interface PeritacionRepository extends JpaRepository<Peritacion, Long> {

	/**
	 * Localiza una peritación por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad peritación.
	 */
	Peritacion findById(long id);

	/**
	 * Obtiene un listado de peritaciones filtradas por siniestro.
	 * 
	 * @param siniestro - Entidad siniestro.
	 * @return Devuelve un listado de entidades peritacion filtrado por siniestro.
	 */
	List<Peritacion> findBySiniestro(Siniestro siniestro);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_PERITACION) FROM PERITACIONES", nativeQuery = true)
	Long findMaxId();

}
