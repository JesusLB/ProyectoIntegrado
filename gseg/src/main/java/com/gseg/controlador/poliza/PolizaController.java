package com.gseg.controlador.poliza;

import java.time.LocalDate;
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
import com.gseg.enumerado.TipoPago;
import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.poliza.Producto;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.poliza.CiaService;
import com.gseg.servicio.poliza.PolizaService;
import com.gseg.servicio.poliza.ProductoService;
import com.gseg.servicio.poliza.RamoService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.PolizaValidator;

/**
 * Controlador póliza.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/poliza")
public class PolizaController {

	/**
	 * Vista para la gestión de póliza.
	 */
	private final static String VISTA_GESTION_POLIZA = "gestor/poliza/gestionPoliza";

	/**
	 * Vista para la edición de póliza.
	 */
	private final static String VISTA_EDICION_POLIZA = "gestor/poliza/edicionPoliza";

	/**
	 * Vista para la edición de póliza.
	 */
	private final static String REDIRECT_GESTION_POLIZA = "redirect:/gseg/poliza/gestion";

	/**
	 * Variable para mostrar mensaje de domiciliación guardada.
	 */
	private boolean polizaGuardada = false;

	/**
	 * Servicio de poliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Validador de póliza.
	 */
	@Autowired
	PolizaValidator polizaValidator;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de ramo.
	 */
	@Autowired
	RamoService ramoService;

	/**
	 * Servicio cia.
	 */
	@Autowired
	CiaService ciaService;

	/**
	 * Servicio de producto.
	 */
	@Autowired
	ProductoService productoService;

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/********************************************
	 * PÓLIZA
	 ********************************************/

	/**
	 * Vista gestión.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionPolizas(final Model model) {
		model.addAttribute("polizaGuardada", polizaGuardada);
		polizaGuardada = false;
		permisos(model);
		return VISTA_GESTION_POLIZA;
	}

	/**
	 * Acción de nueva alta de póliza.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de póliza.
	 */
	@GetMapping("/alta")
	public String altaPoliza(final Model model) {
		final Poliza poliza = new Poliza();
		cargarFormularioPoliza(model, poliza, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_POLIZA;
	}

	/**
	 * Acción para guardar/editar una póliza.
	 * 
	 * @param model         - Modelo.
	 * @param poliza        - Entidad poliza.
	 * @param bindingResult - Resultado al comprobar errores
	 * @return Devuelve la vista gestión si se guarda correctamente la póliza, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarPoliza(final Model model, @Valid @ModelAttribute("polizaForm") final Poliza poliza,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_POLIZA;

		final Cliente cliente = clienteService.findById(poliza.getCliente().getId());
		poliza.setCliente(cliente);

		// Validaciones complejas
		polizaValidator.validate(poliza, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormularioPoliza(model, poliza, Boolean.FALSE);
			vista = VISTA_EDICION_POLIZA;
		} else {
			polizaGuardada = true;
			model.addAttribute("polizaGuardada", polizaGuardada);
			polizaService.savePoliza(poliza);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar una póliza.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la póliza.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarPoliza(final Model model, @PathVariable("id") final long id) {
		final Poliza poliza = polizaService.findById(id);
		cargarFormularioPoliza(model, poliza, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_POLIZA;
	}

	/**
	 * Acción para ver una póliza.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la póliza.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verPoliza(final Model model, @PathVariable("id") final long id) {
		final Poliza poliza = polizaService.findById(id);
		cargarFormularioPoliza(model, poliza, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_POLIZA;
	}

	/**
	 * Acción para activar una póliza.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la compañía.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activar(final Model model, @PathVariable("id") final long id) {
		final Poliza poliza = polizaService.findById(id);
		if (!poliza.getActivo()) {
			poliza.setActivo(true);
			poliza.setFechaAnulacion(null);
		}
		polizaService.savePoliza(poliza);
		permisos(model);
		return REDIRECT_GESTION_POLIZA;
	}

	/**
	 * Acción para desactivar una póliza.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la póliza.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivarPoliza(final Model model, @PathVariable("id") final long id) {
		final Poliza poliza = polizaService.findById(id);
		if (poliza.getActivo()) {
			poliza.setActivo(false);
			poliza.setFechaAnulacion(LocalDate.now());
		}
		polizaService.savePoliza(poliza);
		permisos(model);
		return REDIRECT_GESTION_POLIZA;
	}

	/**
	 * Acción para buscar/cargar un cliente.
	 * 
	 * @param model         - Modelo.
	 * @param poliza        - Entidad poliza.
	 * @param bindingResult - Resultado al comprobar errores
	 * @return Devuelve la vista edición de póliza con los datos del cliente.
	 */
	@PostMapping("/cliente")
	public String BuscarClientePoliza(final Model model, @ModelAttribute("polizaForm") final Poliza poliza,
			final BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			// Validaciones complejas
			polizaValidator.validate(poliza, bindingResult);
			final Cliente cliente = clienteService.findByIdentificador(poliza.getCliente().getIdentificador());
			poliza.setCliente(cliente);
			poliza.setActivo(Boolean.TRUE);
		}
		cargarFormularioPoliza(model, poliza, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_POLIZA;
	}

	/**
	 * Método para cargar el formulario de póliza.
	 * 
	 * @param model       - Modelo.
	 * @param poliza      - Entidad póliza.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioPoliza(final Model model, final Poliza poliza, final Boolean soloLectura) {
		model.addAttribute("polizaForm", poliza);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaTipoPago", TipoPago.getListaTipoPago());
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
		model.addAttribute("listaRamos", ramoService.findAll());
		model.addAttribute("listaCias", ciaService.findAll());
		model.addAttribute("listaProductos", productoService.findByCia(poliza.getCia()));
	}

	/********************************************
	 * PRODUCTOS
	 ********************************************/

	/**
	 * Carga la lista de productos según la compañía seleccionada.
	 * 
	 * @param cia   - Entidad cia.
	 * @param model - Modelo.
	 * @return - Devuelve una lista de productos.
	 */
	@RequestMapping("/productos")
	public String ajaxProductos(@RequestParam("cia") final Cia cia, final Model model) {
		final List<Producto> listaProductos = productoService.findByCia(cia);
		model.addAttribute("listaProductos", listaProductos);
		return VISTA_EDICION_POLIZA + "::listaProductos";
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
		model.addAttribute("polizaActive", Boolean.TRUE);
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
