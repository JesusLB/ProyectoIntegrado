package com.gseg.servicio.poliza;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.poliza.Poliza;
import com.gseg.modelo.recibo.Recibo;
import com.gseg.repositorio.poliza.ReciboRepository;

/**
 * Servicio de recibo.
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class ReciboService {

	/**
	 * Repositorio de recibo.
	 */
	@Autowired
	ReciboRepository reciboRepository;

	/**
	 * Guarda una entidad recibo.
	 * 
	 * @param recibo - Entidad recibo.
	 * @return Devuelve la entidad guardada.
	 */
	public Recibo saveRecibo(final Recibo recibo) {
		Long idRecibo = reciboRepository.findMaxId();
		if (idRecibo == null) {
			// Si no hay recibos, asignamos al primer id 1
			idRecibo = (long) 1;
			recibo.setId(idRecibo);
		} else {
			if (recibo.getId() == 0) {
				// Si hay recibos y es un alta, incrementamos en 1 el último id
				idRecibo++;
				recibo.setId(idRecibo);
			}
		}
		return reciboRepository.save(recibo);
	}

	/**
	 * Localiza un recibo por su identificador.
	 * 
	 * @param id - Identificador de recibo.
	 * @return Devuelve una entidad recibo.
	 */
	public Recibo findById(final long id) {
		return reciboRepository.findById(id);
	}

	/**
	 * Obtiene un listado de recibos filtrados por póliza.
	 * 
	 * @param poliza - Entidad póliza.
	 * @return Devuelve un listado de entidades recibo filtrados por póliza.
	 */
	public List<Recibo> findByPoliza(final Poliza poliza) {
		return reciboRepository.findByPoliza(poliza);
	}

	/**
	 * Obtiene un listado de todos los recibos.
	 * 
	 * @return Devuelve un listado de entidades recibo.
	 */
	public List<Recibo> findAll() {
		return reciboRepository.findAll();
	}

}
