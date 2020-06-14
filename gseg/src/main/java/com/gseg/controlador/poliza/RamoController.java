package com.gseg.controlador.poliza;

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

import com.gseg.modelo.poliza.Ramo;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.poliza.RamoService;
import com.gseg.servicio.usuario.UsuarioService;

/**
 * Controlador ramo.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/ramo")
public class RamoController {

	/**
	 * Vista para la gestión de ramo.
	 */
	private final static String VISTA_GESTION_RAMO = "gestor/ramo/gestionRamo";

	/**
	 * Vista para la edición de ramo.
	 */
	private final static String VISTA_EDICION_RAMO = "gestor/ramo/edicionRamo";

	/**
	 * Redirección a la vista para la gestión de ramo.
	 */
	private final static String REDIRECT_GESTION_RAMO = "redirect:/gseg/ramo/gestion";

	/**
	 * Variable para mostrar mensaje de ramo guardado.
	 */
	private boolean ramoGuardado = false;

	/**
	 * Servicio de ramo.
	 */
	@Autowired
	RamoService ramoService;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/********************************************
	 * RAMO
	 ********************************************/

	/**
	 * Vista gestión.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionRamos(final Model model) {
		model.addAttribute("ramoGuardado", ramoGuardado);
		ramoGuardado = false;
		permisos(model);
		return VISTA_GESTION_RAMO;
	}

	/**
	 * Acción de nueva alta de ramo.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de ramo.
	 */
	@GetMapping("/alta")
	public String altaRamo(final Model model) {
		final Ramo ramo = new Ramo();
		ramo.setActivo(Boolean.TRUE);
		cargarFormularioRamo(model, ramo, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_RAMO;
	}

	/**
	 * Acción para guardar/editar un ramo.
	 * 
	 * @param model         - Modelo.
	 * @param ramo          - Entidad ramo.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el ramo, si hay
	 *         errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarRamo(final Model model, @Valid @ModelAttribute("ramoForm") final Ramo ramo,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_RAMO;

		if (bindingResult.hasErrors()) {
			cargarFormularioRamo(model, ramo, Boolean.FALSE);
			vista = VISTA_EDICION_RAMO;
		} else {
			ramoGuardado = true;
			model.addAttribute("ramoGuardado", ramoGuardado);
			ramoService.saveRamo(ramo);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un ramo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de ramo.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarRamo(final Model model, @PathVariable("id") final long id) {
		final Ramo ramo = ramoService.findById(id);
		cargarFormularioRamo(model, ramo, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_RAMO;
	}

	/**
	 * Acción para ver un ramo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de ramo.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verRamo(final Model model, @PathVariable("id") final long id) {
		final Ramo ramo = ramoService.findById(id);
		cargarFormularioRamo(model, ramo, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_RAMO;
	}

	/**
	 * Acción para activar un ramo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de ramo.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activarRamo(final Model model, @PathVariable("id") final long id) {
		final Ramo ramo = ramoService.findById(id);
		if (!ramo.getActivo()) {
			ramo.setActivo(true);
		}
		ramoService.saveRamo(ramo);
		permisos(model);
		return REDIRECT_GESTION_RAMO;
	}

	/**
	 * Acción para desactivar un ramo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del ramo.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivarRamo(final Model model, @PathVariable("id") final long id) {
		final Ramo ramo = ramoService.findById(id);
		if (ramo.getActivo()) {
			ramo.setActivo(false);
		}
		ramoService.saveRamo(ramo);
		permisos(model);
		return REDIRECT_GESTION_RAMO;
	}

	/**
	 * Método para cargar el formulario ramo.
	 * 
	 * @param model       - Modelo.
	 * @param usuario     - Entidad ramo.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioRamo(final Model model, final Ramo ramo, final Boolean soloLectura) {
		model.addAttribute("ramoForm", ramo);
		model.addAttribute("soloLectura", soloLectura);
	}

	/********************************************
	 * PERMISOS
	 ********************************************/

	/**
	 * Carga los permisos en el modelo según el rol del usuario.
	 * 
	 * @param model - Modelo
	 */
	private void permisos(final Model model) {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = null;
		if (principal instanceof UserDetails) {
			user = (UserDetails) principal;
		}
		final Usuario usu = usuarioService.findByUsuario(user.getUsername());
		model.addAttribute("ramoActive", Boolean.TRUE);
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
