package com.gseg.validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.cliente.Cliente;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación de cliente.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class ClienteValidator implements Validator {

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Cliente.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Cliente cliente = (Cliente) target;

		// Validación identificador
		if (!cliente.getIdentificador().trim().equals("")) {
			if (cliente.getIdentificador().length() == 9) {
				if (!Identificador.isValido(cliente.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				}
				if (cliente.getId() == 0 && clienteExiste(cliente.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_CLIENTE_EXISTE);
				}
			} else {
				errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		}

		// Validación tipo de documento del identificador
		if (cliente.getTipoDoc() == null || cliente.getTipoDoc().equals("-1")) {
			errors.rejectValue("tipoDoc", ConstantesMensajes.COD_ERROR_TIPO_DOCUMENTO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}

	}

	/**
	 * Comprueba si un cliente existe.
	 * 
	 * @param identificador - Identificador del cliente.
	 * @return Devuelve true si el cliente existe, false en caso contrario.
	 */
	private boolean clienteExiste(final String identificador) {
		return clienteService.findByIdentificador(identificador) != null;
	}

}
