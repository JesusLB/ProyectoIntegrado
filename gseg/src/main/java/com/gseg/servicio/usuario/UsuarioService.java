package com.gseg.servicio.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.usuario.Usuario;
import com.gseg.repositorio.usuario.UsuarioRepository;

/**
 * Servicio usuario
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class UsuarioService {

	/**
	 * Repositorio de usuario.
	 */
	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * Guarda una entidad usuario.
	 * 
	 * @param usuario - Entidad usuario.
	 * @return Devuelve la entidad guardada.
	 */
	public Usuario saveUsuario(final Usuario usuario) {
		Long idUsuario = usuarioRepository.findMaxId();
		if (usuario == null) {
			// Si no hay usuarios, asignamos al primer id 1
			idUsuario = (long) 1;
			usuario.setId(idUsuario);
		} else {
			// Si hay usuarios y es un alta, incrementamos en 1 el último id
			if (usuario.getId() == 0) {
				idUsuario++;
				usuario.setId(idUsuario);
			}
		}
		return usuarioRepository.save(usuario);
	}

	/**
	 * Lista todos los usuarios.
	 * 
	 * @return Devuelve una lista de todas las entidades usuario.
	 */
	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	/**
	 * Localiza un usuario por su identificador(nif, cif, nie)
	 * 
	 * @param usuario - Identificador.
	 * @return Devuelve una entidad usuario.
	 */
	public Usuario findByUsuario(final String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	/**
	 * Localiza un usuario por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad usuario.
	 */
	public Usuario findById(final long id) {
		return usuarioRepository.findById(id);
	}

}
