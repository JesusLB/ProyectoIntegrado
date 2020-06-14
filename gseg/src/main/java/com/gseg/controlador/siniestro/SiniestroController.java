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
import com.gseg.enumerado.TipoEstadoSiniestro;
import com.gseg.enumerado.TipoGenero;
import com.gseg.enumerado.TipoPago;
import com.gseg.enumerado.TipoResponsabilidad;
import com.gseg.enumerado.TipoSiniestro;
import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.siniestro.ActJudicial;
import com.gseg.modelo.siniestro.Contrario;
import com.gseg.modelo.siniestro.Lesionado;
import com.gseg.modelo.siniestro.Peritacion;
import com.gseg.modelo.siniestro.Perito;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.modelo.siniestro.Taller;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.MunicipioService;
import com.gseg.servicio.ProvinciaService;
import com.gseg.servicio.TipoViaService;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.poliza.PolizaService;
import com.gseg.servicio.siniestro.ActJudicialService;
import com.gseg.servicio.siniestro.ContrarioService;
import com.gseg.servicio.siniestro.LesionadoService;
import com.gseg.servicio.siniestro.PeritacionService;
import com.gseg.servicio.siniestro.PeritoService;
import com.gseg.servicio.siniestro.SiniestroService;
import com.gseg.servicio.siniestro.TallerService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.ContrarioValidator;
import com.gseg.validacion.LesionadoValidator;
import com.gseg.validacion.SiniestroValidator;

