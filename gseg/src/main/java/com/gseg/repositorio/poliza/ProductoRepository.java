package com.gseg.repositorio.poliza;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Producto;

/**
 * Repositorio de producto.
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	/**
	 * Localiza un producto por su identificador.
	 * 
	 * @param id - Identificador de producto.
	 * @return Devuelve una entidad producto.
	 */
	Producto findById(long id);

	/**
	 * Obtiene un listado de productos filtrado por compañía.
	 * 
	 * @param cia - Entidad cia.
	 * @return Devuelve un listado de entidades de producto.
	 */
	List<Producto> findByCia(Cia cia);

	/**
	 * Consulta nativa del identificador máximo de clientes.
	 * 
	 * @return Devuelve el identificaor máximo de todos los clientes.
	 */
	@Query(value = "SELECT MAX(ID_PRODUCTO) FROM PRODUCTOS", nativeQuery = true)
	Long findMaxId();

}
