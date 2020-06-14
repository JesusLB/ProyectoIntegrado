package com.gseg.validacion;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.siniestro.Siniestro;
import com.gseg.servicio.siniestro.SiniestroService;
import com.gseg.utils.ConstantesMensajes;

/**
 * Validación de siniestro.
 * @author Jesús López Barragán
 *
 */
@Component
public class SiniestroValidator implements Validator {

	/**
	 * Servicio de siniestro.
	 */
	@Autowired
	SiniestroService siniestroService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Siniestro.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Siniestro siniestro = (Siniestro) target;
		
		if (siniestro.getPoliza() == null || StringUtils.isEmpty(siniestro.getPoliza().getNumPoliza())) {
			errors.rejectValue("poliza.numPoliza", ConstantesMensajes.COD_ERROR_NUMERO_POLIZA, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}
		
		if (siniestro.getTipo() != null && siniestro.getTipo().equals("-1")) {
			errors.rejectValue("tipo", ConstantesMensajes.COD_ERROR_TIPO_SINIESTRO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}
		
		if (siniestro.getEstado() != null && siniestro.getEstado().equals("-1")) {
			errors.rejectValue("estado", ConstantesMensajes.COD_ERROR_ESTADO_SINIESTRO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}
		
		if (siniestro.getResponsabilidad() != null && siniestro.getResponsabilidad().equals("-1")) {
			errors.rejectValue("responsabilidad", ConstantesMensajes.COD_ERROR_RESPONSABILDAD_SINIESTRO, ConstantesMensajes.MSG_CAMPO_OBLIGATORIO);
		}
	}

}
