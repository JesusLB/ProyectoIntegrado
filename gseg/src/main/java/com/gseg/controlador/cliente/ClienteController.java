package com.gseg.controlador.cliente;

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
import com.gseg.enumerado.TipoGenero;
import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.cliente.Domiciliacion;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.MunicipioService;
import com.gseg.servicio.ProvinciaService;
import com.gseg.servicio.TipoViaService;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.cliente.DomiciliacionService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.ClienteValidator;

/**
 * Controlador cliente.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/cliente")
public class ClienteController {

	/**
	 * Vista para la gestión de cliente.
	 */
	private final static String VISTA_GESTION_CLIENTE = "gestor/cliente/gestionCliente";

	/**
	 * Vista para la edición de cliente.
	 */
	private final static String VISTA_EDICION_CLIENTE = "gestor/cliente/edicionCliente";

	/**
	 * Vista para la edición de domiciliación.
	 */
	private final static String VISTA_EDICION_DOMICILIACION = "gestor/cliente/edicionDomiciliacion";

	/**
	 * Redirección a la vista gestión de cliente.
	 */
	private final static String REDIRECT_GESTION_CLIENTE = "redirect:/gseg/cliente/gestion";

	/**
	 * Redirección a la vista edición de cliente.
	 */
	private final static String REDIRECT_EDICION_CLIENTE = "redirect:/gseg/cliente/editar/";

	/**
	 * Variable para mostrar mensaje de cliente guardado.
	 */
	private boolean guardadoCorrecto = false;

	/**
	 * Variable para mostrar mensaje de domiciliación eliminada.
	 */
	private boolean domiciliacionGuardada = false;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Validador de cliente.
	 */
	@Autowired
	ClienteValidator clienteValidator;

	/**
	 * Servicio de domiciliación.
	 */
	@Autowired
	DomiciliacionService domiciliacionService;

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
	 * CLIENTES *
	 ********************************************/

	/**
	 * Vista gestión de cliente.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión de cliente.
	 */
	@GetMapping("/gestion")
	public String gestionClientes(final Model model) {
		model.addAttribute("guardadoCorrecto", guardadoCorrecto);
		guardadoCorrecto = false;
		permisos(model);
		return VISTA_GESTION_CLIENTE;
	}

	/**
	 * Acción de nueva alta de cliente.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de cliente.
	 */
	@GetMapping("/alta")
	public String altaCliente(final Model model) {
		final Cliente cliente = new Cliente();
		cliente.setActivo(Boolean.TRUE);
		cargarFormulario(model, cliente, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CLIENTE;
	}

	/**
	 * Acción para guardar/editar un cliente.
	 * 
	 * @param model         - Modelo.
	 * @param clienteForm   - Entidad cliente.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el cliente, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarCliente(final Model model, @Valid @ModelAttribute("clienteForm") final Cliente clienteForm,
			final BindingResult bindingResult) {
		String vista;

		// Validaciones complejas
		clienteValidator.validate(clienteForm, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormulario(model, clienteForm, Boolean.FALSE);
			vista = VISTA_EDICION_CLIENTE;
		} else {
			clienteForm.setTelefono(clienteForm.getTelefono().replaceAll(" ", ""));
			clienteForm.setMovil(clienteForm.getMovil().replaceAll(" ", ""));
			if (clienteForm.getId() == 0) {
				// Si es un alta asignamo el identificador y redirigimos a edición cliente
				final long idCliente = clienteService.saveCliente(clienteForm).getId();
				vista = REDIRECT_EDICION_CLIENTE + idCliente;
			} else {
				// Si es una edición redirigimos a gestión cliente
				guardadoCorrecto = true;
				model.addAttribute("guardadoCorrecto", Boolean.TRUE);
				clienteService.saveCliente(clienteForm);
				vista = REDIRECT_GESTION_CLIENTE;
			}
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un cliente.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del cliente.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarCliente(final Model model, @PathVariable("id") final long id) {
		final Cliente cliente = clienteService.findById(id);
		model.addAttribute("domiciliacionGuardada", domiciliacionGuardada);
		domiciliacionGuardada = false;
		cargarFormulario(model, cliente, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CLIENTE;
	}

	/**
	 * Acción para ver un cliente.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del cliente.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String ver(final Model model, @PathVariable("id") final long id) {
		final Cliente cliente = clienteService.findById(id);
		cargarFormulario(model, cliente, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_CLIENTE;
	}

	/**
	 * Acción para activar un cliente.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del cliente.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activar(final Model model, @PathVariable("id") final long id) {
		final Cliente cliente = clienteService.findById(id);
		if (!cliente.getActivo()) {
			cliente.setActivo(true);
		}
		clienteService.saveCliente(cliente);
		permisos(model);
		return REDIRECT_GESTION_CLIENTE;
	}

	/**
	 * Acción para desactivar un cliente.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del cliente.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivar(final Model model, @PathVariable("id") final long id) {
		final Cliente cliente = clienteService.findById(id);
		if (cliente.getActivo()) {
			cliente.setActivo(false);
		}
		clienteService.saveCliente(cliente);
		permisos(model);
		return REDIRECT_GESTION_CLIENTE;
	}

	/**
	 * Método para cargar el formulario cliente.
	 * 
	 * @param model       - Modelo.
	 * @param usuario     - Entidad cliente.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormulario(final Model model, final Cliente cliente, final Boolean soloLectura) {
		model.addAttribute("clienteForm", cliente);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(cliente.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
		model.addAttribute("listaGenero", TipoGenero.getListaGenero());
	}

	/**
	 * Carga la lista de municipios según la provincia seleccionada.
	 * 
	 * @param provincia - Entidad provincia.
	 * @param model     - Modelo.
	 * @return - Devuelve una lista de municipios.
	 */
	@RequestMapping("/municipios")
	public String ajaxProvincias(@RequestParam("provincia") final Provincia provincia, final Model model) {
		final List<Municipio> listaMunicipios = municipioService.findByProvincia(provincia);
		model.addAttribute("listaMunicipios", listaMunicipios);
		return VISTA_EDICION_CLIENTE + "::listaMunicipios";
	}

	/********************************************
	 * DOMICILIACIÓN *
	 ********************************************/

	/**
	 * Acción de nueva alta de domiciliación.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de domiciliación.
	 */
	@GetMapping("/domiciliacion/alta/{idCliente}")
	public String altaDomiciliacion(final Model model, @PathVariable("idCliente") final long idCliente) {
		final Domiciliacion domicForm = new Domiciliacion();
		final Cliente cliente = new Cliente();
		cliente.setId(idCliente);
		domicForm.setCliente(cliente);
		domicForm.setActivo(Boolean.TRUE);
		cargarFormularioDomic(model, domicForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_DOMICILIACION;
	}

	/**
	 * Acción para guardar/editar una domiciliación.
	 * 
	 * @param model         - Modelo.
	 * @param domicForm     - Entidad domiciliación.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente la
	 *         domiciliación, si hay errores, devuelve la vista edición con los
	 *         errores.
	 */
	@PostMapping("/domiciliacion/guardar")
	public String guardarDomiciliacion(final Model model,
			@Valid @ModelAttribute("domicForm") final Domiciliacion domicForm, final BindingResult bindingResult) {

		final Cliente cliente = clienteService.findById(domicForm.getCliente().getId());
		String vista = REDIRECT_EDICION_CLIENTE + cliente.getId();

		if (bindingResult.hasErrors()) {
			cargarFormularioDomic(model, domicForm, Boolean.FALSE);
			vista = VISTA_EDICION_DOMICILIACION;
		} else {
			domiciliacionGuardada = true;
			model.addAttribute("domiciliacionGuardada", domiciliacionGuardada);
			cargarFormulario(model, cliente, Boolean.FALSE);
			domicForm.setNumero(domicForm.getNumero().replaceAll(" ", ""));
			domiciliacionService.saveDomiciliacion(domicForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar una domiciliación.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la domiciliación.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/domiciliacion/editar/{id}")
	public String editarDomiciliacion(final Model model, @PathVariable("id") final long id) {
		final Domiciliacion domic = domiciliacionService.findById(id);
		cargarFormularioDomic(model, domic, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_DOMICILIACION;
	}

	/**
	 * Acción para ver una domiciliación.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de domiciliación.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/domiciliacion/ver/{id}")
	public String verDomiciliacion(final Model model, @PathVariable("id") final long id) {
		final Domiciliacion domic = domiciliacionService.findById(id);
		cargarFormularioDomic(model, domic, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_DOMICILIACION;
	}

	/**
	 * Acción para activar una domiciliación.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de domiciliación.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/domiciliacion/activar/{id}")
	public String activarDomiciliacion(final Model model, @PathVariable("id") final long id) {
		final Domiciliacion domic = domiciliacionService.findById(id);
		final Cliente cliente = clienteService.findById(domic.getCliente().getId());
		if (!domic.getActivo()) {
			domic.setActivo(true);
		}
		domiciliacionService.saveDomiciliacion(domic);
		permisos(model);
		cargarFormulario(model, cliente, Boolean.FALSE);
		return REDIRECT_EDICION_CLIENTE + cliente.getId();
	}

	/**
	 * Acción para desactivar un cliente.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del cliente.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/domiciliacion/desactivar/{id}")
	public String desactivarDomiciliacion(final Model model, @PathVariable("id") final long id) {
		final Domiciliacion domic = domiciliacionService.findById(id);
		final Cliente cliente = clienteService.findById(domic.getCliente().getId());
		if (domic.getActivo()) {
			domic.setActivo(false);
		}
		domiciliacionService.saveDomiciliacion(domic);
		permisos(model);
		cargarFormulario(model, cliente, Boolean.FALSE);
		return REDIRECT_EDICION_CLIENTE + cliente.getId();
	}

	/**
	 * Método para cargar el formulario domiciliación.
	 * 
	 * @param model       - Modelo.
	 * @param usuario     - Entidad domiciliación.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioDomic(final Model model, final Domiciliacion domicForm, final Boolean soloLectura) {
		model.addAttribute("domicForm", domicForm);
		model.addAttribute("soloLectura", soloLectura);
	}

	/********************************************
	 * PERMISOS *
	 ********************************************/

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
		final Usuario usu = usuarioService.findByUsuario(user.getUsername());
		model.addAttribute("clienteActive", Boolean.TRUE);
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
