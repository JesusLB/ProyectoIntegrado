package com.gseg.validacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gseg.modelo.siniestro.Contrario;
import com.gseg.servicio.siniestro.ContrarioService;
import com.gseg.utils.ConstantesMensajes;
import com.gseg.utils.Identificador;

/**
 * Validación de cliente.
 * 
 * @author Jesús López Barragán
 *
 */
@Component
public class ContrarioValidator implements Validator {
	
	@Autowired
	ContrarioService contrarioService;

	@Override
	public boolean supports(final Class<?> clazz) {
		return Contrario.class.equals(clazz);
	}

	/**
	 * Validaciones.
	 */
	@Override
	public void validate(final Object target, final Errors errors) {
		final Contrario contrario = (Contrario)target;
		
		// Validación identificador
		if (!contrario.getIdentificador().trim().equals("")) {
			if (contrario.getIdentificador().length() == 9) {
				if (!Identificador.isValido(contrario.getIdentificador())) {
					errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_IDENTIFICADOR_NO_VALIDO);
				}				
			} else {
				errors.rejectValue("identificador", ConstantesMensajes.COD_ERROR_IDENTIFICADOR, ConstantesMensajes.MSG_TAMANIO_IDENTIFICADOR);
			}
		}
		
	}

}
