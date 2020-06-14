package com.gseg.servicio.poliza;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.poliza.Poliza;
import com.gseg.repositorio.poliza.PolizaRepository;

/**
 * Servicio de póliza.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class PolizaService {

	/**
	 * Repositorio de póliza.
	 */
	@Autowired
	PolizaRepository polizaRepository;

	/**
	 * Guarda una entidad póliza.
	 * 
	 * @param poliza - Entidad poliza.
	 * @return Devuelve la entidad guardada.
	 */
	public Poliza savePoliza(final Poliza poliza) {
		Long idPoliza = polizaRepository.findMaxId();
		if (poliza.getActivo()) {			
			poliza.setFechaAnulacion(null);
		} else {
			poliza.setFechaAnulacion(LocalDate.now());
		}
		if (idPoliza == null) {
			// Si no hay pólizas, asignamos al primer id 1
			idPoliza = (long) 1;
			poliza.setId(idPoliza);
		} else {
			if (poliza.getId() == 0) {
				// Si hay pólizas y es un alta, incrementamos en 1 el último id
				idPoliza++;
				poliza.setId(idPoliza);
			}
		}
		return polizaRepository.save(poliza);
	}

	/**
	 * Localiza una póliza por su identificador.
	 * 
	 * @param id - Identificador de póliza.
	 * @return Devuelve una entidad póliza.
	 */
	public Poliza findById(final long id) {
		return polizaRepository.findById(id);
	}

	/**
	 * Obtiene un listado de todas las pólizas.
	 * 
	 * @return Devuelve un listado de todas las entidades de póliza.
	 */
	public List<Poliza> findAll() {
		return polizaRepository.findAll();
	}

	/**
	 * Localiza una póliza por su cliente.
	 * 
	 * @param cliente - Entidad cliente.
	 * @return Devuelve una entidad póliza.
	 */
	public List<Poliza> findByCliente(final Cliente cliente) {
		return polizaRepository.findByCliente(cliente);
	}

	/**
	 * Localiza una póliza por su número.
	 * 
	 * @param numPoliza - Número de póliza.
	 * @return Devuelve una entidad póliza.
	 */
	public Poliza findByNumPoliza(final String numPoliza) {
		return polizaRepository.findByNumPoliza(numPoliza);
	}

	/**
	 * Cuenta el número total de pólizas que existen en base de datos.
	 * 
	 * @return Devuelve el número total de pólizas.
	 */
	public long count() {
		return polizaRepository.count();
	}

}
