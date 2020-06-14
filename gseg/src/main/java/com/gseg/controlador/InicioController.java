package com.gseg.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gseg.enumerado.TipoDocumento;
import com.gseg.enumerado.TipoGenero;
import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.usuario.Usuario;
import com.gseg.servicio.MunicipioService;
import com.gseg.servicio.ProvinciaService;
import com.gseg.servicio.TipoViaService;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.servicio.cliente.DomiciliacionService;
import com.gseg.servicio.poliza.PolizaService;
import com.gseg.servicio.siniestro.SiniestroService;
import com.gseg.servicio.usuario.UsuarioService;

/**
 * Controlador inicio.
 * 
 * @author Jesús López Barragán
 *
 */
@Controller
@RequestMapping("/gseg/inicio")
public class InicioController {

	/**
	 * Vista de inicio para gestor y administrador.
	 */
	private final static String VISTA_INICIO = "inicio";

	/**
	 * Vista de inicio para el usuario.
	 */
	private final static String VISTA_INICIO_USUARIO = "usuario/inicio";

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	/**
	 * Servicio de póliza.
	 */
	@Autowired
	PolizaService polizaService;

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

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
	 * Servicio de domiciliación.
	 */
	@Autowired
	DomiciliacionService domiciliacionService;

	/********************************************
	 * INICIO
	 ********************************************/

	/**
	 * Vista gestión gestor y administrador.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/")
	public String gestionInicio(final Model model) {
		model.addAttribute("numeroClientes", clienteService.count());
		model.addAttribute("numeroPolizas", polizaService.count());
		model.addAttribute("numeroSiniestros", siniestroService.count());
		permisos(model);
		return VISTA_INICIO;
	}

	/**
	 * Vista usuario con los datos del cliente.
	 * 
	 * @param model - Modelo.
	 * @return Devuelve la vista de gestión.
	 */
	@GetMapping("/usuario")
	public String gestionInicioUsuario(final Model model) {
		final Usuario usuario = getUsuarioLogado();
		final Cliente cliente = clienteService.findByIdentificador(usuario.getUsuario());
		model.addAttribute("nombreUsuario",
				(cliente.getNombre() + " " + cliente.getApe1() + " " + cliente.getApe2()).trim());
		cargarFormularioCliente(model, cliente, Boolean.TRUE);
		permisos(model);
		return VISTA_INICIO_USUARIO;
	}

	/**
	 * Método para cargar el formulario cliente.
	 * 
	 * @param model       - Modelo
	 * @param cliente     - Entidad cliente
	 * @param soloLectura - Parámetro para determinar si el formulario es de solo
	 *                    lectura.
	 */
	private void cargarFormularioCliente(final Model model, final Cliente cliente, final Boolean soloLectura) {
		model.addAttribute("clienteForm", cliente);
		model.addAttribute("soloLectura", soloLectura);
		model.addAttribute("listaProvincias", provinciaService.findAll());
		model.addAttribute("listaMunicipios", municipioService.findByProvincia(cliente.getProvincia()));
		model.addAttribute("listaVias", tipoViaService.findAll());
		model.addAttribute("listaTipoDocumento", TipoDocumento.getListaTipoDocumento());
		model.addAttribute("listaGenero", TipoGenero.getListaGenero());
	}

	/********************************************
	 * PERMISOS *
	 ********************************************/

	/**
	 * Carga los permisos en el modelo según el rol del usuario.
	 * 
	 * @param model - Modelo
	 */
	private void permisos(final Model model) {
		final Usuario usu = getUsuarioLogado();
		model.addAttribute("inicioActive", Boolean.TRUE);
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

	private Usuario getUsuarioLogado() {
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails user = null;
		if (principal instanceof UserDetails) {
			user = (UserDetails) principal;
		}
		final Usuario usu = usuarioService.findByUsuario(user.getUsername());
		return usu;
	}

}
