package com.gseg.servicio.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.repositorio.cliente.ClienteRepository;

/**
 * Servicio cliente
 * 
 * @author Jesús López Barragán
 *
 */
@Service
public class ClienteService {

	/**
	 * Repositorio de cliente.
	 */
	@Autowired
	ClienteRepository clienteRepository;

	/**
	 * Guarda una entidad cliente.
	 * 
	 * @param cliente - Entidad cliente.
	 * @return Devuelve la entidad guardada.
	 */
	public Cliente saveCliente(final Cliente cliente) {
		Long idCliente = clienteRepository.findMaxId();
		cliente.setIdentificador(cliente.getIdentificador().toUpperCase());
		if (idCliente == null) {
			idCliente = (long) 1;
			cliente.setId(idCliente);
		} else {
			if (cliente.getId() == 0) {
				idCliente++;
				cliente.setId(idCliente);
			}
		}		
		return clienteRepository.save(cliente);
	}

	/**
	 * Localiza un cliente por su identificador(nif, nie, cif)
	 * 
	 * @param identificador - Identificador del cliente.
	 * @return Devuelve una entidad cliente.
	 */
	public Cliente findByIdentificador(final String nif) {
		return clienteRepository.findByIdentificador(nif);
	}

	/**
	 * Localiza un cliente por su identificador
	 * 
	 * @param id - Identificador del cliente.
	 * @return Devuelve una entidad cliente.
	 */
	public Cliente findById(final long id) {
		return clienteRepository.findById(id);
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	/**
	 * Cuenta el número total de clientes que existen en base de datos.
	 * 
	 * @return Devuelve el número total de clientes.
	 */
	public long count() {
		return clienteRepository.count();
	}

}
