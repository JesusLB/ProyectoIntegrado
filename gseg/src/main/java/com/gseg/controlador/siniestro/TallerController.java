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

import com.gseg.enumerado.TipoDocumento;
import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.siniestro.Taller;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.MunicipioService;
import com.gseg.servicio.ProvinciaService;
import com.gseg.servicio.TipoViaService;
import com.gseg.servicio.siniestro.TallerService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.TallerValidator;

/**
 * Controlador taller.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/taller")
public class TallerController {

	/**
	 * Vista para la gestión de taller.
	 */
	private final static String VISTA_GESTION_TALLER = "gestor/taller/gestionTaller";

	/**
	 * Vista para la edición de taller.
	 */
	private final static String VISTA_EDICION_TALLER = "gestor/taller/edicionTaller";

	/**
	 * Redirección a la vista para la gestión de taller.
	 */
	private final static String REDIRECT_GESTION_TALLER = "redirect:/gseg/taller/gestion";

	/**
	 * Variable para mostrar mensaje taller guardado.
	 */
	private boolean tallerGuardado = false;

	/**
	 * Variable para mostrar mensaje taller eliminado.
	 */
	private boolean tallerEliminado = false;

	/**
	 * Servicio de taller.
	 */
	@Autowired
	TallerService tallerService;

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

	/**
	 * Validación de taller
	 */
	@Autowired
	TallerValidator tallerValidator;

	/********************************************
	 * TALLER
	 ********************************************/

	/**
	 * Vista gestión.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionTaller(final Model model) {
		model.addAttribute("tallerGuardado", tallerGuardado);
		model.addAttribute("tallerEliminado", tallerEliminado);
		tallerGuardado = false;
		tallerEliminado = false;
		permisos(model);
		return VISTA_GESTION_TALLER;
	}

	/**
	 * Acción de nueva alta de taller.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de taller.
	 */
	@GetMapping("/alta")
	public String altaTaller(final Model model) {
		final Taller taller = new Taller();
		cargarFormularioTaller(model, taller, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_TALLER;
	}

	/**
	 * Acción para guardar/editar un taller.
	 * 
	 * @param model         - Modelo.
	 * @param taller        - Entidad taller.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el taller, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarTaller(final Model model, @Valid @ModelAttribute("tallerForm") final Taller tallerForm,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_TALLER;

		// Validaciones complejas
		tallerValidator.validate(tallerForm, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormularioTaller(model, tallerForm, Boolean.FALSE);
			vista = VISTA_EDICION_TALLER;
		} else {
			tallerGuardado = true;
			tallerForm.setTelefono(tallerForm.getTelefono().replaceAll(" ", ""));
			tallerForm.setMovil(tallerForm.getMovil().replaceAll(" ", ""));
			tallerService.saveTaller(tallerForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un taller.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de taller.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarTaller(final Model model, @PathVariable("id") final long id) {
		final Taller taller = tallerService.findById(id);
		cargarFormularioTaller(model, taller, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_TALLER;
	}

	/**
	 * Acción para ver un taller.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de taller.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verTaller(final Model model, @PathVariable("id") final long id) {
		final Taller taller = tallerService.findById(id);
		cargarFormularioTaller(model, taller, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_TALLER;
	}

	/**
	 * Acción para eliminar un taller.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de taller.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/eliminar/{id}")
	public String eliminarPerito(final Model model, @PathVariable("id") final long id) {
		final Taller taller = tallerService.findById(id);
		if (taller != null) {
			tallerService.deleteTaller(taller);
		}
		permisos(model);
		tallerEliminado = true;
		return REDIRECT_GESTION_TALLER;
	}

	/**
	 * Método para cargar el formulario de taller.
	 * 
	 * @param model       - Modelo.
	 * @param taller      - Entidad taller.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioTaller(final Model model, final Taller taller, final Boolean soloLectura) {
		model.addAttribute("tallerForm", taller);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(taller.getProvincia()));
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
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
		return VISTA_EDICION_TALLER + "::listaMunicipios";
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
		model.addAttribute("tallerActive", Boolean.TRUE);
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
