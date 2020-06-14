package com.gseg.validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.siniestro.Lesionado;
import com.gseg.servicio.siniestro.LesionadoService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación de lesionado.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class LesionadoValidator implements Validator {
	
	@Autowired
	LesionadoService lesionadoService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Lesionado.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Lesionado lesionado = (Lesionado)target;
		
		// Validación identificador
		if (!lesionado.getIdentificador().trim().equals("")) {
			if (lesionado.getIdentificador().length() == 9) {
				if (!Identificador.isValido(lesionado.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				}				
			} else {
				errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		}
		
	}

}
