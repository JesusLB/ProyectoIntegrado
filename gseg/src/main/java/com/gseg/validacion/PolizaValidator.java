package com.gseg.validacion;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.poliza.Poliza;
import com.gseg.servicio.cliente.ClienteService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación de póliza.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class PolizaValidator implements Validator {

	/**
	 * Servicio de cliente.
	 */
	@Autowired
	ClienteService clienteService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Poliza.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Poliza poliza = (Poliza) target;

		if (poliza.getFormaPago() != null && poliza.getFormaPago().equals("-1")) {
			errors.rejectValue("formaPago", "1", ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}

		if (poliza.getCliente() != null && StringUtils.isNotEmpty(poliza.getCliente().getIdentificador())) {
			if (poliza.getCliente().getIdentificador().length() == 9) {
				if (!Identificador.isValido(poliza.getCliente().getIdentificador())) {
					errors.rejectValue("cliente.identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				} else if (poliza.getCliente().getId() == 0 && !clienteExiste(poliza.getCliente().getIdentificador())) {
					errors.rejectValue("cliente.identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_CLIENTE_NO_EXISTE);
				}
			} else {
				errors.rejectValue("cliente.identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		} else {
			errors.rejectValue("cliente.identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
			errors.rejectValue("cliente.tipoDoc", ConstantesMensajes.COD_ERROR_TIPO_DOCUMENTO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}

	}

	/**
	 * Comprueba si un cliente existe.
	 * 
	 * @param identificador - Identificador del cliente.
	 * @return Devuelve true si existe, false, en caso contrario.
	 */
	private boolean clienteExiste(final String identificador) {
		return clienteService.findByIdentificador(identificador) != null;
	}

}
