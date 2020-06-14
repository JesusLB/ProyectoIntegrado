package com.gseg.repositorio.siniestro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.siniestro.Siniestro;

/**
 * Repositorio de siniestro.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface SiniestroRepository extends JpaRepository<Siniestro, Long> {

	/**
	 * Localiza una entidad siniestro por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad siniestro.
	 */
	Siniestro findById(long id);

	/**
	 * Obtiene un listado de siniestros filtrados por su póliza.
	 * 
	 * @param poliza - Entidad póliza.
	 * @return Devuelve un listad
	 */
	List<Siniestro> findByPoliza(Poliza poliza);

	/**
	 * Obtiene un listado de siniestros filtrados por su cliente.
	 * 
	 * @param cliente - Entidad cliente.
	 * @return Devuelve un listado de entidades siniestro filtrado por su cliente.
	 */
	List<Siniestro> findByCliente(Cliente cliente);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_STRO) FROM SINIESTROS", nativeQuery = true)
	Long findMaxId();

}
