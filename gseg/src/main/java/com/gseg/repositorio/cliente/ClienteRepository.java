package com.gseg.repositorio.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.cliente.Cliente;

/**
 * Repositorio cliente
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	/**
	 * Localiza un cliente por su identificador(nif, nie, cif)
	 * 
	 * @param identificador - Identificador del cliente.
	 * @return Devuelve una entidad cliente.
	 */
	Cliente findByIdentificador(String identificador);

	/**
	 * Localiza un cliente por su identificador
	 * 
	 * @param id - Identificador del cliente.
	 * @return Devuelve una entidad cliente.
	 */
	Cliente findById(long id);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_CLIENTE) FROM CLIENTES", nativeQuery = true)
	Long findMaxId();

}
