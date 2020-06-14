package com.gseg.repositorio.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.usuario.Usuario;

/**
 * Repositorio usuario
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Localiza un usuario por su identificador(nif, nie, cif)
	 * 
	 * @param usuario - Identificador del usuario.
	 * @return Devuelve una entidad usuario.
	 */
	Usuario findByUsuario(String usuario);

	/**
	 * Loclaliza un usuario por su identificador(nif, nie, cif)
	 * 
	 * @param id - Identificador del usuario.
	 * @return Devuelve una entidad usuario.
	 */
	Usuario findById(long id);

	/**
	 * Consulta nativa del identificador máximo de usuarios.
	 * 
	 * @return Devuelve el identificaor máximo de todos los usuarios.
	 */
	@Query(value = "SELECT MAX(ID_USUARIO) FROM USUARIOS", nativeQuery = true)
	Long findMaxId();

}
