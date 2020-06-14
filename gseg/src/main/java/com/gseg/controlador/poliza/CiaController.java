package com.gseg.controlador.poliza;

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
import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.ContactoDepartamento;
import com.gseg.modelo.poliza.Departamento;
import com.gseg.modelo.poliza.Producto;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.MunicipioService;
import com.gseg.servicio.ProvinciaService;
import com.gseg.servicio.TipoViaService;
import com.gseg.servicio.poliza.CiaService;
import com.gseg.servicio.poliza.ContactoDepartamentoService;
import com.gseg.servicio.poliza.DepartamentoService;
import com.gseg.servicio.poliza.ProductoService;
import com.gseg.servicio.usuario.UsuarioService;
import com.gseg.validacion.CiaValidator;

/**
 * Controlador cia.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/cia")
public class CiaController {

	/**
	 * Vista para la gestión de cia.
	 */
	private final static String VISTA_GESTION_CIA = "gestor/cia/gestionCia";

	/**
	 * Vista para la edición de cia.
	 */
	private final static String VISTA_EDICION_CIA = "gestor/cia/edicionCia";

	/**
	 * Vista para la edición de producto.
	 */
	private final static String VISTA_EDICION_PRODUCTO = "gestor/producto/edicionProducto";

	/**
	 * Vista para la edición de departamento.
	 */
	private final static String VISTA_EDICION_DEPARTAMENTO = "gestor/departamento/edicionDepartamento";

	/**
	 * Vista para la edición de contacto de departamento.
	 */
	private final static String VISTA_EDICION_CONTACTO_DEPARTAMENTO = "gestor/departamento/edicionContactoDepartamento";

	/**
	 * Redirección a la vista gestión de cia.
	 */
	private final static String REDIRECT_GESTION_CIA = "redirect:/gseg/cia/gestion";

	/**
	 * Redirección a la vista edición de cia.
	 */
	private final static String REDIRECT_EDICION_CIA = "redirect:/gseg/cia/editar/";

	/**
	 * Redirección a la vista edición de departamento.
	 */
	private final static String REDIRECT_EDICION_DEPARTAMENTO = "redirect:/gseg/cia/departamento/editar/";

	/**
	 * Variable para mostrar mensaje de cia guardada.
	 */
	private boolean ciaGuardada = false;

	/**
	 * Variable para mostrar mensaje de producto guardado.
	 */
	private boolean productoGuardado = false;

	/**
	 * Variable para mostrar mensaje de departamento guardado.
	 */
	private boolean departamentoGuardado = false;

	/**
	 * Variable para mostrar mensaje de contacto guardado.
	 */
	private boolean contactoGuardado = false;

	/**
	 * Servicio de cia.
	 */
	@Autowired
	CiaService ciaService;

	/**
	 * Validador de cia.
	 */
	@Autowired
	CiaValidator ciaValidator;

	/**
	 * Servicio de usuario.
	 */
	@Autowired
	UsuarioService usuarioService;

	/**
	 * Servicio de producto.
	 */
	@Autowired
	ProductoService productoService;

	/**
	 * Servicio de departamento.
	 */
	@Autowired
	DepartamentoService departamentoService;

	/**
	 * Servicio de contacto de departamento.
	 */
	@Autowired
	ContactoDepartamentoService contactoDepartamentoService;

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
	 * COMPAÑÍA
	 ********************************************/

	/**
	 * Vista gestión
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/gestion")
	public String gestionCias(final Model model) {
		model.addAttribute("ciaGuardada", ciaGuardada);
		ciaGuardada = false;
		permisos(model);
		return VISTA_GESTION_CIA;
	}

	/**
	 * Acción de nueva alta de compañía.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de compañía.
	 */
	@GetMapping("/alta")
	public String altaCia(final Model model) {
		final Cia cia = new Cia();
		cia.setActivo(Boolean.TRUE);
		cargarFormulario(model, cia, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CIA;
	}

	/**
	 * Acción para guardar/editar una compañía.
	 * 
	 * @param model         - Modelo.
	 * @param ciaForm       - Entidad cia.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente la compañía, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/guardar")
	public String guardarCia(final Model model, @Valid @ModelAttribute("ciaForm") final Cia ciaForm,
			final BindingResult bindingResult) {
		String vista = REDIRECT_GESTION_CIA;

		// Validaciones complejas
		ciaValidator.validate(ciaForm, bindingResult);
		if (bindingResult.hasErrors()) {
			cargarFormulario(model, ciaForm, Boolean.FALSE);
			vista = VISTA_EDICION_CIA;
		} else {
			ciaForm.setTelefono(ciaForm.getTelefono().replaceAll(" ", ""));
			if (ciaForm.getId() == 0) {
				// Si es un alta asignamo el identificador y redirigimos a edición cliente
				final long idCia = ciaService.saveCia(ciaForm).getId();
				vista = REDIRECT_EDICION_CIA + idCia;
			} else {
				// Si es una edición redirigimos a gestión cia
				ciaGuardada = true;
				model.addAttribute("ciaGuardada", ciaGuardada);
				ciaService.saveCia(ciaForm);
				vista = REDIRECT_GESTION_CIA;
			}

		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar una compañía.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la compañía.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/editar/{id}")
	public String editarCia(final Model model, @PathVariable("id") final long id) {
		final Cia cia = ciaService.findById(id);
		model.addAttribute("productoGuardado", productoGuardado);
		model.addAttribute("departamentoGuardado", departamentoGuardado);
		productoGuardado = false;
		departamentoGuardado = false;
		cargarFormulario(model, cia, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CIA;
	}

	/**
	 * Acción para ver una compañía.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la compañía.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/ver/{id}")
	public String verCia(final Model model, @PathVariable("id") final long id) {
		final Cia cia = ciaService.findById(id);
		cargarFormulario(model, cia, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_CIA;
	}

	/**
	 * Acción para activar una compañía.
	 * 
	 * @param model - Modelo
	 * @param id    - Identificador de la compañía.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/activar/{id}")
	public String activarCia(final Model model, @PathVariable("id") final long id) {
		final Cia cia = ciaService.findById(id);
		if (!cia.getActivo()) {
			cia.setActivo(true);
		}
		ciaService.saveCia(cia);
		permisos(model);
		return REDIRECT_GESTION_CIA;
	}

	/**
	 * Acción para desactivar una compañía.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de la compañía.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/desactivar/{id}")
	public String desactivarCia(final Model model, @PathVariable("id") final long id) {
		final Cia cia = ciaService.findById(id);
		if (cia.getActivo()) {
			cia.setActivo(false);
		}
		ciaService.saveCia(cia);
		permisos(model);
		return REDIRECT_GESTION_CIA;
	}

	/**
	 * Método para cargar el formulario compañía.
	 * 
	 * @param model       - Modelo
	 * @param cia         - Entidad compañía
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormulario(final Model model, final Cia cia, final Boolean soloLectura) {
		model.addAttribute("ciaForm", cia);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(cia.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
	}

	/********************************************
	 * PRODUCTO
	 ********************************************/

	/**
	 * Acción de nueva alta de producto.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de cia.
	 * @return Devuelve la vista edición con el formulario de producto.
	 */
	@GetMapping("/producto/alta/{idCia}")
	public String altaProducto(final Model model, @PathVariable("idCia") final long idCia) {
		final Producto productoForm = new Producto();
		final Cia cia = new Cia();
		cia.setId(idCia);
		productoForm.setCia(cia);
		productoForm.setActivo(Boolean.TRUE);
		cargarFormularioProducto(model, productoForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_PRODUCTO;
	}

	/**
	 * Acción para guardar/editar un producto.
	 * 
	 * @param model         - Modelo.
	 * @param productoForm  - Entidad producto.
	 * @param bindingResult - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el producto, si
	 *         hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/producto/guardar")
	public String guardarProducto(final Model model, @Valid @ModelAttribute("productoForm") final Producto productoForm,
			final BindingResult bindingResult) {

		final Cia cia = ciaService.findById(productoForm.getCia().getId());
		String vista = REDIRECT_EDICION_CIA + cia.getId();

		if (bindingResult.hasErrors()) {
			cargarFormularioProducto(model, productoForm, Boolean.FALSE);
			vista = VISTA_EDICION_PRODUCTO;
		} else {
			productoGuardado = true;
			model.addAttribute("productoGuardado", productoGuardado);
			cargarFormulario(model, cia, Boolean.FALSE);
			productoService.saveProducto(productoForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un producto.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de producto.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/producto/editar/{id}")
	public String editarProducto(final Model model, @PathVariable("id") final long id) {
		final Producto producto = productoService.findById(id);
		cargarFormularioProducto(model, producto, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_PRODUCTO;
	}

	/**
	 * Acción para ver un producto.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de producto.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/producto/ver/{id}")
	public String verProducto(final Model model, @PathVariable("id") final long id) {
		final Producto producto = productoService.findById(id);
		cargarFormularioProducto(model, producto, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_PRODUCTO;
	}

	/**
	 * Acción para activar un producto.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de producto.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/producto/activar/{id}")
	public String activarProducto(final Model model, @PathVariable("id") final long id) {
		final Producto producto = productoService.findById(id);
		final Cia cia = ciaService.findById(producto.getCia().getId());
		if (!producto.getActivo()) {
			producto.setActivo(true);
		}
		productoService.saveProducto(producto);
		permisos(model);
		cargarFormulario(model, cia, Boolean.FALSE);
		return REDIRECT_EDICION_CIA + cia.getId();
	}

	/**
	 * Acción para desactivar un producto.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del producto.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/producto/desactivar/{id}")
	public String productoDomiciliacion(final Model model, @PathVariable("id") final long id) {
		final Producto producto = productoService.findById(id);
		final Cia cia = ciaService.findById(producto.getCia().getId());
		if (producto.getActivo()) {
			producto.setActivo(false);
		}
		productoService.saveProducto(producto);
		permisos(model);
		cargarFormulario(model, cia, Boolean.FALSE);
		return REDIRECT_EDICION_CIA + cia.getId();
	}

	/**
	 * Método para cargar el formulario producto.
	 * 
	 * @param model       - Modelo.
	 * @param producto    - Entidad compañía.
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioProducto(final Model model, final Producto producto, final Boolean soloLectura) {
		model.addAttribute("productoForm", producto);
		model.addAttribute("soloLectura", soloLectura);
	}

	/********************************************
	 * DEPARTAMENTO
	 ********************************************/

	/**
	 * Acción de nueva alta de departamento.
	 * 
	 * @param model - Modelo.
	 * @param idCia - Identificador de cia.
	 * @return Devuelve la vista edición con el formulario de producto.
	 */
	@GetMapping("/departamento/alta/{idCia}")
	public String altaDepartamento(final Model model, @PathVariable("idCia") final long idCia) {
		final Departamento departamentoForm = new Departamento();
		final Cia cia = new Cia();
		cia.setId(idCia);
		departamentoForm.setCia(cia);
		departamentoForm.setActivo(Boolean.TRUE);
		cargarFormularioDepartamento(model, departamentoForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_DEPARTAMENTO;
	}

	/**
	 * Acción para guardar/editar un departamento.
	 * 
	 * @param model            - Modelo.
	 * @param departamentoForm - Entidad departamento.
	 * @param bindingResult    - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el departamento,
	 *         si hay errores, devuelve la vista edición con los errores.
	 */
	@PostMapping("/departamento/guardar")
	public String guardarDepartamento(final Model model,
			@Valid @ModelAttribute("departamentoForm") final Departamento departamentoForm,
			final BindingResult bindingResult) {
		String vista;

		if (bindingResult.hasErrors()) {
			cargarFormularioDepartamento(model, departamentoForm, Boolean.FALSE);
			vista = VISTA_EDICION_DEPARTAMENTO;
		} else {
			departamentoForm.setTelefono(departamentoForm.getTelefono().replaceAll(" ", ""));
			if (departamentoForm.getId() == 0) {
				// Si es un alta asignamo el identificador y redirigimos a edición departamento
				final long idDepartamento = departamentoService.saveDepartamento(departamentoForm).getId();
				vista = REDIRECT_EDICION_DEPARTAMENTO + idDepartamento;
			} else {
				// Si es una edición redirigimos a gestión departamento
				departamentoGuardado = true;
				model.addAttribute("departamentoGuardado", departamentoGuardado);
				final Cia cia = ciaService.findById(departamentoForm.getCia().getId());
				cargarFormulario(model, cia, Boolean.FALSE);
				departamentoService.saveDepartamento(departamentoForm);
				vista = REDIRECT_EDICION_CIA + departamentoForm.getCia().getId();
			}
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un departamento
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de departamento.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/departamento/editar/{id}")
	public String editarDepartamento(final Model model, @PathVariable("id") final long id) {
		final Departamento departamento = departamentoService.findById(id);
		model.addAttribute("contactoGuardado", contactoGuardado);
		contactoGuardado = false;
		cargarFormularioDepartamento(model, departamento, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_DEPARTAMENTO;
	}

	/**
	 * Acción para ver un departamento.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de departamento.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/departamento/ver/{id}")
	public String verDepartamento(final Model model, @PathVariable("id") final long id) {
		final Departamento departamento = departamentoService.findById(id);
		cargarFormularioDepartamento(model, departamento, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_DEPARTAMENTO;
	}

	/**
	 * Acción para activar un departamento.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de producto.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/departamento/activar/{id}")
	public String activarDepartamento(final Model model, @PathVariable("id") final long id) {
		final Departamento departamento = departamentoService.findById(id);
		final Cia cia = ciaService.findById(departamento.getCia().getId());
		if (!departamento.getActivo()) {
			departamento.setActivo(true);
		}
		departamentoService.saveDepartamento(departamento);
		permisos(model);
		cargarFormulario(model, cia, Boolean.FALSE);
		return REDIRECT_EDICION_CIA + cia.getId();
	}

	/**
	 * Acción para desactivar un departamento.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del departamento.
	 * @return - Devuelve la vista gestión.
	 */
	@GetMapping("/departamento/desactivar/{id}")
	public String departamentoDomiciliacion(final Model model, @PathVariable("id") final long id) {
		final Departamento departamento = departamentoService.findById(id);
		final Cia cia = ciaService.findById(departamento.getCia().getId());
		if (departamento.getActivo()) {
			departamento.setActivo(false);
		}
		departamentoService.saveDepartamento(departamento);
		permisos(model);
		cargarFormulario(model, cia, Boolean.FALSE);
		return REDIRECT_EDICION_CIA + cia.getId();
	}

	/**
	 * Método para cargar el formulario departamento.
	 * 
	 * @param model        - Modelo.
	 * @param departamento - Entidad departamento.
	 * @param soloLectura  - Parámetro para determinar si el formulario es de solo
	 *                     lectura.
	 */
	private void cargarFormularioDepartamento(final Model model, final Departamento departamento,
			final Boolean soloLectura) {
		model.addAttribute("departamentoForm", departamento);
		model.addAttribute("soloLectura", soloLectura);
	}

	/********************************************
	 * CONTACTO DEPARTAMENTOS
	 ********************************************/

	/**
	 * Acción de nueva alta de contacto de departamento.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista edición con el formulario de contacto de
	 *         departamento.
	 */
	@GetMapping("/departamento/contacto/alta/{idDepartamento}")
	public String altaContactoDepartamento(final Model model,
			@PathVariable("idDepartamento") final long idDepartamento) {
		final ContactoDepartamento contactoDepartamentoForm = new ContactoDepartamento();
		final Departamento departamento = new Departamento();
		departamento.setId(idDepartamento);
		contactoDepartamentoForm.setDepartamento(departamento);
		cargarFormularioContactoDepartamento(model, contactoDepartamentoForm, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CONTACTO_DEPARTAMENTO;
	}

	/**
	 * Acción para guardar/editar un contacto de departamento.
	 * 
	 * @param model                    - Modelo.
	 * @param contactoDepartamentoForm - Entidad ContactoDepartamento.
	 * @param bindingResult            - Resultado al comprobar errores.
	 * @return Devuelve la vista gestión si se guarda correctamente el contacto del
	 *         departamento, si hay errores, devuelve la vista edición con los
	 *         errores.
	 */
	@PostMapping("/departamento/contacto/guardar")
	public String guardarContactoDepartamento(final Model model,
			@Valid @ModelAttribute("contactoDepartamentoForm") final ContactoDepartamento contactoDepartamentoForm,
			final BindingResult bindingResult) {

		final Departamento departamento = departamentoService
				.findById(contactoDepartamentoForm.getDepartamento().getId());
		String vista = REDIRECT_EDICION_DEPARTAMENTO + departamento.getId();

		if (bindingResult.hasErrors()) {
			cargarFormularioContactoDepartamento(model, contactoDepartamentoForm, Boolean.FALSE);
			vista = VISTA_EDICION_CONTACTO_DEPARTAMENTO;
		} else {
			contactoGuardado = true;
			model.addAttribute("contactoGuardado", contactoGuardado);
			cargarFormularioDepartamento(model, departamento, Boolean.FALSE);
			contactoDepartamentoForm.setTelefono(contactoDepartamentoForm.getTelefono().replaceAll(" ", ""));
			contactoDepartamentoForm.setMovil(contactoDepartamentoForm.getMovil().replaceAll(" ", ""));
			contactoDepartamentoService.saveContactoDepartamento(contactoDepartamentoForm);
		}
		permisos(model);
		return vista;
	}

	/**
	 * Acción para editar un contacto de un departamento.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de contacto de departamento.
	 * @return Devuelve la vista edición.
	 */
	@GetMapping("/departamento/contacto/editar/{id}")
	public String editarContactoDepartamento(final Model model, @PathVariable("id") final long id) {
		final ContactoDepartamento contactoDepartamento = contactoDepartamentoService.findById(id);
		cargarFormularioContactoDepartamento(model, contactoDepartamento, Boolean.FALSE);
		permisos(model);
		return VISTA_EDICION_CONTACTO_DEPARTAMENTO;
	}

	/**
	 * Acción para ver un contacto de un departamento.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador del contacto de departamento.
	 * @return - Devuelve la vista edición.
	 */
	@GetMapping("/departamento/contacto/ver/{id}")
	public String verContactoDepartamento(final Model model, @PathVariable("id") final long id) {
		final ContactoDepartamento contactoDepartamento = contactoDepartamentoService.findById(id);
		cargarFormularioContactoDepartamento(model, contactoDepartamento, Boolean.TRUE);
		permisos(model);
		return VISTA_EDICION_CONTACTO_DEPARTAMENTO;
	}

	/**
	 * Acción para eliminar un contacto de un departamento.
	 * 
	 * @param model - Modelo.
	 * @param id    - Identificador de contacto de departamento.
	 * @return Devuelve la vista gestión.
	 */
	@GetMapping("/departamento/contacto/eliminar/{id}")
	public String activarContactoDepartamento(final Model model, @PathVariable("id") final long id) {
		final ContactoDepartamento contactoDepartamento = contactoDepartamentoService.findById(id);
		final Departamento departamento = departamentoService.findById(contactoDepartamento.getDepartamento().getId());
		if (contactoDepartamento != null) {
			contactoDepartamentoService.deleteContactoDepartamento(contactoDepartamento);
		}
		permisos(model);
		model.addAttribute("contactoEliminado", Boolean.TRUE);
		cargarFormularioDepartamento(model, departamento, Boolean.FALSE);
		return REDIRECT_EDICION_DEPARTAMENTO + departamento.getId();
	}

	/**
	 * Método para cargar el formulario departamento.
	 * 
	 * @param model                - Modelo
	 * @param contactoDepartamento - Entidad ContactoDepartamento
	 * @param soloLectura          - Parámetro para determinar si el formulario es
	 *                             de solo lectura.
	 */
	private void cargarFormularioContactoDepartamento(final Model model,
			final ContactoDepartamento contactoDepartamento, final Boolean soloLectura) {
		model.addAttribute("contactoDepartamentoForm", contactoDepartamento);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(contactoDepartamento.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
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
	public String ajaxMunicipios(@RequestParam("provincia") final Provincia provincia, final Model model) {
		final List<Municipio> listaMunicipios = municipioService.findByProvincia(provincia);
		model.addAttribute("listaMunicipios", listaMunicipios);
		return VISTA_EDICION_CIA + "::listaMunicipios";
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
		model.addAttribute("ciaActive", Boolean.TRUE);
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
