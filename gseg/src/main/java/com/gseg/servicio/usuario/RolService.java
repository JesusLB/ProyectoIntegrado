package com.gseg.servicio.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.usuario.Rol;
import com.gseg.repositorio.usuario.RolRepository;

/**
 * Servicio de Rol.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class RolService {

	/**
	 * Repositorio de rol.
	 */
	@Autowired
	RolRepository rolRepository;

	/**
	 * Guarda una entidad rol.
	 * 
	 * @param rol - Entidad rol.
	 * @return Devuelve la entidad rol guardada.
	 */
	public Rol saveRol(final Rol rol) {
		Long idRol = rolRepository.findMaxId();
		if (rol == null) {
			// Si no hay roles, asignamos al primer id 1
			idRol = (long) 1;
			rol.setId(idRol);
		} else {
			if (rol.getId() == 0) {
				// Si hay roles y es un alta, incrementamos en 1 el último id
				idRol++;
				rol.setId(idRol);
			}
		}
		return rolRepository.save(rol);
	}

	/**
	 * Lista todos los roles.
	 * 
	 * @return Devuelve una lista con todas las entidades de rol.
	 */
	public List<Rol> findAll() {
		return rolRepository.findAll();
	}

	/**
	 * Localiza un rol por nombre.
	 * 
	 * @param nombre - Nombre del rol.
	 * @return Devuelve una entidad rol.
	 */
	public Rol findByNombre(final String nombre) {
		return rolRepository.findByNombre(nombre);
	}

	/**
	 * Locliza un rol por su identificador.
	 * 
	 * @param id - Identificador del rol.
	 * @return Devuelve una entidad rol.
	 */
	public Rol findById(final long id) {
		return rolRepository.findById(id);
	}

}
