package com.gseg.repositorio.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gseg.modelo.usuario.Rol;

/**
 * Repositorio rol.
 * 
 * @author Jesús López Barragán
 *
 */
public interface RolRepository extends JpaRepository<Rol, Long> {

	/**
	 * Localiza un rol por su nombre.
	 * 
	 * @param nombre - Nombre del rol.
	 * @return Devuelve una entidad rol.
	 */
	Rol findByNombre(String nombre);

	/**
	 * Localiza un rol por su identificador.
	 * 
	 * @param id - Identificador de rol.
	 * @return Devuelve una entidad rol.
	 */
	Rol findById(long id);

	/**
	 * Consulta nativa del identificador máximo de roles.
	 * 
	 * @return Devuelve el identificaor máximo de todos los roles.
	 */
	@Query(value = "SELECT MAX(ID_ROL) FROM ROLES", nativeQuery = true)
	Long findMaxId();

}
