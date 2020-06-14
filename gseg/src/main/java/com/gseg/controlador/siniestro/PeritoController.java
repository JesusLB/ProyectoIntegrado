package com.gseg.controlador.siniestro;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.siniestro.Perito;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.MunicipioService;
import com.gseg.servicio.ProvinciaService;
import com.gseg.servicio.TipoViaService;
import com.gseg.servicio.siniestro.PeritoService;
import com.gseg.servicio.usuario.UsuarioService;

/**
 * Controlador perito.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/perito")
public class PeritoController {

	/**
	 * Vista para la gestión de perito.
	 */
	private final static String VISTA_GESTION_PERITO = "gestor/perito/gestionPerito";

	/**
	 * Vista para la edición de perito.
	 */
	private final static String VISTA_EDICION_PERITO = "gestor/perito/edicionPerito";

	/**
	 * Redirección a la vista para la gestión de perito.
	 */
	private final static String REDIRECT_GESTION_PERITO = "redirect:/gseg/perito/gestion";

	/**
	 * Variable para mostrar mensaje perito guardado.
	 */
	private boolean peritoGuardado = false;

	/**
	 * Variable para mostrar mensaje perito eliminado.
	 */
	private boolean peritoEliminado = false;

	/**
	 * Servicio de perito.
	 */
	@Autowired
	PeritoService peritoService;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de provincia.
	 */
	@Autowired
	ProvinciaService provinciaService;

	/**
	 * Servicio de municipio.
	 */
	@Autowired
	MunicipioService municipioService;

	/**
	 * Servicio de tipo de vía.
	 */
	@Autowired
	TipoViaService tipoViaService;

	/********************************************
	 * PERITO
	 ********************************************/

	/**
	 * Vista gestión.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionPeritos(final Model model) {
		model.addAttribute("peritoGuardado", peritoGuardado);
		model.addAttribute("peritoEliminado", peritoEliminado);
		peritoGuardado = false;
		peritoEliminado = false;
		permisos(model);
		return VISTA_GESTION_PERITO;
	}

	/**
	 * Acción de nueva alta de perito.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de perito.
	 */
	@GetMapping("/alta")
	public String altaPerito(final Model model) {
		final Perito perito = new Perito();
		cargarFormularioPerito(model, perito, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_PERITO;
	}

	/**
	 * Acción para guardar/editar un perito.
	 * 
	 * @param model         - Modelo.
	 * @param perito        - Entidad perito.
	 * @param bindingResult - Resultado al comprobar errores
	 * @return Devuelve la vista gestión si se guarda correctamente el perito, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarPerito(final Model model, @Valid @ModelAttribute("peritoForm") final Perito peritoForm,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_PERITO;

		if (bindingResult.hasErrors()) {
			cargarFormularioPerito(model, peritoForm, Boolean.FALSE);
			vista = VISTA_EDICION_PERITO;
		} else {
			peritoGuardado = true;
			peritoForm.setTelefono(peritoForm.getTelefono().replaceAll(" ", ""));
			peritoForm.setMovil(peritoForm.getMovil().replaceAll(" ", ""));
			peritoService.savePerito(peritoForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un perito.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del perito.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarPerito(final Model model, @PathVariable("id") final long id) {
		final Perito perito = peritoService.findById(id);
		cargarFormularioPerito(model, perito, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_PERITO;
	}

	/**
	 * Acción para ver un perito.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de perito.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verPerito(final Model model, @PathVariable("id") final long id) {
		final Perito perito = peritoService.findById(id);
		cargarFormularioPerito(model, perito, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_PERITO;
	}

	/**
	 * Acción para eliminar un perito.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de perito.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/eliminar/{id}")
	public String eliminarPerito(final Model model, @PathVariable("id") final long id) {
		final Perito perito = peritoService.findById(id);
		if (perito != null) {
			peritoService.deletePerito(perito);
		}
		permisos(model);
		peritoEliminado = true;
		return REDIRECT_GESTION_PERITO;
	}

	/**
	 * Método para cargar el formulario de perito.
	 * 
	 * @param model       - Modelo.
	 * @param perito      - Entidad perito.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioPerito(final Model model, final Perito perito, final Boolean soloLectura) {
		model.addAttribute("peritoForm", perito);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(perito.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
	}

	/**
	 * Carga la lista de municipios según la provincia seleccionada.
	 * 
	 * @param provincia - Entidad provincia
	 * @param model     - Modelo
	 * @return - Devuelve una lista de municipios
	 */
	@RequestMapping("/municipios")
	public String ajaxProvincias(@RequestParam("provincia") final Provincia provincia, final Model model) {
		final List<Municipio> listaMunicipios = municipioService.findByProvincia(provincia);
		model.addAttribute("listaMunicipios", listaMunicipios);
		return VISTA_EDICION_PERITO + "::listaMunicipios";
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
		model.addAttribute("peritoActive", Boolean.TRUE);
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
