package com.gseg.controlador.usuario;

import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.usuario.RolService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.UsuarioValidator;

/**
 * Controlador usuario.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/usuario")
public class UsuarioController {

	/**
	 * Vista para la gestión de usuarios.
	 */
	private final static String VISTA_GESTION = "admin/usuario/gestionUsuario";

	/**
	 * Vista para la edición de usuario.
	 */
	private final static String VISTA_EDICION = "admin/usuario/edicionUsuario";

	/**
	 * Redirección a la vista gestión usuario.
	 */
	private final static String REDIRECT_GESTION = "redirect:/gseg/usuario/gestion";

	/**
	 * Variable para mostrar mensaje de usuario guardado.
	 */
	private boolean guardadoCorrecto = false;

	/**
	 * Validador de usuario.
	 */
	@Autowired
	UsuarioValidator usuarioValidator;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de rol.
	 */
	@Autowired
	RolService rolService;

	/**
	 * Vista gestión de usuario.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión de usuario.
	 */
	@GetMapping("/gestion")
	public String gestionUsuario(final Model model) {
		model.addAttribute("guardadoCorrecto", guardadoCorrecto);
		guardadoCorrecto = false;
		permisos(model);
		return VISTA_GESTION;
	}

	/**
	 * Acción de nueva alta de usuario.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de usuario.
	 */
	@GetMapping("/alta")
	public String altaUsuario(final Model model) {
		final Usuario usuario = new Usuario();
		usuario.setFechaAlta(LocalDate.now());
		usuario.setActivo(Boolean.TRUE);
		cargarFormulario(model, usuario, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION;
	}

	/**
	 * Acción para guardar/editar un usuario.
	 * 
	 * @param model         - Modelo.
	 * @param usuarioForm   - Entidad Usuario.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el usuario, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarUsuario(final Model model, @Valid @ModelAttribute("usuarioForm") final Usuario usuarioForm,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION;

		// Validaciones complejas
		usuarioValidator.validate(usuarioForm, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormulario(model, usuarioForm, Boolean.FALSE);
			vista = VISTA_EDICION;
		} else {
			if (!usuarioForm.getActivo()) {
				usuarioForm.setFechaBaja(LocalDate.now());
			} else {
				usuarioForm.setFechaBaja(null);
			}
			guardadoCorrecto = true;
			usuarioService.saveUsuario(usuarioForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un usuario
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del usuario.
	 * @return Devuelve la vista edición de usuario.
	 */
	@GetMapping("/editar/{id}")
	public String editarUsuario(final Model model, @PathVariable("id") final long id) {
		final Usuario usuario = usuarioService.findById(id);
		cargarFormulario(model, usuario, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION;
	}

	/**
	 * Acción para ver un usuario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del usuario.
	 * @return - Devuelve la vista edición de usuario y modo solo lectura.
	 */
	@GetMapping("/ver/{id}")
	public String verUsuario(final Model model, @PathVariable("id") final long id) {
		final Usuario usuario = usuarioService.findById(id);
		cargarFormulario(model, usuario, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION;
	}

	/**
	 * Acción para activar un usuario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del usuario.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activar(final Model model, @PathVariable("id") final long id) {
		final Usuario usuario = usuarioService.findById(id);
		if (!usuario.getActivo()) {
			usuario.setActivo(true);
			usuario.setFechaBaja(null);
		}
		usuarioService.saveUsuario(usuario);
		permisos(model);
		return REDIRECT_GESTION;
	}

	/**
	 * Acción para desactivar un usuario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del usuario.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivar(final Model model, @PathVariable("id") final long id) {
		final Usuario usuario = usuarioService.findById(id);
		if (usuario.getActivo()) {
			usuario.setActivo(false);
			usuario.setFechaBaja(LocalDate.now());
		}
		usuarioService.saveUsuario(usuario);
		permisos(model);
		return REDIRECT_GESTION;
	}

	/**
	 * Método para cargar el formulario usuario.
	 * 
	 * @param model       - Modelo.
	 * @param usuario     - Entidad usuario.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormulario(final Model model, final Usuario usuario, final Boolean soloLectura) {
		model.addAttribute("usuarioForm", usuario);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaRoles", rolService.findAll());
	}

	/**
	 * Carga los permisos en el modelo según el rol del usuario.
	 * 
	 * @param model - Modelo.
	 */
	private void permisos(final Model model) {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = null;
		if (principal instanceof UserDetails) {
			user = (UserDetails) principal;
		}
		model.addAttribute("usuarioActive", Boolean.TRUE);
		final Usuario usu = usuarioService.findByUsuario(user.getUsername());
		model.addAttribute("nombreUsuario", (usu.getNombre() + " " + usu.getApe1() + " " + usu.getApe2()).trim());
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
		}
	}

}
