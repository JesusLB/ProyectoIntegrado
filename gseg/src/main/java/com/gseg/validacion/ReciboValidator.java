package com.gseg.validacion;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.recibo.Recibo;
import com.gseg.servicio.poliza.ReciboService;
import com.gseg.utils.ConstantesMensajes;

/**
 * Validación de recibo.
 * 
 * @author Jesús Lópz Barragán
 *
 */
@Component
public class ReciboValidator implements Validator {

	/**
	 * Servicio de recibo.
	 */
	@Autowired
	ReciboService reciboService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Recibo.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Recibo recibo = (Recibo) target;

		if (recibo.getEstado() != null && recibo.getEstado().equals("-1")) {
			errors.rejectValue("estado", ConstantesMensajes.COD_ERROR_ESTADO_RECIBO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}

		if (recibo.getPoliza() == null || StringUtils.isEmpty(recibo.getPoliza().getNumPoliza())) {
			errors.rejectValue("poliza.numPoliza", ConstantesMensajes.COD_ERROR_NUMERO_POLIZA, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}

	}

}
