package com.gseg.repositorio.poliza;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.recibo.Recibo;

/**
 * Repositorio de recibo.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Long> {

	/**
	 * Localiza un recibo por su identificador.
	 * 
	 * @param id - Identificador de recibo.
	 * @return Devuelve una entidad recibo.
	 */
	Recibo findById(long id);

	/**
	 * Obtiene un listado de recibos filtrados por póliza.
	 * 
	 * @param poliza - Entidad póliza.
	 * @return Devuelve un listado de entidades recibo filtrados por póliza.
	 */
	List<Recibo> findByPoliza(Poliza poliza);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_RECIBO) FROM RECIBOS", nativeQuery = true)
	Long findMaxId();

}
