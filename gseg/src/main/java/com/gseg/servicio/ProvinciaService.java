package com.gseg.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.Provincia;
import com.gseg.repositorio.ProvinciaRepository;

/**
 * Servicio de provincia.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class ProvinciaService {

	/**
	 * Repositorio de provincia.
	 */
	@Autowired
	ProvinciaRepository repositorio;

	/**
	 * Localiza una provincia por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad provincia.
	 */
	public Provincia findById(final long id) {
		return repositorio.findById(id);
	}

	/**
	 * Localiza una provincia por su nombre.
	 * 
	 * @param nombre - Nombre de la provincia.
	 * @return Devuelve una entidad provincia.
	 */
	public Provincia findByNombre(final String nombre) {
		return repositorio.findByNombre(nombre);
	}

	/**
	 * Obtiene un listado de todas las provincias.
	 * 
	 * @return Devuelve un listado de todas las entidades provincia.
	 */
	public List<Provincia> findAll() {
		return repositorio.findAll();
	}

}
