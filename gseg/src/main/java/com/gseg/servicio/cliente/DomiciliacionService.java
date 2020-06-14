package com.gseg.servicio.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.modelo.cliente.Domiciliacion;
import com.gseg.repositorio.cliente.DomiciliacionRepository;

/**
 * Servicio domiciliación
 * @author Jesus
 *
 */
@Service
public class DomiciliacionService {
	
	@Autowired
	DomiciliacionRepository domiciliacionRepository;
	
	public Domiciliacion saveDomiciliacion(final Domiciliacion domiciliacion) {
		Long idDomiciliacion = domiciliacionRepository.findMaxId();
		if (idDomiciliacion == null) {
			// Si no hay domiciliaciones, asignamos al primer id 1
			idDomiciliacion = (long)1;
			domiciliacion.setId(idDomiciliacion);
		} else {
			if (domiciliacion.getId() == 0) {
				// Si hay domiciliaciones y es un alta, incrementamos en 1 el último id
				idDomiciliacion++;
				domiciliacion.setId(idDomiciliacion);
			}
		}		
		return domiciliacionRepository.save(domiciliacion);
	}
	
	public Domiciliacion findById(final long id) {
		return domiciliacionRepository.findById(id);
	}
	
	public List<Domiciliacion> findAll() {
		return domiciliacionRepository.findAll();
	}
	
	public List<Domiciliacion> findByCliente(final Cliente cliente){
		return domiciliacionRepository.findByCliente(cliente);
	}

}
