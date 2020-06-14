package com.gseg.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;
import com.gseg.repositorio.MunicipioRepository;

/**
 * Servicio de municipio.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class MunicipioService {

	/**
	 * Repositorio de municipio.
	 */
	@Autowired
	MunicipioRepository repositorio;

	/**
	 * Localiza un municipio por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad municipio.
	 */
	public Municipio findById(final long id) {
		return repositorio.findById(id);
	}

	/**
	 * Localiza un municipio por su nombre.
	 * 
	 * @param nombre - Nombre del municipio.
	 * @return Devuelve una entidad municipio.
	 */
	public Municipio findByNombre(final String nombre) {
		return repositorio.findByNombre(nombre);
	}

	/**
	 * Obtiene un listado de todos los municipios.
	 * 
	 * @return Devuelve un listado con todas las entidades municipio.
	 */
	public List<Municipio> findAll() {
		return repositorio.findAll();
	}

	/**
	 * Obtiene un listado de municipios filtrados por provincia.
	 * 
	 * @param provincia - Entidad provincia.
	 * @return Devuelve un listado de entidades municipio filtrado por provincia.
	 */
	public List<Municipio> findByProvincia(final Provincia provincia) {
		return repositorio.findByProvincia(provincia);
	}
}
