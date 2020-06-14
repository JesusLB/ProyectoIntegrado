package com.gseg.servicio.siniestro;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.repositorio.siniestro.SiniestroRepository;

/**
 * Servicio de siniestro
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class SiniestroService {

	/**
	 * Repositorio de siniestro.
	 */
	@Autowired
	SiniestroRepository siniestroRepository;

	/**
	 * Guarda una entidad siniestro.
	 * 
	 * @param siniestro - Entidad siniestro.
	 * @return Devuelve la entidad guardada.
	 */
	public Siniestro saveSiniestro(final Siniestro siniestro) {
		Long idSiniestro = siniestroRepository.findMaxId();
		if (idSiniestro == null) {
			// Si no hay siniestros, asignamos al primer id 1
			idSiniestro = (long) 1;
			siniestro.setId(idSiniestro);
		} else {
			if (siniestro.getId() == 0) {
				// Si hay siniestros y es un alta, incrementamos en 1 el último id
				idSiniestro++;
				siniestro.setId(idSiniestro);
			}
		}		
		return siniestroRepository.save(siniestro);
	}

	/**
	 * Localiza una entidad siniestro por su identificador.
	 * 
	 * @param id - Identificador.
	 * @return Devuelve una entidad siniestro.
	 */
	public Siniestro findById(final long id) {
		return siniestroRepository.findById(id);
	}

	/**
	 * Obtiene un listado de siniestros filtrados por su póliza.
	 * 
	 * @param poliza - Entidad póliza.
	 * @return Devuelve un listad
	 */
	public List<Siniestro> findByPoliza(final Poliza poliza) {
		return siniestroRepository.findByPoliza(poliza);
	}

	public List<Siniestro> findByCliente(final Cliente cliente) {
		return siniestroRepository.findByCliente(cliente);
	}

	/**
	 * Obtiene un listado de siniestros filtrados por su cliente.
	 * 
	 * @param cliente - Entidad cliente.
	 * @return Devuelve un listado de entidades siniestro filtrado por su cliente.
	 */
	public List<Siniestro> findAll() {
		return siniestroRepository.findAll();
	}

	/**
	 * Cuenta el número total de siniestros que existen en base de datos.
	 * 
	 * @return Devuelve el número total de siniestros.
	 */
	public long count() {
		return siniestroRepository.count();
	}

}
