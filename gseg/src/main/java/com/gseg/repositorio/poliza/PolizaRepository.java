package com.gseg.repositorio.poliza;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;

/**
 * Repositorio de póliza.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface PolizaRepository extends JpaRepository<Poliza, Long> {

	/**
	 * Localiza una póliza por su identificador.
	 * 
	 * @param id - Identificador de póliza.
	 * @return Devuelve una entidad póliza.
	 */
	Poliza findById(long id);

	/**
	 * Localiza una póliza por su cliente.
	 * 
	 * @param cliente - Entidad cliente.
	 * @return Devuelve una entidad póliza.
	 */
	List<Poliza> findByCliente(Cliente cliente);

	/**
	 * Localiza una póliza por su número.
	 * 
	 * @param numPoliza - Número de póliza.
	 * @return Devuelve una entidad póliza.
	 */
	Poliza findByNumPoliza(String numPoliza);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_POLIZA) FROM POLIZAS", nativeQuery = true)
	Long findMaxId();

}
