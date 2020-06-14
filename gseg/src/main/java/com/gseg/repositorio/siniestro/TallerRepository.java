package com.gseg.repositorio.siniestro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gseg.modelo.siniestro.Taller;

public interface TallerRepository extends JpaRepository<Taller, Long> {

	/**
	 * Localiza un taller por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad taller.
	 */
	Taller findById(long id);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_TALLER) FROM TALLERES", nativeQuery = true)
	Long findMaxId();

}
