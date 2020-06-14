package com.gseg.controlador.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gseg.modelo.usuario.Usuario;
import com.gseg.repositorio.usuario.RolRepository;
import com.gseg.repositorio.usuario.UsuarioRepository;

/**
 * Controlador de login.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
public class LoginController {

	/**
	 * Vista de login.
	 */
	private static final String VISTA_LOGIN = "login";

	/**
	 * Redirección a la vista de inicio para gestor y administrador.
	 */
	private static final String REDIRECT_VISTA_INICIO = "redirect:/gseg/inicio/";

	/**
	 * Redirección a la vista de inicio para el usuario.
	 */
	private static final String REDIRECT_VISTA_INICIO_USUARIO = "redirect:/gseg/inicio/usuario";

	/**
	 * Repositorio de rol.
	 */
	@Autowired
	RolRepository rolRepository;

	/**
	 * Repositorio de usuario.
	 */
	@Autowired
	UsuarioRepository usuarioRepository;

	/**
	 * Vista de login.
	 * 
	 * @return Devuelve la vista login.
	 */
	@GetMapping({ "/", "/gseg/login" })
	public String login() {
		return VISTA_LOGIN;
	}

	/**
	 * Vista de logout.
	 * 
	 * @return Devuelve la vista login.
	 */
	@GetMapping("/gseg/logout")
	public String logout() {
		return VISTA_LOGIN;
	}

	/**
	 * Según el rol de usuario carga la vista que corresponda.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista que corresponda según el rol de usuario.
	 */
	@GetMapping("/gseg")
	public String inicio(final Model model) {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = null;
		if (principal instanceof UserDetails) {
			user = (UserDetails) principal;
		}
		final Usuario usu = usuarioRepository.findByUsuario(user.getUsername());
		String vista = REDIRECT_VISTA_INICIO;

		if (usu.getRol().getNombre().equals("ROLE_ADMIN")) {
			model.addAttribute("admin", Boolean.TRUE);
			model.addAttribute("gestor", Boolean.FALSE);
			model.addAttribute("usuario", Boolean.FALSE);
		} else if (usu.getRol().getNombre().equals("ROLE_GESTOR")) {
			model.addAttribute("admin", Boolean.FALSE);
			model.addAttribute("gestor", Boolean.TRUE);
			model.addAttribute("usuario", Boolean.FALSE);
		} else if (usu.getRol().getNombre().equals("ROLE_USUARIO")) {
			model.addAttribute("admin", Boolean.FALSE);
			model.addAttribute("gestor", Boolean.FALSE);
			model.addAttribute("usuario", Boolean.TRUE);
			vista = REDIRECT_VISTA_INICIO_USUARIO;
		}
		return vista;
	}

}
