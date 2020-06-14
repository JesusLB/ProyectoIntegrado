package com.gseg.validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.siniestro.Taller;
import com.gseg.servicio.siniestro.TallerService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación de taller.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class TallerValidator implements Validator {

	/**
	 * Servicio de taller.
	 */
	@Autowired
	TallerService tallerService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Taller.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Taller taller = (Taller) target;

		// Validación identificador
		if (!taller.getIdentificador().trim().equals("")) {
			if (taller.getIdentificador().length() == 9) {
				if (!Identificador.isValido(taller.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				}
			} else {
				errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		}

	}

}
