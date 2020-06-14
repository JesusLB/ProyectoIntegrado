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

import com.gseg.enumerado.TipoEstadoRecibo;
import com.gseg.enumerado.TipoPago;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.recibo.Recibo;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.poliza.PolizaService;
import com.gseg.servicio.poliza.ReciboService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.ReciboValidator;

/**
 * Controlador de recibo.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/recibo")
public class ReciboController {

	/**
	 * Vista para la gestión de recibo.
	 */
	private final static String VISTA_GESTION_RECIBO = "gestor/recibo/gestionRecibo";

	/**
	 * Vista para la edición de recibo.
	 */
	private final static String VISTA_EDICION_RECIBO = "gestor/recibo/edicionRecibo";

	/**
	 * Redirección a la vista para la gestión de recibo.
	 */
	private final static String REDIRECT_GESTION_RECIBO = "redirect:/gseg/recibo/gestion";

	/**
	 * Variable para mostrar mensaje de recibo guardado.
	 */
	private boolean reciboGuardado = false;

	/**
	 * Servicio de recibo.
	 */
	@Autowired
	ReciboService reciboService;

	/**
	 * Servicio de poliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Validador de recibo.
	 */
	@Autowired
	ReciboValidator reciboValidator;

	/********************************************
	 * RECIBO
	 ********************************************/

	/**
	 * Vista gestión.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionRecibos(final Model model) {
		model.addAttribute("reciboGuardado", reciboGuardado);
		reciboGuardado = false;
		permisos(model);
		return VISTA_GESTION_RECIBO;
	}

	/**
	 * Acción de nueva alta de recibo.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de recibo.
	 */
	@GetMapping("/alta")
	public String altaRecibo(final Model model) {
		final Recibo recibo = new Recibo();
		recibo.setActivo(Boolean.TRUE);
		cargarFormularioRecibo(model, recibo, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_RECIBO;
	}

	/**
	 * Acción para guardar/editar un recibo.
	 * 
	 * @param model         - Modelo.
	 * @param recibo        - Entidad recibo.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el recibo, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarRecibo(final Model model, @Valid @ModelAttribute("reciboForm") final Recibo recibo,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_RECIBO;

		final Poliza poliza = polizaService.findById(recibo.getPoliza().getId());
		recibo.setPoliza(poliza);

		// Validaciones complejas
		reciboValidator.validate(recibo, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormularioRecibo(model, recibo, Boolean.FALSE);
			vista = VISTA_EDICION_RECIBO;
		} else {
			reciboGuardado = true;
			model.addAttribute("reciboGuardado", reciboGuardado);
			reciboService.saveRecibo(recibo);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para buscar/cargar una poliza.
	 * 
	 * @param model         - Modelo.
	 * @param recibo        - Entidad Recibo.
	 * @param bindingResult - Resultado al comprobar errores
	 * @return Devuelve la vista gestión.
	 */
	@PostMapping("/poliza")
	public String BuscarReciboPoliza(final Model model, @ModelAttribute("reciboForm") final Recibo recibo,
			final BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			// Validaciones complejas
			reciboValidator.validate(recibo, bindingResult);
			final Poliza poliza = polizaService.findByNumPoliza(recibo.getPoliza().getNumPoliza());
			recibo.setPoliza(poliza);
			recibo.setActivo(Boolean.TRUE);
		}
		cargarFormularioRecibo(model, recibo, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_RECIBO;
	}

	/**
	 * Acción para editar un recibo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del recibo.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarRamo(final Model model, @PathVariable("id") final long id) {
		final Recibo recibo = reciboService.findById(id);
		cargarFormularioRecibo(model, recibo, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_RECIBO;
	}

	/**
	 * Acción para ver un recibo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del recibo.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verRecibo(final Model model, @PathVariable("id") final long id) {
		final Recibo recibo = reciboService.findById(id);
		cargarFormularioRecibo(model, recibo, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_RECIBO;
	}

	/**
	 * Acción para activar un recibo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del recibo.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activarRecibo(final Model model, @PathVariable("id") final long id) {
		final Recibo recibo = reciboService.findById(id);
		if (!recibo.getActivo()) {
			recibo.setActivo(true);
		}
		reciboService.saveRecibo(recibo);
		permisos(model);
		return REDIRECT_GESTION_RECIBO;
	}

	/**
	 * Acción para desactivar un recibo.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del recibo.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivarRecibo(final Model model, @PathVariable("id") final long id) {
		final Recibo recibo = reciboService.findById(id);
		if (recibo.getActivo()) {
			recibo.setActivo(false);
		}
		reciboService.saveRecibo(recibo);
		permisos(model);
		return REDIRECT_GESTION_RECIBO;
	}

	/**
	 * Método para cargar el formulario recibo.
	 * 
	 * @param model       - Modelo.
	 * @param recibo      - Entidad recibo.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioRecibo(final Model model, final Recibo recibo, final Boolean soloLectura) {
		model.addAttribute("reciboForm", recibo);
		model.addAttribute("listaTipoPago", TipoPago.getListaTipoPago());
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaEstado", TipoEstadoRecibo.getListaTipoEstadoRecibo());
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
		model.addAttribute("reciboActive", Boolean.TRUE);
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
