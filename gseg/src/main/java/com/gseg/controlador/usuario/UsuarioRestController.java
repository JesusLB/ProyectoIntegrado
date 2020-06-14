package com.gseg.controlador.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.usuario.UsuarioService;

/**
 * RestController de usuario.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class UsuarioRestController {

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Lista todos los usuarios.
	 * 
	 * @return Devuelve una lista de usuarios en formato JSON.
	 */
	@RequestMapping(path = "/listaUsuarios", method = RequestMethod.GET)
	public List<Usuario> listaUsuarios() {
		return usuarioService.findAll();
	}

}
