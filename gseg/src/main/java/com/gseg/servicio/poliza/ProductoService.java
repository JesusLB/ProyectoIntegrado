package com.gseg.servicio.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.poliza.Cia;
import com.gseg.modelo.poliza.Producto;
import com.gseg.repositorio.poliza.ProductoRepository;

/**
 * Servicio de producto.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class ProductoService {

	/**
	 * Repositorio de producto.
	 */
	@Autowired
	ProductoRepository productoRepository;

	/**
	 * Guarda una entidad producto.
	 * 
	 * @param producto - Entidad producto.
	 * @return Devuelve la entidad guardada.
	 */
	public Producto saveProducto(final Producto producto) {
		Long idProducto = productoRepository.findMaxId();
		if (idProducto == null) {
			// Si no hay productos, asignamos al primer id 1
			idProducto = (long) 1;
			producto.setId(idProducto);
		} else {
			if (producto.getId() == 0) {
				// Si hay productos y es un alta, incrementamos en 1 el último id
				idProducto++;
				producto.setId(idProducto);
			}
		}
		return productoRepository.save(producto);
	}

	/**
	 * Localiza un producto por su identificador.
	 * 
	 * @param id - Identificador de producto.
	 * @return Devuelve una entidad producto.
	 */
	public Producto findById(final long id) {
		return productoRepository.findById(id);
	}

	/**
	 * Obtiene un listado de productos filtrado por compañía.
	 * 
	 * @param cia - Entidad cia.
	 * @return Devuelve un listado de entidades de producto.
	 */
	public List<Producto> findByCia(final Cia cia) {
		return productoRepository.findByCia(cia);
	}

	/**
	 * Obtiene un listado de todos los productos.
	 * 
	 * @return Devuelve un listado de entidades producto.
	 */
	public List<Producto> findAll() {
		return productoRepository.findAll();
	}

}