/**
 * Controlador siniestro.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/siniestro")
public class SiniestroController {

	/**
	 * Vista para la gestión de siniestro.
	 */
	private final static String VISTA_GESTION_SINIESTRO = "gestor/siniestro/gestionSiniestro";

	/**
	 * Vista para la edición de siniestro.
	 */
	private final static String VISTA_EDICION_SINIESTRO = "gestor/siniestro/edicionSiniestro";

	/**
	 * Vista para la edición de contrario.
	 */
	private final static String VISTA_EDICION_CONTRARIO = "gestor/siniestro/edicionContrario";

	/**
	 * Vista para la edición de lesionado.
	 */
	private final static String VISTA_EDICION_LESIONADO = "gestor/siniestro/edicionLesionado";

	/**
	 * Vista para la edición de peritación.
	 */
	private final static String VISTA_EDICION_PERITACION = "gestor/siniestro/edicionPeritacion";

	/**
	 * Vista para la edición de actuación judicial.
	 */
	private final static String VISTA_EDICION_ACT_JUDICIAL = "gestor/siniestro/edicionActJudicial";

	/**
	 * Redirección a la vista para la gestión de siniestro.
	 */
	private final static String REDIRECT_GESTION_SINIESTRO = "redirect:/gseg/siniestro/gestion";

	/**
	 * Redirección a la vista para la edición de siniestro.
	 */
	private final static String REDIRECT_EDICION_SINIESTRO = "redirect:/gseg/siniestro/editar/";

	/**
	 * Redirección a la vista para la edición de peritación.
	 */
	private final static String REDIRECT_EDICION_PERITACION = "redirect:/gseg/siniestro/peritacion/editar/";

	/**
	 * Variable para mostrar mensaje siniestro guardado.
	 */
	private boolean siniestroGuardado = false;

	/**
	 * Variable para mostrar mensaje contrario guardado.
	 */
	private boolean contrarioGuardado = false;

	/**
	 * Variable para mostrar mensaje lesionado guardado.
	 */
	private boolean lesionadoGuardado = false;

	/**
	 * Variable para mostrar mensaje perito guardado.
	 */
	private boolean peritacionGuardada = false;

	/**
	 * Variable para mostrar mensaje peritación eliminada.
	 */
	private boolean peritacionEliminada = false;

	/**
	 * Variable para mostrar mensaje actuación judicial guardada.
	 */
	private boolean actJudicialGuardada = false;

	/**
	 * Variable para mostrar mensaje actaución judicial eliminada.
	 */
	private boolean actJudicialEliminada = false;

	/**
	 * Servicio de siniestro
	 */
	@Autowired
	SiniestroService siniestroService;

	/**
	 * Servicio de póliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de contrario.
	 */
	@Autowired
	ContrarioService contrarioService;

	/**
	 * Servicio de lesionado.
	 */
	@Autowired
	LesionadoService lesionadoService;

	/**
	 * Servicio de peritación.
	 */
	@Autowired
	PeritacionService peritacionService;

	/**
	 * Servicio de perito.
	 */
	@Autowired
	PeritoService peritoService;

	/**
	 * Servicio de taller.
	 */
	@Autowired
	TallerService tallerService;

	/**
	 * Servicio de actuaciones judiciales.
	 */
	@Autowired
	ActJudicialService actJudicialService;

	/**
	 * Servicio de tipo de vía.
	 */
	@Autowired
	TipoViaService tipoViaService;

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
	 * Validación de siniestro.
	 */
	@Autowired
	SiniestroValidator siniestroValidator;

	/**
	 * Validación de lesionado.
	 */
	@Autowired
	LesionadoValidator lesionadoValidator;

	/**
	 * Validación de contrario.
	 */
	@Autowired
	ContrarioValidator contrarioValidator;

	/********************************************
	 * SINIESTRO
	 ********************************************/

	/**
	 * Vista gestión.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionSiniestros(final Model model) {
		model.addAttribute("siniestroGuardado", siniestroGuardado);
		siniestroGuardado = false;
		permisos(model);
		return VISTA_GESTION_SINIESTRO;
	}

	/**
	 * Acción de nueva alta de siniestro.
	 * 
	 * @param model - Modelo
	 * @return Devuelve la vista edición con el formulario de siniestro.
	 */
	@GetMapping("/alta")
	public String altaSiniestro(final Model model) {
		final Siniestro siniestro = new Siniestro();
		siniestro.setActivo(Boolean.TRUE);
		cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_SINIESTRO;
	}

	/**
	 * Acción para guardar/editar un siniestro.
	 * 
	 * @param model         - Modelo.
	 * @param siniestro     - Entidad siniestro.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el siniestro, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarSiniestro(final Model model, @Valid @ModelAttribute("siniestroForm") final Siniestro siniestro,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_SINIESTRO;

		final Poliza poliza = polizaService.findById(siniestro.getPoliza().getId());
		final Cliente cliente = clienteService.findById(siniestro.getCliente().getId());
		siniestro.setPoliza(poliza);
		siniestro.setCliente(cliente);

		// Validaciones complejas
		siniestroValidator.validate(siniestro, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
			System.err.println(bindingResult.getFieldError());
			vista = VISTA_EDICION_SINIESTRO;
		} else {
			siniestroGuardado = true;
			model.addAttribute("siniestroGuardado", siniestroGuardado);
			siniestroService.saveSiniestro(siniestro);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para buscar/cargar una poliza.
	 * 
	 * @param model         - Modelo.
	 * @param siniestro     - Entidad Siniestro.
	 * @param bindingResult - Resultado al comprobar errores
	 * @return Devuelve la vista gestión.
	 */
	@PostMapping("/poliza")
	public String BuscarSiniestroPoliza(final Model model, @ModelAttribute("siniestroForm") final Siniestro siniestro,
			final BindingResult bindingResult) {

		if (!bindingResult.hasErrors()) {
			// Validaciones complejas
			siniestroValidator.validate(siniestro, bindingResult);
			final Poliza poliza = polizaService.findByNumPoliza(siniestro.getPoliza().getNumPoliza());
			final Cliente cliente = clienteService.findById(poliza.getCliente().getId());
			siniestro.setPoliza(poliza);
			siniestro.setCliente(cliente);
			siniestro.setActivo(Boolean.TRUE);
		}
		cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_SINIESTRO;
	}

	/**
	 * Acción para editar un siniestro.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del siniestro.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarSiniestro(final Model model, @PathVariable("id") final long id) {
		final Siniestro siniestro = siniestroService.findById(id);
		cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
		permisos(model);
		model.addAttribute("contrarioGuardado", contrarioGuardado);
		model.addAttribute("lesionadoGuardado", lesionadoGuardado);
		model.addAttribute("peritacionEliminada", peritacionEliminada);
		model.addAttribute("peritacionGuardada", peritacionGuardada);
		model.addAttribute("actJudicialEliminada", actJudicialEliminada);
		contrarioGuardado = false;
		lesionadoGuardado = false;
		peritacionGuardada = false;
		peritacionEliminada = false;
		actJudicialEliminada = false;
		return VISTA_EDICION_SINIESTRO;
	}

	/**
	 * Acción para ver un siniestro.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del siniestro.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verSiniestro(final Model model, @PathVariable("id") final long id) {
		final Siniestro siniestro = siniestroService.findById(id);
		cargarFormularioSiniestro(model, siniestro, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_SINIESTRO;
	}

	/**
	 * Acción para activar un siniestro.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del siniestro.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activarSiniestro(final Model model, @PathVariable("id") final long id) {
		final Siniestro siniestro = siniestroService.findById(id);
		if (!siniestro.getActivo()) {
			siniestro.setActivo(true);
		}
		siniestroService.saveSiniestro(siniestro);
		permisos(model);
		return REDIRECT_GESTION_SINIESTRO;
	}

	/**
	 * Acción para desactivar un siniestro.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del recibo.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivarSiniestro(final Model model, @PathVariable("id") final long id) {
		final Siniestro siniestro = siniestroService.findById(id);
		if (siniestro.getActivo()) {
			siniestro.setActivo(false);
		}
		siniestroService.saveSiniestro(siniestro);
		permisos(model);
		return REDIRECT_GESTION_SINIESTRO;
	}

	/**
	 * Método para cargar el formulario de siniestro.
	 * 
	 * @param model       - Modelo.
	 * @param siniestro   - Entidad siniestro.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioSiniestro(final Model model, final Siniestro siniestro, final Boolean soloLectura) {
		model.addAttribute("siniestroForm", siniestro);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(siniestro.getProvincia()));
		model.addAttribute("listaTipoPago", TipoPago.getListaTipoPago());
		model.addAttribute("listaVias", tipoViaService.findAll());
		model.addAttribute("listaEstado", TipoEstadoSiniestro.getListaTipoEstadoSiniestro());
		model.addAttribute("listaResponsabilidad", TipoResponsabilidad.getListaTipoResponsabilidad());
		model.addAttribute("listaTipoSiniestro", TipoSiniestro.getListaTipoSiniestro());
	}

	/********************************************
	 * CONTRARIO
	 ********************************************/

	/**
	 * Acción de nueva alta de contrario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador.
	 * @return Devuelve la vista edición con el formulario de domiciliación.
	 */
	@GetMapping("/contrario/alta/{idSiniestro}")
	public String altaContrario(final Model model, @PathVariable("idSiniestro") final long idSiniestro) {
		final Contrario contrarioForm = new Contrario();
		final Siniestro siniestro = new Siniestro();
		siniestro.setId(idSiniestro);
		contrarioForm.setSiniestro(siniestro);
		cargarFormularioContrario(model, contrarioForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CONTRARIO;
	}

	/**
	 * Acción para guardar/editar un contrario.
	 * 
	 * @param model         - Modelo.
	 * @param contrarioForm - Entidad contrario.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el contrario, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/contrario/guardar")
	public String guardarContrario(final Model model,
			@Valid @ModelAttribute("contrarioForm") final Contrario contrarioForm, final BindingResult bindingResult) {

		final Siniestro siniestro = siniestroService.findById(contrarioForm.getSiniestro().getId());
		String vista = REDIRECT_EDICION_SINIESTRO + siniestro.getId();

		// Validaciones complejas
		contrarioValidator.validate(contrarioForm, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormularioContrario(model, contrarioForm, Boolean.FALSE);
			vista = VISTA_EDICION_CONTRARIO;
		} else {
			contrarioGuardado = true;
			model.addAttribute("contrarioGuardado", contrarioGuardado);
			cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
			contrarioForm.setTelefono(contrarioForm.getTelefono().replaceAll(" ", ""));
			contrarioForm.setMovil(contrarioForm.getMovil().replaceAll(" ", ""));
			contrarioService.saveContrario(contrarioForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un contrario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del contrario.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/contrario/editar/{id}")
	public String editarContrario(final Model model, @PathVariable("id") final long id) {
		final Contrario contrario = contrarioService.findById(id);
		cargarFormularioContrario(model, contrario, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CONTRARIO;
	}

	/**
	 * Acción para ver un contrario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de contrario.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/contrario/ver/{id}")
	public String verContrario(final Model model, @PathVariable("id") final long id) {
		final Contrario contrario = contrarioService.findById(id);
		cargarFormularioContrario(model, contrario, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_CONTRARIO;
	}

	/**
	 * Acción para eliminar un contrario.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de contrario.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/contrario/eliminar/{id}")
	public String eliminarContrario(final Model model, @PathVariable("id") final long id) {
		final Contrario contrario = contrarioService.findById(id);
		final Siniestro siniestro = siniestroService.findById(contrario.getSiniestro().getId());
		if (contrario != null) {
			contrarioService.deleteContrario(contrario);
		}
		permisos(model);
		model.addAttribute("contrarioEliminado", Boolean.TRUE);
		cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
		return REDIRECT_EDICION_SINIESTRO + siniestro.getId();
	}

	/**
	 * Método para cargar el formulario de contrario.
	 * 
	 * @param model       - Modelo.
	 * @param contrario   - Entidad Contrario.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioContrario(final Model model, final Contrario contrario, final Boolean soloLectura) {
		model.addAttribute("contrarioForm", contrario);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		// model.addAttribute("listaMunicipios",
		// municipioService.findByProvincia(contrario.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
		model.addAttribute("listaGenero", TipoGenero.getListaGenero());
	}

	/********************************************
	 * LESIONADO
	 ********************************************/

	/**
	 * Acción de nueva alta de lesionado.
	 * 
	 * @param model       - Modelo.
	 * @param idSiniestro - Identificador.
	 * @return Devuelve la vista edición con el formulario de domiciliación.
	 */
	@GetMapping("/lesionado/alta/{idSiniestro}")
	public String altaLesionado(final Model model, @PathVariable("idSiniestro") final long idSiniestro) {
		final Lesionado lesionadoForm = new Lesionado();
		final Siniestro siniestro = new Siniestro();
		siniestro.setId(idSiniestro);
		lesionadoForm.setSiniestro(siniestro);
		cargarFormularioLesionado(model, lesionadoForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_LESIONADO;
	}

	/**
	 * Acción para guardar/editar un lesionado.
	 * 
	 * @param model         - Modelo.
	 * @param lesionadoForm - Entidad lesionado.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el lesionado, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/lesionado/guardar")
	public String guardarLesionado(final Model model,
			@Valid @ModelAttribute("lesionadoForm") final Lesionado lesionadoForm, final BindingResult bindingResult) {

		final Siniestro siniestro = siniestroService.findById(lesionadoForm.getSiniestro().getId());
		String vista = REDIRECT_EDICION_SINIESTRO + siniestro.getId();

		// validaciones complejas.
		lesionadoValidator.validate(lesionadoForm, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormularioLesionado(model, lesionadoForm, Boolean.FALSE);
			vista = VISTA_EDICION_LESIONADO;
		} else {
			lesionadoGuardado = true;
			model.addAttribute("lesionadoGuardado", lesionadoGuardado);
			cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
			lesionadoForm.setTelefono(lesionadoForm.getTelefono().replaceAll(" ", ""));
			lesionadoForm.setMovil(lesionadoForm.getMovil().replaceAll(" ", ""));
			lesionadoService.saveLesionado(lesionadoForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un lesionado.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del lesionado.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/lesionado/editar/{id}")
	public String editarLesionado(final Model model, @PathVariable("id") final long id) {
		final Lesionado lesionado = lesionadoService.findById(id);
		cargarFormularioLesionado(model, lesionado, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_LESIONADO;
	}

	/**
	 * Acción para ver un lesionado.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de lesionado.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/lesionado/ver/{id}")
	public String verLesionado(final Model model, @PathVariable("id") final long id) {
		final Lesionado lesionado = lesionadoService.findById(id);
		cargarFormularioLesionado(model, lesionado, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_LESIONADO;
	}

	/**
	 * Acción para eliminar un lesionado.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de lesionado.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/lesionado/eliminar/{id}")
	public String eliminarLesionado(final Model model, @PathVariable("id") final long id) {
		final Lesionado lesionado = lesionadoService.findById(id);
		final Siniestro siniestro = siniestroService.findById(lesionado.getSiniestro().getId());
		if (lesionado != null) {
			lesionadoService.deleteLesionado(lesionado);
		}
		permisos(model);
		model.addAttribute("lesionadoEliminado", Boolean.TRUE);
		cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
		return REDIRECT_EDICION_SINIESTRO + siniestro.getId();
	}

	/**
	 * Método para cargar el formulario de lesionado.
	 * 
	 * @param model       - Modelo.
	 * @param lesionado   - Entidad Lesionado.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioLesionado(final Model model, final Lesionado lesionado, final Boolean soloLectura) {
		model.addAttribute("lesionadoForm", lesionado);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(lesionado.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
		model.addAttribute("listaGenero", TipoGenero.getListaGenero());
	}

	/********************************************
	 * PERITACIONES
	 ********************************************/

	/**
	 * Acción de nueva alta de peritación.
	 * 
	 * @param model       - Modelo.
	 * @param idSiniestro - Identificador.
	 * @return Devuelve la vista edición con el formulario de peritación.
	 */
	@GetMapping("/peritacion/alta/{idSiniestro}")
	public String altaPeritacion(final Model model, @PathVariable("idSiniestro") final long idSiniestro) {
		final Peritacion peritacionForm = new Peritacion();
		final Siniestro siniestro = new Siniestro();
		siniestro.setId(idSiniestro);
		peritacionForm.setSiniestro(siniestro);
		cargarFormularioPeritacion(model, peritacionForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_PERITACION;
	}

	/**
	 * Acción para guardar/editar una peritación.
	 * 
	 * @param model          - Modelo.
	 * @param peritacionForm - Entidad peritación.
	 * @param bindingResult  - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente la peritación,
	 *         si hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/peritacion/guardar")
	public String guardarPeritacion(final Model model,
			@Valid @ModelAttribute("peritacionForm") final Peritacion peritacionForm,
			final BindingResult bindingResult) {

		final Peritacion peritacion = peritacionService.findById(peritacionForm.getId());
		final Siniestro siniestro = siniestroService.findById(peritacionForm.getSiniestro().getId());
		String vista;

		if (bindingResult.hasErrors()) {
			cargarFormularioPeritacion(model, peritacionForm, Boolean.FALSE);
			vista = VISTA_EDICION_PERITACION;
		} else {
			peritacionGuardada = true;
			model.addAttribute("peritacionGuardada", peritacionGuardada);
			cargarFormularioPeritacion(model, peritacion, Boolean.FALSE);
			if (peritacionForm.getId() != 0 && peritacionForm.getPerito() != null) {
				peritacionForm.setPerito(peritacion.getPerito());
			}
			if (peritacionForm.getId() != 0 && peritacionForm.getTaller() != null) {
				peritacionForm.setTaller(peritacion.getTaller());
			}
			if (peritacionForm.getId() == 0) {
				final Peritacion peritacionGuardada = peritacionService.savePeritacion(peritacionForm);
				vista = REDIRECT_EDICION_PERITACION + peritacionGuardada.getId();
			} else {
				peritacionService.savePeritacion(peritacionForm);
				vista = REDIRECT_EDICION_SINIESTRO + siniestro.getId();
			}

		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar una peritación.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la peritación.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/peritacion/editar/{id}")
	public String editarPeritacion(final Model model, @PathVariable("id") final long id) {
		final Peritacion peritacion = peritacionService.findById(id);
		model.addAttribute("peritacionGuardada", peritacionGuardada);
		peritacionGuardada = false;
		cargarFormularioPeritacion(model, peritacion, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_PERITACION;
	}

	/**
	 * Acción para eliminar una peritación.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de peritación.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/peritacion/eliminar/{id}")
	public String eliminarPeritacion(final Model model, @PathVariable("id") final long id) {
		final Peritacion peritacion = peritacionService.findById(id);
		final Siniestro siniestro = siniestroService.findById(peritacion.getSiniestro().getId());
		if (peritacion != null) {
			peritacionService.deletePeritacion(peritacion);
		}
		permisos(model);
		peritacionEliminada = true;
		cargarFormularioPeritacion(model, peritacion, Boolean.FALSE);
		return REDIRECT_EDICION_SINIESTRO + siniestro.getId();
	}

	/**
	 * Acción para asignar un perito a la peritación.
	 * 
	 * @param model        - Modelo.
	 * @param idPeritacion - Identificador de peritación.
	 * @param idPerito     - Identificador de perito.
	 * @return Devuelve la vista de edición peritación.
	 */
	@GetMapping("/peritacion/asignar/perito/{idPeritacion}/{idPerito}")
	public String asignarPerito(final Model model, @PathVariable("idPeritacion") final long idPeritacion,
			@PathVariable("idPerito") final long idPerito) {
		final Perito perito = peritoService.findById(idPerito);
		final Peritacion peritacion = peritacionService.findById(idPeritacion);
		peritacion.setPerito(perito);
		peritacionService.savePeritacion(peritacion);
		cargarFormularioPeritacion(model, peritacion, Boolean.FALSE);
		return REDIRECT_EDICION_PERITACION + peritacion.getId();
	}

	/**
	 * Acción para asignar un taller a la peritación.
	 * 
	 * @param model        - Modelo.
	 * @param idPeritacion - Identificador de peritación.
	 * @param idTaller     - Identificador de taller.
	 * @return Devuelve la vista de edición peritación.
	 */
	@GetMapping("/peritacion/asignar/taller/{idPeritacion}/{idTaller}")
	public String asignarTaller(final Model model, @PathVariable("idPeritacion") final long idPeritacion,
			@PathVariable("idTaller") final long idTaller) {
		final Taller taller = tallerService.findById(idTaller);
		final Peritacion peritacion = peritacionService.findById(idPeritacion);
		peritacion.setTaller(taller);
		peritacionService.savePeritacion(peritacion);
		cargarFormularioPeritacion(model, peritacion, Boolean.FALSE);
		return REDIRECT_EDICION_PERITACION + peritacion.getId();
	}

	/**
	 * Acción para ver una peritación.
	 * 
	 * @param model - Modelo
	 * @param id    - Identificador de peritación.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/peritacion/ver/{id}")
	public String verPeritacion(final Model model, @PathVariable("id") final long id) {
		final Peritacion peritacion = peritacionService.findById(id);
		cargarFormularioPeritacion(model, peritacion, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_PERITACION;
	}

	/**
	 * Método para cargar el formulario de peritación.
	 * 
	 * @param model       - Modelo.
	 * @param peritacion  - Entidad peritación.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioPeritacion(final Model model, final Peritacion peritacion, final Boolean soloLectura) {
		model.addAttribute("peritacionForm", peritacion);
		model.addAttribute("soloLectura", soloLectura);
	}

	/********************************************
	 * ACT JUDICIALES
	 ********************************************/

	/**
	 * Acción de nueva alta de actuaciones judiciales.
	 * 
	 * @param model       - Modelo.
	 * @param idSiniestro - Identificador.
	 * @return Devuelve la vista edición con el formulario de actuaciones
	 *         judiciales.
	 */
	@GetMapping("/actuacionjudicial/alta/{idSiniestro}")
	public String altaActJudiciales(final Model model, @PathVariable("idSiniestro") final long idSiniestro) {
		final ActJudicial actJudicialForm = new ActJudicial();
		final Siniestro siniestro = new Siniestro();
		siniestro.setId(idSiniestro);
		actJudicialForm.setSiniestro(siniestro);
		cargarFormularioActJudicial(model, actJudicialForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_ACT_JUDICIAL;
	}

	/**
	 * Acción para guardar/editar una actuación judicial.
	 * 
	 * @param model         - Modelo.
	 * @param actJudicial   - Entidad ActJudicial.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente la actuación
	 *         judicial, si hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/actuacionjudicial/guardar")
	public String guardarActJudicial(final Model model,
			@Valid @ModelAttribute("actJudicialForm") final ActJudicial actJudicialForm,
			final BindingResult bindingResult) {

		final Siniestro siniestro = siniestroService.findById(actJudicialForm.getSiniestro().getId());
		String vista = REDIRECT_EDICION_SINIESTRO + siniestro.getId();

		if (bindingResult.hasErrors()) {
			cargarFormularioActJudicial(model, actJudicialForm, Boolean.FALSE);
			System.err.println(bindingResult.getFieldError());
			vista = VISTA_EDICION_ACT_JUDICIAL;
		} else {
			actJudicialGuardada = true;
			model.addAttribute("actJudicialGuardada", actJudicialGuardada);
			cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
			System.err.println(actJudicialForm.toString());
			actJudicialService.saveActJudicial(actJudicialForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar una actuación judicial.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la actuación judicial.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/actuacionjudicial/editar/{id}")
	public String editarActJudicial(final Model model, @PathVariable("id") final long id) {
		final ActJudicial actJudicial = actJudicialService.findById(id);
		cargarFormularioActJudicial(model, actJudicial, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_ACT_JUDICIAL;
	}

	/**
	 * Acción para ver un actuación judicial.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la actuación judicial.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/actuacionjudicial/ver/{id}")
	public String verActJudicial(final Model model, @PathVariable("id") final long id) {
		final ActJudicial actJudicial = actJudicialService.findById(id);
		cargarFormularioActJudicial(model, actJudicial, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_ACT_JUDICIAL;
	}

	/**
	 * Acción para eliminar una actuación judicial.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de actuación judicial.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/actuacionjudicial/eliminar/{id}")
	public String eliminarActJudicial(final Model model, @PathVariable("id") final long id) {
		final ActJudicial actJudicial = actJudicialService.findById(id);
		final Siniestro siniestro = siniestroService.findById(actJudicial.getSiniestro().getId());
		if (actJudicial != null) {
			actJudicialService.deleteActJudicial(actJudicial);
		}
		permisos(model);
		actJudicialEliminada = true;
		cargarFormularioSiniestro(model, siniestro, Boolean.FALSE);
		return REDIRECT_EDICION_SINIESTRO + siniestro.getId();
	}

	/**
	 * Método para cargar el formulario de actuaciones judiciales.
	 * 
	 * @param model       - Modelo.
	 * @param actJudicial - Entidad ActJudiciales.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioActJudicial(final Model model, final ActJudicial actJudicial,
			final Boolean soloLectura) {
		model.addAttribute("actJudicialForm", actJudicial);
		model.addAttribute("soloLectura", soloLectura);
	}

	/********************************************
	 * MUNICIPIOS
	 ********************************************/

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
		return VISTA_EDICION_CONTRARIO + "::listaMunicipios";
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
		model.addAttribute("siniestroActive", Boolean.TRUE);
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
