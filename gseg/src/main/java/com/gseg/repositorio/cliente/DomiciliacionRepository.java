package com.gseg.repositorio.cliente;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.cliente.Domiciliacion;

/**
 * Repositorio domiciliación
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface DomiciliacionRepository extends JpaRepository<Domiciliacion, Long> {

	/**
	 * Localiza una domiciliación por su identificador.
	 * 
	 * @param id - Identificador de la domiciliación.
	 * @return - Devuelve una entidad domiciliación.
	 */
	Domiciliacion findById(long id);

	/**
	 * Lista todas las domicliaciones de un cliente.
	 * 
	 * @param cliente - Entidad cliente.
	 * @return Devuelve una lista de domiciliaciones filtradas por cliente.
	 */
	List<Domiciliacion> findByCliente(Cliente cliente);

	/**
	 * Consulta nativa del identificador máximo de domiciliaciones.
	 * 
	 * @return Devuelve el identificaor máximo de todos los domicliaciones.
	 */
	@Query(value = "SELECT MAX(ID_DOMICILIACION) FROM DOMICILIACIONES", nativeQuery = true)
	Long findMaxId();

}
