package com.gseg.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gseg.modelo.Municipio;
import com.gseg.modelo.Provincia;

/**
 * Repositorio municipio
 * 
 * @author Jesús López Barragán
 *
 */
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Long> {

	/**
	 * Localiza un municipio por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad municipio.
	 */
	Municipio findById(long id);

	/**
	 * Localiza un municipio por su nombre.
	 * 
	 * @param nombre - Nombre del municipio.
	 * @return Devuelve una entidad municipio.
	 */
	Municipio findByNombre(String nombre);

	/**
	 * Obtiene un listado de municipios filtrados por provincia.
	 * 
	 * @param provincia - Entidad provincia.
	 * @return Devuelve un listado de entidades municipio filtrado por provincia.
	 */
	List<Municipio> findByProvincia(Provincia provincia);

}
