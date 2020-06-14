package com.gseg.controlador.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Producto;
import com.gseg.servicio.poliza.CiaService;
import com.gseg.servicio.poliza.ProductoService;

/**
 * RestController de producto.
 * 
 * @author Jesús López Barragán
 *
 */
@RestController
public class ProductoRestController {

	/**
	 * Servicio de producto.
	 */
	@Autowired
	ProductoService productoService;

	/**
	 * Servicio de cia.
	 */
	@Autowired
	CiaService ciaService;

	/**
	 * Obtiene una lista de los productos filtrado por compañía.
	 * 
	 * @param idCia - Identificador de compañía.
	 * @return Devuelve un listado de los productos filtrado por compañía en formato
	 *         JSON.
	 */
	@RequestMapping(path = "/listaProductos/{idCia}", method = RequestMethod.GET)
	public List<Producto> listaProductos(@PathVariable("idCia") final long idCia) {
		final Cia cia = ciaService.findById(idCia);
		return productoService.findByCia(cia);
	}

}
